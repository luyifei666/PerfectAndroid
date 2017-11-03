package maitong.com.perfect.fragment;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.List;

import maitong.com.perfect.MainApplication;
import maitong.com.perfect.R;
import maitong.com.perfect.base.BaseFragment;
import maitong.com.perfect.bean.Course;
import maitong.com.perfect.utils.AppNetConfig;
import maitong.com.perfect.utils.AppSharePreferenceMgr;
import okhttp3.Call;

/**
 * Created by tony on 15/8/17.
 */
public class ClassTableFragment extends BaseFragment {

    private MaterialDialog dialogLoading;
    private SVProgressHUD mSVProgressHUD;
    public final static String HAS_NEW_NEWS = "HAS_NEW_NEWS";
    public final static String UPDATE_NEWS_DOT = "UPDATE_NEWS_DOT";

    private Context mContext;
    private TextView week01_course1,week01_course2,week01_course3,week01_course4,week01_course5,week01_course6,week01_course7,week01_course8,week01_course9;
    private TextView week02_course1,week02_course2,week02_course3,week02_course4,week02_course5,week02_course6,week02_course7,week02_course8,week02_course9;
    private TextView week03_course1,week03_course2,week03_course3,week03_course4,week03_course5,week03_course6,week03_course7,week03_course8,week03_course9;
    private TextView week04_course1,week04_course2,week04_course3,week04_course4,week04_course5,week04_course6,week04_course7,week04_course8,week04_course9;
    private TextView week05_course1,week05_course2,week05_course3,week05_course4,week05_course5,week05_course6,week05_course7,week05_course8,week05_course9;


    private static ClassTableFragment sFragment;

    public static ClassTableFragment newInstance() {
        if (sFragment == null) {
            sFragment = new ClassTableFragment();
        }
        return sFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contextView = inflater.inflate(R.layout.fragment_classtable, container, false);
        mContext = getActivity();
//        dialogLoading= new MaterialDialog.Builder(mContext)
//                .content("请稍等......")
//                .canceledOnTouchOutside(false)
//                .cancelable(false)
//                .show();
//        showProgressDialog();
        mSVProgressHUD = new SVProgressHUD(getActivity());
        mSVProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.Black);
        mSVProgressHUD.show();
        initUI(contextView);
        initData();
        return contextView;
    }

    @Override
    protected void initUI(View view) {
        String schoolName = (String) AppSharePreferenceMgr.get(getContext(), "SCHOOLNAME", "");
        updateSubTitleBar(view, schoolName, -1, null);
        setleftTitleBarBtnVisable(view, View.GONE);
    }

    @Override
    protected void initData() {
        String url = AppNetConfig.GET_COURSETABLEDATA_URL;
        String username = (String) AppSharePreferenceMgr.get(getActivity(),"USER","");
        String password = (String) AppSharePreferenceMgr.get(getActivity(),"PWD","");
        OkHttpUtils
                .get()
                .url(url)
                .addParams("username", username)
                .addParams("password", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(final String response, int id) {
                        Log.e("lyf","response = "+response);
                        Log.e("lyf","URLDecoder.decode(response) = "+ URLDecoder.decode(response));
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String role = (String) AppSharePreferenceMgr.get(getActivity(),"ROLE","");
                                    String datas = URLDecoder.decode(response);
                                    if(!TextUtils.isEmpty(datas)){
                                        List<Course> list = com.alibaba.fastjson.JSONArray.parseArray(datas, Course.class);
                                        if(list!=null&&list.size()>0){
                                            if(role.equals("student")){
                                                for(int i = 0;i<list.size();i++){
                                                    switch (Integer.parseInt(list.get(i).getIndex())) {
                                                        case 1:
                                                            week01_course1 = (TextView) getActivity().findViewById(R.id.week01_course1);
                                                            week01_course1.setText(list.get(i).getCourse());
                                                            break;
                                                        case 2:
                                                            week02_course1 = (TextView) getActivity().findViewById(R.id.week02_course1);
                                                            week02_course1.setText(list.get(i).getCourse());
                                                            break;
                                                        case 3:
                                                            week03_course1 = (TextView) getActivity().findViewById(R.id.week03_course1);
                                                            week03_course1.setText(list.get(i).getCourse());
                                                            break;
                                                        case 4:
                                                            week04_course1 = (TextView) getActivity().findViewById(R.id.week04_course1);
                                                            week04_course1.setText(list.get(i).getCourse());
                                                            break;
                                                        case 5:
                                                            week05_course1 = (TextView) getActivity().findViewById(R.id.week05_course1);
                                                            week05_course1.setText(list.get(i).getCourse());
                                                            break;
                                                        case 6:
                                                            week01_course2 = (TextView) getActivity().findViewById(R.id.week01_course2);
                                                            week01_course2.setText(list.get(i).getCourse());
                                                            break;
                                                        case 7:
                                                            week02_course2 = (TextView) getActivity().findViewById(R.id.week02_course2);
                                                            week02_course2.setText(list.get(i).getCourse());
                                                            break;
                                                        case 8:
                                                            week03_course2 = (TextView) getActivity().findViewById(R.id.week03_course2);
                                                            week03_course2.setText(list.get(i).getCourse());
                                                            break;
                                                        case 9:
                                                            week04_course2 = (TextView) getActivity().findViewById(R.id.week04_course2);
                                                            week04_course2.setText(list.get(i).getCourse());
                                                            break;
                                                        case 10:
                                                            week05_course2 = (TextView) getActivity().findViewById(R.id.week05_course2);
                                                            week05_course2.setText(list.get(i).getCourse());
                                                            break;
                                                        case 11:
                                                            week01_course3 = (TextView) getActivity().findViewById(R.id.week01_course3);
                                                            week01_course3.setText(list.get(i).getCourse());
                                                            break;
                                                        case 12:
                                                            week02_course3 = (TextView) getActivity().findViewById(R.id.week02_course3);
                                                            week02_course3.setText(list.get(i).getCourse());
                                                            break;
                                                        case 13:
                                                            week03_course3 = (TextView) getActivity().findViewById(R.id.week03_course3);
                                                            week03_course3.setText(list.get(i).getCourse());
                                                            break;
                                                        case 14:
                                                            week04_course3 = (TextView) getActivity().findViewById(R.id.week04_course3);
                                                            week04_course3.setText(list.get(i).getCourse());
                                                            break;
                                                        case 15:
                                                            week05_course3 = (TextView) getActivity().findViewById(R.id.week05_course3);
                                                            week05_course3.setText(list.get(i).getCourse());
                                                            break;
                                                        case 16:
                                                            week01_course4 = (TextView) getActivity().findViewById(R.id.week01_course4);
                                                            week01_course4.setText(list.get(i).getCourse());
                                                            break;
                                                        case 17:
                                                            week02_course4 = (TextView) getActivity().findViewById(R.id.week02_course4);
                                                            week02_course4.setText(list.get(i).getCourse());
                                                            break;
                                                        case 18:
                                                            week03_course4 = (TextView) getActivity().findViewById(R.id.week03_course4);
                                                            week03_course4.setText(list.get(i).getCourse());
                                                            break;
                                                        case 19:
                                                            week04_course4 = (TextView) getActivity().findViewById(R.id.week04_course4);
                                                            week04_course4.setText(list.get(i).getCourse());
                                                            break;
                                                        case 20:
                                                            week05_course4 = (TextView) getActivity().findViewById(R.id.week05_course4);
                                                            week05_course4.setText(list.get(i).getCourse());
                                                            break;
                                                        case 21:
                                                            week01_course5 = (TextView) getActivity().findViewById(R.id.week01_course5);
                                                            week01_course5.setText(list.get(i).getCourse());
                                                            break;
                                                        case 22:
                                                            week02_course5 = (TextView) getActivity().findViewById(R.id.week02_course5);
                                                            week02_course5.setText(list.get(i).getCourse());
                                                            break;
                                                        case 23:
                                                            week03_course5 = (TextView) getActivity().findViewById(R.id.week03_course5);
                                                            week03_course5.setText(list.get(i).getCourse());
                                                            break;
                                                        case 24:
                                                            week04_course5 = (TextView) getActivity().findViewById(R.id.week04_course5);
                                                            week04_course5.setText(list.get(i).getCourse());
                                                            break;
                                                        case 25:
                                                            week05_course5 = (TextView) getActivity().findViewById(R.id.week05_course5);
                                                            week05_course5.setText(list.get(i).getCourse());
                                                            break;
                                                        case 26:
                                                            week01_course6 = (TextView) getActivity().findViewById(R.id.week01_course6);
                                                            week01_course6.setText(list.get(i).getCourse());
                                                            break;
                                                        case 27:
                                                            week02_course6 = (TextView) getActivity().findViewById(R.id.week02_course6);
                                                            week02_course6.setText(list.get(i).getCourse());
                                                            break;
                                                        case 28:
                                                            week03_course6 = (TextView) getActivity().findViewById(R.id.week03_course6);
                                                            week03_course6.setText(list.get(i).getCourse());
                                                            break;
                                                        case 29:
                                                            week04_course6 = (TextView) getActivity().findViewById(R.id.week04_course6);
                                                            week04_course6.setText(list.get(i).getCourse());
                                                            break;
                                                        case 30:
                                                            week05_course6 = (TextView) getActivity().findViewById(R.id.week05_course6);
                                                            week05_course6.setText(list.get(i).getCourse());
                                                            break;
                                                        case 31:
                                                            week01_course7 = (TextView) getActivity().findViewById(R.id.week01_course7);
                                                            week01_course7.setText(list.get(i).getCourse());
                                                            break;
                                                        case 32:
                                                            week02_course7 = (TextView) getActivity().findViewById(R.id.week02_course7);
                                                            week02_course7.setText(list.get(i).getCourse());
                                                            break;
                                                        case 33:
                                                            week03_course7 = (TextView) getActivity().findViewById(R.id.week03_course7);
                                                            week03_course7.setText(list.get(i).getCourse());
                                                            break;
                                                        case 34:
                                                            week04_course7 = (TextView) getActivity().findViewById(R.id.week04_course7);
                                                            week04_course7.setText(list.get(i).getCourse());
                                                            break;
                                                        case 35:
                                                            week05_course7 = (TextView) getActivity().findViewById(R.id.week05_course7);
                                                            week05_course7.setText(list.get(i).getCourse());
                                                            break;
                                                        case 36:
                                                            week01_course8 = (TextView) getActivity().findViewById(R.id.week01_course8);
                                                            week01_course8.setText(list.get(i).getCourse());
                                                            break;
                                                        case 37:
                                                            week02_course8 = (TextView) getActivity().findViewById(R.id.week02_course8);
                                                            week02_course8.setText(list.get(i).getCourse());
                                                            break;
                                                        case 38:
                                                            week03_course8 = (TextView) getActivity().findViewById(R.id.week03_course8);
                                                            week03_course8.setText(list.get(i).getCourse());
                                                            break;
                                                        case 39:
                                                            week04_course8 = (TextView) getActivity().findViewById(R.id.week04_course8);
                                                            week04_course8.setText(list.get(i).getCourse());
                                                            break;
                                                        case 40:
                                                            week05_course8 = (TextView) getActivity().findViewById(R.id.week05_course8);
                                                            week05_course8.setText(list.get(i).getCourse());
                                                            break;
                                                        case 41:
                                                            week01_course9 = (TextView) getActivity().findViewById(R.id.week01_course9);
                                                            week01_course9.setText(list.get(i).getCourse());
                                                            break;
                                                        case 42:
                                                            week02_course9 = (TextView) getActivity().findViewById(R.id.week02_course9);
                                                            week02_course9.setText(list.get(i).getCourse());
                                                            break;
                                                        case 43:
                                                            week03_course9 = (TextView) getActivity().findViewById(R.id.week03_course9);
                                                            week03_course9.setText(list.get(i).getCourse());
                                                            break;
                                                        case 44:
                                                            week04_course9 = (TextView) getActivity().findViewById(R.id.week04_course9);
                                                            week04_course9.setText(list.get(i).getCourse());
                                                            break;
                                                        case 45:
                                                            week05_course9 = (TextView) getActivity().findViewById(R.id.week05_course9);
                                                            week05_course9.setText(list.get(i).getCourse());
                                                            int selectColor = getResources().getColor(R.color.courseTextColor);
                                                            week05_course9.setTextColor(selectColor);
                                                            break;

                                                        default:
                                                            break;
                                                    }

                                                }
                                            }else if(role.equals("teacher")){
                                                for(int i = 0;i<list.size();i++){
                                                    switch (Integer.parseInt(list.get(i).getIndex())) {
                                                        case 1:
                                                            week01_course1 = (TextView) getActivity().findViewById(R.id.week01_course1);
                                                            week01_course1.setText(list.get(i).getSchool());
                                                            break;
                                                        case 2:
                                                            week02_course1 = (TextView) getActivity().findViewById(R.id.week02_course1);
                                                            week02_course1.setText(list.get(i).getSchool());
                                                            break;
                                                        case 3:
                                                            week03_course1 = (TextView) getActivity().findViewById(R.id.week03_course1);
                                                            week03_course1.setText(list.get(i).getSchool());
                                                            break;
                                                        case 4:
                                                            week04_course1 = (TextView) getActivity().findViewById(R.id.week04_course1);
                                                            week04_course1.setText(list.get(i).getSchool());
                                                            break;
                                                        case 5:
                                                            week05_course1 = (TextView) getActivity().findViewById(R.id.week05_course1);
                                                            week05_course1.setText(list.get(i).getSchool());
                                                            break;
                                                        case 6:
                                                            week01_course2 = (TextView) getActivity().findViewById(R.id.week01_course2);
                                                            week01_course2.setText(list.get(i).getSchool());
                                                            break;
                                                        case 7:
                                                            week02_course2 = (TextView) getActivity().findViewById(R.id.week02_course2);
                                                            week02_course2.setText(list.get(i).getSchool());
                                                            break;
                                                        case 8:
                                                            week03_course2 = (TextView) getActivity().findViewById(R.id.week03_course2);
                                                            week03_course2.setText(list.get(i).getSchool());
                                                            break;
                                                        case 9:
                                                            week04_course2 = (TextView) getActivity().findViewById(R.id.week04_course2);
                                                            week04_course2.setText(list.get(i).getSchool());
                                                            break;
                                                        case 10:
                                                            week05_course2 = (TextView) getActivity().findViewById(R.id.week05_course2);
                                                            week05_course2.setText(list.get(i).getSchool());
                                                            break;
                                                        case 11:
                                                            week01_course3 = (TextView) getActivity().findViewById(R.id.week01_course3);
                                                            week01_course3.setText(list.get(i).getSchool());
                                                            break;
                                                        case 12:
                                                            week02_course3 = (TextView) getActivity().findViewById(R.id.week02_course3);
                                                            week02_course3.setText(list.get(i).getSchool());
                                                            break;
                                                        case 13:
                                                            week03_course3 = (TextView) getActivity().findViewById(R.id.week03_course3);
                                                            week03_course3.setText(list.get(i).getSchool());
                                                            break;
                                                        case 14:
                                                            week04_course3 = (TextView) getActivity().findViewById(R.id.week04_course3);
                                                            week04_course3.setText(list.get(i).getSchool());
                                                            break;
                                                        case 15:
                                                            week05_course3 = (TextView) getActivity().findViewById(R.id.week05_course3);
                                                            week05_course3.setText(list.get(i).getSchool());
                                                            break;
                                                        case 16:
                                                            week01_course4 = (TextView) getActivity().findViewById(R.id.week01_course4);
                                                            week01_course4.setText(list.get(i).getSchool());
                                                            break;
                                                        case 17:
                                                            week02_course4 = (TextView) getActivity().findViewById(R.id.week02_course4);
                                                            week02_course4.setText(list.get(i).getSchool());
                                                            break;
                                                        case 18:
                                                            week03_course4 = (TextView) getActivity().findViewById(R.id.week03_course4);
                                                            week03_course4.setText(list.get(i).getSchool());
                                                            break;
                                                        case 19:
                                                            week04_course4 = (TextView) getActivity().findViewById(R.id.week04_course4);
                                                            week04_course4.setText(list.get(i).getSchool());
                                                            break;
                                                        case 20:
                                                            week05_course4 = (TextView) getActivity().findViewById(R.id.week05_course4);
                                                            week05_course4.setText(list.get(i).getSchool());
                                                            break;
                                                        case 21:
                                                            week01_course5 = (TextView) getActivity().findViewById(R.id.week01_course5);
                                                            week01_course5.setText(list.get(i).getSchool());
                                                            break;
                                                        case 22:
                                                            week02_course5 = (TextView) getActivity().findViewById(R.id.week02_course5);
                                                            week02_course5.setText(list.get(i).getSchool());
                                                            break;
                                                        case 23:
                                                            week03_course5 = (TextView) getActivity().findViewById(R.id.week03_course5);
                                                            week03_course5.setText(list.get(i).getSchool());
                                                            break;
                                                        case 24:
                                                            week04_course5 = (TextView) getActivity().findViewById(R.id.week04_course5);
                                                            week04_course5.setText(list.get(i).getSchool());
                                                            break;
                                                        case 25:
                                                            week05_course5 = (TextView) getActivity().findViewById(R.id.week05_course5);
                                                            week05_course5.setText(list.get(i).getSchool());
                                                            break;
                                                        case 26:
                                                            week01_course6 = (TextView) getActivity().findViewById(R.id.week01_course6);
                                                            week01_course6.setText(list.get(i).getSchool());
                                                            break;
                                                        case 27:
                                                            week02_course6 = (TextView) getActivity().findViewById(R.id.week02_course6);
                                                            week02_course6.setText(list.get(i).getSchool());
                                                            break;
                                                        case 28:
                                                            week03_course6 = (TextView) getActivity().findViewById(R.id.week03_course6);
                                                            week03_course6.setText(list.get(i).getSchool());
                                                            break;
                                                        case 29:
                                                            week04_course6 = (TextView) getActivity().findViewById(R.id.week04_course6);
                                                            week04_course6.setText(list.get(i).getSchool());
                                                            break;
                                                        case 30:
                                                            week05_course6 = (TextView) getActivity().findViewById(R.id.week05_course6);
                                                            week05_course6.setText(list.get(i).getSchool());
                                                            break;
                                                        case 31:
                                                            week01_course7 = (TextView) getActivity().findViewById(R.id.week01_course7);
                                                            week01_course7.setText(list.get(i).getSchool());
                                                            break;
                                                        case 32:
                                                            week02_course7 = (TextView) getActivity().findViewById(R.id.week02_course7);
                                                            week02_course7.setText(list.get(i).getSchool());
                                                            break;
                                                        case 33:
                                                            week03_course7 = (TextView) getActivity().findViewById(R.id.week03_course7);
                                                            week03_course7.setText(list.get(i).getSchool());
                                                            break;
                                                        case 34:
                                                            week04_course7 = (TextView) getActivity().findViewById(R.id.week04_course7);
                                                            week04_course7.setText(list.get(i).getSchool());
                                                            break;
                                                        case 35:
                                                            week05_course7 = (TextView) getActivity().findViewById(R.id.week05_course7);
                                                            week05_course7.setText(list.get(i).getSchool());
                                                            break;
                                                        case 36:
                                                            week01_course8 = (TextView) getActivity().findViewById(R.id.week01_course8);
                                                            week01_course8.setText(list.get(i).getSchool());
                                                            break;
                                                        case 37:
                                                            week02_course8 = (TextView) getActivity().findViewById(R.id.week02_course8);
                                                            week02_course8.setText(list.get(i).getSchool());
                                                            break;
                                                        case 38:
                                                            week03_course8 = (TextView) getActivity().findViewById(R.id.week03_course8);
                                                            week03_course8.setText(list.get(i).getSchool());
                                                            break;
                                                        case 39:
                                                            week04_course8 = (TextView) getActivity().findViewById(R.id.week04_course8);
                                                            week04_course8.setText(list.get(i).getSchool());
                                                            break;
                                                        case 40:
                                                            week05_course8 = (TextView) getActivity().findViewById(R.id.week05_course8);
                                                            week05_course8.setText(list.get(i).getSchool());
                                                            break;
                                                        case 41:
                                                            week01_course9 = (TextView) getActivity().findViewById(R.id.week01_course9);
                                                            week01_course9.setText(list.get(i).getSchool());
                                                            break;
                                                        case 42:
                                                            week02_course9 = (TextView) getActivity().findViewById(R.id.week02_course9);
                                                            week02_course9.setText(list.get(i).getSchool());
                                                            break;
                                                        case 43:
                                                            week03_course9 = (TextView) getActivity().findViewById(R.id.week03_course9);
                                                            week03_course9.setText(list.get(i).getSchool());
                                                            break;
                                                        case 44:
                                                            week04_course9 = (TextView) getActivity().findViewById(R.id.week04_course9);
                                                            week04_course9.setText(list.get(i).getSchool());
                                                            break;
                                                        case 45:
                                                            week05_course9 = (TextView) getActivity().findViewById(R.id.week05_course9);
                                                            week05_course9.setText(list.get(i).getSchool());
                                                            break;

                                                        default:
                                                            break;
                                                    }

                                                }
                                            }

                                        }
                                    }
                                }
                            });
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }finally {
//                            dissmissProgressDialog();
                            try {
                                mSVProgressHUD.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                });
    }

    @Override
    public void onResume() {
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

}
