package maitong.com.perfect.custorm;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import maitong.com.perfect.R;

/**
 * Created by tony on 15/8/19.
 */
public class MainBottomBar extends LinearLayout {
    private LinearLayout ll_news, ll_chats, ll_main, ll_circle, ll_me;
    private ImageView iv_news, iv_chats, iv_main, iv_circle, iv_me;
    private ImageView iv_news_new, iv_chats_new, iv_circle_new, iv_me_new,iv_main_new;
    private TextView tv_news, tv_chats, tv_main, tv_circle, tv_me;
    private ViewPager pager;

    public MainBottomBar(Context context) {
        super(context);
    }

    public MainBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MainBottomBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void initUI() {
        ll_news = findViewById(R.id.ll_news);
        ll_chats = findViewById(R.id.ll_chats);
        ll_main = findViewById(R.id.ll_main);
        ll_circle = findViewById(R.id.ll_circle);
        ll_me = findViewById(R.id.ll_me);
        iv_news = findViewById(R.id.iv_news);
        iv_chats = findViewById(R.id.iv_chats);
        iv_main = findViewById(R.id.iv_main);
        iv_circle = findViewById(R.id.iv_circle);
        iv_me = findViewById(R.id.iv_me);
        iv_news_new = findViewById(R.id.iv_news_new);
        iv_chats_new = findViewById(R.id.iv_chats_new);
        iv_circle_new = findViewById(R.id.iv_circle_new);
        iv_main_new = findViewById(R.id.iv_main_new);
        iv_me_new = findViewById(R.id.iv_me_new);
        tv_news = findViewById(R.id.tv_news);
        tv_chats = findViewById(R.id.tv_chats);
        tv_main = findViewById(R.id.tv_main);
        tv_circle = findViewById(R.id.tv_circle);
        tv_me = findViewById(R.id.tv_me);
        ll_news.setOnClickListener(onClickListener);
        ll_chats.setOnClickListener(onClickListener);
        ll_main.setOnClickListener(onClickListener);
        ll_circle.setOnClickListener(onClickListener);
        ll_me.setOnClickListener(onClickListener);

        iv_news_new.setVisibility(GONE);
        iv_chats_new.setVisibility(GONE);
        iv_circle_new.setVisibility(GONE);
        iv_me_new.setVisibility(GONE);
        iv_main_new.setVisibility(GONE);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (pager == null) {
                return;
            }
            int id = view.getId();
            int index = 0;
            if (id == R.id.ll_news) {
                index = 0;
            } else if (id == R.id.ll_chats) {
                index = 1;
            } else if (id == R.id.ll_main) {
                index = 2;
            } else if (id == R.id.ll_circle) {
                index = 3;
            } else if (id == R.id.ll_me) {
                index = 4;
            }
            pager.setCurrentItem(index);
        }
    };

    public void setViewPager(ViewPager pager) {
        initUI();
        this.pager = pager;
        pager.setOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                }
        );
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        if (pager == null) {
            return;
        }
        int index = pager.getCurrentItem();
        iv_news.setImageResource(R.drawable.icon_course_table_nor);
        iv_chats.setImageResource(R.drawable.icon_meassage_nor);
//        iv_main.setImageResource(R.drawable.icon_main_tab_key_d);//钥匙
        iv_main.setBackgroundResource(0);
        iv_main.setImageResource(R.drawable.icon_school_nor);
        iv_circle.setImageResource(R.drawable.icon_friend_add_nor);
        iv_me.setImageResource(R.drawable.icon_my_nor);
        tv_news.setTextColor(getResources().getColor(R.color.gray_common));
        tv_chats.setTextColor(getResources().getColor(R.color.gray_common));
        tv_main.setTextColor(getResources().getColor(R.color.gray_common));
        tv_circle.setTextColor(getResources().getColor(R.color.gray_common));
        tv_me.setTextColor(getResources().getColor(R.color.gray_common));
        if (index == 0) {
            iv_news.setImageResource(R.drawable.icon_course_table_sel);
            tv_news.setTextColor(getResources().getColor(R.color.gray_select));
        } else if (index == 1) {
            iv_chats.setImageResource(R.drawable.icon_meassage_sel);
            tv_chats.setTextColor(getResources().getColor(R.color.gray_select));
        } else if (index == 2) {
            iv_main.setBackgroundResource(R.drawable.icon_school_sel);
            tv_main.setTextColor(getResources().getColor(R.color.gray_select));
        } else if (index == 3) {
            iv_circle.setImageResource(R.drawable.icon_friend_add_sel);
            tv_circle.setTextColor(getResources().getColor(R.color.gray_select));
        } else if (index == 4) {
            iv_me.setImageResource(R.drawable.icon_my_sel);
            tv_me.setTextColor(getResources().getColor(R.color.gray_select));
        }
    }
}
