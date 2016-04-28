package base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hm.testproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 带有导航条的基类
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年11月6日 下午4:59:50
 */
public abstract class BaseViewPagerFragment extends BaseFragment {
    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.pager)
    protected
    ViewPager pager;


    /*protected ViewPager mViewPager;*/
   /* protected ViewPageFragmentAdapter mTabsAdapter;
    protected EmptyLayout mErrorLayout;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_viewpage_fragment, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /*mViewPager = (ViewPager) view.findViewById(R.id.pager);*/

        //mErrorLayout = (EmptyLayout) view.findViewById(R.id.error_layout);

       /* mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(),
                mTabStrip, mViewPager);*/
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(pager);
        setScreenPageLimit();
        // onSetupTabAdapter(mTabsAdapter);
        // if (savedInstanceState != null) {
        // int pos = savedInstanceState.getInt("position");
        // mViewPager.setCurrentItem(pos, true);
        // }
    }

    protected void setScreenPageLimit() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    // @Override
    // public void onSaveInstanceState(Bundle outState) {
    // //No call for super(). Bug on API Level > 11.
    // if (outState != null && mViewPager != null) {
    // outState.putInt("position", mViewPager.getCurrentItem());
    // }
    // //super.onSaveInstanceState(outState);
    // }

    // protected abstract void onSetupTabAdapter(ViewPageFragmentAdapter adapter);
}