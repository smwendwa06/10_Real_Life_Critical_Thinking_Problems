// import java.util.*;
import java.util.Scanner;

/** Problem 4: Supermarket Barcode Checker (EAN-13) */
public class BarcodeChecker4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter 13-digit barcode (or 'q' to quit): ");
            String code = sc.nextLine().trim();
            if (code.equalsIgnoreCase("q")) break;

            if (code.length() != 13) { System.out.println("Invalid input: barcode must be exactly 13 digits."); continue; }
            boolean allDigits = true;
            for (int i = 0; i < code.length(); i++) if (!Character.isDigit(code.charAt(i))) allDigits = false;
            if (!allDigits) { System.out.println("Invalid input: barcode must contain digits only."); continue; }

            int sumOdd = 0, sumEven = 0;
            for (int i = 0; i < 12; i++) {
                int digit = code.charAt(i) - '0'; // char -> int
                if (i % 2 == 0) sumOdd += digit;  // 1st, 3rd, 5th... positions
                else sumEven += digit;            // 2nd, 4th, 6th... positions
            }
            int check = (10 - ((sumOdd + 3 * sumEven) % 10)) % 10;
            int last = code.charAt(12) - '0';
            System.out.println("Calculated check digit: " + check + ", barcode check digit: " + last);
            System.out.println(check == last ? "Valid Barcode" : "Invalid Barcode");
        }
    }
}
