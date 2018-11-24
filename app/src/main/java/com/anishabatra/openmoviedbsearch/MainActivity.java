package com.anishabatra.openmoviedbsearch;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.android.volley.RequestQueue;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText editTextMovieName;

    SQLiteDatabase mydatabase;

    public void btnSearch_Click(View view) {
        final String movieName = editTextMovieName.getText().toString();
        Log.i("Movie name", movieName);

        if (movieName.equals("")) {
            showMiscAlert("Movie Name is empty");
        } else {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://www.omdbapi.com/?i=tt3896198&apikey=9e288e47&s=" + movieName;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //editTextMovieName.setText("Response is: "+ response.substring(0,500));
                            Log.i("JSON Response", response);

                            ArrayList<MovieSearchInfo> movieSearchInfos = extractMovieSearchResultsFromJsonResponse(response);

                            if (movieSearchInfos == null) {
                                showMiscAlert("List is too big");

                                return;
                            }

                            Intent intent = new Intent(getApplicationContext(), MovieSearchResultsActivity.class);
                            intent.putExtra("MovieSearchInfos", movieSearchInfos);
                            startActivity(intent);

                            saveSearchHistoryToDatabase(movieName);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("JSON Response", "That didn't work");

                    showMiscAlert(("API didn't work"));
                }
            });

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    private void showMiscAlert(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(errorMessage);
        builder.setTitle("Error");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        builder.create();
        builder.show();
    }

    public ArrayList<MovieSearchInfo> extractMovieSearchResultsFromJsonResponse(String respJsonString) {
        ArrayList<MovieSearchInfo> movieSearchInfos = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(respJsonString);
            JSONArray jsonArraySearchIfo = jsonObject.getJSONArray("Search");

            for (int i = 0; i < jsonArraySearchIfo.length(); i++) {
                JSONObject jObject = jsonArraySearchIfo.getJSONObject(i);

                MovieSearchInfo movieSearchInfo = new MovieSearchInfo();

                movieSearchInfo.setTitle(jObject.getString("Title"));
                movieSearchInfo.setImdbID(jObject.getString("imdbID"));
                movieSearchInfo.setType(jObject.getString("Type"));
                movieSearchInfo.setPoster(jObject.getString("Poster"));
                movieSearchInfo.setYear(jObject.getString("Year"));

                movieSearchInfos.add(movieSearchInfo);
            }

            return movieSearchInfos;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveSearchHistoryToDatabase(String movieName) {
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Titles(Name VARCHAR);");
        mydatabase.execSQL("INSERT INTO Titles VALUES('" + movieName + "');");
    }

    public void btnShowHistory_Click(View view) {
        Intent intent = new Intent(this, SearchHistoryActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMovieName = findViewById(R.id.editTextMovieName);

        mydatabase = openOrCreateDatabase("Movie", MODE_PRIVATE, null);

    }
}