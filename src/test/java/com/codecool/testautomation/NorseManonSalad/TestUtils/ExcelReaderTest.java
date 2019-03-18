package com.codecool.testautomation.NorseManonSalad.TestUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelReaderTest {

    ExcelReader reader;

    @BeforeEach
    void setup() {
        reader = new ExcelReader();
    }

    @Test
    void readSheet() {
        List<String[]> expectedResult = new ArrayList<>();
        String[] firstRow = {"user10", "CCPass123", "User profile for User 10"};
        String[] secondRow = {"", "", "usernameerror"};
        String[] thirdRow = {"example", "Example123", "usernameerror"};
        expectedResult.add(firstRow);
        expectedResult.add(secondRow);
        expectedResult.add(thirdRow);

        List<String[]> actualResult = reader.readSheet("login");

        String[][] arrayExpected = expectedResult.toArray(new String[0][]);
        String[][] arrayActual = actualResult.toArray(new String[0][]);

        assertTrue(Arrays.deepEquals(arrayExpected, arrayActual));
    }
}