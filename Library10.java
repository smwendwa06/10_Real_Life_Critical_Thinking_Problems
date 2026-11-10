import java.io.*;
import java.util.*;
import java.util.Scanner;

/** Problem 10: Simple Library Book Management System */
public class Library10 {
    static ArrayList<String> titles = new ArrayList<>();
    static ArrayList<String> authors = new ArrayList<>();
    static ArrayList<Boolean> available = new ArrayList<>();
    static final String FILE = "library.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        load();
        int choice;
        do {
            System.out.println("\n=== Nyeri Community Library ===");
            System.out.println("1 Add  2 Borrow  3 Return  4 Display all  5 Search  6 Exit");
            choice = readInt(sc, "Choice: ", 1, 6);
            switch (choice) {
                case 1:
                    System.out.print("Title: "); String t = sc.nextLine().trim();
                    System.out.print("Author: "); String a = sc.nextLine().trim();
                    if (t.isEmpty()) { System.out.println("Title cannot be empty."); break; }
                    if (find(t) != -1) { System.out.println("That title already exists."); break; }
                    titles.add(t); authors.add(a); available.add(true);
                    System.out.println("Added.");
                    break;
                case 2:
                    System.out.print("Title to borrow: ");
                    int i = find(sc.nextLine().trim());
                    if (i == -1) System.out.println("Book not found.");
                    else if (!available.get(i)) System.out.println("Sorry, already borrowed.");
                    else { available.set(i, false); System.out.println("Enjoy \"" + titles.get(i) + "\"!"); }
                    break;
                case 3:
                    System.out.print("Title to return: ");
                    int j = find(sc.nextLine().trim());
                    if (j == -1) System.out.println("Book not found.");
                    else if (available.get(j)) System.out.println("That book was not borrowed.");
                    else { available.set(j, true); System.out.println("Returned. Thank you!"); }
                    break;
                case 4:
                    display();
                    break;
                case 5:
                    System.out.print("Search text: ");
                    String q = sc.nextLine().trim().toLowerCase();
                    boolean any = false;
                    for (int k = 0; k < titles.size(); k++)
                        if (titles.get(k).toLowerCase().contains(q)) {
                            System.out.println(titles.get(k) + " by " + authors.get(k) + " - " + (available.get(k) ? "Available" : "Borrowed"));
                            any = true;
                        }
                    if (!any) System.out.println("No matches.");
                    break;
                case 6:
                    save();
                    System.out.println("Library saved. Goodbye!");
                    break;
            }
        } while (choice != 6);
    }

    static int find(String title) { // exact match, ignoring case
        for (int i = 0; i < titles.size(); i++) if (titles.get(i).equalsIgnoreCase(title)) return i;
        return -1;
    }
    static void display() {
        if (titles.isEmpty()) { System.out.println("No books yet."); return; }
        int avail = 0;
        for (int i = 0; i < titles.size(); i++) {
            System.out.println((i + 1) + ". " + titles.get(i) + " by " + authors.get(i) + " - " + (available.get(i) ? "Available" : "Borrowed"));
            if (available.get(i)) avail++;
        }
        System.out.println("Total: " + titles.size() + "  Available: " + avail + "  Borrowed: " + (titles.size() - avail));
    }
    static void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (int i = 0; i < titles.size(); i++) pw.println(titles.get(i) + "|" + authors.get(i) + "|" + available.get(i));
        } catch (IOException e) { System.out.println("Could not save: " + e.getMessage()); }
    }
    static void load() {
        File f = new File(FILE);
        if (!f.exists()) return;
        try (Scanner in = new Scanner(f)) {
            while (in.hasNextLine()) {
                String[] p = in.nextLine().split("\\|");
                if (p.length == 3) { titles.add(p[0]); authors.add(p[1]); available.add(Boolean.parseBoolean(p[2])); }
            }
        } catch (IOException e) { System.out.println("Could not load saved library."); }
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
