package ru.erofeev.labmovies.data;

import ru.erofeev.labmovies.entity.BaseJson;
import io.reactivex.Observable;
import ru.erofeev.labmovies.entity.MovieDetails;

public interface MovieService {
    Observable<BaseJson> getMovies100();
    Observable<MovieDetails> getMovieDetails(int id);

}
