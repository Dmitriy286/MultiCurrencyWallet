package models;

import java.util.HashMap;
import java.util.Map;

public class Currency {
    String name;
    Map<Currency, Double> rates;

    public Currency(String name) {
        this.name = name;
        this.rates = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Currency, Double> getRates() {
        return rates;
    }

    public void setRates(Currency currencyInRateList, double rate) {
        if (rate <= 0) {
            System.out.println("You have entered wrong value");
        } else {
            this.getRates().put(currencyInRateList, rate);
            System.out.println("Rate " + this.getName() + "/" + currencyInRateList.getName() +
                    " has been established as 1 to " + this.getRates().get(currencyInRateList));
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
