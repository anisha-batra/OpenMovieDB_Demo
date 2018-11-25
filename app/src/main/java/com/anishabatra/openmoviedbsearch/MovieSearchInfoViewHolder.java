package com.anishabatra.openmoviedbsearch;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MovieSearchInfoViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewTitle;
    private TextView textViewReleaseYear;
    private ImageView imageViewMoviePoster;

    private MovieSearchResultsActivity mainActivity;

    public MovieSearchInfoViewHolder(@NonNull View itemView, MovieSearchResultsActivity mainActivity) {
        super(itemView);
        this.textViewTitle = itemView.findViewById(R.id.textViewTitle);
        this.textViewReleaseYear = itemView.findViewById(R.id.textViewReleaseYear);
        this.imageViewMoviePoster = itemView.findViewById(R.id.imageView_movie_poster);

        this.mainActivity = mainActivity;
    }

    public void setMovieInfoItem(final MovieSearchInfo movieSearchInfo) {
        textViewTitle.setText(movieSearchInfo.getTitle());
        textViewReleaseYear.setText(movieSearchInfo.getYear());

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    is = (InputStream) new URL(movieSearchInfo.getPoster()).getContent();
                    Drawable d = Drawable.createFromStream(is, "src name");
                    imageViewMoviePoster.setImageDrawable(d);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
