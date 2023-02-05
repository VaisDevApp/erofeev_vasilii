package ru.erofeev.labmovies.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.erofeev.labmovies.App;
import ru.erofeev.labmovies.R;
import ru.erofeev.labmovies.data.MovieService;
import ru.erofeev.labmovies.data.MovieServiceRetrofit;
import ru.erofeev.labmovies.databinding.ActivityMovieDetailBinding;
import ru.erofeev.labmovies.entity.Countries;
import ru.erofeev.labmovies.entity.Genres;
import ru.erofeev.labmovies.entity.MovieDetails;

public class MovieDetailActivity extends AppCompatActivity {

    public static String FILM_ID_EXTRA = "FILM_ID";
    private MovieService movieService;
    private ActivityMovieDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieService = ((App)getApplication()).getMovieService();
        LayoutInflater layoutInflater = getLayoutInflater();
        binding = ActivityMovieDetailBinding.inflate(layoutInflater);
        setContentView(binding.getRoot());
        binding.backView.setOnClickListener(v -> finish());
        loadData();
    }

    private void loadData() {
        int filmID = getIntent().getIntExtra(FILM_ID_EXTRA, -1);
        movieService.getMovieDetails(filmID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        binding.errorView.setVisibility(View.INVISIBLE);
                        binding.contentView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onNext(MovieDetails movieDetails) {
                        setData(movieDetails);
                        binding.errorView.setVisibility(View.INVISIBLE);
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        binding.contentView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        binding.errorView.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        binding.contentView.setVisibility(View.INVISIBLE);
                        binding.reloadView.setOnClickListener(v -> loadData());
                    }

                    @Override
                    public void onComplete() {
                        //Empty
                    }
                });
    }
    private void setData(MovieDetails movieDetails) {
        binding.titleView.setText(movieDetails.getNameRu());
        binding.descriptionView.setText(movieDetails.getDescription());
        binding.genresView.setText(getString(R.string.genres_string, Genres.buildString(movieDetails.getGenres())));
        binding.countriesView.setText(getString(R.string.country_string, Countries.buildString(movieDetails.getCountries())));
        Glide.with(MovieDetailActivity.this).load(movieDetails.getPosterUrl()).into(binding.bannerView);
    }
}