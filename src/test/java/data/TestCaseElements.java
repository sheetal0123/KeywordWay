package data;

/**
 * This class will create objects for each row from sheet
 *
 */
public class TestCaseElements {

	String testName, testSteps, actionName, actionValues, locatorName, locatorValues, pageName, suitesName, run;

	public TestCaseElements(String testName, String testSteps, String actionName, String actionValues,
			String locatorName, String locatorValues, String pageName, String suitesName,String run) {
		this.testName = testName;
		this.testSteps = testSteps;
		this.actionName = actionName;
		this.actionValues = actionValues;
		this.locatorName = locatorName;
		this.locatorValues = locatorValues;
		this.pageName = pageName;
		this.suitesName = suitesName;
		this.run = run;
	}

	public String getTestName() {
		return testName;
	}

	public String getTestSteps() {
		return testSteps;
	}

	public String getActionName() {
		return actionName;
	}

	public String getActionValues() {
		return actionValues;
	}

	public String getLocatorName() {
		return locatorName;
	}

	public String getLocatorValues() {
		return locatorValues;
	}

	public String getPageName() {
		return pageName;
	}

	public String getSuitesName() {
		return suitesName;
	}
	
	public String getRun() {
		return run;
	}

}
