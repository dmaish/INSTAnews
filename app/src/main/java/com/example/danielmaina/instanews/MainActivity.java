package com.example.danielmaina.instanews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ReadRss readRss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);
        //ReadRss readRss = new ReadRss(this);
        readRss.execute();

        //Declaring a ListView and its ArrayAdapter
        ListView myListView = (ListView) findViewById(R.id.myListView);
        ArrayAdapter itemsAdapter;

        itemsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,);

        //set the adapter to the ListView
        myListView.setAdapter(itemsAdapter);
    }
}
