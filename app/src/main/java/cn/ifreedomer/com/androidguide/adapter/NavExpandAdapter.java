package cn.ifreedomer.com.androidguide.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.event.ContentEvent;
import cn.ifreedomer.com.androidguide.manager.NotifycationManager;
import cn.ifreedomer.com.androidguide.model.ContentModel;
import cn.ifreedomer.com.androidguide.model.NavExpandedModel;
import cn.ifreedomer.com.androidguide.model.SubTitleModel;
import cn.ifreedomer.com.androidguide.util.LogUtil;

/**
 * @author:eavawu
 * @date: 6/18/16.
 * @todo:
 */
public class NavExpandAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {
    private Context mContext;
    private List<NavExpandedModel> mDataList;
    //    private List<NavExpandedModel> mListDataHeader; // header titles
//
//    // child data in format of header title, child title
//    private HashMap<NavExpandedModel, List<String>> mListDataChild;
    ExpandableListView expandList;

    public NavExpandAdapter(Context context, List<NavExpandedModel> dataList, ExpandableListView mView) {
        this.mContext = context;
        this.expandList = mView;
        mDataList = dataList;
        expandList.setOnGroupClickListener(this);
        expandList.setOnChildClickListener(this);
    }


    @Override
    public int getGroupCount() {
        int i = mDataList.size();
        Log.d("GROUPCOUNT", String.valueOf(i));
        return this.mDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        NavExpandedModel model = mDataList.get(groupPosition);
        return model.getContentMap().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        NavExpandedModel model = mDataList.get(groupPosition);
        HashMap<SubTitleModel, List<ContentModel>> contentMap = model.getContentMap();
        SubTitleModel[] subTitleModels = new SubTitleModel[contentMap.size()];
        subTitleModels = contentMap.keySet().toArray(subTitleModels);
        return contentMap.get(subTitleModels[childPosition]);
    }

    public String getChildTitle(int groupPosition, int childPosition){
        NavExpandedModel model = mDataList.get(groupPosition);
        HashMap<SubTitleModel, List<ContentModel>> contentMap = model.getContentMap();
        SubTitleModel[] subTitleModels = new SubTitleModel[contentMap.size()];
        subTitleModels = contentMap.keySet().toArray(subTitleModels);
        return subTitleModels[childPosition].getSubTitle();
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        NavExpandedModel headerTitle = (NavExpandedModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.nav_list_header, null);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.submenu);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getTitleName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChildTitle(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.nav_list_submenu, null);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu);

        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        LogUtil.info("NavExpandAdapter", groupPosition + "");
        View arrowIv = v.findViewById(R.id.arrow_iv);
        arrowIv.setSelected(!arrowIv.isSelected());
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        final String childText = (String) getChildTitle(groupPosition, childPosition);
        List<ContentModel> child = (List<ContentModel>) getChild(groupPosition, childPosition);
        ContentEvent event = new ContentEvent(child,childText);
        NotifycationManager.getInstance().post(event);
        return false;
    }
}
