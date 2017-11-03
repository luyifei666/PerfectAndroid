package maitong.com.perfect.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import maitong.com.perfect.R;
import maitong.com.perfect.adapter.AdministrarPageAdapter;
import maitong.com.perfect.adapter.MainTabPageAdapter;
import maitong.com.perfect.base.BaseFragmentActivity;
import maitong.com.perfect.custorm.MainBottomBar;
import maitong.com.perfect.fragment.ClassTableFragment;
import maitong.com.perfect.fragment.ContactsFragment;
import maitong.com.perfect.fragment.FamilyFragment;
import maitong.com.perfect.fragment.MineFragment;
import maitong.com.perfect.fragment.SchoolFragment;
import maitong.com.perfect.utils.MySnackbar;

/**
 * tab 主页
 *
 * @author tony
 * @since 2014-12-22
 */
public class MainTabActivity extends BaseFragmentActivity {


    public static final String ACTION_TAB_NEW = "ACTION_TAB_NEW";
    public static final String ACTION_NEED_CHECK_LOGIN = "ACTION_NEED_CHECK_LOGIN";
    @Bind(R.id.tv_main)
    TextView mTvMain;
    private MainTabPageAdapter adapter;
    //    private CustomViewPager pager;
    private ViewPager pager;
    private MainBottomBar mainBottomBar;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //主页开启时不使用动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_main_tab);
        ButterKnife.bind(this);
//        isRunning = true;

        //判断是否有权限 6.0
        requestSomePermission();
        
        initView();
        initData();
//        registerNewReceivers();
    }

    @Override
    protected void onResume() {
        //无效uid时，跳转到登陆和注册页面
//        if (ConfigDao.getInstance().getUID() <= 0) {
//            AccountController.gotoReLoginActivity();
//        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        pager = findViewById(R.id.pager);
        // 实例化TabPageIndicator然后设置ViewPager与之关联
        mainBottomBar = findViewById(R.id.bar);

    }

    private void initData() {
        fragments.add(ClassTableFragment.newInstance());
        fragments.add(FamilyFragment.newInstance());
        fragments.add(SchoolFragment.newInstance());
        fragments.add(ContactsFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        final AdministrarPageAdapter adapter = new AdministrarPageAdapter(this.getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
//        pager.setCurrentItem(0);
        mainBottomBar.setViewPager(pager);
    }

    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(@SuppressWarnings("NullableProblems") Message msg) {
            super.dispatchMessage(msg);
            if (mainBottomBar != null) {
                mainBottomBar.notifyDataSetChanged();
            }
        }
    };


    /**
     * 点击2次back键退出
     */

    @Override
    public void onBackPressed() {
        if (!isNeedQuit()) {
            return;
        } else {
//            AccountController.finishAll();
            finish();
        }
        super.onBackPressed();
    }

    /**
     * 两次点击退出的时间间隔
     */
    private static final long QUIT_DUR = 3000;
    /**
     * 第一次点击退出时候的时间
     */
    private long quitTime = 0;

    private boolean isNeedQuit() {
        if (System.currentTimeMillis() - quitTime < QUIT_DUR) {
            return true;
        } else {
            quitTime = System.currentTimeMillis();
//            ToastUtil.showShortToast("再按一次退出程序");
            MySnackbar.makeSnackBarOrange(mTvMain,"再按一次退出程序");
            return false;
        }
    }


    private void requestSomePermission() {

        // 先判断是否有权限。
        if (!AndPermission.hasPermission(MainTabActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                !AndPermission.hasPermission(MainTabActivity.this, Manifest.permission.READ_CONTACTS) ||
                !AndPermission.hasPermission(MainTabActivity.this, Manifest.permission.WRITE_CONTACTS) ||
                !AndPermission.hasPermission(MainTabActivity.this, Manifest.permission.CALL_PHONE)
                ) {
            // 申请权限。
            AndPermission.with(MainTabActivity.this)
                    .requestCode(100)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.CALL_PHONE)
                    .send();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            Log.e("lyf", "权限onSucceed:" + grantedPermissions.toString());
            MySnackbar.makeSnackBarBlack(pager, "权限申请成功");
            if (grantedPermissions.contains("android.permission.ACCESS_FINE_LOCATION")) {
                Log.e("lyf", "读取联系人申请成功");
//                mainPresenter.getLocationInfo();
//                queryContactPhoneNumber();
//                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            Log.i("lyf", "权限onFailed:" + deniedPermissions.toString());
            // 权限申请失败回调。
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(MainTabActivity.this, deniedPermissions)) {
                // 第二种：用自定义的提示语。
                AndPermission.defaultSettingDialog(MainTabActivity.this, 300)
                        .setTitle("权限申请失败")
                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        }
    };

//    private NewBroadcastReceiver newReceiver;

//    void registerNewReceivers() {
//        IntentFilter orderFilter = new IntentFilter();
//        orderFilter.addAction(ACTION_TAB_NEW);
//        orderFilter.addAction(AboutActivity.CHECK_UPDATE_OVER);
//        orderFilter.addAction(CircleFragment.ACTION_CIRCLE_MESSAGE_CHANGED);
//        newReceiver = new NewBroadcastReceiver();
//        LocalBroadcastManager.getInstance(this).registerReceiver(newReceiver, orderFilter);
//    }
//
//    void unregisterNewReceivers() {
//        if (newReceiver != null) {
//            LocalBroadcastManager.getInstance(this).unregisterReceiver(newReceiver);
//        }
//    }
//
//    private class NewBroadcastReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(MainTabActivity.this MainTabActivity.this, Intent intent) {
//            handler.sendEmptyMessage(0);
//        }
//    }


    @Override
    public void finish() {
        super.finish();
        //主页关闭时使用特殊动画
        overridePendingTransition(R.anim.anim_activity_stay, R.anim.anim_activity_main_out);

    }

}
