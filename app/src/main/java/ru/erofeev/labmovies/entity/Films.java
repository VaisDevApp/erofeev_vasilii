package ru.erofeev.labmovies.entity;

import java.util.List;

public class Films {
    private int filmId;
    private String nameRu;
    private int year;
    private List<Countries> countries;
    private List<Genres> genres;
    private String posterUrlPreview;

    public Films(int filmId, String nameRu, int year, List<Countries> countries, List<Genres> genres, String posterUrlPreview) {
        this.filmId = filmId;
        this.nameRu = nameRu;
        this.year = year;
        this.countries = countries;
        this.genres = genres;
        this.posterUrlPreview = posterUrlPreview;
    }

    public String getPosterUrlPreview() {
        return posterUrlPreview;
    }

    public int getFilmId() {
        return filmId;
    }

    public String getNameRu() {
        return nameRu;
    }

    public int getYear() {
        return year;
    }

    public List<Countries> getCountries() {
        return countries;
    }

    public List<Genres> getGenres() {
        return genres;
    }
}
