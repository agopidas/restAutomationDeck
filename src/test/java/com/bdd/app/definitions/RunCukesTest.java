package com.bdd.app.definitions;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;


/**
 * Cucumber runner class
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(plugin = { "pretty", "html:target/cucumber-html-report", "json:target/cucumber-report.json",
		"junit:target_junit/cucumber.xml" }, tags = {
				"@restapi" }, features = "src/test/resources/features", glue = "com.bdd.app.definitions")

public class RunCukesTest {

}
