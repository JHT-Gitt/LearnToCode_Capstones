package org.example;
import org.w3c.dom.CDATASection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Report {
    public static String file = "transaction.csv";
   public static ArrayList<Entries> entry = Tracker.showEntry(file);
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
    public static void backToReport(){
        boolean isTrue = false;
        System.out.println("\n-------------------------------------------------------------------------");
        System.out.println("   H - Home Screen    L - Ledger Screen   R - Report Screen   X - Exit");
        System.out.println("-------------------------------------------------------------------------");
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
    private static void customSearch() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = null;
        LocalDate endDate = null;
        String desc = null;
        String vendor = null;
        boolean isMatch = false;
        boolean isFound = false;
        boolean vendorFound = false;
        boolean descFound = false;
        boolean amountFound = false;
        boolean startFound = false;
        boolean endFound = false;
        float amount = 0;
        scanner.nextLine();
        System.out.println("\n-------🔎 CUSTOM SEARCH 🔍-------");
        System.out.println("\nPress Enter to skip question");

        while (true) {
        while (true) {
            System.out.print("Enter start date(yyyy-MM-dd): ");
            String startDateTemp = scanner.nextLine().trim();
            if (startDateTemp.isEmpty()) {
                break;
            }
            try {
                startDate = LocalDate.parse(startDateTemp, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date format or value. Please try again.");
                System.out.println("\nPress Enter to skip question");
            }
        }
        while (true) {
            System.out.print("Enter end date  (yyyy-MM-dd): ");
            String endDateTemp = scanner.nextLine().trim();
            if (endDateTemp.isEmpty()) {
                break;
            }
            try {
                endDate = LocalDate.parse(endDateTemp, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date format or value. Please try again.\n");
            }
        }
             try {
            if (endDate != null && startDate != null && startDate.isAfter(endDate)) {
                startDate = null;
                endDate = null;
                System.out.print("\nStart Date must be before the End date.\n\n");
            } else{
                   break;
                }
            }catch(NullPointerException e){
            //scanner.nextLine();
            }
        }
        System.out.print("Enter Description : ");
        String descTemp = scanner.nextLine();
        if(!descTemp.isEmpty()){
            desc = descTemp;
        }
        System.out.print("Enter Vendors name: ");
        String nameTemp = scanner.nextLine();
        if(!nameTemp.isEmpty()){
            vendor = nameTemp;
        }
        while(true) {
            System.out.print("Enter the amount  : ");
            String amountTemp = scanner.nextLine();
            if (amountTemp.isEmpty()) {
                break;
                }
                try {
                    amount = Float.parseFloat(amountTemp);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid ! number only\n");
                }
        }
        try {
            entry.sort((e1, e2) -> e2.getDate().compareTo(e1.getDate())); // Sort newest first

            for (Entries e : entry) {
                LocalDate entryDate = LocalDate.parse(e.getDate(), formatter);
                boolean matches = false;
                    if (endDate != null && startDate != null && startDate.isBefore(entryDate) && endDate.isAfter(entryDate)) {
                        matches = true;
                        isMatch = true;

                    }
                        if (!isMatch && startDate != null && endDate == null && (entryDate.isAfter(startDate) || entryDate.isEqual(startDate))) {
                        matches = true;
                        startFound = true;
                    }
                    if (!isMatch && endDate != null && startDate == null && (entryDate.isBefore(endDate) || entryDate.isEqual(endDate))) {
                        matches = true;
                        endFound = true;
                    }
                if (desc != null && !desc.isEmpty() && e.getDescription().equalsIgnoreCase(desc)) {
                        matches = true;
                        descFound = true;
                    }
                if (vendor != null && !vendor.isEmpty() && e.getVendor().equalsIgnoreCase(vendor)) {
                    matches = true;
                    vendorFound =true;
                }
                if (amount != 0 && Math.abs(e.getAmount() - amount) <= 0.01f) {
                    matches = true;
                    amountFound = true;
                }
                if (matches) {
//                    System.out.printf("\n%s ➖ %s ➖ %s ➖     %s   ➖️ %.2f\n",
//                            e.getDate(), e.getTime(), e.getDescription(), e.getVendor(), e.getAmount());
                    if (!isFound) {
                        System.out.println("\n-------------------------🔎CUSTOM SEARCH🔍-------------------------------");
                        //System.out.println("  Date     ➖   Time   ➖   Description     ➖   Vendor   ➖  Amount  ");
                        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
                        isFound = true;
                    }
                    System.out.printf("%-12s %-10s %-25s %-15s $%-9.2f\n",
                            e.getDate(), e.getTime(),
                            Tracker.truncate(e.getDescription(), 25),
                            Tracker.truncate(e.getVendor(), 15),e.getAmount());
                }
                isMatch = false;
            }
            System.out.println("-------------------------------------------------------------------------");
            if (vendor != null && !vendor.isEmpty() && !vendorFound) {
                System.out.println("\n❌ Vendor not found: " + vendor);
                isFound = true;
            }
            if(desc != null && !desc.isEmpty() && !descFound){
                System.out.println("\n❌ Description not found: " + desc);
                isFound = true;
            }
            if(amount != 0 && !amountFound ){
                System.out.println("\n❌ Amount not found: " + amount);
                isFound = true;
            }
            if(startDate != null && !startFound){
                System.out.println("\n❌ No entries found from the provided start date");
                isFound = true;
            }
            if(endDate != null && !endFound){
                System.out.println("\n❌ No entries found from the provided end date");
                isFound = true;
            }

            if(!isFound){
                System.out.println("                    Search fields are empty");
                System.out.println("-------------------------------------------------------------------------");
            }
        }catch (DateTimeParseException e){
            //
        }catch (NullPointerException e){
           // scanner.nextLine();
        }
        backToReport();
    }
    public static void monthToDate() {
        boolean hasEntries = false;
        LocalDate now = LocalDate.now();
        System.out.println("\n---------------------------MONTH TO DATE ENTRIES------------------------");
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
        for (Entries entries : entry) {
            LocalDate dateCompare = LocalDate.parse(entries.getDate());
            if (dateCompare.getMonth().equals(now.getMonth()) && dateCompare.getYear() == now.getYear()) {
                hasEntries = true;
                System.out.printf("%-12s %-10s %-25s %-15s $%-9.2f\n",
                        entries.getDate(), entries.getTime(),
                        Tracker.truncate(entries.getDescription(), 25),
                        Tracker.truncate(entries.getVendor(), 15),entries.getAmount());
            }
        }
        System.out.println("------------------------------------------------------------------------");
        if(!hasEntries){
            System.out.println("                     ❌  EMPTY ENTRIES  ❌ ");
        }
        backToReport();
    }
    public static void prevMonth() {
    boolean hasEntries = false;
        LocalDate now = LocalDate.now();
        LocalDate previousMonth = now.minusMonths(1);
        System.out.println("\n-------------------------PREVIOUS MONTH ENTRIES--------------------------");
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
        for(Entries entries : entry){
            LocalDate dateCompare = LocalDate.parse(entries.getDate());
            if(dateCompare.getMonth() == previousMonth.getMonth() && dateCompare.getYear() == now.getYear()) {
                hasEntries = true;
                System.out.printf("%-12s %-10s %-25s %-15s $%-9.2f\n",
                        entries.getDate(), entries.getTime(),
                        Tracker.truncate(entries.getDescription(), 25),
                        Tracker.truncate(entries.getVendor(), 15),entries.getAmount());
                }
        }

        System.out.println("-------------------------------------------------------------------------");
        if(!hasEntries){
            System.out.println("                     ❌  EMPTY ENTRIES  ❌ ");
        }
        backToReport();
    }
    public static void yearToDate() {
        boolean hasEntries = false;
        LocalDate now = LocalDate.now();
        System.out.println("\n----------------------------YEAR TO DATE ENTRIES-------------------------");
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
        for (Entries entries : entry) {

            LocalDate dateCompare = LocalDate.parse(entries.getDate());
            if (dateCompare.getYear() == now.getYear()) {
                hasEntries = true;
                System.out.printf("%-12s %-10s %-25s %-15s $%-9.2f\n",
                        entries.getDate(), entries.getTime(),
                        Tracker.truncate(entries.getDescription(), 25),
                        Tracker.truncate(entries.getVendor(), 15),entries.getAmount());
            }
        }
        System.out.println("-------------------------------------------------------------------------");
        if(!hasEntries){
            System.out.println("                     ❌  EMPTY ENTRIES  ❌ ");
        }
        backToReport();
    }
    public static void prevYear() {
        boolean hasEntries = false;
        LocalDate now = LocalDate.now();
        LocalDate previousYear = now.minusYears(1);
        System.out.println("\n--------------------------PREVIOUS YEAR ENTRIES--------------------------");
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
        for (Entries entries : entry) {
            LocalDate dateCompare = LocalDate.parse(entries.getDate());
            if(dateCompare.getYear() == previousYear.getYear()){
                hasEntries = true;
                System.out.printf("%-12s %-10s %-25s %-15s $%-9.2f\n",
                        entries.getDate(), entries.getTime(),
                        Tracker.truncate(entries.getDescription(), 25),
                        Tracker.truncate(entries.getVendor(), 15),entries.getAmount());
            }
        }
        System.out.println("-------------------------------------------------------------------------");
        if(!hasEntries){
            System.out.println("                     ❌  EMPTY ENTRIES  ❌ ");
        }
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
            entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
            for (Entries entries : entry) {
                String name = entries.getVendor().toLowerCase();
                if (vendor.equalsIgnoreCase(name)) {
                    if (!isFound) {
                        System.out.println("\n------------------------------------VENDOR--------------------------------");
                        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
                        isFound = true;
                    }
                    System.out.printf("%-12s %-10s %-25s %-15s $%-9.2f\n",
                            entries.getDate(), entries.getTime(),
                            Tracker.truncate(entries.getDescription(), 25),
                            Tracker.truncate(entries.getVendor(), 15),entries.getAmount());
                    isTrue = true;
                }
            }
            System.out.println("--------------------------------------------------------------------------");
            if (!isFound) {
                System.out.println("                    ❌Vendor's name/company not found❌");
                System.out.println("--------------------------------------------------------------------------");
            }
        }
       backToReport();
    }
}
