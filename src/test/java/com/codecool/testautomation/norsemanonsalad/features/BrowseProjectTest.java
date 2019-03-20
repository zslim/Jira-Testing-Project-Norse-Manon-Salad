package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.ExcelReader;
import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}