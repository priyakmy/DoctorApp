package com.mcura.mcurapharmacy.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mcura.mcurapharmacy.BuildConfig;
import com.mcura.mcurapharmacy.Helper.Helper;
import com.mcura.mcurapharmacy.Interface.LabInterface;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.Constants;
import com.mcura.mcurapharmacy.Utils.EnumType;
import com.mcura.mcurapharmacy.adpater.CounterSpinnerAdapter;
import com.mcura.mcurapharmacy.adpater.LabOrderExpandableAdapter;
import com.mcura.mcurapharmacy.adpater.LabSavedOrderDialogAdapter;
//import com.mcura.mcurapharmacy.adpater.PharmacyAdapter;
import com.mcura.mcurapharmacy.com.allen.expandablelistview.SwipeMenuExpandableCreator;
import com.mcura.mcurapharmacy.com.allen.expandablelistview.SwipeMenuExpandableListView;
import com.mcura.mcurapharmacy.com.baoyz.swipemenulistview.SwipeMenu;
import com.mcura.mcurapharmacy.com.baoyz.swipemenulistview.SwipeMenuItem;
import com.mcura.mcurapharmacy.model.CounterModel;
import com.mcura.mcurapharmacy.model.FollowUpModel;
import com.mcura.mcurapharmacy.model.LabChildDatum;
import com.mcura.mcurapharmacy.model.LabDatum;
import com.mcura.mcurapharmacy.model.LabOrderDetailModel;
import com.mcura.mcurapharmacy.model.LabOrderModel;
import com.mcura.mcurapharmacy.model.PharmacyModel;
import com.mcura.mcurapharmacy.model.PostPaymentModel;
import com.mcura.mcurapharmacy.view.SegmentedGroup;

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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LabOrderActivity extends AppCompatActivity implements LabInterface, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    String counterName[] = {"SELECT COUNTER", "CT SCANNING", "ULTRASOUND", "PATHOLOGY", "X-RAY", "RADIOLOGY", "MRI"};
    int counterId[] = {0, 6811, 6812, 6815, 6819, 6820, 6823};


    public static int totalAmount;
    private String patAgeGender, patName, patContact, consultantName, patDob;
    private String hospitalId;
    private Integer selectedUserRoleId;
    private Integer mrno;
    EditText search_by_patient;
    ImageView imageData;
    AlertDialog.Builder alertBuilder;
    AlertDialog ad;
    public MCuraApplication mCuraApplication;
    private ProgressDialog progress;
    private SwipeMenuExpandableListView laborder_listview;
    //private PharmacyAdapter pharmacyAdapter;
    private Spinner pharmacy_status;
    String[] pharmacyStatus, pharmacyStatusArray;
    private SimpleDateFormat dateFormatter;
    private TextView tvFromTime, tvToTime;
    Calendar now, fromDate, toDate;
    private DatePickerDialog fromDatePickerDialog, toDatePickerDialog;
    private SharedPreferences mSharedPreference;
    public ImageView iv_search_pat;
    public static String searchBy = "";
    private ImageButton iv_logout;
    ArrayAdapter spinnerArrayAdapter;
    int roleID, userRoleId, currentStatusId, subTenantId;
    String subTenantName;
    private TextView tv_subtenant_name,queue;
    private String pdf;
    Filter filter;
    WebView lastVisitSummary_webview;
    private AlertDialog alertDialog;
    private Integer prescriptionId;
    private int prescriptionStatus;
    private AlertDialog dialog;
    private ArrayAdapter statusListArrayAdapter;
    private PharmacyModel model;
    private int labPharmacyType;
    private String estimateBillId;
    private String totalDays;
    private String amount;
    private String paymentMode = "1";
    private WebView myWebView;
    private String completeDate;
    private String time;
    private String subTanantName;
    private String subTanantAddress;
    private String subTanantContact;
    private String otp;
    private String payableAmount;
    private SwipeRefreshLayout swipeContainer;
    private String paymentDescription;
    private int appNatureId;
    private CheckBox cbRefund;
    private ArrayList<FollowUpModel> followUpModelArrayList;
    private int paidStatus;
    private TextView tv_total_amount;
    private Integer prescriptionOrderId;
    private Button btn_deliver;
    private int statusId;
    private SegmentedGroup segmentedGroup;
    private SwipeMenuItem item1;
    private Snackbar snackbar;
    private RelativeLayout parent_layout;
    public static int orderKey;
    private int pos;
    private LabOrderExpandableAdapter labOrderExpandableAdapter;
    private LinearLayout bottom_layout;
    private CounterSpinnerAdapter counterSpinnerAdapter;
    private ArrayList<CounterModel> counterModelArrayList;
    private Spinner spinner_lab_filter;
    public static String counterNo = "0";
    private Spinner spinner_search_by;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_order);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        pharmacyStatus = getResources().getStringArray(R.array.pharmacy_status);
        mSharedPreference = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        labPharmacyType = mSharedPreference.getInt(Constants.LAB_PHARMACY_TYPE, 0);
        roleID = mSharedPreference.getInt(Constants.ROLE_ID_KEY, 0);
        userRoleId = mSharedPreference.getInt(Constants.USER_ROLE_ID_KEY, 0);
        subTenantId = mSharedPreference.getInt(Constants.SUB_TANENT_ID_KEY, 0);
        subTenantName = mSharedPreference.getString(Constants.SUB_TANENT_ID_NAME, "SubTenant Name");
        //currentStatusId=mSharedPreference.getInt(Constants.STATUS_ID_KEY,0);
        Log.e("RoleId", String.valueOf(roleID));
        Log.e("UserRoleId", String.valueOf(userRoleId));
        Log.e("CurrentStatusId", String.valueOf(currentStatusId));
        Log.e("labPharmacyType", String.valueOf(labPharmacyType));
        now = Calendar.getInstance();
        orderKey = 0;
        queue = findViewById(R.id.queue);
        queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LabOrderActivity.this, QueueActivity.class);
                i.putExtra("ClickTo","LabOrderActivity");
                startActivity(i);
                finish();
            }
        });
        initView();
    }


    private void initView() {
        setCounterData();
        //getTransactionListFromApi();
        String searchByList[] = {"Select", "Patient", "Doctor"};
        spinner_search_by = (Spinner) findViewById(R.id.spinner_search_by);

        spinner_lab_filter = (Spinner) findViewById(R.id.spinner_lab_filter);


        counterSpinnerAdapter = new CounterSpinnerAdapter(LabOrderActivity.this,
                android.R.layout.simple_spinner_item,
                counterModelArrayList);
        spinner_lab_filter.setAdapter(counterSpinnerAdapter);


        spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                searchByList);
        spinner_search_by.setAdapter(spinnerArrayAdapter);
        spinner_lab_filter.setSelection(0);
        bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
        segmentedGroup = (SegmentedGroup) findViewById(R.id.appointment_segment);
        segmentedGroup.check(R.id.rd_pendingorder);
        segmentedGroup.setOnCheckedChangeListener(this);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US); //dd-MM-yyyy
        now = Calendar.getInstance();
        parent_layout = (RelativeLayout) findViewById(R.id.parent_layout);
        tv_subtenant_name = (TextView) findViewById(R.id.tv_subtenant_name);
        tv_subtenant_name.setText(subTenantName);
        iv_logout = (ImageButton) findViewById(R.id.iv_logout);
        iv_logout.setOnClickListener(this);
        search_by_patient = (EditText) findViewById(R.id.et_search_by_patient);
        spinner_lab_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchBy = "Lab User";
                CounterModel counterModel = counterSpinnerAdapter.getItem(position);
                counterNo = counterModel.getCounterId() + "";
                labOrderExpandableAdapter.filterData(counterModel.getCounterId() + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_search_by.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchBy = (String) parent.getItemAtPosition(position);
                //Toast.makeText(LabOrderActivity.this, searchBy, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        search_by_patient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("editable", editable.toString());
                labOrderExpandableAdapter.filterData(editable.toString());
            }
        });
        Log.d("searchBy", searchBy);
        spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                pharmacyStatus);
        laborder_listview = (SwipeMenuExpandableListView) findViewById(R.id.laborder_listview);

        tvFromTime = (TextView) findViewById(R.id.tv_fromTime);
        tvFromTime.setText(dateFormatter.format(now.getTime()));
        tvToTime = (TextView) findViewById(R.id.tv_toTime);
        tvToTime.setText(dateFormatter.format(now.getTime()));
        tvFromTime.setOnClickListener(this);
        tvToTime.setOnClickListener(this);


        // 7. Create and set a SwipeMenuExpandableCreator
        // define the group and child creator function
        SwipeMenuExpandableCreator creator = new SwipeMenuExpandableCreator() {
            @Override
            public void createGroup(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    /*case 0:
                        createMenu1(menu);
                        break;
                    case 1:
                        createMenu2(menu);
                        break;
                    case 2:
                        createMenu3(menu);
                        break;*/
                }
            }

            @Override
            public void createChild(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                    case 1:
                        createMenu1(menu);
                        break;
                    case 2:
                        createMenu1(menu);
                        break;
                }
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xEE, 0xEE, 0xEE)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.mipmap.pay);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xD9, 0xD9, 0xDE)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.mipmap.report_collect);
                menu.addMenuItem(item2);
                SwipeMenuItem item3 = new SwipeMenuItem(getApplicationContext());
                item3.setBackground(new ColorDrawable(Color.rgb(0xCD, 0xCD, 0xCD)));
                item3.setWidth(dp2px(90));
                item3.setIcon(R.mipmap.ic_cancel);
                menu.addMenuItem(item3);
            }
        };
        laborder_listview.setMenuCreator(creator);
        laborder_listview.setOnMenuItemClickListener(new SwipeMenuExpandableListView.OnMenuItemClickListenerForExpandable() {
            @Override
            public boolean onMenuItemClick(int groupPosition, int childPosition, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // payment
                        if (labOrderExpandableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 3) {
                            showPaymentDialogForChild(labOrderExpandableAdapter.getGroup(groupPosition), labOrderExpandableAdapter.getChild(groupPosition, childPosition));
                        } else if(labOrderExpandableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 12){
                            Toast.makeText(LabOrderActivity.this, "Order is cancelled.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LabOrderActivity.this, "Payment Already Done.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        // Report Collected
                        //Toast.makeText(LabOrderActivity.this, "Report Collected", Toast.LENGTH_SHORT).show();
                        if (labOrderExpandableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 10) {
                            JsonObject obj = new JsonObject();
                            obj.addProperty("UserRoleID", labOrderExpandableAdapter.getGroup(groupPosition).getUserRoleId());
                            obj.addProperty("LabOrderID", labOrderExpandableAdapter.getGroup(groupPosition).getLabOrderId());
                            obj.addProperty("orderTxnId", labOrderExpandableAdapter.getChild(groupPosition, childPosition).getOrderTransactionId());
                            obj.addProperty("Statusid", 11);
                            Log.d("jsonObj", obj.toString());
                            showConfirmationDialog(getString(R.string.sampleConfirmationMsg), obj);
                        } else if (labOrderExpandableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 11) {
                            Toast.makeText(LabOrderActivity.this, "Already Delivered.", Toast.LENGTH_SHORT).show();
                        } else if (labOrderExpandableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 3) {
                            Toast.makeText(LabOrderActivity.this, "Payment is not done.", Toast.LENGTH_SHORT).show();
                        }else if (labOrderExpandableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 12) {
                            Toast.makeText(LabOrderActivity.this, "Order is cancelled.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        //cancel
                        if (labOrderExpandableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 3) {
                            JsonObject jsonObj = new JsonObject();
                            jsonObj.addProperty("UserRoleID", labOrderExpandableAdapter.getGroup(groupPosition).getUserRoleId());
                            jsonObj.addProperty("LabOrderID", labOrderExpandableAdapter.getGroup(groupPosition).getLabOrderId());
                            jsonObj.addProperty("orderTxnId", labOrderExpandableAdapter.getChild(groupPosition, childPosition).getOrderTransactionId());
                            jsonObj.addProperty("Statusid", 12);
                            Log.d("jsonObj", jsonObj.toString());
                            showConfirmationDialog(getString(R.string.orderCancelMsg), jsonObj);
                        }else if (labOrderExpandableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 12) {
                            Toast.makeText(LabOrderActivity.this, "Order is already cancelled.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LabOrderActivity.this, "Order Cannot be cancelled", Toast.LENGTH_SHORT).show();
                        }
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
// 5.Set OnGroupClickListener
        laborder_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                /*Toast.makeText(LabOrderActivity.this, "group " + groupPosition + " clicked", Toast.LENGTH_SHORT)
                        .show();*/
                return false;
            }
        });


        // 6.Set OnChildClickListener
        laborder_listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                /*Toast.makeText(LabOrderActivity.this,
                        "group " + groupPosition + " child " + childPosition + " clicked", Toast.LENGTH_SHORT).show();*/
                LabDatum labDatum = labOrderExpandableAdapter.getGroup(groupPosition);
                LabChildDatum labChildDatum = labOrderExpandableAdapter.getChild(groupPosition, childPosition);
                new getData(labDatum, labChildDatum).execute();
                return false;
            }
        });


        setFromDateTimeField();
        setToDateTimeField();
        getLabOrderDataFromApi_v2();
    }

    private void showConfirmationDialog(String msg, final JsonObject jsonObj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LabOrderActivity.this);
        builder.setTitle("Confirmation Message");
        builder.setMessage(msg);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setLabStatus(jsonObj);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.show();
    }

    private void setLabStatus(JsonObject jsonObj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.labPrescOrdersUpdateByTxnId(jsonObj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                dismissLoadingDialog();
                reloadLabDetail();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void setCounterData() {
        counterModelArrayList = new ArrayList<>();
        for (int i = 0; i < counterName.length; i++) {
            CounterModel counterModel = new CounterModel();
            counterModel.setCounterName(counterName[i]);
            counterModel.setCounterId(counterId[i]);
            counterModelArrayList.add(counterModel);
        }
    }

    /*private void callSavedLabOrderDetailApi(final LabDatum labDatum, final LabChildDatum labChildDatum) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getSavedLabOrderDetails(labDatum.getLabOrderId(), labDatum.getUserRoleId(), labChildDatum.getOrderTransactionId(), new Callback<LabOrderModel>() {
            @Override
            public void success(LabOrderModel labOrderModel, Response response) {
                if (paidStatus == 0) {
                    //showUnpaidPharmacyOrderDialog(pharmacyModel, pharmacyOrderDetailModels, pharmacyChildDatum);
                } else if (paidStatus == 1) {
                    //showPaidPharmacyOrderDialog(pharmacyOrderDetailModels);
                }
                showUnpaidLabOrderDialog(labDatum, labOrderModel, labChildDatum);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }*/


    private class getData extends AsyncTask<String, String, String> {

        HttpURLConnection urlConnection;
        LabDatum mlabDatum;
        LabChildDatum mLabChildDatum;

        public getData(LabDatum labDatum, LabChildDatum labChildDatum) {
            mlabDatum = labDatum;
            mLabChildDatum = labChildDatum;
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
                String serviceUrl = BuildConfig.API_URL + "/GetSavedLabOrderDetails_v1?UserRoleID=" + mlabDatum.getUserRoleId() + "&LabOrderId=" + mlabDatum.getLabOrderId() + "&orderTransactionId=" + mLabChildDatum.getOrderTransactionId();
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
            showSavedLabOrderDialog(mlabDatum, labOrderDetailModels, mLabChildDatum);
        }
    }


    private void showSavedLabOrderDialog(final LabDatum labDatum, ArrayList<LabOrderDetailModel> labOrderDetailModels, final LabChildDatum labChildDatum) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LabOrderActivity.this);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(LabOrderActivity.this);
        View convertView = (View) inflater.inflate(R.layout.lab_unpaid_order_dialog, null);
        builder.setView(convertView);
        ImageButton ib_close = (ImageButton) convertView.findViewById(R.id.ib_close);
        tv_total_amount = (TextView) convertView.findViewById(R.id.tv_total_amount);
        final ExpandableListView lab_order_explistview = (ExpandableListView) convertView.findViewById(R.id.lab_order_explistview);
        ImageView tv_check_uncheck = (ImageView) convertView.findViewById(R.id.tv_check_uncheck);

        TextView tv_txn_id = (TextView) convertView.findViewById(R.id.tv_txn_id);
        TextView tv_pat_name = (TextView) convertView.findViewById(R.id.tv_pat_name);
        TextView tv_pat_dob_age = (TextView) convertView.findViewById(R.id.tv_pat_dob_age);
        TextView tv_pat_contact = (TextView) convertView.findViewById(R.id.tv_pat_contact);
        TextView tv_ordered_date = (TextView) convertView.findViewById(R.id.tv_ordered_date);
        TextView btn_paynow = (TextView) convertView.findViewById(R.id.btn_paynow);
        Button iv_report_collected = (Button) convertView.findViewById(R.id.iv_report_collected);
        TextView tv_delivered_time = (TextView) convertView.findViewById(R.id.tv_delivered_time);
        TextView tv_order_cancel = convertView.findViewById(R.id.tv_order_cancel);
        if (labChildDatum.getOrderStatus() == 3) {
            btn_paynow.setVisibility(View.VISIBLE);
            iv_report_collected.setVisibility(View.GONE);
            tv_total_amount.setTextColor(ContextCompat.getColor(LabOrderActivity.this, R.color.red));
            tv_delivered_time.setVisibility(View.GONE);
            tv_order_cancel.setVisibility(View.GONE);
        } else if (labChildDatum.getOrderStatus() == 10) {
            btn_paynow.setVisibility(View.GONE);
            iv_report_collected.setVisibility(View.VISIBLE);
            tv_total_amount.setTextColor(ContextCompat.getColor(LabOrderActivity.this, R.color.green));
            tv_delivered_time.setVisibility(View.GONE);
            tv_order_cancel.setVisibility(View.GONE);
        } else if (labChildDatum.getOrderStatus() == 11) {
            btn_paynow.setVisibility(View.GONE);
            tv_total_amount.setTextColor(ContextCompat.getColor(LabOrderActivity.this, R.color.green));
            iv_report_collected.setVisibility(View.GONE);
            tv_delivered_time.setVisibility(View.VISIBLE);
            tv_order_cancel.setVisibility(View.GONE);
            //tv_delivered_time.setText(Helper.changeDateFormat(labChildDatum.getDeliveredDate()));
        } else if (labChildDatum.getOrderStatus() == 12) {
            btn_paynow.setVisibility(View.GONE);
            tv_total_amount.setTextColor(ContextCompat.getColor(LabOrderActivity.this, R.color.red));
            iv_report_collected.setVisibility(View.GONE);
            tv_delivered_time.setVisibility(View.GONE);
            tv_order_cancel.setVisibility(View.VISIBLE);
        }
        tv_txn_id.setText("Txn Id: " + labChildDatum.getOrderTransactionId().toString());
        tv_pat_name.setText(labDatum.getPatname().toString());
        tv_pat_contact.setText(labDatum.getMobile().toString());

        String jsonValue = labChildDatum.getOrderedDate();
        String timestamp1 = jsonValue.split("\\(")[1].split("\\+")[0];
        Date createdOn1 = new Date();
        try {
            createdOn1  = new Date(Long.parseLong(timestamp1));
        }catch (NumberFormatException  numberFormatException){
        }         SimpleDateFormat orderedFormat = new SimpleDateFormat("dd/MM/yyyy | h:mm");
        String orderedDate = orderedFormat.format(createdOn1);
        Log.d("formattedDate", orderedDate);
        System.out.print("formattedDate-->" + orderedDate);
        tv_ordered_date.setText(orderedDate.toString());

        tv_delivered_time.setText(Helper.changeDateTimeFormat(labChildDatum.getDeliveredDate()).toString());


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

        LabSavedOrderDialogAdapter labsavedDialogAdapter = new LabSavedOrderDialogAdapter(LabOrderActivity.this, labDatum, labOrderDetailModels, labChildDatum, tv_total_amount, btn_paynow) {
            @Override
            public void OnIndicatorClick(boolean isExpanded, int position) {
                if (isExpanded) {
                    lab_order_explistview.collapseGroup(position);
                } else {
                    lab_order_explistview.expandGroup(position);
                }
            }

            @Override
            public void OnTextClick() {
                //Do whatever you want to do on text click
            }
        };
        lab_order_explistview.setAdapter(labsavedDialogAdapter);
        final AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.alpha = 1.0f; // Transparency
        dialogWindow.setAttributes(lp);
        double totalSavedAmount = 0.0;
        for (int i = 0; i < labOrderDetailModels.size(); i++) {
            totalSavedAmount += Double.parseDouble(labOrderDetailModels.get(i).getLabTestCost());
        }
        tv_total_amount.setText(totalSavedAmount + "");

        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showPaymentDialogForChild(labDatum, labChildDatum);
            }
        });
        iv_report_collected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                JsonObject obj = new JsonObject();
                obj.addProperty("UserRoleID", labDatum.getUserRoleId());
                obj.addProperty("LabOrderID", labDatum.getLabOrderId());
                obj.addProperty("orderTxnId", labChildDatum.getOrderTransactionId());
                obj.addProperty("Statusid", 11);
                Log.d("jsonObj", obj.toString());
                showConfirmationDialog(getString(R.string.sampleConfirmationMsg), obj);
            }
        });
    }

    private void showPaymentDialogForChild(final LabDatum group, final LabChildDatum child) {
        paymentMode = "1";
        final Dialog dialog = new Dialog(LabOrderActivity.this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f);
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pharmacy_payment_dialog);
        final EditText bill_payment_activity_hIs_no = (EditText) dialog.findViewById(R.id.bill_payment_activity_hIs_no);
        final TextView bill_payable_amount = (TextView) dialog.findViewById(R.id.bill_payable_amount);
        bill_payable_amount.setText(child.getOrderedAmount() + "");
        RadioGroup payment_mode = (RadioGroup) dialog.findViewById(R.id.payment_mode);
        TextView activity_bill_payment_paynow = (TextView) dialog.findViewById(R.id.activity_bill_payment_paynow);
        TextView tv_close_dialog = (TextView) dialog.findViewById(R.id.tv_close_dialog);
        TextView tv_consultant_name = (TextView) dialog.findViewById(R.id.tv_consultant_name);
        tv_consultant_name.setVisibility(View.GONE);
        TextView tv_ord_id = (TextView) dialog.findViewById(R.id.tv_ord_id);
        tv_ord_id.setVisibility(View.GONE);
        TextView tv_txn_id = (TextView) dialog.findViewById(R.id.tv_txn_id);
        tv_txn_id.setVisibility(View.VISIBLE);
        TextView tv_ordered_date = (TextView) dialog.findViewById(R.id.tv_ordered_date);
        tv_ordered_date.setVisibility(View.VISIBLE);
        TextView tv_pat_name = (TextView) dialog.findViewById(R.id.tv_pat_name);
        TextView tv_pat_dob_age = (TextView) dialog.findViewById(R.id.tv_pat_dob_age);
        TextView tv_pat_contact = (TextView) dialog.findViewById(R.id.tv_pat_contact);

        String jsonValue = child.getOrderedDate();
        String timestamp1 = jsonValue.split("\\(")[1].split("\\+")[0];
        Date createdOn1 = new Date();
        try {
            createdOn1  = new Date(Long.parseLong(timestamp1));
        }catch (NumberFormatException  numberFormatException){
        }         SimpleDateFormat orderedFormat = new SimpleDateFormat("dd/MM/yyyy | h:mm");
        String orderedDate = orderedFormat.format(createdOn1);
        Log.d("formattedDate", orderedDate);
        System.out.print("formattedDate-->" + orderedDate);
        tv_ordered_date.setText(orderedDate.toString());

        tv_consultant_name.setText(group.getDoctorName().toString());
        tv_pat_name.setText(group.getPatname().toString());
        tv_pat_contact.setText(group.getMobile().toString());
        tv_ord_id.setText("Ord Id: " + group.getLabOrderId().toString());
        tv_txn_id.setText("Txn Id: " + child.getOrderTransactionId().toString());
        String patDob = group.getDob().toString();
        int patGender = group.getGenderId();
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


        tv_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        activity_bill_payment_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(bill_payable_amount.getText().toString())) {
                    dialog.dismiss();
                    payableAmount = bill_payable_amount.getText().toString();
                    payableAmount = payableAmount.replace("₹ ", "");
                    appNatureId = 0;
                    paymentDescription = "Pharmacy Amount";
                    if (paymentMode.equals("1")) {
                        callPaymentAPiforLabKims(bill_payment_activity_hIs_no.getText().toString(), group, child);
                    } else if (paymentMode.equals("2")) {
                        callPaymentAPiforLabKims(bill_payment_activity_hIs_no.getText().toString(), group, child);
                    } else if (paymentMode.equals("3")) {
                        callPaymentAPiforLabKims(bill_payment_activity_hIs_no.getText().toString(), group, child);
                    }
                } else {
                    bill_payable_amount.setError("Enter Amount to be paid.");
                }
            }
        });
        payment_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cash:
                        paymentMode = "1";
                        break;
                    case R.id.card:
                        paymentMode = "2";
                        break;
                    case R.id.cheque:
                        paymentMode = "3";
                        break;
                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.alpha = 1.0f; // Transparency
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private void callPaymentAPiforLabKims(String his, LabDatum group, LabChildDatum child) {
        String hId;
        if (group.getHospitalNo().equals("")) {
            hId = "0";
        } else {
            hId = group.getHospitalNo();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", group.getOrderBySubTenantId());
        obj.addProperty("HospitalNo", hId);
        obj.addProperty("AppnatureId", 0);
        obj.addProperty("UserRoleId", group.getUserRoleId());
        obj.addProperty("Description", "Lab");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", payableAmount);
        obj.addProperty("CollectedBy", userRoleId);
        obj.addProperty("ServiceType", 4);
        obj.addProperty("Mrno", group.getMrno());
        obj.addProperty("HIS_BillNo", his);
        obj.addProperty("MobileNo", group.getMobile().toString().trim());
        //obj.addProperty("LabOrderID", 0);
        //obj.addProperty("PharmacyOrderID", pharmacyModels.getPresOrderId());
        obj.addProperty("orderId", group.getLabOrderId());
        obj.addProperty("ScheduleId", 0);
        JsonArray objectKeyArray = new JsonArray();
        objectKeyArray.add(new JsonPrimitive(child.getOrderTransactionId().toString()));
        obj.add("OrdTxnIds", objectKeyArray);

        Log.d("postpaymentv4", obj.toString());

        if (paymentMode.equals("1")) {
            postPaymentForLabCash(obj, group);
        }
        if (paymentMode.equals("2")) {
            postPaymentForLabCard(obj, group);
        }
        if (paymentMode.equals("3")) {
            postPaymentForLabCash(obj, group);
        }
    }

    private void postPaymentForLabCard(JsonObject obj, final LabDatum labDatum) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentLabFee(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    Toast.makeText(LabOrderActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, labDatum);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(LabOrderActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, labDatum);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mOrderTransactionIdNull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }
                /*Toast.makeText(LabOrderActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                if (postPaymentModel.getMsg().equalsIgnoreCase("Payment Successfull") || postPaymentModel.getMsg().equalsIgnoreCase("Payment is already done.")) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //getPharmacyPrescOrdersUpdateBillingDone(pharmacyModels);
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, group);
                }*/
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showErrorDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LabOrderActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.show();
    }

    private void postPaymentForLabCash(JsonObject obj, final LabDatum labDatum) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentLabFee(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    Toast.makeText(LabOrderActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, labDatum);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(LabOrderActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, labDatum);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mOrderTransactionIdNull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }
                /*Toast.makeText(LabOrderActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                if (postPaymentModel.getMsg().equalsIgnoreCase("Payment Successfull") || postPaymentModel.getMsg().equalsIgnoreCase("Payment is already done.")) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //getPharmacyPrescOrdersUpdateBillingDone(pharmacyModels);
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, group);
                }*/
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {

                dismissLoadingDialog();
            }
        });
    }

    private void showSuccessDialog(String msg, final String data[], final LabDatum labDatum) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LabOrderActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reloadLabDetail();
                printBill(data, labDatum);
                dialog.dismiss();
            }
        });

        android.app.AlertDialog dialog = builder.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rd_ordered:
                orderKey = 1;
                labOrderExpandableAdapter.reloadData();
                bottom_layout.setVisibility(View.VISIBLE);
                spinner_lab_filter.setVisibility(View.VISIBLE);
                spinner_lab_filter.setSelection(0);
                spinner_search_by.setSelection(0);
                break;
            case R.id.rd_pendingorder:
                orderKey = 0;
                labOrderExpandableAdapter.reloadData();
                bottom_layout.setVisibility(View.GONE);
                laborder_listview.collapseGroup(pos);
                spinner_lab_filter.setVisibility(View.GONE);
                spinner_lab_filter.setSelection(0);
                spinner_search_by.setSelection(0);
                break;
        }
    }


    public void reloadLabDetail() {
        getLabOrderDataFromApi_v2();
    }

    public void getLabOrderDataFromApi_v2() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getLabOrdersList_v2(subTenantId, userRoleId, tvFromTime.getText().toString(), tvToTime.getText().toString(), new Callback<LabOrderModel>() {
            @Override
            public void success(LabOrderModel labOrderModel, Response response) {
                if (labOrderModel.getStatus() == 1) {
                    labOrderExpandableAdapter = new LabOrderExpandableAdapter(LabOrderActivity.this, labOrderModel.getData()) {
                        @Override
                        public void OnIndicatorClick(boolean isExpanded, int position) {
                            pos = position;
                            if (orderKey == 1) {
                                if (isExpanded) {
                                    laborder_listview.collapseGroup(position);
                                } else {
                                    laborder_listview.expandGroup(position);
                                }
                            } else if (orderKey == 0) {
                                laborder_listview.collapseGroup(position);
                            }
                        }

                        @Override
                        public void OnTextClick() {
                            //Do whatever you want to do on text click
                        }
                    };
                    laborder_listview.setAdapter(labOrderExpandableAdapter);
                } else {
                    Toast.makeText(LabOrderActivity.this, labOrderModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
                //swipeContainer.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "onResume");
        //getPharmacyDataFromApi_v1();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fromTime:
                fromDatePickerDialog.show();
                break;
            case R.id.tv_toTime:
                toDatePickerDialog.show();
                break;
            case R.id.iv_logout:
                SharedPreferences.Editor editor = mSharedPreference.edit();
                editor.remove(Constants.STATUS_ID_KEY);
                editor.remove(Constants.DOMAIN_KEY);
                editor.remove(Constants.LOGIN_ID_KEY);
                editor.remove(Constants.LOGIN_NAME_KEY);
                editor.remove(Constants.PIN_KEY);
                editor.remove(Constants.PASSWORD_KEY);
                editor.remove(Constants.USER_ROLE_ID_KEY);
                editor.remove(Constants.ROLE_ID_KEY);
                editor.remove(Constants.ADDRESS_ID_KEY);
                editor.remove(Constants.CONTACT_ID_KEY);
                editor.remove(Constants.DOB_KEY);
                editor.remove(Constants.GENDER_ID_KEY);
                editor.remove(Constants.UNAME_KEY);
                editor.remove(Constants.USER_ID_KEY);
                editor.remove(Constants.LAB_PHARMACY_TYPE);
                editor.apply();

                Intent intentLogout = new Intent(LabOrderActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                finish();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage("Do you want to exit application?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
                LabOrderActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();

        //super.onBackPressed();
    }

    private void setFromDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fromDate = Calendar.getInstance();
                fromDate.set(year, monthOfYear, dayOfMonth);
                tvFromTime.setText(dateFormatter.format(fromDate.getTime()));
                getLabOrderDataFromApi_v2();

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setToDateTimeField() {
        final Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                toDate = Calendar.getInstance();
                toDate.set(year, monthOfYear, dayOfMonth);
                tvToTime.setText(dateFormatter.format(toDate.getTime()));
                getLabOrderDataFromApi_v2();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(LabOrderActivity.this);
            progress.setCancelable(false);
            progress.setMessage(getString(R.string.loading_message));
        }
        progress.show();
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = getString(R.string.app_name) +
                " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    public void printBill(String data[], LabDatum group) {
        Calendar now = Calendar.getInstance();
        completeDate = now.get(Calendar.DATE) + "/" + now.get(Calendar.MONTH) + 1 + "/" + now.get(Calendar.YEAR);
        time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());

        if (group.getSub_tenant_id() == 500) {
            subTanantName = "BLK Hospital";
            subTanantAddress = "Pusa Road, Rajinder Nagar, New Delhi, Delhi 110005";
            subTanantContact = "Tel:+91-11-30403040";
        } else if (group.getSub_tenant_id() == 243) {
            subTanantName = "Sir Ganga Ram Hospital";
            subTanantAddress = "Rajinder Nagar, New Delhi-60";
            subTanantContact = "Tel:+91-11-25750000, 42254000, Fax:+91-1142251755";
        } else if (group.getSub_tenant_id() == 525) {
            subTanantName = "U K Nursing Home";
            subTanantAddress = "M-1, Main Road, Vikas Puri, Delhi, 110018";
            subTanantContact = "Tel:+91-11-40955555";
        } else if (group.getSub_tenant_id() == 528 || group.getSub_tenant_id() == 515 || group.getSub_tenant_id() == 547) {
            subTanantName = "KIMS Hospitals";
            subTanantAddress = "1-112 / 86, Survey No 55/ EE, Kondapur Village, Serilingampally Mandal, Hyderabad, Telangana 500084";
            subTanantContact = "Tel:+91-88973 14141";
        } else if (group.getSub_tenant_id() == 529) {
            subTanantName = "KIMS Hospitals";
            subTanantAddress = "1-112 / 86, Survey No 55/ EE, Kondapur Village, Serilingampally Mandal, Hyderabad, Telangana 500084";
            subTanantContact = "Tel:+91-88973 14141";
        }
        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(view);
                myWebView = null;
            }
        });
        String htmlDocument = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<title>Pay Slip</title>\n" +
                "<style type=\"text/css\">\n" +
                "*{ margin:0px; padding:0px;}\n" +
                ".payslipOuter{ margin:10px; padding:0px; width:700px; float:left; font-family:Arial, Helvetica, sans-serif; border:1px #ccc solid; }\n" +
                "table>tr>td{ border:none; outline:none; }\n" +
                "strong{ font-weight:normal;}\n" +
                ".invoiceTable > tbody > tr > th{ font-weight:normal;}\n" +
                ".payslipOuter h4{ font-weight:normal; font-size:15px; text-align:center; margin:3px 0px; font-weight:normal; }\n" +
                ".payslipOuter p{ text-align:center; font-size:13px; line-height:20px;}\n" +
                ".payslipOuter h3{ font-size:14px;  text-align:center; text-transform:uppercase; text-decoration:underline; line-height:22px; font-weight:normal;}\n" +
                ".receivedwith{ width:153px; font-size:14px; float:left; padding-top:12px; font-weight:bold}\n" +
                ".receivedwithValue{ width:100px; float:left;  }\n" +
                ".valueInput{ width:100%; border:1px dotted #aaa;  outline:none; border-top-color: #fff; border-left-color: #fff; border-right-color: #fff; height:20px; }\n" +
                "\n" +
                ".receivedwithValue, .sumrepeesValue, .towordsValue, .paymodeValue, .rupeesValue, .billnoValue, .dateValue, .patientnameValue, .cashcardValue, .hospitalIdValue, .rsinwordsValue, .amountwordsValue, .consultantValue{ float:left; margin-top:5px; }\n" +
                "\n" +
                ".receivedwith, .sumrepees, .towords, .paymode, .rupees, .signature, .billno, .date, .patientname, .cashcard, .hospitalId, .rsinwords, .amountWords{ font-size:14px; float:left; padding-top:12px; font-weight:normal; height:30px;}\n" +
                "\n" +
                "\n" +
                ".billno, .date, .patientname, .cashcard, .hospitalId, .consultant{ height:25px; font-size:13px; float:left; padding-top:12px; font-weight:normal; }\n" +
                "\n" +
                ".department{ text-align:center; width:100%; margin:0 auto; font-size:13px; font-weight:normal;}\n" +
                ".department .valueInput{ text-align:center;  font-weight:normal; border-bottom:0px; margin-top:5px;}\n" +
                "\n" +
                ".rupesOuter{ float:left; width:160px;}\n" +
                ".signatureOuter{ float:right; width:310px; text-align:center;} \n" +
                "\n" +
                ".signature{ width:155px; float:right}\n" +
                ".signatureValue{ width:155px; float:left;}\n" +
                "\n" +
                ".rsinwords{ width:1px;}\n" +
                ".sumrepees{ width:70px;}\n" +
                ".sumrepeesValue{ width:183px}\n" +
                ".towords{ width:72px;}\n" +
                ".towordsValue{ width:298px}\n" +
                ".paymode{ width:25px;}\n" +
                ".paymodeValue{width:125px;}\n" +
                ".rupees{ width:28px;}\n" +
                ".rupeesValue{width:120px;}\n" +
                ".rsinwordsValue{ width:535px;}\n" +
                ".consultant{ width:100%;}\n" +
                ".consultantValue{width:100%;}\n" +
                "\n" +
                "\n" +
                ".amountWords{ width:130px;}\n" +
                ".amountwordsValue{ width:546px;}\n" +
                "\n" +
                "\n" +
                ".billnoValue{ width:80px;}\n" +
                ".dateValue{ width:150px;} \n" +
                ".patientnameValue{ width:200px;}\n" +
                ".cashcardValue{ width:98px;}\n" +
                ".hospitalIdValue{ width:105px;}\n" +
                "\n" +
                ".billno{ width:50px;}\n" +
                ".date{ width:45px;}\n" +
                ".patientname{ width:95px;} \n" +
                ".cashcard{ width:77px}\n" +
                ".hospitalId{ width:75px;}\n" +
                "\n" +
                "\n" +
                "\n" +
                ".receivedwithOuter{ width:260px; float:left;}\n" +
                ".sumrepeesOuter{ width:253px; float:right;}\n" +
                ".paymodeOuter{ width:160px; float:left;}\n" +
                ".towordsOuter{ width:370px; float:right;}\n" +
                ".amoutnwordsOuter{ width:100%;}\n" +
                ".consultantOuter{ width:300px; margin:0px auto;}\n" +
                ".departmentOuter{ width:200px; margin:5px auto;}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                ".billnoOuter{ width:200px; float:left;}\n" +
                ".dateOuter{ width:200px; float:right;}\n" +
                ".patientnameOuter{ width:310px; float:left;}\n" +
                ".cashcardOuter{ width:175px; float:right;}\n" +
                ".hospitalIdOuter{ width:200px; float:left;}\n" +
                "\n" +
                ".headpart{ background-color:#f2f2f2;}\n" +
                "\n" +
                ".borderLine{ width:100%; margin-top:15px; border-bottom:1px #333 dashed;}\n" +
                ".bordervLine{ border-left:1px #333 solid; height:100%; width:1px; height:250px;}\n" +
                "\n" +
                ".invoiceTable{ width:100%; font-size:14px;}\n" +
                ".invoiceTable > tbody > tr > td{ padding:7px 10px; text-align:left; }\n" +
                ".invoiceTable > tbody > tr > th{ padding:10px 10px; text-align:left; }\n" +
                "\n" +
                ".consultantValue .valueInput{ border-bottom:none; font-size:20px; text-align:center;}\n" +
                "</style>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"payslipOuter\">\n" +
                "<table width=\"100%\" style=\"padding:5px;\">\n" +
                "\t<tr>\n" +
                "\t\t<td colspan=\"2\"><div class=\"consultantOuter\">\n" +
                "        <div class=\"consultantValue\"><input type=\"text\" class=\"valueInput\" value=\"" + group.getDoctorName() + "\"></div></div>\n" +
                "        \n" +
                "        <div class=\"departmentOuter\">\n" +
                "        <div class=\"department\"><input type=\"text\" class=\"valueInput\" value=\"" + data[2] + "\"></div></div>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "  <tr>\n" +
                "    <td colspan=\"2\"><h4>" + subTanantName + "</h4>\n" +
                "\t<p> " + subTanantAddress + "</br>\n" +
                "    " + subTanantContact + "</br></p>\n" +
                "  <h3 style=\"margin-bottom:20px;\"> Pharmacy Receipt</h3>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    \n" +
                " <tr>\n" +
                "    \t<td colspan=\"2\"><div class=\"billnoOuter\"><div class=\"billno\">Bill No.</div>\n" +
                "   \t    <div class=\"billnoValue\"><input type=\"text\" class=\"valueInput\" value = \"" + data[1] + "\"></div></div> <div class=\"dateOuter\"><div class=\"date\">Date</div> <div class=\"dateValue\"><input type=\"text\" class=\"valueInput\" value=\"" + completeDate + " | " + time + "\"></div></div></td>\n" +
                "    </tr>\n" +
                "    \n" +
                "      <tr>\n" +
                "    \t<td colspan=\"2\"><div class=\"patientnameOuter\"><div class=\"patientname\">Patient Name</div>\n" +
                "   \t    <div class=\"patientnameValue\"><input type=\"text\" class=\"valueInput\" value=\"" + group.getPatname() + "\"></div></div> \n" +
                "        \n" +
                /*"        <div class=\"hospitalIdOuter\"><div class=\"hospitalId\">Hospital ID</div>\n" +
                "        <div class=\"hospitalIdValue\"><input type=\"text\" class=\"valueInput\" value=\"" + tvHospitalId.getText().toString() + "\"></div></div>\n" +*/
                "        \n" +
                "        <div class=\"cashcardOuter\"><div class=\"cashcard\">E-Wallet ID</div> <div class=\"cashcardValue\"><input type=\"text\" class=\"valueInput\" value = \"" + data[0] + "\"></div></div></td>\n" +
                "    </tr>\n" +
                "   \n" +
                "</table>\n" +
                "<div style=\"border-top:1px #333 solid; height:1px;\"></div>\n" +
                "<table width=\"100%\" class=\"invoiceTable\">    \n" +
                "  <tr style=\"border-bottom:1px #333 solid\">\n" +
                "  \t<th>Description</th>\n" +
                "    <th>&nbsp;</th>\n" +
                "    <th style=\"width:120px;\">Amount</th>\n" +
                "  </tr>\n" +
                " </table>\n" +
                " <div style=\"border-top:1px #333 solid; height:1px;\"></div>\n" +
                " <table width=\"100%\" class=\"invoiceTable\">\n" +
                "  <tr>\n" +
                "  \t<td>" + paymentDescription + "</td>\n" +
                "     <td rowspan=\"3\" style=\"width:1px; border-left:1px #333 solid; padding:7px 0px;\">&nbsp;</td>\n" +
                "    <td style=\"width:120px;\">Rs. " + payableAmount + "</td>\n" +
                "  </tr>\n" +
                "\n" +
                "\n" +
                "    <tr>\n" +
                "    <td>&nbsp; </td>\n" +
                "    <td>&nbsp;</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td style=\"border-top:1px #333 solid; text-align:right; padding:7px 10px;\"><strong>Total Amount</strong></td>\n" +
                "    <td style=\"border-top:1px #333 solid; padding:7px 10px;\"><strong>Rs. " + payableAmount + "</strong></td>\n" +
                "  </tr>\n" +
                "   \n" +
                "    <tr>\n" +
                "    <td colspan=\"3\"><div class=\"amoutnwordsOuter\">\n" +
                "    <div class=\"amountWords\">Amount in words:</div>\n" +
                "    <div class=\"amountwordsValue\"><input type=\"text\" class=\"valueInput\" value = \"" + Helper.convert(Integer.parseInt(payableAmount)) + " Only\"></div>\n" +
                "     </div></td>\n" +
                "    </tr>\n" +
                "    \n" +
                "     \n" +
                "  </table>\n" +
                "  \n" +
                " <!-- <div style=\"border-top:1px #333 solid; height:1px;\"></div>-->\n" +
                "  \n" +
                "  <table width=\"100%\" style=\"padding:5px;\">\n" +
                "     <tr>     \n" +
                "    <td><div class=\"signatureOuter\">\n" +
                "      <div class=\"signature\">Authorized Signatory</br>\n" +
                "      </div>\n" +
                "     <!-- <div class=\"signatureValue\"><input type=\"text\" class=\"valueInput\"></div>--></div></td>\n" +
                "    </tr>\n" +
                "   \n" +
                "\n" +
                "  \n" +
                "</table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";
        Log.d("htmlDocument", htmlDocument);
        webView.loadDataWithBaseURL(null, htmlDocument,
                "text/html", "UTF-8", null);
        myWebView = webView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
