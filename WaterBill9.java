import java.io.*;
import java.util.*;
import java.util.Scanner;

public class WaterBill9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> bills = new ArrayList<>();
        double monthTotal = 0;
        int customer = 0;

        while (true) {
            double m3 = readDouble(sc, "\nConsumption in m3 (-1 to finish): ");
            if (m3 == -1) break;
            if (m3 < 0) { System.out.println("Consumption cannot be negative."); continue; }
            customer++;

            // Tiered charge
            double tier1, tier2 = 0, tier3 = 0, tier4 = 0;
            if (m3 <= 6) tier1 = m3 * 50;
            else {
                tier1 = 6 * 50;
                if (m3 <= 16) tier2 = (m3 - 6) * 80;
                else {
                    tier2 = 10 * 80;
                    if (m3 <= 36) tier3 = (m3 - 16) * 120;
                    else { tier3 = 20 * 120; tier4 = (m3 - 36) * 150; }
                }
            }
            double water = tier1 + tier2 + tier3 + tier4;
            double service = 200, sewer = 0.75 * water;
            double total = water + service + sewer;

            String bill = String.format(
                "==============================%n  NAIROBI WATER - BILL #%d%n==============================%n" +
                "Consumption:        %10.2f m3%n  Tier 1 (1-6):     %10.2f%n  Tier 2 (7-16):    %10.2f%n" +
                "  Tier 3 (17-36):   %10.2f%n  Tier 4 (36+):     %10.2f%n" +
                "Water charge:       %10.2f%nService charge:     %10.2f%nSewerage (75%%):    %10.2f%n" +
                "------------------------------%nTOTAL (KSh):       %10.2f%n==============================",
                customer, m3, tier1, tier2, tier3, tier4, water, service, sewer, total);
            System.out.println(bill);
            bills.add(bill);
            monthTotal += total;
        }
        System.out.printf("%nCustomers billed: %d   Total billed: KSh %.2f%n", customer, monthTotal);
        try (PrintWriter pw = new PrintWriter(new FileWriter("water_bills.txt"))) {
            for (String b : bills) pw.println(b);
            System.out.println("(Bills saved to water_bills.txt)");
        } catch (IOException e) { System.out.println("Could not save file."); }
    }
    static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Invalid. Enter a number."); }
        }
    }
}
