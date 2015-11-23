package moyun.sinaapp.com.yuedu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import moyun.sinaapp.com.yuedu.ui.fragment.MusicItemPagerFragment;

import java.util.List;

/**
 * Created by Moy on 九月29  029.
 * 歌曲 和 列表
 */
public class MusicItemFragmentPagerAdapter extends FragmentPagerAdapter {

    private static String[] tabName = {
            "歌曲", "列表"
    };

    private List data;

    public MusicItemFragmentPagerAdapter(FragmentManager fm, List data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        // 等待编辑
        return MusicItemPagerFragment.NEW(tabName[position], data);
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
