package cencosud.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaseUtils {

    private final static String TASKLIST = "tasklist";
    private static String KILL = "taskkill /F /IM ";
    public final static String IE_EXE = "iexplore.exe";
    public final static String IE_DRIVER_EXE = "IEDriverServer.exe";
    public final static String CHROME_EXE = "chrome.exe";
    public final static String CHROMEDRIVER_EXE = "chromedriver.exe";
    public final static String EDGE_EXE = "MicrosoftEdge.exe";
    public final static String EDGEDRIVER_EXE = "MsEdgeDriver.exe";
    public final static String FIREFOX_EXE = "firefox.exe";
    public final static String FIREFOXDRIVER_EXE = "geckodriver.exe";
    private static String OS = System.getProperty("os.name").toLowerCase();
    public static boolean debug = true;
    // ANSI colors tobe used in println and printf.
    public final static String ANSI_RESET = "\u001B[0m";
    public final static String ANSI_BLACK = "\u001B[30m";
    public final static String ANSI_RED = "\u001B[31m";
    public final static String ANSI_GREEN = "\u001B[32m";
    public final static String ANSI_YELLOW = "\u001B[33m";
    public final static String ANSI_BLUE = "\u001B[34m";
    public final static String ANSI_PURPLE = "\u001B[35m";
    public final static String ANSI_CYAN = "\u001B[36m";
    public final static String ANSI_WHITE = "\u001B[37m";
    public final static String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public final static String ANSI_RED_BACKGROUND = "\u001B[41m";
    public final static String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public final static String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public final static String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public final static String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public final static String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public final static String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    /**
     * Prints an string using format string to console when debug mode is ON.
     */
    public static void printf(String format, Object... args) {
        if (debug)
            System.out.printf(format, args);
    }

    /**
     * Prints an string to console when debug mode is ON.
     */
    public static void println(String x) {
        if (debug)
            System.out.println(x);
    }

    /**
     * @param processName
     */
    public static void killProcessIfRunning(String processName) {
        System.out.println("Init Killing Process");
        //System.out.println("El OS Name es: " + OS);
        //System.out.println(processName);
        if (OS.contains("indow")) {
            OS = "win";
        }
        switch (OS) {
            case "win":
                System.out.println("Trying to kill process: " + processName);
                try {
                    if (isProcessRunning(processName)) {
                        Runtime.getRuntime().exec("taskkill /F /IM " + processName + " /T");
                    }
                } catch (IOException ex) {
                    System.out.println("Error on kill process " + processName + ": " + ex.getMessage());
                }
        }
        System.out.println("Exit Killing Process");

    }

    public static boolean isProcessRunning(String processName) { if(OS.contains("indow")){OS = "win";} switch (OS) { case "win": Process process; try { process = Runtime.getRuntime().exec(TASKLIST); } catch (IOException ex) { System.out.println("Error on get runtime" + ex.getMessage()); return false; } String line; try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));) { while ((line = reader.readLine()) != null) { if (line.contains(processName)) { System.out.println("Process found"); return true; } } } catch (IOException ex) { System.out.println("Error on check for process " + processName + ": " + ex.getMessage()); } return false; } return false; }

}
