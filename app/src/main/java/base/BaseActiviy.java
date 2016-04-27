package base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hm.testproject.AppManager;

import org.kymjs.kjframe.ui.I_KJActivity;
import org.kymjs.kjframe.ui.I_SkipActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/27.
 */
public abstract class BaseActiviy extends AppCompatActivity implements I_KJActivity,I_SkipActivity{
    @Override
    public void setRootView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initDataFromThread() {

    }

    @Override
    public void initWidget() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void skipActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent(aty,cls);
        aty.startActivity(intent);
    }

    @Override
    public void skipActivity(Activity aty, Intent it) {
            aty.startActivity(it);
            aty.finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.inject(this);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent(aty,cls);
        intent.putExtras(extras);
        aty.startActivity(intent);
        aty.finish();
    }

    @Override
    public void showActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent(aty,cls);
        aty.startActivity(intent);
    }

    @Override
    public void showActivity(Activity aty, Intent it) {
        aty.startActivity(it);
    }

    @Override
    public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent(aty,cls);
        intent.putExtras(extras);
        aty.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }
}
