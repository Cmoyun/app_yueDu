package moyun.sinaapp.com.yuedu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.ui.adapter._NewsImagesAdapter;
import moyun.sinaapp.com.yuedu.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moy on 九月26  026.
 * Intent imgsUrl title 描述s
 */
public class NewsImgActivity extends AppCompatActivity implements View.OnClickListener {

    int nowPager = 0;
    ImageButton goback;
    ImageButton share;
    ImageButton forwarding;
    TextView index;
    TextView allIndex;
    TextView description;
    ViewPager newsImgs;

    String title;
    String[] descriptions;
    String[] imgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_images);
        findView();
        intentBundle();
        initView();
    }

    private void intentBundle() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        imgs = intent.getStringArrayExtra("image");
        descriptions = intent.getStringArrayExtra("description");
    }

    private void initView() {
        List<View> imageViews = new ArrayList<View>();
        for (String img : imgs) {
            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(lp);
            imageViews.add(imageView);
        }
        newsImgs.setAdapter(new _NewsImagesAdapter(imageViews, imgs));
        allIndex.setText(imgs.length + "");
        newsImgs.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                nowPager = position;
                index.setText((position + 1) + "");
                description.setText(descriptions[position]);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        goback.setOnClickListener(this);
        share.setOnClickListener(this);
        forwarding.setOnClickListener(this);
    }

    private void findView() {
        goback = (ImageButton) findViewById(R.id.goback);
        share = (ImageButton) findViewById(R.id.share);
        forwarding = (ImageButton) findViewById(R.id.forwarding);
        index = (TextView) findViewById(R.id.index);
        allIndex = (TextView) findViewById(R.id.all_index);
        description = (TextView) findViewById(R.id.description);
        newsImgs = (ViewPager) findViewById(R.id.news_imgs);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback:
                finish();
                break;
            case R.id.share:
                startActivity(IntentUtil.shareAction(String.format("%s %s", title, imgs[nowPager])));
                break;
            case R.id.forwarding:
                startActivity(IntentUtil.forwardAction(imgs[nowPager]));
                break;
        }
    }
}
