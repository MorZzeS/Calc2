import java.util.Scanner;

public class Calc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пример: ");
        String input = scanner.nextLine();
        scanner.close();

        String result = calc(input);
        System.out.println("Результат: " + result);
    }
    public static String calc(String input) {
        try {
            String[] parts = input.split(" ");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            }
            String operand1 = parts[0];
            String operator = parts[1];
            String operand2 = parts[2];
            boolean isRoman = isRomanNumber(operand1) && isRomanNumber(operand2);
            int num1 = isRoman ? romanToArabic(operand1) : Integer.parseInt(operand1);
            int num2 = isRoman ? romanToArabic(operand2) : Integer.parseInt(operand2);
            if ((num1 < 1 || num1 > 10) || (num2 < 1 || num2 > 10)) {
                throw new IllegalArgumentException("Числа должны быть в диапазоне от 1 до 10.");
            }
            int result = switch (operator) {
                case "+" -> num1 + num2;
                case "-" -> num1 - num2;
                case "*" -> num1 * num2;
                case "/" -> num1 / num2;
                default -> throw new IllegalArgumentException("Неподдерживаемая операция: " + operator);
            };
            return isRoman ? arabicToRoman(result) : Integer.toString(result); // Возвращаем результат вычисления
        } catch (NumberFormatException e) {
            return "Ошибка: Используются одновременно разные системы счисления.";
        } catch (IllegalArgumentException | ArithmeticException e) {
            return "Ошибка: " + e.getMessage();
        }
    }
    private static boolean isRomanNumber(String s) {
        return s.matches("^[IVXLC]+$");
    }
    private static int romanToArabic(String roman) {
        int result = 0;
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            char c = roman.charAt(i);
            int value = romanDigitToValue(c);
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }
        return result;
    }
    private static String arabicToRoman(int arabic) {
        if (arabic <= 0) {
            throw new IllegalArgumentException("Результат не может быть меньше или равен нулю.");
        }
        String[] romanNumerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "C"};
        if (arabic <= 10) {
            return romanNumerals[arabic - 1];
        }
        StringBuilder roman = new StringBuilder();
        if (arabic >= 100) {
            roman.append("C");
            arabic -= 100;
        }
        if (arabic >= 90) {
            roman.append("XC");
            arabic -= 90;
        }
        if (arabic >= 50) {
            roman.append("L");
            arabic -= 50;
        }
        if (arabic >= 40) {
            roman.append("XL");
            arabic -= 40;
        }
        if (arabic >= 10) {
            roman.append("X");
            arabic -= 10;
        }
        while (arabic >= 1) {
            roman.append("I");
            arabic -= 1;
        }
        return roman.toString();
    }
    private static int romanDigitToValue(char c) {
        return switch (c) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            default -> throw new IllegalArgumentException("Недопустимая римская цифра: " + c);
        };
    }
}
