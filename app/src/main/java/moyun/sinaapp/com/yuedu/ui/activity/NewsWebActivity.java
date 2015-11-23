package moyun.sinaapp.com.yuedu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import com.orhanobut.logger.Logger;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.utils.IntentUtil;

/**
 * Created by Moy on 九月25  025.
 * Intent - [标题、链接]
 */
public class NewsWebActivity extends AppCompatActivity implements View.OnClickListener {

    WebView newsWeb;
    Toolbar toolbar;
    String title;
    String url;
    ImageButton forwarding;
    ImageButton refresh;
    ImageButton share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);
        findView(); // 寻找id
        handlerIntent(); // 意图处理
        initView(); // 初始化view
    }

    private void handlerIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        title = bundle.getString("title");
        url = bundle.getString("url");
        Logger.v("url "+ url);
    }

    private void findView() {
        newsWeb = (WebView) findViewById(R.id.news_web);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        forwarding = (ImageButton) findViewById(R.id.forwarding);
        refresh = (ImageButton) findViewById(R.id.refresh);
        share = (ImageButton) findViewById(R.id.share);
    }

    private void initView() {
        initToolbar();
        WebSettings set = newsWeb.getSettings();
        set.setJavaScriptEnabled(true);
        newsWeb.setWebChromeClient(new WebChromeClient() {
        });
        newsWeb.loadUrl(url);

        forwarding.setOnClickListener(this);
        refresh.setOnClickListener(this);
        share.setOnClickListener(this);
    }


    /**
     * 初始化 Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forwarding:
                startActivity(IntentUtil.forwardAction(url));
                break;
            case R.id.share:
                startActivity(IntentUtil.shareAction(String.format("%s %s", title, url)));
                break;
            case R.id.refresh:
                newsWeb.reload();
                break;
        }
    }
}
