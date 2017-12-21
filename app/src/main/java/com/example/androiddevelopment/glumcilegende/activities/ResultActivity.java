package com.example.androiddevelopment.glumcilegende.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.androiddevelopment.glumcilegende.R;

/**
 * Created by BBLOJB on 21.12.2017..
 */

public class ResultActivity extends AppCompatActivity{

    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String[] data = (String[]) getIntent().getExtras().get("data");

        ListView listView = (ListView) findViewById(R.id.listview);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, data);
        listView.setAdapter(adapter);
    }
}

