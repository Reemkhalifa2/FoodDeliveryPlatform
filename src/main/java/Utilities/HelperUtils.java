package Utilities;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class HelperUtils {
    public static Boolean isNull(Object obj){
        return obj == null;
    }
    public static Boolean isNull(List<String> list){
        return list.isEmpty() ;
    }

    public static Boolean isNull(String str){
        return str == null || str.isEmpty() ;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isNotNull(String str) {
        return str != null && !str.trim().isEmpty();
    }

    //String Validation Methods
    public static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }
    public static boolean isValidString(String str, int minLength) {
        return str != null && str.length() >= minLength;
    }
    public static boolean isValidString(String str, int minLength, int maxLength) {
        return str != null && str.length() >= minLength && str.length() <= maxLength;
    }

    public static boolean isValidString(String str, String regex) {
        return str != null && str.matches(regex);
    }



    // Date Validation Method
    public static Boolean isValidDate(Date date){
        return date != null;
    }

    public static Boolean isValidDate(String dateStr){
        if (dateStr == null || dateStr.isEmpty()) {
            return false;
        }

        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static Boolean isValidDate(Date date, Date minDate, Date maxDate){
        return  date.after(minDate) && date.before(maxDate) ;
    }

    public static Boolean isFutureDate(Date date){
        Date today = new Date();
        return date.after(today);
    }

    public static Boolean isPastDate(Date date){
        Date today = new Date();
        return date.before(today);
    }
    public static Boolean isPastDate(LocalDate date, LocalDate newDate){
        return newDate.isBefore(date);
    }
    public static Boolean isToday(Date date){
        Date today = new Date();
        return date.equals(today);
    }

    //Numeric Validation Methods
    public static Boolean isValidNumber(int num, int min, int max){
        return num >= min && num <= max;
    }

    public static Boolean isValidNumber(double num, double min, double max){
        return num >= min && num <= max;
    }

    public static Boolean isPositive(int num) {
        return num > 0;
    }
    public static Boolean isPositive(double num) {
        return num > 0;
    }

    public static Boolean isNegative(double num) {
        return num < 0;
    }
    public static Boolean isNegative(int num) {
        return num < 0;
    }

    //Input Validation Methods
    public static boolean isValidAge(int age) {
        return age >= 0 && age <= 120;
    }

    public static boolean isValidAge(LocalDate dateOfBirth) {

        if (dateOfBirth == null) {
            return false;
        }

        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();

        return age >= 0 && age <= 120;
    }
}
