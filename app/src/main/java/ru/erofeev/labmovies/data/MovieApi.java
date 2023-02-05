package ru.erofeev.labmovies.data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.erofeev.labmovies.entity.PageFilms;
import ru.erofeev.labmovies.entity.MovieDetails;

public interface MovieApi {
    String API_KEY_HEADER = "X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b";

    @Headers({
            "accept: application/json",
            API_KEY_HEADER
    })
    @GET("api/v2.2/films/top")
    Observable<PageFilms> getMovies100(@Query("type") String type, @Query("page") int page);

    @Headers({
            "accept: application/json",
            API_KEY_HEADER
    })
    @GET("api/v2.2/films/{id}")
    Observable<MovieDetails> getMovieDetails(@Path("id") int id);

}
