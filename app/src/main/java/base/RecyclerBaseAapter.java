package base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hm.testproject.R;

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
    protected boolean ishasFoot = false;
    protected boolean ishasHead = false;
    protected View mFootview;

    public boolean ishasFoot() {
        return ishasFoot;
    }

    public void setIshasFoot(boolean ishasFoot) {
        this.ishasFoot = ishasFoot;
    }

    public boolean ishasHead() {
        return ishasHead;
    }

    public void setIshasHead(boolean ishasHead) {
        this.ishasHead = ishasHead;
    }

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
    public  void initViewData(ViewHolder vh,T t){

    }
    protected boolean isHasFoot(){
        return true;
    }

    public ArrayList<T> getmDatas() {
        return mDatas;
    }

    public void setDatas(ArrayList mDatas) {
        this.mDatas = mDatas;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position != getItemCount()-1 && isHasFoot())
        initViewData(holder,mDatas.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount()-1 && isHasFoot()){
            return getState();
            }

        return STATE_OTHER;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent.getContext());
            mFootview =  inflater.inflate(R.layout.list_cell_footer,null);
            ProgressBar progressBar = (ProgressBar) mFootview.findViewById(R.id.progressbar);
            TextView text = (TextView) mFootview.findViewById(R.id.text);
            switch (viewType){
                case STATE_LOAD_MORE:
                    progressBar.setVisibility(View.VISIBLE);
                    text.setText("正在加载····");
                    break;
                case STATE_NO_MORE:
                    text.setText("没有更多了");
                    progressBar.setVisibility(View.GONE);
                    break;
                case STATE_NETWORK_ERROR:
                    text.setText("网络错误");
                    progressBar.setVisibility(View.GONE);
                    break;
            }
            return  new ViewHolder(mFootview);
    }

    @Override
    public int getItemCount() {
        switch (getState()){
            case STATE_NO_DATA:
                return 1;
            case STATE_LOAD_MORE:
            case STATE_NO_MORE:
            case STATE_EMPTY_ITEM:
            case STATE_NETWORK_ERROR:
                return mDatas.size() + 1;
            case STATE_LESS_ONE_PAGE:
                return mDatas.size();
            default:
                break;
        }
        return mDatas.size();
    }


    public void setFooterViewLoading(String message) {
        ProgressBar progressbar = (ProgressBar) mFootview.findViewById(R.id.progressbar);
        TextView text = (TextView) mFootview.findViewById(R.id.text);
        text.setText(message);
        progressbar.setVisibility(View.VISIBLE);
    }
    public void setFooterViewLoading() {
        setFooterViewLoading("正在加载");
    }
}
