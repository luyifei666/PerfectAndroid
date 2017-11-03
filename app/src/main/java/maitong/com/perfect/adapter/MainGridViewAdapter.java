package maitong.com.perfect.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haozhang.lib.SlantedTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import maitong.com.perfect.R;
import maitong.com.perfect.bean.ClickModel;

public class MainGridViewAdapter extends BaseAdapter {

    ViewHolder mViewHolder;
    Context mContext;
    List<ClickModel> modelList;

    public MainGridViewAdapter(Context c) {
        mContext = c;
        modelList = new ArrayList<>();
    }

    public int getCount() {

        return modelList.size();
    }

    public Object getItem(int arg0) {

        return modelList.get(arg0);
    }

    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup arg2) {
        LayoutInflater li = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = li.inflate(R.layout.main_item, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.mImg.setBackgroundResource(modelList.get(pos).getPictrue());
        mViewHolder.mText.setText(modelList.get(pos).getText());
        mViewHolder.mLabeltext.setText(pos<3?"必修":modelList.get(pos).isFlag()?"已选择":"未选择");
//        mViewHolder.mLabeltext.setSlantedBackgroundColor(pos<3?R.color.blue_1_205791:modelList.get(pos).isFlag()?R.color.blue_1_205791:R.color.gray_3_a8a8a8);
        mViewHolder.mLabeltext.setSlantedBackgroundColor(pos<3? Color.parseColor("#3687FD"):modelList.get(pos).isFlag()?Color.parseColor("#3687FD"):Color.parseColor("#DBDBDB"));
        if (modelList.get(pos).isFlag()){
            switch (pos){
                case 3:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.politics_select);
                    break;
                case 4:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.history_select);
                    break;
                case 5:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.geography_select);
                    break;
                case 6:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.chemistry_select);
                    break;
                case 7:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.physics_select);
                    break;
                case 8:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.biology_select);
                    break;
            }
        }else {
            switch (pos){
                case 3:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.politics_nor);
                    break;
                case 4:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.history_nor);
                    break;
                case 5:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.geography_nor);
                    break;
                case 6:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.chemistry_nor);
                    break;
                case 7:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.physics_nor);
                    break;
                case 8:
                    mViewHolder.mImg.setBackgroundResource(R.mipmap.biology_nor);
                    break;
            }
        }

        return convertView;

    }

    public void addList(List<ClickModel> modelList){
        this.modelList.addAll(modelList);
        notifyDataSetChanged();
    }

    public void clear(){
        this.modelList.clear();
        notifyDataSetChanged();
    }

    public int changeClick(int position){
        if (position<3){
            Toast.makeText(mContext,"不可修改必填项",Toast.LENGTH_SHORT).show();
            return getYiXuanZhong();
        }
        if(getYiXuanZhong()==6&&!modelList.get(position).isFlag()){
            Toast.makeText(mContext,"不可选择过多课程" ,Toast.LENGTH_SHORT).show();
            return getYiXuanZhong();
        }


        modelList.get(position).setFlag(!modelList.get(position).isFlag());
        notifyDataSetChanged();
        return getYiXuanZhong();
    }

    public int getYiXuanZhong(){
        int i = 0;
        for (ClickModel model:
                this.modelList) {
            if (model.isFlag()){
                i++;
            }
        }
        return i;
    }


    static class ViewHolder {
        ImageView mImg;
        TextView mText;
        SlantedTextView mLabeltext;
        RelativeLayout mImgRightBtTri;

        ViewHolder(View view) {
            mImg = view.findViewById(R.id.img);
            mText = view.findViewById(R.id.text);
            mLabeltext = view.findViewById(R.id.labeltext);
            mImgRightBtTri = view.findViewById(R.id.img_right_bt_tri);
        }
    }
}