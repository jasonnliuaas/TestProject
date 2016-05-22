package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.testproject.R;

import java.util.ArrayList;
import java.util.List;

import base.RecyclerBaseAapter;
import bean.GameNews;
import butterknife.ButterKnife;
import butterknife.InjectView;
import viewholder.ViewHolder;

/**
 * Created by Ekko on 2016/5/20.
 */
public class GameNewsAdapter extends RecyclerBaseAapter<GameNews> {
    public GameNewsAdapter(Context context) {
        super(context);
    }
    public GameNewsAdapter(Context context,int layoutid) {
        super(context,layoutid);
        this.mDatas = new ArrayList<GameNews>();
    }
    @Override
    public void initViewData(ViewHolder vh, GameNews gameNews) {
        if(vh instanceof GameNewsViewHolder){
            ((GameNewsViewHolder) vh).tvDescription.setText(gameNews.getIs_report());
            ((GameNewsViewHolder) vh).tvTime.setText(gameNews.getPublication_date());
            ((GameNewsViewHolder) vh).tvTitle.setText(gameNews.getTitle());
        }
    }

    @Override
    public void addData(GameNews gameNews) {
        this.mDatas.add(gameNews);
        notifyDataSetChanged();

    }

    @Override
    public void addData(List<GameNews> t) {
        this.mDatas.addAll(t);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType != STATE_OTHER){
            return   super.onCreateViewHolder(parent,viewType);
        }else{
            GameNewsViewHolder viewHolder = new GameNewsViewHolder(getLayoutInflater(parent.getContext()).inflate(R.layout.item_gamenews, null));
            return viewHolder;
        }
    }

    class GameNewsViewHolder extends ViewHolder {
        @InjectView(R.id.iv_tip)
        ImageView ivTip;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.tv_description)
        TextView tvDescription;
        @InjectView(R.id.tv_time)
        TextView tvTime;

        public GameNewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }

}
