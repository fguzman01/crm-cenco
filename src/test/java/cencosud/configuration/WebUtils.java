package cencosud.configuration;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WebUtils extends BaseUtils {

    public WebDriver driver = null;
    protected static AppiumDriver driverApp = null;
    public JavascriptExecutor js;
    public long timeOutInSeconds = 120;
    public Map<String, Object> vars;
    public String mensaje = "";

    public WebUtils() {
        printf(ANSI_YELLOW + "Web WebDriver created.\n");
    }

    public WebUtils(WebDriver driverObject) {
        driver = driverObject;
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }


    /**
     * Get a unified timeout parameter for implicit waits, page loads and
     * javascripts.
     */
    public long GetTimeouts() {
        return this.timeOutInSeconds;
    }

    /**
     * Establish a unified timeout parameter for implicit waits, page loads and
     * javascripts.
     *
     * @param timeOutInSeconds
     */
    public void SetTimeouts(long timeOutInSeconds) {
        this.timeOutInSeconds = timeOutInSeconds;
        try {
            driver.manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS); // Timeouts de waitFor*
            driver.manage().timeouts().setScriptTimeout(timeOutInSeconds, TimeUnit.SECONDS); // Timeout de ejecución javascript
            driver.manage().timeouts().pageLoadTimeout(timeOutInSeconds, TimeUnit.SECONDS); // Timeout de espera de página
        } catch (Exception e) {
            printf(ANSI_YELLOW + "SetTimeouts in %d seconds. WARNING can not set pageLoadTimeout/setScriptTimeout.\n", timeOutInSeconds);
        }
        //printf( ANSI_YELLOW+"SetTimeouts in %d seconds.\n", timeOutInSeconds);
    }


    public boolean WaitForElementVisible(By locator) {
        printf(ANSI_YELLOW + "Waiting for element %s...", locator.toString());
        long currentTimeout = GetTimeouts();
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            new WebDriverWait(driver, timeOutInSeconds).ignoring(StaleElementReferenceException.class)
                    .ignoring(WebDriverException.class).until(ExpectedConditions.visibilityOfElementLocated(locator));
            printf(ANSI_YELLOW + "visible!!!\n");
            SetTimeouts(currentTimeout);
            return true;
        } catch (Exception e) {
            SetTimeouts(currentTimeout);
            printf(ANSI_RED + "failed!!! %s\n", e.getMessage());
            throw new WebDriverException(e.getMessage());
        }

    }

    public void ClearText (By locator){

        printf(ANSI_YELLOW + "ClearTextBox %s...", locator);
        long currentTimeout = GetTimeouts();
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            new WebDriverWait(driver, timeOutInSeconds)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(WebDriverException.class)
                    .until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).clear();
            SetTimeouts(currentTimeout);
            printf(ANSI_YELLOW + "done.\n", locator);
        } catch (Exception e) {
            SetTimeouts(currentTimeout);
            printf(ANSI_RED + "failed!!! %s\n", e.getMessage());
            throw new WebDriverException(e.getMessage());
        }

    }

    /**
     * Waits for an element to be visible and not fail if not found on timeout, instead it will return true or false.
     * @param locator
     * @param timeout max time to wait for element to be visible.
     * @return true or false depending on finding the element in timeout seconds.
     */
    public boolean WaitForElementVisible(By locator, long timeout) {
        printf( ANSI_YELLOW+"Waiting for element %s on %d seconds...", locator.toString(), timeout);
        long currentTimeout = GetTimeouts();
        try {
            driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
            new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class)
                    .ignoring(WebDriverException.class).until(ExpectedConditions.visibilityOfElementLocated(locator));
            printf( ANSI_YELLOW+"visible!!!\n");
            SetTimeouts(currentTimeout);
            return true;
        } catch (Exception e) {
            SetTimeouts(currentTimeout);
            printf( ANSI_RED+"no visible!!! %s\n", e.getMessage());
            mensaje = e.getMessage();
            return false;
        }
    }

    public String retornaMensaje (){
        return mensaje;
    }

    /**
     * Waits for an element to be visible and fail if not found on timeOutInSeconds.
     * @param webElement
     */
    public void WaitForElementVisible(WebElement webElement) {
        printf( ANSI_YELLOW+"Waiting for element %s...", webElement.toString());
        long currentTimeout = GetTimeouts();
        try {
            driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
            new WebDriverWait(driver, timeOutInSeconds).ignoring(StaleElementReferenceException.class)
                    .ignoring(WebDriverException.class).until(ExpectedConditions.visibilityOf(webElement));
            printf( ANSI_YELLOW+"visible!!!\n");
            SetTimeouts(currentTimeout);
        } catch (Exception e) {
            SetTimeouts(currentTimeout);
            printf( ANSI_RED+"failed!!! %s\n", e.getMessage());
            throw new WebDriverException( e.getMessage());
        }
    }

    public void WaitForElementVisible(MobileElement mobileElement) {
        printf( ANSI_YELLOW+"Waiting for element %s...", mobileElement.toString());
        long currentTimeout = GetTimeouts();
        try {
            driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
            new WebDriverWait(driver, timeOutInSeconds).ignoring(StaleElementReferenceException.class)
                    .ignoring(WebDriverException.class).until(ExpectedConditions.visibilityOf(mobileElement));
            printf( ANSI_YELLOW+"visible!!!\n");
            SetTimeouts(currentTimeout);
        } catch (Exception e) {
            SetTimeouts(currentTimeout);
            printf( ANSI_RED+"failed!!! %s\n", e.getMessage());
            throw new WebDriverException( e.getMessage());
        }
    }


    public void SendKeys(By locator, String keys) {
        printf(ANSI_YELLOW + "SendKeys %s to object %s...", keys, locator);
        long currentTimeout = GetTimeouts();
        //HighLightElement((By) driverApp.findElement(locator));
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            new WebDriverWait(driver, timeOutInSeconds).ignoring(StaleElementReferenceException.class)
                    .ignoring(WebDriverException.class).until(ExpectedConditions.visibilityOfElementLocated(locator));
            driver.findElement(locator).sendKeys(keys);
            SetTimeouts(currentTimeout);
            println("done!");
        } catch (Exception e) {
            SetTimeouts(currentTimeout);
            printf(ANSI_RED + "failed! %s\n", e.getMessage());
            throw new WebDriverException(e.getMessage());
        }
    }

    /**
     * Waits for an element tobe clickable located using By and click in it.
     *
     * @param locator
     */

    public void Click(By locator) {
        printf(ANSI_YELLOW + "Clicking %s...", locator);
        long currentTimeout = GetTimeouts();
        try {
            // HighLightElement( locator );
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            new WebDriverWait(driver, timeOutInSeconds)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(WebDriverException.class)
                    .until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).click();
            SetTimeouts(currentTimeout);
            printf(ANSI_YELLOW + "done.\n", locator);
        } catch (Exception e) {
            SetTimeouts(currentTimeout);
            printf(ANSI_RED + "failed!!! %s\n", e.getMessage());
            throw new WebDriverException(e.getMessage());
        }
    }

    /**
     * Swipe in Screen
     */

    /**
     * Find elements in DOM using By locator.  When debug mode is ON highlights the element to help visual debug.
     * @param by
     * @return WebElement
     */
    public List<WebElement> FindElements(By by) {
        printf( ANSI_YELLOW+"Finding elements %s...", by);
        try {
            List <WebElement> elements = driver.findElements(by);
            //elements.forEach(element -> { HighLightElement(element);} ); // Highlights on debug mode.
            printf( ANSI_YELLOW+"found %d elements.\n", elements.size());
            return elements;
        } catch (Exception e) {
            printf( ANSI_RED+"failed!!! %s\n", e.getMessage());
            throw new WebDriverException( e.getMessage());
        }
    }


    public void swipe( WebElement Panel) throws InterruptedException{
        Dimension size = driver.manage().window().getSize();
        System.out.println(size);
        Dimension dimension = Panel.getSize();

        int Anchor = Panel.getSize().getHeight()/2;

        Double ScreenWidthStart = dimension.getWidth() * 0.8;
        int scrollStart = ScreenWidthStart.intValue();

        Double ScreenWidthEnd = dimension.getWidth() * 0.2;
        int scrollEnd = ScreenWidthEnd.intValue();

        new TouchAction((PerformsTouchActions) driver)
                .press(PointOption.point(scrollStart, Anchor))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(scrollEnd, Anchor))
                .release().perform();
        Thread.sleep(1000);

    }


    /**
     * Get Text of an element located by.
     *
     * @param by
     */
    public String GetText(By by) {
        printf( ANSI_YELLOW+"GetText from %s...", by);
        long currentTimeout = GetTimeouts();
        try {
            //HighLightElement( by );
            driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
            String text = new WebDriverWait(driver, timeOutInSeconds)
                    .until(ExpectedConditions.presenceOfElementLocated(by))
                    .getText();
            printf( ANSI_YELLOW+"done [%s].\n", text, by);
            SetTimeouts(currentTimeout);
            return text;
        } catch (Exception e) {
            SetTimeouts(currentTimeout);
            printf( ANSI_RED+"failed!!!\n", e.getMessage());
            throw new WebDriverException( e.getMessage());
        }
    }

    /**
     * Close current webdriver session and collects possible garbage.
     * @throws Exception
     */
    public void CloseWebDriver() throws Exception {
        //Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        //String browserName = cap.getBrowserName().toLowerCase();
        try {
            if (driver != null) {
                driver.quit();
                driver = null;
                println(ANSI_YELLOW + "Markito is destroyed.");
            } else println(ANSI_YELLOW + "Markito is null.  Please check your teardown process.");
        } catch (Exception e) {
            println(ANSI_YELLOW + "ERROR: There was a problem closing Markito webdriver.");
            throw new Exception(e.getMessage());
        }
    }

    public void swipeElementAndroid(MobileElement el, Direction dir) {
        System.out.println("swipeElementAndroid(): dir: '" + dir + "'"); // always log your actions

        // Animation default time:
        //  - Android: 300 ms
        //  - iOS: 200 ms
        // final value depends on your app and could be greater
        final int ANIMATION_TIME = 200; // ms

        final int PRESS_TIME = 200; // ms

        int edgeBorder;
        PointOption pointOptionStart, pointOptionEnd;

        // init screen variables
        Rectangle rect = el.getRect();
        // sometimes it is needed to configure edgeBorders
        // you can also improve borders to have vertical/horizontal
        // or left/right/up/down border variables
        edgeBorder = 0;

        switch (dir) {
            case DOWN: // from up to down
                pointOptionStart = PointOption.point(rect.x + rect.width / 2,
                        rect.y + edgeBorder);
                pointOptionEnd = PointOption.point(rect.x + rect.width / 2,
                        rect.y + rect.height - edgeBorder);
                break;
            case UP: // from down to up
                pointOptionStart = PointOption.point(rect.x + rect.width / 2,
                        rect.y + rect.height - edgeBorder);
                pointOptionEnd = PointOption.point(rect.x + rect.width / 2,
                        rect.y + edgeBorder);
                break;
            case LEFT: // from right to left
                pointOptionStart = PointOption.point(rect.x + rect.width - edgeBorder,
                        rect.y + rect.height / 2);
                pointOptionEnd = PointOption.point(rect.x + edgeBorder,
                        rect.y + rect.height / 2);
                break;
            case RIGHT: // from left to right
                pointOptionStart = PointOption.point(rect.x + edgeBorder,
                        rect.y + rect.height / 2);
                pointOptionEnd = PointOption.point(rect.x + rect.width - edgeBorder,
                        rect.y + rect.height / 2);
                break;
            default:
                throw new IllegalArgumentException("swipeElementAndroid(): dir: '" + dir + "' NOT supported");
        }

        // execute swipe using TouchAction
        try {
            new TouchAction(driverApp)
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeElementAndroid(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

    /**
     * Highlights WebElement if debug mode is ON.
     *
     * @param element
     */
    public void HighLightElement(WebElement element) {
        if (debug)
            ExecuteJsScript("arguments[0].setAttribute('style', 'background: yellow; border: 3px solid blue;');", element);
    }

    /**
     * Execute a JavaScript script.
     * Please refer to "https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/remote/server/handler/ExecuteScript.html"
     *
     * @param script
     * @param args
     */
    public void ExecuteJsScript(String script, java.lang.Object... args) {
        js.executeScript(script, args);
    }

    /**
     * Capture Screenshot and post on Allure Report
     * @param nameScreenshot
     * @throws IOException
     */
    public void screenShotAllure(String nameScreenshot) throws IOException{
        try {
            File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Allure.addAttachment(nameScreenshot, FileUtils.openInputStream(screenShot));
        }catch (Exception e){
            System.out.println("No se pudo sacar Screenshot");
            System.out.println("Causa: " + e.getCause().toString() + " \n" + "StackTrace: " + e.fillInStackTrace().toString());
        }
    }

    public void screenShotAllureMobile(String nameScreenshot) throws IOException{
        try {
            File screenShot = ((TakesScreenshot) driverApp).getScreenshotAs(OutputType.FILE);
            Allure.addAttachment(nameScreenshot, FileUtils.openInputStream(screenShot));
        }catch (Exception e){
            System.out.println("No se pudo sacar Screenshot");
            System.out.println("Causa: " + e.getCause().toString() + " \n" + "StackTrace: " + e.fillInStackTrace().toString());
        }
    }


    public void CloseAppium (){
        BaseUtils.killProcessIfRunning("Appium.exe");
        BaseUtils.killProcessIfRunning("adb.exe");
    }


    }
