package com.codecool.testautomation.NorseManonSalad.features;

import com.codecool.testautomation.NorseManonSalad.testutils.ExcelReader;
import com.codecool.testautomation.NorseManonSalad.testutils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private static List<String[]> testData;
    private ExcelReader reader;
    private Login login;

    @BeforeAll
    static void init() {
        testData = ExcelReader.readSheet("login_failed");
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

    @Test
    void loginFail() {
        List<Boolean> actualResults = new ArrayList<>();
        for (String[] row :
                testData) {
            login.login(row[0], row[1]);
            actualResults.add(login.validateErrorMessage(row[2]));
        }
        assertTrue(!actualResults.contains(false));
    }

    @Test
    void loginSuccessful() {
        String expectedResult = System.getenv("PROFILE_PICTURE_TEXT");
        login.login(login.USER_NAME, login.PASSWORD);
        assertEquals(expectedResult, login.validateSuccessfulLogin());
    }
}