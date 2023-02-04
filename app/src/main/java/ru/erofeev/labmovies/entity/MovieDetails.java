package ru.erofeev.labmovies.entity;

import java.util.List;

public class MovieDetails {
    private String nameRu;
    private String description;
    private List<Genres> genres;
    private List<Countries> countries;
    private String posterUrl;

    public MovieDetails(String nameRu, String description, List<Genres> genres, List<Countries> countries, String posterUrl) {
        this.nameRu = nameRu;
        this.description = description;
        this.genres = genres;
        this.countries = countries;
        this.posterUrl = posterUrl;
    }

    public String getNameRu() {
        return nameRu;
    }

    public String getDescription() {
        return description;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public List<Countries> getCountries() {
        return countries;
    }

    public String getPosterUrl() {
        return posterUrl;
    }
}
