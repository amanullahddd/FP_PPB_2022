package com.example.nguberpklui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    ArrayList<HistoryModel> historyList;
    ListView listView;
    HistoryModel historyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        getSupportActionBar().hide();

        //listView = (ListView) findViewById(R.id.lv_history);
        myDB = new DatabaseHelper(this);

        historyList = new ArrayList<>();
        Cursor data = myDB.getListContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(HistoryActivity.this,"Tidak ada riwayat panggilan.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                historyModel = new HistoryModel(data.getString(1),data.getString(2),data.getString(3),data.getString(4));
                historyList.add(i,historyModel);
                System.out.println(data.getString(1)+" "+data.getString(2)+" "+data.getString(3)+" "+data.getString(4));
                System.out.println(historyList.get(i).getTanggal());
                i++;
            }
            Collections.reverse(historyList);
            ListAdapter adapter =  new ListAdapter(this,R.layout.viewholder_history, historyList);
            listView = (ListView) findViewById(R.id.lv_history);
            listView.setAdapter(adapter);
        }
        ImageButton imgbtnhistoryback = findViewById(R.id.imgbtn_history_back);
        imgbtnhistoryback.setOnClickListener(op);
    }


    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imgbtn_history_back:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                    break;
            }
        }
    };


}