package maitong.com.perfect.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;

import java.lang.reflect.Field;

import maitong.com.perfect.MainApplication;
import maitong.com.perfect.R;
import maitong.com.perfect.utils.StatusBarUtil;
import maitong.com.perfect.utils.StringUtil;

public abstract class BaseFragment extends Fragment {

    protected abstract void initUI(View view);

    protected abstract void initData();

    private SVProgressHUD mSVProgressHUD;
    /**
     * 显示提示页面
     *
     * @param upImage 右上角箭头是否显示
     * @param icon    中心图标是否显示
     * @param notice  提示文案
     */
    public void showTipsView(View view, boolean upImage, boolean icon, String notice) {
        if (view == null) {
            return;
        }
        RelativeLayout rl_tips = view.findViewById(R.id.rl_tips);
        if (rl_tips != null) {
            rl_tips.setVisibility(View.VISIBLE);
            if (upImage) {
                view.findViewById(R.id.iv_tips_up).setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.iv_tips_up).setVisibility(View.GONE);
            }
            if (icon) {
                view.findViewById(R.id.iv_tips_icon).setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.iv_tips_icon).setVisibility(View.GONE);
            }
            TextView textView = view.findViewById(R.id.tv_tips_text);
            if (textView != null) {
                textView.setText(notice);
            }
        }
    }

    /**
     * 隐藏提示画面
     */
    public void hideTipsView(View view) {
        if (view == null) {
            return;
        }
        RelativeLayout rl_tips = view.findViewById(R.id.rl_tips);
        if (rl_tips != null) {
            rl_tips.setVisibility(View.GONE);
        }
    }


    /**
     * @param title               居中文字
     * @param rightIconResourceId 右边icon的资源id 为-1隐藏组件
     * @param rightClickListener  右侧点击事件监听
     */
    protected void updateSubTitleBar(View view, final String title,
                                     int rightIconResourceId, View.OnClickListener rightClickListener) {
        TextView tv = view.findViewById(R.id.sub_title_bar_tv_title);
        if (tv != null) {
            if (title != null && title.length() > 12) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.7f);
            }
            tv.setText(title);
        }
        RelativeLayout leftLL = view.findViewById(R.id.sub_title_bar_ll_left);
        RelativeLayout rightLL = view.findViewById(R.id.sub_title_bar_ll_right);
        ImageView rightIcon = view.findViewById(R.id.sub_title_bar_img_right);
        if (rightIcon != null) {
            if (rightIconResourceId != -1) {
                rightIcon.setBackgroundResource(rightIconResourceId);
            } else {
                if (rightLL != null) {
                    rightLL.setVisibility(View.INVISIBLE);
                }
            }
        }
        if (leftLL != null) {
            leftLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
        if (rightLL != null) {
            rightLL.setOnClickListener(rightClickListener);
        }
    }

    /**
     * 设置右侧文字按钮形式的标题栏
     *
     * @param title              标题文字
     * @param rightText          右侧按钮文字
     * @param rightClickListener 右侧点击监听
     */
    protected void updateSubTitleTextBar(View view, final String title,
                                         String rightText, View.OnClickListener rightClickListener) {
        TextView tv = view.findViewById(R.id.sub_title_bar_tv_title);
        if (tv != null) {
            if (title != null && title.length() > 12) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.7f);
            }
            tv.setText(title);
        }
        RelativeLayout leftLL = view.findViewById(R.id.sub_title_bar_ll_left);
        RelativeLayout rightLL = view.findViewById(R.id.sub_title_bar_ll_right);
        rightLL.setVisibility(View.GONE);

        LinearLayout rightTextLL = view.findViewById(R.id.sub_title_bar_ll_btn_right);
        TextView rightBtn = view.findViewById(R.id.sub_title_bar_btn_right);
        if (!StringUtil.isEmpty(rightText)) {
            rightBtn.setText(rightText);
            rightTextLL.setVisibility(View.VISIBLE);
        } else {
            if (rightTextLL != null) {
                rightTextLL.setVisibility(View.INVISIBLE);
            }
        }
        if (leftLL != null) {
            leftLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
        rightTextLL.setOnClickListener(rightClickListener);
    }

    /**
     * 只更新标题文字
     *
     * @param title 标题文字
     */
    protected void updateTitleText(View view, String title) {
        TextView tv = view.findViewById(R.id.sub_title_bar_tv_title);
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
    protected void updateRightTextBtn(View view, boolean clickable) {
        LinearLayout rightTextLL = view.findViewById(R.id.sub_title_bar_ll_btn_right);
        if (rightTextLL != null) {
            rightTextLL.setEnabled(clickable);
        }
    }

    /**
     * 设置右侧按钮的文字颜色
     *
     * @param colorId 已设置的颜色值
     */
    protected void updateRightTextColor(View view, int colorId) {
        TextView rightBtn = view.findViewById(R.id.sub_title_bar_btn_right);
        if (rightBtn != null) {
            rightBtn.setTextColor(getResources().getColor(colorId));
        }
    }

    public void setSubTitleBarBtn(View view, boolean leftClickable,
                                  boolean rightClickable) {
        RelativeLayout leftIcon = view.findViewById(R.id.sub_title_bar_ll_left);
        RelativeLayout rightIcon = view.findViewById(R.id.sub_title_bar_ll_right);
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
     * @param leftVisible 左侧按钮可见
     */
    public void setleftTitleBarBtnVisable(View view, int leftVisible) {
        RelativeLayout leftIcon = view.findViewById(R.id.sub_title_bar_ll_left);
        if (leftIcon != null)
            leftIcon.setVisibility(leftVisible);
    }

    /**
     * 设置title Bar 的右侧红点是否可见。
     *
     * @param leftVisible 左侧按钮可见
     */
    public void setIconNewVisible(View view, int leftVisible) {
        if (view == null) {
            return;
        }
        ImageView iv_new = view.findViewById(R.id.iv_title_right_new);
        if (iv_new != null)
            iv_new.setVisibility(leftVisible);
    }

    /**
     * 设置title Bar 的左右按钮是否可见。
     *
     * @param rightVisible 右侧按钮可见
     */
    public void setRightTitleBarBtnVisable(View view, int rightVisible) {
        RelativeLayout rightIcon = view.findViewById(R.id.sub_title_bar_ll_right);
        if (rightIcon != null)
            rightIcon.setVisibility(rightVisible);
    }

    /**
     * 设置左侧按钮点击事件
     *
     * @param leftListener 左侧监听
     */
    public void setLeftTitleBarBtnListener(View view, View.OnClickListener leftListener) {
        RelativeLayout leftIcon = view.findViewById(R.id.sub_title_bar_ll_left);
        if (leftIcon != null) {
            leftIcon.setOnClickListener(leftListener);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        registerConnectionState();
        initStatus();
//        initDialog();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initDialog() {
        mSVProgressHUD = new SVProgressHUD(getActivity());
    }

    public void showProgressDialog() {
        mSVProgressHUD = new SVProgressHUD(getActivity());
//        dissmissProgressDialog();
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
        }else {
            mSVProgressHUD.dismiss();
        }
    }

    private void initStatus() {
        //设置状态栏的颜色
//		int currentSkinType = SkinManager.getCurrentSkinType(this);
//		if (SkinManager.THEME_DAY == currentSkinType) {
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.blue_1_205791), 60);
//		} else {
//			StatusBarUtil.setColor(this, getResources().getColor(R.color.main_color_night), 60);
//		}
    }

    @Override
    public void onDestroy() {
//        unregisterConnectionState();
        super.onDestroy();
    }

    private ConnectionStateChangedReceiver connectionStateChangedReceiver;

    private void registerConnectionState() {
        connectionStateChangedReceiver = new ConnectionStateChangedReceiver();
        IntentFilter orderFilter = new IntentFilter();
        orderFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(connectionStateChangedReceiver, orderFilter);
    }

//    private void unregisterConnectionState() {
//        if (connectionStateChangedReceiver != null) {
//            getActivity().unregisterReceiver(connectionStateChangedReceiver);
//        }
//    }

    public View contextView;

    public void setContextView(View view) {
        this.contextView = view;
    }

    private boolean hideNetErrorTips = false;

    public void setHideNetErrorTips(boolean hideNetErrorTips) {
        this.hideNetErrorTips = hideNetErrorTips;
    }

    public class ConnectionStateChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (contextView == null || hideNetErrorTips) {
                return;
            }
            boolean isBreak = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            LinearLayout ll_network_error = contextView.findViewById(R.id.ll_network_error);
            if (isBreak) {
                if (ll_network_error != null) {
                    ll_network_error.setVisibility(View.VISIBLE);
                }
            } else {
                if (ll_network_error != null) {
                    ll_network_error.setVisibility(View.GONE);
                }
                //检查百度推送key是否已经获取
//                ApiKeyController.getInstance().checkBaiduApiKey();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //
    @Override
    public void onDestroyView() {
// TODO Auto-generated method stub
        super.onDestroyView();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("test", "test");
        super.onSaveInstanceState(outState);
    }
}
