package com.studiesproject;

import com.studiesproject.engine.SentensesSplitter;
import com.studiesproject.engine.SplitterEngine;
import com.studiesproject.proxy.TaggerProxy;
import com.studiesproject.tagger.LocalTagger;
import com.studiesproject.utils.Log;
import com.sun.org.apache.xerces.internal.impl.XMLDocumentScannerImpl;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import java.io.IOException;

/*
    1. Uzycie TaggerProxy do otagowania tekstu za pomoca tapiki
    2. Czytanie zdania po zdaniu z uzyciem splitterengine
    3. Wyszukiwanie ostatniego slowa w zdaniu i nakladanie taga za nie </endsentence> w wyjsciowym pliku
    4. ...
 */

public class Main {

    protected static final String REGEX_1 = "[0-9]*:(0+[1-9]):(([0-2]+[0-9])|30|31)";

    private static final String TAG = "Main";

    public static String test(String e) {
        return new String(e);
    }

    public static void main(String[] args) throws IOException, TransformerException {
	// write your code here
        /*
        SentensesSplitter splitter = new SentensesSplitter();
        splitter.setDataSourceAndPrepare("TESTOWY_PLIK.txt");

        String s = "ewqeqwe";
        System.out.println( s == test(s));

        while (splitter.hasNext()) {
            String next = splitter.next();
            if (!next.isEmpty()) {
                System.out.println(next);
            }
        }*/

        TaggerProxy proxy = new TaggerProxy();
        proxy.setInputFile("C:\\Users\\MrLukashem\\Downloads\\TaKIPI18\\TaKIPI18\\Windows\\testowy.txt");
        proxy.setOutputFile("C:\\Users\\MrLukashem\\Downloads\\TaKIPI18\\TaKIPI18\\Windows\\newOutput2.xml");

        if (!proxy.blockRun()) {
            Log.e(TAG, "proxy.blockRun() error");
        }

        LocalTagger tagger = new LocalTagger(proxy.getOutputFile(), "outputNowy.xml");
        tagger.startProcessing();
    }
}
