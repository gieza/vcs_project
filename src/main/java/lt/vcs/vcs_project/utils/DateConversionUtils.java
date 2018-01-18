package lt.vcs.vcs_project.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateConversionUtils {

    static final List<String> formatStrings = Arrays.asList("yyyy-MM-dd", "yyyy.MM.dd", "yyyy/MM/dd", "dd.MM.yyyy",
            "dd/MM/yyyy");


    public static LocalDate parseLocalDate(String dateString) {
        for (String formatString : formatStrings) {
            try {
                return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(formatString));

            } catch (DateTimeParseException e) {
                //System.out.println(e.getStackTrace());
            }
        }

        return null;
    }
}
