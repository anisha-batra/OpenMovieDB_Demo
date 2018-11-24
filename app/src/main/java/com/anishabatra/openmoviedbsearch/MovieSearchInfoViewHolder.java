package com.anishabatra.openmoviedbsearch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MovieSearchInfoViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewTitle;
    private TextView textViewReleaseYear;

    public MovieSearchInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textViewTitle = itemView.findViewById(R.id.textViewTitle);
        this.textViewReleaseYear = itemView.findViewById(R.id.textViewReleaseYear);
    }

    public void setMovieInfoItem(final MovieSearchInfo movieSearchInfo) {
        textViewTitle.setText(movieSearchInfo.getTitle());
        textViewReleaseYear.setText(movieSearchInfo.getYear());
    }
}
