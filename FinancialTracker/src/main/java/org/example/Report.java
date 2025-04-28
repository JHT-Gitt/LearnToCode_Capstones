package org.example;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Report {
    public static String file = "transasctions.csv";
   public static ArrayList<Entries> entry = Tracker.showEntry(file);

    public static Scanner scanner = new Scanner(System.in);

public static void report() {

    boolean isTrue = false;

    System.out.println("\n-------REPORT SCREEN--------   ");
    System.out.println("Search by:");
    System.out.println(" 1 - Month To Date(MTD)");
    System.out.println(" 2 - Previous Month");
    System.out.println(" 3 - Year To Date(YTD)");
    System.out.println(" 4 - Previous Year");
    System.out.println(" 5 - Search by Vendor");
    System.out.println(" 0 - Back to Ledger Screen");
    System.out.println("----------------------------");

    while (!isTrue) {
        try {
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    Tracker.ledger();
                    isTrue = true;
                    break;
                case 1:
                    monthToDate();
                    break;
                case 2:
                    prevMonth();
                    break;
                case 3:
                    yearToDate();
                    break;
                case 4:
                    prevYear();
                    break;
                case 5:
                    searchVendor();
                    break;
                default:
                    System.out.println("\nInvalid choice ! Choose only between 0 and 5");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid Input");
            scanner.nextLine();
        }
    }
}
    public static void monthToDate() {
        LocalDate now = LocalDate.now();
        System.out.println("\n-------------------MONTH TO DATE ENTRIES------------------------");
        System.out.println("  Date     -   Time   -   Description     -   Vendor   -  Amount  ");
        for (Entries entries : entry) {
            LocalDate dateCompare = LocalDate.parse(entries.getDate());
            if (dateCompare.getMonth().equals(now.getMonth()) && dateCompare.getYear() == now.getYear()) {
                System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n", entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
            }
        }
        System.out.println("----------------------------------------------------------------");
        report();
    }
    public static void prevMonth() {
        System.out.println("heyprevmonth");
        report();
    }

    public static void yearToDate() {

        LocalDate now = LocalDate.now();

        System.out.println("\n---------------------YEAR TO DATE ENTRIES-----------------------");
        System.out.println("  Date     -   Time   -   Description     -   Vendor   -  Amount  ");
        for (Entries entries : entry) {
            LocalDate dateCompare = LocalDate.parse(entries.getDate());
            if (dateCompare.getYear() == now.getYear()) {
                System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n", entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
            }
        }
        System.out.println("----------------------------------------------------------------");
        report();
    }

    public static void prevYear() {
        System.out.println("hey prevyear");
        report();
    }

    public static void searchVendor() {
        System.out.println("hey vendor");
        report();
    }


}
