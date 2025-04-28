package org.example;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Report {
    public static String file = "transasctions.csv";
   public static ArrayList<Entries> addEntry = Tracker.showEntry(file);

    public static Scanner scanner = new Scanner(System.in);


    public static void reports(){

        LocalDate now = LocalDate.now();


        for (Entries entries : addEntry) {

                LocalDate dateCompare = LocalDate.parse(entries.getDate());
                if (dateCompare.getMonth().equals(now.getMonth()) && dateCompare.getYear() == now.getYear()) {
                    System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n",
                            entries.getDate(), entries.getTime(), entries.getDescription(), entries.getVendor(), entries.getAmount());
                }

        }

    }


}
