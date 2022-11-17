package models;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Currency class
 */
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

    /**
     * Sets rates for current currency. Rate is established as value of one unit of current currencies
     * compared to defined units of chosen currency.
     * @param currencyInRateList chosen currency to compare
     * @param rate double amount of chosen currency units
     */
    public void setRates(Currency currencyInRateList, double rate) {
        if (rate <= 0) {
            System.out.println("You have entered wrong value");
        } else {
            this.getRates().put(currencyInRateList, rate);
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            System.out.println("Rate " + this.getName() + "/" + currencyInRateList.getName() +
                    " has been established as 1 to "
                    + decimalFormat.format(this.getRates().get(currencyInRateList)));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return name.equals(currency.name) && rates.equals(currency.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rates);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
