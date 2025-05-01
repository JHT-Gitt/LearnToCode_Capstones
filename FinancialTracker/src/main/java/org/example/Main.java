package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {


        ArrayList<Entries> entry = Tracker.showEntry("transactions.csv");

        Menus.menu(entry);



    }
}