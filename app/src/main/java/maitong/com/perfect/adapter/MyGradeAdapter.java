package maitong.com.perfect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import maitong.com.perfect.R;
import maitong.com.perfect.bean.scoreDto;


public class MyGradeAdapter extends BaseAdapter {

    //    ViewHolder mViewHolder;
    Context mContext;
    private List<scoreDto> list;
    ViewHolder mViewHolder;

    public MyGradeAdapter(Context c, List<scoreDto> list) {
        mContext = c;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int arg0) {

        return null;
    }

    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup arg2) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
        convertView = li.inflate(R.layout.grade_item, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.Subjec.setText(list.get(pos).getCourseName()+"");
        mViewHolder.Grade.setText(list.get(pos).getScore()+"");
        mViewHolder.Average.setText(list.get(pos).getClassroomAvgScore()+"");
        mViewHolder.Highest.setText(list.get(pos).getClassroomMaxScore()+"");
        mViewHolder.Minimum.setText(list.get(pos).getClassroomMinScore()+"");

        return convertView;
    }

    static class ViewHolder {
        TextView Subjec,Grade,Average,Highest,Minimum;

        public ViewHolder(View view) {
            Subjec = (TextView) view.findViewById(R.id.Subjec);
            Grade = (TextView) view.findViewById(R.id.Grade);
            Average = (TextView) view.findViewById(R.id.Average);
            Highest = (TextView) view.findViewById(R.id.Highest);
            Minimum = (TextView) view.findViewById(R.id.Minimum);
        }
    }
}