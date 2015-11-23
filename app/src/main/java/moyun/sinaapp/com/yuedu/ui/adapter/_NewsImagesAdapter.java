package moyun.sinaapp.com.yuedu.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Moy on 九月26  026.
 */
public class _NewsImagesAdapter extends PagerAdapter {

    List<View> views;
    String[] urls;
    String[] descriptions;

    public _NewsImagesAdapter(List<View> views, String[] urls) {
        this.views = views;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View target = views.get(position);
        Glide.with(container.getContext()).load(urls[position]).into((ImageView) target);
        container.addView(target);
        return views.get(position);
    }
}
