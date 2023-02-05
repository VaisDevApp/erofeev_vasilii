package ru.erofeev.labmovies.data;

import ru.erofeev.labmovies.entity.PageFilms;
import io.reactivex.Observable;
import ru.erofeev.labmovies.entity.MovieDetails;

public interface MovieService {
    Observable<PageFilms> getMovies100();
    Observable<MovieDetails> getMovieDetails(int id);

}
