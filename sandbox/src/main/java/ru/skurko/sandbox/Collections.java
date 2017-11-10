package ru.skurko.sandbox;

import java.util.ArrayList;
import java.util.List;

public class Collections {
    public static void main(String[] args) {
//        String[] langs = new String[4];
//        langs[0] = "Java";
//        langs[1] = "C#";
//        langs[2] = "Python";
//        langs[3] = "PHP";

//        Или:
        String[] langs = {"Java", "C#", "Python", "PHP"};

//        for (int i = 0; i < langs.length; i++) {
//            System.out.println("Я хочу выучить "+ langs[i]);
//        }
//        Или:
        for (String l : langs) {
            System.out.println("Я хочу выучить " + l);
        }

        List<String> languages = new ArrayList<>();
        languages.add("Java");
        languages.add("C#");
        languages.add("Python");
        languages.add("PHP");

        for (String l : languages) {
            System.out.println("Я выбираю из списка " + l);
        }

//        for (int i = 0; i < languages.size(); i++) {
//            System.out.println("Я выбираю из списка " + languages.get(i));
//        }
    }
}
