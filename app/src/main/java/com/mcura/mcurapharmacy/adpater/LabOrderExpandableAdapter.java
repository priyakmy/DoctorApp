package com.mcura.mcurapharmacy.adpater;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mcura.mcurapharmacy.BuildConfig;
import com.mcura.mcurapharmacy.Helper.Helper;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.EnumType;
import com.mcura.mcurapharmacy.activity.LabOrderActivity;
import com.mcura.mcurapharmacy.activity.ViewPDFActivity;
import com.mcura.mcurapharmacy.activity.VisitSummaryActivity;
import com.mcura.mcurapharmacy.com.allen.expandablelistview.BaseSwipeMenuExpandableListAdapter;
import com.mcura.mcurapharmacy.com.baoyz.swipemenulistview.ContentViewWrapper;
import com.mcura.mcurapharmacy.model.LabChildDatum;
import com.mcura.mcurapharmacy.model.LabChildTokenDatum;
import com.mcura.mcurapharmacy.model.LabDatum;
import com.mcura.mcurapharmacy.model.LabOrderDetailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mCURA1 on 7/13/2017.
 */

public class LabOrderExpandableAdapter extends BaseSwipeMenuExpandableListAdapter {
    private Context mContext;
    private ProgressDialog progress;
    private MCuraApplication mCuraApplication;
    AlertDialog mainLabDialog = null;
    private ArrayList<LabDatum> testPharmacy = new ArrayList<>();
    private ArrayList<LabDatum> originalList;
    private ArrayList<LabDatum> labModelList;
    private List<LabDatum> labData = new ArrayList<>();
    private int tokenNo;
    private String searchKey;

    public LabOrderExpandableAdapter(Context context, List<LabDatum> data) {
        this.mContext = context;
        originalList = new ArrayList<>();
        labModelList = new ArrayList<LabDatum>();
        this.labData = data;
        reloadData();
    }

    public void reloadData() {
        if (LabOrderActivity.orderKey == 1) {
            labModelList.clear();
            for (int i = 0; i < labData.size(); i++) {
                if (labData.get(i).getStatusId() == 10) {
                    labModelList.add(labData.get(i));
                }
            }
            for (int i = 0; i < labData.size(); i++) {
                if (labData.get(i).getStatusId() == 3) {
                    labModelList.add(labData.get(i));
                }
            }
            for (int i = 0; i < labData.size(); i++) {
                if (labData.get(i).getStatusId() == 11) {
                    labModelList.add(labData.get(i));
                }
            }
            for (int i = 0; i < labData.size(); i++) {
                if (labData.get(i).getStatusId() == 12) {
                    labModelList.add(labData.get(i));
                }
            }
        } else if (LabOrderActivity.orderKey == 0) {
            labModelList.clear();
            for (int i = 0; i < labData.size(); i++) {
                if (labData.get(i).getStatusId() == 0) {
                    labModelList.add(labData.get(i));
                }
            }
        }
        originalList = new ArrayList<>();
        testPharmacy.clear();
        for (int i = 0; i < labModelList.size(); i++) {
            testPharmacy.add(labModelList.get(i));
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
        final LabDatum labDatum = (LabDatum) getGroup(groupPosition);
        holder.tv_order_id.setText(labDatum.getLabOrderId().toString());
        holder.tv_doc_name.setText(labDatum.getDoctorName().toString());
        holder.tv_patient_name.setText(labDatum.getPatname().toString());
        holder.tv_patient_contact.setText(labDatum.getMobile().toString().trim());

        if (LabOrderActivity.orderKey == 1) {
            holder.tv_token_no.setVisibility(View.VISIBLE);
            if (labDatum.getStatusId() != 0) {
                String tokenNo = "";
                for (LabChildTokenDatum labChildTokenDatum : labDatum.getLabChildTokenData()) {
                    if (labChildTokenDatum.getCounterId() == Integer.parseInt(LabOrderActivity.counterNo)) {
                        tokenNo = labChildTokenDatum.getTokenNo().toString();
                        Log.d("tokenNo", tokenNo);
                        Log.d("tokenNo", LabOrderActivity.counterNo);
                        break;
                    }
                }
                holder.tv_token_no.setText(tokenNo);
                if (labDatum.getStatusId().toString().equals("3")) {
                    convertView.setBackgroundResource(R.drawable.border_side_color_yellow_layout);
                } else if (labDatum.getStatusId().toString().equals("10")) {
                    convertView.setBackgroundResource(R.drawable.border_side_color_blue_layout);
                } else if (labDatum.getStatusId().toString().equals("11")) {
                    convertView.setBackgroundResource(R.drawable.border_side_color_green_layout);
                } else if (labDatum.getStatusId().toString().equals("12")) {
                    convertView.setBackgroundResource(R.drawable.border_side_color_red_layout);
                }
            } else {
                convertView.setBackgroundResource(R.drawable.border_side_color_yellow_layout);
            }

        } else if (LabOrderActivity.orderKey == 0) {
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
                if (!TextUtils.isEmpty(labDatum.getLastVisitSummaryPath())) {
                    mContext.startActivity(new Intent(mContext, ViewPDFActivity.class).putExtra("pdf", labDatum.getLastVisitSummaryPath()).putExtra("patname", labDatum.getPatname().toString()).putExtra("patcontact", labDatum.getMobile().toString()));
                } else {
                    Toast.makeText(mContext, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callPharmacyOrderDetailApi(pharmacyModel);
                new getData(labDatum).execute();
            }
        });
        return new ContentViewWrapper(convertView, reUseable);
    }

    private class getData extends AsyncTask<String, String, String> {

        HttpURLConnection urlConnection;
        LabDatum mlabDatum;

        public getData(LabDatum labDatum) {
            mlabDatum = labDatum;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... args) {

            StringBuilder result = new StringBuilder();

            try {
                //GetLabOrderDetails_v2
                //GetLabOrderDetails_v1
                String serviceUrl = BuildConfig.API_URL + "/GetLabOrderDetails_v2?UserRoleID=" + mlabDatum.getUserRoleId() + "&LabOrderId=" + mlabDatum.getLabOrderId();
                Log.d("serviceUrl", serviceUrl);
                URL url = new URL(serviceUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("authkeytoken", Helper.getAESCryptEncodeString());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception e) {
                dismissLoadingDialog();
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("result", result);
            //Do something with the JSON string
            dismissLoadingDialog();
            ArrayList<LabOrderDetailModel> labOrderDetailModels = new ArrayList<>();
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    JSONArray jsonTestArray = jsonData.getJSONArray("test");
                    for (int i = 0; i < jsonTestArray.length(); i++) {
                        JSONObject jsonTest = jsonTestArray.getJSONObject(i);
                        LabOrderDetailModel labOrderDetailModel = new LabOrderDetailModel();
                        labOrderDetailModel.setLabTestNature(EnumType.LabObjNature.kLabObjNatureTest);
                        labOrderDetailModel.setLabTestName(jsonTest.getString("testName"));
                        labOrderDetailModel.setLabTestId(jsonTest.getString("testId"));
                        labOrderDetailModel.setLabTestCost(jsonTest.getString("cost"));
                        labOrderDetailModel.setLabTestInstId(jsonTest.getString("testInsId"));
                        labOrderDetailModel.setLabTestInstruction(jsonTest.getString("testInstruction"));
                        labOrderDetailModel.setSelected(false);
                        labOrderDetailModels.add(labOrderDetailModel);
                    }
                    JSONArray jsonGroupArray = jsonData.getJSONArray("group");
                    for (int i = 0; i < jsonGroupArray.length(); i++) {
                        JSONObject jsonGroup = jsonGroupArray.getJSONObject(i);
                        LabOrderDetailModel labOrderDetailModel = new LabOrderDetailModel();
                        labOrderDetailModel.setLabTestNature(EnumType.LabObjNature.kLabObjNatureGroup);
                        labOrderDetailModel.setLabTestName(jsonGroup.getString("grpName"));
                        labOrderDetailModel.setLabTestId(jsonGroup.getString("grpId"));
                        labOrderDetailModel.setLabTestCost(jsonGroup.getString("cost"));
                        labOrderDetailModel.setSelected(false);
                        //labOrderDetailModel.setLabTestInstId(jsonGroup.getString("testInsId"));
                        //labOrderDetailModel.setLabTestInstruction(jsonGroup.getString("testInstruction"));
                        JSONArray jsonGroupTestArray = jsonGroup.getJSONArray("testList");
                        ArrayList<LabOrderDetailModel> groupTestList = new ArrayList<>();
                        for (int j = 0; j < jsonGroupTestArray.length(); j++) {
                            JSONObject jsonTest = jsonGroupTestArray.getJSONObject(j);
                            LabOrderDetailModel labOrderDetailModel1 = new LabOrderDetailModel();
                            labOrderDetailModel1.setLabTestNature(EnumType.LabObjNature.kLabObjNatureTest);
                            labOrderDetailModel1.setLabTestName(jsonTest.getString("testName"));
                            labOrderDetailModel1.setLabTestId(jsonTest.getString("testId"));
                            labOrderDetailModel1.setLabTestCost(jsonTest.getString("cost"));
                            labOrderDetailModel1.setLabTestInstId(jsonTest.getString("testInsId"));
                            labOrderDetailModel1.setLabTestInstruction(jsonTest.getString("testInstruction"));
                            labOrderDetailModel1.setSelected(false);
                            groupTestList.add(labOrderDetailModel1);
                        }
                        labOrderDetailModel.setChildren(groupTestList);
                        labOrderDetailModels.add(labOrderDetailModel);
                    }
                    JSONArray jsonPackageArray = jsonData.getJSONArray("package");
                    for (int i = 0; i < jsonPackageArray.length(); i++) {
                        JSONObject jsonPackage = jsonPackageArray.getJSONObject(i);
                        LabOrderDetailModel labOrderDetailModel = new LabOrderDetailModel();
                        labOrderDetailModel.setLabTestNature(EnumType.LabObjNature.kLabObjNaturePackage);
                        labOrderDetailModel.setLabTestName(jsonPackage.getString("pkgName"));
                        labOrderDetailModel.setLabTestId(jsonPackage.getString("pkgId"));
                        labOrderDetailModel.setLabTestCost(jsonPackage.getString("cost"));
                        labOrderDetailModel.setSelected(false);
                        //labOrderDetailModel.setLabTestInstId(jsonPackage.getString("testInsId"));
                        //labOrderDetailModel.setLabTestInstruction(jsonPackage.getString("testInstruction"));
                        JSONArray jsonPackageGroupArray = jsonPackage.getJSONArray("groupList");
                        ArrayList<LabOrderDetailModel> packageGroupList = new ArrayList<>();
                        for (int j = 0; j < jsonPackageGroupArray.length(); j++) {
                            JSONObject jsonGroup = jsonPackageGroupArray.getJSONObject(j);
                            LabOrderDetailModel labOrderDetailModel1 = new LabOrderDetailModel();
                            labOrderDetailModel1.setLabTestNature(EnumType.LabObjNature.kLabObjNatureGroup);
                            labOrderDetailModel1.setLabTestName(jsonGroup.getString("grpName"));
                            labOrderDetailModel1.setLabTestId(jsonGroup.getString("grpId"));
                            labOrderDetailModel1.setLabTestCost(jsonGroup.getString("cost"));
                            labOrderDetailModel1.setSelected(false);
                            //labOrderDetailModel1.setLabTestInstId(jsonGroup.getString("testInsId"));
                            //labOrderDetailModel1.setLabTestInstruction(jsonGroup.getString("testInstruction"));
                            JSONArray jsonGroupTestArray = jsonGroup.getJSONArray("testList");
                            ArrayList<LabOrderDetailModel> packageGroupTestList = new ArrayList<>();
                            for (int k = 0; k < jsonGroupTestArray.length(); k++) {
                                JSONObject jsonTest = jsonGroupTestArray.getJSONObject(k);
                                LabOrderDetailModel labOrderDetailModel2 = new LabOrderDetailModel();
                                labOrderDetailModel2.setLabTestNature(EnumType.LabObjNature.kLabObjNatureTest);
                                labOrderDetailModel2.setLabTestName(jsonTest.getString("testName"));
                                labOrderDetailModel2.setLabTestId(jsonTest.getString("testId"));
                                labOrderDetailModel2.setLabTestCost(jsonTest.getString("cost"));
                                labOrderDetailModel2.setLabTestInstId(jsonTest.getString("testInsId"));
                                labOrderDetailModel2.setLabTestInstruction(jsonTest.getString("testInstruction"));
                                labOrderDetailModel2.setSelected(false);
                                packageGroupTestList.add(labOrderDetailModel2);
                            }
                            packageGroupList.add(labOrderDetailModel1);
                            labOrderDetailModel1.setChildren(packageGroupTestList);
                        }
                        JSONArray jsonGroupTestArray = jsonPackage.getJSONArray("testList");
                        for (int l = 0; l < jsonGroupTestArray.length(); l++) {
                            JSONObject jsonTest = jsonGroupTestArray.getJSONObject(l);
                            LabOrderDetailModel labOrderDetailModel2 = new LabOrderDetailModel();
                            labOrderDetailModel2.setLabTestNature(EnumType.LabObjNature.kLabObjNatureTest);
                            labOrderDetailModel2.setLabTestName(jsonTest.getString("testName"));
                            labOrderDetailModel2.setLabTestId(jsonTest.getString("testId"));
                            labOrderDetailModel2.setLabTestCost(jsonTest.getString("cost"));
                            labOrderDetailModel2.setLabTestInstId(jsonTest.getString("testInsId"));
                            labOrderDetailModel2.setLabTestInstruction(jsonTest.getString("testInstruction"));
                            labOrderDetailModel2.setSelected(false);
                            packageGroupList.add(labOrderDetailModel2);
                        }
                        labOrderDetailModel.setChildren(packageGroupList);
                        //labOrderDetailModel.setChildren(packageTestList);
                        labOrderDetailModels.add(labOrderDetailModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("labOrderDetailModels", labOrderDetailModels.size() + "");
            }
            showLabOrderDetailDialog(mlabDatum, labOrderDetailModels);
        }
    }

    private void showLabOrderDetailDialog(LabDatum labDatum, ArrayList<LabOrderDetailModel> labOrderDetailModels) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View convertView = (View) inflater.inflate(R.layout.lab_main_order_dialog, null);
        builder.setView(convertView);
        ImageButton ib_close = (ImageButton) convertView.findViewById(R.id.ib_close);
        TextView tv_total_amount = (TextView) convertView.findViewById(R.id.tv_total_amount);
        final ExpandableListView lab_order_listview = (ExpandableListView) convertView.findViewById(R.id.lab_order_listview);

        Spinner lab_main_order_dialog_counter = (Spinner) convertView.findViewById(R.id.lab_main_order_dialog_counter);
        CounterListAdapter counterListAdapter = new CounterListAdapter(mContext);
        lab_main_order_dialog_counter.setAdapter(counterListAdapter);

        ImageView tv_check_uncheck = (ImageView) convertView.findViewById(R.id.tv_check_uncheck);

        TextView tv_consultant_name = (TextView) convertView.findViewById(R.id.tv_consultant_name);
        TextView tv_ord_id = (TextView) convertView.findViewById(R.id.tv_ord_id);
        TextView tv_pat_name = (TextView) convertView.findViewById(R.id.tv_pat_name);
        TextView tv_pat_dob_age = (TextView) convertView.findViewById(R.id.tv_pat_dob_age);
        TextView tv_pat_contact = (TextView) convertView.findViewById(R.id.tv_pat_contact);
        TextView btn_paynow = (TextView) convertView.findViewById(R.id.btn_paynow);
        TextView btn_ordernow = (TextView) convertView.findViewById(R.id.btn_ordernow);

        tv_consultant_name.setText(labDatum.getDoctorName().toString());
        tv_pat_name.setText(labDatum.getPatname().toString());
        tv_pat_contact.setText(labDatum.getMobile().toString());
        tv_ord_id.setText("Ord Id: " + labDatum.getLabOrderId().toString());
        String patDob = labDatum.getDob().toString();
        int patGender = labDatum.getGenderId();
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
        mainLabDialog = builder.show();
        //PharmacyOrderDialogAdapter pharmacyOrderDialogAdapter = new PharmacyOrderDialogAdapter(mContext, pharmacyModel, followUpModelArrayList, pharmacyOrderDetailModels, tv_total_amount, btn_paynow, tv_check_uncheck, btn_ordernow,mainPresDialog);
        //pharmacy_order_listview.setAdapter(pharmacyOrderDialogAdapter);
        final int[] lastExpandedPosition = {-1};
        final LabOrderDetailExpandableAdapter labOrderDetailExpandableAdapter = new LabOrderDetailExpandableAdapter(mContext, labDatum, labOrderDetailModels, btn_paynow, tv_total_amount, mainLabDialog,btn_ordernow) {
            @Override
            public void OnIndicatorClick(boolean isExpanded, int position) {
                if (lastExpandedPosition[0] != -1 && position != lastExpandedPosition[0]) {
                    lab_order_listview.collapseGroup(lastExpandedPosition[0]);
                }
                lastExpandedPosition[0] = position;
                if (isExpanded) {
                    lab_order_listview.collapseGroup(position);
                } else {
                    lab_order_listview.expandGroup(position);
                }
            }

            @Override
            public void OnTextClick() {
                //Do whatever you want to do on text click
            }
        };
        lab_order_listview.setAdapter(labOrderDetailExpandableAdapter);
        /*lab_order_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(mContext,labOrderDetailExpandableAdapter.getGroup(groupPosition).getLabTestName(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
        /*lab_order_listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                LabOrderDetailModel list = labOrderDetailExpandableAdapter.getChild(groupPosition,childPosition);
                Toast.makeText(mContext,"hello",Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext,list.getLabTestName()+"",Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/
        mainLabDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mainLabDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        Window dialogWindow = mainLabDialog.getWindow();
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
                mainLabDialog.dismiss();
            }
        });
        lab_main_order_dialog_counter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        LabChildDatum labChildDatum = (LabChildDatum) getChild(groupPosition, childPosition);
        holder.tv_txn_id.setText("Txn id: " + labChildDatum.getOrderTransactionId().toString());
        holder.tv_ord_amount.setText(labChildDatum.getOrderedAmount().toString());
        if (labChildDatum.getOrderStatus() == 3) {
            holder.tv_ord_amount.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
            holder.iv_payment_status.setImageResource(R.mipmap.sample_collection_o);
            holder.iv_payment_status.setVisibility(View.VISIBLE);
            convertView.setBackgroundResource(R.drawable.childview_bg);
        } else if (labChildDatum.getOrderStatus() == 10) {
            holder.tv_ord_amount.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            holder.iv_payment_status.setImageResource(R.mipmap.sample_collection_o);
            holder.iv_payment_status.setVisibility(View.VISIBLE);
            convertView.setBackgroundResource(R.drawable.childview_bg);
        } else if (labChildDatum.getOrderStatus() == 11) {
            holder.tv_ord_amount.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            holder.iv_payment_status.setImageResource(R.mipmap.sample_collection_g);
            holder.iv_payment_status.setVisibility(View.VISIBLE);
            convertView.setBackgroundResource(R.drawable.childview_bg);
        }else if (labChildDatum.getOrderStatus() == 12) {
            holder.tv_ord_amount.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.iv_payment_status.setVisibility(View.GONE);
            convertView.setBackgroundResource(R.drawable.child_cancel_bg);
        }
        String jsonValue = labChildDatum.getOrderedDate();
        String timestamp = jsonValue.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date();
        try {
            createdOn  = new Date(Long.parseLong(timestamp));
        }catch (NumberFormatException  numberFormatException){
        }         SimpleDateFormat orderedFormat = new SimpleDateFormat("dd/MM/yyyy | h:mm");
        String orderedDate = orderedFormat.format(createdOn);
        Log.d("formattedDate", orderedDate);
        System.out.print("formattedDate-->" + orderedDate);
        holder.tv_ordered_date.setText(orderedDate.toString());
        return new ContentViewWrapper(convertView, reUseable);
    }

    @Override
    public int getGroupCount() {
        return testPharmacy.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return testPharmacy.get(groupPosition).getLabChildData().size();
    }

    @Override
    public LabDatum getGroup(int groupPosition) {
        return testPharmacy.get(groupPosition);
    }

    @Override
    public LabChildDatum getChild(int groupPosition, int childPosition) {
        return testPharmacy.get(groupPosition).getLabChildData().get(childPosition);
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
        TextView tv_order_id, tv_doc_name, tv_patient_name, tv_status, tv_datetime, tv_payment_status, tv_patient_contact, tv_token_no;
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

    public void filterData(String query) {
        if (LabOrderActivity.searchBy.equals("Patient")) {
            query = query.toLowerCase();
            testPharmacy.clear();
            if (query.isEmpty()) {
                testPharmacy.addAll(originalList);
            } else {
                for (LabDatum continent : originalList) {
                    if (continent.getPatname().toLowerCase().contains(query)) {
                        testPharmacy.add(continent);
                    }
                }
                Log.d("testPharmacy_size", testPharmacy.size() + "");
            }
            notifyDataSetChanged();
        }
        if (LabOrderActivity.searchBy.equals("Doctor")) {
            query = query.toLowerCase();
            testPharmacy.clear();
            if (query.isEmpty()) {
                testPharmacy.addAll(originalList);
            } else {
                for (LabDatum continent : originalList) {
                    if (continent.getDoctorName().toLowerCase().contains(query)) {
                        testPharmacy.add(continent);
                    }
                }
                Log.d("testPharmacy_size", testPharmacy.size() + "");
            }
            notifyDataSetChanged();
        }
        if (LabOrderActivity.searchBy.equals("Select")) {
            query = query.toLowerCase();
            testPharmacy.clear();
            if (query.isEmpty()) {
                testPharmacy.addAll(originalList);
            }
            notifyDataSetChanged();
        }
        if (LabOrderActivity.searchBy.equals("Lab User")) {
            query = query.toLowerCase();
            testPharmacy.clear();
            if (query.isEmpty() || (LabOrderActivity.counterNo.equals("0"))) {
                testPharmacy.addAll(originalList);
            } else {
                for (int i = 0; i < originalList.size(); i++) {
                    for (int j = 0; j < originalList.get(i).getLabChildTokenData().size(); j++) {
                        if (originalList.get(i).getLabChildTokenData().get(j).getCounterId().toString().equals(query)) {
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
