package adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<ViewPageInfo> infos = new ArrayList<>();
    private Context mContext;
    public ViewPagerAdapter(FragmentManager fm,Context mcontext) {
        super(fm);
        this.mContext = mcontext;
    }
    @Override
    public Fragment getItem(int position) {
        ViewPageInfo info = infos.get(position);
        return Fragment.instantiate(mContext, info.clss.getName(), info.args);
    }

    public void addFragment(ViewPageInfo info){
        infos.add(info);
        notifyDataSetChanged();
    }
    public void addTab(String title, String tag, Class<?> clss, Bundle args) {
        ViewPageInfo viewPageInfo = new ViewPageInfo(title, tag, clss, args);
        infos.add(viewPageInfo);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return infos.get(position).title;
    }
}
