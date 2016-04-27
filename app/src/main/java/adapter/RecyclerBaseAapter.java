package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hm.testproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RecyclerBaseAapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int STATE_EMPTY_ITEM = 0;
    public static final int STATE_LOAD_MORE = 1;
    public static final int STATE_NO_MORE = 2;
    public static final int STATE_NO_DATA = 3;
    public static final int STATE_LESS_ONE_PAGE = 4;
    public static final int STATE_NETWORK_ERROR = 5;
    public static final int STATE_OTHER = 6;

    public RecyclerBaseAapter(Context context) {
        getLayoutInflater(context);

    }

    private LayoutInflater mInflater;

    protected LayoutInflater getLayoutInflater(Context context) {
        if (mInflater == null) {
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_tip)
        ImageView ivTip;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.ll_title)
        LinearLayout llTitle;
        @InjectView(R.id.tv_description)
        TextView tvDescription;
        @InjectView(R.id.tv_source)
        TextView tvSource;
        @InjectView(R.id.tv_time)
        TextView tvTime;
        @InjectView(R.id.tv_comment_count)
        TextView tvCommentCount;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }


    protected int state = STATE_LESS_ONE_PAGE;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(mInflater.inflate(R.layout.layout_card_item, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
