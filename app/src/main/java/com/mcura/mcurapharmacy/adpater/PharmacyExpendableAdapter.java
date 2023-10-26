package com.mcura.mcurapharmacy.adpater;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import androidx.core.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mcura.mcurapharmacy.Helper.Helper;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.activity.LabOrderActivity;
import com.mcura.mcurapharmacy.activity.PharmacyActivity;
import com.mcura.mcurapharmacy.activity.ViewPDFActivity;
import com.mcura.mcurapharmacy.activity.VisitSummaryActivity;
import com.mcura.mcurapharmacy.com.allen.expandablelistview.BaseSwipeMenuExpandableListAdapter;
import com.mcura.mcurapharmacy.com.baoyz.swipemenulistview.ContentViewWrapper;
import com.mcura.mcurapharmacy.model.FollowUpModel;
import com.mcura.mcurapharmacy.model.PharmacyChildDatum;
import com.mcura.mcurapharmacy.model.PharmacyChildTokenDatum;
import com.mcura.mcurapharmacy.model.PharmacyModel;
import com.mcura.mcurapharmacy.model.PharmacyOrderDetailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mCURA1 on 6/14/2017.
 */

public class PharmacyExpendableAdapter extends BaseSwipeMenuExpandableListAdapter {
    private Context mContext;
    private ProgressDialog progress;
    private MCuraApplication mCuraApplication;
    AlertDialog mainPresDialog = null;
    private ArrayList<PharmacyModel> testPharmacy = new ArrayList<>();
    private ArrayList<PharmacyModel> originalList;
    private ArrayList<PharmacyModel> pharmacyModelList;
    private List<PharmacyModel> pharmacyData = new ArrayList<>();
    public PharmacyExpendableAdapter(Context context, List<PharmacyModel> data) {
        this.mContext = context;
        //data = new ArrayList<>();
        pharmacyModelList = new ArrayList<PharmacyModel>();
        this.pharmacyData = data;
        /*Log.d("orderKey",PharmacyActivity.orderKey+"");
        if (PharmacyActivity.orderKey == 1) {
            pharmacyModelList.clear();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getStatusId() >= 0) {
                    pharmacyModelList.add(data.get(i));
                }
            }
        } else if (PharmacyActivity.orderKey == 0) {
            pharmacyModelList.clear();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getStatusId() == 0) {
                    pharmacyModelList.add(data.get(i));
                }
            }
        }
        originalList = new ArrayList<>();
        testPharmacy.clear();
        for (int i = 0; i < pharmacyModelList.size(); i++) {
            testPharmacy.add(pharmacyModelList.get(i));
        }
        originalList.addAll(testPharmacy);*/
        reloadData();
    }

    public void reloadData(){
        if (PharmacyActivity.orderKey == 1) {
            pharmacyModelList.clear();
            for (int i = 0; i < pharmacyData.size(); i++) {
                if (pharmacyData.get(i).getStatusId() == 8) {
                    pharmacyModelList.add(pharmacyData.get(i));
                }
            }
            for (int i = 0; i < pharmacyData.size(); i++) {
                if (pharmacyData.get(i).getStatusId() == 3) {
                    pharmacyModelList.add(pharmacyData.get(i));
                }
            }
            for (int i = 0; i < pharmacyData.size(); i++) {
                if (pharmacyData.get(i).getStatusId() == 7) {
                    pharmacyModelList.add(pharmacyData.get(i));
                }
            }
            for (int i = 0; i < pharmacyData.size(); i++) {
                if (pharmacyData.get(i).getStatusId() == 12) {
                    pharmacyModelList.add(pharmacyData.get(i));
                }
            }
        } else if (PharmacyActivity.orderKey == 0) {
            pharmacyModelList.clear();
            for (int i = 0; i < pharmacyData.size(); i++) {
                if (pharmacyData.get(i).getStatusId() == 0) {
                    pharmacyModelList.add(pharmacyData.get(i));
                }
            }
        }
        originalList = new ArrayList<>();
        testPharmacy.clear();
        for (int i = 0; i < pharmacyModelList.size(); i++) {
            testPharmacy.add(pharmacyModelList.get(i));
        }
        originalList.addAll(testPharmacy);
        notifyDataSetChanged();
    }

    // This Function used to inflate parent rows view
    public void OnIndicatorClick(boolean isExpanded, int position) {

    }

    public void OnTextClick() {

    }

    @Override
    public boolean isGroupSwipable(int groupPosition) {
        return false;
    }

    @Override
    public boolean isChildSwipable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public ContentViewWrapper getGroupViewAndReUsable(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        boolean reUseable = true;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.pharmacy_pending_order_row_layout, null);
            convertView.setTag(new ViewHolder(convertView));
            reUseable = false;
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        final PharmacyModel pharmacyModel = (PharmacyModel) getGroup(groupPosition);
        holder.tv_order_id.setText(pharmacyModel.getPresOrderId().toString());
        holder.tv_doc_name.setText(pharmacyModel.getDoctorName().toString());
        holder.tv_patient_name.setText(pharmacyModel.getPatname().toString());
        holder.tv_patient_contact.setText(pharmacyModel.getMobile().toString().trim());

        if (PharmacyActivity.orderKey == 1) {
            holder.tv_token_no.setVisibility(View.VISIBLE);
            if (pharmacyModel.getStatusId() != 0) {
                String tokenNo = "";
                for (PharmacyChildTokenDatum pharmacyChildTokenDatum : pharmacyModel.getPharmacyChildTokenData()) {
                    if (pharmacyChildTokenDatum.getCounterId() == Integer.parseInt(PharmacyActivity.counterNo)) {
                        tokenNo = pharmacyChildTokenDatum.getTokenNo().toString();
                        Log.d("tokenNo", tokenNo);
                        Log.d("tokenNo", PharmacyActivity.counterNo);
                        break;
                    }else{
                        Log.d("counterNo", PharmacyActivity.counterNo);
                    }
                }
                holder.tv_token_no.setText(tokenNo);
                if (pharmacyModel.getStatusId().toString().equals("3")) {
                    convertView.setBackgroundResource(R.drawable.border_side_color_yellow_layout);
                } else if (pharmacyModel.getStatusId().toString().equals("8")) {
                    convertView.setBackgroundResource(R.drawable.border_side_color_blue_layout);
                } else if (pharmacyModel.getStatusId().toString().equals("7")) {
                    convertView.setBackgroundResource(R.drawable.border_side_color_green_layout);
                }else if (pharmacyModel.getStatusId().toString().equals("12")) {
                    convertView.setBackgroundResource(R.drawable.border_side_color_red_layout);
                }
            } else {
                convertView.setBackgroundResource(R.drawable.border_side_color_yellow_layout);
            }
        } else if (PharmacyActivity.orderKey == 0) {
            holder.tv_token_no.setVisibility(View.GONE);
            convertView.setBackgroundResource(R.drawable.pharmacy_pending_order_row_layout_bg);
        }
        int imageResourceId = isExpanded ? R.mipmap.arrow_down
                : R.mipmap.arrow_left;
        holder.iv_open_child.setImageResource(imageResourceId);
        holder.iv_open_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIndicatorClick(isExpanded, groupPosition);
            }
        });
        holder.iv_viewrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getPatientRecord(pharmacyModel);
                if (!pharmacyModel.getLastVisitSummaryPath().equals("")) {
                    mContext.startActivity(new Intent(mContext, ViewPDFActivity.class).putExtra("pdf", pharmacyModel.getLastVisitSummaryPath()).putExtra("patname", pharmacyModel.getPatname().toString()).putExtra("patcontact", pharmacyModel.getMobile().toString()));
                } else {
                    Toast.makeText(mContext, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPharmacyOrderDetailApi(pharmacyModel);
            }
        });
        return new ContentViewWrapper(convertView, reUseable);
    }

    @Override
    public ContentViewWrapper getChildViewAndReUsable(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        boolean reUseable = true;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_list_app, null);
            convertView.setTag(new ViewHolder(convertView));
            reUseable = false;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (null == holder) {
            holder = new ViewHolder(convertView);
        }

        PharmacyChildDatum pharmacyChildDatum = (PharmacyChildDatum) getChild(groupPosition, childPosition);
        holder.tv_txn_id.setText("Txn id: " + pharmacyChildDatum.getOrderTransactionId()+"");
        holder.tv_ord_amount.setText((Math.round(pharmacyChildDatum.getOrderedAmount() * 100.0) / 100.0)+"");
        if (pharmacyChildDatum.getOrderStatus() == 3) {
            holder.tv_delivery_date.setVisibility(View.GONE);
            holder.tv_ord_amount.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
            holder.iv_payment_status.setImageResource(R.mipmap.cart);
            holder.iv_payment_status.setVisibility(View.VISIBLE);
            convertView.setBackgroundResource(R.drawable.childview_bg);
        } else if (pharmacyChildDatum.getOrderStatus() == 8) {
            holder.tv_delivery_date.setVisibility(View.GONE);
            holder.tv_ord_amount.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            holder.iv_payment_status.setImageResource(R.mipmap.cart);
            holder.iv_payment_status.setVisibility(View.VISIBLE);
            convertView.setBackgroundResource(R.drawable.childview_bg);
        } else if (pharmacyChildDatum.getOrderStatus() == 7) {
            holder.tv_delivery_date.setVisibility(View.VISIBLE);
            holder.tv_ord_amount.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            holder.iv_payment_status.setImageResource(R.mipmap.cart_g);
            holder.iv_payment_status.setVisibility(View.VISIBLE);
            convertView.setBackgroundResource(R.drawable.childview_bg);
        } else if (pharmacyChildDatum.getOrderStatus() == 12) {
            holder.tv_delivery_date.setVisibility(View.GONE);
            holder.tv_ord_amount.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.iv_payment_status.setImageResource(R.mipmap.cart);
            holder.iv_payment_status.setVisibility(View.GONE);
            convertView.setBackgroundResource(R.drawable.child_cancel_bg);
        }


        holder.tv_ordered_date.setText(Helper.changeDateFormat(pharmacyChildDatum.getOrderedDate()));
        if (pharmacyChildDatum.getOrderStatus() == 7) {
            holder.tv_delivery_date.setText(Helper.changeDateFormat(pharmacyChildDatum.getDeliveredDate()));
        }
        return new ContentViewWrapper(convertView, reUseable);
    }



    @Override
    public int getGroupCount() {
        return testPharmacy.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return testPharmacy.get(groupPosition).getPharmacyChildData().size();
    }

    @Override
    public PharmacyModel getGroup(int groupPosition) {
        return testPharmacy.get(groupPosition);
    }

    @Override
    public PharmacyChildDatum getChild(int groupPosition, int childPosition) {
        return testPharmacy.get(groupPosition).getPharmacyChildData().get(childPosition);
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
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ViewHolder {
        TextView tv_order_id, tv_doc_name, tv_patient_name, tv_status, tv_datetime, tv_payment_status, tv_patient_contact,tv_token_no;
        TextView tv_est_id, tv_days, tv_amount;
        Button btn_pay;
        Button iv_viewrecord;
        ImageButton iv_open_child;
        LinearLayout ll_main, ll_bottom_layout;

        //will be delete
        TextView tv_txn_id;
        TextView tv_ord_amount, tv_delivery_date, tv_ordered_date;
        ImageView iv_payment_status;
        //will be delete

        public ViewHolder(View convertView) {
//will be delete
            tv_token_no = (TextView) convertView.findViewById(R.id.tv_token_no);
            tv_txn_id = (TextView) convertView.findViewById(R.id.tv_txn_id);
            tv_ord_amount = (TextView) convertView.findViewById(R.id.tv_ord_amount);
            tv_ordered_date = (TextView) convertView.findViewById(R.id.tv_ordered_date);
            tv_delivery_date = (TextView) convertView.findViewById(R.id.tv_delivery_date);
            iv_payment_status = (ImageView) convertView.findViewById(R.id.iv_payment_status);
//will be delete
            //ll_main = (LinearLayout) convertView.findViewById(R.id.ll_main);
            tv_order_id = (TextView) convertView.findViewById(R.id.tv_order_id);
            tv_doc_name = (TextView) convertView.findViewById(R.id.tv_doc_name);
            tv_patient_name = (TextView) convertView.findViewById(R.id.tv_patient_name);
            //holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            //holder.tv_payment_status = (TextView) convertView.findViewById(R.id.tv_payment_status);
            tv_patient_contact = (TextView) convertView.findViewById(R.id.tv_patient_contact);
            iv_viewrecord = (Button) convertView.findViewById(R.id.iv_view_record);
            iv_open_child = (ImageButton) convertView.findViewById(R.id.iv_open_child);

            convertView.setTag(this);
        }
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

    private void callPharmacyOrderDetailApi(final PharmacyModel pharmacyModel) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.pharmacyOrderdetail(pharmacyModel.getPrescriptionId(), pharmacyModel.getUserRoleId(), new Callback<PharmacyOrderDetailModel[]>() {
            @Override
            public void success(PharmacyOrderDetailModel[] pharmacyOrderDetailModels, Response response) {
                showPharmacyOrderDialog(pharmacyOrderDetailModels, pharmacyModel);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showPharmacyOrderDialog(PharmacyOrderDetailModel[] pharmacyOrderDetailModels, PharmacyModel pharmacyModel) {
        ArrayList<FollowUpModel> followUpModelArrayList = null;
        try {
            JSONArray m_jArry = new JSONArray(((PharmacyActivity) mContext).loadJSONFromAsset());
            followUpModelArrayList = new ArrayList<FollowUpModel>();

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                FollowUpModel followUpModel = new FollowUpModel();
                followUpModel.setFollowupUnit(jo_inside.getString("followupunit"));
                followUpModel.setFollowupId(jo_inside.getString("followupid"));
                followUpModel.setFollowupNumber(jo_inside.getString("followupnumber"));
                followUpModelArrayList.add(followUpModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View convertView = (View) inflater.inflate(R.layout.pharmacy_main_order_dialog, null);
        builder.setView(convertView);
        ImageButton ib_close = (ImageButton) convertView.findViewById(R.id.ib_close);
        TextView tv_total_amount = (TextView) convertView.findViewById(R.id.tv_total_amount);
        ListView pharmacy_order_listview = (ListView) convertView.findViewById(R.id.pharmacy_order_listview);
        ImageView tv_check_uncheck = (ImageView) convertView.findViewById(R.id.tv_check_uncheck);

        TextView tv_consultant_name = (TextView) convertView.findViewById(R.id.tv_consultant_name);
        TextView tv_ord_id = (TextView) convertView.findViewById(R.id.tv_ord_id);
        TextView tv_pat_name = (TextView) convertView.findViewById(R.id.tv_pat_name);
        TextView tv_pat_dob_age = (TextView) convertView.findViewById(R.id.tv_pat_dob_age);
        TextView tv_pat_contact = (TextView) convertView.findViewById(R.id.tv_pat_contact);
        TextView btn_paynow = (TextView) convertView.findViewById(R.id.btn_paynow);
        TextView btn_ordernow = (TextView) convertView.findViewById(R.id.btn_ordernow);

        tv_consultant_name.setText(pharmacyModel.getDoctorName().toString());
        tv_pat_name.setText(pharmacyModel.getPatname().toString());
        tv_pat_contact.setText(pharmacyModel.getMobile().toString());
        tv_ord_id.setText("Ord Id: " + pharmacyModel.getPresOrderId().toString());
        String patDob = pharmacyModel.getDob().toString();
        int patGender = pharmacyModel.getGenderId();
        String timestamp = patDob.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date();
        try {
            createdOn  = new Date(Long.parseLong(timestamp));
        }catch (NumberFormatException  numberFormatException){
        }         SimpleDateFormat sdf = new SimpleDateFormat("MM,dd,yyyy");
        String formattedDate = sdf.format(createdOn);
        System.out.println("formattedDate-->" + formattedDate);
        String[] out = formattedDate.split(",");

        int year = Integer.parseInt(out[2]);
        int month = Integer.parseInt(out[0]);
        int day = Integer.parseInt(out[1]);
        String patAgeGender;
        if (patGender == 1) {
            patAgeGender = Helper.getAge(year, month, day) + "/M";
        } else {
            patAgeGender = Helper.getAge(year, month, day) + "/F";
        }
        tv_pat_dob_age.setText(patAgeGender);
        mainPresDialog = builder.show();


        for (int i = 0; i < pharmacyOrderDetailModels.length; i++) {
            int selectedDays = 0, noOfDays = 0;
            if (pharmacyOrderDetailModels[i].getFollowupDurationUnit().startsWith("Day")) {
                selectedDays = pharmacyOrderDetailModels[i].getFollowupDurationno();
            } else if (pharmacyOrderDetailModels[i].getFollowupDurationUnit().startsWith("Week")) {
                selectedDays = pharmacyOrderDetailModels[i].getFollowupDurationno() * 7;
            } else if (pharmacyOrderDetailModels[i].getFollowupDurationUnit().startsWith("Month")) {
                selectedDays = pharmacyOrderDetailModels[i].getFollowupDurationno() * 30;
            }
            noOfDays = selectedDays;
            double prescribedUnit = (double) (pharmacyOrderDetailModels[i].getPrescribedPack() * pharmacyOrderDetailModels[i].getDrug().getUnitsPerMinPack());
            int noOfUnit = getNoOfUnitForDays(selectedDays, noOfDays, prescribedUnit);
            if (noOfUnit < pharmacyOrderDetailModels[i].getDrug().getMinimumUnit()) {
                noOfUnit = pharmacyOrderDetailModels[i].getDrug().getMinimumUnit();
            }
            int minBillableUnit;
            Log.d("noOfUnit", noOfUnit + "");
            if (pharmacyOrderDetailModels[i].getMinbillableUnit() == 0) {
                minBillableUnit = 1;
            } else {
                minBillableUnit = pharmacyOrderDetailModels[i].getMinbillableUnit();
            }
            int extraPack = noOfUnit % minBillableUnit;
            if (extraPack > 0) {
                extraPack = 1;
            } else {
                extraPack = 0;
            }
            int noOfPacks = (noOfUnit / minBillableUnit) + extraPack;
            int billableUnit = noOfPacks * pharmacyOrderDetailModels[i].getDrug().getUnitsPerMinPack();
            double cost = pharmacyOrderDetailModels[i].getDrug().getCostOfMinPack() * noOfPacks;
            pharmacyOrderDetailModels[i].setBillableAmount((Math.round(cost * 100.0) / 100.0));
            pharmacyOrderDetailModels[i].setBillableUnit(billableUnit);
        }


        PharmacyOrderDialogAdapter pharmacyOrderDialogAdapter = new PharmacyOrderDialogAdapter(mContext, pharmacyModel, followUpModelArrayList, pharmacyOrderDetailModels, tv_total_amount, btn_paynow, tv_check_uncheck, btn_ordernow, mainPresDialog);
        pharmacy_order_listview.setAdapter(pharmacyOrderDialogAdapter);

        mainPresDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mainPresDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        Window dialogWindow = mainPresDialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.alpha = 1.0f; // Transparency
        dialogWindow.setAttributes(lp);
        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresDialog.dismiss();
            }
        });
    }
    private int getNoOfUnitForDays(int selectedDays, int days, double prescribedUnit) {
        Log.d("prescribedUnit", prescribedUnit + "");
        Log.d("selectedDays", selectedDays + "");
        Log.d("days", days + "");
        int noOfUnit = (int) (((prescribedUnit * selectedDays) / days)+0.9);
        Log.d("noOfUnit", noOfUnit + "");
        return noOfUnit;
    }
    public void filterData(String query) {
        if (PharmacyActivity.searchBy.equals("Patient")) {
            query = query.toLowerCase().trim();
            testPharmacy.clear();
            if (query.isEmpty()) {
                testPharmacy.addAll(originalList);
            } else {
                for (PharmacyModel continent : originalList) {
                    if (continent.getPatname().toLowerCase().trim().contains(query)) {
                        testPharmacy.add(continent);
                    }
                }
                Log.d("testPharmacy_size", testPharmacy.size() + "");
            }
            notifyDataSetChanged();
        }if (PharmacyActivity.searchBy.equals("Doctor")) {
            query = query.toLowerCase().trim();
            testPharmacy.clear();
            if (query.isEmpty()) {
                testPharmacy.addAll(originalList);
            } else {
                for (PharmacyModel continent : originalList) {
                    if (continent.getDoctorName().toLowerCase().trim().contains(query)) {
                        testPharmacy.add(continent);
                    }
                }
                Log.d("testPharmacy_size", testPharmacy.size() + "");
            }
            notifyDataSetChanged();
        }
        if (LabOrderActivity.searchBy.equals("Select")) {
            query = query.toLowerCase().trim();
            testPharmacy.clear();
            if (query.isEmpty()) {
                testPharmacy.addAll(originalList);
            }
            notifyDataSetChanged();
        }
        if (PharmacyActivity.searchBy.equals("Pharmacy User")) {
            query = query.toLowerCase();
            testPharmacy.clear();
            if (query.isEmpty() || PharmacyActivity.counterNo.equals("0")) {
                testPharmacy.addAll(originalList);
            } else {
                for (int i = 0; i < originalList.size(); i++) {
                    for (int j = 0; j < originalList.get(i).getPharmacyChildTokenData().size(); j++) {
                        if (originalList.get(i).getPharmacyChildTokenData().get(j).getCounterId().toString().equals(query)) {
                            testPharmacy.add(originalList.get(i));
                        }
                    }
                }
                Log.d("testPharmacy_size", testPharmacy.size() + "");
            }
            notifyDataSetChanged();
        }
    }

}
