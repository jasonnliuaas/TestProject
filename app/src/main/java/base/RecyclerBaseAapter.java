package base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bean.Entity;
import viewholder.ViewHolder;

/**
 * Created by Administrator on 2016/4/27.
 */
public abstract class RecyclerBaseAapter<T extends Entity> extends RecyclerView.Adapter<ViewHolder>{
    public static final int STATE_EMPTY_ITEM = 0;
    public static final int STATE_LOAD_MORE = 1;
    public static final int STATE_NO_MORE = 2;
    public static final int STATE_NO_DATA = 3;
    public static final int STATE_LESS_ONE_PAGE = 4;
    public static final int STATE_NETWORK_ERROR = 5;
    public static final int STATE_OTHER = 6;
    protected int mstate = STATE_LESS_ONE_PAGE;
    private int layoutId;

    public void addData(T t){
        this.mDatas.add(t);
        notifyDataSetChanged();
    }
    public void addData(List<T> t){
        this.mDatas.addAll(t);
        notifyDataSetChanged();
    }
    public void setState(int state) {
        this.mstate = state;
    }

    public int getState() {
        return this.mstate;
    }
    public void clear(){
        this.mDatas.clear();
        notifyDataSetChanged();
    }

    public ArrayList<T> getmDatas() {
        return mDatas;
    }

    public void setDatas(ArrayList mDatas) {
        this.mDatas = mDatas;
    }

    public LayoutInflater getmInflater() {
        return mInflater;
    }

    public void setmInflater(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    protected ArrayList<T> mDatas = new ArrayList<T>();

    public RecyclerBaseAapter(Context context) {
        getLayoutInflater(context);
    }
    public RecyclerBaseAapter(Context context,int layoutId) {
        getLayoutInflater(context);
        this.layoutId = layoutId;
    }

    private LayoutInflater mInflater;

    protected LayoutInflater getLayoutInflater(Context context) {
        if (mInflater == null) {
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(layoutId,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        initViewdata(mDatas.get(position),holder);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void initViewdata(T t,ViewHolder holder);
}
