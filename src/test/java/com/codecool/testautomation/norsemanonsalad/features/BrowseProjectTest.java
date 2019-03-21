package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.ExcelReader;
import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BrowseProjectTest {

    private static List<String[]> testData;
    private BrowseProject browseProject;

    @BeforeAll
    static void init() {
        testData = ExcelReader.readSheet("browse_project");
        Utils.setDriverPath();
    }

    @BeforeEach
    void setUp() {
        browseProject = new BrowseProject(Utils.createDriver());
    }

    @AfterEach
    void tearDown() {
        browseProject.closeDriver();
    }

    @Disabled
    @Test
    void navigateToProjectsDirectly() {
        browseProject.navigateToProjectsDirectly();
        boolean isProjectHeaderPresent = browseProject.validateNavigateToProjects();
        assertTrue(isProjectHeaderPresent);
    }

    @Disabled
    @Test
    void navigateToProjectsUsingMenu() {
        browseProject.navigateToProjectsUsingMenu();
        boolean isProjectHeaderPresent = browseProject.validateNavigateToProjects();
        assertTrue(isProjectHeaderPresent);
    }
    @Disabled
    @Test
    void checkProjectIsPresent() {
        List<Boolean> actualResults = new ArrayList<>();
        browseProject.navigateToProjectsDirectly();
        for (String[] row :
                testData) {
            browseProject.searchProject(row[0]);
            actualResults.add(browseProject.validateTestProjectsPresent(row[1]));
            browseProject.emptySearchField();
        }
        assertTrue(!actualResults.contains(false));
    }

    @Test
    void categoryFilter(){
        List<Boolean> actualResults = new ArrayList<>();
        browseProject.navigateToProjectsDirectly();
        for (String[] row :
                testData) {
            actualResults.add(browseProject.validateCategoryFilter(row[0]));
        }
        assertTrue(!actualResults.contains(false));
    }
}