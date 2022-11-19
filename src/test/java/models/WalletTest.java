package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.InstanceNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    static Wallet firstWallet;
    static Wallet secondWallet;
    static Currency ruble;
    static Currency dollar;

    @BeforeEach
    void setUp() {
        firstWallet = new Wallet("Jeff");
        secondWallet = new Wallet("Elon");
        ruble = new Currency("ruble");
        dollar = new Currency("dollar");
        firstWallet.addCurrency("ruble");
        firstWallet.addCurrency("dollar");
    }

    @Test
    void testAddThirdWalletInTheWalletList() {
        Wallet wallet = new Wallet("Donald");
        int expectedListSize = 3;
        assertEquals(expectedListSize, Wallet.getWalletList().size());
    }

    @Test
    void testWalletIdAssign() {
        Wallet wallet = new Wallet("Aristotelis");
        int expectedId = 3;
        assertEquals(expectedId, wallet.getWalletId());
    }

    @Test
    void testAddWalletWithExistingUserName() {
        AtomicReference<Wallet> wallet = null;
        assertThrows(IllegalArgumentException.class, () -> wallet.set(new Wallet("Jeff")));
    }

    @Test
    void testAddCurrencyReturnFalse() {
        secondWallet.addCurrency("ruble");
        assertFalse(secondWallet.addCurrency("ruble"));
    }

    @Test
    void testAddCurrencyReturnTrue() {
        secondWallet.addCurrency("ruble");
        assertTrue(secondWallet.addCurrency("dollar"));
    }

    @Test
    void testAddCurrency() {
        secondWallet.addCurrency("ruble");
        Map<Currency, Double> expectedCurrenciesAmountMap = new LinkedHashMap<>();
        expectedCurrenciesAmountMap.put(ruble, 0.0);
        assertEquals(expectedCurrenciesAmountMap, secondWallet.getCurrenciesAmountMap());
    }

    @Test
    void testAddWithExistingCurrencyName() {
        firstWallet.addCurrency("ruble");
        Map<Currency, Double> expectedCurrenciesAmountMap = new LinkedHashMap<>();
        expectedCurrenciesAmountMap.put(ruble, 0.0);
        expectedCurrenciesAmountMap.put(dollar, 0.0);
        assertEquals(expectedCurrenciesAmountMap, firstWallet.getCurrenciesAmountMap());
    }

    @Test
    void testPrivateMethodLookForDefaultCurrency() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Currency expectedCurrency = ruble;
        Method method = Wallet.class.getDeclaredMethod("lookForDefaultCurrency");
        method.setAccessible(true);
        Currency actualCurrency = (Currency)method.invoke(firstWallet);
        assertEquals(expectedCurrency, actualCurrency);
    }

    @Test
    void testDepositWithDefaultCurrency() {
        firstWallet.deposit(150);
        Map<Currency, Double> expectedCurrenciesAmountMap = new LinkedHashMap<>();
        expectedCurrenciesAmountMap.put(ruble, 150.0);
        expectedCurrenciesAmountMap.put(dollar, 0.0);
        assertEquals(expectedCurrenciesAmountMap, firstWallet.getCurrenciesAmountMap());
    }

    @Test
    void testDepositWithDefinedCurrency() {
        firstWallet.deposit(dollar, 100.5);
        Map<Currency, Double> expectedCurrenciesAmountMap = new LinkedHashMap<>();
        expectedCurrenciesAmountMap.put(ruble, 0.0);
        expectedCurrenciesAmountMap.put(dollar, 100.5);
        assertEquals(expectedCurrenciesAmountMap, firstWallet.getCurrenciesAmountMap());
    }


    @Test
    void testWithdrawWithDefaultCurrency() {
        firstWallet.deposit(150);
        firstWallet.deposit(dollar,100);

        firstWallet.withdraw(100);

        Map<Currency, Double> expectedCurrenciesAmountMap = new LinkedHashMap<>();
        expectedCurrenciesAmountMap.put(ruble, 50.0);
        expectedCurrenciesAmountMap.put(dollar, 100.0);
        assertEquals(expectedCurrenciesAmountMap, firstWallet.getCurrenciesAmountMap());
    }

    @Test
    void testWithdrawWithDefinedCurrency() {
        firstWallet.deposit(150);
        firstWallet.deposit(dollar,100);

        firstWallet.withdraw(dollar,50);

        Map<Currency, Double> expectedCurrenciesAmountMap = new LinkedHashMap<>();
        expectedCurrenciesAmountMap.put(ruble, 150.0);
        expectedCurrenciesAmountMap.put(dollar, 50.0);
        assertEquals(expectedCurrenciesAmountMap, firstWallet.getCurrenciesAmountMap());
    }

    @Test
    void testWithdrawAmountMoreThenExistInWallet() {
        firstWallet.deposit(ruble,150);
        firstWallet.deposit(dollar,100);
        firstWallet.withdraw(dollar, 200);
        Map<Currency, Double> expectedCurrenciesAmountMap = new LinkedHashMap<>();
        expectedCurrenciesAmountMap.put(ruble, 150.0);
        expectedCurrenciesAmountMap.put(dollar, 0.0);
        assertEquals(expectedCurrenciesAmountMap, firstWallet.getCurrenciesAmountMap());
    }

    @Test
    void testFindCurrencyByName() {
        Currency foundCurrency = firstWallet.findCurrencyByName("ruble");
        Currency expectedCurrency = ruble;
        assertEquals(expectedCurrency, foundCurrency);
    }

    @Test
    void testFindCurrencyByNameNoSuchCurrencyInTheWallet() {
        assertThrows(RuntimeException.class, () -> firstWallet.findCurrencyByName("euro"));
    }

    @Test
    void convert() {

    }

    @Test
    void calcCurrencyInOtherCurrency() {
    }

    @Test
    void showBalance() {
    }

    @Test
    void showTotal() {
    }

    @Test
    void testShowTotal() {
    }
}