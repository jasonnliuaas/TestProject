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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.hm.testproject.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.android.agoo.client.BaseRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adapter.ViewPagerAdapter;
import base.BaseActiviy;
import base.BaseListActivity;
import base.BaseListFragment;
import base.RecyclerBaseAapter;
import bean.News;
import bean.NewsList;
import bean.Title;
import bean.TitleList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
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

    TitleList titleList = null;

    List<Title> titles = new ArrayList<Title>();

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
        pager.setAdapter(fa);
        TestApi.getZmTitlesList(mHandler);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(pager);
    }

    protected AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Gson gson = new Gson();
            String data = "[\n" +
                    "id:" +3+
                    ",\n" +
                    "    {\n" +
                    "        \"id\": \"12\",\n" +
                    "\t\"specil\": \"0\"  ,\n" +
                    "        \"url\":\"\",\n" +
                    "        \"name\":\"最新\"\n" +
                    "    },\n" +
                    "    \n" +
                    "    {\n" +
                    "        \"id\": \"73\",\n" +
                    "\t\"specil\": \"0\"  ,\n" +
                    "        \"url\":\"\",\n" +
                    "        \"name\":\"赛事\"\n" +
                    "    },\n" +
                    "    \n" +
                    "    {\n" +
                    "        \"id\": \"23\",\n" +
                    "\t\"specil\": \"0\"  ,\n" +
                    "        \"url\":\" \",\n" +
                    "        \"name\":\"活动\"\n" +
                    "    },\n" +
                    "    \n" +
                    "    {\n" +
                    "        \"id\": \"11\",\n" +
                    "\t\"specil\": \"1\"   ,\n" +
                    "        \"url\":\"http://lol.qq.com/m/act/a20150319lolapp/video.htm\",\n" +
                    "        \"name\":\"视频\"\n" +
                    "    },\n" +
                    "    \n" +
                    "    {\n" +
                    "        \"id\": \"18\",\n" +
                    "\t\"specil\": \"0\"  ,\n" +
                    "        \"url\":\" \",\n" +
                    "        \"name\":\"娱乐\"\n" +
                    "    },\n" +
                    "    \n" +
                    "    {\n" +
                    "        \"id\": \"3\",\n" +
                    "\t\"specil\": \"0\"  ,\n" +
                    "        \"url\":\"\",\n" +
                    "        \"name\":\"官方\"\n" +
                    "    },\n" +
                    "    \n" +
                    "    {\n" +
                    "        \"id\": \"10\",\n" +
                    "\t\"specil\": \"0\"  ,\n" +
                    "        \"url\":\"\",\n" +
                    "        \"name\":\"攻略\"\n" +
                    "    }\n" +
                    "    \n" +
                    "]";
            data = replaceBlank(data);
            titleList =  gson.fromJson(data, TitleList.class);
            if(titleList.getList().isEmpty()){
                fa.addTab("资讯", "news", NewsFragment.class,
                        getBundle(NewsList.CATALOG_ALL));
                fa.addTab("新闻", "zm_news", GameNewsListFragment.class,
                        getBundle(12));
            }else {
                titles = titleList.getList();
                for (Title t:titles){
                    Bundle bundle = new Bundle();
                    fa.addTab(t.getName(), "zm_news"+t.getId(), GameNewsListFragment.class,
                            getBundle(t.getId()));
                }
            }
            fa.notifyDataSetChanged();

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            fa.addTab("资讯", "news", NewsFragment.class,
                    getBundle(NewsList.CATALOG_ALL));
            fa.addTab("新闻", "zm_news", GameNewsListFragment.class,
                    getBundle(12));
            fa.notifyDataSetChanged();
        }

    };

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }



}
