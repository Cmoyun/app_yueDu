package moyun.sinaapp.com.yuedu.ui.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.MusicBean;
import moyun.sinaapp.com.yuedu.ui.adapter.MusicItemFragmentPagerAdapter;
import moyun.sinaapp.com.yuedu.ui.util.Util;
import moyun.sinaapp.com.yuedu.widgets.service.MusicService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moy on 十月10  010.
 * <p/>
 * 天气图标  || 欢迎界面 || 反馈信息 || 应用更新 || 团队&作品
 */
public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton play;
    ImageButton previous;
    ImageButton next;
    ViewPager musicPager;
    SeekBar seekbar;
    TextView nowTime;
    TextView totalTime;
    Toolbar toolbar;

    Bundle bundle;
    int pos;
    int currentTime; // 当前播放位置
    int duration; // 总长度
    List data;
    boolean isPause = false; // 状态： 暂停中
    boolean isPlaying = false; // 状态：播放中
    PlayerReceiver playerReceiver;

    public static final String UPDATE_ACTION = "com.sinaapp.moyun.UPDATE_ACTION"; // 更新动作
    public static final String MUSIC_AGAIN = "com.sinaapp.moyun.MUSIC_AGAIN";     //控制动作
    public static final String MUSIC_CURRENT = "com.sinaapp.moyun.MUSIC_CURRENT";      //当前音乐改变动作
    public static final String MUSIC_DURATION = "com.sinaapp.moyun.MUSIC_DURATION";    //音乐时长改变动作
    public static final String INIT_INFO = "com.sinaapp.moyun.INIT_INFO";    //音乐时长改变动作

    public static final String MUSIC_ACTION_STATE = "com.sinaapp.moyun.MUSIC_ACTION_STATE";    // 暂停/播放

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        findView();
        initIntent();
        var();
        initView();


        if (pos >= 0) {
            Intent intent = new Intent(this, MusicService.class);
            intent.putExtra("lock", false);
            intent.putExtra("MSG", "");
            startService(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerReceiver = new PlayerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_ACTION);
        filter.addAction(MUSIC_AGAIN);
        filter.addAction(MUSIC_CURRENT);
        filter.addAction(MUSIC_DURATION);
        filter.addAction(MUSIC_ACTION_STATE);
        filter.addAction(INIT_INFO);
        registerReceiver(playerReceiver, filter);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendBroadcast(new Intent(MusicActivity.INIT_INFO));
            }
        }, 1000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(playerReceiver);
    }

    private void initView() {
        musicPager.setAdapter(new MusicItemFragmentPagerAdapter(getSupportFragmentManager(), data));
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        initToolbar();
        seekbar.setMax(100);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    audioTrackChange(progress); //用户控制进度的改变
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void var() {
        pos = bundle.getInt("pos");
        data = bundle.getParcelableArrayList("data");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(MusicService.CUSTOM_VIEW_ID);
    }

    private void initIntent() {
        bundle = getIntent().getExtras();
    }

    private void findView() {
        nowTime = (TextView) findViewById(R.id.now_time);
        totalTime = (TextView) findViewById(R.id.total_time);
        seekbar = (SeekBar) findViewById(R.id.music_seekbar);
        previous = (ImageButton) findViewById(R.id.ctl_previous);
        musicPager = (ViewPager) findViewById(R.id.music_viewpager);
        play = (ImageButton) findViewById(R.id.ctl_play);
        next = (ImageButton) findViewById(R.id.ctl_next);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ctl_play:
                Intent intent = new Intent(this, MusicService.class);
                if (isPause) { // 暂停状态
                    _changePlayerState(true);
                    intent.putExtra("MSG", MusicService.INTENT_MCL_CONTINUE);
                    startService(intent);
                } else if (isPlaying) {// 播放状态
                    _changePlayerState(false);
                    intent.putExtra("MSG", MusicService.INTENT_MCL_PAUSE);
                    startService(intent);
                } else {
                    play();
                }
                break;
            case R.id.ctl_next:
                next();
                break;
            case R.id.ctl_previous:
                previous();
                break;
        }
    }


    /**
     * 用来接收从service传回来的广播的内部类
     * 更新UI
     *
     * @author wwj
     */

    public class PlayerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case UPDATE_ACTION: // 更新动作
                    //获取Intent中的current消息，current代表当前正在播放的歌曲
                    pos = intent.getIntExtra("current", -1);
                    if (pos >= 0) {
                        MusicBean m = ((MusicBean) data.get(pos));
                        TextView songName = (TextView) findViewById(R.id.song_name);
                        TextView singerName = (TextView) findViewById(R.id.singer_name);
                        TextView albumName = (TextView) findViewById(R.id.album_name);
                        if (m != null) {
                            songName.setText(m.getSong_name() + "");
                            singerName.setText(m.getSinger_name() + "");
                            albumName.setText("《"+m.getAlbum_name() + "》");
                        }
                    }
                    break;
                case MUSIC_CURRENT: // 当前播放时间
                    currentTime = intent.getIntExtra("currentTime", -1);
                    nowTime.setText(Util.timeFormat(currentTime) + "");
                    seekbar.setProgress(currentTime);
                    break;
                case MUSIC_DURATION: // 总时间
                    duration = intent.getIntExtra("duration", -1);
                    seekbar.setMax(duration);
                    totalTime.setText(Util.timeFormat(duration) + "");
                    break;
                case MUSIC_AGAIN: // 刷新状态
                    duration = intent.getIntExtra("duration", -1);
                    pos = intent.getIntExtra("current", -1);
                    break;
                case MUSIC_ACTION_STATE: // 按钮状态
                    _changePlayerState(intent.getBooleanExtra("state", false));
                    break;
                case INIT_INFO: // 更新初始值
                    MusicBean m = ((MusicBean) data.get(pos));
                    TextView songName = (TextView) findViewById(R.id.song_name);
                    TextView singerName = (TextView) findViewById(R.id.singer_name);
                    TextView albumName = (TextView) findViewById(R.id.album_name);
                    if (m != null) {
                        songName.setText(m.getSong_name() + "");
                        singerName.setText(m.getSinger_name() + "");
                        albumName.setText("《" + m.getAlbum_name() + "》");
                        totalTime.setText(m.getAudition_list().get(0).getDuration()+"");
                    }
                    break;
            }
        }
    }

    /**
     * 播放音乐
     */
    public void play() {
        _changePlayerState(true);
        MusicBean m = (MusicBean) data.get(pos);
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra("listPosition", pos);
        intent.putExtra("url", m.getAudition_list().get(0).getUrl());
        intent.putParcelableArrayListExtra("data", (ArrayList<MusicBean>) data);
        intent.putExtra("MSG", MusicService.INTENT_MCL_PLAY);
        startService(intent);
    }

    /**
     * 下一首歌曲
     */
    public void next() {
        Intent intent = new Intent(this, MusicService.class);
        _changePlayerState(true);
        pos = ++pos % data.size();
        MusicBean m = (MusicBean) data.get(pos);
        intent.putExtra("listPosition", pos);
        intent.putExtra("url", m.getAudition_list().get(0).getUrl());
        intent.putExtra("MSG", MusicService.INTENT_MCL_NEXT);
        startService(intent);
    }

    /**
     * 上一首歌曲
     */
    public void previous() {
        _changePlayerState(true);
        pos = --pos % data.size() < 0 ? data.size() + pos : pos;
        MusicBean m = (MusicBean) data.get(pos);
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra("listPosition", pos);
        intent.putExtra("url", m.getAudition_list().get(0).getUrl());
        intent.putExtra("MSG", MusicService.INTENT_MCL_PREVIOUS);
        startService(intent);
    }

    public void audioTrackChange(int progress) {
        Intent intent = new Intent(this, MusicService.class);
        MusicBean m = (MusicBean) data.get(pos);
        intent.putExtra("url", m.getAudition_list().get(0).getUrl());
        intent.putExtra("listPosition", pos);
        if(isPause) {
            intent.putExtra("MSG", MusicService.INTENT_MCL_PAUSE);
        }
        else {
            intent.putExtra("MSG", MusicService.INTENT_MCL_PROGRESS_CHANGE);
        }
        intent.putExtra("progress", progress);
        startService(intent);
    }

    private void _changePlayerState(boolean state) {
        Logger.v("--> change bt " + state);
        if (state) { // 播放状态
            play.setImageResource(R.drawable.ic_stop_black_24dp);
            isPlaying = true;
            isPause = false;
        } else { // 暂停状态
            play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            isPlaying = false;
            isPause = true;
        }
    }

    /**
     * 初始化 Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("歌曲");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isPlay", isPlaying);
        outState.putInt("pos", pos);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        isPlaying = savedInstanceState.getBoolean("isPlay", false);
        pos = savedInstanceState.getInt("pos", 0);
    }
}
