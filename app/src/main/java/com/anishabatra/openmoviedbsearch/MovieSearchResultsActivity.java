package com.anishabatra.openmoviedbsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import java.util.ArrayList;

public class MovieSearchResultsActivity extends AppCompatActivity {
    
    RecyclerView recyclerViewSearchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search_results);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewSearchItem = findViewById(R.id.recyclerViewSearchItem);
        ArrayList<MovieSearchInfo> movieSearchInfos = (ArrayList<MovieSearchInfo>)getIntent().getSerializableExtra("MovieSearchInfos");

        recyclerViewSearchItem.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        MovieSearchInfoAdapter movieSearchInfoAdapter = new MovieSearchInfoAdapter(movieSearchInfos);
        recyclerViewSearchItem.setAdapter(movieSearchInfoAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
