package base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.hm.testproject.AppContext;
import com.hm.testproject.R;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import bean.Entity;
import bean.ListEntity;
import bean.News;
import bean.Result;
import bean.ResultBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cache.CacheManager;
import cz.msebera.android.httpclient.Header;
import it.gmariotti.recyclerview.adapter.SlideInBottomAnimatorAdapter;
import ui.DividerItemDecoration;
import ui.SpaceItemDecoration;
import util.TDevice;
import util.XmlUtils;

@SuppressLint("NewApi")
public abstract class BaseListFragment<T extends Entity> extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener
        {
    public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";
    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
    public static int mState = STATE_NONE;
    @InjectView(R.id.swiperefreshlayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.recycleview)
    protected RecyclerView mrecycleView;

    protected RecyclerBaseAapter<T> mAdapter;

  /*  @InjectView(R.id.error_layout)
    protected EmptyLayout mErrorLayout;*/

    protected int mStoreEmptyState = -1;

    protected int mCurrentPage = 0;

    protected int mCatalog = 1;
    // 错误信息
    protected Result mResult;
    private AsyncTask<String, Void, ListEntity<T>> mCacheTask;
    private ParserTask mParserTask;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pull_refresh_listview;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        initView(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCatalog = args.getInt(BUNDLE_KEY_CATALOG, 0);
        }
    }

    protected abstract RecyclerBaseAapter<T> getRecyclerAdapter();

    @Override
    public void initView(View view) {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mrecycleView.setLayoutManager(manager);
        mrecycleView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        //mrecycleView.addItemDecoration(new SpaceItemDecoration(20));
        mrecycleView.setItemAnimator(new DefaultItemAnimator());
        if (mAdapter != null) {
            SlideInBottomAnimatorAdapter slideInBottomAnimatorAdapter = new SlideInBottomAnimatorAdapter(mAdapter, mrecycleView);
            mrecycleView.setAdapter(slideInBottomAnimatorAdapter);
            //mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        } else {
            mAdapter = getRecyclerAdapter();
            SlideInBottomAnimatorAdapter slideInBottomAnimatorAdapter = new SlideInBottomAnimatorAdapter(mAdapter, mrecycleView);
            mrecycleView.setAdapter(slideInBottomAnimatorAdapter);
            /**
             * 第一次请求数据
             */
            requestData(false);
            /*if (requestDataIfViewCreated()) {
                //mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                mState = STATE_NONE;
                requestData(false);
            } else {
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }*/

        }
        mrecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= manager.getItemCount() - 1){
                        if (mAdapter == null || mAdapter.getItemCount() == 0) {
                            return;
                        }
                        if (mState == STATE_NONE) {
                            if (mAdapter.getState() == RecyclerBaseAapter.STATE_LOAD_MORE
                                    || mAdapter.getState() == RecyclerBaseAapter.STATE_NETWORK_ERROR) {
                                mCurrentPage++;
                                mState = STATE_LOADMORE;
                                requestData(false);
                                //mAdapter.setFooterViewLoading();
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

        /*mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentPage = 0;
                mState = STATE_REFRESH;
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                requestData(true);
            }
        });*/
        mrecycleView.setItemAnimator(new DefaultItemAnimator());

    }



        /*if (mAdapter != null) {
            mrecycleView.setAdapter(mAdapter);
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        } else {
            mAdapter = getListAdapter();
            mrecycleView.setAdapter(mAdapter);

            if (requestDataIfViewCreated()) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                mState = STATE_NONE;
                requestData(false);
            } else {
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }

        }
        if (mStoreEmptyState != -1) {
            mErrorLayout.setErrorType(mStoreEmptyState);
        }
    }





    protected abstract RecyclerBaseAapter<T> getListAdapter();



    protected boolean requestDataIfViewCreated() {
        return true;
    }

    protected String getCacheKeyPrefix() {
        return null;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {}

    private String getCacheKey() {
        return new StringBuilder(getCacheKeyPrefix()).append("_")
                .append(mCurrentPage).toString();
    }

    // 是否需要自动刷新
    protected boolean needAutoRefresh() {
        return true;
    }

    /***
     * 获取列表数据
     * 
     * 
     * @author 火蚁 2015-2-9 下午3:16:12
     * 
     * @return void
     * @param refresh
     */

    // 下拉刷新数据
    @Override
    public void onRefresh() {
        if (mState == STATE_REFRESH) {
            return;
        }
        // 设置顶部正在刷新
        // mrecycleView.setSelection(0);
        setSwipeRefreshLoadingState();
        mCurrentPage = 0;
        mState = STATE_REFRESH;
        requestData(true);
    }

    @Override
    public void onDestroy() {
        cancelReadCacheTask();
        cancleParserTask();
        super.onDestroy();
    }

            /**
             * 是否从网络读取数据
             * @param refresh
             */
    protected void requestData(boolean refresh) {
        String key = getCacheKey();
        if (isReadCacheData(refresh)) {
            readCacheData(key);
        } else {
            // 取新的数据
            sendRequestData();
        }
        //sendRequestData();
    }

    /***
     * 判断是否需要读取缓存的数据
     *
     * @author 火蚁 2015-2-10 下午2:41:02
     *
     * @return boolean
     * @param refresh
     * @return
     */
    protected boolean isReadCacheData(boolean refresh) {
        String key = getCacheKey();
        if (!TDevice.hasInternet()) {
            return true;
        }
        // 第一页若不是主动刷新，缓存存在，优先取缓存的
        if (CacheManager.isExistDataCache(getActivity(), key) && !refresh
                && mCurrentPage == 0) {
            return true;
        }
        // 其他页数的，缓存存在以及还没有失效，优先取缓存的
        if (CacheManager.isExistDataCache(getActivity(), key)
                && !CacheManager.isCacheDataFailure(getActivity(), key)
                && mCurrentPage != 0) {
            return true;
        }

        return false;
    }

    protected ListEntity<T> parseList(InputStream is) throws Exception {
        return null;
    }

    protected ListEntity<T> readList(Serializable seri) {
        return null;
    }
/*

    */
/***
 * 判断是否需要读取缓存的数据
 *
 * @author 火蚁 2015-2-10 下午2:41:02
 *
 * @return boolean
 * @param refresh
 * @return
 *//*

    protected boolean isReadCacheData(boolean refresh) {
        String key = getCacheKey();
        if (!TDevice.hasInternet()) {
            return true;
        }
        // 第一页若不是主动刷新，缓存存在，优先取缓存的
        if (CacheManager.isExistDataCache(getActivity(), key) && !refresh
                && mCurrentPage == 0) {
            return true;
        }
        // 其他页数的，缓存存在以及还没有失效，优先取缓存的
        if (CacheManager.isExistDataCache(getActivity(), key)
                && !CacheManager.isCacheDataFailure(getActivity(), key)
                && mCurrentPage != 0) {
            return true;
        }

        return false;
    }

    // 是否到时间去刷新数据了
    private boolean onTimeRefresh() {
        String lastRefreshTime = AppContext.getLastRefreshTime(getCacheKey());
        String currTime = StringUtils.getCurTimeStr();
        long diff = StringUtils.calDateDifferent(lastRefreshTime, currTime);
        return needAutoRefresh() && diff > getAutoRefreshTime();
    }
*/

    /***
     * 自动刷新的时间
     * <p>
     * 默认：自动刷新的时间为半天时间
     *
     * @return
     * @author 火蚁 2015-2-9 下午5:55:11
     */
    protected long getAutoRefreshTime() {
        return 12 * 60 * 60;
    }

    @Override
    public void onResume() {
        super.onResume();/*
        if (onTimeRefresh()) {
            onRefresh();
        }*/
    }

    protected void sendRequestData() {
    }

    private void readCacheData(String cacheKey) {
        cancelReadCacheTask();
        mCacheTask = new CacheTask(getActivity()).execute(cacheKey);
    }

    private void cancelReadCacheTask() {
        if (mCacheTask != null) {
            mCacheTask.cancel(true);
            mCacheTask = null;
        }
    }

    private class CacheTask extends AsyncTask<String, Void, ListEntity<T>> {
        private final WeakReference<Context> mContext;

        private CacheTask(Context context) {
            mContext = new WeakReference<Context>(context);
        }

        @Override
        protected ListEntity<T> doInBackground(String... params) {
            Serializable seri = CacheManager.readObject(mContext.get(),
                    params[0]);
            if (seri == null) {
                return null;
            } else {
                return readList(seri);
            }
        }

        @Override
        protected void onPostExecute(ListEntity<T> list) {
            super.onPostExecute(list);
            if (list != null) {
                executeOnLoadDataSuccess(list.getList());
            } else {
                //executeOnLoadDataError(null);
            }
            executeOnLoadFinish();
        }
    }

    private class SaveCacheTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<Context> mContext;
        private final Serializable seri;
        private final String key;

        private SaveCacheTask(Context context, Serializable seri, String key) {
            mContext = new WeakReference<Context>(context);
            this.seri = seri;
            this.key = key;
        }

        @Override
        protected Void doInBackground(Void... params) {
            CacheManager.saveObject(mContext.get(), seri, key);
            return null;
        }
    }

    protected AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers,
                              byte[] responseBytes) {
            if (mCurrentPage == 0) {
                /*AppContext.putToLastRefreshTime(getCacheKey(),
                        StringUtils.getCurTimeStr());*/
            }

            if (mState == STATE_REFRESH) {
                onRefreshNetworkSuccess();
            }
            executeParserTask(responseBytes);

        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            if (isAdded()) {
                readCacheData(getCacheKey());
            }
        }
    };

    private void executeParserTask(byte[] data) {
        cancleParserTask();
        mParserTask = new ParserTask(data);
        mParserTask.execute();
    }
    private void cancleParserTask(){
        if (mParserTask != null){
            mParserTask.cancel(true);
            mParserTask = null;
        }
    }

    protected void executeOnLoadDataSuccess(List<T> data) {
        if (data == null) {
            data = new ArrayList<T>();
        }

        /*if (mResult != null && !mResult.OK()) {
            AppContext.showToast(mResult.getErrorMessage());
            // 注销登陆，密码已经修改，cookie，失效了
            AppContext.getInstance().Logout();
        }*/

        //mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (mCurrentPage == 0) {
            mAdapter.clear();
        }

        for (int i = 0; i < data.size(); i++) {
            if (compareTo(mAdapter.getmDatas(), data.get(i))) {
                data.remove(i);
                i--;
            }
        }
        int adapterState = RecyclerBaseAapter.STATE_EMPTY_ITEM;
        if ((mAdapter.getItemCount() + data.size()) == 0) {
            adapterState = RecyclerBaseAapter.STATE_EMPTY_ITEM;
        } else if (data.size() == 0
                || (data.size() < getPageSize() && mCurrentPage == 0)) {
            adapterState = RecyclerBaseAapter.STATE_NO_MORE;
            mAdapter.notifyDataSetChanged();
        } else {
            adapterState = RecyclerBaseAapter.STATE_LOAD_MORE;
        }
        mAdapter.setState(adapterState);
        mAdapter.addData(data);
        // 判断等于是因为最后有一项是listview的状态
        if (mAdapter.getItemCount() == 1) {

            if (needShowEmptyNoData()) {
                // mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mAdapter.setState(RecyclerBaseAapter.STATE_EMPTY_ITEM);
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * 是否需要隐藏listview，显示无数据状态
     *
     * @author 火蚁 2015-1-27 下午6:18:59
     */
    protected boolean needShowEmptyNoData() {
        return true;
    }

    protected String getCacheKeyPrefix() {
        return null;
    }
    private String getCacheKey() {
        return new StringBuilder(getCacheKeyPrefix()).append("_")
                .append(mCurrentPage).toString();
    }

    protected boolean compareTo(List<? extends Entity> data, Entity enity) {
        int s = data.size();
        if (enity != null) {
            for (int i = 0; i < s; i++) {
                if (enity.getId() == data.get(i).getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected int getPageSize() {
        return AppContext.PAGE_SIZE;
    }

    protected void onRefreshNetworkSuccess() {
    }

    /*protected void executeOnLoadDataError(String error) {
        if (mCurrentPage == 0
                && !CacheManager.isExistDataCache(getActivity(), getCacheKey())) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        } else {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mAdapter.setState(ListBaseAdapter.STATE_NETWORK_ERROR);
            mAdapter.notifyDataSetChanged();
        }
    }*/

    // 完成刷新
    protected void executeOnLoadFinish() {
        setSwipeRefreshLoadedState();
        mState = STATE_NONE;
    }

    /**
     * 设置顶部正在加载的状态
     */
    protected void setSwipeRefreshLoadingState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    /**
     * 设置顶部加载完毕的状态
     */
    protected void setSwipeRefreshLoadedState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    /*@Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mAdapter == null || mAdapter.getItemCount() == 0) {
            return;
        }
        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        if (mState == STATE_LOADMORE || mState == STATE_REFRESH) {
            return;
        }
        // 判断是否滚动到底部
        boolean scrollEnd = false;
       try {
            if (view.getPositionForView(mAdapter.get) == view
                    .getLastVisiblePosition())
                scrollEnd = true;
        } catch (Exception e) {
            scrollEnd = false;
        }

        if (mState == STATE_NONE && scrollEnd) {
            if (mAdapter.getState() == RecyclerBaseAapter.STATE_LOAD_MORE
                    || mAdapter.getState() == RecyclerBaseAapter.STATE_NETWORK_ERROR) {
                mCurrentPage++;
                mState = STATE_LOADMORE;
                requestData(false);
                //mAdapter.setFooterViewLoading();
            }
        }
    }*/

            /**
             * 把数据转换成序列化
              */
    class ParserTask extends AsyncTask<Void, Void, String> {

        private final byte[] reponseData;
        //是否转换失败
        private boolean parserError;
        private List<T> list;

        public ParserTask(byte[] data) {
            this.reponseData = data;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                ListEntity<T> data = parseList(new ByteArrayInputStream(
                        reponseData));
                new SaveCacheTask(getActivity(), data, getCacheKey()).execute();
                list = data.getList();
                if (list == null) {
                    ResultBean resultBean = XmlUtils.toBean(ResultBean.class,
                            reponseData);
                    if (resultBean != null) {
                        mResult = resultBean.getResult();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

                parserError = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (parserError) {
                //读取缓存数据
                readCacheData(getCacheKey());
            } else {

                executeOnLoadDataSuccess(list);
                executeOnLoadFinish();
            }

            //executeOnLoadDataSuccess(list);
        }







        /**
         * 保存已读的文章列表
         *
         * @param view
         * @param prefFileName
         * @param key
         */
   /* protected void saveToReadedList(final View view, final String prefFileName,
            final String key) {
        // 放入已读列表
        AppContext.putReadedPostList(prefFileName, key, "true");
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        if (tvTitle != null) {
            tvTitle.setTextColor(AppContext.getInstance().getResources().getColor(ThemeSwitchUtils.getTitleReadedColor()));
        }
    }*/



    }

}
