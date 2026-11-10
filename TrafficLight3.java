// import java.util.*;
import java.util.Scanner;

/** Problem 3: Traffic Light Simulation for a Nairobi Junction */
public class TrafficLight3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean rushHour;
        while (true) {
            System.out.print("Is it rush hour? (yes/no): ");
            String ans = sc.nextLine().trim().toLowerCase();
            if (ans.equals("yes")) { rushHour = true; break; }
            if (ans.equals("no")) { rushHour = false; break; }
            System.out.println("Please type yes or no.");
        }
        int total = 0;
        while (total <= 0) {
            System.out.print("Total simulation time (seconds): ");
            try { total = Integer.parseInt(sc.nextLine().trim()); } catch (NumberFormatException e) { total = 0; }
            if (total <= 0) System.out.println("Enter a positive whole number.");
        }

        final int GREEN = rushHour ? 45 : 30, YELLOW = 5, RED = 25;
        final int CYCLE = GREEN + YELLOW + RED;
        StringBuilder sequence = new StringBuilder(); // colour sequence for analysis

        for (int t = 0; t < total; t++) {
            int pos = t % CYCLE; // modulus makes the light repeat
            String colour; int remaining;
            if (pos < GREEN) { colour = "GREEN"; remaining = GREEN - pos; sequence.append('G'); }
            else if (pos < GREEN + YELLOW) { colour = "YELLOW"; remaining = GREEN + YELLOW - pos; sequence.append('Y'); }
            else { colour = "RED"; remaining = CYCLE - pos; sequence.append('R'); }
            System.out.println("t=" + (t + 1) + "s  " + colour + "  (" + remaining + "s remaining)");
        }
        System.out.println("\nCycle length: " + CYCLE + "s");
        System.out.println("Complete cycles: " + (total / CYCLE));
        System.out.println("Leftover seconds in partial cycle: " + (total % CYCLE));
        System.out.println("Sequence: " + sequence);
    }
}
