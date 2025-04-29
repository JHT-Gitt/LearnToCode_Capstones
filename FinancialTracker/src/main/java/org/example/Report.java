package org.example;
import org.w3c.dom.CDATASection;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Report {
    //public static String file = "transactions.csv";
   public static ArrayList<Entries> entry = Tracker.showEntry("transaction.csv");

    public static Scanner scanner = new Scanner(System.in);

public static void report() {

    boolean isTrue = false;

    System.out.println("\n-------REPORTS SCREEN--------   ");
    System.out.println("Search by:");
    System.out.println(" 1 - Month To Date(MTD)");
    System.out.println(" 2 - Previous Month");
    System.out.println(" 3 - Year To Date(YTD)");
    System.out.println(" 4 - Previous Year");
    System.out.println(" 5 - Search by Vendor");
    System.out.println(" 6 - Custom Search");
    System.out.println(" 0 - Back to Ledger Screen");
    System.out.println("-----------------------------");

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
                case 6:
                    customSearch();
                    break;
                default:
                    System.out.println("\nInvalid choice ! Choose only between 0 and 6");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid Input");
            scanner.nextLine();
        }
    }
}

    private static void customSearch() {


    }

    public static void backToReport(){
    boolean isTrue = false;
        System.out.println("\n--------------------");
        System.out.println(" H - Home Screen");
        System.out.println(" L - Ledger Screen");
        System.out.println(" R - Report Screen");
        System.out.println(" X - Exit");
        System.out.println("--------------------");
    while(!isTrue){
        try{
        System.out.print("\nEnter your choice: ");
        String choice = scanner.next();
        if(choice.equalsIgnoreCase("H")){
            Tracker.menu();
        }else if(choice.equalsIgnoreCase("L")){
            Tracker.ledger();
        }else if(choice.equalsIgnoreCase("R")){
            report();
        }else if(choice.equalsIgnoreCase("X")){
            System.exit(0);
        }else{
            System.out.println("\nInvalid input ! Try again");
        }
        }catch (InputMismatchException e) {
            System.out.println("\nError ! Invalid Input !");
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
                System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n",
                        entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
            }
        }
        System.out.println("----------------------------------------------------------------");
        backToReport();
    }
    public static void prevMonth() {
        LocalDate now = LocalDate.now();
        LocalDate previousMonth = now.minusMonths(1);
        System.out.println("\n-------------------PREVIOUS MONTH ENTRIES------------------------");
        System.out.println("  Date     -   Time   -   Description     -   Vendor   -  Amount  ");

        for(Entries entries : entry){
            LocalDate dateCompare = LocalDate.parse(entries.getDate());
            if(dateCompare.getMonth() == previousMonth.getMonth() && dateCompare.getYear() == now.getYear()) {
                    System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n",
                            entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
                }

        }
        System.out.println("----------------------------------------------------------------");
        backToReport();
    }

    public static void yearToDate() {

        LocalDate now = LocalDate.now();

        System.out.println("\n---------------------YEAR TO DATE ENTRIES-----------------------");
        System.out.println("  Date     -   Time   -   Description     -   Vendor   -  Amount  ");
        for (Entries entries : entry) {
            LocalDate dateCompare = LocalDate.parse(entries.getDate());
            if (dateCompare.getYear() == now.getYear()) {
                System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n",
                        entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
            }
        }
        System.out.println("----------------------------------------------------------------");
        backToReport();
    }

    public static void prevYear() {
        LocalDate now = LocalDate.now();
        LocalDate previousYear = now.minusYears(1);
        System.out.println("\n-------------------PREVIOUS YEAR ENTRIES------------------------");
        System.out.println("  Date     -   Time   -   Description     -   Vendor   -  Amount  ");
        for (Entries entries : entry) {
            LocalDate dateCompare = LocalDate.parse(entries.getDate());
            if(dateCompare.getYear() == previousYear.getYear()){
                System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n",
                        entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
            }
        }
        System.out.println("----------------------------------------------------------------");
        backToReport();
    }

    public static void searchVendor() {
    boolean isFound = false;
    boolean isTrue = false;

        System.out.println("\nX - Return to Report Screen");
        scanner.nextLine();
        while(!isTrue) {

            System.out.print("Enter Vendors name/company: ");
            String vendor = scanner.nextLine().toLowerCase();
            if(vendor.equalsIgnoreCase("x")){
                isTrue = true;
                report();
            }
            for (Entries entries : entry) {
                String name = entries.getVendor().toLowerCase();
                if (vendor.equalsIgnoreCase(name)) {
                    if (!isFound) {
                        System.out.println("\n----------------------------VENDOR------------------------------");
                        System.out.println("  Date     -   Time   -   Description     -   Vendor   -  Amount  ");
                        isFound = true;
                    }
                    System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n",
                            entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
                    isTrue = true;
                }
            }
            System.out.println("----------------------------------------------------------------");
            if (!isFound) {
                System.out.println("             Vendor's name/company not found");
                System.out.println("----------------------------------------------------------------");
            }
        }
       backToReport();
    }


}
