import java.util.*;

public class TemperatureMonitor5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double[] temps = new double[24];
        // Handles early discharge: user says how many readings exist (1-24)
        int count = readInt(sc, "How many hourly readings do you have (1-24)? ", 1, 24);
        for (int i = 0; i < count; i++) temps[i] = readDouble(sc, "Hour " + i + " temperature (C): ", 30, 45);

        double max = temps[0], min = temps[0], sum = 0;
        for (int i = 0; i < count; i++) {
            if (temps[i] > max) max = temps[i];
            if (temps[i] < min) min = temps[i];
            sum += temps[i];
        }
        System.out.printf("%nHighest: %.1f C%nLowest: %.1f C%nAverage: %.2f C%n", max, min, sum / count);

        String fever = "", hypo = "";
        for (int i = 0; i < count; i++) {
            if (temps[i] > 37.5) fever += i + " ";
            else if (temps[i] < 36.0) hypo += i + " ";
        }
        System.out.println("Fever hours (>37.5): " + (fever.isEmpty() ? "none" : fever));
        System.out.println("Hypothermia hours (<36.0): " + (hypo.isEmpty() ? "none" : hypo));
        // Stable = all readings within 0.5 C of each other, i.e. (max - min) <= 0.5
        System.out.println("Stable: " + ((max - min) <= 0.5 ? "YES" : "NO"));

        // Text "chart": one # per 0.1 C above 35 C
        System.out.println("\nTemperature chart (each # = 0.1 C above 35.0):");
        for (int i = 0; i < count; i++) {
            int bars = (int) Math.round((temps[i] - 35.0) * 10);
            String line = "";
            for (int b = 0; b < bars; b++) line += "#";
            System.out.printf("%2d | %s %.1f%n", i, line, temps[i]);
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
    static double readDouble(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try { double v = Double.parseDouble(sc.nextLine().trim()); if (v >= min && v <= max) return v; }
            catch (NumberFormatException e) { }
            System.out.println("Invalid. Enter a realistic temperature (" + min + " to " + max + ").");
        }
    }
}
