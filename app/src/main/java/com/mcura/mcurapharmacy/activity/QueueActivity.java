package com.mcura.mcurapharmacy.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.JsonObject;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.Constants;
import com.mcura.mcurapharmacy.adpater.AvailableTokenAdapter;
import com.mcura.mcurapharmacy.adpater.CounterAdapter;
import com.mcura.mcurapharmacy.adpater.Helper;
import com.mcura.mcurapharmacy.adpater.Queue_Adapter;
import com.mcura.mcurapharmacy.adpater.ScheduleSpinnerAdapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mcura.mcurapharmacy.model.AvailableTokenList;
import com.mcura.mcurapharmacy.model.CounterDataModel;
import com.mcura.mcurapharmacy.model.GenerateTokenResultModel;
import com.mcura.mcurapharmacy.model.QueueStatus;
import com.mcura.mcurapharmacy.model.ScheduleModel;
import com.mcura.mcurapharmacy.retrofit.PostActivityTrackerModel;
import com.mcura.mcurapharmacy.retrofit.SubtenantLoginId;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class QueueActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener{
    private TextView selectCounter,selectfacid,start_opd_btn,tv_end_opd,tv_opd_msg,btnNextVisit, bQueueHold,btnQueueHold;


    SwipeMenuListView queueList;

    String completeDate, from_time, to_time;
    private ProgressDialog progressDialog;
    public MCuraApplication mCuraApplication;
    private SharedPreferences mSharedPreference;
    CounterDataModel[] counterDataModel;
    SubtenantLoginId[] subtenantLoginIds;
    AlertDialog.Builder builder;
    CounterAdapter counterAdapter;
    SubFacIdAdapter subFacIdAdapter;
    private Spinner counter_schedule;
    int serviceRoleIId;
    String clickFrom;

    ListView lv;
    int subTenantId;
    int year, month, date;
    AlertDialog dialog;
    ScheduleModel[] mScheduleArray;
    ScheduleSpinnerAdapter scheduleSpinnerAdapter;
    int userRoleId,facsubTenantId;




    public static final String HOLD_MSG = "QUEUE ON HOLD";
    String isDelete_Unblock = null;
    int appId;
    SwipeMenuItem item1;
    QueueStatus qStatus;
    private ToggleButton queueStatusToggle;
    int mrno;
    AlertDialog.Builder alertDialog;
    AlertDialog ad;
    public int user_role_id;
    public int subTanentId;

    //ListView queueList;
    Queue_Adapter adapter;
    private ArrayList<Object> itemList;
    //private itemBean bean;
    ImageButton chkin_btn, visit_entry_btn;
    QueueStatus[] queueStatusArray;
    List<QueueStatus> list = new ArrayList<>();
    androidx.appcompat.app.AlertDialog adSuccess = null;
    androidx.appcompat.app.AlertDialog adError = null;

    ImageView queue_current_status, queue_queue_status, queue_checkIn, queue_visting_entry, logout;
    TextView appointment, queue_mgmt, totalVisiting, totalCheckIn;
    AvailableTokenList[] availableTokenListsArray;
    int chartGenerateStatus, userRoleIdnew;

    SharedPreferences.Editor editor;
    int scheduleId, token_number, tokenNo;
    AvailableTokenAdapter availableTokenAdapter;
    ImageButton  add;
    String docName;


    private int startToken;
     private String scheduleName;
    private String[] scheduleNames;
    private int videoSubscription = 0;
    private ImageView ivSubtanentLogo;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_activity);
        mSharedPreference = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        subTenantId = mSharedPreference.getInt(Constants.SUB_TANENT_ID_KEY, 0);
        selectCounter= (TextView) findViewById(R.id.selectCounter);
        selectfacid= (TextView) findViewById(R.id.selectfacid);
        counter_schedule = (Spinner) findViewById(R.id.counter_schedule);

        //15/09/2023

        selectCounter.setOnClickListener(this);
        selectfacid.setOnClickListener(this);
        counter_schedule.setOnItemSelectedListener(this);
        start_opd_btn = (TextView) findViewById(R.id.start_opd_btn);
        start_opd_btn.setOnClickListener(this);
         Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        completeDate = year + "-" + month + "-" + date; //"2016-05-09"
        Log.d("completeDate", completeDate);
        Intent intent = getIntent();
        clickFrom = intent.getStringExtra("ClickTo");

        assert clickFrom != null;
        if (clickFrom.equals("PharmacyActivity")){
            serviceRoleIId = 2;



        }else if (clickFrom.equals("LabOrderActivity")){
            serviceRoleIId = 3;

        }


        ///doc data

         scheduleName = mSharedPreference.getString("scheduleName", "default OPD");
        scheduleNames = scheduleName.split("-");
        scheduleId = mSharedPreference.getInt("schedule_id", 0);
        editor = mSharedPreference.edit();
        docName = mSharedPreference.getString(Constants.UNAME_KEY, "Undefine");
        user_role_id = mSharedPreference.getInt(Constants.USER_ROLE_ID_KEY, 0);
        videoSubscription = getIntent().getIntExtra("videoSubscription",0);
       // completeDate = mSharedPreference.getString("Queue_Date", "");
        Log.d("completeDate", completeDate);


//        String subtanentImagePath = mSharedPreference.getString(Constants.SUB_TANENT_IMAGE_PATH, "");
//        if(!TextUtils.isEmpty(subtanentImagePath)){
//            Picasso.with(QueueActivity.this).load(subtanentImagePath).into(ivSubtanentLogo);
//        }else{
//            Picasso.with(QueueActivity.this).load(R.drawable.logo).into(ivSubtanentLogo);
//        }
//        if(scheduleNames[0].toLowerCase().equals(Constants.VIDEO_CONSULT_KEY.toLowerCase()) || scheduleNames[0].toLowerCase().equals(Constants.DOC2DOC_KEY.toLowerCase())){
//            updateTokenDetails();
//        }else{
//
//        }
        queueList =  findViewById(R.id.token_status_listview);
        queueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("onItemClick","onItemClick");

//                final QueueStatus modelQueueStatus = adapter.getItem(position);
//                int appVideoCallNatureId = modelQueueStatus.getAppNatureId();
//                if(scheduleNames[0].toLowerCase().equals(Constants.VIDEO_CONSULT_KEY.toLowerCase()) || scheduleNames[0].toLowerCase().equals(Constants.DOC2DOC_KEY.toLowerCase())){
//                    if (modelQueueStatus.getMRNo()!=null) {
//                        startActivity(new Intent(QueueActivity.this, ConsultationActivity.class).putExtra("mrnoValue", modelQueueStatus.getMRNo()).putExtra("subTanentId", subTanentId).putExtra("appVideoCallNatureId",appVideoCallNatureId));
//                    } else {
//                        Toast.makeText(QueueActivity.this, "Detail Not Available", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    if (modelQueueStatus.getCount() == 1) {
//                        startActivity(new Intent(QueueActivity.this, ConsultationActivity.class).putExtra("mrnoValue", modelQueueStatus.getMRNo()).putExtra("subTanentId", subTanentId).putExtra("appVideoCallNatureId",appVideoCallNatureId));
//                    } else {
//                        Toast.makeText(QueueActivity.this, "Detail Not Available", Toast.LENGTH_SHORT).show();
//                    }
//                }

            }
        });
        btnNextVisit = findViewById(R.id.btnNextVisit);

        btnQueueHold = findViewById(R.id.btnQueueHold);
        if(scheduleNames[0].toLowerCase().equals(Constants.VIDEO_CONSULT_KEY.toLowerCase()) || scheduleNames[0].toLowerCase().equals(Constants.DOC2DOC_KEY.toLowerCase())){
            btnNextVisit.setVisibility(View.VISIBLE);
            btnQueueHold.setVisibility(View.VISIBLE);
        }else{
            btnNextVisit.setVisibility(View.VISIBLE);
            btnQueueHold.setVisibility(View.VISIBLE);
        }
        chkin_btn = (ImageButton) findViewById(R.id.chkin_btn);

         appointment = (TextView) findViewById(R.id.appointment);
        queue_mgmt = (TextView) findViewById(R.id.queue_mgmt);
         totalCheckIn = (TextView) findViewById(R.id.total_checkin);




        appointment.setOnClickListener(this);
        queue_mgmt.setOnClickListener(this);
        //add.setOnClickListener(this);
        start_opd_btn.setOnClickListener(this);
        visit_entry_btn = (ImageButton) findViewById(R.id.visit_entry_btn);
        queueStatusToggle = (ToggleButton) findViewById(R.id.queue_status_toggle);
        queueStatusToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //MainActivity.queueStatus = queueStatusToggle.isChecked();
            }
        });
        btnQueueHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDrMessage();
            }
        });
        btnNextVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("list_size", list.size() + "");
                boolean flag = false;
                int patMRNo = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTokenStatus() != null) {
                        if (list.get(i).getTokenStatus().equals("CHECK_IN") && patMRNo == 0) {
                            patMRNo = list.get(i).getMRNo();
                            if (flag) {
                                break;
                            }
                        }
                        if (list.get(i).getTokenStatus().equals("CURRENTLY_VISITING")) {
                            patMRNo = 0;
                            flag = true;
                        }
                    }
                }
                if (patMRNo != 0) {
                    getVisitingStatus(patMRNo);
                } else {
                    Toast.makeText(QueueActivity.this, "No checked In patient available to visit", Toast.LENGTH_LONG).show();
                }
            }
        });


        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.RED));
                //set width of an option (px)
                item1.setWidth(100);
                item1.setIcon(R.drawable.childview_bg);
                item1.setTitleSize(18);
                item1.setTitleColor(Color.WHITE);
                menu.addMenuItem(item1);

            }


        };
        // set creator
        queueList.setMenuCreator(creator);
        queueList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if(scheduleNames[0].toLowerCase().equals(Constants.VIDEO_CONSULT_KEY.toLowerCase()) || scheduleNames[0].toLowerCase().equals(Constants.DOC2DOC_KEY.toLowerCase())){
                    Toast.makeText(QueueActivity.this,"This feature not available in Video Consultation",Toast.LENGTH_LONG).show();
                }else{
                    Log.d("listsize", list.size() + "");
                    if (list.size() > 0) {
                        qStatus = list.get(position);
                        String tokenStatus = null;
                        if (qStatus.getTokenStatus() != null) {
                            tokenStatus = qStatus.getTokenStatus().toString();
                        }
                        //Toast.makeText(QueueStatusActivity.this,qStatus.getTokenStatus().toString(),Toast.LENGTH_LONG).show();
                        try {
                            mrno = qStatus.getMRNo();
                            appId = qStatus.getAppId();
                        } catch (NullPointerException ex) {
                            mrno = 0;
                        }

                        switch (index) {
                            case 0:
                                if (mrno > 0) {
                                    if (tokenStatus.equals("CHECK_IN")) {
                                        if (mrno != 0) {
                                            getVisitingStatus(mrno);
                                        } else {
                                            Toast.makeText(QueueActivity.this, "No checked In patient available to visit", Toast.LENGTH_LONG).show();
                                        }
                                        //Toast.makeText(QueueStatusActivity.this, "Right Selection", Toast.LENGTH_LONG).show();
                                    }
                                    if (tokenStatus.equals("PRE_BOOKED")) {
                                        showDialog();
                                    } else {
                                        Toast.makeText(QueueActivity.this, "This action only for Checked In pateint", Toast.LENGTH_LONG).show();
                                    }
                                } else if (tokenStatus == "BLOCKED") {
                                    Toast.makeText(QueueActivity.this, "This action only for Checked In pateint", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(QueueActivity.this, "This action only for Checked In pateint", Toast.LENGTH_LONG).show();
                                }
                                break;
                        }
                    } else {
                        Toast.makeText(QueueActivity.this, "Some Issue Occur", Toast.LENGTH_LONG).show();
                    }
                }

                return false;
            }
        });






    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.selectfacid:
                subtenetiddd(subTenantId);
                break;

            case R.id.selectCounter:
                getDoctorDetail();
                break;

            case R.id.start_opd_btn:
                //start OPD without start Token number
                //postStartOPD();

                //start OPD with start Token number
                startToken = 0;
//                if (etStartTokenWith.getText().toString().equals("0") || TextUtils.isEmpty(etStartTokenWith.getText().toString())) {
//                    startToken = 1;
//                    Log.d("if", startToken + "");
//                } else {
//                    startToken = Integer.parseInt(etStartTokenWith.getText().toString());
//                    Log.d("else", startToken + "");
//                }
                //postStartOPD(startToken);
                postStartOPD();
                break;


        }

    }

    private void subtenetiddd(Integer subTenantId) {



        mCuraApplication.getInstance().mCuraEndPoint.getSubTenantForFacility(subTenantId, new Callback<SubtenantLoginId[]>() {
            @Override
            public void success(SubtenantLoginId[] subtenantSearchByLoginId, Response response) {

                subtenantLoginIds = subtenantSearchByLoginId;
                if (subtenantLoginIds!= null) {
                    builder = new AlertDialog.Builder(QueueActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View convertView = (View) inflater.inflate(R.layout.search_counter_dialog, null);
                    builder.setView(convertView);


                    lv = (ListView) convertView.findViewById(R.id.counter_list);
                    lv.setTextFilterEnabled(true);
                    subFacIdAdapter = new SubFacIdAdapter(QueueActivity.this,
                            android.R.layout.simple_spinner_item,
                            subtenantLoginIds);
                    lv.setAdapter(subFacIdAdapter );

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           // SharedPreferences.Editor editor = mSharedPreference.edit();

                            facsubTenantId = subFacIdAdapter.getItem(position).getSubTenantId();

                            selectfacid.setText(subFacIdAdapter.getItem(position).getSubTenantName());
                            dialog.dismiss();

                        }
                    });

                    dialog = builder.show();
                }
                dismissLoadingDialog();



            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });



    }


    public void getDoctorDetail() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getCounterList(facsubTenantId, serviceRoleIId, new Callback<CounterDataModel[]>() {
            @Override
            public void success(CounterDataModel[] counterData, Response response) {

                counterDataModel = counterData;
                if (counterDataModel!= null) {
                    builder = new AlertDialog.Builder(QueueActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View convertView = (View) inflater.inflate(R.layout.search_counter_dialog, null);
                    builder.setView(convertView);


                    lv = (ListView) convertView.findViewById(R.id.counter_list);
                    lv.setTextFilterEnabled(true);
                    counterAdapter = new CounterAdapter(QueueActivity.this,
                            android.R.layout.simple_spinner_item,
                            counterDataModel);
                    lv.setAdapter(counterAdapter );

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SharedPreferences.Editor editor = mSharedPreference.edit();
                            userRoleId = counterAdapter.getItem(position).getUserRoleId();
                            Log.d("ServiceRoleId",counterAdapter.getItem(position).getServiceRoleId()+"");
//                            editor.putString(Constant.USER_NAME, doctorSpinnerAdapter.getItem(position).getUserName());
                              userRoleIdnew = counterAdapter.getItem(position).getUserRoleId();
////                            editor.putInt(Constant.SERVICE_ROLE_ID, doctorSpinnerAdapter.getItem(position).getServiceRoleId());
////                            editor.apply();
                            selectCounter.setText(counterAdapter.getItem(position).getUserName());
                            dialog.dismiss();
                            getScheduleData(userRoleIdnew);
                        }
                    });

                    dialog = builder.show();
                }
                dismissLoadingDialog();

            }



            @Override
            public void failure(RetrofitError error) {

            }
        });




    }

    private void getScheduleData(int userRoleIdnew) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getSchedule_Day(userRoleId, completeDate, new Callback<ScheduleModel[]>() {
            @Override
            public void success(ScheduleModel[] scheduleModels, Response response) {

                mScheduleArray = scheduleModels;
                /*for (int i = 0; i < mScheduleArray.length; i++) {
                    Log.d("time", mScheduleArray[i].getFromTime() + "..." + mScheduleArray[i].getToTime());
                }*/


                scheduleSpinnerAdapter = new ScheduleSpinnerAdapter(QueueActivity.this,
                        android.R.layout.simple_spinner_item,
                        mScheduleArray);
                counter_schedule.setAdapter(scheduleSpinnerAdapter);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                //Toast.makeText(LoginActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }







    //DOooooooop


    public void showDialog() {
        alertDialog = new AlertDialog.Builder(QueueActivity.this);
        alertDialog.setMessage("Do you want to continue without checkIn");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setCheckInStatusWithoutFee();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
            }
        });
        ad = alertDialog.show();
    }

    public void setCheckInStatusWithoutFee() {
        //Toast.makeText(CheckInActivity.this,"subtenentid = "+subTanentId,Toast.LENGTH_LONG).show();
        JsonObject obj = new JsonObject();
        obj.addProperty("MRNo", mrno);
        obj.addProperty("UserRoleId", user_role_id);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("SubTenantId", subTanentId);
        obj.addProperty("Date", completeDate);
        obj.addProperty("appId",appId);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.patient_Check_In(obj, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                String msg = null;
                int chartGenerateStatus;
                msg = generateTokenResultModel.getMsg();
                chartGenerateStatus = generateTokenResultModel.getStatus();
                if (chartGenerateStatus == 1) {
                    //Toast.makeText(VisitActivity.this, msg, Toast.LENGTH_LONG).show();
                    getVisitingStatus(mrno);
                    //startActivity(new Intent(VisitActivity.this, Queue_status.class));
                } else if (chartGenerateStatus == 2) {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                    getVisitingStatus(mrno);
                } else if (chartGenerateStatus == 3) {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                } else if (chartGenerateStatus == 5) {
                    showErrorDialog(msg + " Do you want to pay now.");
                } else {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                //Toast.makeText(CheckInActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            /*Log.d("RetrofitError", error.toString());
            startActivity(new Intent(CheckInActivity.this, Queue_status.class));*/
                dismissLoadingDialog();
            }
        });
    }
    public void getVisitingStatus(int mrno) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.patient_Visit_Entry(mrno, user_role_id, subTanentId, scheduleId, completeDate, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel s, Response response) {
                int chartGenerateStatus;
                String msg = s.getMsg();
                chartGenerateStatus = s.getStatus();
                if (chartGenerateStatus == 1) {
                    showSuccessDialog(msg);
                } else {
                    showErrorDialog(msg);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showErrorDialog(String msg) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(QueueActivity.this);
        builder.setTitle("Failure");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adError.dismiss();
            }
        });
        adError = builder.show();
    }

    private void showSuccessDialog(String msg) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(QueueActivity.this);
        builder.setTitle("Success");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getQueueData();
                adSuccess.dismiss();
            }
        });
        adSuccess = builder.show();
    }

    public void showConfirmPopup() {
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Do you want to cancel token number " + qStatus.getTokenNo() + " for " + qStatus.getPatName());
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelToken(mrno);
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
            }
        });
        ad = alertDialog.show();
    }




    public void cancelToken(int mrno) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.cancel_Token_List(mrno, user_role_id, subTanentId, scheduleId, completeDate, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                int status = generateTokenResultModel.getStatus();
                String msg = generateTokenResultModel.getMsg();
                if (status == 1) {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                    getQueueData();
                } else if (status == 4) {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void updateTokenDetails(){
        JsonObject obj = new JsonObject();
        obj.addProperty("scheduleId",scheduleId);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.updateTokenDetails(obj, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                getQueueData();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }



    private void postDrMessage() {
        JsonObject obj = new JsonObject();
        obj.addProperty("UserRoleId", userRoleIdnew);
        obj.addProperty("SubTenantId", facsubTenantId);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("message", HOLD_MSG);
        obj.addProperty("status", 1);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.addDrMessage(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                if (s.equals("1")) {
                    Toast.makeText(QueueActivity.this, "Queue Kept On Hold", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(QueueActivity.this, "Something failed", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                ad.dismiss();
                Toast.makeText(QueueActivity.this, "Something failed", Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }
        });
    }


    public void postStartOPD() {
        JsonObject obj = new JsonObject();
        obj.addProperty("UserRoleId", userRoleIdnew);
        obj.addProperty("SubTenantId", facsubTenantId);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("Date", completeDate);

        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.generate_Token_Chart(obj, new Callback<GenerateTokenResultModel>() {

            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                String msg = null;
                msg = generateTokenResultModel.getMsg();
                chartGenerateStatus = generateTokenResultModel.getStatus();
                if (chartGenerateStatus == 1) {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                    postActivityTrackerFromAPI("start_opd");
                    //getQueueData();
                } else if (chartGenerateStatus == 2) {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                    getQueueData();
                } else if (chartGenerateStatus == 3) {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                } else if (chartGenerateStatus == 4) {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                } else if (chartGenerateStatus == 5) {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                } else if (chartGenerateStatus == 6) {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                    getQueueData();
                } else {
                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                }
                //dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
                Toast.makeText(QueueActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postActivityTrackerFromAPI(String source) {
        int slotAppId = 0;
        int actTransMasterId = 0;
        int patMrNo = 0;
        if (source.equals("start_opd")) {
            slotAppId = 0;
            actTransMasterId = EnumType.ActTransactMasterEnum.Start_OPD.getActTransactMasterTypeId();
            patMrNo = 0;
        } else if (source.equals("end_opd")) {
            slotAppId = 0;
            actTransMasterId = EnumType.ActTransactMasterEnum.End_OPD.getActTransactMasterTypeId();
            patMrNo = 0;
        } else if (source.equals("no_show")) {
            slotAppId = qStatus.getAppId();
            actTransMasterId = EnumType.ActTransactMasterEnum.No_Show.getActTransactMasterTypeId();
            patMrNo = qStatus.getMRNo();
        } else if (source.equals("msg_broadcast")) {
            slotAppId = 0;
            actTransMasterId = EnumType.ActTransactMasterEnum.Msg_Broadcast.getActTransactMasterTypeId();
            patMrNo = 0;
        }
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("actBuildVersion", Helper.getBuildVersion(QueueActivity.this));
        obj.addProperty("delivered", 0);
        obj.addProperty("actUserRoleId", user_role_id);
        obj.addProperty("actSubTenantId", subTanentId);
        obj.addProperty("actScheduleId", scheduleId);
        obj.addProperty("actAppId", slotAppId);
        obj.addProperty("actUserMediumId", 4);
        obj.addProperty("drUserRoleId", user_role_id);
        obj.addProperty("actRemarks", "");
        obj.addProperty("actTransMasterId", actTransMasterId);
        obj.addProperty("patMrno", patMrNo);
        obj.addProperty("actOthers", "");

        mCuraApplication.getInstance().mCuraEndPoint.postActivityTracker(obj, new Callback<PostActivityTrackerModel>() {
            @Override
            public void success(PostActivityTrackerModel postActivityTrackerModel, Response response) {
                getQueueData();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                getQueueData();
                dismissLoadingDialog();
            }
        });
    }


//    public void getQueueData() {
//        queueStatusArray = new QueueStatus[]{};
//        list.clear();
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.tokenViewOther(userRoleIdnew, subTanentId, scheduleId, completeDate, new Callback<QueueStatus[]>() {
//            @Override
//            public void success(QueueStatus[] queueStatus, Response response) {
//                if (queueStatus != null) {
//                    queueStatusArray = queueStatus;
//                    if (queueStatusArray.length > 0) {
//                        for (int i = 0; i < queueStatusArray.length; i++) {
//                            list.add(queueStatusArray[i]);
//                        }
//
//                        Collections.sort(list, new Comparator<QueueStatus>() {
//                            @Override
//                            public int compare(QueueStatus lhs, QueueStatus rhs) {
//                                return Integer.valueOf(lhs.getTokenNo()).compareTo(rhs.getTokenNo());
//                            }
//                        });
//                        int countCheckin = 0;
//                        int countVisiting = 0;
//                        for (int j = 0; j < queueStatus.length; j++) {
//                            if (queueStatus[j].getTokenStatus() != null) {
//                                String tokenStatus = queueStatus[j].getTokenStatus().toString();
//                                if (tokenStatus.equals("CHECK_IN")) {
//                                    countCheckin++;
//                                }
//                            }
//                        }
//                        totalCheckIn.setText(countCheckin + "");
//                        adapter = new Queue_Adapter(QueueActivity.this, list, subTanentId,videoSubscription);
//                        queueList.setAdapter(adapter);
//                        dismissLoadingDialog();
//                    } else if (queueStatusArray == null) {
//                        Toast.makeText(QueueActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
//                        dismissLoadingDialog();
//                    } else {
//                        Toast.makeText(QueueActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
//                        dismissLoadingDialog();
//                    }
//                } else {
//                    Toast.makeText(QueueActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
//                    dismissLoadingDialog();
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Toast.makeText(QueueActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
//                dismissLoadingDialog();
//            }
//        });
//    }
public void getQueueData() {
    queueStatusArray = new QueueStatus[]{};
    list.clear();
    showLoadingDialog();
    mCuraApplication.getInstance().mCuraEndPoint.tokenViewOther(userRoleIdnew, facsubTenantId, scheduleId, completeDate, new Callback<QueueStatus[]>() {
        @Override
        public void success(QueueStatus[] queueStatus, Response response) {
            if (queueStatus != null) {
                queueStatusArray = queueStatus;
                if (queueStatusArray.length > 0) {
                   // tv_opd_msg.setVisibility(View.GONE);
                    queueList.setVisibility(View.VISIBLE);
                    for (int i = 0; i < queueStatusArray.length; i++) {
                        list.add(queueStatusArray[i]);
                    }
                    adapter = new Queue_Adapter(QueueActivity.this, list, scheduleId);
                    queueList.setAdapter(adapter);
                    Collections.sort(list, new Comparator<QueueStatus>() {
                        @Override
                        public int compare(QueueStatus lhs, QueueStatus rhs) {
                            return Integer.valueOf(lhs.getTokenNo()).compareTo(rhs.getTokenNo());
                        }
                    });
                    dismissLoadingDialog();
                } else if (queueStatusArray == null) {
                    Toast.makeText(QueueActivity.this, "NoShowData Not Available", Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                } else {
                    queueList.setVisibility(View.GONE);
                   // tv_opd_msg.setVisibility(View.VISIBLE);
                    //Toast.makeText(QueueStatusActivity.this, "NoShowData Not Available", Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                }
            } else {
                Toast.makeText(QueueActivity.this, "NoShowData Not Available", Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Toast.makeText(QueueActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
            dismissLoadingDialog();
        }
    });
}
    /**
     *
     */
    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(QueueActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(getString(R.string.loading_message));
        }
        progressDialog.show();
    }

    /**
     *
     */
    public void dismissLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        token_number = availableTokenAdapter.getItem(position).getTokenNo();
//        ad.dismiss();
//        moveTokenList();
//    }

//    public void moveTokenList() {
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.move_Token_List(mrno, user_role_id, subTanentId, scheduleId, token_number, completeDate, new Callback<GenerateTokenResultModel>() {
//            @Override
//            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
//                int status = generateTokenResultModel.getStatus();
//                String msg = generateTokenResultModel.getMsg();
//                if (status == 1) {
//                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
//                    getQueueData();
//                } else if (status == 4) {
//                    Toast.makeText(QueueActivity.this, msg, Toast.LENGTH_LONG).show();
//                }
//                dismissLoadingDialog();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                dismissLoadingDialog();
//            }
//        });
//    }

    @Override
    public void onBackPressed() {


        if(serviceRoleIId==2) {
            Intent i = new Intent(QueueActivity.this, PharmacyActivity.class);
            startActivity(i);
            finish();
        }
        if(serviceRoleIId==3) {
            Intent i = new Intent(QueueActivity.this, LabOrderActivity.class);
            startActivity(i);
            finish();
        }


    }

}
