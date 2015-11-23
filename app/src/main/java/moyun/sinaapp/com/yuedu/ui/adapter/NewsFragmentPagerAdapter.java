package moyun.sinaapp.com.yuedu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import moyun.sinaapp.com.yuedu.ui.fragment.NewsPagerFragment;

/**
 * Created by Moy on 九月18  018.
 */
public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    private static String[] tabName = {
//            "推荐", "地方新闻",
            "头条", "娱乐", "财经",
            "科技", "历史", "军事",
            "体育", "汽车", "时尚",
    };
    private static String[] code = {
//            "url_1", "url_2",
            "SYLB10,SYDT10,SYRECOMMEND", "YL53,FOCUSYL53", "CJ33,FOCUSCJ33",
            "KJ123,FOCUSKJ123", "LS153,FOCUSLS153", "JS83,FOCUSJS83",
            "TY43,FOCUSTY43,TYLIVE", "QC45,FOCUSQC45", "SS78,FOCUSSS78"
    };

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsPagerFragment.NEW(code[position]);
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