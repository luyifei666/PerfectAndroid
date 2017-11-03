package maitong.com.perfect.utils;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import maitong.com.perfect.MainApplication;
import maitong.com.perfect.R;


/**
 * Toast显示帮助类，不需要在UI线程调用也不会死
 *
 * @author Tony
 */
public class ToastUtil {

    private static long lastShowTime = -1;

    private static void _showToast(final int textid, final String text,
                                   final int delay, final boolean allowToastQueue, final int gravity, final int xOffset, final int yOffset, final int layoutid) {
        long currentTime = System.currentTimeMillis();

        if (delay == Toast.LENGTH_SHORT) {
            if (!allowToastQueue && currentTime - lastShowTime < 2000) {
                return;
            } else {
                lastShowTime = currentTime;
            }
        } else if (delay == Toast.LENGTH_LONG) {
            if (!allowToastQueue && currentTime - lastShowTime < 3000) {
                return;
            } else {
                lastShowTime = currentTime;
            }
        }

        final View layout;
        if (layoutid != -1) {
            layout = LayoutInflater.from(MainApplication.getContext()).inflate(layoutid, null);
        } else {
            layout = null;
        }

        new Handler(MainApplication.getContext().getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast t;
                        if (textid == -1) {
                            t = Toast.makeText(MainApplication.getContext(), text, delay);
                        } else {
                            t = Toast.makeText(MainApplication.getContext(), textid, delay);
                        }

                        if (gravity != -1) {
                            t.setGravity(gravity, xOffset, yOffset);
                        } else {
                            t.setMargin(0, 0.05f);
                        }

                        if (layoutid != -1) {

                            TextView textViewMessage = layout.findViewById(R.id.textViewMessage);

                            if (textid == -1) {
                                textViewMessage.setText(text);
                            } else {
                                textViewMessage.setText(textid);
                            }

                            t.setView(layout);
                        }
                        t.show();
                    }
                });
    }


    /**
     * 入队列，短显示
     */
    public static void showShortToast(final int id) {
        _showToast(id, null, Toast.LENGTH_SHORT, true, -1, 0, 0, R.layout.custom_toast);
    }

    public static void showShortToast(final String tips) {
        if (StringUtil.isEmpty(tips)) {
            return;
        }
        _showToast(-1, tips, Toast.LENGTH_SHORT, true, -1, 0, 0, R.layout.custom_toast);
    }

}
