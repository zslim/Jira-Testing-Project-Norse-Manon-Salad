package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.ExcelReader;
import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class BrowseIssuesTest {

    Login login;
    BrowseIssues browseIssues;
    private ExcelReader reader;
    private static String[] testData = new String[3];

    @BeforeAll
    static void init(){
        testData = new String[]{"TOUCAN", "JETI", "COALA"};
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
    void displayedIssues(){
        String expectedTitle = "Search";
        browseIssues.displayAllIssues();
        assertEquals(expectedTitle,browseIssues.validateIssuesDisplayed());
    }

    @Test
    void testIfAllProjectsHas3Issues(){
        Map<String, Integer> actual = browseIssues.getNumOfIssuesPerProject(testData);
        System.out.println(Arrays.toString(actual.entrySet().toArray()));
    }
}
