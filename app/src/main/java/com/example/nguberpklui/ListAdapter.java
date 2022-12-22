package com.example.nguberpklui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<HistoryModel> {
    private LayoutInflater mInflater;
    private ArrayList<HistoryModel> historyModels;
    private int mViewResourceId;

    public ListAdapter(Context context, int textViewResourceId, ArrayList<HistoryModel> historyModels) {
        super(context, textViewResourceId, historyModels);
        this.historyModels = historyModels;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        HistoryModel hModel = historyModels.get(position);

        if (hModel != null) {
            TextView judul = (TextView) convertView.findViewById(R.id.txt_history_judul);
            TextView nomorhp = (TextView) convertView.findViewById(R.id.txt_history_nohp);
            TextView tanggal = (TextView) convertView.findViewById(R.id.txt_history_tanggal);
            TextView status = (TextView) convertView.findViewById(R.id.txt_history_status);
            if (judul != null) {
                judul.setText(hModel.getJudul());
            }
            if (nomorhp != null) {
                nomorhp.setText((hModel.getNomerhp()));
            }
            if (tanggal != null) {
                tanggal.setText((hModel.getTanggal()));
            }
            if (status != null) {
                status.setText((hModel.getKondisi()));
            }

        }

        return convertView;
    }

}
