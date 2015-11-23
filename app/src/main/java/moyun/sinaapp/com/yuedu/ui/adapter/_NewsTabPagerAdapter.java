package moyun.sinaapp.com.yuedu.ui.adapter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Request;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.news.DocBean;
import moyun.sinaapp.com.yuedu.entity.news.SlidesBean;
import moyun.sinaapp.com.yuedu.http.HttpHandler;
import moyun.sinaapp.com.yuedu.utils.Lang;
import moyun.sinaapp.com.yuedu.utils.SingletonUtil;
import moyun.sinaapp.com.yuedu.utils.UserSetting;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Moy on 九月14  014.
 */
public class _NewsTabPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private static String[] tabTitle = {
//            "推荐", "地方新闻",
            "头条", "娱乐", "财经",
            "科技", "历史", "军事",
            "体育", "汽车", "时尚",
    };
    private static String ifeng = "http://api.iclient.ifeng.com/ClientNews?id=%s&page=%d";
    private static String[] code = {
//            "url_1", "url_2",
            "SYLB10,SYDT10,SYRECOMMEND", "YL53,FOCUSYL53", "CJ33,FOCUSCJ33",
            "KJ123,FOCUSKJ123", "LS153,FOCUSLS153", "JS83,FOCUSJS83",
            "TY43,FOCUSTY43,TYLIVE", "QC45,FOCUSQC45", "SS78,FOCUSSS78"
    };
    private static int[] page = new int[code.length];

    public final int tabCount = tabTitle.length > code.length ? code.length : tabTitle.length;
    private ArrayList<View> views;
    private View nowView;
    private View loadingView;
    private LayoutInflater inflater;

    /**
     * 第一次初始化   —> 绑定失败点击事件 & 显示刷新
     * |->成功  —> 解除失败点击事件 & 绑定下拉刷新 todo 怎么知道第几次刷新
     * |->失败  —> 显示失败页面
     */
    public static final int HANDLER_INIT_NEWS = 1;
    public static final int HANDLER_REFRESH_NEWS = 2;
    public static final int HANDLER_LOADING_NEWS_SUCCESS = 3;
    public static final int HANDLER_LOADING_NEWS_ERROR = 4;

    public static final String INTENT_POS = "position"; // 当前操作的viewpager

    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int pos = (int) msg.obj;

            switch (msg.what) {
                case HANDLER_INIT_NEWS:
                    Logger.v(" -- > 初始化新闻 " + pos);
                    break;
                case HANDLER_REFRESH_NEWS:
                    Logger.v(" -- > 刷新新闻 " + pos);
                    break;
                case HANDLER_LOADING_NEWS_SUCCESS:
                    Logger.v(" -- > 加载新闻成功 " + pos);
                    break;
                case HANDLER_LOADING_NEWS_ERROR:
                    Logger.v(" -- > 加载新闻失败 " + pos);
                    break;
            }
        }
    };

    public _NewsTabPagerAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.views = new ArrayList<>();
        for (int i = 0; i < tabCount; i++) {
            this.views.add(inflater.inflate(R.layout.wg_pager_content, null));
            this.views.add(inflater.inflate(android.R.layout.simple_list_item_1, null));
        }
        Arrays.fill(page, 1);
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        nowView = views.get(position);
        nowView.findViewById(R.id.loading).setVisibility(View.VISIBLE);
        container.addView(nowView);
        return nowView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private void initViewAndEvent() {
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) nowView.findViewById(R.id.swipe_ly);
        refreshLayout.setColorSchemeColors(
                nowView.getResources().getColor(R.color.google_blue),
                nowView.getResources().getColor(R.color.google_red),
                nowView.getResources().getColor(R.color.google_yellow),
                nowView.getResources().getColor(R.color.google_green));

//        newsItems.setAdapter(new NewsItemAdapter(nowView.getContext()));

    }

    private void _getNews(final int position) {
        Logger.v(" --> url "+ String.format(ifeng, code[position], page[position]++));
        try {
            HttpHandler.me().getAsy(String.format(ifeng, code[position], page[position]++), new HttpHandler.OnAsyResultCallBack<String>(String.class) {
                @Override
                public void onResponse(String rs) {
                    views.get(position).findViewById(R.id.loading).setVisibility(View.GONE);
                    Logger.v("隐藏 加载条");
                    RecyclerView nItem = (RecyclerView) views.get(position).findViewById(R.id.news_items);
                    nItem.setVisibility(View.VISIBLE);
                    // --todo 数据
                    List<Object> data = new ArrayList<Object>();
                    try {
                        JSONArray top = new JSONArray(rs);
                        JSONArray items_1 = top.getJSONArray(0);
                        for (int i = 0; i < items_1.length(); i++) {
                            JSONObject item = items_1.getJSONObject(i);
                            String type = (String) item.get("type");
                            if ("doc".equals(type)) {
                                data.add(SingletonUtil.gson().fromJson(item.toString(), DocBean.class));
                            }else if ("slide".equals(type)) {
                                data.add(SingletonUtil.gson().fromJson(item.toString(), SlidesBean.class));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Logger.v(" -- 数据" + rs);
                }

                @Override
                public void onFailure(Request request, IOException e) {
                    if (page[position] == 1 || !UserSetting.isNetworkAvailable(views.get(position).getContext())) {
                        Lang.handMsg(handler, HANDLER_LOADING_NEWS_ERROR, null);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error:
                Logger.v("联网失败点击事件");
                break;
        }
    }
}
