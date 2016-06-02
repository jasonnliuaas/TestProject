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
import com.ta.utdid2.android.utils.StringUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import bean.Entity;
import util.TDevice;
import viewholder.ViewHolder;

/**
 * Created by Administrator on 2016/4/27.
 */
public abstract class RecyclerBaseAapter<T extends Entity> extends RecyclerView.Adapter<ViewHolder>
implements View.OnClickListener{
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


    public OnRecyclerViewItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    protected OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }


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
        if (getItemViewType(position) == STATE_OTHER) {
            initViewData(holder, mDatas.get(position));
        } else {
            ProgressBar progressBar = (ProgressBar) holder.getView(R.id.progressbar);
            TextView text = (TextView) holder.getView(R.id.text);
            switch (getState()) {
                case STATE_LOAD_MORE:
                    setFooterViewLoading();
                    break;
                case STATE_NO_MORE:
                    mFootview.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    text.setVisibility(View.VISIBLE);
                    text.setText("已加载全部");
                    break;
                case STATE_EMPTY_ITEM:
                    progressBar.setVisibility(View.GONE);
                    mFootview.setVisibility(View.VISIBLE);
                    text.setText("暂无数据");
                    break;
                case STATE_NETWORK_ERROR:
                    mFootview.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    text.setVisibility(View.VISIBLE);
                    if (TDevice.hasInternet()) {
                        text.setText("加载出错了");
                    } else {
                        text.setText("没有可用的网络");
                    }
                    break;
                default:
                    progressBar.setVisibility(View.GONE);
                    mFootview.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    break;
            }
        }
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



    public void setFooterViewLoading() {
        setFooterViewLoading("正在加载");
    }

    public void setFooterViewLoading(String loadMsg) {
        ProgressBar progress = (ProgressBar) mFootview
                .findViewById(R.id.progressbar);
        TextView text = (TextView) mFootview.findViewById(R.id.text);
        mFootview.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        if (StringUtils.isEmpty(loadMsg)) {
            text.setText("正在加载···");
        } else {
            text.setText(loadMsg);
        }
    }


    public void setFooterViewText(String msg) {
        ProgressBar progress = (ProgressBar) mFootview
                .findViewById(R.id.progressbar);
        TextView text = (TextView) mFootview.findViewById(R.id.text);
        mFootview.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText(msg);
    }
}
