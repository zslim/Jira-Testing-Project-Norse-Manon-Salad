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
    private Login logger;

    @BeforeAll
    static void init() {
        testData = ExcelReader.readSheet("browse_project");
        Utils.setDriverPath();
    }

    @BeforeEach
    void setUp() {
        logger = new Login(Utils.createDriver());
    }

    @AfterEach
    void tearDown() {
        logger.closeDriver();
    }

    @Test
    void navigateToProjectsDirectly() {
    }

    @Test
    void navigateToProjectsUsingMenu() {
    }
}