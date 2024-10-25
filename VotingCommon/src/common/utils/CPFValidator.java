package common.utils;

public class CPFValidator {
    public static boolean isValidCPF(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }
}