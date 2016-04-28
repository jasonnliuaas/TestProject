package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import viewholder.ViewHolder;
import base.RecyclerBaseAapter;
import bean.News;
import viewholder.ViewHolder;

/**
 * Created by Ekko. on 2016/4/28.
 */
public class NewsAdapter extends RecyclerBaseAapter<News> {
    public NewsAdapter(Context context) {
        super(context);
    }

    public NewsAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void initViewdata(ViewHolder holder, News news) {

    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'layout_card_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */







   /* @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(getLayoutInflater(parent.getContext()).inflate(R.layout.layout_card_item, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        News news =getmDatas().get(position);
        vh.tv_title.setText(news.getTitle());

       *//* if (AppContext.isOnReadedPostList(NewsList.PREF_READED_NEWS_LIST,
                news.getId() + "")) {
            vh.title.setTextColor(parent.getContext().getResources()
                    .getColor(ThemeSwitchUtils.getTitleReadedColor()));
        } else {
            vh.title.setTextColor(parent.getContext().getResources()
                    .getColor(ThemeSwitchUtils.getTitleUnReadedColor()));
        }*//*

        String description = news.getBody();
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
        vh.comment_count.setText(news.getCommentCount() + "");
    }

    *//**
     * This class contains all butterknife-injected Views & Layouts from layout file 'layout_card_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     *//*
     class ViewHolder extends RecyclerBaseAapter.MyViewHolder {
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }*/
}
