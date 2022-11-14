package models;

import java.util.HashMap;
import java.util.Map;

public class Currency {
    String name;
    Map<String, Integer> rates = new HashMap<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getRates() {
        return rates;
    }

    public void setRates(Map<String, Integer> rates) {
        this.rates = rates;
    }
}
