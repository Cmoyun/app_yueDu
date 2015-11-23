package moyun.sinaapp.com.yuedu.ui.util;

import android.os.Bundle;
import moyun.sinaapp.com.yuedu.entity.MusicBean;

/**
 * Created by Moy on 十月10  010.
 */
public class Util {

    public static final Bundle tt2bundle(MusicBean music) {
        String singerName = music.getSinger_name();
        String songName = music.getSong_name();
        String albumName = music.getAlbum_name();
        String url = music.getAudition_list().get(0).getUrl();
        String size = music.getAudition_list().get(0).getSize();
        String duration = music.getAudition_list().get(0).getDuration();

        Bundle bundle = new Bundle();
        bundle.putString("singerName", singerName);
        bundle.putString("songName", songName);
        bundle.putString("albumName", albumName);
        bundle.putString("url", url);
        bundle.putString("size", size);
        bundle.putString("duration", duration);
        return bundle;
    }

    public static final String timeFormat(int t) {

        int time = t / 1000;

        long min = time / 60;
        long s = time % 60;
        String minStr = min < 10 ? "0" + min : min + "";
        String sStr = s < 10 ? "0" + s : s + "";
        return minStr + ":" + sStr;
    }
}
