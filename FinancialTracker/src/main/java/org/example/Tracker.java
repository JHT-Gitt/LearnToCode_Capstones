package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        } catch (IOException e) {
            System.out.println("\nError ! Something went wrong.");
        }
        return entry;
    }
    public static String truncate(String text, int maxLength) {
        return text.length() <= maxLength ? text : text.substring(0, maxLength - 3) + "...";
    }
    public static void showAllEntry(){
        boolean hasEntries = false;
        System.out.println("\n-----------------------------ALL ENTRIES----------------------------------");
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------");

        entry.sort(Comparator.comparing(Entries::getDate).thenComparing(Entries::getTime).reversed());
        for(Entries entries : entry){
            hasEntries = true;
            System.out.printf("%-12s %-10s %-25s %-15s $%-9.2f\n",
                    entries.getDate(),
                    entries.getTime(),
                    truncate(entries.getDescription(), 25),
                    truncate(entries.getVendor(), 15),
                    entries.getAmount());
        }
        System.out.println("--------------------------------------------------------------------------");
        if(!hasEntries){
            System.out.println("                     ❌  EMPTY ENTRIES  ❌ ");
        }
        Menus.isToLedger(entry);
    }
    public static void showDeposits(){
        boolean hasEntries = false;
        System.out.println("\n----------------------------  DEPOSITS  ----------------------------------");
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------");
        entry.sort(Comparator.comparing(Entries::getDate).thenComparing(Entries::getTime).reversed());
        for(Entries entries : entry) {
            if (entries.getAmount() > 0) {
                hasEntries = true;
                System.out.printf("%-12s %-10s %-25s %-15s $%-9.2f\n",
                        entries.getDate(),
                        entries.getTime(),
                        truncate(entries.getDescription(), 25),
                        truncate(entries.getVendor(), 15),
                        entries.getAmount());
            }
        }
        System.out.println("--------------------------------------------------------------------------");
        if(!hasEntries){
            System.out.println("                     ❌  EMPTY ENTRIES  ❌ ");
        }
        Menus.isToLedger(entry);
    }
    public static void showPayments(){
        boolean hasEntries = false;
        System.out.println("\n----------------------------  PAYMENTS  ----------------------------------");
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------");
        entry.sort(Comparator.comparing(Entries::getDate).thenComparing(Entries::getTime).reversed());
        for(Entries entries : entry) {
            if (entries.getAmount() < 0) {
                hasEntries = true;
                System.out.printf("%-12s %-10s %-25s %-15s $%-9.2f\n",
                        entries.getDate(),
                        entries.getTime(),
                        truncate(entries.getDescription(), 25),
                        truncate(entries.getVendor(), 15),
                        entries.getAmount());
            }
        }
        System.out.println("--------------------------------------------------------------------------");
        if(!hasEntries){
            System.out.println("                     ❌  EMPTY ENTRIES  ❌ ");
        }
        Menus.isToLedger(entry);
    }
    public static void addDeposits() {
        float amount;
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        String dateToday = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String timeToday = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        try {
            //  scanner.nextLine();
            System.out.print("\nEnter description: ");
            String desc = scanner.next();
            System.out.print("Enter Vendor name: ");
            String vendor = scanner.next();
            while (true) {
                System.out.print("Enter the amount you want to deposit: ");
                try {
                    amount = scanner.nextFloat();
                    scanner.nextLine();
                    if (amount <= 0) {
                        System.out.println("\nInvalid input. No less than $1.\n");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Number only.\n");
                    scanner.nextLine();
                }
            }
            //Entries addEntry = new Entries(today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), time.format(DateTimeFormatter.ofPattern("HH:mm:ss")), desc, vendor, amount);
            Entries addEntry = new Entries(dateToday,timeToday,desc,vendor,amount);
            entry.add(addEntry);
            BufferedWriter buff = new BufferedWriter(new FileWriter("transactions.csv", true));
//            buff.write("date|time|description|vendor|amount");
//            buff.newLine();
//            for (Entries entries : entry) {
//                buff.write(String.format("%s|%s|%s|%s|%.2f", entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount()));
//             buff.newLine();
//             }
            buff.write(String.format("\n%s|%s|%s|%s|%.2f",addEntry.getDate(),addEntry.getTime(),addEntry.getDescription(),addEntry.getVendor(),addEntry.getAmount()));
            System.out.println("\nDeposit Success !");
            buff.close();

        } catch (IOException e) {
            System.out.println("\nError");
        }
        returned();
    }
    public static void makePayment(){
        float amount;
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        String dateToday = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String timeToday = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        try {
            scanner.nextLine();
            System.out.print("\nEnter description: ");
            String desc = scanner.nextLine();
            System.out.print("Enter Vendor name: ");
            String vendor = scanner.nextLine();
            while (true) {
                System.out.print("Enter the amount you want to pay: ");
                try {
                    amount = scanner.nextFloat();
                    scanner.nextLine();
                    if (amount < 0) {
                        System.out.println("\nInvalid input. No less than $1.\n");
                        continue;
                    }else{
                        amount = -amount;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Number only\n");
                    scanner.nextLine();
                }
            }
            Entries addEntry = new Entries(dateToday,timeToday,desc,vendor,amount);
            entry.add(addEntry);
            BufferedWriter buff = new BufferedWriter(new FileWriter("transactions.csv",true));
//            buff.write("date|time|description|vendor|amount");
//            buff.newLine();
//            for (Entries entries : entry) {
//                buff.write(String.format("%s|%s|%s|%s|%.2f", entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount()));
//                buff.newLine();
//            }
            buff.write(String.format("\n%s|%s|%s|%s|%.2f",addEntry.getDate(),addEntry.getTime(),addEntry.getDescription(),addEntry.getVendor(),addEntry.getAmount()));
            System.out.println("\nPayment Success !");
            buff.close();

        } catch (IOException e) {
            System.out.println("\nError");
        }
        payBack();
    }
    public static void returned(){
        boolean isTrue = false;
        System.out.println("\n----------------------------------------------");
        System.out.println(" D - Deposit Again  H - Home Screen  X - Exit");
        System.out.println("----------------------------------------------");

        while(!isTrue) {
            System.out.print("\nEnter : ");
            String enter = scanner.next();
            if (enter.equalsIgnoreCase("D")) {
                addDeposits();
                isTrue = true;
            } else if (enter.equalsIgnoreCase("H")) {
                Menus.menu(entry);
                isTrue = true;
            } else if (enter.equalsIgnoreCase("X")) {
                System.out.println("\nGoodbye and come again");
                System.exit(0);
            } else {
                System.out.println("\nInvalid input. Try again");
            }
        }
    }
    public static void payBack(){
        boolean isTrue = false;
        System.out.println("\n----------------------------------------------");
        System.out.println("   P - Pay Again  H - Home Screen  X - Exit");
        System.out.println("----------------------------------------------");

        while(!isTrue) {
            System.out.print("\nEnter : ");
            String enter = scanner.next();
            if (enter.equalsIgnoreCase("P")) {
                makePayment();
                isTrue = true;
            } else if (enter.equalsIgnoreCase("H")) {
                Menus.menu(entry);
                isTrue = true;
            } else if (enter.equalsIgnoreCase("X")) {
                System.out.println("\nGoodbye and come again");
                System.exit(0);
            } else {
                System.out.println("\nInvalid input. Try again");
            }
        }
    }

}
