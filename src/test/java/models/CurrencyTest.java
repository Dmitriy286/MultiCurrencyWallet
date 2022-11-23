package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyTest {
    static Currency currentCurrency;
    static Currency currencyInRateList;

    @BeforeEach
    public void setUp() {
        currentCurrency = new Currency("dollar");
        currencyInRateList = new Currency("ruble");
    }

    @Test
    public void testCreateNewCurrencyWithEmptyUserName() {
        assertThrows(IllegalArgumentException.class, () -> new Currency(""));
    }

    @Test
    public void testSetRatesWithPositiveAmount() {
        double rate = 5;
        boolean result = currentCurrency.setRates(currencyInRateList, rate);
        assertTrue(result);
    }

    @Test
    public void testSetRatesWithNegativeOrZeroAmount() {
        double rate = -1.5;
        boolean resultNegative = currentCurrency.setRates(currencyInRateList, rate);
        assertFalse(resultNegative);
        boolean resultZero = currentCurrency.setRates(currencyInRateList, rate);
        assertFalse(resultZero);
    }

    @Test
    public void testGetEmptyRates() {
        Map<Currency, Double> rates = currentCurrency.getRates();
        Map<Currency, Double> expected = new HashMap<>();
        assertEquals(expected, rates);
    }

    @Test
    public void testSetRates() {
        double rate = 60;

        currentCurrency.setRates(currencyInRateList, rate);
        Map<Currency, Double> rates = currentCurrency.getRates();

        Map<Currency, Double> expected = new HashMap<>();
        expected.put(currencyInRateList, rate);
        assertEquals(expected, rates);
    }

}