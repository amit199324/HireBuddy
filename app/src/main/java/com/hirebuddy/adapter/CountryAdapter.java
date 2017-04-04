package com.hirebuddy.adapter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hirebuddy.R;
import com.hirebuddy.bean.CountryCodeBean;

import java.util.ArrayList;

/**
 * Created by mobulous02 on 20/1/16.
 */
public class CountryAdapter extends BaseAdapter {
    ArrayList<CountryCodeBean> list;
    AppCompatActivity ctx;
    public CountryAdapter(AppCompatActivity ctx, ArrayList<CountryCodeBean> list)
    {

        this.ctx=ctx;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.country_list, null);

            holder = new ViewHolder();
            holder.tv_country_name = (TextView) convertView.findViewById(R.id.tvcountryname);
            holder.tvcountrycode = (TextView) convertView.findViewById(R.id.tvcountrycode);
            holder.tvcountryISOcode = (TextView) convertView.findViewById(R.id.tvcountryISOcode);

            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        CountryCodeBean item = list.get(position);
        holder.tv_country_name.setText(item.getCountryName());
        holder.tvcountrycode.setText(item.getCountryCode());
        holder.tvcountryISOcode.setText(item.getCountryIsoCode());
        return convertView;
    }



    private class ViewHolder {

        TextView tv_country_name,tvcountrycode,tvcountryISOcode;

    }
}
