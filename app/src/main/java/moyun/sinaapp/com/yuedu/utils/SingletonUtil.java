package moyun.sinaapp.com.yuedu.utils;

import com.google.gson.Gson;

/**
 * Created by Moy on 九月21  021.
 */
public class SingletonUtil {
    private static Gson gson;

    public static Gson gson() {
        if (Lang.isEmpty(gson)) {
            gson = new Gson();
        }
        return gson;
    }
}
