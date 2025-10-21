package cencosud.configuration;

import org.testng.Assert;


public class BaseConfig extends WebUtils {

    protected void finalize() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }


   public void assertTrue(boolean exp, String msg){
       if ( !exp )
           Assert.assertTrue(exp, msg);
   }

    public void fail(String msg){
        finalize();
        Assert.fail(msg);
    }

    public void assertEquals(String actual, String expected, String msg){
        if (!actual.equals(expected))
            finalize();
        Assert.assertEquals(actual, expected, msg);
    }

    public void assertNotEquals(int actual, int expected, String msg){
        if (actual == expected)
            finalize();
        Assert.assertNotEquals(actual, expected, msg);
    }


}
