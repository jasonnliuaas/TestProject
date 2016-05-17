package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hm.testproject.R;

import org.kymjs.kjframe.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import base.RecyclerBaseAapter;
import bean.News;
import butterknife.ButterKnife;
import butterknife.InjectView;
import viewholder.ViewHolder;

/**
 * Created by Ekko. on 2016/4/28.
 */
public class NewsAdapter extends RecyclerBaseAapter<News>{


    public NewsAdapter(Context context) {
        super(context);
    }

    public NewsAdapter(Context context, int layoutId) {
        super(context, layoutId);
        this.mDatas = new ArrayList<News>();

    }

    @Override
    public void addData(News news) {
       this.mDatas.add(news);
        notifyDataSetChanged();
    }
    public void addData(List<News> t){
        this.mDatas.addAll(t);
        notifyDataSetChanged();
    }

    @Override
    public void initViewData(ViewHolder vh, News news) {
        if(vh instanceof MyViewHolder){
            ((MyViewHolder) vh).tvDescription.setText(news.getBody());
            ((MyViewHolder) vh).tvTitle.setText(news.getTitle());
            ((MyViewHolder) vh).tvTime.setText(StringUtils.friendlyTime(news.getPubDate()));
            ((MyViewHolder) vh).tvCommentCount.setText(news.getCommentCount()+"");
            //((MyViewHolder) vh).tvSource.setText(news.getAuthor());
        }
    }

    /*@Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(getLayoutInflater(parent.getContext()).inflate(R.layout.layout_card_item, null));
        return viewHolder;
    }*/

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }






    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType != STATE_OTHER){
          return   super.onCreateViewHolder(parent,viewType);
        }else{
            MyViewHolder viewHolder = new MyViewHolder(getLayoutInflater(parent.getContext()).inflate(R.layout.layout_card_item, null));
            return viewHolder;
        }
    }



    /*@Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        News news =getmDatas().get(position);


       *//* if (AppContext.isOnReadedPostList(NewsList.PREF_READED_NEWS_LIST,
                news.getId() + "")) {
            vh.title.setTextColor(parent.getContext().getResources()
                    .getColor(ThemeSwitchUtils.getTitleReadedColor()));
        } else {
            vh.title.setTextColor(parent.getContext().getResources()
                    .getColor(ThemeSwitchUtils.getTitleUnReadedColor()));
        }*//*

        *//*String description = news.getBody();
        vh.description.setVisibility(View.GONE);
        if (description != null && !StringUtils.isEmpty(description)) {
            vh.description.setVisibility(View.VISIBLE);
            vh.description.setText(description.trim());
        }

        vh.source.setText(news.getAuthor());
        if (StringUtils.isToday(news.getPubDate())) {
            vh.tip.setVisibility(View.VISIBLE);
        } else {
            vh.tip.setVisibility(View.GONE);
        }
        vh.time.setText(StringUtils.friendly_time(news.getPubDate()));
        vh.comment_count.setText(news.getCommentCount() + "");*//*
    }*/

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'layout_card_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
     class MyViewHolder extends ViewHolder {
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

        MyViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
