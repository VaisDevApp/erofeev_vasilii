package ru.erofeev.labmovies.presentation.adapter;

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
import ru.erofeev.labmovies.entity.Films;
import ru.erofeev.labmovies.entity.Genres;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemHolder> {
    private List<Films> filmsList = new ArrayList<>();
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
        Films film = filmsList.get(position);
        holder.titleView.setText(film.getNameRu());
        holder.descriptionView.setText(Genres.buildString(film.getGenres()) + " (" + film.getYear() + ")");
        Glide.with(holder.logoView).load(film.getPosterUrlPreview()).into(holder.logoView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOnClickListener.onClickItem(film);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmsList.size();
    }
    public void update(List<Films> filmsList) {
        this.filmsList = filmsList;
        notifyDataSetChanged();
    }
    class MovieItemHolder extends RecyclerView.ViewHolder {
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
        void onClickItem(Films films);
    }
}
