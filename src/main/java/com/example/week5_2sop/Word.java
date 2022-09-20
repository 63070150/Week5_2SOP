package com.example.week5_2sop;

import org.apache.tomcat.util.digester.ArrayStack;

import java.util.ArrayList;

public class Word {
    public ArrayList<String> badWords = new ArrayList<String>();
    public ArrayList<String> goodWords = new ArrayList<String>();
    public Word(){
        badWords.add("Fuck");
        badWords.add("olo");
        goodWords.add("happy");
        goodWords.add("enjooy");
        goodWords.add("life");
    }
}
