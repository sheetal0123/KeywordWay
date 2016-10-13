package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestCaseReader {

	BaseClass base = new BaseClass();
	static List<TestCaseElements> lsAllCases = new ArrayList<TestCaseElements>();
	static List<TestCaseElements> lsSuiteCases = new ArrayList<TestCaseElements>();
	static List<TestCaseElements> lsPageCases = new ArrayList<TestCaseElements>();

	public void createObjects(String line) {
		String[] arr = line.split(",");

		// obj will be GC once method will finish - not sure
		TestCaseElements obj = new TestCaseElements(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8]);
		lsAllCases.add(obj);
	}

	public void readFile() throws IOException {
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
		System.out.println("Total List Size: " + lsAllCases.size());
	}

	

	/**
	 * This method will filter out cases from all list as per suite passed
	 */
	public void createSuitesList(String suiteToRun) {

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
	}

	
	
	/**
	 * This method will filter out cases from all list as per page name passed
	 */
	public void createPageList(String pageToRun) {

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
					continue;				// skip rest loop	
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
	}
	
	
	
	/**
	 * This method will call Base class methods and steps will get execute on browser
	 */
	public void runTest(List<TestCaseElements> ls) {
		System.out.println("List Size to run: " + ls.size());

		for (int i = 0; i < ls.size(); i++) {
			String actionName = ls.get(i).getActionName();

			if (actionName.equals("openBrowser")) {
				base.openUrl(ls.get(i).getActionValues());
			} else if (actionName.equals("verifyTitleContains")) {
				base.verifyTitleContains(ls.get(i).getActionValues());
			} else if (actionName.equals("enterText")) {
				base.sendKeys(ls.get(i).getLocatorName(), ls.get(i).getLocatorValues(), ls.get(i).getActionValues());
			} else if (actionName.equals("verifyUrlContains")) {
				base.verifyUrlContains(ls.get(i).getActionValues());
			} else if (actionName.equals("pressEnterKey")) {
				base.pressEnterKey(ls.get(i).getLocatorName(), ls.get(i).getLocatorValues());
			}
		}
	}

	
	public static void main(String[] args) throws IOException {
		TestCaseReader obj = new TestCaseReader();
		obj.readFile(); // create complete list
		//obj.createSuitesList("sanity"); // filter out suite test
		obj.createPageList("homepage");   // filter out page test
		
		//runner
		//obj.runTest(lsAllCases); 
		//obj.runTest(lsSuiteCases); 
		obj.runTest(lsPageCases); 
		
		
	}
}
