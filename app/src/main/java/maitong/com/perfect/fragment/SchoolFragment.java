package maitong.com.perfect.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maitong.com.perfect.MainActivity;
import maitong.com.perfect.R;
import maitong.com.perfect.activity.CourseActivity;
import maitong.com.perfect.activity.MainTabActivity;
import maitong.com.perfect.activity.ScoreQueryActivity;
import maitong.com.perfect.base.BaseFragment;
import maitong.com.perfect.custorm.PopWindow;
import maitong.com.perfect.utils.AppNetConfig;
import maitong.com.perfect.utils.AppSharePreferenceMgr;
import maitong.com.perfect.utils.IntentUtils;
import maitong.com.perfect.utils.ToastUtil;
import okhttp3.Call;

/**
 * Created by tony on 15/8/17.
 */
public class SchoolFragment extends BaseFragment implements View.OnClickListener {

    public final static String HAS_NEW_NEWS = "HAS_NEW_NEWS";
    public final static String UPDATE_NEWS_DOT = "UPDATE_NEWS_DOT";

    private Context context;
    //手机页面右上角弹矿
    private ImageView iv_title_setting;
    private Banner banner;
    private List<String> imgList = null;
    private RadioButton rb_xk;//选课
    private RadioButton rb_xkqk;//选课情况
    private RadioButton rb_cjcx;//成绩查询
    private RadioButton rb_xx;//学校
    private RadioButton rb_kcqk;//家校通
    private LinearLayout more;//更多期待
    private static SchoolFragment sFragment;
    private SVProgressHUD mSVProgressHUD;

    public static SchoolFragment newInstance() {
        if (sFragment == null) {
            sFragment = new SchoolFragment();
        }
        return sFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contextView = inflater.inflate(R.layout.fragment_school, container, false);
        context = getActivity();
        mSVProgressHUD = new SVProgressHUD(context);
        mSVProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.Black);
        mSVProgressHUD.show();
        initUI(contextView);
        initData();

        return contextView;
    }

    @Override
    protected void initUI(View view) {
        banner = view.findViewById(R.id.banner);
        setleftTitleBarBtnVisable(view, View.GONE);
        String title = (String) AppSharePreferenceMgr.get(context, "schoolName", "");
        updateSubTitleBar(view, title, R.drawable.add_noborder, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopWindow popWindow = new PopWindow(getActivity());
                popWindow.showPopupWindow(getActivity().findViewById(R.id.sub_title_bar_img_right));
            }
        });
        //初始化布局对象
        rb_xk = view.findViewById(R.id.rb_xk);
        rb_xkqk = view.findViewById(R.id.rb_xkqk);
        rb_xx = view.findViewById(R.id.rb_xx);
        rb_cjcx = view.findViewById(R.id.rb_cjcx);
        more = view.findViewById(R.id.more);
        rb_kcqk = view.findViewById(R.id.rb_kcqk);
        rb_xk.setOnClickListener(this);
        rb_cjcx.setOnClickListener(this);
        rb_xkqk.setOnClickListener(this);
        rb_xx.setOnClickListener(this);
        more.setOnClickListener(this);
        rb_kcqk.setOnClickListener(this);
//
    }

    @Override
    protected void initData() {
        try {
            String url = AppNetConfig.GET_IMG_URL;
            OkHttpUtils
                    .post()
                    .url(url)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onResponse(String response, int id) {
                            //解析json数据
                            JSONArray objects = JSON.parseArray(response);
                            imgList = new ArrayList<String>();
                            for (int i = 0; i < objects.size(); i++) {
                                imgList.add(objects.get(i).toString());
                            }
                            //设置banner样式
                            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                            //设置图片加载器
                            banner.setImageLoader(new GlideImageLoader());
                            banner.setImages(imgList);
                            //设置banner动画效果
                            banner.setBannerAnimation(Transformer.DepthPage);
                            //设置标题集合（当banner样式有显示title时）
                            String[] titles = new String[]{"家校互网", "新高考改革", "全面发展素质教育"};
                            banner.setBannerTitles(Arrays.asList(titles));
                            //设置自动轮播，默认为true
                            banner.isAutoPlay(true);
                            //设置轮播时间
                            banner.setDelayTime(3000);
                            //设置指示器位置（当banner模式中有指示器时）
                            banner.setIndicatorGravity(BannerConfig.CENTER);
                            //banner设置方法全部调用完毕时最后调用
                            banner.start();
                        }


                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(context, "连接错误", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    if (mSVProgressHUD.isShowing()) {
                        mSVProgressHUD.dismiss();
                    }
                    Looper.loop();
                }
            }).start();
        }
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

    @Override
    public void onClick(View view) {
        Intent it;
        switch (view.getId()) {
            case R.id.rb_xk://选课
                mSVProgressHUD = new SVProgressHUD(context);
                mSVProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.Black);
                mSVProgressHUD.show();
                it = new Intent(getContext(), CourseActivity.class);
                startActivity(it);
                mSVProgressHUD.dismiss();
                break;
            case R.id.rb_cjcx://成绩查询
                it = new Intent(getContext(), ScoreQueryActivity.class);
                startActivity(it);
                break;
            case R.id.rb_xx://学校
                IntentUtils.startToWebActivity(context, "", "周公解梦", "http://m.sui.taobao.org/demos/");
                break;
            case R.id.rb_xkqk://选课情况
                IntentUtils.startToWebActivity(context, "", "周公解梦", "https://www.wjx.cn/jq/17157054.aspx");
                break;
            case R.id.rb_kcqk://家校通
                IntentUtils.startToWebActivity(context, "", "周公解梦", "https://weui.io/");
                break;
            case R.id.more:
                Toast.makeText(context,"敬请期待~",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);

        }
    }
}
