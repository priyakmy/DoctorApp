//package com.mcura.mcurapharmacy.activity;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//import com.baoyz.swipemenulistview.SwipeMenu;
//import com.baoyz.swipemenulistview.SwipeMenuCreator;
//import com.baoyz.swipemenulistview.SwipeMenuItem;
//import com.baoyz.swipemenulistview.SwipeMenuListView;
//import com.google.gson.JsonObject;
//import com.mcura.MCuraApplication;
//import com.mcura.R;
//import com.mcura.Utils.Constant;
//import com.mcura.Utils.Constants;
//import com.mcura.Utils.EnumType;
//import com.mcura.adapters.Queue_Adapter;
//import com.mcura.helper.Helper;
//import com.mcura.modelclass.AvailableTokenAdapter;
//import com.mcura.modelclass.AvailableTokenList;
//import com.mcura.modelclass.GenerateTokenResultModel;
//import com.mcura.modelclass.PostActivityTrackerModel.PostActivityTrackerModel;
//import com.mcura.modelclass.QueueStatus;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//
//public class QueueStatusActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
//    public static final String HOLD_MSG = "QUEUE ON HOLD";
//    String isDelete_Unblock = null;
//    int appId;
//    SwipeMenuItem item1;
//    QueueStatus qStatus;
//    private ToggleButton queueStatusToggle;
//    int mrno;
//    AlertDialog.Builder alertDialog;
//    AlertDialog ad;
//    public int user_role_id;
//    public int subTanentId;
//    ProgressDialog progressDialog;
//    public MCuraApplication mCuraApplication;
//    //ListView queueList;
//    Queue_Adapter adapter;
//    private ArrayList<Object> itemList;
//    //private itemBean bean;
//    ImageButton chkin_btn, visit_entry_btn;
//    QueueStatus[] queueStatusArray;
//    SwipeMenuListView queueList;
//    List<QueueStatus> list = new ArrayList<>();
//    androidx.appcompat.app.AlertDialog adSuccess = null;
//    androidx.appcompat.app.AlertDialog adError = null;
//    int year, month, date;
//    String completeDate;
//    TextView start_opd_btn;
//    ImageButton load_nfc;
//    ImageView queue_current_status, queue_queue_status, queue_checkIn, queue_visting_entry, logout;
//    TextView appointment, queue_mgmt, doctorName, totalVisiting, totalCheckIn;
//    AvailableTokenList[] availableTokenListsArray;
//    int chartGenerateStatus;
//    private SharedPreferences mSharedPreference;
//    SharedPreferences.Editor editor;
//    int scheduleId, token_number, tokenNo;
//    AvailableTokenAdapter availableTokenAdapter;
//    ImageButton checkin, visiting, add;
//    String docName;
//    ImageView ivDoctorProfile;
//    ImageView mail_btn;
//    ImageView queueRefresh;
//    LinearLayout btnQueueHold, btnNextVisit;
//    private int startToken;
//    private EditText etStartTokenWith;
//    private String scheduleName;
//    private String[] scheduleNames;
//    private int videoSubscription = 0;
//    private ImageView ivSubtanentLogo;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_queue_status);
//        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        //getSupportActionBar().hide();
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
//        );
//        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
//                Context.MODE_PRIVATE);
//        String docProfilePic = mSharedPreference.getString(Constants.USER_PROFILE_PIC, "default");
//        scheduleName = mSharedPreference.getString("scheduleName", "default OPD");
//        scheduleNames = scheduleName.split("-");
//        scheduleId = mSharedPreference.getInt(Constants.SCHEDULE_ID, 0);
//        editor = mSharedPreference.edit();
//        docName = mSharedPreference.getString(Constants.UNAME_KEY, "Undefine");
//        user_role_id = mSharedPreference.getInt(Constants.USER_ROLE_ID_KEY, 0);
//        subTanentId = mSharedPreference.getInt(Constants.SUB_TANENT_ID_KEY, 0);
//        videoSubscription = getIntent().getIntExtra("videoSubscription",0);
//        completeDate = mSharedPreference.getString("Queue_Date", "");
//        Log.d("completeDate", completeDate);
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        etStartTokenWith = findViewById(R.id.etStartTokenWith);
//        if (mToolbar != null) {
//            setSupportActionBar(mToolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setTitle("");
//            getSupportActionBar().setSubtitle("");
//
//        }
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        ivSubtanentLogo = findViewById(R.id.ivSubtanentLogo);
//        Display display = getWindowManager().getDefaultDisplay();
//        int width = ((display.getWidth() * 60) / 100); // ((display.getWidth()*20)/100)
//        Toolbar.LayoutParams parms = new Toolbar.LayoutParams(width, Toolbar.LayoutParams.MATCH_PARENT);
//        parms.gravity = Gravity.CENTER;
//        ivSubtanentLogo.setLayoutParams(parms);
//        String subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "");
//        if(!TextUtils.isEmpty(subtanentImagePath)){
//            Picasso.with(QueueStatusActivity.this).load(subtanentImagePath).into(ivSubtanentLogo);
//        }else{
//            Picasso.with(QueueStatusActivity.this).load(R.drawable.logo).into(ivSubtanentLogo);
//        }
//        if(scheduleNames[0].toLowerCase().equals(Constants.VIDEO_CONSULT_KEY.toLowerCase()) || scheduleNames[0].toLowerCase().equals(Constants.DOC2DOC_KEY.toLowerCase())){
//            updateTokenDetails();
//        }else{
//            getQueueData();
//        }
//        queueList = (SwipeMenuListView) findViewById(R.id.token_status_listview);
//        queueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("onItemClick","onItemClick");
//
//                final QueueStatus modelQueueStatus = adapter.getItem(position);
//                int appVideoCallNatureId = modelQueueStatus.getAppNatureId();
//                if(scheduleNames[0].toLowerCase().equals(Constants.VIDEO_CONSULT_KEY.toLowerCase()) || scheduleNames[0].toLowerCase().equals(Constants.DOC2DOC_KEY.toLowerCase())){
//                    if (modelQueueStatus.getMRNo()!=null) {
//                        startActivity(new Intent(QueueStatusActivity.this, ConsultationActivity.class).putExtra("mrnoValue", modelQueueStatus.getMRNo()).putExtra("subTanentId", subTanentId).putExtra("appVideoCallNatureId",appVideoCallNatureId));
//                    } else {
//                        Toast.makeText(QueueStatusActivity.this, "Detail Not Available", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    if (modelQueueStatus.getCount() == 1) {
//                        startActivity(new Intent(QueueStatusActivity.this, ConsultationActivity.class).putExtra("mrnoValue", modelQueueStatus.getMRNo()).putExtra("subTanentId", subTanentId).putExtra("appVideoCallNatureId",appVideoCallNatureId));
//                    } else {
//                        Toast.makeText(QueueStatusActivity.this, "Detail Not Available", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//        });
//        btnNextVisit = findViewById(R.id.btnNextVisit);
//
//        btnQueueHold = findViewById(R.id.btnQueueHold);
//        if(scheduleNames[0].toLowerCase().equals(Constants.VIDEO_CONSULT_KEY.toLowerCase()) || scheduleNames[0].toLowerCase().equals(Constants.DOC2DOC_KEY.toLowerCase())){
//            btnNextVisit.setVisibility(View.VISIBLE);
//            btnQueueHold.setVisibility(View.VISIBLE);
//        }else{
//            btnNextVisit.setVisibility(View.VISIBLE);
//            btnQueueHold.setVisibility(View.VISIBLE);
//        }
//        chkin_btn = (ImageButton) findViewById(R.id.chkin_btn);
//        start_opd_btn = (TextView) findViewById(R.id.start_opd_btn);
//        checkin = (ImageButton) findViewById(R.id.chk_in);
//        appointment = (TextView) findViewById(R.id.appointment);
//        queue_mgmt = (TextView) findViewById(R.id.queue_mgmt);
//        visiting = (ImageButton) findViewById(R.id.visiting);
//        totalCheckIn = (TextView) findViewById(R.id.total_checkin);
//        queueRefresh = (ImageView) findViewById(R.id.queue_refresh);
//        queueRefresh.setOnClickListener(this);
//        //totalVisiting = (TextView) findViewById(R.id.total_visiting);
//        //add = (ImageButton) findViewById(R.id.add_icon);
//        ivDoctorProfile = (ImageView) findViewById(R.id.activity_calendar_iv_doctor);
//        doctorName = (TextView) findViewById(R.id.docname);
//        mail_btn = (ImageView) findViewById(R.id.mail_btn);
//        mail_btn.setOnClickListener(this);
//        //set hospital logo
//
//
//        if (docProfilePic != "default") {
//            Picasso.with(this).load(docProfilePic).into(ivDoctorProfile);
//        }
//        doctorName.setText(docName.toString());
//        load_nfc = (ImageButton) findViewById(R.id.load_nfc);
//        load_nfc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(QueueStatusActivity.this, SearchPatientActivity.class).putExtra("userroleid", user_role_id));
//            }
//        });
//        checkin.setOnClickListener(this);
//        visiting.setOnClickListener(this);
//        appointment.setOnClickListener(this);
//        queue_mgmt.setOnClickListener(this);
//        //add.setOnClickListener(this);
//        start_opd_btn.setOnClickListener(this);
//        visit_entry_btn = (ImageButton) findViewById(R.id.visit_entry_btn);
//        queueStatusToggle = (ToggleButton) findViewById(R.id.queue_status_toggle);
//        queueStatusToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                //MainActivity.queueStatus = queueStatusToggle.isChecked();
//            }
//        });
//        btnQueueHold.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                postDrMessage();
//            }
//        });
//        btnNextVisit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("list_size", list.size() + "");
//                boolean flag = false;
//                int patMRNo = 0;
//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(i).getTokenStatus() != null) {
//                        if (list.get(i).getTokenStatus().equals("CHECK_IN") && patMRNo == 0) {
//                            patMRNo = list.get(i).getMRNo();
//                            if (flag) {
//                                break;
//                            }
//                        }
//                        if (list.get(i).getTokenStatus().equals("CURRENTLY_VISITING")) {
//                            patMRNo = 0;
//                            flag = true;
//                        }
//                    }
//                }
//                if (patMRNo != 0) {
//                    getVisitingStatus(patMRNo);
//                } else {
//                    Toast.makeText(QueueStatusActivity.this, "No checked In patient available to visit", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                item1 = new SwipeMenuItem(
//                        getApplicationContext());
//                item1.setBackground(new ColorDrawable(Color.RED));
//                //set width of an option (px)
//                item1.setWidth(100);
//                item1.setIcon(R.drawable.ic_visiting);
//                item1.setTitleSize(18);
//                item1.setTitleColor(Color.WHITE);
//                menu.addMenuItem(item1);
//            }
//        };
//        // set creator
//        queueList.setMenuCreator(creator);
//        queueList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                if(scheduleNames[0].toLowerCase().equals(Constants.VIDEO_CONSULT_KEY.toLowerCase()) || scheduleNames[0].toLowerCase().equals(Constants.DOC2DOC_KEY.toLowerCase())){
//                    Toast.makeText(QueueStatusActivity.this,"This feature not available in Video Consultation",Toast.LENGTH_LONG).show();
//                }else{
//                    Log.d("listsize", list.size() + "");
//                    if (list.size() > 0) {
//                        qStatus = list.get(position);
//                        String tokenStatus = null;
//                        if (qStatus.getTokenStatus() != null) {
//                            tokenStatus = qStatus.getTokenStatus().toString();
//                        }
//                        //Toast.makeText(QueueStatusActivity.this,qStatus.getTokenStatus().toString(),Toast.LENGTH_LONG).show();
//                        try {
//                            mrno = qStatus.getMRNo();
//                            appId = qStatus.getAppId();
//                        } catch (NullPointerException ex) {
//                            mrno = 0;
//                        }
//
//                        switch (index) {
//                            case 0:
//                                if (mrno > 0) {
//                                    if (tokenStatus.equals("CHECK_IN")) {
//                                        if (mrno != 0) {
//                                            getVisitingStatus(mrno);
//                                        } else {
//                                            Toast.makeText(QueueStatusActivity.this, "No checked In patient available to visit", Toast.LENGTH_LONG).show();
//                                        }
//                                        //Toast.makeText(QueueStatusActivity.this, "Right Selection", Toast.LENGTH_LONG).show();
//                                    }
//                                    if (tokenStatus.equals("PRE_BOOKED")) {
//                                        showDialog();
//                                    } else {
//                                        Toast.makeText(QueueStatusActivity.this, "This action only for Checked In pateint", Toast.LENGTH_LONG).show();
//                                    }
//                                } else if (tokenStatus == "BLOCKED") {
//                                    Toast.makeText(QueueStatusActivity.this, "This action only for Checked In pateint", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Toast.makeText(QueueStatusActivity.this, "This action only for Checked In pateint", Toast.LENGTH_LONG).show();
//                                }
//                                break;
//                        }
//                    } else {
//                        Toast.makeText(QueueStatusActivity.this, "Some Issue Occur", Toast.LENGTH_LONG).show();
//                    }
//                }
//
//                return false;
//            }
//        });
//        queueList.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
//
//        /*queueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final QueueStatus modelQueueStatus = adapter.getItem(position);
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (modelQueueStatus.getCount() == 1) {
//                            startActivity(new Intent(QueueStatusActivity.this, ConsultationActivity.class).putExtra("mrnoValue", modelQueueStatus.getMRNo()).putExtra("subTanentId", subTanentId));
//                        } else {
//                            Toast.makeText(QueueStatusActivity.this, "Detail Not Available", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });*/
//    }
//
//    public void showDialog() {
//        alertDialog = new AlertDialog.Builder(QueueStatusActivity.this);
//        alertDialog.setMessage("Do you want to continue without checkIn");
//        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                setCheckInStatusWithoutFee();
//            }
//        });
//        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ad.dismiss();
//            }
//        });
//        ad = alertDialog.show();
//    }
//
//    public void setCheckInStatusWithoutFee() {
//        //Toast.makeText(CheckInActivity.this,"subtenentid = "+subTanentId,Toast.LENGTH_LONG).show();
//        JsonObject obj = new JsonObject();
//        obj.addProperty("MRNo", mrno);
//        obj.addProperty("UserRoleId", user_role_id);
//        obj.addProperty("ScheduleId", scheduleId);
//        obj.addProperty("SubTenantId", subTanentId);
//        obj.addProperty("Date", completeDate);
//        obj.addProperty("appId",appId);
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.patient_Check_In(obj, new Callback<GenerateTokenResultModel>() {
//            @Override
//            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
//                String msg = null;
//                int chartGenerateStatus;
//                msg = generateTokenResultModel.getMsg();
//                chartGenerateStatus = generateTokenResultModel.getStatus();
//                if (chartGenerateStatus == 1) {
//                    //Toast.makeText(VisitActivity.this, msg, Toast.LENGTH_LONG).show();
//                    getVisitingStatus(mrno);
//                    //startActivity(new Intent(VisitActivity.this, Queue_status.class));
//                } else if (chartGenerateStatus == 2) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                    getVisitingStatus(mrno);
//                } else if (chartGenerateStatus == 3) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                } else if (chartGenerateStatus == 5) {
//                    showErrorDialog(msg + " Do you want to pay now.");
//                } else {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                }
//                dismissLoadingDialog();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                //Toast.makeText(CheckInActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//            /*Log.d("RetrofitError", error.toString());
//            startActivity(new Intent(CheckInActivity.this, Queue_status.class));*/
//                dismissLoadingDialog();
//            }
//        });
//    }
//    public void getVisitingStatus(int mrno) {
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.patient_Visit_Entry(mrno, user_role_id, subTanentId, scheduleId, completeDate, new Callback<GenerateTokenResultModel>() {
//            @Override
//            public void success(GenerateTokenResultModel s, Response response) {
//                int chartGenerateStatus;
//                String msg = s.getMsg();
//                chartGenerateStatus = s.getStatus();
//                if (chartGenerateStatus == 1) {
//                    showSuccessDialog(msg);
//                } else {
//                    showErrorDialog(msg);
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
//
//    private void showErrorDialog(String msg) {
//        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(QueueStatusActivity.this);
//        builder.setTitle("Failure");
//        builder.setMessage(msg);
//        builder.setCancelable(false);
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                adError.dismiss();
//            }
//        });
//        adError = builder.show();
//    }
//
//    private void showSuccessDialog(String msg) {
//        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(QueueStatusActivity.this);
//        builder.setTitle("Success");
//        builder.setMessage(msg);
//        builder.setCancelable(false);
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                getQueueData();
//                adSuccess.dismiss();
//            }
//        });
//        adSuccess = builder.show();
//    }
//
//    public void showConfirmPopup() {
//        alertDialog = new AlertDialog.Builder(this);
//        alertDialog.setMessage("Do you want to cancel token number " + qStatus.getTokenNo() + " for " + qStatus.getPatName());
//        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                cancelToken(mrno);
//            }
//        });
//        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ad.dismiss();
//            }
//        });
//        ad = alertDialog.show();
//    }
//
//    public void getAllAvailableTokenPopup() {
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.available_Token_List(user_role_id, subTanentId, scheduleId, completeDate, new Callback<AvailableTokenList[]>() {
//            @Override
//            public void success(AvailableTokenList[] availableTokenLists, Response response) {
//                availableTokenListsArray = availableTokenLists;
//                if (availableTokenListsArray.length > 0) {
//                    showAllAvailableTokenPopup();
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
//
//    public void showAllAvailableTokenPopup() {
//        alertDialog = new AlertDialog.Builder(QueueStatusActivity.this);
//        LayoutInflater inflater = getLayoutInflater();
//        View convertView = (View) inflater.inflate(R.layout.search_patient_row_layout, null);
//        alertDialog.setView(convertView);
//        //alertDialog.setTitle("List");
//        ListView lv = (ListView) convertView.findViewById(R.id.search_patient_listview);
//        availableTokenAdapter = new AvailableTokenAdapter(QueueStatusActivity.this,
//                android.R.layout.simple_spinner_item,
//                availableTokenListsArray);
//        lv.setAdapter(availableTokenAdapter);
//        ad = alertDialog.show();
//        lv.setOnItemClickListener(this);
//
//    }
//
//    public void cancelToken(int mrno) {
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.cancel_Token_List(mrno, user_role_id, subTanentId, scheduleId, completeDate, new Callback<GenerateTokenResultModel>() {
//            @Override
//            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
//                int status = generateTokenResultModel.getStatus();
//                String msg = generateTokenResultModel.getMsg();
//                if (status == 1) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                    getQueueData();
//                } else if (status == 4) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
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
//
//    private void updateTokenDetails(){
//        JsonObject obj = new JsonObject();
//        obj.addProperty("scheduleId",scheduleId);
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.updateTokenDetails(obj, new Callback<GenerateTokenResultModel>() {
//            @Override
//            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
//                getQueueData();
//                dismissLoadingDialog();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                dismissLoadingDialog();
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id) {
//            case R.id.queue_refresh:
//                if(scheduleNames[0].toLowerCase().equals(Constants.VIDEO_CONSULT_KEY.toLowerCase()) || scheduleNames[0].toLowerCase().equals(Constants.DOC2DOC_KEY.toLowerCase())){
//                    updateTokenDetails();
//                }else{
//                    getQueueData();
//                }
//
//                break;
//            case R.id.start_opd_btn:
//                //start OPD without start Token number
//                //postStartOPD();
//
//                //start OPD with start Token number
//                startToken = 0;
//                if (etStartTokenWith.getText().toString().equals("0") || TextUtils.isEmpty(etStartTokenWith.getText().toString())) {
//                    startToken = 1;
//                    Log.d("if", startToken + "");
//                } else {
//                    startToken = Integer.parseInt(etStartTokenWith.getText().toString());
//                    Log.d("else", startToken + "");
//                }
//                //postStartOPD(startToken);
//                postStartOPD();
//                break;
//            case R.id.appointment:
//                startActivity(new Intent(QueueStatusActivity.this, CalendarActivity.class));
//                break;
//            case R.id.queue_mgmt:
//                startActivity(new Intent(QueueStatusActivity.this, CalendarActivity.class));
//                break;
//            case R.id.mail_btn:
//                //Toast.makeText(QueueStatusActivity.this,"Hello",Toast.LENGTH_LONG).show();
//                alertDialog = new AlertDialog.Builder(QueueStatusActivity.this);
//                LayoutInflater inflater = getLayoutInflater();
//                View convertView = inflater.inflate(R.layout.message_dialog_layout, null);
//                alertDialog.setView(convertView);
//                final EditText message = (EditText) convertView.findViewById(R.id.message);
//                TextView send_message = (TextView) convertView.findViewById(R.id.alert_submit);
//                TextView cancel_message = (TextView) convertView.findViewById(R.id.alert_cancel);
//                cancel_message.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ad.dismiss();
//                    }
//                });
//                send_message.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (message.getText().toString().trim().length() > 0) {
//                            postDrMessage(message.getText().toString());
//                        } else {
//                            message.setError("Put message to send");
//                        }
//                    }
//                });
//                ad = alertDialog.show();
//                break;
//        }
//    }
//
//    private void postDrMessage() {
//        JsonObject obj = new JsonObject();
//        obj.addProperty("UserRoleId", user_role_id);
//        obj.addProperty("SubTenantId", subTanentId);
//        obj.addProperty("ScheduleId", scheduleId);
//        obj.addProperty("message", HOLD_MSG);
//        obj.addProperty("status", 1);
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.addDrMessage(obj, new Callback<String>() {
//            @Override
//            public void success(String s, Response response) {
//                if (s.equals("1")) {
//                    Toast.makeText(QueueStatusActivity.this, "Queue Kept On Hold", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(QueueStatusActivity.this, "Something failed", Toast.LENGTH_LONG).show();
//                }
//                dismissLoadingDialog();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ad.dismiss();
//                Toast.makeText(QueueStatusActivity.this, "Something failed", Toast.LENGTH_LONG).show();
//                dismissLoadingDialog();
//            }
//        });
//    }
//
//    private void postDrMessage(String message) {
//        JsonObject obj = new JsonObject();
//        obj.addProperty("UserRoleId", user_role_id);
//        obj.addProperty("SubTenantId", subTanentId);
//        obj.addProperty("ScheduleId", scheduleId);
//        obj.addProperty("message", message);
//        obj.addProperty("status", 1);
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.addDrMessage(obj, new Callback<String>() {
//            @Override
//            public void success(String s, Response response) {
//                if (s.equals("1")) {
//                    postActivityTrackerFromAPI("msg_broadcast");
//                    Toast.makeText(QueueStatusActivity.this, "Message Successfully Sent", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(QueueStatusActivity.this, "Message Failed", Toast.LENGTH_LONG).show();
//                }
//                ad.dismiss();
//                dismissLoadingDialog();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ad.dismiss();
//                Toast.makeText(QueueStatusActivity.this, "Message Failed", Toast.LENGTH_LONG).show();
//                dismissLoadingDialog();
//            }
//        });
//    }
//
//    public void postStartOPD() {
//        JsonObject obj = new JsonObject();
//        obj.addProperty("UserRoleId", user_role_id);
//        obj.addProperty("SubTenantId", subTanentId);
//        obj.addProperty("ScheduleId", scheduleId);
//        obj.addProperty("Date", completeDate);
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.generate_Token_Chart(obj, new Callback<GenerateTokenResultModel>() {
//            @Override
//            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
//                String msg = null;
//                msg = generateTokenResultModel.getMsg();
//                chartGenerateStatus = generateTokenResultModel.getStatus();
//                if (chartGenerateStatus == 1) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                    postActivityTrackerFromAPI("start_opd");
//                    //getQueueData();
//                } else if (chartGenerateStatus == 2) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                    getQueueData();
//                } else if (chartGenerateStatus == 3) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                    dismissLoadingDialog();
//                } else {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                    dismissLoadingDialog();
//                }
//                //dismissLoadingDialog();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                dismissLoadingDialog();
//            }
//        });
//    }
//
//    public void postStartOPD(int sToken) {
//
//        JsonObject obj = new JsonObject();
//        obj.addProperty("UserRoleId", user_role_id);
//        obj.addProperty("SubTenantId", subTanentId);
//        obj.addProperty("ScheduleId", scheduleId);
//        obj.addProperty("Date", completeDate);
//        obj.addProperty("startToken", startToken);
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.generateChartWithTokenNo(obj, new Callback<GenerateTokenResultModel>() {
//            @Override
//            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
//                String msg = null;
//                msg = generateTokenResultModel.getMsg();
//                chartGenerateStatus = generateTokenResultModel.getStatus();
//                if (chartGenerateStatus == 1) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                    postActivityTrackerFromAPI("start_opd");
//                    //getQueueData();
//                } else if (chartGenerateStatus == 2) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                    getQueueData();
//                } else if (chartGenerateStatus == 3) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                    dismissLoadingDialog();
//                } else {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                    dismissLoadingDialog();
//                }
//                //dismissLoadingDialog();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                dismissLoadingDialog();
//            }
//        });
//    }
//
//
//    public void getQueueData() {
//        queueStatusArray = new QueueStatus[]{};
//        list.clear();
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.tokenViewOther(user_role_id, subTanentId, scheduleId, completeDate, new Callback<QueueStatus[]>() {
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
//                        adapter = new Queue_Adapter(QueueStatusActivity.this, list, subTanentId,videoSubscription);
//                        queueList.setAdapter(adapter);
//                        dismissLoadingDialog();
//                    } else if (queueStatusArray == null) {
//                        Toast.makeText(QueueStatusActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
//                        dismissLoadingDialog();
//                    } else {
//                        Toast.makeText(QueueStatusActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
//                        dismissLoadingDialog();
//                    }
//                } else {
//                    Toast.makeText(QueueStatusActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
//                    dismissLoadingDialog();
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Toast.makeText(QueueStatusActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
//                dismissLoadingDialog();
//            }
//        });
//    }
//
//    /**
//     *
//     */
//    public void showLoadingDialog() {
//
//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog(QueueStatusActivity.this);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage(getString(R.string.loading_message));
//        }
//        progressDialog.show();
//    }
//
//    /**
//     *
//     */
//    public void dismissLoadingDialog() {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
//    }
//
//    private void postActivityTrackerFromAPI(String source) {
//        int slotAppId = 0;
//        int actTransMasterId = 0;
//        int patMrNo = 0;
//        if (source.equals("start_opd")) {
//            slotAppId = 0;
//            actTransMasterId = EnumType.ActTransactMasterEnum.Start_OPD.getActTransactMasterTypeId();
//            patMrNo = 0;
//        } else if (source.equals("end_opd")) {
//            slotAppId = 0;
//            actTransMasterId = EnumType.ActTransactMasterEnum.End_OPD.getActTransactMasterTypeId();
//            patMrNo = 0;
//        } else if (source.equals("no_show")) {
//            slotAppId = qStatus.getAppId();
//            actTransMasterId = EnumType.ActTransactMasterEnum.No_Show.getActTransactMasterTypeId();
//            patMrNo = qStatus.getMRNo();
//        } else if (source.equals("msg_broadcast")) {
//            slotAppId = 0;
//            actTransMasterId = EnumType.ActTransactMasterEnum.Msg_Broadcast.getActTransactMasterTypeId();
//            patMrNo = 0;
//        }
//        showLoadingDialog();
//        JsonObject obj = new JsonObject();
//        obj.addProperty("actBuildVersion", Helper.getBuildVersion(QueueStatusActivity.this));
//        obj.addProperty("delivered", 0);
//        obj.addProperty("actUserRoleId", user_role_id);
//        obj.addProperty("actSubTenantId", subTanentId);
//        obj.addProperty("actScheduleId", scheduleId);
//        obj.addProperty("actAppId", slotAppId);
//        obj.addProperty("actUserMediumId", 4);
//        obj.addProperty("drUserRoleId", user_role_id);
//        obj.addProperty("actRemarks", "");
//        obj.addProperty("actTransMasterId", actTransMasterId);
//        obj.addProperty("patMrno", patMrNo);
//        obj.addProperty("actOthers", "");
//
//        mCuraApplication.getInstance().mCuraEndPoint.postActivityTracker(obj, new Callback<PostActivityTrackerModel>() {
//            @Override
//            public void success(PostActivityTrackerModel postActivityTrackerModel, Response response) {
//                getQueueData();
//                dismissLoadingDialog();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                getQueueData();
//                dismissLoadingDialog();
//            }
//        });
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        token_number = availableTokenAdapter.getItem(position).getTokenNo();
//        ad.dismiss();
//        moveTokenList();
//    }
//
//    public void moveTokenList() {
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.move_Token_List(mrno, user_role_id, subTanentId, scheduleId, token_number, completeDate, new Callback<GenerateTokenResultModel>() {
//            @Override
//            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
//                int status = generateTokenResultModel.getStatus();
//                String msg = generateTokenResultModel.getMsg();
//                if (status == 1) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
//                    getQueueData();
//                } else if (status == 4) {
//                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
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
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    public void setBlockApi() {
//        JsonObject blockPatient = new JsonObject();
//        blockPatient.addProperty("appointmentid", appId);
//        blockPatient.addProperty("UserRoleID", user_role_id);
//        blockPatient.addProperty("isDelete_Unblock", isDelete_Unblock);
//        showLoadingDialog();
//        mCuraApplication.getInstance().mCuraEndPoint.updateAppointment_Block(blockPatient, new Callback<String>() {
//            @Override
//            public void success(String s, Response response) {
//                Toast.makeText(getApplicationContext(), "Response-->" + s, Toast.LENGTH_LONG).show();
//                if (isDelete_Unblock.equals("true")) {
//                    //Intent in = new Intent(QueueStatusActivity.this, CheckInActivity.class).putExtra("TokenNo", tokenNo).putExtra("appId", appId);
//                    //startActivity(in);
//                } else if (isDelete_Unblock.equals("false")) {
//                    getQueueData();
//                }
//
//                dismissLoadingDialog();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                dismissLoadingDialog();
//            }
//        });
//    }
//}
//
//
//
//
