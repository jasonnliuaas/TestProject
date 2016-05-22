package ui.acy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.hm.testproject.R;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.android.agoo.client.BaseRegistrar;

import java.util.List;

import adapter.ViewPagerAdapter;
import base.BaseActiviy;
import base.BaseListActivity;
import base.BaseListFragment;
import base.RecyclerBaseAapter;
import bean.News;
import bean.NewsList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import fragment.GameNewsListFragment;
import fragment.NewsFragment;
import http.TestApi;

public class NewsActivity extends BaseActiviy {

    private PushAgent mPushAgent;
    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.pager)
    ViewPager pager;
    ViewPagerAdapter fa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.inject(this);
        initWidget();
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);
        mPushAgent.enable(new IUmengRegisterCallback() {
            @Override
            public void onRegistered(String s) {
                Log.d("TAG",s);

            }
        });
        boolean registered = mPushAgent.isRegistered();
        boolean enabled = mPushAgent.isEnabled();
        if (registered && enabled) {

        }

    }
    private Bundle getBundle(int newType) {
        Bundle bundle = new Bundle();
        bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, newType);
        return bundle;
    }
    @Override
    public void initWidget() {
        //this.setSupportActionBar(toolbar);
        fa = new ViewPagerAdapter(getSupportFragmentManager(),this);
        fa.addTab("资讯", "news", NewsFragment.class,
                getBundle(NewsList.CATALOG_ALL));
        fa.addTab("新闻", "zm_news", GameNewsListFragment.class,
                getBundle(12));/*
        fa.addTab("page霸", "news", NewsFragment.class,
                getBundle(NewsList.CATALOG_ALL));
        fa.addTab("page业", "news_week", NewsFragment.class,
                getBundle(NewsList.CATALOG_ALL));*/
        pager.setAdapter(fa);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(pager);

    }


}
