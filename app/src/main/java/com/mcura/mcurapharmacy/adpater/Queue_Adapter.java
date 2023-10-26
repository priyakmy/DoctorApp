package com.mcura.mcurapharmacy.adpater;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.Constants;
import com.mcura.mcurapharmacy.activity.EnumType;
import com.mcura.mcurapharmacy.model.QueueStatus;
import com.mcura.mcurapharmacy.retrofit.PostActivityTrackerModel;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Mcura on 5/6/2016.
 */
public class Queue_Adapter extends BaseAdapter {

    private final int scheduleId;
    private int frontOfficeUserRoleId;
    private String buildVersionName;
    private int subTanentId;
    private int user_role_id;
    private SharedPreferences mSharedPreference;
    ArrayList<Object> itemList;

    public Context context;
    public LayoutInflater inflater;
    QueueStatus[] queueStatusArray;
    List<QueueStatus> list;
    private ProgressDialog progressDialog;
    private String tokenStatus="";
    private MCuraApplication mCuraApplication;
    private int actTransactId=0;
    private AlertDialog successAlertDialog;
    private String isDelete_Unblock;
    private int tokenNo=0;

    public Queue_Adapter(Context context, List<QueueStatus> list,int scheduleId) {
        this.context = context;
        this.list = list;
        this.scheduleId = scheduleId;
        Log.d("list_size",list.size()+"");
        mSharedPreference = context.getSharedPreferences(context.getString(R.string.app_name),
                Context.MODE_PRIVATE);
      }


    static class ViewHolder {
        ImageView statusImg;
        TextView txtViewNo;
        TextView txtViewName;
        TextView txtViewMrno;
        TextView txtViewStatus;
        TextView txtViewHospitalNo;
        public ViewHolder(View v) {
            statusImg = (ImageView) v.findViewById(R.id.Chk_img);
            txtViewNo = (TextView) v.findViewById(R.id.tv_No);
            txtViewName = (TextView) v.findViewById(R.id.tv_Name);
            txtViewMrno = (TextView) v.findViewById(R.id.tv_Mrno);
            txtViewStatus = (TextView) v.findViewById(R.id.tv_status);
           // txtViewHospitalNo = (TextView) v.findViewById(R.id.tv_hospital_no);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public QueueStatus getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final QueueStatus model = list.get(position);
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.items, null);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.txtViewNo.setText(model.getTokenNo().toString());
//        if (model.getHospitalNo() != null) {
//            if (!model.getHospitalNo().toString().equals("")) {
//                holder.txtViewHospitalNo.setText(model.getHospitalNo() + "");
//            } else {
//                holder.txtViewHospitalNo.setVisibility(View.GONE);
//            }
//        } else {
//            holder.txtViewHospitalNo.setVisibility(View.GONE);
//        }
        if (model.getPatName() != null) {
            holder.txtViewName.setText(model.getPatName().toString());
        }
        if (model.getMRNo() != null) {
            holder.txtViewMrno.setText(model.getMRNo().toString());
        }
        if (model.getTokenStatus() != null) {
            String tokenStatus = model.getTokenStatus().toString();
            //holder.txtViewStatus.setText(tokenStatus);
            if (tokenStatus.equals("CHECK_IN")) {
                holder.statusImg.setImageResource(R.drawable.check_in);
                holder.txtViewStatus.setText("CHECK IN");
            }
            if (tokenStatus.equals("PRE_BOOKED")) {
                holder.statusImg.setImageResource(R.drawable.pre_booked);
                holder.txtViewStatus.setText("PRE BOOKED");
            }
            if (tokenStatus.equals("CURRENTLY_VISITING")) {
                holder.statusImg.setImageResource(R.drawable.visiting);
                holder.txtViewStatus.setText("CURRENTLY VISITING");
            }
            if (tokenStatus.equals("CHECK_OUT")) {
                holder.statusImg.setImageResource(R.drawable.complete);
                holder.txtViewStatus.setText("CHECK OUT");
            }
            if (tokenStatus.equals("BLOCKED")) {
                holder.statusImg.setImageResource(R.drawable.status_block);
                holder.txtViewStatus.setText("BLOCKED");
                row.setBackgroundColor(context.getResources().getColor(R.color.bg_norm));
            }
            if (tokenStatus.equals("NO_SHOW")) {
                holder.statusImg.setImageResource(R.drawable.ic_no_show);
                holder.txtViewStatus.setText("NO SHOW");
            }
        }
        final ViewHolder finalHolder = holder;
        holder.txtViewNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getTokenStatus() != null) {
                    tokenStatus = model.getTokenStatus().toString();
                    if (tokenStatus.equals("BLOCKED")) {
                        finalHolder.txtViewNo.setClickable(true);
                        tokenNo = Integer.parseInt(finalHolder.txtViewNo.getText().toString());
                        isDelete_Unblock = "true";
                        //Toast.makeText(Queue_status.this,txtViewNo.getText().toString(),Toast.LENGTH_LONG).show();
                        int appId = model.getAppId();
                        actTransactId = EnumType.ActTransactMasterEnum.Unblock_APT.getActTransactMasterTypeId();

                    }
                } else {
                    finalHolder.txtViewNo.setClickable(true);
                    tokenNo = Integer.parseInt(finalHolder.txtViewNo.getText().toString());
                    isDelete_Unblock = "false";
                    int appId = model.getAppId();
                    //Toast.makeText(Queue_status.this,txtViewNo.getText().toString(),Toast.LENGTH_LONG).show();
                    actTransactId = EnumType.ActTransactMasterEnum.Block_APT.getActTransactMasterTypeId();

                }
            }
        });
        return row;
    }



    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(context.getString(R.string.loading_message));
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
}