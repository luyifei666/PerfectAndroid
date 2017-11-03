package maitong.com.perfect.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.List;

import maitong.com.perfect.R;
import maitong.com.perfect.adapter.ContactAdapter;
import maitong.com.perfect.base.BaseFragment;
import maitong.com.perfect.bean.Contact;
import maitong.com.perfect.custorm.PopWindow;
import maitong.com.perfect.custorm.SideBar;
import maitong.com.perfect.utils.HanziToPinyin;
import maitong.com.perfect.utils.MySnackbar;

/**
 * Created by tony on 15/8/17.
 */
public class ContactsFragment extends BaseFragment implements SideBar
        .OnTouchingLetterChangedListener, TextWatcher {

    public final static String HAS_NEW_NEWS = "HAS_NEW_NEWS";
    public final static String UPDATE_NEWS_DOT = "UPDATE_NEWS_DOT";
    private static final int REFRESH_COMPLETE = 0X110;
    private SVProgressHUD mSVProgressHUD;

    private Context context;

    private ListView mListView;
    private TextView mFooterView;

    private KJHttp kjh = null;
    private ArrayList<Contact> datas;
    private ContactAdapter mAdapter;

    private static ContactsFragment sFragment;

    public static ContactsFragment newInstance() {
        if (sFragment == null) {
            sFragment = new ContactsFragment();
        }
        return sFragment;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    if (mSVProgressHUD.isShowing()) {
                        mSVProgressHUD.dismiss();
                    }
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contextView = inflater.inflate(R.layout.fragment_contacts, container, false);
        context = getActivity();
        //判断是否有权限 6.0
//        requestSomePermission();
        initUI(contextView);
        initData();
        mSVProgressHUD = new SVProgressHUD(context);
        mHandler.sendEmptyMessageAtTime(REFRESH_COMPLETE, 2500);
        return contextView;
    }

    @Override
    protected void initUI(View view) {
//        showTipsView(view,false,true,"抱歉,没有数据了..");
        updateSubTitleBar(view, "联系人", -1, null);
        setleftTitleBarBtnVisable(view, View.GONE);
        mListView = view.findViewById(R.id.school_friend_member);
        SideBar mSideBar = (SideBar) view.findViewById(R.id.school_friend_sidrbar);
        TextView mDialog = (TextView) view.findViewById(R.id.school_friend_dialog);

        mSideBar.setTextView(mDialog);
        mSideBar.setOnTouchingLetterChangedListener(this);
//        mSearchInput.addTextChangedListener(this);

        // 给listView设置adapter
        mFooterView = (TextView) View.inflate(context, R.layout.item_list_contact_count, null);
        mListView.addFooterView(mFooterView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (datas.size() != 0 && datas.size() != i) {
                    String number = datas.get(i).getPhonenum();
                    //用intent启动拨打电话
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void initData() {
        datas = new ArrayList<>();
        queryContactPhoneNumber();
        mFooterView.setText(datas.size() + "位联系人");
        mAdapter = new ContactAdapter(mListView, datas);
        Log.e("lyf", "datas = " + datas.size());
        mListView.setAdapter(mAdapter);
    }

    private void queryContactPhoneNumber() {
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, null, null, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            // 取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name = cursor.getString(nameFieldColumnIndex);
            String number = cursor.getString(numberFieldColumnIndex);
            Contact data = new Contact();
            data.setName(name);
            data.setPhonenum(number);
            data.setPinyin(HanziToPinyin.getPinYin(data.getName()));
            datas.add(data);
            Log.e("lyf", "name = " + name + ", num = " + number + ",datas = " + datas.size());
        }
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int position = 0;
        // 该字母首次出现的位置
        if (mAdapter != null) {
            position = mAdapter.getPositionForSection(s.charAt(0));
        }
        if (position != -1) {
            mListView.setSelection(position);
        } else if (s.contains("#")) {
            mListView.setSelection(0);
        }
    }

    @Override
    public void onResume() {
        mSVProgressHUD = new SVProgressHUD(context);
        mHandler.sendEmptyMessageAtTime(REFRESH_COMPLETE, 2500);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop() {
        super.onStop();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


//    private void requestSomePermission() {
//
//        // 先判断是否有权限。
//        if (!AndPermission.hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
//                !AndPermission.hasPermission(context, Manifest.permission.READ_CONTACTS) ||
//                !AndPermission.hasPermission(context, Manifest.permission.WRITE_CONTACTS) ||
//                !AndPermission.hasPermission(context, Manifest.permission.CALL_PHONE)
//                ) {
//            // 申请权限。
//            AndPermission.with(getActivity())
//                    .requestCode(100)
//                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.CALL_PHONE)
//                    .send();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
//        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
//    }
//
//    private PermissionListener listener = new PermissionListener() {
//        @Override
//        public void onSucceed(int requestCode, List<String> grantedPermissions) {
//            Log.e("lyf", "权限onSucceed:" + grantedPermissions.toString());
//            MySnackbar.makeSnackBarBlack(mListView, "权限申请成功");
//            if (grantedPermissions.contains("android.permission.ACCESS_FINE_LOCATION")) {
//                Log.e("lyf", "读取联系人申请成功");
////                mainPresenter.getLocationInfo();
////                queryContactPhoneNumber();
////                mAdapter.notifyDataSetChanged();
//            }
//        }
//
//        @Override
//        public void onFailed(int requestCode, List<String> deniedPermissions) {
//            Log.i("lyf", "权限onFailed:" + deniedPermissions.toString());
//            // 权限申请失败回调。
//            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
//            if (AndPermission.hasAlwaysDeniedPermission(getActivity(), deniedPermissions)) {
//                // 第二种：用自定义的提示语。
//                AndPermission.defaultSettingDialog(getActivity(), 300)
//                        .setTitle("权限申请失败")
//                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                        .setPositiveButton("好，去设置")
//                        .show();
//            }
//        }
//    };

}
