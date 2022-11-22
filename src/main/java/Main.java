import java.util.Scanner;

public class Main {
    protected static Application application;
    private static Scanner scanner;
    public static void main(String[] args) throws InterruptedException {
        init();
    }

    public static void init() throws InterruptedException {
        scanner = new Scanner(System.in);
        System.out.println("Starting application...");
        Thread.sleep(1000);
        application = new Application(scanner);
    }

    public static void terminate() throws InterruptedException {
        System.out.println("Terminating application...");
        application = null;
        Thread.sleep(1000);
        init();
    }

    public static void terminateAndClose() {
        System.out.println("Terminating application...");
        application = null;
        scanner.close();
    }
}
