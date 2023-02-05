package ru.erofeev.labmovies.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.erofeev.labmovies.App;
import ru.erofeev.labmovies.data.MovieService;
import ru.erofeev.labmovies.databinding.ActivityMovieListBinding;
import ru.erofeev.labmovies.entity.PageFilms;
import ru.erofeev.labmovies.entity.Film;
import ru.erofeev.labmovies.presentation.adapter.MovieListAdapter;

public class MovieListActivity extends AppCompatActivity {
    private final static int MAX_PAGE = 5;
    private final static String CURRENT_PAGE_KEY = "CURRENT_PAGE_KEY";
    private MovieService movieService;
    private ActivityMovieListBinding binding;
    private int currentPage = 1;
    private MovieListAdapter movieListAdapter = new MovieListAdapter(film -> openDetails(film));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY);
        }
        movieService = ((App) getApplication()).getMovieService();
        binding = ActivityMovieListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerView.setAdapter(movieListAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));  //располагаем элементы списка вертикально
        binding.nextView.setOnClickListener(v -> loadNextPage());
        binding.preView.setOnClickListener(v -> preViewPage());
        loadData();
        updateVisibilityPageButton();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_PAGE_KEY, currentPage);
    }

    private void updateVisibilityPageButton() {
        if (currentPage == MAX_PAGE) {
            binding.nextView.setVisibility(View.INVISIBLE);
        } else {
            binding.nextView.setVisibility(View.VISIBLE);
        }
        if (currentPage == 1) {
            binding.preView.setVisibility(View.INVISIBLE);
        } else {
            binding.preView.setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        movieService.getMovies100(currentPage)
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

    private void loadNextPage() {
        if (currentPage > 0 && currentPage < MAX_PAGE) {
            currentPage++;
            loadData();
        }
        updateVisibilityPageButton();
    }

    private void preViewPage() {
        if (currentPage > 1 && currentPage <= MAX_PAGE) {
            currentPage--;
            loadData();
        }
        updateVisibilityPageButton();
    }

    private void openDetails(Film film) {
        Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.FILM_ID_EXTRA, film.getFilmId());
        startActivity(intent);
    }
}