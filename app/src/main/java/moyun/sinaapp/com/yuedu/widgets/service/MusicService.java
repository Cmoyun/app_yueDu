package moyun.sinaapp.com.yuedu.widgets.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import com.orhanobut.logger.Logger;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.MusicBean;
import moyun.sinaapp.com.yuedu.ui.activity.MusicActivity;

import java.util.ArrayList;

/**
 * Created by Moy on 十月10  010.
 * <p/>
 * player 播放指定歌曲
 * clickplay 暂停/播放
 * next 下一首
 * prev 上一首
 * changed 滑动
 */
public class MusicService extends Service {

    private String path; // 播放路径
    private MediaPlayer musicPlayer;
    private int duration; // 总长度
    private int current = 0; // 当前播放的位置
    private int currentTime;        //当前播放进度
    private boolean isPause = false;

    private String msg;
    private ArrayList<MusicBean> data;


    public static final String ACTION = "com.sinaapp.moyun.MusicService";

    public static final String INTENT_MCL_STOP = "INTENT_MCL_STOP"; // 终止
    public static final String INTENT_MCL_PAUSE = "INTENT_MCL_PAUSE"; // 暂停
    public static final String INTENT_MCL_CONTINUE = "INTENT_MCL_CONTINUE"; // 继续
    public static final String INTENT_MCL_PLAY = "INTENT_MCL_PLAY"; // 播放
    public static final String INTENT_MCL_PREVIOUS = "INTENT_MCL_PREVIOUS"; // 上一首
    public static final String INTENT_MCL_NEXT = "INTENT_MCL_NEXT"; // 下一首
    public static final String INTENT_MCL_PROGRESS_CHANGE = "INTENT_MCL_PROGRESS_CHANGE"; //进度更新
    public static final String INTENT_MCL_PLAYING = "INTENT_MCL_PLAYING"; //进度更新

    public static final String INTENT_MCL_AGAIN = "INTENT_MCL_AGAIN"; //刷新位置

    public static final String NOTIFICATION_STATE_PAUSE = "NOTIFICATION_STATE_PAUSE"; //进度更新
    public static final String NOTIFICATION_STATE_PLAY = "NOTIFICATION_STATE_PLAY"; //进度更新

    public static final String NOTIFY_MUSIC_TOGGLE = "com.sinaapp.moyun.NOTIFY_MUSIC_TOGGLE"; // 切换

    public static final int CUSTOM_VIEW_ID = 2;
    NotificationManager notificationManager;
    Notification notification;
    RemoteViews contentView;

    private boolean lock = true;

    /**
     * handler用来接收消息，来发送广播更新播放时间
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                if (musicPlayer != null && lock) {
                    currentTime = musicPlayer.getCurrentPosition(); // 获取当前音乐播放的位置
                    Intent intent = new Intent();
                    intent.setAction(MusicActivity.MUSIC_CURRENT);
                    intent.putExtra("currentTime", currentTime);
                    sendBroadcast(intent); // 给PlayerActivity发送广播
                    handler.sendEmptyMessageDelayed(1, 1000);
                } else {

                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        musicPlayer = new MediaPlayer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return super.onStartCommand(intent, flags, startId);
        lock = intent.getBooleanExtra("lock", true);
        switch (msg = intent.getStringExtra("MSG")) {
            case INTENT_MCL_PLAY: //直接播放音乐
                createNotification();
                path = intent.getStringExtra("url");        //歌曲路径
                current = intent.getIntExtra("listPosition", -1);
                data = intent.getParcelableArrayListExtra("data");
                play(0);
                break;
            case INTENT_MCL_PAUSE: //暂停
                pause();
                break;
            case INTENT_MCL_STOP: //停止
                stop();
                break;
            case INTENT_MCL_CONTINUE: //继续播放
                resume();
                break;
            case INTENT_MCL_PREVIOUS: //上一首
                path = intent.getStringExtra("url");        //歌曲路径
                current = intent.getIntExtra("listPosition", -1);
                previous();
                break;
            case INTENT_MCL_NEXT:    //下一首
                path = intent.getStringExtra("url");        //歌曲路径
                current = intent.getIntExtra("listPosition", -1);
                next();
                break;
            case INTENT_MCL_PROGRESS_CHANGE:    //进度更新
                Logger.v("PLAYER S -- INTENT_MCL_PROGRESS_CHANGE");
                currentTime = intent.getIntExtra("progress", -1);
                play(currentTime);
                break;
            case INTENT_MCL_PLAYING:    //播放中
                handler.sendEmptyMessage(1);
                break;
            case INTENT_MCL_AGAIN:    // 刷新信息
                Intent sendIntent = new Intent(MusicActivity.MUSIC_AGAIN);
                sendIntent.putExtra("current", current);
                sendIntent.putExtra("duration", duration);
                // 发送广播，将被Activity组件中的BroadcastReceiver接收到
                sendBroadcast(sendIntent);
                break;
            case NOTIFY_MUSIC_TOGGLE:
                if (isPause) {
                    resume();
                } else {
                    pause();
                }
                break;
            default:
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 播放音乐
     *
     * @param currentTime
     */
    private void play(int currentTime) {
        try {
            musicPlayer.reset();// 把各项参数恢复到初始状态
            musicPlayer.setDataSource(path);
            musicPlayer.prepare(); // 进行缓冲
            _changeNotification(NOTIFICATION_STATE_PLAY, data.get(current));
            musicPlayer.setOnPreparedListener(new PreparedListener(currentTime));// 注册一个监听器
            sendChangeBtState(true);
            Intent sendIntent = new Intent(MusicActivity.UPDATE_ACTION);
            sendIntent.putExtra("current", current);
            // 发送广播，将被Activity组件中的BroadcastReceiver接收到
            sendBroadcast(sendIntent);
            handler.sendEmptyMessage(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停音乐
     */
    private void pause() {
        if (musicPlayer != null && musicPlayer.isPlaying()) {
            musicPlayer.pause();
            _changeNotification(NOTIFICATION_STATE_PAUSE, data.get(current));
            sendChangeBtState(false);
            isPause = true;
        }
    }

    private void resume() {
        if (isPause) {
            _changeNotification(NOTIFICATION_STATE_PLAY, data.get(current));
            sendChangeBtState(true);
            musicPlayer.start();
            isPause = false;
        }
    }

    /**
     * 上一首
     */
    private void previous() {
        Intent sendIntent = new Intent(MusicActivity.UPDATE_ACTION);
        sendIntent.putExtra("current", current);
        // 发送广播，将被Activity组件中的BroadcastReceiver接收到
        sendBroadcast(sendIntent);
        play(0);
    }

    /**
     * 下一首
     */
    private void next() {
        Intent sendIntent = new Intent(MusicActivity.UPDATE_ACTION);
        sendIntent.putExtra("current", current);
        // 发送广播，将被Activity组件中的BroadcastReceiver接收到
        sendBroadcast(sendIntent);
        play(0);
    }

    /**
     * 停止音乐
     */
    private void stop() {
        if (musicPlayer != null) {
            musicPlayer.stop();
            sendChangeBtState(false);
            try {
                musicPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.release();
            musicPlayer = null;
        }

    }

    /**
     * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
     */
    private final class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int currentTime;

        public PreparedListener(int currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            musicPlayer.start(); // 开始播放
            if (currentTime > 0) { // 如果音乐不是从头播放
                musicPlayer.seekTo(currentTime);
            }
            Intent intent = new Intent();
            intent.setAction(MusicActivity.MUSIC_DURATION);
            duration = musicPlayer.getDuration();
            intent.putExtra("duration", duration);  //通过Intent来传递歌曲的总长度
            sendBroadcast(intent);
        }
    }

    /**
     * 创建通知
     */
    private void createNotification() {
        // --
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification(R.mipmap.music_logo, getText(R.string.app_name), System.currentTimeMillis());
        contentView = new RemoteViews(getPackageName(), R.layout.notification_music);
        // intent
        Intent toggleIntent = new Intent(this, MusicService.class);
        toggleIntent.setAction(ACTION);
        toggleIntent.putExtra("MSG", MusicService.NOTIFY_MUSIC_TOGGLE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, toggleIntent, 0);
        contentView.setOnClickPendingIntent(R.id.ctl_music, pendingIntent);
        notification.contentView = contentView;
        notificationManager.notify(CUSTOM_VIEW_ID, notification);
    }

    /**
     * 更改通知消息状态
     * @param state
     * @param music
     */
    private void _changeNotification(String state, MusicBean music) {
        @DrawableRes
        int srcId = 0;

        switch (state) {
            case NOTIFICATION_STATE_PAUSE:
                srcId = R.drawable.ic_play_arrow_black_24dp;
                break;
            case NOTIFICATION_STATE_PLAY:
                srcId = R.drawable.ic_stop_black_24dp;
                contentView.setTextViewText(R.id.music_name, music.getSong_name());
                contentView.setTextViewText(R.id.album_name, "来自 《" + music.getAlbum_name()+"》");
                break;
        }
        contentView.setImageViewResource(R.id.ctl_music, srcId);
        notificationManager.notify(CUSTOM_VIEW_ID, notification);
    }

    private void sendChangeBtState(boolean state) { // true 为播放状态
        Intent sendIntent = new Intent(MusicActivity.MUSIC_ACTION_STATE);
        sendIntent.putExtra("state", state);
        // 发送广播，将被Activity组件中的BroadcastReceiver接收到
        sendBroadcast(sendIntent);
    }
}
