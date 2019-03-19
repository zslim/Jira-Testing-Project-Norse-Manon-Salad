package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;


public class BrowseIssuesTest {

    Login login;
    BrowseIssues browseIssues;

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
}
