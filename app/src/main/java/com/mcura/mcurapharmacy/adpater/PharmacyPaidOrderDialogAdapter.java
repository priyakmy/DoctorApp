package com.mcura.mcurapharmacy.adpater;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.Constants;
import com.mcura.mcurapharmacy.model.FollowUpModel;
import com.mcura.mcurapharmacy.model.LabPharmacyPostResponseModel;
import com.mcura.mcurapharmacy.model.PharmacyOrderDetailModel;
import com.mcura.mcurapharmacy.model.PostPaymentModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mCURA1 on 4/13/2017.
 */

public class PharmacyPaidOrderDialogAdapter extends BaseAdapter {
    private String patContact;
    private int frontOfficeUserRoleId;
    private int subTanentId;
    private int scheduleId;
    private SharedPreferences mSharedPreference;
    private LinkedHashMap<Integer, JsonObject> jsonObjectHashMap;
    private LayoutInflater mInflater;
    Context context;
    public MCuraApplication mCuraApplication;
    private ProgressDialog progress;
    private boolean[] isChecked;
    Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();
    ArrayList<FollowUpModel> followUpModelArrayList;
    PharmacyOrderDetailModel[] pharmacyOrderDetailModels;
    TextView tv_total_amount;
    int noOfDays=0,selectedDays = 0;
    Button btn_paynow;
    private int totalAmount=0;
    private String paymentMode="cash";
    String hospitalId;
    int userRoleId, mrno;
    ImageView tv_check_uncheck;
    private boolean isAllChecked;
    private ViewHolder holder;
    private JsonObject ordDetailObj;

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
    public PharmacyPaidOrderDialogAdapter(final Context context, ArrayList<FollowUpModel> followUpModelArrayList, PharmacyOrderDetailModel[] pharmacyOrderDetailModels, TextView tv_total_amount, Button btn_paynow, String hospitalId, int userRoleId, int mrno, String patContact, final ImageView tv_check_uncheck) {
        this.context = context;
        this.tv_check_uncheck = tv_check_uncheck;
        this.btn_paynow = btn_paynow;
        this.userRoleId = userRoleId;
        this.hospitalId = hospitalId;
        this.patContact = patContact;
        this.mrno = mrno;
        this.tv_total_amount = tv_total_amount;
        this.followUpModelArrayList = followUpModelArrayList;
        this.pharmacyOrderDetailModels = pharmacyOrderDetailModels;
        isChecked = new boolean[pharmacyOrderDetailModels.length];
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        jsonObjectHashMap = new LinkedHashMap<Integer, JsonObject>();
        mSharedPreference = context.getSharedPreferences(context.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        String subtanentImagePath = mSharedPreference.getString(Constants.SUB_TANENT_IMAGE_PATH, "default");
        String docProfilePic = mSharedPreference.getString(Constants.USER_PROFILE_PIC, "default");
        scheduleId = mSharedPreference.getInt(Constants.SCHEDULE_ID, 0);
        subTanentId = mSharedPreference.getInt(Constants.SUB_TANENT_ID_KEY, 0);
        frontOfficeUserRoleId = mSharedPreference.getInt(Constants.USER_ROLE_ID_KEY, 0);

        btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("jsonObjectHashMap===>"+jsonObjectHashMap.toString());
                //postPharmacyOrderDetailsApi();
            }
        });
        tv_check_uncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAllChecked){
                    tv_check_uncheck.setImageResource(R.mipmap.all_uncheck);
                    unselectAll();
                    isAllChecked = false;
                }else{
                    tv_check_uncheck.setImageResource(R.mipmap.all_check);
                    selectAll();
                    isAllChecked = true;
                }
            }
        });
    }
    public void selectAll() {
        jsonObjectHashMap.clear();
        tv_check_uncheck.setImageResource(R.mipmap.all_check);
        totalAmount = 0;
        for (int i = 0; i < isChecked.length; i++) {
            isChecked[i] = true;
        }
        for (int i = 0; i < pharmacyOrderDetailModels.length; i++) {
            ordDetailObj = new JsonObject();
            totalAmount += pharmacyOrderDetailModels[i].getBillableAmount();
            ordDetailObj.addProperty("MedId",pharmacyOrderDetailModels[i].getDrug().getMedId());
            ordDetailObj.addProperty("orderedUnit",pharmacyOrderDetailModels[i].getBillableUnit());
            ordDetailObj.addProperty("orderedAmount",pharmacyOrderDetailModels[i].getBillableAmount());
            jsonObjectHashMap.put(i,ordDetailObj);
        }
        tv_total_amount.setText("₹ " + totalAmount);
        notifyDataSetChanged();
    }

    public void unselectAll() {
        for (int i = 0; i < isChecked.length; i++) {
            isChecked[i] = false;
            jsonObjectHashMap.remove(i);
        }
        tv_total_amount.setText("₹ " + totalAmount);
        notifyDataSetChanged();
    }

/*    private void postPharmacyOrderDetailsApi() {
        JsonObject obj = new JsonObject();
        obj.addProperty("prescriptionId",9909);
        JsonArray jsonArray = new JsonArray();

        Iterator myVeryOwnIterator = jsonObjectHashMap.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            Integer key=(Integer)myVeryOwnIterator.next();
            JsonObject value=(JsonObject)jsonObjectHashMap.get(key);
            Log.d("key",value.get("orderedAmount")+"");
            jsonArray.add(value);
        }
        obj.add("ordDetail",jsonArray);
        System.out.println("jsonObject====>"+obj.toString());
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postPharmacyOrderDetails(obj, new Callback<LabPharmacyPostResponseModel>() {
            @Override
            public void success(LabPharmacyPostResponseModel s, Response response) {
                if(s.getId()==1){
                    Toast.makeText(context,s.getMsg(),Toast.LENGTH_LONG).show();
                }else if(s.getId()==0){
                    Toast.makeText(context,s.getMsg(),Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }*/
   /* private void postPaymentAPI_V1() {
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", hospitalId);
        obj.addProperty("AppnatureId", 0);
        obj.addProperty("UserRoleId", userRoleId);
        obj.addProperty("Description", "");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", totalAmount);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", 6);
        obj.addProperty("Mrno", mrno);
        obj.addProperty("HIS_BillNo", 0);
        obj.addProperty("MobileNo", patContact);
        obj.addProperty("LabOrderID", 0);
        obj.addProperty("PharmacyOrderID", 9909);
        obj.addProperty("ScheduleId", 0);
        obj.addProperty("AppId", 0);
        if (paymentMode.equals("card")) {
            postPaymentAPIForCard(obj);
        }
    }*/
    /*private void postPaymentAPIForCard(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPayment_v2(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getMsg().equals("Balance is less than Bill Amount. Please Add Amount")) {
                    showErrorDialog(postPaymentModel.getMsg());
                } else {

                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }*/
    /*private void showErrorDialog(String msg) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.app.AlertDialog dialog = builder.show();
    }*/
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
            convertView = mInflater.inflate(R.layout.pharmacy_paid_order_dialog_row_layout, null);
            holder = new ViewHolder();
            holder.cb_select_row = (CheckBox) convertView.findViewById(R.id.cb_select_row);
            holder.spinner_duration = (Spinner) convertView.findViewById(R.id.spinner_duration);
            holder.medicine_name = (TextView) convertView.findViewById(R.id.medicine_name);
            holder.tv_priceperunit = (TextView) convertView.findViewById(R.id.tv_priceperunit);
            holder.tv_billabe_unit = (TextView) convertView.findViewById(R.id.tv_billabe_unit);
            holder.tv_billabe_amount = (TextView) convertView.findViewById(R.id.tv_billabe_amount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }            //viewHolder.text.setText(list.get(position).getName());
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#f6f6f6"));
        }
        holder.medicine_name.setText(pharmacyOrderDetailModel.getDrug().getBrandName().toString());

        final ViewHolder finalHolder = holder;
/*        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonObjectHashMap.clear();
                totalAmount = 0;
                if (!finalHolder.cb_select_row.isChecked()) {
                    isChecked[position] = true;
                    //totalAmount += Integer.parseInt(finalHolder.tv_billabe_amount.getText().toString());
                    finalHolder.cb_select_row.setChecked(isChecked[position]);

                    for(int i=0;i<isChecked.length;i++){
                        if(isChecked[i]){
                            tv_check_uncheck.setImageResource(R.mipmap.all_check);
                            isAllChecked = true;
                        }else{
                            tv_check_uncheck.setImageResource(R.mipmap.all_uncheck);
                            isAllChecked = false;
                            break;
                        }
                    }
                } else {
                    isChecked[position] = false;
                    //totalAmount -= Integer.parseInt(finalHolder.tv_billabe_amount.getText().toString());
                    finalHolder.cb_select_row.setChecked(isChecked[position]);
                    jsonObjectHashMap.remove(position);
                    for(int i=0;i<isChecked.length;i++){
                        if(isChecked[i]){
                            tv_check_uncheck.setImageResource(R.mipmap.all_check);
                            isAllChecked = true;
                        }else{
                            tv_check_uncheck.setImageResource(R.mipmap.all_uncheck);
                            isAllChecked = false;
                            break;
                        }
                    }
                }
                for(int i=0;i<pharmacyOrderDetailModels.length;i++){
                    ordDetailObj = new JsonObject();
                    if(isChecked[i]){
                        totalAmount += pharmacyOrderDetailModels[i].getBillableAmount();
                        ordDetailObj.addProperty("MedId",pharmacyOrderDetailModels[i].getDrug().getMedId());
                        ordDetailObj.addProperty("orderedUnit",pharmacyOrderDetailModels[i].getBillableUnit());
                        ordDetailObj.addProperty("orderedAmount",pharmacyOrderDetailModels[i].getBillableAmount());
                        jsonObjectHashMap.put(i,ordDetailObj);
                    }
                }
                tv_total_amount.setText("₹ "+totalAmount);
            }
        });*/
        //holder.cb_select_row.setChecked(isChecked[position]);

        final FollowUpAdapter followUpAdapter = new FollowUpAdapter(context,followUpModelArrayList);
        holder.spinner_duration.setEnabled(false);
        holder.spinner_duration.setAdapter(followUpAdapter);
        holder.spinner_duration.setSelection(getIndex(pharmacyOrderDetailModel.getOrderedFollowupId()));
        if (myMap.containsKey(position)) {
            holder.spinner_duration.setSelection(myMap.get(position));
        }
        final ViewHolder finalHolder1 = holder;
        holder.spinner_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int selectedIndex, long id) {
                totalAmount = 0;
                myMap.put(position, selectedIndex);
                if(followUpAdapter.getItem(selectedIndex).getFollowupUnit().startsWith("Day")){
                    selectedDays = Integer.parseInt(followUpAdapter.getItem(selectedIndex).getFollowupNumber());
                    Log.d("selectedDays",selectedDays+"");
                }else if(followUpAdapter.getItem(selectedIndex).getFollowupUnit().startsWith("Week")){
                    selectedDays = Integer.parseInt(followUpAdapter.getItem(selectedIndex).getFollowupNumber())*7;
                    Log.d("selectedDays",selectedDays+"");
                }else if(followUpAdapter.getItem(selectedIndex).getFollowupUnit().startsWith("Month")){
                    selectedDays = Integer.parseInt(followUpAdapter.getItem(selectedIndex).getFollowupNumber())*30;
                    Log.d("selectedDays",selectedDays+"");
                }
                if(pharmacyOrderDetailModel.getFollowupDurationUnit().startsWith("Day")){
                    noOfDays = pharmacyOrderDetailModel.getFollowupDurationno();
                }else if(pharmacyOrderDetailModel.getFollowupDurationUnit().startsWith("Week")){
                    noOfDays = pharmacyOrderDetailModel.getFollowupDurationno()*7;
                }else if(pharmacyOrderDetailModel.getFollowupDurationUnit().startsWith("Month")){
                    noOfDays = pharmacyOrderDetailModel.getFollowupDurationno()*30;
                }

                double prescribedUnit = (double)(pharmacyOrderDetailModel.getPrescribedPack()*pharmacyOrderDetailModel.getDrug().getUnitsPerMinPack());

                int noOfUnit = getNoOfUnitForDays(selectedDays,noOfDays,prescribedUnit);
                if(noOfUnit<pharmacyOrderDetailModel.getDrug().getMinimumUnit()){
                    noOfUnit=pharmacyOrderDetailModel.getDrug().getMinimumUnit();
                }
                Log.d("noOfUnit",noOfUnit+"");
                int minBillableUnit;
                Log.d("noOfUnit",noOfUnit+"");
                if(pharmacyOrderDetailModel.getMinbillableUnit()==0){
                    minBillableUnit = 1;
                }else{
                    minBillableUnit = pharmacyOrderDetailModel.getMinbillableUnit();
                }
                int extraPack = noOfUnit%minBillableUnit;
                if(extraPack>0){
                    extraPack=1;
                }else{
                    extraPack=0;
                }
                Log.d("extraPack",extraPack+"");
                int noOfPacks = (noOfUnit/minBillableUnit)+extraPack;
                Log.d("noOfPacks",noOfPacks+"");
                int billableUnit = noOfPacks * pharmacyOrderDetailModel.getDrug().getUnitsPerMinPack();
                Log.d("billableUnit",billableUnit+"");
                double cost = pharmacyOrderDetailModel.getDrug().getCostOfMinPack() * noOfPacks;
                Log.d("cost",cost+"");
                pharmacyOrderDetailModels[position].setBillableAmount(cost);
                pharmacyOrderDetailModels[position].setBillableUnit(billableUnit);
                finalHolder1.tv_billabe_unit.setText(billableUnit+"");
                finalHolder1.tv_billabe_amount.setText(cost+"");
                for(int i=0;i<pharmacyOrderDetailModels.length;i++){
                    ordDetailObj = new JsonObject();
                    //if(isChecked[i]){
                    if(pharmacyOrderDetailModels[i].getBillableAmount()!=0.0) {
                        totalAmount += pharmacyOrderDetailModels[i].getBillableAmount();
                    }
                    ordDetailObj.addProperty("MedId",pharmacyOrderDetailModels[i].getDrug().getMedId());
                    ordDetailObj.addProperty("orderedUnit",pharmacyOrderDetailModels[i].getBillableUnit());
                    ordDetailObj.addProperty("orderedAmount",pharmacyOrderDetailModels[i].getBillableAmount());
                    jsonObjectHashMap.put(i,ordDetailObj);
                    //}
                }
                tv_total_amount.setText("₹ "+totalAmount);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return convertView;
    }

    private class ViewHolder {
        CheckBox cb_select_row;
        Spinner spinner_duration;
        TextView medicine_name,tv_priceperunit,tv_billabe_unit,tv_billabe_amount;
    }
    public int getIndex(int followUpId)
    {
        for (int i = 0; i < followUpModelArrayList.size(); i++)
        {
            FollowUpModel auction = followUpModelArrayList.get(i);
            if ((followUpId+"").equals(auction.getFollowupId()))
            {
                return i;
            }
        }
        return -1;
    }

    private int getNoOfUnitForDays(int selectedDays,int days,double prescribedUnit){
        Log.d("prescribedUnit",prescribedUnit+"");
        Log.d("selectedDays",selectedDays+"");
        Log.d("days",days+"");
        double noOfUnit = (prescribedUnit*selectedDays)/days;
        Log.d("noOfUnit",noOfUnit+"");
        return (int)noOfUnit;
    }
}
