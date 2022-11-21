import models.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {
    static Scanner scanner;
    static Application application;


    @AfterEach
    void tearDown() throws NoSuchFieldException, IllegalAccessException {
        Wallet.getWalletList().clear();
        Field field = Wallet.class.getDeclaredField("idCount");
        field.setAccessible(true);
        field.set(null, 1);
    }

    @Test
    void testChooseDefinedCurrency() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String forScan = "\n" +
                "dollar";

        application = new Application();
        application.scanner = new Scanner(forScan);
        application.wallet = new Wallet("Elon");
        application.wallet.addCurrency("ruble");
        application.wallet.addCurrency("dollar");
        Method method = Application.class.getDeclaredMethod("chooseCurrency", String.class);
        method.setAccessible(true);
        String actualCurrencyName = (String) method.invoke(application, "anything");
        String expectedCurrencyName = "dollar";

        assertEquals(expectedCurrencyName, actualCurrencyName);
    }

    @Test
    void testChooseDefaultCurrency() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String forScan = "\n" +
                "\n";

        application = new Application();
        application.scanner = new Scanner(forScan);
        application.wallet = new Wallet("Elon");
        application.wallet.addCurrency("ruble");
        application.wallet.addCurrency("dollar");
        Method method = Application.class.getDeclaredMethod("chooseCurrency", String.class);
        method.setAccessible(true);
        String actualCurrencyName = (String) method.invoke(application, "anything");
        String expectedCurrencyName = "";

        assertEquals(expectedCurrencyName, actualCurrencyName);
    }

    @Test
    void testInitNewApplication() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        Field field = Main.class.getDeclaredField("scanner");
        field.setAccessible(true);
        field.set(null, new Scanner(System.in));
        String forScan = "Jeff\n 111";
        scanner = new Scanner(forScan);
        application = new Application(scanner);

        String expected = "\n" + "WalletId: 1\n" +
                "userName: 'Jeff'\n" +
                "currenciesAmountMap: {}";
        String actual = Wallet.getWalletList().get(0).toString();
        assertEquals(expected, actual);
    }

    @Test
    void testCreateNewWallet() {
        String walletUserName = "Donald";
        String actual = Application.createNewWallet(walletUserName).toString();
        String expected = "\n" + "WalletId: 1\n" +
                "userName: 'Donald'\n" +
                "currenciesAmountMap: {}";
        assertEquals(expected, actual);
    }

    @Test
    void testEnterExistingWallet() {
        String firstWalletUserName = "Donald";
        String secondWalletUserName = "Jeff";
        Application.createNewWallet(firstWalletUserName);
        Application.createNewWallet(secondWalletUserName);

        int actualId = Application.createNewWallet("Donald").getWalletId();
        int expectedId = 1;

        assertEquals(expectedId, actualId);
    }
}