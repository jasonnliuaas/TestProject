package viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/28.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private Context mcontext;
    private View cview;

    public ViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        cview = itemView;
        mcontext = itemView.getContext();
    }

    public View getView(int viewId){
        View view = views.get(viewId);
        if(view == null){
            view = cview.findViewById(viewId);
            views.put(viewId,view);
        }
        return view;
    }

    public void setText(String text,int viewId){
        TextView tv = (TextView) getView(viewId);
        tv.setText(text);
    }

}
