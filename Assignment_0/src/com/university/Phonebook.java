package com.university;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Phonebook {

    Vector<Person> people;

    public Phonebook() {
        people = new Vector<>();
    }

    public void read(String file) {

        try {
            Scanner scanner = new Scanner(new File(file));
            while (scanner.hasNextLine()) {
                String[] person = scanner.nextLine().split(",");
                people.addElement(new Person(person[0].strip().toLowerCase(), person[1].strip()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String number(String name) {
        for (Person p: people) {
            if (Objects.equals(p.name, name.toLowerCase())) { return p.number; }
        }
        return "Person not found!";
    }

    public void sort() {
        people= sort(people);
    }

    private Vector<Person> sort(Vector<Person> tosort) {
        // Initiate a vector which is going to contain the sorted phonebook.
        Vector<Person> sorted;
        //Initiate a List which we will work with for the recursive steps.
        List<String> names = new ArrayList<>();

        //
        for(Person p: tosort) {
            names.add(p.name + "-" + p.number);
        }

        names = sortString(names, names.size()-1);

        sorted = listToVector(names);

        return sorted;
    }

    private Vector<Person> listToVector(List<String> names){
        Vector<Person> people = new Vector<>();
        for(String p: names){
            String[] pRaw = p.split("-");
            people.addElement(new Person(pRaw[0], pRaw[1]));
        }

        return people;

    }

    private List<String> sortString(List<String> names, int n) {
        if(n == 1) { return names; }

        String temp;

        // Bubble-sort, very slow but it does the job.
        for(int i = 0; i < names.size() - 1; i++){
            if(!isSmaller(names.get(i), names.get(i+1))) {
                /*
                    Firstly, the second item selected item will be stored in a temp variable.
                    Secondly, The first selected item is set to the index of the second item.
                    Thirdly, the temp value which is the second selected item, is set to the index of the first item.
                 */
                temp = names.get(i + 1);
                names.set(i + 1, names.get(i));
                names.set(i, temp);
            }
        }
        // Recursive call, insert the list + the length of the list minus 1, so it will not endlessly run the loop.
        return sortString(names, n-1);
    }

    private boolean isSmaller(String s1, String s2) {
        // checks if s1 is smaller than s2, alphabetically
        int limit = Math.min(s1.length(), s2.length());
        String s1n = s1.split("-")[0];
        String s2n = s2.split("-")[0];

        for(int i = 0; i < limit; i++){
            if(s1n.charAt(i) < s2n.charAt(i)) {
                return true; }
            else if(s1n.charAt(i) > s2n.charAt(i)) {
                return false; }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Person p: people) {
            result.append(p.name).append(" - ").append(p.number).append('\n');
        }

        return result.toString();
    }
}
