package moyun.sinaapp.com.yuedu.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.orhanobut.logger.Logger;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.ui.adapter.MusicItemAdapter;
import moyun.sinaapp.com.yuedu.ui.util.DividerItemDecoration;
import moyun.sinaapp.com.yuedu.utils.Lang;

import java.util.List;

/**
 * Created by Moy on 十月12  012.
 * V && 列表
 */
@SuppressLint("ValidFragment")
public class MusicItemPagerFragment extends AbsMusicPagerFragment {

    private String type;
    private List data;
    private LayoutInflater inflater;
    View v;


    public MusicItemPagerFragment(String type, List data) {
        this.type = type;
        this.data = data;
        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case HANDLER_LOADING_NEXT_NEWS:
                        Logger.v("--> Next");
                        break;
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        var();
        if (Lang.isEmpty(v)) {
            switch (type) {
                case "歌曲":
                    _generateMusicInfo();
                    break;
                case "列表":
                    _generateSongList();
                    break;
            }
        }
        return v;
    }

    private void var() {
        inflater = LayoutInflater.from(this.getContext());
    }

    public static MusicItemPagerFragment NEW(String type, List data) {
        return new MusicItemPagerFragment(type, data);
    }


    /**
     * 第一页
     * 生成歌曲的信息
     */
    private void _generateMusicInfo() {
        v = inflater.inflate(R.layout.wg_music_info, null);

    }

    /**
     * 第二页
     * 生成歌曲的列表
     */
    private void _generateSongList() {
        v = inflater.inflate(R.layout.wg_song_list, null);
        RecyclerView songList = (RecyclerView) v.findViewById(R.id.song_list);
        songList.setAdapter(new MusicItemAdapter(MusicItemPagerFragment.this, data, 2));
        songList.addItemDecoration(new DividerItemDecoration(songList.getContext(), DividerItemDecoration.VERTICAL_LIST));
        songList.setLayoutManager(new LinearLayoutManager(songList.getContext(), LinearLayoutManager.VERTICAL, false));
        songList.setItemAnimator(new DefaultItemAnimator());
    }

}
