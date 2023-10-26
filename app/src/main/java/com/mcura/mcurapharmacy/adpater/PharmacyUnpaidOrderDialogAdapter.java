package com.mcura.mcurapharmacy.adpater;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.Constants;
import com.mcura.mcurapharmacy.model.FollowUpModel;
import com.mcura.mcurapharmacy.model.PharmacyModel;
import com.mcura.mcurapharmacy.model.PharmacyOrderDetailModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mCURA1 on 4/13/2017.
 */

public class PharmacyUnpaidOrderDialogAdapter extends BaseAdapter {
    private int prescriptionOrderId;
    private String patContact;
    private int frontOfficeUserRoleId;
    private int subTanentId;
    private int scheduleId;
    private SharedPreferences mSharedPreference;
    private LayoutInflater mInflater;
    Context context;
    public MCuraApplication mCuraApplication;
    private ProgressDialog progress;
    private boolean[] isChecked;
    Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();
    ArrayList<FollowUpModel> followUpModelArrayList;
    PharmacyOrderDetailModel[] pharmacyOrderDetailModels;
    TextView tv_total_amount;
    int noOfDays = 0, selectedDays = 0;
    TextView btn_paynow;
    private double totalAmount = 0.0;
    private String paymentMode = "cash";
    String hospitalId;
    int userRoleId, mrno;
    ImageView tv_check_uncheck;
    private boolean isAllChecked;
    private ViewHolder holder;
    private String hId;

    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(context);
            progress.setCancelable(false);
            progress.setMessage(context.getString(R.string.loading_message));
        }
        progress.show();
    }

    public void dismissLoadingDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    public PharmacyUnpaidOrderDialogAdapter(final Context context, ArrayList<FollowUpModel> followUpModelArrayList, PharmacyModel pharmacyModel, PharmacyOrderDetailModel[] pharmacyOrderDetailModels, TextView tv_total_amount, TextView btn_paynow, final ImageView tv_check_uncheck) {
        this.context = context;
        this.tv_check_uncheck = tv_check_uncheck;
        this.btn_paynow = btn_paynow;
        this.userRoleId = pharmacyModel.getUserRoleId();
        this.hospitalId = pharmacyModel.getHospitalNo();
        this.patContact = pharmacyModel.getMobile().toString();
        this.prescriptionOrderId = pharmacyModel.getPresOrderId();
        this.mrno = pharmacyModel.getMrno();
        this.tv_total_amount = tv_total_amount;
        this.followUpModelArrayList = followUpModelArrayList;
        this.pharmacyOrderDetailModels = pharmacyOrderDetailModels;
        isChecked = new boolean[pharmacyOrderDetailModels.length];
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSharedPreference = context.getSharedPreferences(context.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        String subtanentImagePath = mSharedPreference.getString(Constants.SUB_TANENT_IMAGE_PATH, "default");
        String docProfilePic = mSharedPreference.getString(Constants.USER_PROFILE_PIC, "default");
        scheduleId = mSharedPreference.getInt(Constants.SCHEDULE_ID, 0);
        subTanentId = mSharedPreference.getInt(Constants.SUB_TANENT_ID_KEY, 0);
        frontOfficeUserRoleId = mSharedPreference.getInt(Constants.USER_ROLE_ID_KEY, 0);

        /*btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("jsonObjectHashMap===>"+jsonObjectHashMap.toString());
                postPharmacyOrderDetailsApi();
            }
        });*/
        tv_check_uncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllChecked) {
                    tv_check_uncheck.setImageResource(R.mipmap.all_uncheck);
                    unselectAll();
                    isAllChecked = false;
                } else {
                    tv_check_uncheck.setImageResource(R.mipmap.all_check);
                    selectAll();
                    isAllChecked = true;
                }
            }
        });
        selectAll();
    }

    public void selectAll() {
        totalAmount = 0.0;
        for (int i = 0; i < pharmacyOrderDetailModels.length; i++) {
            totalAmount+=pharmacyOrderDetailModels[i].getOrderedUnitAmount();
        }
        tv_total_amount.setText("₹ " + (Math.round(totalAmount * 100.0) / 100.0));
        notifyDataSetChanged();
    }

    public void unselectAll() {
        for (int i = 0; i < isChecked.length; i++) {
            isChecked[i] = false;
        }
        tv_total_amount.setText("₹ " + (Math.round(totalAmount * 100.0) / 100.0));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pharmacyOrderDetailModels.length;
    }

    @Override
    public PharmacyOrderDetailModel getItem(int position) {
        return pharmacyOrderDetailModels[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PharmacyOrderDetailModel pharmacyOrderDetailModel = pharmacyOrderDetailModels[position];
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pharmacy_unpaid_order_dialog_row_layout, null);
            holder = new ViewHolder();
            holder.ll_medicine_detail = (LinearLayout) convertView.findViewById(R.id.ll_medicine_detail);
            holder.cb_select_row = (CheckBox) convertView.findViewById(R.id.cb_select_row);
            holder.spinner_duration = (TextView) convertView.findViewById(R.id.spinner_duration);
            holder.medicine_name = (TextView) convertView.findViewById(R.id.medicine_name);
            //holder.tv_prescribed_unit = (TextView) convertView.findViewById(R.id.tv_prescribed_unit);
            //holder.tv_minimum_unit = (TextView) convertView.findViewById(R.id.tv_minimum_unit);
            holder.tv_billabe_unit = (TextView) convertView.findViewById(R.id.tv_billabe_unit);
            holder.tv_billabe_amount = (TextView) convertView.findViewById(R.id.tv_billabe_amount);
            holder.tv_price_per_unit = (TextView) convertView.findViewById(R.id.tv_price_per_unit);
            holder.genric_name = (TextView) convertView.findViewById(R.id.genric_name);
            holder.tv_med_form = (TextView) convertView.findViewById(R.id.tv_med_form);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }            //viewHolder.text.setText(list.get(position).getName());
        /*if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#f6f6f6"));
        }*/
        holder.medicine_name.setText(pharmacyOrderDetailModel.getDrug().getBrandName().toString());
        holder.genric_name.setText(pharmacyOrderDetailModel.getDrug().getGenericName().toString());
        //holder.tv_prescribed_unit.setText(pharmacyOrderDetailModel.getPrescribedUnit().toString());
        //holder.tv_minimum_unit.setText(pharmacyOrderDetailModel.getDrug().getMinimumUnit().toString());
        String medicineForm = pharmacyOrderDetailModel.getDrug().getForm().toString();
        String medForm = "";
        if(!medicineForm.equals("")) {
            String medicineFormArray[] = medicineForm.split("/");
            for (int i = 0; i < medicineFormArray.length; i++) {
                medForm += medicineFormArray[i].substring(0, 3);
                if (i != medicineFormArray.length - 1) {
                    medForm += "/";
                }
            }
        }
        Log.d("medForm", medForm);
        holder.tv_med_form.setText("(" + medForm + ")");
        holder.tv_price_per_unit.setText("₹ " + pharmacyOrderDetailModel.getDrug().getUnitPrice() + "/unit");
        holder.ll_medicine_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMedicineDetailDialog(pharmacyOrderDetailModel);
            }
        });
        holder.spinner_duration.setText(pharmacyOrderDetailModel.getOrderedFollowupDurationno()+" "+pharmacyOrderDetailModel.getOrderedFollowupDurationUnit());

        holder.tv_billabe_unit.setText("Qty. " + pharmacyOrderDetailModel.getOrderedUnit() + " ");
        holder.tv_billabe_amount.setText("₹ " + pharmacyOrderDetailModel.getOrderedUnitAmount() + "");
        return convertView;
    }

    private void showMedicineDetailDialog(PharmacyOrderDetailModel pharmacyOrderDetailModel) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f);
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.medicine_detail_dialog);
        TextView medicine_name = (TextView) dialog.findViewById(R.id.medicine_name);
        medicine_name.setText(pharmacyOrderDetailModel.getDrug().getBrandName().toString());
        TextView medicine_strength_form = (TextView) dialog.findViewById(R.id.medicine_strength_form);
        medicine_strength_form.setText(pharmacyOrderDetailModel.getDrug().getStrength().toString() + " | " + pharmacyOrderDetailModel.getDrug().getForm().toString());
        TextView genric_name = (TextView) dialog.findViewById(R.id.genric_name);
        genric_name.setText(pharmacyOrderDetailModel.getDrug().getGenericName().toString());
        ImageButton close_dialog = (ImageButton) dialog.findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width * 0.9);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 1f; // Transparency
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private class ViewHolder {
        CheckBox cb_select_row;
        TextView spinner_duration;
        TextView tv_priceperunit;
        LinearLayout ll_medicine_detail;
        TextView medicine_name, tv_med_form, tv_prescribed_unit, tv_minimum_unit, tv_billabe_unit, tv_billabe_amount, tv_price_per_unit, genric_name;
    }

    public int getIndex(int followUpId) {
        for (int i = 0; i < followUpModelArrayList.size(); i++) {
            FollowUpModel auction = followUpModelArrayList.get(i);
            if ((followUpId + "").equals(auction.getFollowupId())) {
                return i;
            }
        }
        return -1;
    }

    private int getNoOfUnitForDays(int selectedDays, int days, int prescribedUnit) {
        Log.d("prescribedUnit", prescribedUnit + "");
        Log.d("selectedDays", selectedDays + "");
        Log.d("days", days + "");
        int noOfUnit = (prescribedUnit * selectedDays) / days;
        Log.d("noOfUnit", noOfUnit + "");
        return noOfUnit;
    }
}
