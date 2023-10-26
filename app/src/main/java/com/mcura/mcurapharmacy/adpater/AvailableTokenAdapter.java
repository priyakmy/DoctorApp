package com.mcura.mcurapharmacy.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.activity.QueueActivity;
import com.mcura.mcurapharmacy.model.AvailableTokenList;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by GauraVachhani on 16/11/15.
 */
public class AvailableTokenAdapter extends ArrayAdapter<AvailableTokenList> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<AvailableTokenList> values;

    public AvailableTokenAdapter(Context context, int textViewResourceId,
                                 AvailableTokenList[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = new ArrayList<>(Arrays.asList(values));

        /*Doctor mDoctorRecord = new Doctor();
        mDoctorRecord.setUserId(0);
        mDoctorRecord.setUname("Select Doctor");

        this.values.add(0,mDoctorRecord);*/


        /*Collections.sort(this.values, new Comparator<Doctor>() {
            @Override
            public int compare(Doctor lhs, VitalRecordsModel rhs) {
                return Integer.valueOf(lhs.getVitalNatureId()).compareTo(rhs.getVitalNatureId());
            }
        });*/
    }


    public int getCount(){
        return values.size();
    }

    public AvailableTokenList getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        View row = convertView;
        if (row == null) {
            //inflate your customlayout for the textview
            LayoutInflater inflater = ((QueueActivity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.show_token_row_layout, parent, false);
        }
        //put the data in it
        int token = values.get(position).getTokenNo();
        TextView text1 = (TextView) row.findViewById(R.id.token_No);
        text1.setText(token+"");

        return row;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            //inflate your customlayout for the textview
            LayoutInflater inflater = ((QueueActivity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.show_token_row_layout, parent, false);
        }
        //put the data in it
        int token = values.get(position).getTokenNo();
        TextView text1 = (TextView) row.findViewById(R.id.token_No);
        text1.setText(token+"");

        return row;
    }
}
