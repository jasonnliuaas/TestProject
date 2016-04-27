package com.hm.testproject;

import org.kymjs.kjframe.ui.KJActivityStack;

/**
 * Created by Administrator on 2016/4/27.
 */
public class AppManager{
    static KJActivityStack AppManager;
    public static KJActivityStack getAppManager(){
        if(AppManager == null){
            AppManager =KJActivityStack.create();
        }
        return AppManager;
    }
}
