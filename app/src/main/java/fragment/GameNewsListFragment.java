package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.thefinestartist.finestwebview.FinestWebView;

import junit.framework.Test;

import java.io.InputStream;
import java.io.Serializable;

import adapter.GameNewsAdapter;
import base.BaseListFragment;
import base.RecyclerBaseAapter;
import bean.GameNews;
import bean.GameNewsList;
import bean.ListEntity;
import bean.NewsList;
import http.TestApi;
import util.XmlUtils;

/**
 * Created by Ekko on 2016/5/20.
 */
public class GameNewsListFragment extends BaseListFragment<GameNews>{
    public static final  String TAG = GameNewsListFragment.class.getSimpleName();
    private static final String CACHE_KEY_PREFIX = "gamenewslist";
    private String mPageurl="0000";
    private String mnextPage;
    private String channel;
    public GameNewsList gameNewsList;

    @Override
    protected boolean isFrist() {
        return mPageurl.endsWith("_1.shtml");
    }



    @Override
    protected GameNewsList parseList(InputStream is,byte[] data) throws Exception {
        GameNewsList list = null;
        Gson gson = new Gson();
        list = gson.fromJson(new String(data),GameNewsList.class);
        mnextPage = list.getNext();
        return list;
    }

    public GameNewsListFragment getGameNewsListFragment(Context context,String channel){
        String cname = GameNewsListFragment.class.getName();
        GameNewsListFragment newsListFragment = (GameNewsListFragment) Fragment.instantiate(context,cname);
        Bundle bundle = new Bundle();
        bundle.putString("channel",channel);
        return newsListFragment;
    }

    @Override
    protected GameNewsList readList(Serializable seri) {
        gameNewsList = ((GameNewsList) seri);
        mnextPage =gameNewsList.getNext();
        return gameNewsList;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if (lastVisiblePosition >= manager.getItemCount() - 1) {
                        if (mAdapter == null || mAdapter.getItemCount() == 0) {
                            return;
                        }
                        if (mState == STATE_NONE) {
                            if (mAdapter.getState() == RecyclerBaseAapter.STATE_LOAD_MORE
                                    || mAdapter.getState() == RecyclerBaseAapter.STATE_NETWORK_ERROR) {
                                mPageurl = mnextPage;
                                mState = STATE_LOADMORE;
                                requestData(false);
                                mAdapter.setFooterViewLoading();
                            }

                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        String channel = arg.getString("channel");
        if(TextUtils.isEmpty(channel)){
            mPageurl = TestApi.getPages(mCatalog);
        }else{
            mPageurl = TestApi.getPages(channel);
        }
    }

    @Override
    protected RecyclerBaseAapter<GameNews> getRecyclerAdapter() {
        return new GameNewsAdapter(getContext());
    }


    @Override
    public  String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX;
    }



    @Override
    protected void returnFrist() {
        mPageurl = TestApi.getPages(mCatalog);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        new FinestWebView.Builder(getActivity()).show(mAdapter.getmDatas().get(position).getArticle_url());
    }

    @Override
    protected String getCacheKey() {
        return new StringBuilder(getCacheKeyPrefix()).append("_")
                .append(mPageurl).toString();
    }



    @Override
    protected void sendRequestData() {
        TestApi.getZmNewsList(mPageurl,mHandler);
    }
}
