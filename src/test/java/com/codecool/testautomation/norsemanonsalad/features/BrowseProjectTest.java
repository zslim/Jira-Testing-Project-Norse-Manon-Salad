package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class BrowseProjectTest {

    private BrowseProject browseProject;
    private static final String PROJECTS_DATA = "/browse_project_projects.csv";
    private static final String CATEGORY_DATA = "/browse_categories.csv";

    @BeforeAll
    static void init() {
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

    @Test
    void navigateToProjectsDirectly() {
        browseProject.navigateToProjectsDirectly();
        boolean isProjectHeaderPresent = browseProject.validateNavigateToProjects();
        assertTrue(isProjectHeaderPresent);
    }

    @Test
    void navigateToProjectsUsingMenu() {
        browseProject.navigateToProjectsUsingMenu();
        boolean isProjectHeaderPresent = browseProject.validateNavigateToProjects();
        assertTrue(isProjectHeaderPresent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PROJECTS_DATA, numLinesToSkip = 1)
    void checkProjectIsPresent(String projectName, String exceptedTitle) {
        browseProject.navigateToProjectsDirectly();
        browseProject.emptySearchField();
        browseProject.searchProject(projectName);
        assertTrue(browseProject.validateTestProjectsPresent(exceptedTitle));
    }

    @ParameterizedTest
    @CsvFileSource(resources = CATEGORY_DATA, numLinesToSkip = 1)
    void categoryFilter(String category, int numOfProjects) {
        browseProject.navigateToProjectsDirectly();
        browseProject.chooseOneCategory(category);
        assertTrue(browseProject.validateCategoryFilter(numOfProjects));
    }
}