package com.nassreml.crm.user;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    private final static long USERNAME_MIN_SIZE = 6;
    private final static long USERNAME_MAX_SIZE = 15;
    private final static long PASSWORD_MIN_SIZE = 6;
    private final static long PASSWORD_MAX_SIZE = 30;

    public UserUtils() {}

    public static List<String> validateUser(final User user){
        List<String> errors = new ArrayList<>();

        final String username = user.getUsername();
        final String password = user.getPassword();

        validateUsername(errors, username);

        validatePassword(errors, password);

        return errors;
    }

    private static void validatePassword(List<String> errors, String password) {
        if(isNull(password) || isBlank(password) || !isSizeInRange(password, PASSWORD_MIN_SIZE, PASSWORD_MAX_SIZE)){
            errors.add("Password must be between " + PASSWORD_MIN_SIZE + " and " + PASSWORD_MAX_SIZE + " characters.");
        }
    }

    private static void validateUsername(List<String> errors, final String username) {
        if(isNull(username) || isBlank(username) || !isSizeInRange(username, USERNAME_MIN_SIZE, USERNAME_MAX_SIZE)){
            errors.add("Username must be between " + USERNAME_MIN_SIZE + " and " + USERNAME_MAX_SIZE + " characters.");
        }
    }

    private static boolean isNull(final String str){
        return str == null;
    }

    private static boolean isBlank(final String str){
        return str.trim().length() == 0;
    }

    private static boolean isSizeInRange(final String str, final long min, final long max){
        return str.length() >= min && str.length() <= max;
    }
}
