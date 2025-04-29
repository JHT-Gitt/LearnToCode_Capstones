package org.example;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

public class AddPay {

    public static Scanner scanner = new Scanner(System.in);

   // public static ArrayList<Entries> entry = Tracker.showEntry("transactions.csv");

    static String file = "transactions.csv";
    static ArrayList<Entries> entry = Tracker.showEntry(file);

//    public static void addDeposits() {
//        float amount;
//        LocalDate today = LocalDate.now();
//        LocalTime time = LocalTime.now();
//        String dateToday = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        String timeToday = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//        try {
//            BufferedWriter buff = new BufferedWriter(new FileWriter(file, true));
//        System.out.print("\nEnter description: ");
//        String desc = scanner.nextLine();
//        System.out.print("Enter Vendor name: ");
//        String vendor = scanner.nextLine();
//        while (true) {
//            System.out.print("Enter the amount: ");
//            try {
//                amount = scanner.nextFloat();
//                scanner.nextLine();
//                if (amount < 0) {
//                    System.out.println("Amount cannot be negative. Please enter a valid amount.");
//                    continue;
//                }
//                break;
//            } catch (InputMismatchException e) {
//                System.out.println("\nInvalid input for amount. Please enter a valid number.");
//                scanner.nextLine();
//            }
//        }
////        Entries addEntry = new Entries(today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), time.format(DateTimeFormatter.ofPattern("HH:mm:ss")), desc, vendor, amount);
////        entry.add(addEntry);
////        System.out.println("\nDeposit Added !");
//
////            buff.write("date|time|description|vendor|amount");
////          buff.newLine();
////            for (Entries entries : entry) {
////                buff.write(String.format("\n%s|%s|%s|%s|%.2f", entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount()));
//               // buff.newLine();
//           // }
////            buff.write(String.format("\n%s|%s|%s|%s|%.2f",
//
////                    addEntry.getDate(),
////                    addEntry.getTime(),
////                    addEntry.getDescription(),
////                    addEntry.getVendor(),
////                    addEntry.getAmount()));
//           // buff.newLine();
//            buff.newLine();
//            buff.write(cvs(dateToday, timeToday, desc, vendor, amount));
//            System.out.println("\nDeposit Added !");
//            buff.close();
//
//        } catch (IOException e) {
//            System.out.println("\nError");
//        }
//        returned();
//
//    }
//    public static String cvs(String date, String time, String description, String vendor, float amount ){
//        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
//    }
//
//    public static void returned(){
//        boolean isTrue = false;
//        System.out.println("\nDo you want to deposit again ?\n D - Deposit \n H - Home Screen \n X - Exit");
//
//        while(!isTrue) {
//            System.out.print("\nEnter : ");
//            String enter = scanner.nextLine();
//            if (enter.equalsIgnoreCase("D")) {
//                addDeposits();
//                isTrue = true;
//            } else if (enter.equalsIgnoreCase("H")) {
//                Tracker.menu();
//                isTrue = true;
//            } else if (enter.equalsIgnoreCase("X")) {
//                System.exit(0);
//            } else {
//                System.out.println("\nInvalid input. Try again");
//            }
//        }
//    }
}
