package maitong.com.perfect.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import maitong.com.perfect.R;
import maitong.com.perfect.adapter.MyGradeAdapter;
import maitong.com.perfect.bean.GradeBean;
import maitong.com.perfect.bean.myTotal;
import maitong.com.perfect.bean.schoolAvgMaxMinScore;
import maitong.com.perfect.bean.scoreDto;
import maitong.com.perfect.custorm.ExpandableListViewScroll;
import maitong.com.perfect.custorm.ListViewScroll;
import maitong.com.perfect.utils.AppNetConfig;
import maitong.com.perfect.utils.AppSharePreferenceMgr;
import okhttp3.Call;

public class MyGradeActivity extends ChartBaseActivity implements OnChartValueSelectedListener {

    private static final int REFRESH_COMPLETE = 0X110;
    @Bind(R.id.userNickname)
    TextView userNicknameText;
    @Bind(R.id.calssroomName)
    TextView calssroomNameText;
    @Bind(R.id.chart1)
    PieChart mChart1;
    @Bind(R.id.schoolAllScoreAvg)
    TextView mSchoolAllScoreAvg;
    @Bind(R.id.schoolAllScoreMax)
    TextView mSchoolAllScoreMax;
    @Bind(R.id.schoolAllScoreMin)
    TextView mSchoolAllScoreMin;
    @Bind(R.id.myscore)
    TextView mMyscore;
    @Bind(R.id.mylist)
    ListViewScroll mylist;
    @Bind(R.id.time)
    TextView tvTime;
    private PieChart mChart;
    //    private ListViewScroll mylist;
    private MyGradeAdapter mMyGradeAdapter;
    private List<GradeBean> list;
    private Context mContext;
    private String type;
    private String time;
    private int myScore;
    private List<schoolAvgMaxMinScore> schoolAvgMaxMinScoreList = new ArrayList<>();
    private List<scoreDto> CoreProcess = new ArrayList<>();
    private List<myTotal> myTotalScore = new ArrayList<>();
    private SVProgressHUD mSVProgressHUD;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    mMyGradeAdapter.notifyDataSetChanged();
                    if (mSVProgressHUD.isShowing()) {
                        mSVProgressHUD.dismiss();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grade);
        ButterKnife.bind(this);
        mContext = this;
        type = getIntent().getStringExtra("type");
        time = getIntent().getStringExtra("time");
        setToolbar_left_tv("返回");
        setToolbar_string("我的成绩");
        mSVProgressHUD = new SVProgressHUD(mContext);
        mSVProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.Black);
        mSVProgressHUD.show();
//        addTestData();
        init();
        getData();
//        initOne();
    }

    private void getData() {
        String url = AppNetConfig.GET_getScoreDataByTime_URL;
        String username = (String) AppSharePreferenceMgr.get(mContext, "USER", "");//是下面参数的studentNo
        String schoolId = (String) AppSharePreferenceMgr.get(mContext, "SCHOOLID", "");//schoolId
        String classroomId = (String) AppSharePreferenceMgr.get(mContext, "CLASSROOMID", "");//classroomId
        String userNickname = (String) AppSharePreferenceMgr.get(mContext, "USERNICKNAME", "");
        String gradeName = (String) AppSharePreferenceMgr.get(mContext, "GRADENAME", "");
        String classroomName = (String) AppSharePreferenceMgr.get(mContext, "CLASSROOMNAME", "");
        userNicknameText.setText(userNickname);
        calssroomNameText.setText(gradeName + "(" + classroomName + ")");
        OkHttpUtils
                .get()
                .url(url)
                .addParams("studentNo", username)
                .addParams("schoolId", schoolId)
                .addParams("classroomId", classroomId)
                .addParams("type", type)
                .addParams("time", time)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Log.e("fmz", response);
                            JSONObject jsonObject = new JSONObject(URLDecoder.decode(response));
//                            {"data":{"schoolAvgMaxMinScoreList":[{"schoolAllScoreAvg":604.6,"schoolAllScoreMin":90,"schoolAllScoreMax":900}],
//                                "scoreDtoList":[{"score":82,"courseName":"??","classroomAvgScore":68.2,"classroomMaxScore":100,"classroomMinScore":10},{"score":86,"courseName":"??","classroomAvgScore":67.6,"classroomMaxScore":100,"classroomMinScore":10},{"score":87,"courseName":"??","classroomAvgScore":66.8,"classroomMaxScore":100,"classroomMinScore":10},{"score":82,"courseName":"??","classroomAvgScore":66.4,"classroomMaxScore":100,"classroomMinScore":10},{"score":82,"courseName":"??","classroomAvgScore":68.2,"classroomMaxScore":100,"classroomMinScore":10},{"score":84,"courseName":"??","classroomAvgScore":65.8,"classroomMaxScore":100,"classroomMinScore":10},{"score":80,"courseName":"??","classroomAvgScore":66.2,"classroomMaxScore":100,"classroomMinScore":10},{"score":85,"courseName":"??","classroomAvgScore":68.2,"classroomMaxScore":100,"classroomMinScore":10},{"score":85,"courseName":"??","classroomAvgScore":67.2,"classroomMaxScore":100,"classroomMinScore":10}],
//                                "myTotalScore":[{"myTotalScore":753}]}}
                            JSONObject obj = jsonObject.getJSONObject("data");
                            JSONArray arr2 = obj.getJSONArray("schoolAvgMaxMinScoreList");
                            JSONArray arr3 = obj.getJSONArray("scoreDtoList");
                            JSONArray arr4 = obj.getJSONArray("myTotalScore");
                            schoolAvgMaxMinScoreList = new Gson().fromJson(arr2.toString(), new TypeToken<List<schoolAvgMaxMinScore>>() {
                            }.getType());
                            List<scoreDto> CoreProcess22 = new Gson().fromJson(arr3.toString(), new TypeToken<List<scoreDto>>() {
                            }.getType());
                            myTotalScore = new Gson().fromJson(arr4.toString(), new TypeToken<List<myTotal>>() {
                            }.getType());
                            try {
                                Log.e("lyf","schoolAvgMaxMinScoreList = " + schoolAvgMaxMinScoreList.size());
                                myScore = myTotalScore.get(0).getMyTotalScore();//我的总分
                                mMyscore.setText(myScore + "");
                                mSchoolAllScoreMax.setText(schoolAvgMaxMinScoreList.get(0).getSchoolAllScoreMax() + "");
                                mSchoolAllScoreMin.setText(schoolAvgMaxMinScoreList.get(0).getSchoolAllScoreMin() + "");
                                mSchoolAllScoreAvg.setText(schoolAvgMaxMinScoreList.get(0).getSchoolAllScoreAvg() + "");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            CoreProcess.addAll(CoreProcess22);
                            initOne(myScore + "");
                            mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            if (mSVProgressHUD.isShowing()) {
                                mSVProgressHUD.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                });
    }

    private void addTestData() {
        list = new ArrayList<>();
        GradeBean bean;
        for (int i = 0; i < 6; i++) {
            bean = new GradeBean();
            bean.setSubjec("语文");
            bean.setAverage("92");
            bean.setGrade("98");
            bean.setHighest("98");
            bean.setMinimum("89");
            list.add(bean);

            bean = new GradeBean();
            bean.setSubjec("数学");
            bean.setAverage("92");
            bean.setGrade("98");
            bean.setHighest("98");
            bean.setMinimum("89");
            list.add(bean);

            bean = new GradeBean();
            bean.setSubjec("英语");
            bean.setAverage("92");
            bean.setGrade("98");
            bean.setHighest("98");
            bean.setMinimum("89");
            list.add(bean);

            bean = new GradeBean();
            bean.setSubjec("物理");
            bean.setAverage("92");
            bean.setGrade("98");
            bean.setHighest("98");
            bean.setMinimum("89");
            list.add(bean);

            bean = new GradeBean();
            bean.setSubjec("化学");
            bean.setAverage("92");
            bean.setGrade("98");
            bean.setHighest("98");
            bean.setMinimum("89");
            list.add(bean);

            bean = new GradeBean();
            bean.setSubjec("生物");
            bean.setAverage("85");
            bean.setGrade("86");
            bean.setHighest("96");
            bean.setMinimum("52");
            list.add(bean);
        }

    }

    private void init() {
        mMyGradeAdapter = new MyGradeAdapter(mContext, CoreProcess);
        View headView = LayoutInflater.from(mContext).inflate(R.layout.grade_header, null);
        mylist.addHeaderView(headView);
        mylist.setAdapter(mMyGradeAdapter);
        tvTime.setText("第" + time + "次考试");
    }

    private void initOne(String myScore) {
        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        //设置中间文件
        mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterTextColor(Color.parseColor("#ff0099cc"));
        mChart.setCenterTextSize(12);
        mChart.setCenterText(generateCenterSpannableText(myScore));

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(90f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        // 触摸旋转
        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //变化监听
        mChart.setOnChartValueSelectedListener(this);

        //模拟数据
//        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//        entries.add(new PieEntry(100, "优秀"));
//        entries.add(new PieEntry(20, "满分"));
//        entries.add(new PieEntry(30, "及格"));
//        entries.add(new PieEntry(10, "不及格"));


//        setData(entries);

        setData(2, 66);

        for (IDataSet<?> set : mChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
        mChart.setUsePercentValues(false);//不使用百分比
        mChart.setDrawEntryLabels(!mChart.isDrawEntryLabelsEnabled());//去除饼状图中的描述
        mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());//去除饼状图中百分比
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

//        mSeekBarX.setOnSeekBarChangeListener(this);
//        mSeekBarY.setOnSeekBarChangeListener(this);

        Legend mLegend = mChart.getLegend(); //设置比例图
        mLegend.setEnabled(false);

//        Legend l = mChart.getLegend(0);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        // entry label styling
        // 输入标签样式
//        mChart.setEntryLabelColor(Color.WHITE);
//        mChart.setEntryLabelTypeface(mTfRegular);
//        mChart.setEntryLabelTextSize(12f);
    }

    private SpannableString generateCenterSpannableText(String myScore) {

        SpannableString s = new SpannableString(myScore + "\n我的成绩");
//        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
//        for (int i = 0; i < count ; i++) {
//            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
//                    mParties[i % mParties.length],
//                    getResources().getDrawable(R.drawable.star)));
//        }
        entries.add(new PieEntry((float) (66.6),
                mParties[0],
                getResources().getDrawable(R.drawable.star)));
        entries.add(new PieEntry((float) (33.3),
                mParties[1],
                getResources().getDrawable(R.drawable.star)));

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);

        colors.add(ColorTemplate.rgb("##ff0099cc"));
        colors.add(ColorTemplate.rgb("#EDEDED"));

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private void setData(ArrayList<PieEntry> entries) {

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
        colors.add(ColorTemplate.rgb("#008000"));
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(R.color.green);
        mChart.setData(data);
        mChart.highlightValues(null);
        //刷新
        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }


    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

}
