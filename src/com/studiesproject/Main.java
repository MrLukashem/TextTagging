package com.studiesproject;

import com.studiesproject.engine.SentensesSplitter;
import com.studiesproject.engine.SplitterEngine;
import com.sun.org.apache.xerces.internal.impl.XMLDocumentScannerImpl;
import org.w3c.dom.Document;

import java.io.IOException;

public class Main {

    protected static final String REGEX_1 = "[0-9]*:(0+[1-9]):(([0-2]+[0-9])|30|31)";

    public static String test(String e) {
        return new String(e);
    }

    public static void main(String[] args) throws IOException {
	// write your code here
        SplitterEngine engine = new SplitterEngine();
        engine.setSourceFile("TESTOWY_PLIK.txt");

        String s = "ewqeqwe";
        System.out.println( s == test(s));
        engine.getNextSentense();
    }
}
