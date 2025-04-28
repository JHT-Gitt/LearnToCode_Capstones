package org.example;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Tracker {

    public static Scanner scanner = new Scanner(System.in);

    public static ArrayList<Entries> entry = new ArrayList<>();

    public static ArrayList<Entries> showEntry(String file){

        try{
            BufferedReader buff = new BufferedReader(new FileReader(file));
            buff.readLine();
            String line;
            while((line = buff.readLine()) != null){
                String[] token = line.split("\\|");
                if(token.length == 5){
                    String date = token[0];
                    String time = token[1];
                    String desc = token[2];
                    String vendor = token[3];
                    float amount = Float.parseFloat(token[4]);

                    Entries addEntries = new Entries(date,time,desc,vendor,amount);
                    entry.add(addEntries);
                }
            }
    buff.close();
        } catch (Exception e) {
            System.out.println("\nError ! Something went wrong.");
        }
        return entry;
    }
    public static void isToLedger(){
        boolean isTrue = false;
        String input;
        System.out.println("\n-----------------");
        System.out.println(" H - Home Screen");
        System.out.println(" L - Ledger Screen");
        System.out.println(" X - Exit");
        System.out.println("-----------------");
        while(!isTrue){
            try{
                System.out.print("\nEnter your choice: ");
                input = scanner.next();

                if(input.equalsIgnoreCase("H")){
                    menu();
                }else if(input.equalsIgnoreCase("L")){
                    ledger();
                }else if(input.equalsIgnoreCase("X")){
                    System.out.println("\nGoodbye and great a good day");
                    System.exit(0);
                }else{
                    System.out.println("\nInvalid ! choose between H and X only");
                }
            } catch (Exception e) {
                System.out.println("\nInvalid Input");
                scanner.nextLine();
            }
        }
    }
    public static void isContinue(){
        boolean isTrue = false;
        String input;
                System.out.println("\n-----------------");
                System.out.println(" H - Home Screen");
                System.out.println(" X - Exit");
                System.out.println("-----------------");
        while(!isTrue){
            try{
                System.out.print("\nEnter your choice: ");
                input = scanner.next();

                if(input.equalsIgnoreCase("H")){
                    menu();
                }else if(input.equalsIgnoreCase("X")){
                    System.out.println("\nGoodbye and great a good day");
                    System.exit(0);
                }else{
                    System.out.println("\nInvalid ! choose between H and X only");
                }
            } catch (Exception e) {
                System.out.println("\nInvalid Input");
                scanner.nextLine();
            }
        }
    }
    public static void menu(){
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
                    addDeposit();
                }else if (input.equalsIgnoreCase("P")){
                    makePayment();
                }else if (input.equalsIgnoreCase("L")) {
                    ledger();
                }else if(input.equalsIgnoreCase("X")){
                    System.out.println("\nGoodbye");
                    System.exit(0);
                }else{
                    System.out.println("Invalid input");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nError ! Invalid Input !");
                scanner.nextLine();
            }
        }
    }
    public static void addDeposit(){

    }
    public static void makePayment(){
        System.out.println("pay,emt nah yet");
        menu();
    }
    public static void ledger(){
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
                    showAllEntry();
                }else if(input.equalsIgnoreCase("D")){
                    showDeposits();
                }else if(input.equalsIgnoreCase("P")){
                    showPayments();
                }else if(input.equalsIgnoreCase("R")){
                    Report.reports();
                    //report();
                }else if(input.equalsIgnoreCase("H")){
                    menu();
                }else{
                    System.out.println("\nInvalid input ! Try again");
                }

            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Input");
                scanner.nextLine();

            }
        }
    }
    public static void showAllEntry(){
        System.out.println("\n-------------------ALL ENTRIES----------------------------------");
        System.out.println("  Date     -   Time   -   Description     -   Vendor   -  Amount  ");
   //     entry.sort(Comparator.comparing(Entries::getDate));
        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
        for(Entries entries : entry){
            System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n", entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(),entries.getAmount());
        }
        isToLedger();
    }
    public static void showDeposits(){
        System.out.println("\n-------------------  DEPOSITS  ----------------------------------");
        System.out.println("  Date     -   Time   -   Description     -   Vendor   -  Amount  ");
        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
        for(Entries entries : entry) {
            if (entries.getAmount() > 0) {
                System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n", entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
            }
        }
        isToLedger();
    }
    public static void showPayments(){
        System.out.println("\n-------------------  PAYMENTS  ----------------------------------");
        System.out.println("  Date     -   Time   -   Description     -   Vendor   -  Amount  ");
        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
        for(Entries entries : entry) {
            if (entries.getAmount() < 0) {
                System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n", entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
            }
        }
        isToLedger();
    }
    public static void monthToDate() {
        LocalDate now = LocalDate.now();

        for (Entries entries : entry) {
            LocalDate dateCompare = LocalDate.parse(entries.getDate());
            if (dateCompare.getMonth().equals(now.getMonth()) && dateCompare.getYear() == now.getYear()) {
                System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n", entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
            }
        }

        report();
    }
    public static void prevMonth() {
        System.out.println("heyprevmonth");
        report();
    }

    public static void yearToDate() {

        LocalDate now = LocalDate.now();

        System.out.println("\n---------------YEAR TO DATE ENTRIES----------------");
        for (Entries entries : entry) {
            LocalDate dateCompare = LocalDate.parse(entries.getDate());
            if (dateCompare.getYear() == now.getYear()) {
                System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n", entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
            }
        }
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
    public static void report() {

        boolean isTrue = false;

        System.out.println("\n    REPORT SCREEN   ");
        System.out.println("\nSearch by:");
        System.out.println(" 1 - Month To Date(MTD)");
        System.out.println(" 2 - Previous Month");
        System.out.println(" 3 - Year To Date(YTD)");
        System.out.println(" 4 - Previous Year");
        System.out.println(" 5 - Search by Vendor");
        System.out.println(" 0 - Back to Ledger Screen");

        while (!isTrue) {
            try {
                System.out.print("\nEnter your choice: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        ledger();
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


}
