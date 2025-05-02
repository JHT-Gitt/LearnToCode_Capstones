package org.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menus {
    public static Scanner scanner = new Scanner(System.in);

    public static void menu(ArrayList<Entries> entry){
        String input;
        boolean isTrue = false;
        System.out.println("\n------- HOME SCREEN -------");
        System.out.println("| D - Add Deposit         |");
        System.out.println("| P - Make Payment(Debit) |");
        System.out.println("| L - Ledger              |");
        System.out.println("| X - Exit                |");
        System.out.println("---------------------------");
        while(!isTrue) {
            try {
                System.out.print("\nEnter your choice: ");
                input = scanner.next().toLowerCase();

                if(input.equalsIgnoreCase("D")){
                    Tracker.addDeposits();
                }else if (input.equalsIgnoreCase("P")){
                    Tracker.makePayment();
                }else if (input.equalsIgnoreCase("L")) {
                    ledger(entry);
                }else if(input.equalsIgnoreCase("X")){
                    System.out.println("\nGoodbye and come again");
                    System.exit(0);
                }else{
                    System.out.println("\nInvalid input! Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nError ! Invalid Input !");
                scanner.nextLine();
            }
        }
    }
    public static void ledger(ArrayList<Entries> entry){
        boolean isTrue = false;
        String input;

        System.out.println("\n-------------- LEDGER --------------");
        System.out.println("| A - Display All Entries          |");
        System.out.println("| D - Display All Deposits Entries |");
        System.out.println("| P - Display All Payments Entries |");
        System.out.println("| R - Reports                      |");
        System.out.println("| H - Home Screen                  |");
        System.out.println("------------------------------------");
        while(!isTrue) {
            try {
                System.out.print("\nEnter your choice: ");
                input = scanner.next();

                if(input.equalsIgnoreCase("A")){
                    Tracker.showAllEntry();
                }else if(input.equalsIgnoreCase("D")){
                    Tracker.showDeposits();
                }else if(input.equalsIgnoreCase("P")){
                    Tracker.showPayments();
                }else if(input.equalsIgnoreCase("R")){
                    report(entry);
                }else if(input.equalsIgnoreCase("H")){
                    menu(entry);
                }else{
                    System.out.println("\nInvalid input ! Try again");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Input");
                scanner.nextLine();
            }
        }
    }
    public static void report(ArrayList<Entries> entry) {
        boolean isTrue = false;
        System.out.println("\n-------REPORTS SCREEN--------   ");
        System.out.println("Search by:");
        System.out.println(" 1 - Month To Date(MTD)");
        System.out.println(" 2 - Previous Month");
        System.out.println(" 3 - Year To Date(YTD)");
        System.out.println(" 4 - Previous Year");
        System.out.println(" 5 - Search by Vendor");
        System.out.println(" 6 - Custom Search");
        System.out.println(" 7 - Entries Summary");
        System.out.println(" 0 - Back to Ledger Screen");
        System.out.println("-----------------------------");
        while (!isTrue) {
            try {
                System.out.print("\nEnter your choice: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        ledger(entry);
                        isTrue = true;
                        break;
                    case 1:
                        Report.monthToDate(entry);
                        break;
                    case 2:
                        Report.prevMonth(entry);
                        break;
                    case 3:
                        Report.yearToDate(entry);
                        break;
                    case 4:
                        Report.prevYear(entry);
                        break;
                    case 5:
                        Report.searchVendor(entry);
                        break;
                    case 6:
                        Report.customSearch(entry);
                        break;
                    case 7:
                        Report.summary(entry);
                    break;
                    default:
                        System.out.println("\nInvalid choice ! Choose only between 0 and 7");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Input");
                scanner.nextLine();
            }
        }
    }
    public static void backToReport(ArrayList<Entries> entry){
        boolean isTrue = false;
        System.out.println("\n-------------------------------------------------------------------------");
        System.out.println("   H - Home Screen    L - Ledger Screen   R - Report Screen   X - Exit");
        System.out.println("-------------------------------------------------------------------------");
        while(!isTrue){
            try{
                System.out.print("\nEnter your choice: ");
                String choice = scanner.next();
                if(choice.equalsIgnoreCase("H")){
                    menu(entry);
                }else if(choice.equalsIgnoreCase("L")){
                    ledger(entry);
                }else if(choice.equalsIgnoreCase("R")){
                    report(entry);
                }else if(choice.equalsIgnoreCase("X")){
                    System.out.println("\nGoodbye and come again");
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
    public static void isToLedger(ArrayList<Entries> entry){
        boolean isTrue = false;
        String input;
        System.out.println("\n--------------------------------------------------");
        System.out.println(" H - Home Screen    L - Ledger Screen    X - Exit");
        System.out.println("--------------------------------------------------");
        while(!isTrue){
            try{
                System.out.print("\nEnter your choice: ");
                input = scanner.next();

                if(input.equalsIgnoreCase("H")){
                    menu(entry);
                }else if(input.equalsIgnoreCase("L")){
                    ledger(entry);
                }else if(input.equalsIgnoreCase("X")){
                    System.out.println("\nGoodbye and have great a day");
                    System.exit(0);
                }else{
                    System.out.println("\nInvalid ! choose between H, L and X only");
                }
            } catch (Exception e) {
                System.out.println("\nInvalid Input");
                scanner.nextLine();
            }
        }
    }

}
