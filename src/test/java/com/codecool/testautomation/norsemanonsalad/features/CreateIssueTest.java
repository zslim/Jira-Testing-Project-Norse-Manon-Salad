package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateIssueTest {

    private Login login;
    private CreateIssue createIssue;
    private WebDriver driver;
    private static final String FAIL_TEST_DATA = "/create_issue_fail.csv";
    private static final String PASS_TEST_DATA = "/create_issue_pass.csv";
    private static final String PROJECT_TYPES_TEST_DATA = "/create_issue_project_with_types.csv";

    @BeforeAll
    static void init() {
        Utils.setDriverPath();
    }

    @BeforeEach
    void setUp(){
        driver = Utils.createDriver();
        login = new Login(driver);
        createIssue = new CreateIssue(driver);
        login.loginSuccessful();
        createIssue.clickCreateButton();
    }

    @AfterEach
    void tearDown() {
        login.closeDriver();
    }

    @ParameterizedTest
    @CsvFileSource(resources = PASS_TEST_DATA, numLinesToSkip = 1)
    void testSuccessfulIssueCreation(String projectName, String issueType, String summary) {
        createIssue.createNewIssue(projectName, issueType, summary);

        assertTrue(createIssue.validateSuccessfulIssueCreation());

        createIssue.deleteIssue(projectName, summary);
    }

    @ParameterizedTest
    @CsvFileSource(resources = FAIL_TEST_DATA, numLinesToSkip = 1)
    void testUnsuccessfulIssueCreationWithEmptySummary(String projectName, String issueType, String summary) {
        createIssue.createNewIssue(projectName, issueType, summary);

        assertTrue(createIssue.validateEmptySummaryError());
    }

    @ParameterizedTest
    @CsvFileSource(resources = PROJECT_TYPES_TEST_DATA, numLinesToSkip = 1)
    void testProjectHasAllRequiredTypes(String projectName) {
        List<String> expectedTypes = createIssue.getRequiredTypes();

        String actualResult = createIssue.getProjectTypes(projectName, expectedTypes);

        assertEquals(expectedTypes.toString(), actualResult);
    }

}