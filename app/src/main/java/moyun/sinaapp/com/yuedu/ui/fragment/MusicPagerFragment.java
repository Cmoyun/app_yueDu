package moyun.sinaapp.com.yuedu.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.MusicBean;
import moyun.sinaapp.com.yuedu.http.HttpHandler;
import moyun.sinaapp.com.yuedu.ui.adapter.MusicItemAdapter;
import moyun.sinaapp.com.yuedu.ui.util.DividerItemDecoration;
import moyun.sinaapp.com.yuedu.utils.Lang;
import moyun.sinaapp.com.yuedu.utils.SingletonUtil;
import moyun.sinaapp.com.yuedu.utils.UserSetting;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Moy on 九月18  018.
 */
@SuppressLint("ValidFragment")
public class MusicPagerFragment extends AbsMusicPagerFragment implements View.OnClickListener {


    private String domain = "http://so.ard.iyyin.com/s/song_with_out?q=%s&page=%d&size=%d";
    private static final int CUSTOM_REQ_COUNT = 10;
    private int nowPage = 1;
    private String key;
    View v;
    // res id
    RecyclerView musicItem;
    View loadView;
    View error;
    MusicItemAdapter musicItemAdapter;
    SwipeRefreshLayout swipeLy;
    FloatingActionButton floatButton;

    public MusicPagerFragment() {

    }

    public MusicPagerFragment(String key) {
        this.key = key;
        handler = new Handler(Looper.myLooper()) {
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
    }


    public static MusicPagerFragment NEW(String key) {
        return new MusicPagerFragment(key);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Lang.isEmpty(v)) {
            v = inflater.inflate(R.layout.wg_pager_content, container, false);
            loadView = v.findViewById(R.id.loading);
            musicItem = (RecyclerView) v.findViewById(R.id.news_items);
            error = v.findViewById(R.id.error);
            swipeLy = (SwipeRefreshLayout) v.findViewById(R.id.swipe_ly);
            floatButton = (FloatingActionButton) v.findViewById(R.id.float_button);

            musicItem.addItemDecoration(new DividerItemDecoration(musicItem.getContext(), DividerItemDecoration.VERTICAL_LIST));
            musicItem.setLayoutManager(new LinearLayoutManager(musicItem.getContext(), LinearLayoutManager.VERTICAL, false));
            musicItem.setItemAnimator(new DefaultItemAnimator());
            musicItem.setOnTouchListener(new View.OnTouchListener() {
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
            if (loadView != null) {
                loadView.setVisibility(View.VISIBLE);
            }
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
                                musicItem.setVisibility(View.VISIBLE);
                                try {
                                    if (Lang.isEmpty(musicItemAdapter)) {
                                        musicItem.setAdapter(musicItemAdapter = new MusicItemAdapter(MusicPagerFragment.this, _toMusicItem(rs)));
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
                                        musicItemAdapter.updateItems(_toMusicItem(rs));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    musicItemAdapter.addItems(_toMusicItem(rs));
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
            musicItemAdapter.addItems(_toMusicItem(newJson));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void _hideAll() {
        if (loadView != null && error != null && musicItem != null) {
            loadView.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            musicItem.setVisibility(View.GONE);
        }
    }

    /**
     * 转 图片json为List
     *
     * @param respStr
     * @return
     * @throws JSONException
     */
    private List _toMusicItem(String respStr) throws JSONException {
        JSONObject tj = new JSONObject(respStr);
        JSONArray data = tj.getJSONArray("data");
        List<Object> rt = SingletonUtil.gson().fromJson(data.toString(), new TypeToken<List<MusicBean>>() {
        }.getType());// 转存
        return rt;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_button:
                musicItem.smoothScrollToPosition(0);
                break;
        }
    }


    // 获得地址
    private String _domain(String key, int start, int len) {
        return String.format(domain, key, start, len);
//        return String.format(domain, Uri.encode(key), Uri.encode("全部"), start, len);
    }

}
