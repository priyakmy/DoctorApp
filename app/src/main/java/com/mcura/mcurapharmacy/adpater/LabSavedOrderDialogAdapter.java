package com.mcura.mcurapharmacy.adpater;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.EnumType;
import com.mcura.mcurapharmacy.model.LabChildDatum;
import com.mcura.mcurapharmacy.model.LabDatum;
import com.mcura.mcurapharmacy.model.LabOrderDetailModel;
import com.mcura.mcurapharmacy.model.LabPharmacyPostResponseModel;
import com.mcura.mcurapharmacy.view.CustomExpListView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mCURA1 on 8/17/2017.
 */

public class LabSavedOrderDialogAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private LabDatum labDatum;
    private ArrayList<LabOrderDetailModel> labOrderDetailModels;
    private boolean isAllChecked;
    private SecondLevelAdapter secondLevelAdapter;
    //private CheckBox cb_select_row_level_first;
    private MCuraApplication mCuraApplication;
    private ProgressDialog progress;
    TextView tv_total_amount;
    double totalAmount = 0.0;

    public LabSavedOrderDialogAdapter(Context context, final LabDatum labDatum, final ArrayList<LabOrderDetailModel> labOrderDetailModel, LabChildDatum labChildDatum, TextView btn_paynow, TextView tv_total_amount) {
        this.mContext = context;
        this.labDatum = labDatum;
        this.labOrderDetailModels = labOrderDetailModel;
        this.tv_total_amount = tv_total_amount;
        /*btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });*/
    }

    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(mContext);
            progress.setCancelable(false);
            progress.setMessage(mContext.getString(R.string.loading_message));
        }
        progress.show();
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }



    public void selectAll(LabOrderDetailModel headerTitle) {
        headerTitle.setSelected(true);
        for (int i = 0; i < headerTitle.getChildren().size(); i++) {
            headerTitle.getChildren().get(i).setSelected(true);
        }
        notifyDataSetChanged();

    }

    public void unselectAll(LabOrderDetailModel headerTitle) {
        headerTitle.setSelected(false);
        for (int i = 0; i < headerTitle.getChildren().size(); i++) {
            headerTitle.getChildren().get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }


    // This Function used to inflate parent rows view
    public void OnIndicatorClick(boolean isExpanded, int position) {

    }

    public void OnTextClick() {

    }

    @Override
    public int getGroupCount() {
        return labOrderDetailModels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.d("getChildrenCount", labOrderDetailModels.get(groupPosition).getChildren().size() + "");
        return 1;
    }

    @Override
    public LabOrderDetailModel getGroup(int groupPosition) {
        return labOrderDetailModels.get(groupPosition);
    }

    @Override
    public LabOrderDetailModel getChild(int groupPosition, int childPosition) {
        return labOrderDetailModels.get(groupPosition).getChildren().get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        final LabOrderDetailModel headerTitle = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.lab_list_saved_group, parent, false);
        }
        CheckBox cb_select_row_level_first = (CheckBox) convertView.findViewById(R.id.cb_select_row_level_first);
        cb_select_row_level_first.setVisibility(View.GONE);
        ImageButton indicator = (ImageButton) convertView.findViewById(R.id.indicator);
        int imageResourceId = isExpanded ? R.mipmap.minus
                : R.mipmap.plus;

        if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNaturePackage || headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureGroup) {
            indicator.setVisibility(View.VISIBLE);
            indicator.setImageResource(imageResourceId);
        } else if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureTest) {
            indicator.setVisibility(View.INVISIBLE);
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

        //cb_select_row_level_first.setChecked(headerTitle.isSelected());
/*        cb_select_row_level_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cb_click", groupPosition + "");
                if (!headerTitle.isSelected()) {
                    selectAll(headerTitle);


                } else {
                    unselectAll(headerTitle);
                }
                totalAmount = 0.0;
                for (int i = 0; i < labOrderDetailModels.size(); i++) {
                    if (labOrderDetailModels.get(i).isSelected() == true) {
                        totalAmount += Double.parseDouble(labOrderDetailModels.get(i).getLabTestCost());
                    }
                }
                tv_total_amount.setText(totalAmount + "");
            }
        });*/
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.d("status", "true");
        final CustomExpListView secondLevelExpListView = new CustomExpListView(this.mContext);
        ArrayList<LabOrderDetailModel> parentNode = labOrderDetailModels.get(groupPosition).getChildren();
        secondLevelAdapter = new SecondLevelAdapter(this.mContext, parentNode, groupPosition) {
            @Override
            public void OnIndicatorClick(boolean isExpanded, int position) {
                if (isExpanded) {
                    secondLevelExpListView.collapseGroup(position);
                } else {
                    secondLevelExpListView.expandGroup(position);
                }
            }

            @Override
            public void OnTextClick() {
                //Do whatever you want to do on text click
            }
        };
        secondLevelExpListView.setAdapter(secondLevelAdapter);
        secondLevelExpListView.setGroupIndicator(null);
        return secondLevelExpListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class SecondLevelAdapter extends BaseExpandableListAdapter {
        private final Context context;
        ArrayList<LabOrderDetailModel> parentNode;
        public int gp;

        public SecondLevelAdapter(Context context, ArrayList<LabOrderDetailModel> parentNode,  int gp) {

            this.context = context;
            this.parentNode = parentNode;
            this.gp = gp;
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
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.lab_list_saved_item, parent, false);
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
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.lab_list_saved_group_second, parent, false);
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
            TextView tv_test_cost_second = (TextView) convertView.findViewById(R.id.tv_test_cost_second);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getLabTestName());
            tv_test_cost_second.setText(headerTitle.getLabTestCost().toString());
            cb_select_row_level_second.setChecked(headerTitle.isSelected());
            cb_select_row_level_second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (headerTitle.isSelected() == true) {
                        headerTitle.setSelected(false);
                        LabSavedOrderDialogAdapter.this.labOrderDetailModels.get(gp).setSelected(false);
                    } else {
                        headerTitle.setSelected(true);
                        for (int i = 0; i < parentNode.size(); i++) {
                            if (parentNode.get(i).isSelected() == false) {
                                break;
                            } else {
                                if ((parentNode.size() - 1) == i) {
                                    LabSavedOrderDialogAdapter.this.labOrderDetailModels.get(gp).setSelected(true);
                                }
                            }
                        }
                    }
                    LabSavedOrderDialogAdapter.this.notifyDataSetChanged();
                }
            });

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headerTitle.isSelected()) {
                    headerTitle.setSelected(false);
                } else {
                    headerTitle.setSelected(true);
                }
            }
        });*/

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

}

