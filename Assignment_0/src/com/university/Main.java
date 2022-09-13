package com.university;

public class Main {

    public static void main(String[] args) {

        Phonebook database = new Phonebook();
        database.read("phonebook.txt");
        //System.out.println(database.number("Bel le Belgica"));
        database.sort();
        System.out.println(database.toString());

    }
}

