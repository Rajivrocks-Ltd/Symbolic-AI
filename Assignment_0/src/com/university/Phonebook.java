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
        people = sort(people);
    }

    private Vector<Person> sort(Vector<Person> tosort) {
        // Initiate a vector which is going to contain the sorted phonebook.
        Vector<Person> sorted;
        //Initiate a List which we will work with for the recursive steps.
        List<String> names = new ArrayList<>();

        // convert vector to list so we can work with this in the recursive funtion.
        for(Person p: tosort) {
            names.add(p.name + "-" + p.number);
        }

        // Recursive function, size of the function minus 1 since .size is not null based length.
        names = sortString(names, names.size()-1);

        // Convert list back to vector for the final step.
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

    // Main recursion loop
    private List<String> sortString(List<String> names, int n) {

        /*
            Make two variables, boolean to keep track if the list is sorted
            The string is to keep track of the temp variable in the bubble sorting step.
        */
        boolean sorted = true;
        String temp;

        /*
            Bubble-sort, very slow but it does the job.
            Loop over all items in length n, last item will always be sorted at the end of the loop that's why n < 1
         */
        for(int i = 0; i < n; i++){
            if(!isSmaller(names.get(i), names.get(i+1))) {
                /*
                    Firstly, the second item selected item will be stored in a temp variable.
                    Secondly, The first selected item is set to the index of the second item.
                    Thirdly, the temp value which is the second selected item, is set to the index of the first item.
                 */
                temp = names.get(i + 1);
                names.set(i + 1, names.get(i));
                names.set(i, temp);

                // if in the for loops means it's still not sorted, that's why: false.
                sorted = false;
            }
        }
        // Recursive call, insert the list + the length of the list minus 1, so it will not endlessly run the loop.
        if(sorted) { return names; }
        return sortString(names, n-1);
    }

    private boolean isSmaller(String s1, String s2) {
        // checks if s1 is smaller than s2, alphabetically
        int limit = Math.min(s1.length(), s2.length());
        String s1n = s1.split("-")[0];
        String s2n = s2.split("-")[0];

        for(int i = 0; i < limit; i++){
            if(s1n.charAt(i) > s2n.charAt(i)) { return false; }
            else if(s1n.charAt(i) < s2n.charAt(i)) { return true; }
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
