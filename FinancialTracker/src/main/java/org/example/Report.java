package org.example;
import org.w3c.dom.CDATASection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Report {
    //    public static String file = "transactions.csv";
//   public static ArrayList<Entries> entry = Tracker.showEntry(file);
    public static Scanner scanner = new Scanner(System.in);


    public static void customSearch(ArrayList<Entries> entry) {
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
        //  scanner.nextLine();
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
                    System.out.println("\nPress Enter to skip filter");
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
                amount = Math.round(amount * 100)/ 100f;
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid ! number only\n");
            }
        }
        try {
            entry.sort(Comparator.comparing(Entries::getDate).thenComparing(Entries::getTime).reversed());
            for (Entries e : entry) {
                LocalDate entryDate = LocalDate.parse(e.getDate(), formatter);
                boolean matches = false;
                if (endDate != null && startDate != null && startDate.isBefore(entryDate) && endDate.isAfter(entryDate)) {
                    matches = true;
                    isMatch = true;
                    startFound = true;
                    endFound = true;
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
                if(amount !=0 && amount == e.getAmount()){
                    matches = true;
                    amountFound = true;
                }
                if (matches) {
                    if (!isFound) {
                        System.out.println("\n-------------------------🔎CUSTOM SEARCH🔍------------------------------");
                        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
                        System.out.println("-------------------------------------------------------------------------");
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
        Menus.backToReport(entry);
    }
    public static void monthToDate(ArrayList<Entries> entry) {
        boolean hasEntries = false;
        LocalDate now = LocalDate.now();
        System.out.println("\n---------------------------MONTH TO DATE ENTRIES-------------------------");
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-------------------------------------------------------------------------");

        entry.sort(Comparator.comparing(Entries::getDate).thenComparing(Entries::getTime).reversed());
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
        System.out.println("-------------------------------------------------------------------------");
        if(!hasEntries){
            System.out.println("                     ❌  EMPTY ENTRIES  ❌ ");
        }
        Menus.backToReport(entry);
    }
    public static void prevMonth(ArrayList<Entries> entry) {
        boolean hasEntries = false;
        LocalDate now = LocalDate.now();
        LocalDate previousMonth = now.minusMonths(1);
        System.out.println("\n-------------------------PREVIOUS MONTH ENTRIES--------------------------");
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-------------------------------------------------------------------------");

        entry.sort(Comparator.comparing(Entries::getDate).thenComparing(Entries::getTime).reversed());
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
        Menus.backToReport(entry);
    }
    public static void yearToDate(ArrayList<Entries> entry) {
        boolean hasEntries = false;
        LocalDate now = LocalDate.now();
        System.out.println("\n----------------------------YEAR TO DATE ENTRIES-------------------------");
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-------------------------------------------------------------------------");

        entry.sort(Comparator.comparing(Entries::getDate).thenComparing(Entries::getTime).reversed());
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
        Menus.backToReport(entry);
    }
    public static void prevYear(ArrayList<Entries> entry) {
        boolean hasEntries = false;
        LocalDate now = LocalDate.now();
        LocalDate previousYear = now.minusYears(1);
        System.out.println("\n--------------------------PREVIOUS YEAR ENTRIES--------------------------");
        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-------------------------------------------------------------------------");

        entry.sort(Comparator.comparing(Entries::getDate).thenComparing(Entries::getTime).reversed());
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
        Menus.backToReport(entry);
    }
    public static void searchVendor(ArrayList<Entries> entry) {
        boolean isFound = false;
        boolean isTrue = false;
        System.out.println("\nX - Return to Report Screen");
        // scanner.nextLine();
        while(!isTrue) {
            System.out.print("Enter Vendors name/company: ");
            String vendor = scanner.nextLine().toLowerCase();
            if(vendor.equalsIgnoreCase("x")){
                isTrue = true;
                Menus.report(entry);
            }
            entry.sort(Comparator.comparing(Entries::getDate).thenComparing(Entries::getTime).reversed());
            for (Entries entries : entry) {
                String name = entries.getVendor().toLowerCase();
                if (vendor.equalsIgnoreCase(name)) {
                    if (!isFound) {
                        System.out.println("\n------------------------------------VENDOR--------------------------------");
                        System.out.printf("%-12s %-10s %-25s %-15s %-10s\n", "Date", "Time", "Description", "Vendor", "Amount");
                        System.out.println("--------------------------------------------------------------------------");
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
        Menus.backToReport(entry);
    }
}
