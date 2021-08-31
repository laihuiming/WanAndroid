package com.example.myapp.Base;

import android.app.Activity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by laihm on 2021/8/26
 */
public class ActivityManage {
    //保存所有创建的Activity
    private static Set<Activity> allActivities = new HashSet<>();

    /**
     * 添加Activity到管理器
     *
     * @param activity activity
     */
    public static void addActivity(Activity activity) {
        if (activity != null) {
            allActivities.add(activity);
        }
    }


    /**
     * 从管理器移除Activity
     *
     * @param activity activity
     */
    public static void removeActivity(Activity activity) {
        if (activity != null) {
            allActivities.remove(activity);
        }
    }

    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        for (Activity activity : allActivities) {
            activity.finish();
        }

    }
    /**
     * 应用程序退出
     */
    public static void appExit() {
        try {
            finishAll();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }


    }
}
