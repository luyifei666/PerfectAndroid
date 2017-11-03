package maitong.com.perfect.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maitong.com.perfect.R;
import maitong.com.perfect.adapter.MainGridViewAdapter;
import maitong.com.perfect.base.BaseActivity;
import maitong.com.perfect.bean.ClickModel;
import maitong.com.perfect.utils.AppSharePreferenceMgr;


/**
 * Created by zheng on 2017/9/18.
 */

public class CourseActivity extends BaseActivity {

    @Bind(R.id.my_grid)
    GridView mMyGrid;
    @Bind(R.id.myselect)
    TextView mMyselect;
    @Bind(R.id.btn_select)
    Button mBtnSelect;

    private Context mContext;
    private MainGridViewAdapter mAdapter;
    int[] imageId = new int[]{R.mipmap.chinese_select, R.mipmap.math_select, R.mipmap.english_select, R.mipmap.politics_nor, R.mipmap.history_nor, R.mipmap.geography_nor, R.mipmap.chemistry_nor, R.mipmap.physics_nor, R.mipmap.biology_nor};
    List<ClickModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        ButterKnife.bind(this);
        mContext = this;
        String title = (String) AppSharePreferenceMgr.get(mContext, "schoolName", "");
        setToolbar_left_tv("返回");
        setToolbar_string(title);
        mAdapter = new MainGridViewAdapter(mContext);
        mMyGrid.setAdapter(mAdapter);

        for (int i = 0; i < imageId.length; i++) {
            ClickModel click = new ClickModel();
            click.setText(mContext.getResources().getStringArray(R.array.main)[i]);
            click.setFlag(i < 3 ? true : false);
            click.setPictrue(imageId[i]);
            modelList.add(click);
        }

        mAdapter.addList(modelList);

        mMyGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mMyselect.setText(mAdapter.changeClick(i) + "/6");
            }
        });

    }

    @OnClick(R.id.btn_select)
    public void onViewClicked() {
        String s = "";
        for (ClickModel model : modelList) {
            if (model.isFlag()){
                s = model.getText()+ "," + s ;
            }
        }
        Toast.makeText(mContext,"已选课程："+s,Toast.LENGTH_LONG).show();
    }
}
