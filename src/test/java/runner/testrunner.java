package runner;

import org.testng.annotations.DataProvider;

import parameters.excelreader;
import stepdefinitions.hooks;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "stepdefinitions",
    plugin = {"pretty", "html:reports/cucumber-html-report.html"}
)
public class testrunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        // Read Excel data once
        String[][] excelData = excelreader.readData();
        hooks.excelData = excelData;   // ✅ make available globally

        Object[][] cucumberScenarios = super.scenarios();

        // Expand scenarios per Excel row
        Object[][] finalScenarios = new Object[excelData.length * cucumberScenarios.length][];
        int index = 0;

        for (int i = 0; i < excelData.length; i++) {
            hooks.currentrow = i;   // ✅ track row index
            for (int j = 0; j < cucumberScenarios.length; j++) {
                finalScenarios[index++] = cucumberScenarios[j];
            }
        }

        return finalScenarios;
    }
}
