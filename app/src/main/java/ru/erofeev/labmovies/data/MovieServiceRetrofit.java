package ru.erofeev.labmovies.data;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.erofeev.labmovies.entity.PageFilms;
import ru.erofeev.labmovies.entity.MovieDetails;

public class MovieServiceRetrofit implements MovieService {
    private static String BASE_URL = "https://kinopoiskapiunofficial.tech/";
    private static String TYPE_TOP_100 = "TOP_100_POPULAR_FILMS";
    private MovieApi movieApi;

    public MovieServiceRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  // поключили парсинг из Json в объект java
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // подключаем RxAdapter, который позволяет указывать возвращаемый тип Observable
                .build();
        movieApi = retrofit.create(MovieApi.class);
    }

    @Override
    public Observable<PageFilms> getMovies100(int page) {
        return movieApi.getMovies100(TYPE_TOP_100, page);
    }

    @Override
    public Observable<MovieDetails> getMovieDetails(int id) {
        return movieApi.getMovieDetails(id);
    }
}
