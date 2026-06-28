package com.example.FoodDeliveryPlatformDemo.utilities;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.*;

public class HelperUtils {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // Parse String → Date
    public static Date parse(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            return FORMAT.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd. Got: " + dateStr);
        }
    }

    // Format Date → String
    public static String format(Date date) {
        if (date == null) return null;
        return FORMAT.format(date);
    }

    // Set time to start of day  00:00:00
    public static Date startOfDay(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE,      0);
        cal.set(Calendar.SECOND,      0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // Set time to end of day  23:59:59
    public static Date endOfDay(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE,      59);
        cal.set(Calendar.SECOND,      59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    // Check from is before to
    public static boolean isValidRange(Date from, Date to) {
        if (from == null || to == null) return true;
        return from.before(to);
    }
    public static Double deductedAmount(Double price , Integer quantity) {
        return price * quantity;
    }

    // Distance in kilometers using Haversine Formula
    public static double calculateDistance(double lat1, double lng1,
                                    double lat2, double lng2) {

        final int EARTH_RADIUS = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);

        double a =
                Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                        + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lngDistance / 2)
                        * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    // subtotal + fee
    public static double calculateTotal(double subtotal, double fee) {
        return subtotal + fee;
    }

    // subtotal + fee - discount
    public static double calculateTotal(double subtotal,
                                 double fee,
                                 double discount) {
        return subtotal + fee - discount;
    }

    // Default currency format
    public static String formatCurrency(double amount) {
        NumberFormat formatter =
                NumberFormat.getCurrencyInstance(Locale.US);

        return formatter.format(amount);
    }

    // Currency with specific code
    public static String formatCurrency(double amount,
                                 String currencyCode) {

        return String.format("%.2f %s",
                amount,
                currencyCode.toUpperCase());
    }
    public static Integer subtract(Integer a , Integer b){
        return a  - b;
    }
    public static Double subtract(Double a , Double b){
        return a  - b;
    }



    // Check if current time is within business hours
    public static boolean isBusinessOpen(String openTime,
                                  String closeTime) {

        LocalTime now = LocalTime.now();

        LocalTime open = LocalTime.parse(openTime);
        LocalTime close = LocalTime.parse(closeTime);

        return !now.isBefore(open) && !now.isAfter(close);
    }
    public static UUID generateId(){
        return UUID.randomUUID();
    }

    public static String generateId(String prefix){
        return prefix+UUID.randomUUID();
    }

    public static String generateId(String prefix, int length){
        return prefix + UUID.randomUUID().toString().substring(0,length);
    }

    public static String generateId(String prefix, String suffix){
        return prefix + UUID.randomUUID() + suffix;
    }
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
