package ru.erofeev.labmovies.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.erofeev.labmovies.R;
import ru.erofeev.labmovies.entity.Film;
import ru.erofeev.labmovies.entity.Genres;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemHolder> {
    private List<Film> filmList = new ArrayList<>();
    private ListOnClickListener listOnClickListener;

    public MovieListAdapter(ListOnClickListener listOnClickListener) {
        this.listOnClickListener = listOnClickListener;
    }

    @NonNull
    @Override
    public MovieItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());  // создаем экземпляр класса LayoutInflater
        View view = layoutInflater.inflate(R.layout.item_movie, parent, false);
        return new MovieItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemHolder holder, int position) {
        Context context = holder.titleView.getContext();
        Film film = filmList.get(position);
        holder.titleView.setText(film.getNameRu());
        holder.descriptionView.setText(context.getString(R.string.skobki_string, Genres.buildString(film.getGenres()), String.valueOf(film.getYear())));
        Glide.with(holder.logoView).load(film.getPosterUrlPreview()).into(holder.logoView);
        holder.cardView.setOnClickListener(v -> listOnClickListener.onClickItem(film));
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public void update(List<Film> filmList) {
        this.filmList = filmList;
        notifyDataSetChanged();
    }

    static class MovieItemHolder extends RecyclerView.ViewHolder {
        public ImageView logoView;
        public TextView titleView;
        public TextView descriptionView;
        public View cardView;

        public MovieItemHolder(@NonNull View itemView) {
            super(itemView);
            logoView = itemView.findViewById(R.id.logoView);
            titleView = itemView.findViewById(R.id.titleView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public interface ListOnClickListener {
        void onClickItem(Film film);
    }
}
