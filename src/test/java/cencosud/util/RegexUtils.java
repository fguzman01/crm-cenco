package cencosud.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import cencosud.exception.PatternNotFoundException;

public class RegexUtils {

    public static String findPattern(String pattern, String input) throws PatternNotFoundException {
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new PatternNotFoundException("Pattern not found in the provided input.");
        }
    }
    
}
