package ui.acy;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hm.testproject.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;
import base.BaseActiviy;
import bean.GameNews;
import bean.GameNewsList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import http.TestApi;

public class TestActivity extends BaseActiviy {

    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.indicator)
    CirclePageIndicator indicator;
    GameNewsList gameNewsList;
    List<ImageView> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_top);
        ButterKnife.inject(this);
        initWidget();

    }

    @Override
    public void initWidget() {
        viewpager.setAdapter(adapter);
        indicator.setViewPager(viewpager);
        TestApi.getZmNewsList(TestApi.getPages(13),mHandler);
    }

    PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(images.get(position));
            return images.get(position % images.size());
        }
    };
    AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Gson gson = new Gson();
            gameNewsList = gson.fromJson(new String(responseBody),GameNewsList.class);
            for(int i = 0;i<gameNewsList.getList().size();i++){
                GameNews gameNews = (GameNews) gameNewsList.getList().get(i);
                ImageView imageView = new ImageView(TestActivity.this);
                Glide.with(TestActivity.this).load(gameNews.getImage_url_big()).into(imageView);
                images.add(imageView);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    };
}
