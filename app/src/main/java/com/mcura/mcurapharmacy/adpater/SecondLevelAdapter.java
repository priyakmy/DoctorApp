/*
package com.mcura.mcurapharmacy.adpater;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.EnumType;
import com.mcura.mcurapharmacy.model.LabOrderDetailModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SecondLevelAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
    ArrayList<LabOrderDetailModel> parentNode;
    public CheckBox cb_select_row_level_first;
    public SecondLevelAdapter(Context mContext, ArrayList<LabOrderDetailModel> parentNode, CheckBox cb_select_row_level_first) {
        this.mContext = mContext;
        this.parentNode = parentNode;
        this.cb_select_row_level_first = cb_select_row_level_first;
    }

    @Override
    public LabOrderDetailModel getChild(int groupPosition, int childPosition) {
        return parentNode.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        LabOrderDetailModel childText = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_item, parent, false);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        txtListChild.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        txtListChild.setText(childText.getLabTestName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return parentNode.get(groupPosition).getChildren().size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public LabOrderDetailModel getGroup(int groupPosition) {
        return parentNode.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return parentNode.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // This Function used to inflate parent rows view
    public void OnIndicatorClick(boolean isExpanded, int position) {

    }

    public void OnTextClick() {

    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final LabOrderDetailModel headerTitle = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_group_second, parent, false);
        }
        CheckBox cb_select_row_level_second = (CheckBox) convertView.findViewById(R.id.cb_select_row_level_second);
        ImageButton indicator = (ImageButton) convertView.findViewById(R.id.indicator);
        int imageResourceId = isExpanded ? R.mipmap.minus
                : R.mipmap.plus;

        if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNaturePackage || headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureGroup) {
            indicator.setVisibility(View.VISIBLE);
            indicator.setImageResource(imageResourceId);
        } else if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureTest) {
            indicator.setVisibility(View.GONE);
        }

        indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIndicatorClick(isExpanded, groupPosition);
            }
        });
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        TextView tv_test_cost = (TextView) convertView.findViewById(R.id.tv_test_cost);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getLabTestName());
        tv_test_cost.setText(headerTitle.getLabTestCost().toString());
        cb_select_row_level_second.setChecked(headerTitle.isSelected());
        cb_select_row_level_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headerTitle.isSelected()==true) {
                    headerTitle.setSelected(false);
                    parentNode.get(groupPosition).setSelected(false);
                } else {
                    headerTitle.setSelected(true);
                }
                notifyDataSetChanged();
            }
        });

        */
/*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headerTitle.isSelected()) {
                    headerTitle.setSelected(false);
                } else {
                    headerTitle.setSelected(true);
                }
            }
        });*//*


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
*/
