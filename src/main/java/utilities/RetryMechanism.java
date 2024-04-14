package utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

public class RetryMechanism implements IRetryAnalyzer, ITestNGListener {
	int retryCount = 0;
	int maxRetryCount = 1;
	
	@Override
	public boolean retry(ITestResult result) {
		
		if(ITestResult.FAILURE == result.getStatus()) {
			if(retryCount<maxRetryCount) {
				retryCount++;
				TextFileWriter.writeInText("Retrying TC : " + result.getName() + " Reason for failure : " + result.getThrowable().getMessage());
				
				return true;
			}
		}
		return false;
	}

}
