package com.codecool.testautomation.norsemanonsalad.testutils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelReaderTest {

    @Test
    void readSheet() {
        List<String[]> expectedResult = new ArrayList<>();
        String[] secondRow = {"", "", "usernameerror"};
        String[] thirdRow = {"example", "Example123", "usernameerror"};
        expectedResult.add(secondRow);
        expectedResult.add(thirdRow);

        List<String[]> actualResult = ExcelReader.readSheet("login_failed");

        String[][] arrayExpected = expectedResult.toArray(new String[0][]);
        String[][] arrayActual = actualResult.toArray(new String[0][]);

        assertTrue(Arrays.deepEquals(arrayExpected, arrayActual));
    }
}