package com.bdd.app.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAPIPage extends BasePage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestAPIPage.class);

	//Configuration for Rest Assured
	private RestAssuredConfig restAssuredConfig = RestAssured.config
			.encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
	//Configuration for HTTP Request
	private RequestSpecification httpRequest = RestAssured.given().config(restAssuredConfig);
	//Response Object
	private Response response;

	

	public void setBaseURI(String baseURI) {
		httpRequest.baseUri(baseURI);
	}
	
	public void resetHttpRequest() {
		restAssuredConfig = RestAssured.config
				.encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
		//Configuration for HTTP Request
		httpRequest = RestAssured.given().config(restAssuredConfig);
	}

	public void getRestRequest(String serviceURI, Map<String, String> headers, Map<String, String> queryParameter,
			Map<String, String> pathParameters) {

		if (headers.size() != 0) {
			httpRequest.headers(headers);
		}

		if (pathParameters.size() != 0) {
			httpRequest.pathParams(pathParameters);
		}

		if (queryParameter.size() != 0) {
			httpRequest.queryParams(queryParameter);
		}

		response = httpRequest.relaxedHTTPSValidation().when().get(serviceURI);

	}

	public int getResponseCode() {
		String body = response.getBody().asString();
		LOGGER.info("Response body is '{}'",body);
		int code = response.getStatusCode();
		LOGGER.info("Response code is '{}'",code);
		return code;
	}

	public String getResponseBody() {
		String body = response.getBody().asString();
		LOGGER.info("Response body is '{}'",body);
		return body;
	}

	public void postRestRequest(String serviceURI, String payload, Map<String, String> headers,
			Map<String, String> queryParameter, Map<String, String> pathParameters) {

		httpRequest.headers(headers);
		httpRequest.pathParams(pathParameters);
		httpRequest.queryParams(queryParameter);

		response = httpRequest.relaxedHTTPSValidation().when().body(payload).post(serviceURI);

	}
	
	public Map<String,String> parseListOfJsonPathToValues(List<String> lsJsonPath) {
		getResponseBody();
		Map<String,String> hmJsonPathValue = new HashMap<String,String>();
		
		for(String path :lsJsonPath) {
			hmJsonPathValue.put(path, response.jsonPath().getString(path));
		}
		
		return hmJsonPathValue;
	}


	public String parseListOfJsonPathToValues(String lsJsonValue) {
		getResponseBody();
		String jsonPathValue = response.jsonPath().getString(lsJsonValue);
		return jsonPathValue;
	}

}
