package utils;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static helpers.DataFakerHelper.getFaker;
import static helpers.DataFakerHelper.getRandomNumber;

public class DataGenerateUtil {
    private static final List<String> TITLES = Arrays.asList("Mr.", "Mrs.");

    @Test
    public void testData(){
        System.out.println(getProgrammingLanguage());
        LogUtils.info(getProgrammingLanguage());
    }

    public static String getProgrammingLanguage(){
        return getFaker().programmingLanguage().name();
    }

    public static String getTitle(){
        return TITLES.get(getRandomNumber(0, TITLES.size()));
    }

    public static String getName(){
        return getFaker().name().fullName();
    }

    public static String getEmail(){
        return getFaker().internet().emailAddress();
    }


}
