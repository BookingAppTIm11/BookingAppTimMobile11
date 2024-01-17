package clients.services;

public class Validate {

    public static boolean isValidInputWithSpaces(String input) {
        String regex = "^[a-zA-Z ]+$";
        return input.matches(regex);
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return email.matches(regex);
    }
    public static boolean isValidAlphanumericWithSpaces(String input) {
        String regex = "^[a-zA-Z0-9 ]+$";
        return input.matches(regex);
    }

    public static boolean containsOnlyNumbers(String input) {
        String regex = "^[0-9]+$";
        return input.matches(regex);
    }
}
