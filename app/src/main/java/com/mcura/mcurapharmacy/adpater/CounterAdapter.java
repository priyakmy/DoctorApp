package com.mcura.mcurapharmacy.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.activity.QueueActivity;
import com.mcura.mcurapharmacy.model.CounterDataModel;

import java.util.ArrayList;
import java.util.Arrays;

public class CounterAdapter extends ArrayAdapter<CounterDataModel> {
    private ArrayList<CounterDataModel> orig;
    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<CounterDataModel> values;

    public CounterAdapter(Context context, int textViewResourceId,
                               CounterDataModel[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = new ArrayList<>(Arrays.asList(values));

        CounterDataModel mDoctorRecord = new CounterDataModel();
        mDoctorRecord.setUserRoleId(0);
        mDoctorRecord.setUserName("Select Counter");

        this.values.add(0,mDoctorRecord);


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

    public CounterDataModel getItem(int position){
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
            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        String item = values.get(position).getUserName();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(item);
        }

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
            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        String item = values.get(position).getUserName();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(item);
        }

        return row;
    }

}