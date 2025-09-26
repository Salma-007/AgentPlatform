package controller;

public class Validator {
    public static void validEmail(String email) throws Exception {
        if (email == null || !email.contains("@")) {
            throw new Exception("Email invalide !");
        }
    }

    public static void validId(Integer id, String fieldName) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception(fieldName + " entrez un Id valide !");
        }
    }

    public static void notEmpty(String value, String fieldName) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            throw new Exception(fieldName + " ne doit pas être vide !");
        }
    }

}
