package lt.vcs.vcs_project.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DateConversionUtilsTest {
    private static String wrongDate = "dffadfdafgf";
    private static String DateWithDash = "2018-01-15";
    private static String DateWithDot = "2018.01.15";
    private static String DateWithDotUK = "15.01.2018";

    @Test
    public void givenDate_in_wrong_format_then_null_is_returned() {
        assertNull("Wrong date string is passed, and null is returned",
                DateConversionUtils.parseLocalDate(wrongDate));
    }

    @Test
    public void givenDate_in_dash_format_then_date_is_parsed() {
        assertEquals("Given 2018-01-15 date string is passed for parsing, " +
                        "same toString()  is returned", DateWithDash,
                DateConversionUtils.parseLocalDate(DateWithDash).toString());
    }

    @Test
    public void givenDate_in_dot_format_then_date_is_parsed() {
        assertEquals("Given 2018.01.15 date string is passed for parsing, " +
                        " toString() returns 2018-01-15", DateWithDash,
                DateConversionUtils.parseLocalDate(DateWithDot).toString());
    }

    @Test
    public void givenDate_in_UK_dot_format_then_date_is_parsed() {
        assertEquals("Given 15.01.2018 date string is passed for parsing, " +
                        " toString() returns 2018-01-15", DateWithDash,
                DateConversionUtils.parseLocalDate(DateWithDotUK).toString());
    }

}