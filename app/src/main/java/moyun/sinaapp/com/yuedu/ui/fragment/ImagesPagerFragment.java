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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.okhttp.Request;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.ImageBean;
import moyun.sinaapp.com.yuedu.http.HttpHandler;
import moyun.sinaapp.com.yuedu.ui.adapter.ImageItemAdapter;
import moyun.sinaapp.com.yuedu.ui.util.DividerItemDecoration;
import moyun.sinaapp.com.yuedu.utils.Lang;
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
public class ImagesPagerFragment extends Fragment implements View.OnClickListener {


    private String domain = "http://image.baidu.com/data/imgs?col=%s&tag=%s&sort=0&pn=%d&rn=%d&p=channel&from=1";
    private static final int CUSTOM_COUNT = 2;
    private static final int CUSTOM_REQ_COUNT = 10;
    private int nowPage = 1;
    private String key;
    View v;
    // res id
    RecyclerView imgItem;
    View loadView;
    View error;
    ImageItemAdapter imageItemAdapter;
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
                    updateImages();
                    break;
                case HANDLER_LOADING_NEXT_NEWS:
                    updateImages();
                    break;
            }
        }
    };

    private ImagesPagerFragment(String key) {
        this.key = key;
    }

    public static ImagesPagerFragment NEW(String key) {
        return new ImagesPagerFragment(key);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Lang.isEmpty(v)) {
            v = inflater.inflate(R.layout.wg_pager_content, container, false);
            loadView = v.findViewById(R.id.loading);
            imgItem = (RecyclerView) v.findViewById(R.id.news_items);
            error = v.findViewById(R.id.error);
            swipeLy = (SwipeRefreshLayout) v.findViewById(R.id.swipe_ly);
            floatButton = (FloatingActionButton) v.findViewById(R.id.float_button);

            imgItem.addItemDecoration(new DividerItemDecoration(imgItem.getContext(), DividerItemDecoration.VERTICAL_LIST));
            imgItem.setLayoutManager(new StaggeredGridLayoutManager(CUSTOM_COUNT, StaggeredGridLayoutManager.VERTICAL));
            imgItem.setItemAnimator(new DefaultItemAnimator());
            imgItem.setOnTouchListener(new View.OnTouchListener() {
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
     * 更新图片列表
     */
    private void updateImages() {
        if (nowPage == 1) {
            _hideAll();
            loadView.setVisibility(View.VISIBLE);
        }
        try {
            HttpHandler.me().getAsy(_domain(key, (nowPage - 1) * CUSTOM_REQ_COUNT, CUSTOM_REQ_COUNT), new HttpHandler.OnAsyResultCallBack<String>(String.class) {
                @Override
                public void onResponse(final String rs) {
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            if (nowPage == 1) { // 第一次 或者刷新的情况
                                _hideAll();
                                imgItem.setVisibility(View.VISIBLE);
                                try {
                                    if (Lang.isEmpty(imageItemAdapter)) {
                                        imgItem.setAdapter(imageItemAdapter = new ImageItemAdapter(ImagesPagerFragment.this, _toImagesItem(rs)));
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
                                        imageItemAdapter.updateItems(_toImagesItem(rs));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    imageItemAdapter.addItems(_toImagesItem(rs));
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
     * 添加新图片
     *
     * @param newJson
     */
    public void addNews(String newJson) {
        try {
            imageItemAdapter.addItems(_toImagesItem(newJson));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void _hideAll() {
        loadView.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        imgItem.setVisibility(View.GONE);
    }

    /**
     * 转 图片json为List
     *
     * @param respStr
     * @return
     * @throws JSONException
     */
    private List _toImagesItem(String respStr) throws JSONException {
        JSONObject tj = new JSONObject(respStr);
        JSONArray data = tj.getJSONArray("imgs");
        List<Object> rt = new ArrayList<Object>();// 转存
        for (int i = 0; i < data.length(); i++) {
            JSONObject obj = data.getJSONObject(i);
            String small = obj.optString("thumbnailUrl");
            String bigger = obj.optString("imageUrl");
            if (Lang.isEmpty(small) || Lang.isEmpty(bigger)) {
                continue;
            }
            rt.add(new ImageBean(small, bigger));
        }
        return rt;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_button:
                imgItem.smoothScrollToPosition(0);
                break;
        }
    }


    // 获得地址
    private String _domain(String key, int start, int len) {
        return String.format(domain, key, "全部", start, len);
//        return String.format(domain, Uri.encode(key), Uri.encode("全部"), start, len);
    }

}
