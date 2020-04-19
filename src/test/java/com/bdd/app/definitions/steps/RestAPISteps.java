package com.bdd.app.definitions.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdd.app.pages.RestAPIPage;
import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

@SuppressWarnings("serial")
public class RestAPISteps extends ScenarioSteps {

	RestAPIPage restAPIPage = new RestAPIPage();
	
	private String payloadPath = "./src/test/resources/requestPayLoad/";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestAPISteps.class);

	@Step
	public void setBaseURI(String uri) {
		restAPIPage.setBaseURI(uri);
	}

	@Step
	public void resetRequest() {
		restAPIPage.resetHttpRequest();
	}
	
	@Step
	public void performGetOperation(String serviceUri) {

		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> queryParam = new HashMap<String, String>();
		Map<String, String> pathParam = new HashMap<String, String>();

		restAPIPage.getRestRequest(serviceUri, headers, queryParam, pathParam);
	}

	@Step
	public void verifyResponseCode(int code) {

		assertTrue("Response code mismatch", restAPIPage.getResponseCode() == code);
	}

	public void verifyResponseBody(DataTable dataTable) {
		// TODO Auto-generated method stub
		Map<String, String> valueToVerify = dataTable.asMap(String.class, String.class);

		List<String> listJsonPath = new ArrayList<String>(valueToVerify.keySet());

		Map<String, String> retreiveValues = restAPIPage.parseListOfJsonPathToValues(listJsonPath);

		for (String path : retreiveValues.keySet()) {
			assertEquals("Data mismatch", valueToVerify.get(path), retreiveValues.get(path));
		}

	}


	public void verifyResponseBody(String jsonValue) {
		// TODO Auto-generated method stub
		String retreiveValues = restAPIPage.parseListOfJsonPathToValues(jsonValue);
		LOGGER.info("*********deck _ id _ value******* '{}'",retreiveValues);
		Serenity.setSessionVariable(jsonValue).to(retreiveValues); //to set value to be used in session

	}


	@Step
	public void performGetOperation(String serviceUri,DataTable dataTable ) {

		Map<String, String> headers = new HashMap<String, String>();
		
		Map<String, String> queryParam = dataTable.asMap(String.class, String.class);
		
		
		Map<String, String> pathParam = new HashMap<String, String>();

		restAPIPage.getRestRequest(serviceUri, headers, queryParam, pathParam);
	}


	public void performPostOperation(String serviceUri, String path) {
		// TODO Auto-generated method stub
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> queryParam = new HashMap<String, String>();
		Map<String, String> pathParam = new HashMap<String, String>();
		
		headers.put("content-type", "application/json");
		headers.put("Accept", "application/json");
		
		String payload = fileReaderUtil(payloadPath+path);
		
		restAPIPage.postRestRequest(serviceUri,payload,headers, queryParam, pathParam);
	}

	public void performPostOperation(String serviceUri, String path, DataTable dataTable) {
		// TODO Auto-generated method stub
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> queryParam = dataTable.asMap(String.class, String.class);
		Map<String, String> pathParam = new HashMap<String, String>();

		headers.put("content-type", "application/json");
		headers.put("Accept", "application/json");

		String payload = fileReaderUtil(payloadPath+path);

		restAPIPage.postRestRequest(serviceUri,payload,headers, queryParam, pathParam);
	}

	@SuppressWarnings("deprecation")
	public String fileReaderUtil(String path) {
		FileReader fileReader;
		String payload = null;
		try {
			fileReader = new FileReader(path);
			payload = IOUtils.toString(fileReader);
			IOUtils.closeQuietly(fileReader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payload;
	}
	
	public void performGetOperationWithParameters(String serviceUri,String param, DataTable dataTable) {
		// TODO Auto-generated method stub
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> queryParam = dataTable.asMap(String.class, String.class);
		Map<String, String> pathParam = new HashMap<String, String>();
		
		LOGGER.info("'{}' value is '{}' ", param , Serenity.getCurrentSession().get(param).toString()); //get value in session
		
		String uri = serviceUri.replace(param, Serenity.getCurrentSession().get(param).toString()); //update service uri with deckid
		
		LOGGER.info("New Service uri is '{}'",uri);
		

		headers.put("content-type", "application/json");
		headers.put("Accept", "application/json");


		restAPIPage.getRestRequest(uri,headers, queryParam, pathParam);
	}

	
	
}
