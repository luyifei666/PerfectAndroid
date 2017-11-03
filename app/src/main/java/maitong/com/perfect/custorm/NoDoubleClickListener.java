package maitong.com.perfect.custorm;

import android.view.View;

import java.util.Calendar;

/**
 * Created by lz on 2016/1/24.
 * 防止快速点击问题
 * 在需要的处理的地方 实例化此类 替代 android的view.onclick 即可
 * 目前处理间隔为1.5秒
 * onclick
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1500;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);

        }
    }


    public abstract void onNoDoubleClick(View v);

}
