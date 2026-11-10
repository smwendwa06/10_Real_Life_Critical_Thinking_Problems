// import java.util.*;
import java.util.Scanner;
public class FeeTracker2 {
    
    static final double FEE = 45000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] names = {"Amina", "Brian", "Cynthia", "David", "Esther", "Felix", "Grace", "Hassan"};
        double[] paid = new double[8];

        System.out.println("=== Fee Payment Tracker (KSh " + FEE + " per student) ===");
        for (int i = 0; i < names.length; i++) System.out.println(i + " - " + names[i]);

        int idx;
        do {
            idx = readInt(sc, "\nStudent index (0-7) or -1 to finish: ", -1, 7);
            if (idx == -1) break;
            double amt = readDouble(sc, "Payment amount: ", 1);
            double balance = FEE - paid[idx];
            if (amt > balance) { // prevent overpayment
                System.out.println("Rejected: " + names[idx] + " only owes KSh " + balance + ".");
            } else {
                paid[idx] += amt;
                System.out.println("Recorded. " + names[idx] + " balance: KSh " + (FEE - paid[idx]));
            }
        } while (true);

        System.out.println("\n--- Fee Report ---");
        System.out.printf("%-10s %12s %12s  %s%n", "Name", "Paid", "Balance", "Status");
        String fullyPaid = "", outstanding = "";
        for (int i = 0; i < names.length; i++) {
            double bal = FEE - paid[i];
            String status;
            if (bal == 0) { status = "Fully paid"; fullyPaid += names[i] + " "; }
            else {
                status = paid[i] == 0 ? "Not paid" : "Partially paid";
                outstanding += names[i] + " (KSh " + bal + ")  ";
            }
            System.out.printf("%-10s %12.2f %12.2f  %s%n", names[i], paid[i], bal, status);
        }
        System.out.println("\nFully paid: " + (fullyPaid.isEmpty() ? "none" : fullyPaid));
        System.out.println("Outstanding: " + (outstanding.isEmpty() ? "none" : outstanding));
    }

    static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try { int v = Integer.parseInt(sc.nextLine().trim()); if (v >= min && v <= max) return v; }
            catch (NumberFormatException e) { }
            System.out.println("Invalid. Enter a whole number from " + min + " to " + max + ".");
        }
    }
    static double readDouble(Scanner sc, String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            try { double v = Double.parseDouble(sc.nextLine().trim()); if (v >= min) return v; }
            catch (NumberFormatException e) { }
            System.out.println("Invalid. Enter a number >= " + min + ".");
        }
    }
}
