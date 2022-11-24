package models;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Currency class. Represents currency entity with its name and exchange rates for other currencies.
 */
public class Currency {
    private final String currencyName;
    private Map<Currency, Double> currenciesExchangeRates;

    public Currency(String currencyName) {
        if (Objects.equals(currencyName, "")) {
            throw new IllegalArgumentException("Currency name can't be empty string");
        }
        this.currencyName = currencyName;
        this.currenciesExchangeRates = new HashMap<>();
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public Map<Currency, Double> getCurrenciesExchangeRates() {
        return currenciesExchangeRates;
    }

    /**
     * Sets rates for current currency. Rate is established as value of one unit of current currencies
     * compared to defined units of chosen currency.
     * @param currencyInRateList chosen currency to compare
     * @param rate double amount of chosen currency units
     */
    public boolean setRates(Currency currencyInRateList, double rate) {
        if (rate <= 0) {
            System.out.println("You have entered wrong value");
            return false;
        } else {
            this.getCurrenciesExchangeRates().put(currencyInRateList, rate);
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            System.out.println("Rate " + this.getCurrencyName() + "/" + currencyInRateList.getCurrencyName() +
                    " has been established as 1 to "
                    + decimalFormat.format(this.getCurrenciesExchangeRates().get(currencyInRateList)));
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return currencyName.equals(currency.currencyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyName);
    }

    @Override
    public String toString() {
        return this.getCurrencyName();
    }
}
