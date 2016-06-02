package adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hm.testproject.R;
import com.thefinestartist.finestwebview.FinestWebView;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapCallBack;
import org.kymjs.kjframe.utils.StringUtils;

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

    public Context context;

    public GameNewsAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public GameNewsAdapter(Context context, int layoutid) {
        super(context, layoutid);
        this.mDatas = new ArrayList<GameNews>();
        this.context = context;
    }

    @Override
    public void initViewData(ViewHolder vh, GameNews gameNews) {
        if (vh instanceof GameNewsViewHolder) {
            ((GameNewsViewHolder) vh).newsFlag.setText("阿巴拉巴萨");
            ((GameNewsViewHolder) vh).tvListitemDate.setText(StringUtils.friendlyTime(gameNews.getPublication_date()));
            ((GameNewsViewHolder) vh).title.setText(gameNews.getTitle());
            ((GameNewsViewHolder) vh).tvListitemAbstract.setText(gameNews.getSummary());
            if(gameNews.getIs_top()){
                ((GameNewsViewHolder) vh).newsSign.setVisibility(View.VISIBLE);
            }else{
                ((GameNewsViewHolder) vh).newsSign.setVisibility(View.GONE);
            }
            if(gameNews.getImage_with_btn()){
                ((GameNewsViewHolder) vh).newsSignVideo.setVisibility(View.VISIBLE);
            }else {
                ((GameNewsViewHolder) vh).newsSignVideo.setVisibility(View.GONE);
            }
            Glide.with(context).load(gameNews.getImage_url_small()).into(((GameNewsViewHolder) vh).headIv);
            //KJBitmap kjbitmap = new KJBitmap();
            //kjbitmap.displayw(((GameNewsViewHolder) vh).headIv,gameNews.getImage_url_small());
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (getItemViewType(position) == STATE_OTHER) {
            holder.itemView.setTag(mDatas.get(position).getArticle_url());
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
        if (viewType != STATE_OTHER) {
            return super.onCreateViewHolder(parent, viewType);
        } else {
            View view = getLayoutInflater(parent.getContext()).inflate(R.layout.news_item_topic, null);
            GameNewsViewHolder viewHolder = new GameNewsViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    class GameNewsViewHolder extends ViewHolder {
        @InjectView(R.id.head_iv)
        ImageView headIv;
        @InjectView(R.id.news_sign_video)
        ImageView newsSignVideo;
        @InjectView(R.id.news_sign)
        ImageView newsSign;
        @InjectView(R.id.title)
        TextView title;
        @InjectView(R.id.tv_listitem_abstract)
        TextView tvListitemAbstract;
        @InjectView(R.id.tv_listitem_date)
        TextView tvListitemDate;
        @InjectView(R.id.news_flag)
        TextView newsFlag;

        public GameNewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
