package com.anishabatra.openmoviedbsearch;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

public class SearchHistoryActivity extends AppCompatActivity {

    SQLiteDatabase mydatabase;
    ListView listViewHistory;

    private void showHistory() {
         ArrayList<String> movieHistory = new ArrayList();

        Cursor resultSet = mydatabase.rawQuery("Select * from Titles",null);
        resultSet.moveToFirst();

        while (resultSet.isAfterLast() == false) {
            String movieName = resultSet.getString(resultSet.getColumnIndex("Name"));
            Log.i("DB MovieTitle ", movieName);
            movieHistory.add(movieName);
            resultSet.moveToNext();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_search_history_item, movieHistory);
        listViewHistory.setAdapter(adapter);
    }

    public void btnBackToSearch_Click(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        mydatabase = openOrCreateDatabase("Movie",MODE_PRIVATE,null);
        listViewHistory = findViewById(R.id.listViewHistory);

        showHistory();
    }
}
