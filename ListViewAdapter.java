package com.iqbalproject.aplikasigis.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iqbalproject.aplikasigis.DataTempat;
import com.iqbalproject.aplikasigis.Pojo.data_tempat;
import com.iqbalproject.aplikasigis.R;

import java.util.List;

/**
 * Created by IQBAL-PC on 07-Apr-17.
 */

public class ListViewAdapter extends BaseAdapter {

    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<data_tempat> mTempats;

    public ListViewAdapter(Activity activity, List<data_tempat> tempats) {
        mActivity = activity;
        mTempats = tempats;
    }

    @Override
    public int getCount() {
        return mTempats.size();
    }

    @Override
    public Object getItem(int position) {
        return mTempats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mInflater == null){
            mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_list_data_tempat, null);
        }

        TextView namaTempat = (TextView) convertView.findViewById(R.id.tvNamaTempatLV);
        TextView alamat = (TextView) convertView.findViewById(R.id.tvAlamatLV);

        data_tempat mData_tempat = mTempats.get(position);

        namaTempat.setText(mData_tempat.getNama());
        alamat.setText(mData_tempat.getAlamat());

        return convertView;
    }
}
