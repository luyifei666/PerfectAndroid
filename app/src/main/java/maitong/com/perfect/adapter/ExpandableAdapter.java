package maitong.com.perfect.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import maitong.com.perfect.R;
import maitong.com.perfect.bean.ExamBean;

/**
 * Created by Bruce on 2017-10-11.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private List<ExamBean> myList;
    private Context mContext;

    public ExpandableAdapter(List<ExamBean> myList, Context context) {
        this.myList = myList;
        this.mContext = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return myList.get(groupPosition).getExmaList().get(childPosition).getNum();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ExpandableAdapter.ItemHolder itemHolder = null;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expendlist_item, null);
            itemHolder = new ExpandableAdapter.ItemHolder();
            itemHolder.txt = (TextView) convertView.findViewById(R.id.txt);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ExpandableAdapter.ItemHolder) convertView.getTag();
        }
        itemHolder.txt.setText(myList.get(groupPosition).getExmaList().size() == 0 ? "" : "第" + myList.get(groupPosition).getExmaList().get(childPosition).getNum() + "次考试");
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return myList.get(groupPosition).getExmaList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return myList.get(groupPosition).getExmaList();
    }

    @Override
    public int getGroupCount() {
        return myList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LinearLayout parentLayout = (LinearLayout) View.inflate(mContext, R.layout.expend_item, null);
        TextView parentTextView = (TextView) parentLayout.findViewById(R.id.expendTv);
        ImageView imageView = (ImageView) parentLayout.findViewById(R.id.imageView);
        switch (groupPosition) {
            case 0:
                imageView.setBackgroundResource(R.mipmap.exam_zk);
                break;
            case 1:
                imageView.setBackgroundResource(R.mipmap.exam_byk);
                break;
            case 2:
                imageView.setBackgroundResource(R.mipmap.exam_yk);
                break;
            case 3:
                imageView.setBackgroundResource(R.mipmap.exam_qz);
                break;
            case 4:
                imageView.setBackgroundResource(R.mipmap.exam_qm);
                break;
            case 5:
                imageView.setBackgroundResource(R.mipmap.exam_xyspks);
                break;
            default:
                break;
        }
        parentTextView.setText(myList.get(groupPosition).getExmaType());
        ImageView parentImageViw = (ImageView) parentLayout.findViewById(R.id.expendIv);
        //判断isExpanded就可以控制是按下还是关闭，同时更换图片
        if (isExpanded) {
            parentImageViw.setBackgroundResource(R.mipmap.table_view_up);
        } else {
            parentImageViw.setBackgroundResource(R.mipmap.table_view_right);
        }
        return parentLayout;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private TextView createView(String content) {
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, 80);
        TextView myText = new TextView(mContext);
        myText.setLayoutParams(layoutParams);
        myText.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        myText.setPadding(80, 0, 0, 0);
        myText.setText(content);
        return myText;
    }

    class ItemHolder {
        public ImageView img;
        public TextView txt;
    }

}
