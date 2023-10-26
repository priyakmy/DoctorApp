package com.mcura.mcurapharmacy.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.model.FollowUpModel;

import java.util.ArrayList;

/**
 * Created by mCURA1 on 4/17/2017.
 */

public class FollowUpAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    Context context;
    ArrayList<FollowUpModel> followUpModelArrayList;
    public FollowUpAdapter(Context context, ArrayList<FollowUpModel> followUpModelArrayList) {
        this.context = context;
        this.followUpModelArrayList = followUpModelArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return followUpModelArrayList.size();
    }

    @Override
    public FollowUpModel getItem(int position) {
        return followUpModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        View row = convertView;
        if (row == null) {
            //inflate your customlayout for the textview

            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        String item;
        if(position==0){
            item = followUpModelArrayList.get(position).getFollowupUnit();
        }else{
           item = followUpModelArrayList.get(position).getFollowupNumber()+" "+followUpModelArrayList.get(position).getFollowupUnit();
        }
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
            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        String item = followUpModelArrayList.get(position).getFollowupNumber()+" "+followUpModelArrayList.get(position).getFollowupUnit();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(item);
        }

        return row;
    }

}
