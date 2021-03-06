package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class TestCaseReader {

	BaseClass base = new BaseClass();
	static List<TestCaseElements> lsAllCases = new ArrayList<TestCaseElements>();
	static List<TestCaseElements> lsSuiteCases = new ArrayList<TestCaseElements>();
	static List<TestCaseElements> lsPageCases = new ArrayList<TestCaseElements>();

	/**
	 * This method will call "TestCaseElements.java" constructor and will create objects
	 * Created objected will be added in a List
	 */
	public void createObjects(String line) {
		String[] arr = line.split(",");

		TestCaseElements obj = new TestCaseElements(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9]);
		lsAllCases.add(obj);
	}

	/**
	 * This method will read csv, line by line and call "createObjects()" method
	 */
	public List<TestCaseElements> getAllObjectList() throws IOException {
		File classpath = new File(System.getProperty("user.dir"));
		String filepath = classpath + "/src/test/resources/datafiles/testcases.csv";
		System.out.println(filepath);
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String line = null;
		int counter = 0;
		while ((line = br.readLine()) != null) {
			if (counter != 0) {
				createObjects(line);
			}
			counter++;
		}
		br.close();
		System.out.println("Total List Size: " + lsAllCases.size());
		
		return lsAllCases;
	}

	

	/**
	 * This method will filter out cases from all list as per suite passed
	 * @throws IOException 
	 */
	public List<TestCaseElements> getSuitesList(String suiteToRun) throws IOException {

		//lsAllCases = getAllObjectList();
		getAllObjectList();
		
		boolean correctSuiteStep = false;
		boolean suiteStep = false;
		
		// create a custom list
		for (int i = 0; i < lsAllCases.size(); i++) {
			String suiteText = lsAllCases.get(i).getSuitesName();

			// check whether current row is a suiteText step or normal step
			if (!suiteText.equals("na")) {
				suiteStep = true;

				if (suiteText.equals(suiteToRun)) {
					correctSuiteStep = true;
					lsSuiteCases.add(lsAllCases.get(i));  // add head
					continue;				// skip rest loop	
				}else{
					correctSuiteStep = false;
					continue; // if wrong suite skip loop
				}
			}else{
				// child
				if(correctSuiteStep){
					lsSuiteCases.add(lsAllCases.get(i));  // add child
					continue;
				}else{
					// do nothing
				}
			}
		}
		
		return lsSuiteCases;
	}

	
	
	/**
	 * This method will filter out cases from all list as per page name passed
	 * @throws IOException 
	 */
	public List<TestCaseElements> getPageList(String pageToRun) throws IOException {

		getAllObjectList();
		
		boolean correctSuiteStep = false;
		boolean suiteStep = false;
		
		// create a custom list
		for (int i = 0; i < lsAllCases.size(); i++) {
			String suiteText = lsAllCases.get(i).getPageName();

			// check whether current row is a suiteText step or normal step
			if (!suiteText.equals("na")) {
				suiteStep = true;

				if (suiteText.equals(pageToRun)) {
					correctSuiteStep = true;
					lsPageCases.add(lsAllCases.get(i));  // add head
					continue;// skip rest loop	
				}else{
					correctSuiteStep = false;
					continue; // if wrong suite skip loop
				}
			}else{
				// child
				if(correctSuiteStep){
					lsPageCases.add(lsAllCases.get(i));  // add child
					continue;
				}else{
					// do nothing
				}
			}
		}
		
		return lsPageCases;
	}
	
	
	
	/**
	 * This method will call Base class methods and steps will get execute on browser
	 * @throws IOException 
	 */
	public void runTest(List<TestCaseElements> ls) throws IOException {
		
		Map<String,String> summaryReport = new LinkedHashMap<>();
		
		if(ls.size() == 0){
			ls = getAllObjectList();
		}
		
		System.out.println("List Size to run: " + ls.size());
		//boolean isActualTestCaseName = false;
		
		for (int i = 0; i < ls.size(); i++) {
			String testcaseName =ls.get(i).getTestName();
			String actionName = ls.get(i).getActionName();
			String actionValue = ls.get(i).getActionValues();
			String locatorName = ls.get(i).getLocatorName();
			String locatorVal = ls.get(i).getLocatorValues();
			String testStep = ls.get(i).getTestSteps(); 
			
			boolean testStepResult = false;
			boolean isTestStepIsAVerificationStep = false;
			String result = null;
			
			switch(actionName){
				case "openBrowser": base.openUrl(actionValue);break;
				case "enterText": base.sendKeys(base.getWebElement(locatorName, locatorVal),actionValue);break;
				case "pressEnterKey" : base.pressEnterKey(base.getWebElement(locatorName, locatorVal));break;
				case "verifyTitleContains" : testStepResult = base.verifyTitleContains(ls.get(i).getActionValues());break;
				case "verifyUrlContains" : testStepResult = base.verifyUrlContains(actionValue);break;
				default: throw new IllegalStateException("Given action name in csv is not matching with any of the existing action name");
			}
			
			//we will check pass/fail for steps which are start with "verify ..."
			if(testStep.startsWith("verify ")){
				isTestStepIsAVerificationStep = true;
			}
			
			
			//add result to an new list1 : testcaseName, result (if any of test step get failed)
			//add result to an new list2 : testcaseName, testSteps, result
			if(testcaseName!= "na"){
				//isActualTestCaseName = true;
				summaryReport.put(testcaseName, "NO VERIFICATION STEP");
				System.out.println("LHM: "+summaryReport);
			}
			
			
			if(isTestStepIsAVerificationStep){
				if(testStepResult){
					result="PASS";
				}else{
					result="FAIL";
				}
				
				
				//fetched last entered key-value pair and updated the value with PASS or FAIL
				Object lastTestCaseNameWithValue = summaryReport.entrySet().toArray()[summaryReport.size() -1];
				String lastTestCaseName = lastTestCaseNameWithValue.toString().split("=")[0];
				summaryReport.put(lastTestCaseName, result);
				System.out.println("Updated LHM: "+summaryReport);
			}
			
			
		}
	}

	
	public static void main(String[] args) throws IOException {
		TestCaseReader obj = new TestCaseReader();

		//different runner type
		obj.runTest(lsAllCases);     //to run all test cases
		//obj.runTest(obj.getSuitesList("critical")); 
		//obj.runTest(obj.getPageList("searchpage")); 
		
		
	}
}
