package lt.vcs.vcs_project.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StringDateConversion {

    public static Date String2Date(String dateInString) {
        final List<String> formatStrings = Arrays.asList("yyyy-MM-dd", "yyyy.MM.dd", "yyyy/MM/dd",
                "dd-MM-yyyy", "dd.MM.yyyy", "dd/MM/yyyy");
        for (String formatString : formatStrings) {
            try {
                return new SimpleDateFormat(formatString).parse(dateInString);
            } catch (ParseException e) {
                System.out.print("Date has incorrect date formatting, " +
                        "should be one of the following: \"yyyy-MM-dd\", \"yyyy.MM.dd\", \"yyyy/MM/dd\",\n" +
                        " \"dd-MM-yyyy\", \"dd.MM.yyyy\", \"dd/MM/yyyy\" \n");
            }
        }
        return null;
    }
}
