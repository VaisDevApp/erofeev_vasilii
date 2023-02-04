package ru.erofeev.labmovies.entity;

import java.util.List;

public class BaseJson {
    private int pagesCount;
    private List<Films> films;

    public BaseJson(int pagesCount, List<Films> films) {
        this.pagesCount = pagesCount;
        this.films = films;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public List<Films> getFilms() {
        return films;
    }
}
