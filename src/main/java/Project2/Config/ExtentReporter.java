package Project2.Config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {
	
	static ExtentReports extent;

	public static ExtentReports getReporterObject()	{
	String path = System.getProperty("user.dir") + "/REPORTS/index.html";
	ExtentSparkReporter reporter = new ExtentSparkReporter(path);
	reporter.config().setReportName("Saucedemo Automation Test Results");
	reporter.config().setDocumentTitle("Saucedemo Automation Test Report");
	
	extent = new ExtentReports();
	extent.attachReporter(reporter);
	extent.setSystemInfo("Automation Tester", "Serban Mora");
	return extent;
	}
}