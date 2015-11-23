package moyun.sinaapp.com.yuedu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import moyun.sinaapp.com.yuedu.ui.fragment.ImagesPagerFragment;

/**
 * Created by Moy on 九月29  029.
 */
public class ImagesFragmentPagerAdapter extends FragmentPagerAdapter {

    private static String[] tabName = {
            "壁纸", "美女", "动漫",
            "明星", "汽车", "摄影", "美食"
    };

    public ImagesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // 等待编辑
        return ImagesPagerFragment.NEW(tabName[position]);
    }

    @Override
    public int getCount() {
        return null != tabName ? tabName.length : 0;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return null != tabName ? tabName[position] : null;
    }
}
