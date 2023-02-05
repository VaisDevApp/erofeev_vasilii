package ru.erofeev.labmovies;

import android.app.Application;

import ru.erofeev.labmovies.data.MovieService;
import ru.erofeev.labmovies.data.MovieServiceRetrofit;

public class App extends Application {
    private MovieService movieService;
    @Override
    public void onCreate() {
        super.onCreate();
        movieService = new MovieServiceRetrofit();
    }

    public MovieService getMovieService() {
        return movieService;
    }
}
