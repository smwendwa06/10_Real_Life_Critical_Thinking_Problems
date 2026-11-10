import java.io.*;
import java.util.*;
import java.util.Scanner;

/** Problem 6: Boda Boda Ride Fare Calculator */
public class BodaFare6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double dayTotal = 0;
        int rides = 0;
        ArrayList<String> log = new ArrayList<>();

        while (true) {
            double km = readDouble(sc, "\nDistance in km (0 to finish): ", 0);
            if (km == 0) break;
            int hour = readInt(sc, "Hour of day (0-23): ", 0, 23);
            boolean rain = readYesNo(sc, "Is it raining? (yes/no): ");

            double base = 50;
            double distanceCharge = km > 2 ? (km - 2) * 20 : 0;
            double subtotal = base + distanceCharge;

            double timeSurcharge = 0; String reason = "none";
            if (hour >= 22 || hour < 6) { timeSurcharge = subtotal * 0.50; reason = "night 50%"; }
            else if ((hour >= 7 && hour < 9) || (hour >= 17 && hour < 19)) { timeSurcharge = subtotal * 0.25; reason = "rush hour 25%"; }
            double rainCharge = rain ? 30 : 0;

            double total = subtotal + timeSurcharge + rainCharge;
            double rounded = Math.round(total / 10.0) * 10; // nearest KSh 10

            System.out.println("--- Fare breakdown ---");
            System.out.printf("Base fare (first 2 km): %.2f%n", base);
            System.out.printf("Distance charge:        %.2f%n", distanceCharge);
            System.out.printf("Time surcharge (%s): %.2f%n", reason, timeSurcharge);
            System.out.printf("Rain surcharge:         %.2f%n", rainCharge);
            System.out.printf("Exact total:            %.2f%n", total);
            System.out.printf("TOTAL (rounded to 10):  KSh %.0f%n", rounded);

            dayTotal += rounded; rides++;
            log.add("Ride " + rides + ": " + km + " km, hour " + hour + ", rain=" + rain + ", fare=" + rounded);
        }
        System.out.println("\nRides today: " + rides + "   Total earnings: KSh " + dayTotal);
        try (PrintWriter pw = new PrintWriter(new FileWriter("boda_rides.txt"))) {
            for (String l : log) pw.println(l);
            pw.println("Total earnings: " + dayTotal);
            System.out.println("(Saved to boda_rides.txt)");
        } catch (IOException e) { System.out.println("Could not save file."); }
    }
    static boolean readYesNo(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("yes")) return true;
            if (s.equals("no")) return false;
            System.out.println("Type yes or no.");
        }
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
            System.out.println("Invalid (negative distances are not allowed). Enter a number >= " + min + ".");
        }
    }
}
