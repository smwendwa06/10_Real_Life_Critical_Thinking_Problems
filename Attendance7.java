import java.util.*;
import java.util.Scanner;

/** Problem 7: Class Attendance and Exam Eligibility System */
public class Attendance7 {
    static final int STUDENTS = 10, LECTURES = 20;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] att = new int[STUDENTS][LECTURES];

        int mode = readInt(sc, "1 = random attendance, 2 = enter manually: ", 1, 2);
        Random rnd = new Random();
        for (int s = 0; s < STUDENTS; s++) {
            for (int l = 0; l < LECTURES; l++) {
                if (mode == 1) att[s][l] = rnd.nextInt(100) < 80 ? 1 : 0; // ~80% chance present
                else att[s][l] = readInt(sc, "Student " + (s + 1) + ", lecture " + (l + 1) + " (1=present, 0=absent): ", 0, 1);
            }
        }

        System.out.println("\nID  Attended  Percent  Status");
        int eligible = 0, ineligible = 0;
        for (int s = 0; s < STUDENTS; s++) {
            int attended = 0;
            for (int l = 0; l < LECTURES; l++) attended += att[s][l];
            double pct = attended * 100.0 / LECTURES;
            String status;
            if (pct >= 75) { status = "ELIGIBLE"; eligible++; } else { status = "NOT ELIGIBLE"; ineligible++; }
            System.out.printf("%2d  %8d  %6.1f%%  %s%n", s + 1, attended, pct, status);
        }
        System.out.println("\nEligible: " + eligible + "   Ineligible: " + ineligible);

        // Extra: lecture with lowest attendance (column totals)
        int worstLecture = 0, worstCount = STUDENTS + 1;
        for (int l = 0; l < LECTURES; l++) {
            int present = 0;
            for (int s = 0; s < STUDENTS; s++) present += att[s][l];
            if (present < worstCount) { worstCount = present; worstLecture = l; }
        }
        System.out.println("Lowest attendance: lecture " + (worstLecture + 1) + " (" + worstCount + "/" + STUDENTS + " present)");
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
