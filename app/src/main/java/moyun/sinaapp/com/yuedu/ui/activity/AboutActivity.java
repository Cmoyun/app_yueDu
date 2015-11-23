package moyun.sinaapp.com.yuedu.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.AboutItemBean;
import moyun.sinaapp.com.yuedu.ui.adapter.AboutItemAdapter;
import moyun.sinaapp.com.yuedu.ui.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moy on 十月07  007.
 * 关于我们
 * =======
 * 当前版本
 * 更新应用
 * logo
 * 团队介绍
 * 项目介绍
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;

    List<AboutItemBean> nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findView();
        initData();
        initView();
    }

    private void initData() {
        nav = new ArrayList();
        nav.add(new AboutItemBean("当前版本", "1.0.3 Beta", 0));
        nav.add(new AboutItemBean("更新应用", "最新版本", 1));
        nav.add(new AboutItemBean("反馈信息", "快来反馈吧..", 3));

    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv.setAdapter(new AboutItemAdapter(this, nav));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        collapsingToolbar.setTitle("About");
        collapsingToolbar.setExpandedTitleColor(Color.BLACK);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findView() {
        rv = (RecyclerView) findViewById(R.id.rv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
