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
import ru.erofeev.labmovies.data.MovieServiceImpl;
import ru.erofeev.labmovies.databinding.ActivityMainBinding;
import ru.erofeev.labmovies.entity.BaseJson;
import ru.erofeev.labmovies.entity.Films;
import ru.erofeev.labmovies.presentation.adapter.MovieListAdapter;

public class MovieListActivity extends AppCompatActivity {
    private MovieService movieService = new MovieServiceImpl();
    private ActivityMainBinding activityMainBinding;
    private MovieListAdapter movieListAdapter = new MovieListAdapter(new MovieListAdapter.ListOnClickListener() {
        @Override
        public void onClickItem(Films films) {
            Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
            intent.putExtra("FILM_ID", films.getFilmId());
            startActivity(intent);
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        activityMainBinding.recyclerView.setAdapter(movieListAdapter);
        activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));  //располанем элементы списка вертикально
        loadData();
    }
    private void loadData() {
        movieService.getMovies100()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseJson>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        activityMainBinding.progressBar.setVisibility(View.VISIBLE);
                        activityMainBinding.errorView.setVisibility(View.INVISIBLE);
                        activityMainBinding.recyclerView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onNext(BaseJson baseJson) {
                        baseJson.toString();
                        movieListAdapter.update(baseJson.getFilms());
                        activityMainBinding.errorView.setVisibility(View.INVISIBLE);
                        activityMainBinding.progressBar.setVisibility(View.INVISIBLE);
                        activityMainBinding.recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        activityMainBinding.errorView.setVisibility(View.VISIBLE);
                        activityMainBinding.progressBar.setVisibility(View.INVISIBLE);
                        activityMainBinding.recyclerView.setVisibility(View.INVISIBLE);
                        activityMainBinding.reloadView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadData();
                            }
                        });
                    }

                    @Override
                    public void onComplete() {
                        //Empty
                    }
                });
    }
}