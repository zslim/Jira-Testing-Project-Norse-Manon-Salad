package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.ExcelReader;
import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;


import static org.junit.jupiter.api.Assertions.*;


public class BrowseIssuesTest {

    Login login;
    BrowseIssues browseIssues;
    private ExcelReader reader;
    private static String[] testData = new String[3];
    //private static List<String[]> testData;

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


    @Test
    void testIfAllProjectsHasAtLeastThreeIssues(){
        int minimalNumOfIssuesRequired = 3;
        boolean minimalNumOfIssuesExist = browseIssues.getNumOfIssuesPerProject(testData, minimalNumOfIssuesRequired);
        assertTrue(minimalNumOfIssuesExist);
    }


    @Test
    void testIfAllIssueDetailsAppear(){
        int numOfDetails = 5;
        browseIssues.displayAllIssues();
        int actualDetails = (browseIssues.getDetailesOfIssue("TOUCAN")).size();
        assertEquals(numOfDetails, actualDetails);
    }
}
