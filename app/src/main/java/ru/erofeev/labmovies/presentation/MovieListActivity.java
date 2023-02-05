package ru.erofeev.labmovies.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.erofeev.labmovies.data.MovieService;
import ru.erofeev.labmovies.data.MovieServiceRetrofit;
import ru.erofeev.labmovies.databinding.ActivityMainBinding;
import ru.erofeev.labmovies.entity.PageFilms;
import ru.erofeev.labmovies.entity.Film;
import ru.erofeev.labmovies.presentation.adapter.MovieListAdapter;

public class MovieListActivity extends AppCompatActivity {
    private MovieService movieService = new MovieServiceRetrofit();
    private ActivityMainBinding binding;
    private MovieListAdapter movieListAdapter = new MovieListAdapter(new MovieListAdapter.ListOnClickListener() {
        @Override
        public void onClickItem(Film film) {
            Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.FILM_ID_EXTRA, film.getFilmId());
            startActivity(intent);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerView.setAdapter(movieListAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));  //располагаем элементы списка вертикально
        loadData();
    }

    private void loadData() {
        movieService.getMovies100()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageFilms>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        binding.errorView.setVisibility(View.INVISIBLE);
                        binding.recyclerView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onNext(PageFilms pageFilms) {
                        movieListAdapter.update(pageFilms.getFilms());
                        binding.errorView.setVisibility(View.INVISIBLE);
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        binding.recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        binding.errorView.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        binding.recyclerView.setVisibility(View.INVISIBLE);
                        binding.reloadView.setOnClickListener(v -> loadData());
                    }

                    @Override
                    public void onComplete() {
                        //Empty
                    }
                });
    }
}