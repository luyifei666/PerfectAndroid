package maitong.com.perfect.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;

import maitong.com.perfect.R;
import maitong.com.perfect.utils.StatusBarUtil;


/**
 * 通用FragmentActivity基类 用来处理页面全局事件
 *
 * @author Tony
 */
public class BaseFragmentActivity extends FragmentActivity {

	private SVProgressHUD mSVProgressHUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_left_out_slow);
        super.onCreate(savedInstanceState);
		initStatus();
		initDialog();
		registerFinishAllReceivers();
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
		StatusBarUtil.setColor(this, getResources().getColor(R.color.common), 60);
//		} else {
//			StatusBarUtil.setColor(this, getResources().getColor(R.color.main_color_night), 60);
//		}
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
		unregisterFinishAllReceivers();
    }

    /**
	 * 
	 * @param title
	 *            居中文字
	 * @param rightIconResourceId
	 *            右边icon的资源id 为-1隐藏组件
	 * @param rightClickListener
	 */
	protected void updateSubTitleBar(final String title,
			int rightIconResourceId, OnClickListener rightClickListener) {
		TextView tv = findViewById(R.id.sub_title_bar_tv_title);
		if (tv != null) {
			if (title != null && title.length() > 12) {
				tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.7f);
			}
			tv.setText(title);
		}
		RelativeLayout leftLL = findViewById(R.id.sub_title_bar_ll_left);
		RelativeLayout rightLL = findViewById(R.id.sub_title_bar_ll_right);
		ImageView rightIcon = findViewById(R.id.sub_title_bar_img_right);
		if (rightIcon != null) {
			if (rightIconResourceId != -1) {
				rightIcon.setBackgroundResource(rightIconResourceId);
				rightLL.setVisibility(View.VISIBLE);
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
					hideSoftKeyBoard(BaseFragmentActivity.this);
					finish();
				}
			});
		}
		if (rightLL != null) {
			rightLL.setOnClickListener(rightClickListener);
		}
	}

	public void setSubTitleBarBtn(boolean leftClickable, boolean rightClickable) {
		RelativeLayout leftIcon = findViewById(R.id.sub_title_bar_ll_left);
		RelativeLayout rightIcon = findViewById(R.id.sub_title_bar_ll_right);
		if (leftIcon != null) {
			leftIcon.setEnabled(leftClickable);
		}
		if (rightIcon != null) {
			rightIcon.setEnabled(rightClickable);
		}
	}

	/**
	 * 设置title Bar 的左右按钮是否可见。
	 * 
	 * @param leftVisible
	 */
	public void setleftTitleBarBtnVisable(int leftVisible) {
		RelativeLayout leftIcon = findViewById(R.id.sub_title_bar_ll_left);
		if (leftIcon != null)
			leftIcon.setVisibility(leftVisible);
	}

	/**
	 * 设置title Bar 的左右按钮是否可见。
	 * 
	 * @param rightVisible
	 */
	public void setRightTitleBarBtnVisable(int rightVisible) {
		RelativeLayout rightIcon = findViewById(R.id.sub_title_bar_ll_right);
		if (rightIcon != null)
			rightIcon.setVisibility(rightVisible);
	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideSoftKeyBoard(Activity activity) {
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
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null && imm.isActive()) {
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		}
	}

	private FinishAllBroadcastReceiver finishAllReceiver = null;

	private void registerFinishAllReceivers() {
		IntentFilter orderFilter = new IntentFilter();
		orderFilter.addAction(BaseActivity.FINISH_ALL);
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

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.anim_activity_left_in_slow, R.anim.anim_activity_right_out);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}
