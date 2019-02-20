package com.rg17.credittransfer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Win8 on 4/21/2018.
 */

public class DetailsAdapter extends ArrayAdapter
{
    List list = new ArrayList();

    public DetailsAdapter(Context context, int resources)
    {
        super(context,resources);
    }


    public void add(Details object)
    {
        list.add(object);
        super.add(object);
    }

    @Override
    public int getCount()
    {
        return  list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View row = convertView;
        DetailsHolder detailsHolder;
        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.user_details_row,parent,false);
            detailsHolder = new DetailsHolder();
            detailsHolder.tx_fn = (TextView)row.findViewById(R.id.text_first_name);
            detailsHolder.tx_ln = (TextView)row.findViewById(R.id.text_last_name);
            detailsHolder.tx_cc = (TextView)row.findViewById(R.id.text_credit);
            row.setTag(detailsHolder);
        }
        else
        {
            detailsHolder = (DetailsHolder)row.getTag();
        }
        Details details = (Details)getItem(position);
        detailsHolder.tx_fn.setText(details.getFirstName().toString());
        detailsHolder.tx_ln.setText(details.getLastName().toString());
        detailsHolder.tx_cc.setText(Integer.toString(details.getCurrentCredit()));

        return row;
    }


    static class DetailsHolder
    {
        TextView tx_fn,tx_ln,tx_cc;
    }

}
