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
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mcura.mcurapharmacy.Helper.Helper;
import com.mcura.mcurapharmacy.Interface.PharmacyInterface;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.Constants;
import com.mcura.mcurapharmacy.Utils.EnumType;
import com.mcura.mcurapharmacy.adpater.CounterSpinnerAdapter;
//import com.mcura.mcurapharmacy.adpater.PharmacyAdapter;
import com.mcura.mcurapharmacy.adpater.PharmacyExpendableAdapter;
import com.mcura.mcurapharmacy.adpater.PharmacyOrderDialogAdapter;
import com.mcura.mcurapharmacy.adpater.PharmacyPaidOrderDialogAdapter;
import com.mcura.mcurapharmacy.adpater.PharmacyUnpaidOrderDialogAdapter;
import com.mcura.mcurapharmacy.com.allen.expandablelistview.SwipeMenuExpandableCreator;
import com.mcura.mcurapharmacy.com.allen.expandablelistview.SwipeMenuExpandableListView;
import com.mcura.mcurapharmacy.com.baoyz.swipemenulistview.SwipeMenu;
import com.mcura.mcurapharmacy.com.baoyz.swipemenulistview.SwipeMenuItem;
import com.mcura.mcurapharmacy.model.CounterModel;
import com.mcura.mcurapharmacy.model.FollowUpModel;
import com.mcura.mcurapharmacy.model.PharmacyChildDatum;
import com.mcura.mcurapharmacy.model.PharmacyDatum;
import com.mcura.mcurapharmacy.model.PharmacyModel;
import com.mcura.mcurapharmacy.model.PharmacyOrderDetailModel;
import com.mcura.mcurapharmacy.model.PostPaymentModel;
import com.mcura.mcurapharmacy.view.SegmentedGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PharmacyActivity extends AppCompatActivity implements PharmacyInterface, View.OnClickListener, AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {

    public static String counterNo = "0";
    String counterName[] = {"SELECT COUNTER", "Pharmacy"};
    int counterId[] = {0, 22777};

    public static double totalAmount;
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
    private SwipeMenuExpandableListView patient_listview;
    //private PharmacyAdapter pharmacyAdapter;
    private Spinner pharmacy_status;
    String[] pharmacyStatus, pharmacyStatusArray;
    private SimpleDateFormat dateFormatter;
    private TextView tvFromTime, tvToTime;
    Calendar now, fromDate, toDate;
    private DatePickerDialog fromDatePickerDialog, toDatePickerDialog;
    private SharedPreferences mSharedPreference;
    public ImageView iv_search_pat;
    public static String searchBy;
    private ImageButton iv_logout;
    ArrayAdapter spinnerArrayAdapter;
    int roleID, userRoleId, currentStatusId, subTenantId;
    String subTenantName;
    private TextView tv_subtenant_name;
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
    private TextView queue;
    private String estimateBillId;
    private String totalDays;
    private String amount;
    private String paymentMode;
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
    private PharmacyModel[] pharmacyModelArray;
    private Snackbar snackbar;
    private RelativeLayout parent_layout;
    public static int orderKey;
    private int pos;
    private PharmacyExpendableAdapter pharmacyExpendableAdapter;
    private LinearLayout bottom_layout;
    private List<PharmacyModel> pharmacyModelList;
    private Spinner spinner_pharmacy_filter;
    private CounterSpinnerAdapter counterSpinnerAdapter;
    private ArrayList<CounterModel> counterModelArrayList;
    private Spinner spinner_search_by;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);
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
                Intent i = new Intent(PharmacyActivity.this, QueueActivity.class);
                i.putExtra("ClickTo","PharmacyActivity");
                startActivity(i);
                finish();
            }
        });
        initView();
    }


    private void initView() {
        setCounterData();
        //getTransactionListFromApi();
        spinner_search_by = (Spinner) findViewById(R.id.spinner_search_by);
        spinner_pharmacy_filter = (Spinner) findViewById(R.id.spinner_pharmacy_filter);

        counterSpinnerAdapter = new CounterSpinnerAdapter(PharmacyActivity.this,
                android.R.layout.simple_spinner_item,
                counterModelArrayList);
        spinner_pharmacy_filter.setAdapter(counterSpinnerAdapter);

        String[] searchByList = {"Select", "Patient", "Doctor"};

        spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                searchByList);
        spinner_search_by.setAdapter(spinnerArrayAdapter);
        spinner_pharmacy_filter.setSelection(0);
        bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
        segmentedGroup = (SegmentedGroup) findViewById(R.id.appointment_segment);
        segmentedGroup.check(R.id.rd_pendingorder);
        segmentedGroup.setOnCheckedChangeListener(this);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US); //dd-MM-yyyy
        now = Calendar.getInstance();
        parent_layout = (RelativeLayout) findViewById(R.id.parent_layout);
        tv_subtenant_name = (TextView) findViewById(R.id.tv_subtenant_name);
        tv_subtenant_name.setText(subTenantName);
        pharmacy_status = (Spinner) findViewById(R.id.pharmacy_status);
        iv_logout = (ImageButton) findViewById(R.id.iv_logout);
        iv_logout.setOnClickListener(this);
        search_by_patient = (EditText) findViewById(R.id.et_search_by_patient);
        /*swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                getPharmacyDataFromApi_v1();
            }
        });*/
        //swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        /*swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                getPharmacyDataFromApi_v1();
            }
        });*/
        spinner_pharmacy_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchBy = "Pharmacy User";
                CounterModel counterModel = counterSpinnerAdapter.getItem(position);
                counterNo = counterModel.getCounterId() + "";
                Log.d("counterNo", counterNo + "");
                pharmacyExpendableAdapter.filterData(counterModel.getCounterId() + "");
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
                pharmacyExpendableAdapter.filterData(editable.toString());

                /*filter = pharmacyAdapter.getFilter();
                filter.filter(editable);*/
            }
        });

        spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                pharmacyStatus);
        pharmacy_status.setAdapter(spinnerArrayAdapter);
        /*pharmacy_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                search_by_patient.getText().clear();
                searchBy = "status";
                switch (position) {
                    case 0:
                        getPharmacyDataFromApi_v1();
                        break;
                    case 1:
                        filter = pharmacyAdapter.getFilter();
                        filter.filter("1");
                        break;
                    case 2:
                        filter = pharmacyAdapter.getFilter();
                        filter.filter("3");
                        break;
                    case 3:
                        filter = pharmacyAdapter.getFilter();
                        filter.filter("8");
                        break;
                    case 4:
                        filter = pharmacyAdapter.getFilter();
                        filter.filter("7");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        patient_listview = (SwipeMenuExpandableListView) findViewById(R.id.patient_listview);

        tvFromTime = (TextView) findViewById(R.id.tv_fromTime);
        tvFromTime.setText(dateFormatter.format(now.getTime()));
        tvToTime = (TextView) findViewById(R.id.tv_toTime);
        tvToTime.setText(dateFormatter.format(now.getTime()));
        tvFromTime.setOnClickListener(this);
        tvToTime.setOnClickListener(this);


        //click on row
        patient_listview.setOnItemClickListener(this);

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
                item2.setIcon(R.mipmap.ic_deliver);
                menu.addMenuItem(item2);
                SwipeMenuItem item3 = new SwipeMenuItem(getApplicationContext());
                item3.setBackground(new ColorDrawable(Color.rgb(0xCD, 0xCD, 0xCD)));
                item3.setWidth(dp2px(90));
                item3.setIcon(R.mipmap.ic_cancel);
                menu.addMenuItem(item3);
            }

            private void createMenu2(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE0, 0x3F)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.mipmap.ic_deliver);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.mipmap.ic_cancel);
                menu.addMenuItem(item2);
            }

            private void createMenu3(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1, 0xF5)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.mipmap.ic_deliver);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.mipmap.ic_cancel);
                menu.addMenuItem(item2);
            }
        };
        patient_listview.setMenuCreator(creator);
        patient_listview.setOnMenuItemClickListener(new SwipeMenuExpandableListView.OnMenuItemClickListenerForExpandable() {
            @Override
            public boolean onMenuItemClick(int groupPosition, int childPosition, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // payment
                        if (pharmacyExpendableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 3) {
                            showPaymentDialogForChild(pharmacyExpendableAdapter.getGroup(groupPosition), pharmacyExpendableAdapter.getChild(groupPosition, childPosition));
                        } else if (pharmacyExpendableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 12) {
                            Toast.makeText(PharmacyActivity.this, "Order is cancelled.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PharmacyActivity.this, "Payment Already Done.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        // deliver
                        //Toast.makeText(PharmacyActivity.this,"Deliver",Toast.LENGTH_SHORT).show();
                        if (pharmacyExpendableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 8) {
                            JsonObject obj = new JsonObject();
                            obj.addProperty("UserRoleID", pharmacyExpendableAdapter.getGroup(groupPosition).getUserRoleId());
                            obj.addProperty("presciptionID", pharmacyExpendableAdapter.getGroup(groupPosition).getPrescriptionId());
                            obj.addProperty("orderTxnId", pharmacyExpendableAdapter.getChild(groupPosition, childPosition).getOrderTransactionId());
                            obj.addProperty("Statusid", 7);
                            Log.d("jsonObj", obj.toString());
                            showConfirmationDialog(getString(R.string.deliverConfirmationMsg),obj);
                        }else if (pharmacyExpendableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 12) {
                            Toast.makeText(PharmacyActivity.this, "Order is cancelled.", Toast.LENGTH_SHORT).show();
                        }else if (pharmacyExpendableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 7) {
                            Toast.makeText(PharmacyActivity.this, "Order is delivered.", Toast.LENGTH_SHORT).show();
                        }else if (pharmacyExpendableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 3) {
                            Toast.makeText(PharmacyActivity.this, "Payment is not done.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PharmacyActivity.this, "Payment is not done.", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case 2:
                        //cancel
                        //Toast.makeText(PharmacyActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
                        if (pharmacyExpendableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 3) {
                            JsonObject jsonObj = new JsonObject();
                            jsonObj.addProperty("UserRoleID", pharmacyExpendableAdapter.getGroup(groupPosition).getUserRoleId());
                            jsonObj.addProperty("presciptionID", pharmacyExpendableAdapter.getGroup(groupPosition).getPrescriptionId());
                            jsonObj.addProperty("orderTxnId", pharmacyExpendableAdapter.getChild(groupPosition, childPosition).getOrderTransactionId());
                            jsonObj.addProperty("Statusid", 12);
                            Log.d("jsonObj", jsonObj.toString());
                            showConfirmationDialog(getString(R.string.orderCancelMsg),jsonObj);
                        }else if (pharmacyExpendableAdapter.getChild(groupPosition, childPosition).getOrderStatus() == 12) {
                            Toast.makeText(PharmacyActivity.this, "Order already is cancelled.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PharmacyActivity.this, "Order Cannot be cancelled", Toast.LENGTH_SHORT).show();
                        }

                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
// 5.Set OnGroupClickListener
        patient_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                /*Toast.makeText(PharmacyActivity.this, "group " + groupPosition + " clicked", Toast.LENGTH_SHORT)
                        .show();*/
                return false;
            }
        });


        // 6.Set OnChildClickListener
        patient_listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                /*Toast.makeText(PharmacyActivity.this,
                        "group " + groupPosition + " child " + childPosition + " clicked", Toast.LENGTH_SHORT).show();*/
                PharmacyModel pharmacyModel = pharmacyExpendableAdapter.getGroup(groupPosition);
                PharmacyChildDatum pharmacyChildDatum = pharmacyExpendableAdapter.getChild(groupPosition, childPosition);
                callSavedPharmacyOrderDetailApi(pharmacyModel, pharmacyChildDatum);
                return false;
            }
        });


        setFromDateTimeField();
        setToDateTimeField();
        getPharmacyDataFromApi_v1();
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

    private void showConfirmationDialog(String msg, final JsonObject obj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PharmacyActivity.this);
        builder.setTitle("Confirmation Message");
        builder.setMessage(msg);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setPharmacyStatus(obj);
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

    private void setPharmacyStatus(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.pharmacyPrescOrdersUpdateBy_TxnId(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                dismissLoadingDialog();
                reloadPharmacyDetail();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }/*{
        model = pharmacyAdapter.getItem(position);
        patName = pharmacyAdapter.getItem(position).getPatname().toString();
        patContact = pharmacyAdapter.getItem(position).getMobile().toString().trim();
        consultantName = pharmacyAdapter.getItem(position).getDoctorName().toString();
        patDob = pharmacyAdapter.getItem(position).getDob().toString();
        hospitalId = pharmacyAdapter.getItem(position).getHospitalNo();
        selectedUserRoleId = pharmacyAdapter.getItem(position).getUserRoleId();
        mrno = pharmacyAdapter.getItem(position).getMrno();
        prescriptionId = pharmacyAdapter.getItem(position).getPrescriptionId();
        prescriptionOrderId = model.getPresOrderId();
        int patGender = pharmacyAdapter.getItem(position).getGenderId();
        paidStatus = model.getPaidStatus();
        statusId = model.getStatusId();
        String timestamp = patDob.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date(Long.parseLong(timestamp));
        SimpleDateFormat sdf = new SimpleDateFormat("MM,dd,yyyy");
        String formattedDate = sdf.format(createdOn);
        System.out.println("formattedDate-->" + formattedDate);
        String[] out = formattedDate.split(",");

        int year = Integer.parseInt(out[2]);
        int month = Integer.parseInt(out[0]);
        int day = Integer.parseInt(out[1]);
        if (patGender == 1) {
            patAgeGender = Helper.getAge(year, month, day) + "/M";
        } else {
            patAgeGender = Helper.getAge(year, month, day) + "/F";
        }
        if (orderKey == 0) {
            //callPharmacyOrderDetailApi();
        } else {
            //callSavedPharmacyOrderDetailApi();
        }

    }*/

    private void callPharmacyOrderDetailApi() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.pharmacyOrderdetail(prescriptionId, selectedUserRoleId, new Callback<PharmacyOrderDetailModel[]>() {
            @Override
            public void success(PharmacyOrderDetailModel[] pharmacyOrderDetailModels, Response response) {
                //showPharmacyOrderDialog(pharmacyOrderDetailModels);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showPharmacyOrderDialog(PharmacyOrderDetailModel[] pharmacyOrderDetailModels) {
        try {
            JSONArray m_jArry = new JSONArray(loadJSONFromAsset());
            followUpModelArrayList = new ArrayList<>();

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

        AlertDialog.Builder builder = new AlertDialog.Builder(PharmacyActivity.this);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(PharmacyActivity.this);
        View convertView = (View) inflater.inflate(R.layout.pharmacy_main_order_dialog, null);
        builder.setView(convertView);
        ImageButton ib_close = (ImageButton) convertView.findViewById(R.id.ib_close);
        TextView tv_total_amount = (TextView) convertView.findViewById(R.id.tv_total_amount);
        ListView pharmacy_order_listview = (ListView) convertView.findViewById(R.id.pharmacy_order_listview);
        ImageView tv_check_uncheck = (ImageView) convertView.findViewById(R.id.tv_check_uncheck);

        TextView tv_consultant_name = (TextView) convertView.findViewById(R.id.tv_consultant_name);
        TextView tv_pat_name = (TextView) convertView.findViewById(R.id.tv_pat_name);
        TextView tv_pat_dob_age = (TextView) convertView.findViewById(R.id.tv_pat_dob_age);
        TextView tv_pat_contact = (TextView) convertView.findViewById(R.id.tv_pat_contact);
        TextView btn_paynow = (TextView) convertView.findViewById(R.id.btn_paynow);
        tv_consultant_name.setText(consultantName);
        tv_pat_name.setText(patName);
        tv_pat_contact.setText(patContact);
        tv_pat_dob_age.setText(patAgeGender);

//        PharmacyOrderDialogAdapter pharmacyOrderDialogAdapter = new PharmacyOrderDialogAdapter(PharmacyActivity.this,model, followUpModelArrayList,pharmacyOrderDetailModels,tv_total_amount,btn_paynow,tv_check_uncheck);
//        pharmacy_order_listview.setAdapter(pharmacyOrderDialogAdapter);
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
        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rd_ordered:
                orderKey = 1;
                pharmacyExpendableAdapter.reloadData();
                bottom_layout.setVisibility(View.VISIBLE);
                spinner_pharmacy_filter.setVisibility(View.VISIBLE);
                spinner_pharmacy_filter.setSelection(0);
                spinner_search_by.setSelection(0);
                break;
            case R.id.rd_pendingorder:
                orderKey = 0;
                pharmacyExpendableAdapter.reloadData();
                bottom_layout.setVisibility(View.GONE);
                patient_listview.collapseGroup(pos);
                spinner_pharmacy_filter.setVisibility(View.GONE);
                spinner_pharmacy_filter.setSelection(0);
                spinner_search_by.setSelection(0);
                break;
        }
    }


    private void callSavedPharmacyOrderDetailApi(final PharmacyModel pharmacyModel, final PharmacyChildDatum pharmacyChildDatum) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.pharmacyOrderDetailByTxnId(pharmacyModel.getPrescriptionId(), pharmacyModel.getUserRoleId(), pharmacyChildDatum.getOrderTransactionId(), new Callback<PharmacyOrderDetailModel[]>() {
            @Override
            public void success(PharmacyOrderDetailModel[] pharmacyOrderDetailModels, Response response) {
                showUnpaidPharmacyOrderDialog(pharmacyModel, pharmacyOrderDetailModels, pharmacyChildDatum);
                /*if (paidStatus == 0) {
                    Log.d("paidStatus","0");
                    showUnpaidPharmacyOrderDialog(pharmacyModel, pharmacyOrderDetailModels, pharmacyChildDatum);
                } else if (paidStatus == 1) {
                    Log.d("paidStatus","1");
                    showPaidPharmacyOrderDialog(pharmacyOrderDetailModels);
                }*/
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showPaidPharmacyOrderDialog(PharmacyOrderDetailModel[] pharmacyOrderDetailModels) {
        try {
            JSONArray m_jArry = new JSONArray(loadJSONFromAsset());
            followUpModelArrayList = new ArrayList<>();

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

        AlertDialog.Builder builder = new AlertDialog.Builder(PharmacyActivity.this);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(PharmacyActivity.this);
        View convertView = (View) inflater.inflate(R.layout.pharmacy_paid_order_dialog, null);
        builder.setView(convertView);
        ImageButton ib_close = (ImageButton) convertView.findViewById(R.id.ib_close);
        TextView tv_total_amount = (TextView) convertView.findViewById(R.id.tv_total_amount);
        ListView pharmacy_order_listview = (ListView) convertView.findViewById(R.id.pharmacy_order_listview);
        ImageView tv_check_uncheck = (ImageView) convertView.findViewById(R.id.tv_check_uncheck);

        TextView tv_consultant_name = (TextView) convertView.findViewById(R.id.tv_consultant_name);
        TextView tv_pat_name = (TextView) convertView.findViewById(R.id.tv_pat_name);
        TextView tv_pat_dob_age = (TextView) convertView.findViewById(R.id.tv_pat_dob_age);
        TextView tv_pat_contact = (TextView) convertView.findViewById(R.id.tv_pat_contact);
        btn_deliver = (Button) convertView.findViewById(R.id.btn_deliver);
        tv_consultant_name.setText(consultantName);
        tv_pat_name.setText(patName);
        tv_pat_contact.setText(patContact);
        tv_pat_dob_age.setText(patAgeGender);

        PharmacyPaidOrderDialogAdapter pharmacypaidOrderDialogAdapter = new PharmacyPaidOrderDialogAdapter(PharmacyActivity.this, followUpModelArrayList, pharmacyOrderDetailModels, tv_total_amount, btn_deliver, hospitalId, selectedUserRoleId, mrno, patContact, tv_check_uncheck);
        pharmacy_order_listview.setAdapter(pharmacypaidOrderDialogAdapter);
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
        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (statusId == 7) {
            btn_deliver.setText("Delivered");
            btn_deliver.setBackgroundResource(R.drawable.paid_btn_bg);
            btn_deliver.setTextColor(ContextCompat.getColor(PharmacyActivity.this, R.color.black));
            btn_deliver.setEnabled(false);
            btn_deliver.setClickable(false);
        } else {
            btn_deliver.setText("Deliver");
            btn_deliver.setBackgroundResource(R.drawable.btn_bg);
            btn_deliver.setTextColor(ContextCompat.getColor(PharmacyActivity.this, R.color.white));
            btn_deliver.setEnabled(true);
            btn_deliver.setClickable(true);
        }
        btn_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showComfirmationDialog();
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("FollowUp.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void showUnpaidPharmacyOrderDialog(final PharmacyModel pharmacyModel, PharmacyOrderDetailModel[] pharmacyOrderDetailModels, final PharmacyChildDatum pharmacyChildDatum) {
        try {
            JSONArray m_jArry = new JSONArray(loadJSONFromAsset());
            followUpModelArrayList = new ArrayList<>();

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

        AlertDialog.Builder builder = new AlertDialog.Builder(PharmacyActivity.this);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(PharmacyActivity.this);
        View convertView = (View) inflater.inflate(R.layout.pharmacy_unpaid_order_dialog, null);
        builder.setView(convertView);
        ImageButton ib_close = (ImageButton) convertView.findViewById(R.id.ib_close);
        tv_total_amount = (TextView) convertView.findViewById(R.id.tv_total_amount);
        ListView pharmacy_order_listview = (ListView) convertView.findViewById(R.id.pharmacy_order_listview);
        ImageView tv_check_uncheck = (ImageView) convertView.findViewById(R.id.tv_check_uncheck);

        TextView tv_order_cancel = convertView.findViewById(R.id.tv_order_cancel);
        TextView tv_txn_id = (TextView) convertView.findViewById(R.id.tv_txn_id);
        TextView tv_pat_name = (TextView) convertView.findViewById(R.id.tv_pat_name);
        TextView tv_pat_dob_age = (TextView) convertView.findViewById(R.id.tv_pat_dob_age);
        TextView tv_pat_contact = (TextView) convertView.findViewById(R.id.tv_pat_contact);
        TextView tv_ordered_date = (TextView) convertView.findViewById(R.id.tv_ordered_date);
        TextView btn_paynow = (TextView) convertView.findViewById(R.id.btn_paynow);
        Button iv_deliver = (Button) convertView.findViewById(R.id.iv_deliver);
        TextView tv_delivered_time = (TextView) convertView.findViewById(R.id.tv_delivered_time);
        if (pharmacyChildDatum.getOrderStatus() == 3) {
            btn_paynow.setVisibility(View.VISIBLE);
            iv_deliver.setVisibility(View.GONE);
            tv_total_amount.setTextColor(ContextCompat.getColor(PharmacyActivity.this, R.color.red));
            tv_delivered_time.setVisibility(View.GONE);
            tv_order_cancel.setVisibility(View.GONE);
        } else if (pharmacyChildDatum.getOrderStatus() == 8) {
            btn_paynow.setVisibility(View.GONE);
            iv_deliver.setVisibility(View.VISIBLE);
            tv_total_amount.setTextColor(ContextCompat.getColor(PharmacyActivity.this, R.color.green));
            tv_delivered_time.setVisibility(View.GONE);
            tv_order_cancel.setVisibility(View.GONE);
        } else if (pharmacyChildDatum.getOrderStatus() == 7) {
            btn_paynow.setVisibility(View.GONE);
            tv_total_amount.setTextColor(ContextCompat.getColor(PharmacyActivity.this, R.color.green));
            iv_deliver.setVisibility(View.GONE);
            tv_delivered_time.setVisibility(View.VISIBLE);
            tv_delivered_time.setText(Helper.changeDateFormat(pharmacyChildDatum.getDeliveredDate()));
            tv_order_cancel.setVisibility(View.GONE);
        } else if (pharmacyChildDatum.getOrderStatus() == 12) {
            btn_paynow.setVisibility(View.GONE);
            tv_total_amount.setTextColor(ContextCompat.getColor(PharmacyActivity.this, R.color.red));
            iv_deliver.setVisibility(View.GONE);
            tv_delivered_time.setVisibility(View.GONE);
            tv_delivered_time.setText(Helper.changeDateFormat(pharmacyChildDatum.getDeliveredDate()));
            tv_order_cancel.setVisibility(View.VISIBLE);
        }
        tv_txn_id.setText("Txn Id: " + pharmacyChildDatum.getOrderTransactionId() + "");
        tv_pat_name.setText(pharmacyModel.getPatname().toString());
        tv_pat_contact.setText(pharmacyModel.getMobile().toString());

        String jsonValue = pharmacyChildDatum.getOrderedDate();
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

        PharmacyUnpaidOrderDialogAdapter pharmacyOrderDialogAdapter = new PharmacyUnpaidOrderDialogAdapter(PharmacyActivity.this, followUpModelArrayList, pharmacyModel, pharmacyOrderDetailModels, tv_total_amount, btn_paynow, tv_check_uncheck);
        pharmacy_order_listview.setAdapter(pharmacyOrderDialogAdapter);
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
                showPharmacyBillPayDialog(pharmacyModel, pharmacyChildDatum);
            }
        });
        iv_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                JsonObject obj = new JsonObject();
                obj.addProperty("UserRoleID", pharmacyModel.getUserRoleId());
                obj.addProperty("presciptionID", pharmacyModel.getPrescriptionId());
                obj.addProperty("orderTxnId", pharmacyChildDatum.getOrderTransactionId());
                obj.addProperty("Statusid", 7);
                Log.d("jsonObj", obj.toString());
                showConfirmationDialog(getString(R.string.deliverConfirmationMsg),obj);
            }
        });
    }


    private void showPaymentDialogForChild(final PharmacyModel pharmacyModel, final PharmacyChildDatum pharmacyChildDatum) {
        paymentMode = "1";
        final Dialog dialog = new Dialog(PharmacyActivity.this);
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
        bill_payable_amount.setText((Math.round(pharmacyChildDatum.getOrderedAmount() * 100.0) / 100.0) + "");
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

        String jsonValue = pharmacyChildDatum.getOrderedDate();
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

        tv_consultant_name.setText(pharmacyModel.getDoctorName().toString());
        tv_pat_name.setText(pharmacyModel.getPatname().toString());
        tv_pat_contact.setText(pharmacyModel.getMobile().toString());
        tv_ord_id.setText("Ord Id: " + pharmacyModel.getPresOrderId().toString());
        tv_txn_id.setText("Txn Id: " + pharmacyChildDatum.getOrderTransactionId() + "");
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
                        callPaymentAPiforPharmacyKims(bill_payment_activity_hIs_no.getText().toString(), pharmacyModel, pharmacyChildDatum);
                    } else if (paymentMode.equals("2")) {
                        callPaymentAPiforPharmacyKims(bill_payment_activity_hIs_no.getText().toString(), pharmacyModel, pharmacyChildDatum);
                    } else if (paymentMode.equals("3")) {
                        callPaymentAPiforPharmacyKims(bill_payment_activity_hIs_no.getText().toString(), pharmacyModel, pharmacyChildDatum);
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

    private void showPharmacyBillPayDialog(final PharmacyModel pharmacyModel, final PharmacyChildDatum pharmacyChildDatum) {
        paymentMode = "1";
        final Dialog dialog = new Dialog(PharmacyActivity.this);
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
        bill_payable_amount.setText(tv_total_amount.getText() + "");
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

        String jsonValue = pharmacyChildDatum.getOrderedDate();
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

        tv_consultant_name.setText(pharmacyModel.getDoctorName().toString());
        tv_pat_name.setText(pharmacyModel.getPatname().toString());
        tv_pat_contact.setText(pharmacyModel.getMobile().toString());
        tv_ord_id.setText("Ord Id: " + pharmacyModel.getPresOrderId().toString());
        tv_txn_id.setText("Txn Id: " + pharmacyChildDatum.getOrderTransactionId() + "");
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
                        callPaymentAPiforPharmacyKims(bill_payment_activity_hIs_no.getText().toString(), pharmacyModel, pharmacyChildDatum);
                    } else if (paymentMode.equals("2")) {
                        callPaymentAPiforPharmacyKims(bill_payment_activity_hIs_no.getText().toString(), pharmacyModel, pharmacyChildDatum);
                    } else if (paymentMode.equals("3")) {
                        callPaymentAPiforPharmacyKims(bill_payment_activity_hIs_no.getText().toString(), pharmacyModel, pharmacyChildDatum);
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

    /*public void getPharmacyDataFromApi() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.pharmacyOrderSearchList(subTenantId, 0, userRoleId, tvFromTime.getText().toString(), tvToTime.getText().toString(), new Callback<PharmacyModel[]>() {
            @Override
            public void success(PharmacyModel[] pharmacyModels, Response response) {
                pharmacyModelArray = pharmacyModels;
                Log.e("UserRoleId", String.valueOf(userRoleId));
                Log.e("CurrentStatusId", String.valueOf(currentStatusId));
                // Toast.makeText(PharmacyActivity.this,"getPharmacyData_response"+response,Toast.LENGTH_LONG).show();
                pharmacyAdapter = new PharmacyAdapter(PharmacyActivity.this, pharmacyModels);
                patient_listview.setAdapter(pharmacyAdapter);
                swipeContainer.setRefreshing(false);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
                swipeContainer.setRefreshing(false);
            }
        });
    }*/
    public void reloadPharmacyDetail() {
        getPharmacyDataFromApi_v1();
    }

    public void getPharmacyDataFromApi_v1() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.pharmacyOrderSearchList_v1(subTenantId, userRoleId, tvFromTime.getText().toString(), tvToTime.getText().toString(), new Callback<PharmacyDatum>() {
            @Override
            public void success(PharmacyDatum pharmacyDatum, Response response) {
                if (pharmacyDatum.getStatus() == 1) {
                    /*for(int i=0;i<pharmacyDatum.getData().size();i++) {
                        pharmacyModelArray[i] = pharmacyDatum.getData().get(i);
                    }*/
                    pharmacyExpendableAdapter = new PharmacyExpendableAdapter(PharmacyActivity.this, pharmacyDatum.getData()) {
                        @Override
                        public void OnIndicatorClick(boolean isExpanded, int position) {
                            pos = position;
                            if (orderKey == 1) {
                                if (isExpanded) {
                                    patient_listview.collapseGroup(position);
                                } else {
                                    patient_listview.expandGroup(position);
                                }
                            } else if (orderKey == 0) {
                                patient_listview.collapseGroup(position);
                            }
                        }

                        @Override
                        public void OnTextClick() {
                            //Do whatever you want to do on text click
                        }
                    };
                    patient_listview.setAdapter(pharmacyExpendableAdapter);
                /*pharmacyAdapter = new PharmacyAdapter(PharmacyActivity.this, pharmacyModels);
                patient_listview.setAdapter(pharmacyAdapter);*/
                    //swipeContainer.setRefreshing(false);
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
                Intent intentLogout = new Intent(PharmacyActivity.this, LoginActivity.class);
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
                PharmacyActivity.super.onBackPressed();
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
                getPharmacyDataFromApi_v1();

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
                getPharmacyDataFromApi_v1();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(PharmacyActivity.this);
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

    private void callPaymentAPiforPharmacyKims(String his, PharmacyModel pharmacyModels, PharmacyChildDatum pharmacyChildDatum) {
        String hId;
        if (pharmacyModels.getHospitalNo().equals("")) {
            hId = "0";
        } else {
            hId = pharmacyModels.getHospitalNo();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", pharmacyModels.getOrderBySubTenantId());
        obj.addProperty("HospitalNo", hId);
        obj.addProperty("AppnatureId", 0);
        obj.addProperty("UserRoleId", pharmacyModels.getUserRoleId());
        obj.addProperty("Description", "Pharmacy");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", payableAmount);
        obj.addProperty("CollectedBy", userRoleId);
        obj.addProperty("ServiceType", 5);
        obj.addProperty("Mrno", pharmacyModels.getMrno());
        obj.addProperty("HIS_BillNo", his);
        obj.addProperty("MobileNo", pharmacyModels.getMobile().toString().trim());
        //obj.addProperty("LabOrderID", 0);
        //obj.addProperty("PharmacyOrderID", pharmacyModels.getPresOrderId());
        obj.addProperty("orderId", pharmacyModels.getPresOrderId());
        obj.addProperty("ScheduleId", 0);
        JsonArray objectKeyArray = new JsonArray();
        objectKeyArray.add(new JsonPrimitive(pharmacyChildDatum.getOrderTransactionId() + ""));
        obj.add("OrdTxnIds", objectKeyArray);

        Log.d("postpaymentv3", obj.toString());

        if (paymentMode.equals("1")) {
            postPaymentForPharmacyCash(obj, pharmacyModels);
        }
        if (paymentMode.equals("2")) {
            postPaymentForPharmacyCard(obj, pharmacyModels);
        }
        if (paymentMode.equals("3")) {
            postPaymentForPharmacyCash(obj, pharmacyModels);
        }
    }

    private void postPaymentForPharmacyCard(JsonObject obj, final PharmacyModel pharmacyModels) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentPharmacyFee(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {

                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    Toast.makeText(PharmacyActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, pharmacyModels);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(PharmacyActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, pharmacyModels);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mOrderTransactionIdNull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }


                /*if (postPaymentModel.getMsg().equals("Balance is less than Bill Amount. Please Add Amount")) {
                    showErrorDialog(postPaymentModel.getMsg());
                } else if (postPaymentModel.getMsg().equalsIgnoreCase("Payment Successfull") || postPaymentModel.getMsg().equalsIgnoreCase("Payment is already done.")) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadPharmacyDetail();
                    //printBill(data, pharmacyModels);

                    showSuccessDialog(postPaymentModel.getMsg(),data,pharmacyModels);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(PharmacyActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.show();
    }

    private void postPaymentForPharmacyCash(JsonObject obj, final PharmacyModel pharmacyModels) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentPharmacyFee(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {

                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    Toast.makeText(PharmacyActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, pharmacyModels);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(PharmacyActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, pharmacyModels);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mOrderTransactionIdNull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }

                /*Toast.makeText(PharmacyActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                if (postPaymentModel.getMsg().equalsIgnoreCase("Payment Successfull") || postPaymentModel.getMsg().equalsIgnoreCase("Payment is already done.")) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //getPharmacyPrescOrdersUpdateBillingDone(pharmacyModels);
                    //reloadPharmacyDetail();
                    //printBill(data, pharmacyModels);
                    showSuccessDialog(postPaymentModel.getMsg(),data,pharmacyModels);
                }*/
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {

                dismissLoadingDialog();
            }
        });
    }

    private void showSuccessDialog(String msg, final String data[], final PharmacyModel pharmacyModels) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PharmacyActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reloadPharmacyDetail();
                printBill(data, pharmacyModels);
                dialog.dismiss();
            }
        });

        android.app.AlertDialog dialog = builder.show();
    }

    public void printBill(String data[], PharmacyModel pharmacyModels) {
        Calendar now = Calendar.getInstance();
        completeDate = now.get(Calendar.DATE) + "/" + now.get(Calendar.MONTH) + 1 + "/" + now.get(Calendar.YEAR);
        time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        String printAmount[] = payableAmount.split("\\.");
        if (pharmacyModels.getSubTenantId() == 500) {
            subTanantName = "BLK Hospital";
            subTanantAddress = "Pusa Road, Rajinder Nagar, New Delhi, Delhi 110005";
            subTanantContact = "Tel:+91-11-30403040";
        } else if (pharmacyModels.getSubTenantId() == 243) {
            subTanantName = "Sir Ganga Ram Hospital";
            subTanantAddress = "Rajinder Nagar, New Delhi-60";
            subTanantContact = "Tel:+91-11-25750000, 42254000, Fax:+91-1142251755";
        } else if (pharmacyModels.getSubTenantId() == 525) {
            subTanantName = "U K Nursing Home";
            subTanantAddress = "M-1, Main Road, Vikas Puri, Delhi, 110018";
            subTanantContact = "Tel:+91-11-40955555";
        } else if (pharmacyModels.getSubTenantId() == 528 || pharmacyModels.getSubTenantId() == 515 || pharmacyModels.getSubTenantId() == 547) {
            subTanantName = "KIMS Hospitals";
            subTanantAddress = "1-112 / 86, Survey No 55/ EE, Kondapur Village, Serilingampally Mandal, Hyderabad, Telangana 500084";
            subTanantContact = "Tel:+91-88973 14141";
        } else if (pharmacyModels.getSubTenantId() == 529) {
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
                "        <div class=\"consultantValue\"><input type=\"text\" class=\"valueInput\" value=\"" + pharmacyModels.getDoctorName() + "\"></div></div>\n" +
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
                "   \t    <div class=\"patientnameValue\"><input type=\"text\" class=\"valueInput\" value=\"" + pharmacyModels.getPatname() + "\"></div></div> \n" +
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
                "    <div class=\"amountwordsValue\"><input type=\"text\" class=\"valueInput\" value = \"" + Helper.convert(Integer.parseInt(printAmount[0])) + " and " + Helper.convert(Integer.parseInt(printAmount[1])) + " Paise Only\"></div>\n" +
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
