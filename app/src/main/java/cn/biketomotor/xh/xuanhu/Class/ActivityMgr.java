package cn.biketomotor.xh.xuanhu.Class;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityMgr {

    private static List<Activity> activityList = new ArrayList<>();

    //往活动列表里添加活动
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //删除活动列表的相应活动
    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    //判断活动列表是否完成，若未完成则强制停止
    public static void finishAllActivities() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
    }
}
