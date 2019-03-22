package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class ProjectVersionsTest {

    private ProjectVersions versions;
    private static final String SUCCESS_DATA_SOURCE = "/version_add_success.csv";

    @BeforeAll
    static void init() {
        Utils.setDriverPath();
    }

    @BeforeEach
    void setUp() {
        Login login = new Login(Utils.createDriver());
        versions = new ProjectVersions(login.getDriver());
        login.loginSuccessful();
        versions.navigateToReleases();
    }

    @AfterEach
    void tearDown() {
        versions.closeDriver();
    }

    @Tag("demo")
    @ParameterizedTest
    @CsvFileSource(resources = SUCCESS_DATA_SOURCE, numLinesToSkip = 1)
    void addNewVersionSuccess(String versionName, String startDate, String releaseDate, String description, String expectedResult) {
        versions.addNewVersion(versionName, startDate, releaseDate, description);
        String actualResult = versions.validateNewVersion();
        assertEquals(expectedResult, actualResult);
        versions.deleteVersion(versionName);
    }
}