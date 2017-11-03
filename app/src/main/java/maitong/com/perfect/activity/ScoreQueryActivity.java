package maitong.com.perfect.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import maitong.com.perfect.R;
import maitong.com.perfect.adapter.ExpandableAdapter;
import maitong.com.perfect.base.BaseActivity;
import maitong.com.perfect.bean.ExamBean;
import maitong.com.perfect.bean.ExamlistBean;
import maitong.com.perfect.custorm.ExpandableListViewScroll;
import maitong.com.perfect.utils.AppNetConfig;
import maitong.com.perfect.utils.AppSharePreferenceMgr;
import okhttp3.Call;

/**
 * 成绩查询
 * Created by zheng on 2017/10/9.
 */

public class ScoreQueryActivity extends BaseActivity {
    private List<String> scoreParentData = new ArrayList<String>();//定义组数据
    private List<List<String>> scoreChildrenData = new ArrayList<>();//定义组中的子数据
    private Context context;
    private ExpandableAdapter adapter;

    private List<ExamBean> myList;
    private List<ExamlistBean> examNum;
    private ExamBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_query);
        context = this;
        setToolbar_left_tv("返回");
        setToolbar_string("成绩查询");
        showProgressDialog();
        initScoreTypeTree();
        ExpandableListViewScroll myExpandableListView = (ExpandableListViewScroll) findViewById(R.id.scoreExpandableListView);
        adapter = new ExpandableAdapter(myList, context);
        myExpandableListView.setAdapter(adapter);
//        myExpandableListView.expandGroup(0);//默认展开第一组
        myExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                Intent it = new Intent(ScoreQueryActivity.this, MyGradeActivity.class);
//                it.putExtra("type",myList.get(groupPosition).getExmaType());
                it.putExtra("type", groupPosition + "");
                it.putExtra("time", myList.get(groupPosition).getExmaList().get(childPosition).getNum());
                startActivity(it);
                dissmissProgressDialog();
                return false;
            }
        });
    }

    public void initScoreTypeTree() {
        myList = new ArrayList<>();

        String url = AppNetConfig.GET_SCOREDATETREE_URL;
        String username = (String) AppSharePreferenceMgr.get(context, "USER", "");//是下面参数的studentNo
        OkHttpUtils
                .get()
                .url(url)
                .addParams("studentNo", "xs03")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            {"data":[{"exam":0,"examlist":[]},{"exam":1,"examlist":[{"num":1}]},{"exam":2,"examlist":[{"num":1}]},{"exam":0,"examlist":[]},{"exam":4,"examlist":[{"num":1}]}]}
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                bean = new ExamBean();
                                examNum = new ArrayList<>();
                                JSONObject temp = (JSONObject) jsonArray.get(i);
                                switch (temp.optInt("exam") + "") {
                                    case "0":
                                        bean.setExmaType("周考");//考试种类
                                        break;
                                    case "1":
                                        bean.setExmaType("半月考");//考试种类
                                        break;
                                    case "2":
                                        bean.setExmaType("月考");//考试种类
                                        break;
                                    case "3":
                                        bean.setExmaType("期中考试");//考试种类
                                        break;
                                    case "4":
                                        bean.setExmaType("期末考试");//考试种类
                                        break;
                                    case "5":
                                        bean.setExmaType("学业水平考试");//考试种类
                                        break;
                                    default:
                                        break;
                                }
                                try {
                                    JSONArray jsonArray2 = temp.getJSONArray("examlist");
                                    if (jsonArray2.length() != 0) {
                                        ExamlistBean exa;
                                        for (int j = 0; j < jsonArray2.length(); j++) {
                                            JSONObject temp2 = (JSONObject) jsonArray2.get(j);
                                            exa = new ExamlistBean();
                                            if (temp2.optString("num") != null || temp2.optString("num") != "") {
                                                exa.setNum(temp2.optInt("num") + "");
                                            }
                                            examNum.add(exa);
                                        }
                                    } else {
                                        examNum = new ArrayList<>();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                bean.setExmaList(examNum);
                                myList.add(bean);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }finally {
                            dissmissProgressDialog();
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                });
    }

}
