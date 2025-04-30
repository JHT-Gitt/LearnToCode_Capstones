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

    private static void customSearch() {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate startDate = null;
    LocalDate endDate = null;
    String desc= "";
    String vendor = "";
    boolean isMatch = false;
    float amount = 0;
        scanner.nextLine();
        System.out.println("\n-------CUSTOM SEARCH-------");
        System.out.println("\nPress Enter to skip question");
        while(true) {
                System.out.print("Enter start date(yyyy-MM-dd): ");
                String startDateTemp = scanner.nextLine().trim();
                if (startDateTemp.isEmpty()) {
                    break;
                }
                try{
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
                try{
                    endDate = LocalDate.parse(endDateTemp, formatter);
                    break;
                } catch (DateTimeParseException e) {
                 System.out.println("\nInvalid date format or value. Please try again.\n");

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
        System.out.print("Enter the amount  : ");
        String amountTemp = scanner.nextLine();
        if(!amountTemp.isEmpty()){
            try {
                amount = Float.parseFloat(amountTemp);
            } catch (NumberFormatException e) {
                scanner.nextLine();
            }
        }
        try {
            entry.sort((e1, e2) -> e2.getDate().compareTo(e1.getDate())); // Sort newest first

            for (Entries e : entry) {
                LocalDate entryDate = LocalDate.parse(e.getDate(), formatter);

                boolean matches = false;


//                if( startDate !=null && endDate !=null && entryDate.isAfter(endDate) && entryDate.isBefore(startDate)){

                    if (endDate != null && startDate != null && startDate.isBefore(entryDate) && endDate.isAfter(entryDate)) {
                        matches = true;
                        isMatch = true;
                        //break;
                    }
//
//                        if (startDate != null && (entryDate.isAfter(startDate) || entryDate.isEqual(startDate))) {
//                        matches = true;
//                        }
//                        if (endDate != null && (entryDate.isBefore(endDate) || entryDate.isEqual(endDate))) {
//                        matches = true;
//                        }



//                    else if((!matches && startDate != null && (entryDate.isAfter(startDate) || entryDate.isEqual(startDate)) ) || (!matches && endDate != null && (entryDate.isBefore(endDate) || entryDate.isEqual(endDate)))){
//                        matches = true;
//                        break;
//                    }
                   // }
                        if (!isMatch && startDate != null && endDate == null && (entryDate.isAfter(startDate) || entryDate.isEqual(startDate))) {
                        matches = true;
                    }
//
                    if (!isMatch && endDate != null && startDate == null && (entryDate.isBefore(endDate) || entryDate.isEqual(endDate))) {
                        matches = true;
                    }
         //       }

                if (desc != null && !desc.isEmpty() && e.getDescription().equalsIgnoreCase(desc)) {
                    matches = true;
                }
                if (vendor != null && !vendor.isEmpty() && e.getVendor().equalsIgnoreCase(vendor)) {
                    matches = true;
                }
                if (amount != 0 && Math.abs(e.getAmount() - amount) <= 0.01f) {
                    matches = true;
                }
                if (matches) {
                    System.out.printf("\n%s - %s - %s - %s ➡️ %.2f\n",
                            e.getDate(), e.getTime(), e.getDescription(), e.getVendor(), e.getAmount());
                }
                isMatch = false;
            }


//            entry.sort((e1, e2) -> e2.getDate().compareTo(e1.getDate())); // Sort by date descending
//            for (Entries e : entry) {
//                LocalDate entryDate = LocalDate.parse(e.getDate(), formatter);
//                boolean matches = true;
//
//                if (startDate != null && entryDate.isBefore(startDate)) {
//                    matches = false;
//                }
//                if (endDate != null && entryDate.isAfter(endDate)) {
//                    matches = false;
//                }
//                if (desc != null && !desc.isEmpty() && !e.getDescription().equalsIgnoreCase(desc)) {
//                    matches = false;
//                }
//                if (vendor != null && !vendor.isEmpty() && !e.getVendor().equalsIgnoreCase(vendor)) {
//                    matches = false;
//                }
//                if (amount != 0 && Math.abs(e.getAmount() - amount) > 0.01f) {
//                    matches = false;
//                }
//                if(startDate == null && endDate == null && desc.isEmpty() && vendor.isEmpty() && amount ==0){
//                    System.out.println("\nMust Fill at least one field to filter search");
//                    backToReport();
//                }
//
//                if (matches) {
//                    System.out.printf("\n%s - %s - %s - %s ➡️ %.2f\n",
//                            e.getDate(), e.getTime(), e.getDescription(), e.getVendor(), e.getAmount());
//                    isMatch = true;
//                }


//            entry.sort((e1, e2) -> e2.getDate().compareTo(e1.getDate())); // assuming getDate() returns String
//            for (Entries e : entry) {
//                LocalDate entryDate = LocalDate.parse(e.getDate(), formatter);
//
//                if ( amount == e.getAmount() || desc.equalsIgnoreCase(e.getDescription())|| vendor.equalsIgnoreCase(e.getVendor())
//                 || (startDate != null && entryDate.isAfter(startDate)) || (endDate != null && entryDate.isBefore(endDate))
//                || (endDate != null && startDate != null && entryDate.isAfter(startDate) && entryDate.isBefore(endDate))) {
//
//                        System.out.printf("\n%s - %s - %s - %s ➡️ %.2f\n",
//                                e.getDate(), e.getTime(), e.getDescription(), e.getVendor(), e.getAmount());
//                }
//        try {
//            entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
//            for (Entries e : entry) {
//                String dateHolder = e.getDate();
//                LocalDate dateFile = LocalDate.parse(dateHolder, formatter);
////                String descHolder = e.getDescription().toLowerCase();
////                String vendorHolder = e.getVendor().toLowerCase();
//                float amountHolder = e.getAmount();
//
//                if ((endDate != null && startDate != null && startDate.isBefore(dateFile) && endDate.isAfter(dateFile))
//                        || desc.equalsIgnoreCase(e.getDescription().toLowerCase())|| vendor.equalsIgnoreCase(e.getVendor()) || (amount == amountHolder)
//                        || (Objects.requireNonNull(startDate).isBefore(dateFile) && endDate == null)){
////                        || (endDate.isAfter(dateFile) && startDate == null)  )  {
//                    System.out.printf("\n%s - %s - %s - %s  ➡️   %.2f\n",
//                            e.getDate(), e.getTime(), e.getDescription(), e.getVendor(), e.getAmount());
//                }
//            }
        }catch (DateTimeParseException e){
            //
        }catch (NullPointerException e){
           // scanner.nextLine();
        }
//        if(!isMatch){
//            System.out.println("\nCustom Filter Search not found");
//        }
        backToReport();

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
        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
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
        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
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
        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
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
        entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
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
            entry.sort((entry1, entry2) -> entry2.getDate().compareTo(entry1.getDate()));
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
