package maitong.com.perfect.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import maitong.com.perfect.R;
import maitong.com.perfect.custorm.NoDoubleClickListener;


public class ChartBaseActivity extends FragmentActivity {


    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    protected Typeface mTfRegular;
    protected Typeface mTfLight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }



    private TextView toolbar_string, toolbar_left_tv, toolbar_right_tv;
    private ImageView toolbar_left_im, toolbar_right_im;
    private RelativeLayout toolbar;

    public TextView getToolbar_string() {
        return toolbar_string;
    }

    public int getToolbarHeight() {
        toolbar = (RelativeLayout) findViewById(R.id.toolbar);
        return toolbar.getHeight();
    }

    public void setToolbar_string(String str) {
        toolbar_string = (TextView) findViewById(R.id.toolbar_title);
        toolbar_string.setText(str);
    }

    public TextView getToolbar_left_tv() {
        return toolbar_left_tv;
    }

    public void setToolbar_left_tv(String str) {
        toolbar_left_tv = (TextView) findViewById(R.id.toolbar_left_tv);
        if (!str.equals("")) {
            toolbar_left_tv.setText(str);
        }
        toolbar_left_tv.setVisibility(View.VISIBLE);
        toolbar_left_tv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                finish();
            }
        });
    }

    public TextView getToolbar_right_tv() {
        return toolbar_right_tv;
    }

    public void setToolbar_right_tv(String str) {
        toolbar_right_tv = (TextView) findViewById(R.id.toolbar_right_tv);
        toolbar_right_tv.setText(str);
        toolbar_right_tv.setVisibility(View.VISIBLE);
    }

    public ImageView getToolbar_left_im() {
        return toolbar_left_im;
    }

    public void setToolbar_left_im(int id) {
        toolbar_left_im = (ImageView) findViewById(R.id.toolbar_left_im);
        toolbar_left_im.setImageResource(id);
        toolbar_left_im.setVisibility(View.VISIBLE);
        toolbar_left_im.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                finish();
            }
        });
    }

    public ImageView getToolbar_right_im() {
        return toolbar_right_im;
    }

    public void setToolbar_right_im(int id) {
        toolbar_right_im = (ImageView) findViewById(R.id.toolbar_right_im);
        toolbar_right_im.setImageResource(id);
        toolbar_right_im.setVisibility(View.VISIBLE);
    }

    public void hindAll() {
        if (toolbar_left_im != null) {
            toolbar_left_im.setVisibility(View.GONE);
        }
        if (toolbar_left_tv != null) {
            toolbar_left_tv.setVisibility(View.GONE);
        }
        if (toolbar_right_im != null) {
            toolbar_right_im.setVisibility(View.GONE);
        }
        if (toolbar_right_im != null) {
            toolbar_right_im.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyBoard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(activity.getWindow().peekDecorView()
                    .getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showSoftKeyBoard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

}
