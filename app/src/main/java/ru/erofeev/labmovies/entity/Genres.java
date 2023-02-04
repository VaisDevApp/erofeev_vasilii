package ru.erofeev.labmovies.entity;

import java.util.List;

public class Genres {
    private String genre;

    public Genres(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public static String buildString(List<Genres> genresList) {
        StringBuilder resultGenres = new StringBuilder();
        for (int i = 0; i < genresList.size(); i++) {
            if (i != 0) {
                resultGenres.append(", ");
            }
            resultGenres.append(genresList.get(i).getGenre());
        }
        return resultGenres.toString();
    }
}
