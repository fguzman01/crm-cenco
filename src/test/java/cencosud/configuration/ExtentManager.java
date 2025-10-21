package cencosud.configuration;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static  boolean STATUS_TESTS = false;
    static Timestamp ts = new Timestamp(System.currentTimeMillis());
    //	static Date date = new Date(ts.getTime());
    static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-hhmm");
    static String dateString = format.format(new Date(ts.getTime()));


    private static ExtentReports extent;
    // Refactor file name contains name suite
    private static String reportFileName = "Test_1-Automaton-Report" +dateString+ ".html";
    private static String fileSeperator = System.getProperty("file.separator");
    private static String reportFilepath = System.getProperty("user.dir") + fileSeperator + "test-output";
    private static String reportFileLocation = reportFilepath + fileSeperator + reportFileName;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    // Create an extent report instance
    public static ExtentReports createInstance() {
        String fileName = getReportPath(reportFilepath);

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileName);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        // Set environment details
        extent.setSystemInfo("OS", "Windows");
        extent.setSystemInfo("AUT", "QA");

        return extent;
    }

    // Create the report path
    private static String getReportPath(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!");
                return reportFileLocation;
            } else {
                System.out.println("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
        return reportFileLocation;
    }

    /*
    public static void getStatusAndIdList() throws IOException {

        ArrayList<String> resultados = new ArrayList<>();
        //ExtentReports extent = getInstance();
        //System.out.println("Test ID: "+extent.getClass().getResource("testID")+"Status: "+extent.getClass().getResource("testStatus"));
        int numero_fallos = 0;
        int numero_pass = 0;
        int totalTest = 0;
        String reporte = getReportPath(reportFilepath);
        String cadena;
        FileReader f = new FileReader(reporte);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            if (cadena.contains("<span class='test-name'>Comprar")) {
                totalTest = totalTest + 1;
            }
            if (cadena.contains("<span class='test-status right fail'>")) {
                numero_fallos = numero_fallos + 1;
            }
            if (cadena.contains("<span class='test-status right pass'>")) {
                numero_pass = numero_pass + 1;
            }
        }
        b.close();
	
        float porcentaje_exito = (numero_pass * 100) / totalTest;
        float porcentaje_fallos = (numero_fallos * 100) / totalTest;
        if (porcentaje_exito >= 90) {
            STATUS_TESTS = true;
        } else {
            STATUS_TESTS = false;
        }
        //resultados.add("% DE EXITO "+porcentaje_exito);
        GenerarInformeOutput.agregarResultados(String.valueOf(STATUS_TESTS));
        System.out.print("% EXITO "+porcentaje_exito);
        System.out.print("% FALLOS "+porcentaje_fallos);


    }*/
}

