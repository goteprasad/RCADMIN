package com.example.admin.rcadmin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterCustomer extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] txtname, txtmobile, txtvillage, txtage;
    private final Integer[] imageId;
    public AdapterCustomer(Activity context,
                           String[] txtname, String[] txtmobile, String[] txtvillage, String[] txtage, Integer[] imageId) {
        super(context, R.layout.row_customer, txtname);
        this.context = context;
        this.txtname = txtname;
        this.txtmobile = txtmobile;
        this.txtvillage = txtvillage;
        this.txtage = txtage;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.row_customer, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.customer_name);
        TextView txtMobile = (TextView) rowView.findViewById(R.id.customer_mobile);
        TextView txtVillage = (TextView) rowView.findViewById(R.id.customer_village);
        TextView txtAge = (TextView) rowView.findViewById(R.id.customer_age);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.customer_img);
        txtName.setText(txtname[position]);
        txtMobile.setText(txtmobile[position]);
        txtVillage.setText(txtvillage[position]);
        txtAge.setText(txtage[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}