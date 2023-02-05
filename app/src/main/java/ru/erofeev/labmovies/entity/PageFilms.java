package ru.erofeev.labmovies.entity;

import java.util.List;

public class PageFilms {
    private int pagesCount;
    private List<Film> films;

    public PageFilms(int pagesCount, List<Film> films) {
        this.pagesCount = pagesCount;
        this.films = films;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public List<Film> getFilms() {
        return films;
    }
}
