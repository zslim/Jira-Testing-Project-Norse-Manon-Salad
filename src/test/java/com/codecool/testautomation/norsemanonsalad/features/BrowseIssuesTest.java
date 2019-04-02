package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;


import static org.junit.jupiter.api.Assertions.*;


public class BrowseIssuesTest {

    Login login;
    BrowseIssues browseIssues;
    private static final String TEST_DATA_MIN_ISSUES = "/browse_issue_min_num_of_issues.csv";
    private static final String TEST_DATA_PROJECT_DETAILS = "/browse_issue_details_of_project.csv";

    @BeforeAll
    static void init(){
        Utils.setDriverPath();
    }

    @BeforeEach
    void setUp() {
        WebDriver driver = Utils.createDriver();
        login = new Login(driver);
        browseIssues = new BrowseIssues(driver);
        login.loginSuccessful();
    }

    @AfterEach
    void tearDown() {
        login.closeDriver();
    }

    @Disabled
    @Test
    void testIssuesDisplayed(){
        String expectedTitle = "Search";
        browseIssues.displayAllIssues();
        assertEquals(expectedTitle,browseIssues.validateIssuesDisplayed());
    }

    @Disabled
    @ParameterizedTest
    @CsvFileSource(resources =  TEST_DATA_MIN_ISSUES, numLinesToSkip = 1)
    void testIfAllProjectsHasAtLeastThreeIssues(String projectName, int numOfProjectsRequired){
        boolean minimalNumOfIssuesExist = browseIssues.validateNumberOfIssues(projectName, numOfProjectsRequired);
        assertTrue(minimalNumOfIssuesExist);
    }

    @Disabled
    @ParameterizedTest
    @CsvFileSource(resources = TEST_DATA_PROJECT_DETAILS, numLinesToSkip = 1)
    void testIfAllIssueDetailsAppear(String projectName){
        String[] expectedDetails = {"Type:", "Status:", "Priority:", "Resolution:", "Labels:"};
        browseIssues.seeDetailsOfIssue(projectName);
        String[] actualDetails = browseIssues.validateIssueDetail();
        assertArrayEquals(expectedDetails, actualDetails);
    }

    @Disabled //stale element exception to be caught
    @Test
    void testPagination(){
        browseIssues.displayAllIssues();
        int summarizedIssuesNum = browseIssues.getNumOfIssues();
        int actualNumOfIssuesByPagination = browseIssues.getNumOfIssuesByPagination();
        assertEquals(summarizedIssuesNum,actualNumOfIssuesByPagination);
    }
}
