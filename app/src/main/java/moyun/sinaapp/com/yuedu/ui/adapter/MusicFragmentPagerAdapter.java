package moyun.sinaapp.com.yuedu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import moyun.sinaapp.com.yuedu.ui.fragment.MusicPagerFragment;

/**
 * Created by Moy on 九月29  029.
 */
public class MusicFragmentPagerAdapter extends FragmentPagerAdapter {

    private static String[] tabName = {
            "热门", "经典", "闽南","粤语","周杰伦", "Rihanna","P!nk","Beyoncé","Chris Medina"
    };

    public MusicFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // 等待编辑
        return MusicPagerFragment.NEW(tabName[position]);
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
