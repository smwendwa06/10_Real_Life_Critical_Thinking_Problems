import java.io.*;
import java.util.*;
import java.util.Scanner;

/** Problem 8: Airtime Reseller Inventory System */
public class AirtimeInventory8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] denom = {10, 20, 50, 100, 250, 500};  // parallel arrays:
        int[] qty   = {20, 20, 15, 10, 8, 6};       // qty[i] belongs to denom[i]
        ArrayList<String> sales = new ArrayList<>();
        int choice;

        do {
            System.out.println("\n=== Airtime Inventory (Thika) ===");
            System.out.println("1. Sell  2. Restock  3. View inventory  4. Exit");
            choice = readInt(sc, "Choice: ", 1, 4);
            switch (choice) {
                case 1: case 2:
                    int d = readInt(sc, "Denomination (10/20/50/100/250/500): ", 1, 500);
                    int idx = -1;
                    for (int i = 0; i < denom.length; i++) if (denom[i] == d) idx = i;
                    if (idx == -1) { System.out.println("That denomination does not exist."); break; }
                    int q = readInt(sc, "Quantity: ", 1, 100000);
                    if (choice == 1) {
                        if (q > qty[idx]) { System.out.println("Not enough stock! Only " + qty[idx] + " available."); break; }
                        qty[idx] -= q;
                        sales.add("SOLD " + q + " x " + d + " = KSh " + (q * d));
                        System.out.println("Sold " + q + " x KSh " + d);
                    } else {
                        qty[idx] += q;
                        System.out.println("Restocked " + q + " x KSh " + d);
                    }
                    if (qty[idx] < 5) System.out.println("WARNING: KSh " + d + " airtime is low (" + qty[idx] + " left)!");
                    break;
                case 3:
                    showInventory(denom, qty);
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    break;
            }
        } while (choice != 4);

        showInventory(denom, qty);
        long value = 0;
        for (int i = 0; i < denom.length; i++) value += (long) denom[i] * qty[i];
        System.out.println("Total value of remaining stock: KSh " + value);
        System.out.println("Sales today: " + sales.size());
        for (String s : sales) System.out.println("  " + s);
        try (PrintWriter pw = new PrintWriter(new FileWriter("airtime_inventory.txt"))) {
            for (int i = 0; i < denom.length; i++) pw.println(denom[i] + "," + qty[i]);
            System.out.println("(Inventory saved to airtime_inventory.txt)");
        } catch (IOException e) { System.out.println("Could not save file."); }
    }
    static void showInventory(int[] denom, int[] qty) {
        System.out.println("Denomination | Quantity");
        for (int i = 0; i < denom.length; i++) System.out.printf("%12d | %d%s%n", denom[i], qty[i], qty[i] < 5 ? "  (LOW)" : "");
    }
    static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try { int v = Integer.parseInt(sc.nextLine().trim()); if (v >= min && v <= max) return v; }
            catch (NumberFormatException e) { }
            System.out.println("Invalid. Enter a whole number from " + min + " to " + max + ".");
        }
    }
}
