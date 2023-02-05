package ru.erofeev.labmovies;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import ru.erofeev.labmovies.entity.Genres;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GenresUnitTest {
    @Test
    public void addition_isCorrect3() {
        List<Genres> genresList = new ArrayList<>();
        genresList.add(new Genres("Комедия"));
        genresList.add(new Genres("Триллер"));
        genresList.add(new Genres("Ужасы"));
        String result = Genres.buildString(genresList);
        assertEquals("Комедия, Триллер, Ужасы", result);
    }
    @Test
    public void addition_isCorrect1() {
        List<Genres> genresList = new ArrayList<>();
        genresList.add(new Genres("Комедия"));
        String result = Genres.buildString(genresList);
        assertEquals("Комедия", result);
    }
    @Test
    public void addition_isCorrect0() {
        List<Genres> genresList = new ArrayList<>();
        String result = Genres.buildString(genresList);
        assertEquals("", result);
    }
}