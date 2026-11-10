import java.io.*;
import java.util.*;
import java.util.Scanner;
public class MpesaFloat1 {

    static final double START_FLOAT = 50000, MIN_SAFE = 10000, MAX_SAFE = 100000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double floatBal = START_FLOAT, totalDeposits = 0, totalWithdrawals = 0;
        int deposits = 0, withdrawals = 0, declined = 0;
        ArrayList<String> history = new ArrayList<>(); // transaction history for later review

        System.out.println("=== M-Pesa Agent Float (Kawangware) ===");
        System.out.println("Opening float: KSh " + START_FLOAT);
        int n = readInt(sc, "Number of transactions: ", 0, 10000);

        for (int i = 1; i <= n; i++) {
            int type = readInt(sc, "Txn " + i + " type (1=Deposit, 2=Withdrawal): ", 1, 2);
            double amt = readDouble(sc, "Amount (KSh): ", 1);
            switch (type) {
                case 1: // customer deposits cash -> agent float increases
                    floatBal += amt; totalDeposits += amt; deposits++;
                    history.add("Txn " + i + " DEPOSIT " + amt + " -> float " + floatBal);
                    break;
                case 2: // customer withdraws -> float decreases (only if enough cash)
                    if (amt > floatBal) {
                        System.out.println("DECLINED: withdrawal exceeds available float (KSh " + floatBal + ").");
                        declined++;
                        history.add("Txn " + i + " WITHDRAWAL " + amt + " DECLINED -> float " + floatBal);
                    } else {
                        floatBal -= amt; totalWithdrawals += amt; withdrawals++;
                        history.add("Txn " + i + " WITHDRAWAL " + amt + " -> float " + floatBal);
                    }
                    break;
            }
            System.out.println("Float balance: KSh " + floatBal);
            if (floatBal < MIN_SAFE) System.out.println("WARNING: Float below KSh 10,000 - rebalance (get more cash)!");
            else if (floatBal > MAX_SAFE) System.out.println("WARNING: Float above KSh 100,000 - rebalance (bank excess cash)!");
        }

        System.out.println("\n--- Summary ---");
        System.out.println("Deposits:    " + deposits + " (KSh " + totalDeposits + ")");
        System.out.println("Withdrawals: " + withdrawals + " (KSh " + totalWithdrawals + ")");
        System.out.println("Declined:    " + declined);
        System.out.println("Final float: KSh " + floatBal);
        System.out.println("\n--- Transaction history ---");
        for (String h : history) System.out.println(h);
        saveToFile("mpesa_history.txt", history);
    }

    static void saveToFile(String name, ArrayList<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(name))) {
            for (String l : lines) pw.println(l);
            System.out.println("(History saved to " + name + ")");
        } catch (IOException e) { System.out.println("Could not save file: " + e.getMessage()); }
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