package ui.acy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hm.testproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsActivity extends AppCompatActivity {

    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.inject(this);
    }
}
