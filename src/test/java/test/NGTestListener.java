package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class NGTestListener implements ITestListener{

	private static final Logger logger = LogManager.getLogger(NGTestListener.class);
	
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
//		logger.info(result.getTestName()+" test execution started");
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
//		logger.info(result.getTestName()+" test execution failed");
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		logger.info(context.getName()+" test execution started");
		
	}

	public void onFinish(ITestContext context) {
		logger.info(context.getName()+" test execution finished");
		
	}

}
