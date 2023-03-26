package ru.ilichev.webprac.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListUtil {
    public static List<String> commaSeparatedToList(String commaSeparated) {
        List<String> these = Arrays.asList(commaSeparated.split("\\s*,\\s*"));
        if (commaSeparated.isEmpty()) {
            these = Collections.emptyList();
        }
        return these;
    }
}
