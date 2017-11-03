package maitong.com.perfect.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.kaopiz.kprogresshud.KProgressHUD;

import maitong.com.perfect.MainApplication;
import maitong.com.perfect.R;
import maitong.com.perfect.custorm.NoDoubleClickListener;
import maitong.com.perfect.utils.JsonUtils;
import maitong.com.perfect.utils.StatusBarUtil;
import maitong.com.perfect.utils.StringUtil;
import maitong.com.perfect.utils.ToastUtil;


/**
 * 通用Activity基类 用来处理页面全局事件
 *
 * @author Tony
 */
public class BaseActivity extends AppCompatActivity {


    boolean isFromNotification;
    private SVProgressHUD mSVProgressHUD;

    public static final String FINISH_ALL = "FINISH_ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_left_out_slow);
        super.onCreate(savedInstanceState);
//		判断是否从通知进入
//		isFromNotification = getIntent().getBooleanExtra(StatusBarController.EXTRA_FROM_NOTIFICATION, false);
//		registerConnectionState();
//		registerFinishAllReceivers();
        initStatus();
        initDialog();
    }

    private void initDialog() {
        mSVProgressHUD = new SVProgressHUD(this);
    }

    public void showProgressDialog() {
        dissmissProgressDialog();
        mSVProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void showProgressDialog(String message) {
        if (TextUtils.isEmpty(message)) {
            showProgressDialog();
        } else {
            dissmissProgressDialog();
            mSVProgressHUD.showWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Black);
        }
    }

    public void showProgressSuccess(String message) {
        dissmissProgressDialog();
        mSVProgressHUD.showSuccessWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void showProgressError(String message) {
        dissmissProgressDialog();
        mSVProgressHUD.showErrorWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void dissmissProgressDialog() {
        if (mSVProgressHUD.isShowing()) {
            mSVProgressHUD.dismiss();
        }
    }

    private void initStatus() {
        //设置状态栏的颜色
//		int currentSkinType = SkinManager.getCurrentSkinType(this);
//		if (SkinManager.THEME_DAY == currentSkinType) {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.blue_1_205791), 60);
//		} else {
//			StatusBarUtil.setColor(this, getResources().getColor(R.color.main_color_night), 60);
//		}
    }


    @Override
    protected void onDestroy() {
        unregisterFinishAllReceivers();
        unregisterBaseReceivers();
//		unregisterConnectionState();
        super.onDestroy();
    }

    /**
     * @param title               居中文字
     * @param rightIconResourceId 右边icon的资源id 为-1隐藏组件
     * @param rightClickListener  右侧点击事件监听
     */
    protected void updateSubTitleBar(final String title,
                                     int rightIconResourceId, OnClickListener rightClickListener) {
        TextView tv = (TextView) findViewById(R.id.sub_title_bar_tv_title);
        if (tv != null) {
            if (title != null && title.length() > 12) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.7f);
            }
            tv.setText(title);
        }
        RelativeLayout leftLL = (RelativeLayout) findViewById(R.id.sub_title_bar_ll_left);
        RelativeLayout rightLL = (RelativeLayout) findViewById(R.id.sub_title_bar_ll_right);
        ImageView rightIcon = (ImageView) findViewById(R.id.sub_title_bar_img_right);
        if (rightIcon != null) {
            if (rightIconResourceId != -1) {
                rightIcon.setBackgroundResource(rightIconResourceId);
                if (rightLL != null) {
                    rightLL.setVisibility(View.VISIBLE);
                }
            } else {
                if (rightLL != null) {
                    rightLL.setVisibility(View.INVISIBLE);
                }
            }
        }
        if (leftLL != null) {
            leftLL.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyBoard(BaseActivity.this);
                    finish();
//					if (isFromNotification && !MainTabActivity.isRunning) {
//						//从通知栏进入 并且主页没有打开，打开主页
////						AccountController.gotoMainActivity(BaseActivity.this, true);
//					}
                }
            });
        }
        if (rightLL != null) {
            rightLL.setOnClickListener(rightClickListener);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//		if (isFromNotification && !MainTabActivity.isRunning) {
////			从通知栏进入 并且主页没有打开，打开主页
//			AccountController.gotoMainActivity(BaseActivity.this, true);
//		}
    }

    /**
     * 设置右侧文字按钮形式的标题栏
     *
     * @param title              标题文字
     * @param rightText          右侧按钮文字
     * @param rightClickListener 右侧点击监听
     */
    protected void updateSubTitleTextBar(final String title,
                                         String rightText, OnClickListener rightClickListener) {
        TextView tv = (TextView) findViewById(R.id.sub_title_bar_tv_title);
        if (tv != null) {
            if (title != null && title.length() > 12) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.7f);
            }
            tv.setText(title);
        }
        RelativeLayout leftLL = (RelativeLayout) findViewById(R.id.sub_title_bar_ll_left);
        RelativeLayout rightLL = (RelativeLayout) findViewById(R.id.sub_title_bar_ll_right);
        rightLL.setVisibility(View.GONE);

        LinearLayout rightTextLL = (LinearLayout) findViewById(R.id.sub_title_bar_ll_btn_right);
        TextView rightBtn = (TextView) findViewById(R.id.sub_title_bar_btn_right);
        if (!StringUtil.isEmpty(rightText)) {
            rightBtn.setText(rightText);
            rightTextLL.setVisibility(View.VISIBLE);
        } else {
            if (rightTextLL != null) {
                rightTextLL.setVisibility(View.INVISIBLE);
            }
        }
        if (leftLL != null) {
            leftLL.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyBoard(BaseActivity.this);
                    finish();
                }
            });
        }
        if (rightTextLL != null) {
            rightTextLL.setOnClickListener(rightClickListener);
        }
    }

    protected void setTitleBarRightText(String rightText) {
        TextView rightBtn = (TextView) findViewById(R.id.sub_title_bar_btn_right);
        rightBtn.setText(rightText);
    }

    /**
     * 只更新标题文字
     *
     * @param title 标题文字
     */
    protected void updateTitleText(String title) {
        TextView tv = (TextView) findViewById(R.id.sub_title_bar_tv_title);
        if (tv != null) {
            if (title != null && title.length() > 12) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.7f);
            }
            tv.setText(title);
        }
    }

    /**
     * 设置右侧文字按钮是否可以点击
     *
     * @param clickable 点击标记
     */
    protected void updateRightTextBtn(boolean clickable) {
        LinearLayout rightTextLL = (LinearLayout) findViewById(R.id.sub_title_bar_ll_btn_right);
        if (rightTextLL != null) {
            rightTextLL.setEnabled(clickable);
        }
    }

    /**
     * 设置右侧按钮的文字颜色
     *
     * @param colorId 已设置的颜色值
     */
    protected void updateRightTextColor(int colorId) {
        TextView rightBtn = (TextView) findViewById(R.id.sub_title_bar_btn_right);
        if (rightBtn != null) {
            rightBtn.setTextColor(getResources().getColor(colorId));
        }
    }

    /**
     * 设置title Bar 的左右按钮是否可见。
     *
     * @param leftVisible 左侧按钮可见
     */
    public void setleftTitleBarBtnVisable(int leftVisible) {
        RelativeLayout leftIcon = (RelativeLayout) findViewById(R.id.sub_title_bar_ll_left);
        if (leftIcon != null)
            leftIcon.setVisibility(leftVisible);
    }

    /**
     * 设置title Bar 的右侧红点是否可见。
     *
     * @param leftVisible 左侧按钮可见
     */
    public void setIconNewVisible(int leftVisible) {
        ImageView iv_new = (ImageView) findViewById(R.id.iv_title_right_new);
        if (iv_new != null)
            iv_new.setVisibility(leftVisible);
    }

    /**
     * 设置title Bar 的左右按钮是否可见。
     *
     * @param rightVisible 右侧按钮可见
     */
    public void setRightTitleBarBtnVisable(int rightVisible) {
        RelativeLayout rightIcon = (RelativeLayout) findViewById(R.id.sub_title_bar_ll_right);
        if (rightIcon != null)
            rightIcon.setVisibility(rightVisible);
    }

    /**
     * 设置title Bar 的左右按钮是否可见。
     *
     * @param rightVisible 右侧按钮可见
     */
    public void setRightTextTitleBarBtnVisable(int rightVisible) {
        LinearLayout rightIcon = (LinearLayout) findViewById(R.id.sub_title_bar_ll_btn_right);
        if (rightIcon != null)
            rightIcon.setVisibility(rightVisible);
    }

    /**
     * 设置左侧按钮点击事件
     *
     * @param leftListener 左侧监听
     */
    public void setLeftTitleBarBtnListener(OnClickListener leftListener) {
        RelativeLayout leftIcon = (RelativeLayout) findViewById(R.id.sub_title_bar_ll_left);
        if (leftIcon != null) {
            leftIcon.setOnClickListener(leftListener);
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

    private String baseAction = null;
    private Handler basehHandler = null;
    private BroadcastReceiver baseReceiver = null;

    public void registerBaseReceivers(String action, Handler handler) {
        if (action == null || handler == null) {
            return;
        }
        baseAction = action;
        basehHandler = handler;
        IntentFilter orderFilter = new IntentFilter();
        orderFilter.addAction(action);
        baseReceiver = new BaseBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(baseReceiver, orderFilter);
    }

    public void unregisterBaseReceivers() {
        if (baseReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(baseReceiver);
        }
    }

    private class BaseBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(baseAction)) {
                String json = intent.getStringExtra("json");
                int what = intent.getIntExtra("what", -1);
                int state = intent.getIntExtra("state", -1);
                Message msg = new Message();
                msg.what = what;
                msg.arg1 = state;
                msg.obj = json;
                if (basehHandler != null) {
                    basehHandler.sendMessage(msg);
                }
            }
        }
    }

    private FinishAllBroadcastReceiver finishAllReceiver = null;

    private void registerFinishAllReceivers() {
        IntentFilter orderFilter = new IntentFilter();
        orderFilter.addAction(FINISH_ALL);
        finishAllReceiver = new FinishAllBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(finishAllReceiver, orderFilter);
    }

    private void unregisterFinishAllReceivers() {
        if (finishAllReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(finishAllReceiver);
        }
    }

    private class FinishAllBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    protected boolean handleJson(String json) {
        boolean result = false;
        if (json == null) {// 网络或服务器异常提示
            String message = getString(R.string.network_or_server_error);
            ToastUtil.showShortToast(message);
        } else {
            result = JsonUtils.isSuccess(json);
            if (!result) {// 服务器错返回误信息提示
                String message = JsonUtils.getMessage(json);
                ToastUtil.showShortToast(message);
            }
        }
        return result;
    }

    private ConnectionStateChangedReceiver connectionStateChangedReceiver;

    private void registerConnectionState() {
        connectionStateChangedReceiver = new ConnectionStateChangedReceiver();
        IntentFilter orderFilter = new IntentFilter();
        orderFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectionStateChangedReceiver, orderFilter);
    }

    private void unregisterConnectionState() {
        if (connectionStateChangedReceiver != null) {
            unregisterReceiver(connectionStateChangedReceiver);
        }
    }

    public class ConnectionStateChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isBreak = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            LinearLayout ll_network_error = (LinearLayout) findViewById(R.id.ll_network_error);
            if (isBreak) {
                if (ll_network_error != null) {
                    ll_network_error.setVisibility(View.VISIBLE);
                }
            } else {
                if (ll_network_error != null) {
                    ll_network_error.setVisibility(View.GONE);
                }
                //检查百度推送key是否已经获取
//				ApiKeyController.getInstance().checkBaiduApiKey();
            }
        }
    }

    /**
     * 显示提示页面
     *
     * @param upImage 右上角箭头是否显示
     * @param icon    中心图标是否显示
     * @param notice  提示文案
     */
    public void showTipsView(boolean upImage, boolean icon, String notice) {
        RelativeLayout rl_tips = (RelativeLayout) findViewById(R.id.rl_tips);
        if (rl_tips != null) {
            rl_tips.setVisibility(View.VISIBLE);
            if (upImage) {
                findViewById(R.id.iv_tips_up).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.iv_tips_up).setVisibility(View.GONE);
            }
            if (icon) {
                findViewById(R.id.iv_tips_icon).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.iv_tips_icon).setVisibility(View.GONE);
            }
            TextView textView = (TextView) findViewById(R.id.tv_tips_text);
            if (textView != null) {
                textView.setText(notice);
            }
        }
    }

    /**
     * 隐藏提示画面
     */
    public void hideTipsView() {
        RelativeLayout rl_tips = (RelativeLayout) findViewById(R.id.rl_tips);
        if (rl_tips != null) {
            rl_tips.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void setClipboard(String text) {
        if (TextUtils.isEmpty(text))
            return;
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                    MainApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    MainApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("", text);
            clipboard.setPrimaryClip(clip);
        }
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

    public void initToolBar(Toolbar toolbar, String title, int icon) {
        toolbar.setTitle(title);// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setNavigationIcon(icon);
        setSupportActionBar(toolbar);
//        int currentSkinType = SkinManager.getCurrentSkinType(this);
//        if (SkinManager.THEME_DAY == currentSkinType) {
//            toolbar.setTitleTextColor(getResources().getColor(R.color.gank_text1_color));
//            toolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
//        } else {
//            toolbar.setTitleTextColor(getResources().getColor(R.color.gank_text1_color_night));
//            toolbar.setPopupTheme(R.style.ToolBarPopupThemeNight);
//        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_activity_left_in_slow, R.anim.anim_activity_right_out);
    }
}
