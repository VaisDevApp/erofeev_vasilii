package ru.erofeev.labmovies.entity;

import java.util.List;

public class Countries {
    private String country;

    public Countries(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
    public static String buildString(List<Countries> countriesList) {
        StringBuilder resultCountries = new StringBuilder();
        for (int i=0; i<countriesList.size();i++) {
            if (i!=0) {
                resultCountries.append(", ");
            }
            resultCountries.append(countriesList.get(i).getCountry());
        }
        return resultCountries.toString();
    }
}
