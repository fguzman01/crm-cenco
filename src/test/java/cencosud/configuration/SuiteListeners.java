package cencosud.configuration;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListeners implements ISuiteListener{
    public void onStart(ISuite suite) {	
		System.out.println("Suite executed onStart"  + suite.getName());
	}
	public void onFinish(ISuite suite) {

	}
    
}
