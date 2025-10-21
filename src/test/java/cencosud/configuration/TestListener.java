package cencosud.configuration;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Attachment;
/**
 * Esta clase implementa la lógica de reportes usando Allure.
 */
public class TestListener extends BaseUtils implements ITestListener {

    public void onStart(ITestContext context) {
        println(ANSI_WHITE+("*** Test Suite " + context.getName() + " empezando... ***"));
    }
    @Override
    public void onFinish(ITestContext context) {
        println(ANSI_WHITE+("*** Test Suite " + context.getName() + " terminando ***"));
    }
    @Override
    public void onTestStart(ITestResult result) {
        println(ANSI_WHITE+("*** Ejecutando método " + result.getMethod().getMethodName()) );
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        println(ANSI_GREEN+"*");
        println(ANSI_GREEN+"** "+result.getMethod().getMethodName() + " ejecutada exitosamente...*");
        println(ANSI_GREEN+"*");
    }
    @Override
    public void onTestFailure(ITestResult result) {
        println(ANSI_RED+"*");
        println(ANSI_RED+"** "+ result.getMethod().getMethodName() + "ERROR:"+result.getThrowable().getMessage()+" *");
        println(ANSI_RED+"*");
        //Object testClass = result.getInstance();
        try {
            //WebDriver driver = ((eomNegocio)testClass).driver;
            saveScreenshotPNGwithRobot();
            saveTextLog(result.getMethod().getMethodName()+" fallado.");
            saveTextLog(result.getThrowable().getMessage());
        } catch (Exception e) {
            saveTextLog(result.getMethod().getMethodName()+" fallado y no se pudo obtener imagen.  Causa:"+e.getMessage());
            saveTextLog(result.getThrowable().getMessage());
        }
		/*Object testClass = result.getInstance();
		WebDriver driver = ((eomNegocio)testClass).driver;
		try {
			CloseWebDriver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
    }
    @Override
    public void onTestSkipped(ITestResult result) {
        println(ANSI_PURPLE+"*");
        println(ANSI_PURPLE+"** Prueba " + result.getMethod().getMethodName() + " no considerada(skipped)... Razón:"+result.getSkipCausedBy());
        println(ANSI_PURPLE+"*");
    }
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
    }
    @Attachment(value="Page screenshot", type="image/png")
    public byte[] saveScreenshotPNGWithWebDriver(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    @Attachment(value="{0}", type="text/plain")
    public static String saveTextLog(String message){
        return message;
    }
    @Attachment(value = "Page Screenshot", type = "image/png")
    public static byte[] saveScreenshotPNGwithRobot() throws AWTException{
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage bufferedImage = new Robot().createScreenCapture(screenRect);

        byte[] image = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", bos);
            image = bos.toByteArray();
        } catch (Exception e) { }

        return image;
    }
}
