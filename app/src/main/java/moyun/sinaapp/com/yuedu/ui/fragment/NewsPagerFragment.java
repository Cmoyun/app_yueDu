package moyun.sinaapp.com.yuedu.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Request;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.news.DocBean;
import moyun.sinaapp.com.yuedu.entity.news.SlidesBean;
import moyun.sinaapp.com.yuedu.http.HttpHandler;
import moyun.sinaapp.com.yuedu.ui.adapter.NewsItemAdapter;
import moyun.sinaapp.com.yuedu.ui.util.DividerItemDecoration;
import moyun.sinaapp.com.yuedu.utils.Lang;
import moyun.sinaapp.com.yuedu.utils.SingletonUtil;
import moyun.sinaapp.com.yuedu.utils.UserSetting;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moy on 九月18  018.
 */
@SuppressLint("ValidFragment")
public class NewsPagerFragment extends Fragment implements View.OnClickListener {


    private String domain = "http://api.iclient.ifeng.com/ClientNews?id=%s&page=%d";
    private int nowPage = 1;
    private String code;
    View v;
    // res id
    RecyclerView newsItems;
    View loadView;
    View error;
    NewsItemAdapter newsItemAdapter;
    SwipeRefreshLayout swipeLy;
    FloatingActionButton floatButton;

//    public static final String INTENT_POS_FRAGMENT = "fragment_index";

    public static final int HANDLER_INIT_A_REFRESH_NEWS = 1;  // 初始化 & 刷新
    public static final int HANDLER_LOADING_NEXT_NEWS = 2; // 更新

    public Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_INIT_A_REFRESH_NEWS:
                    nowPage = 1;
                    updateNews();
                    break;
                case HANDLER_LOADING_NEXT_NEWS:
                    updateNews();
                    break;
            }
        }
    };

    private NewsPagerFragment(String code) {
        this.code = code;
    }

    public static NewsPagerFragment NEW(String code) {
        return new NewsPagerFragment(code);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Lang.isEmpty(v)) {
            v = inflater.inflate(R.layout.wg_pager_content, container, false);
            loadView = v.findViewById(R.id.loading);
            newsItems = (RecyclerView) v.findViewById(R.id.news_items);
            error = v.findViewById(R.id.error);
            swipeLy = (SwipeRefreshLayout) v.findViewById(R.id.swipe_ly);
            floatButton = (FloatingActionButton) v.findViewById(R.id.float_button);
            newsItems.addItemDecoration(new DividerItemDecoration(newsItems.getContext(), DividerItemDecoration.VERTICAL_LIST));
            newsItems.setLayoutManager(new LinearLayoutManager(newsItems.getContext(), LinearLayoutManager.VERTICAL, false));
            newsItems.setItemAnimator(new DefaultItemAnimator());
            newsItems.setOnTouchListener(new View.OnTouchListener() {
                float y, dy;
                boolean top = true;
                int boundVal = 18;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        dy = event.getY();
                    } else {
                        y = event.getY();
                        if (dy - y > boundVal && top) { // 向下滑动
                            floatButton.hide();
                            top = false;
                        } else if (y - dy > boundVal && !top) { // 向上滑动
                            floatButton.show();
                            top = true;
                        }
                    }

                    return false;
                }
            });
            swipeLy.setRefreshing(false);
            floatButton.setImageDrawable(getResources().getDrawable(R.mipmap.ic_publish_white_24dp));
            floatButton.setOnClickListener(this);
            Lang.handMsg(handler, HANDLER_INIT_A_REFRESH_NEWS, null);
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * 更新新闻列表
     */
    private void updateNews() {
        if (nowPage == 1) {
            _hideAll();
            loadView.setVisibility(View.VISIBLE);
        }
        try {
            HttpHandler.me().getAsy(String.format(domain, code, nowPage), new HttpHandler.OnAsyResultCallBack<String>(String.class) {
                @Override
                public void onResponse(final String rs) {
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            if (nowPage == 1) {
                                _hideAll();
                                newsItems.setVisibility(View.VISIBLE);
                                try {
                                    if (Lang.isEmpty(newsItemAdapter)) {
                                        newsItems.setAdapter(newsItemAdapter = new NewsItemAdapter(NewsPagerFragment.this, _toNewsItems(rs)));
                                        // 初始化下拉
                                        swipeLy.setColorSchemeColors(
                                                swipeLy.getResources().getColor(R.color.google_blue),
                                                swipeLy.getResources().getColor(R.color.google_red),
                                                swipeLy.getResources().getColor(R.color.google_yellow),
                                                swipeLy.getResources().getColor(R.color.google_green));
                                        swipeLy.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                            @Override
                                            public void onRefresh() {
                                                // 刷新
                                                Lang.handMsg(handler, HANDLER_INIT_A_REFRESH_NEWS, null);
                                            }
                                        });
                                    } else {
                                        newsItemAdapter.updateItems(_toNewsItems(rs));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    newsItemAdapter.addItems(_toNewsItems(rs));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            swipeLy.setRefreshing(false);
                            nowPage++;
                        }
                    });
                }

                @Override
                public void onFailure(Request request, IOException e) {
                    Logger.v("http -- 错误 code" + request.toString() + "  " + e + "" + nowPage);
                    if (nowPage == 1 || !UserSetting.isNetworkAvailable(v.getContext())) {
                        _hideAll();
                        error.setVisibility(View.VISIBLE);
                        error.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Lang.handMsg(handler, HANDLER_INIT_A_REFRESH_NEWS, null);
                            }
                        });
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加新新闻
     *
     * @param newNewsJson
     */
    public void addNews(String newNewsJson) {
        try {
            newsItemAdapter.addItems(_toNewsItems(newNewsJson));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void _hideAll() {
        loadView.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        newsItems.setVisibility(View.GONE);
    }

    /**
     * 转 新闻json为List
     *
     * @param respStr
     * @return
     * @throws JSONException
     */
    private List _toNewsItems(String respStr) throws JSONException {
        JSONArray tj = new JSONArray(respStr);
        // -- 解析顶部
//        JSONObject topEntry = tj.getJSONObject(1);
//        JSONArray tops = topEntry.getJSONArray("item");
        // -- 判断情况 暂不解析


        // -- 解析 2种情况
        JSONObject items_0 = tj.getJSONObject(0);
        // -- 判断情况
        JSONArray items = items_0.getJSONArray("item");
        List<Object> data = new ArrayList<Object>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String type = (String) item.get("type");
            if ("doc".equals(type)) {
                data.add(SingletonUtil.gson().fromJson(item.toString(), DocBean.class));
            } else if ("slide".equals(type)) {
                data.add(SingletonUtil.gson().fromJson(item.toString(), SlidesBean.class));
            }
        }
        return data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_button:
                newsItems.smoothScrollToPosition(0);
                break;
        }
    }
}
