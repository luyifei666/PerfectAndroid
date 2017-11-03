package maitong.com.perfect;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.URLDecoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maitong.com.perfect.activity.MainTabActivity;
import maitong.com.perfect.base.BaseActivity;
import maitong.com.perfect.commens.ActivityManager;
import maitong.com.perfect.utils.AppNetConfig;
import maitong.com.perfect.utils.AppSharePreferenceMgr;
import maitong.com.perfect.utils.KeyboardUtils;
import okhttp3.Call;

public class MainActivity extends BaseActivity {
    @Bind(R.id.blank)
    LinearLayout mBlank;
    //申明控件对象用户名
    private EditText username;
    //申明控件用户密码
    private EditText password;
    private Context context;
    private SVProgressHUD mSVProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        //CrashHandler.getInstance().init();//设置全局异常
        //将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().add(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        String uname = (String) AppSharePreferenceMgr.get(context, "USER", "");
        String pwd = (String) AppSharePreferenceMgr.get(context, "PWD", "");
        username.setText(uname);
        password.setText(pwd);
        if (!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(pwd)) {
            Intent intent = new Intent(MainActivity.this, MainTabActivity.class);
            startActivity(intent);
        }
        //finish();

    }

    /**
     * 登录
     *
     * @param v
     */
    public void login(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                showProgressDialog();
                //获取用户
                final String userName = username.getText().toString();
                //获取密码
                final String userPassword = password.getText().toString();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(MainActivity.this, "用户名或者密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    mSVProgressHUD = new SVProgressHUD(MainActivity.this);
                    loginByGet(userName, userPassword);
                }
                break;

            default:
                break;
        }

    }

    /**
     * 通过GET发送请求
     *
     * @param username
     * @param password
     * @return
     */
    public void loginByGet(final String username, final String password) {
//        final ProgressDialog dialog = ProgressDialog.show(this, "登录中", "正在登录中...");
        try {
            String url = AppNetConfig.LOGIN_URL;
            OkHttpUtils
                    .post()
                    .url(url)
                    .addParams("username", username)
                    .addParams("password", password)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onResponse(String response, int id) {
                            // 第三步，使用getEntity方法活得返回结果
                            String result = URLDecoder.decode(response);
                            Log.e("lyf","result = " + result);
                            JSONObject obj = JSON.parseObject(result);
                            String flag = obj.get("flag").toString();
                            if (flag.equals("true")) {
                                String role = obj.get("role").toString();//角色
                                String schoolId = obj.get("schoolId").toString();//年级id
                                String classroomId = obj.get("classroomId").toString();//班级id
                                String schoolName = obj.get("schoolName").toString();//学校名称
                                String gradeName = obj.get("gradeName").toString();//年级名称
                                String classroomName = obj.get("classroomName").toString();//班级名称
                                String userNickname = obj.get("userNickname").toString();//用户昵称
                                AppSharePreferenceMgr.put(context, "USER", username);
                                AppSharePreferenceMgr.put(context, "PWD", password);
                                AppSharePreferenceMgr.put(context, "ROLE", role);
                                AppSharePreferenceMgr.put(context, "SCHOOLID", schoolId);
                                AppSharePreferenceMgr.put(context, "CLASSROOMID", classroomId);
                                AppSharePreferenceMgr.put(context, "SCHOOLNAME", schoolName);
                                AppSharePreferenceMgr.put(context, "GRADENAME", gradeName);
                                AppSharePreferenceMgr.put(context, "CLASSROOMNAME", classroomName);
                                AppSharePreferenceMgr.put(context, "USERNICKNAME", userNickname);
                                AppSharePreferenceMgr.put(context, "schoolName", schoolName);
                                Intent intent = new Intent(MainActivity.this, MainTabActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(MainActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
                        }
                    });


//			String url = AppNetConfig.LOGIN_URL;
//			AsyncHttpClient client = new AsyncHttpClient();
//			RequestParams params = new RequestParams();
//			params.put("username", username);
//			params.put("password", password);
//			client.post(url, params, new AsyncHttpResponseHandler(){
//				@Override
//				public void onSuccess(String content) {
//					// 第三步，使用getEntity方法活得返回结果
//					String result = URLDecoder.decode(content);
//					JSONObject obj = JSON.parseObject(result);
//					String flag = obj.get("flag").toString();
//					if(flag.equals("true")){
//						String role = obj.get("role").toString();
//						String schoolId = obj.get("schoolId").toString();
//						String classroomId = obj.get("classroomId").toString();
//						AppSharePreferenceMgr.put(context,"USER",username);
//						AppSharePreferenceMgr.put(context,"PWD",password);
//						AppSharePreferenceMgr.put(context,"ROLE",role);
//						AppSharePreferenceMgr.put(context,"SCHOOLID",schoolId);
//						AppSharePreferenceMgr.put(context,"CLASSROOMID",classroomId);
//						Intent intent = new Intent(MainActivity.this, IndexBottomTabActivity.class);
//						startActivity(intent);
//					}else{
//						Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
//					}
//				}
//				@Override
//				public void onFailure(Throwable error) {
//					Toast.makeText(MainActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
//				}
//			});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    dissmissProgressDialog();
                    Looper.loop();
                }
            }).start();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setMessage("您确定要退出吗？")
                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("再看看", null)
                    .show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.blank)
    public void onViewClicked() {
        KeyboardUtils.hideSoftInput(this);
    }
}
