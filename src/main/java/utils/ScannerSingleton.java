package utils;

import java.util.Scanner;

/**
 *
 * @author Ciso
 */
public class ScannerSingleton {

    private static Scanner instance;

    private ScannerSingleton() {
        instance = null;
    }

    public static Scanner getInstance() {
        if (instance == null) {
            instance = new Scanner(System.in);
        }
        return instance;
    }

    public static void close() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }

}
