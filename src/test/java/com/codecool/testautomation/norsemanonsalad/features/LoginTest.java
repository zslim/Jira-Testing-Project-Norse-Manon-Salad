package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private Login login;
    private static final String FAIL_TEST_DATA_SOURCE = "/login_fail.csv";

    @BeforeAll
    static void init() {
        Utils.setDriverPath();
    }

    @BeforeEach
    void setUp() {
        login = new Login(Utils.createDriver());
    }

    @AfterEach
    void tearDown() {
        login.closeDriver();
    }

    @ParameterizedTest
    @CsvFileSource(resources = FAIL_TEST_DATA_SOURCE, numLinesToSkip = 1)
    void loginFail(String username, String password, String errorId) {
        login.login(username, password);
        boolean errormessagePresent = login.validateErrorMessage(errorId);
        assertTrue(errormessagePresent);
    }

    @Test
    void loginSuccessful() {
        String expectedResult = Utils.getEnvironmentVar("USER_NAME");
        login.login(login.USER_NAME, login.PASSWORD);
        assertEquals(expectedResult, login.validateSuccessfulLogin());
    }
}