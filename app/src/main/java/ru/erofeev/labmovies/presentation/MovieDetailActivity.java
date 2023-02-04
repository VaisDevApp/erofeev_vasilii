package ru.erofeev.labmovies.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.erofeev.labmovies.R;
import ru.erofeev.labmovies.data.MovieService;
import ru.erofeev.labmovies.data.MovieServiceImpl;
import ru.erofeev.labmovies.databinding.ActivityMainBinding;
import ru.erofeev.labmovies.databinding.ActivityMovieDetailBinding;
import ru.erofeev.labmovies.entity.BaseJson;
import ru.erofeev.labmovies.entity.Countries;
import ru.erofeev.labmovies.entity.Genres;
import ru.erofeev.labmovies.entity.MovieDetails;

public class MovieDetailActivity extends AppCompatActivity {
    MovieService movieService = new MovieServiceImpl();
    ActivityMovieDetailBinding activityMovieDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieDetailBinding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(activityMovieDetailBinding.getRoot());
        activityMovieDetailBinding.backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadData();
    }

    private void loadData() {
        int filmID = getIntent().getIntExtra("FILM_ID", -1);
        movieService.getMovieDetails(filmID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        activityMovieDetailBinding.progressBar.setVisibility(View.VISIBLE);
                        activityMovieDetailBinding.errorView.setVisibility(View.INVISIBLE);
                        activityMovieDetailBinding.contentView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onNext(MovieDetails movieDetails) {
                        activityMovieDetailBinding.titleView.setText(movieDetails.getNameRu());
                        activityMovieDetailBinding.descriptionView.setText(movieDetails.getDescription());
                        activityMovieDetailBinding.genresView.setText("Жанры: " + Genres.buildString(movieDetails.getGenres()));
                        activityMovieDetailBinding.countriesView.setText("Страны: " + Countries.buildString(movieDetails.getCountries()));
                        Glide.with(MovieDetailActivity.this).load(movieDetails.getPosterUrl()).into(activityMovieDetailBinding.bannerView);
                        activityMovieDetailBinding.errorView.setVisibility(View.INVISIBLE);
                        activityMovieDetailBinding.progressBar.setVisibility(View.INVISIBLE);
                        activityMovieDetailBinding.contentView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        activityMovieDetailBinding.errorView.setVisibility(View.VISIBLE);
                        activityMovieDetailBinding.progressBar.setVisibility(View.INVISIBLE);
                        activityMovieDetailBinding.contentView.setVisibility(View.INVISIBLE);
                        activityMovieDetailBinding.reloadView.setOnClickListener(new View.OnClickListener() {
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