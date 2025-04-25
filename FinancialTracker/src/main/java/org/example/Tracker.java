package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Tracker {

    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Entries> entry = new ArrayList<>();

    public static ArrayList<Entries> showEntry(){

        try{
            BufferedReader buff = new BufferedReader(new FileReader("transactions.csv"));
            String line;
            while((line = buff.readLine()) !=null){
                String[] token = line.split("\\|");
                if(token.length == 5){
                    String date = token[0];
                    String time = token[1];
                    String desc = token[2];
                    String vendor = token[3];
                    float amount = Float.parseFloat(token[4]);

                    Entries entries = new Entries(date,time,desc,vendor,amount);
                    entry.add(entries);
                }
            }
    buff.close();
        } catch (Exception e) {
            System.out.println("\nError ! Something went wrong.");
        }
        return entry;
    }

    public static void menu(){
        String input;
        boolean isTrue = false;
        System.out.println("\n--- HOME SCREEN ---");
        System.out.println(" D - Add Deposit");
        System.out.println(" P - Make Payment(Debit)");
        System.out.println(" L - Ledger");
        System.out.println(" X - Exit");
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
        System.out.println("add nah yet");
        menu();
    }
    public static void makePayment(){
        System.out.println("pay,emt nah yet");
        menu();
    }
    public static void ledger(){
        System.out.println("ledger nah yet");
        menu();
    }
    public static void showAllEntry(){

    }
    public static void showDeposits(){

    }
    public static void showPayments(){

    }
    public static void showReports(){

    }


}
