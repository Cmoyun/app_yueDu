package moyun.sinaapp.com.yuedu.utils;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Moy on 九月28  028.
 */
public class IntentUtil {

    /**
     * 用浏览器打开
     * @param url 链接
     * @return
     */
    public static Intent forwardAction(String url) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setAction("android.intent.action.VIEW");
        i.setData(Uri.parse(url));
        return i;
    }

    /**
     * 分享
     * @param text 文本
     * @return
     */
    public static Intent shareAction(String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, text);
        return i;
    }
}
