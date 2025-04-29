package org.example;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Tracker {

    public static Scanner scanner = new Scanner(System.in);

    public static ArrayList<Entries> entry = new ArrayList<>();

    public static ArrayList<Entries> showEntry(String file){
       // entry.clear();
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
        } catch (IOException e) {
           // System.out.println("\nError ! Something went wrong.");
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
                    System.out.println("\nInvalid input! Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nError ! Invalid Input !");
                scanner.nextLine();
            }
        }
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
                    Report.report();
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
    public static void addDeposit(){
        System.out.println("\ndeposits nah yet");
        menu();
    }
    public static void makePayment(){
        System.out.println("\npaymenttt nah yet");
        menu();
    }

    public static void showAllEntry(){
        System.out.println("\n-------------------ALL ENTRIES----------------------------------");
        System.out.println("  Date     -   Time   -   Description     -   Vendor   -  Amount  ");

        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
        for(Entries entries : entry){
            System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n", entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(),entries.getAmount());
        }
        System.out.println("-----------------------------------------------------------------");
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
        System.out.println("-----------------------------------------------------------------");
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
        System.out.println("-----------------------------------------------------------------");
        isToLedger();
    }

}
