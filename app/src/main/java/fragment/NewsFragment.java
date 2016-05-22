package fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;


import com.hm.testproject.R;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import adapter.NewsAdapter;
import base.BaseListFragment;
import base.RecyclerBaseAapter;
import bean.News;
import bean.NewsList;
import http.TestApi;
import ui.empty.EmptyLayout;
import util.XmlUtils;

/**
 * 新闻资讯
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年11月12日 下午4:17:45
 * 
 */
public class NewsFragment extends BaseListFragment<News> {

    protected static final String TAG = NewsFragment.class.getSimpleName();
    private static final String CACHE_KEY_PREFIX = "newslist_";

    /*@Override
    protected NewsAdapter getListAdapter() {
        return new NewsAdapter();
    }



*/

    @Override
    protected void returnFrist() {
        mCurrentPage = 0;
    }

    @Override
    protected String getCacheKey() {
        return new StringBuilder(getCacheKeyPrefix()).append("_")
                .append(mCurrentPage).toString();
    }

    @Override
    protected NewsAdapter getRecyclerAdapter() {
        return new NewsAdapter(getActivity(), R.layout.layout_card_item);
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX + mCatalog;
    }

    @Override
    protected NewsList parseList(InputStream is,byte[] data) throws Exception {
        NewsList list = null;
        try {
            list = XmlUtils.toBean(NewsList.class, is);
        } catch (NullPointerException e) {
            list = new NewsList();
        }
        return list;
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
                                mCurrentPage++;
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
    protected NewsList readList(Serializable seri) {
        return ((NewsList) seri);
    }

    @Override
    protected void sendRequestData() {
        TestApi.getNewsList(mCatalog, mCurrentPage, mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        News news = mAdapter.getmDatas().get(position);
        if (news != null) {
            /*UIHelper.showNewsRedirect(view.getContext(), news);

            // 放入已读列表
            saveToReadedList(view, NewsList.PREF_READED_NEWS_LIST, news.getId()
                    + "");*/
        }
    }

    @Override
    protected void executeOnLoadDataSuccess(List<News> data) {
        if (mCatalog == NewsList.CATALOG_WEEK
                || mCatalog == NewsList.CATALOG_MONTH) {
            errorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            if (mState == STATE_REFRESH)
                mAdapter.clear();
            mAdapter.addData(data);
            mState = STATE_NOMORE;
            mAdapter.setState(RecyclerBaseAapter.STATE_NO_MORE);
            return;
        }
        super.executeOnLoadDataSuccess(data);
    }


    @Override
    protected long getAutoRefreshTime() {
        // 最新资讯两小时刷新一次
        if (mCatalog == NewsList.CATALOG_ALL) {

            return 2 * 60 * 60;
        }
        return super.getAutoRefreshTime();
    }

    @Override
    protected boolean isFrist() {
        return mCurrentPage == 0;
    }
}
