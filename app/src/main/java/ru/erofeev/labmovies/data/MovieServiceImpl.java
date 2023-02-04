package ru.erofeev.labmovies.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import ru.erofeev.labmovies.entity.BaseJson;
import ru.erofeev.labmovies.entity.MovieDetails;

public class MovieServiceImpl implements MovieService {

    @Override
    public Observable<BaseJson> getMovies100() {
        return Observable.create(new ObservableOnSubscribe<BaseJson>() {   // создали анонимные класс ObservableOnSubscribe
            @Override
            public void subscribe(ObservableEmitter<BaseJson> e) throws Exception {   // создали источник данных, который делает запрос, обрабатывает данные
                String stringUrl = "https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS&page=1";
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(stringUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("accept", "application/json");
                    urlConnection.setRequestProperty("X-API-KEY", "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b");
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // байтовый поток
                    InputStreamReader input = new InputStreamReader(in); // символьный поток
                    BufferedReader buffer = new BufferedReader(input); // для собирания строк
                    String data = buffer.readLine();
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();  // создается объект класса , который может парсить, разбирать строку в объект
                    BaseJson films = gson.fromJson(data, BaseJson.class); // ссылка на шаблон класса
                    e.onNext(films);  //отправляем данные подписчику
                    e.onComplete();   // закрываем источник
                } catch (Exception g) {
                    g.printStackTrace();
                    e.onError(g);
                } finally {
                    urlConnection.disconnect();
                }
            }
        });
    }

    @Override
    public Observable<MovieDetails> getMovieDetails(int id) {
        return Observable.create(new ObservableOnSubscribe<MovieDetails>() {   // создали анонимные класс ObservableOnSubscribe
            @Override
            public void subscribe(ObservableEmitter<MovieDetails> e) throws Exception {   // создали источник данных, который делает запрос, обрабатывает данные
                String stringUrl = "https://kinopoiskapiunofficial.tech/api/v2.2/films/" + id;
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(stringUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(5000);
                    urlConnection.setRequestProperty("accept", "application/json");
                    urlConnection.setRequestProperty("X-API-KEY", "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b");
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // байтовый поток
                    InputStreamReader input = new InputStreamReader(in); // символьный поток
                    BufferedReader buffer = new BufferedReader(input); // для собирания строк
                    String data = buffer.readLine();
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();  // создается объект класса , который может парсить, разбирать строку в объект
                    MovieDetails filmDetail = gson.fromJson(data, MovieDetails.class); // ссылка на шаблон класса
                    e.onNext(filmDetail);  //отправляем данные подписчику
                    e.onComplete();   // закрываем источник
                } catch (Exception g) {
                    g.printStackTrace();
                    e.onError(g);
                } finally {
                    urlConnection.disconnect();
                }
            }
        });
    }
}
