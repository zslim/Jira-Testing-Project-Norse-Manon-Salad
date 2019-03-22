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
    private static String[] testData = new String[3];
    //private static List<String[]> testData;
    private static final String TEST_DATA_MIN_ISSUES = "/browse_issue_min_num_of_issues.csv";

    @BeforeAll
    static void init(){
        testData = new String[]{"TOUCAN", "JETI", "COALA"};
        //testData = ExcelReader.readSheet("browse_issues");
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

    @Test
    void testIssuesDisplayed(){
        String expectedTitle = "Search";
        browseIssues.displayAllIssues();
        assertEquals(expectedTitle,browseIssues.validateIssuesDisplayed());
    }

    @ParameterizedTest
    @CsvFileSource(resources =  TEST_DATA_MIN_ISSUES, numLinesToSkip = 1)
    void testIfAllProjectsHasAtLeastThreeIssues(String projectName, int numOfProjectsRequired){
        boolean minimalNumOfIssuesExist = browseIssues.validateNumberOfIssues(projectName, numOfProjectsRequired);
        assertTrue(minimalNumOfIssuesExist);
    }

    @Test
    void testIfAllIssueDetailsAppear(){
        int numOfDetails = 5;
        String project = "TOUCAN";
        browseIssues.displayAllIssues();
        int actualDetails = (browseIssues.getDetailesOfIssue(project)).size();
        assertEquals(numOfDetails, actualDetails);
    }

    @Test
    void testPagination(){
        browseIssues.displayAllIssues();
        int expectedNumberOfIssues = browseIssues.getNumOfIssues();
        int stepByStepCount = browseIssues.getNumOfIssuesByPagination();
        assertEquals(expectedNumberOfIssues,stepByStepCount);
    }
}
