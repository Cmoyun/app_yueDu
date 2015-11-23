package moyun.sinaapp.com.yuedu.ui.fragment;

import android.os.Handler;
import android.support.v4.app.Fragment;

/**
 * Created by Moy on 十月12  012.
 */
public abstract class AbsMusicPagerFragment extends Fragment {
    public Handler handler;

    public static final int HANDLER_INIT_A_REFRESH_NEWS = 1;  // 初始化 & 刷新
    public static final int HANDLER_LOADING_NEXT_NEWS = 2; // 更新
}
