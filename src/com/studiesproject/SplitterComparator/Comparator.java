package com.studiesproject.SplitterComparator;

import com.studiesproject.engine.SentensesSplitter;
import com.studiesproject.engine.xml.XmlWriterCore;
import com.studiesproject.utils.Log;
import com.sun.istack.internal.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrLukashem on 14.01.2017.
 */
public class Comparator {
    private final String TAG = "Comparator";

    private String mTxtInput;
    private String mXmlInput;

    private boolean prepareInputFile() {
        RandomAccessFile currentInput = null;
        RandomAccessFile fixedFile = null;

        try {
            currentInput = new RandomAccessFile(new File(mXmlInput), "rw");
            mXmlInput = "fixedInput.xml";
            fixedFile = new RandomAccessFile(new File(mXmlInput), "rw");
            fixedFile.setLength(0);
            // copy files.
            fixedFile.seek(0);
            fixedFile.write("<file>".getBytes());

            int singleBytesRead = (int) currentInput.length(); // single 100 bytes read.
            byte[] bytes = new byte[singleBytesRead];
            int offset = 0;
            //    for (offset = 0; offset < randomAccessFile.length(); offset += singleBytesRead) {
            currentInput.read(bytes, offset, singleBytesRead);
            fixedFile.write(bytes);
            //  }

            fixedFile.seek(fixedFile.length());
            fixedFile.write("</file>".getBytes());

            fixedFile.close();
            currentInput.close();
        } catch (FileNotFoundException fnfe) {
            Log.e(TAG, fnfe.getMessage());
            return false;
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage());
            return false;
        }

        return true;
    }

    private void ssBenchmark(SentensesSplitter splitter, Map<Integer, Integer> ssMap) {
        int sentenceNumber = 0;
        while (splitter.hasNext()) {
            String next = splitter.next();
            if (!next.isEmpty()) {
                Log.v(TAG, next);

                String[] sentence = next.split(" ");
                ssMap.put(sentenceNumber, sentence.length);
            }

            sentenceNumber++;
        }
    }

    private void tpBenchmark(Document document, Map<Integer, Integer> tpMap) {
        NodeList nodeList = document.getElementsByTagName("chunk");
        NodeList chunkChildren;
        Node child;
        Node chunk;
        String content;
        int sentenceNumber = 0;
        int wordsNumber = 0;

        for (int i = 0; i < nodeList.getLength(); i++) {
            chunk = nodeList.item(i);
            chunkChildren = chunk.getChildNodes();

            for (int j = 0; j < chunkChildren.getLength(); j++) {
                child = chunkChildren.item(j);
                if (child.getNodeName().equals("tok")) {
                    content = child.getFirstChild().getTextContent();
                    if (content.charAt(0) == '\n') {
                        content = child.getFirstChild().getNextSibling().getTextContent();
                    }

                    if (!content.equals(".") && !content.equals("!") && !content.equals("?") && !content.equals(";") && !content.equals("\n") && !content.equals(",")) {
                        wordsNumber++;
                    }
                }
            }

            tpMap.put(sentenceNumber, wordsNumber);

            wordsNumber = 0;
            sentenceNumber++;
        }
    }

    private void realCompare(Document document) {
        Map<Integer, Integer> ssMap = new HashMap<>();
        Map<Integer, Integer> tpMap = new HashMap<>();

        SentensesSplitter splitter = new SentensesSplitter();
        splitter.setDataSourceAndPrepare(mTxtInput);
        ssBenchmark(splitter, ssMap);

        tpBenchmark(document, tpMap);

    }

    public void setSentenceSplitterInputFile(@NotNull String inputFile) {
        mTxtInput = inputFile;
    }

    public void setXmlInputFile(@NotNull String xmlInput) {
        mXmlInput = xmlInput;
    }

    public void compare() {
        XmlWriterCore writerCore;
        Document doc;
        try {
            prepareInputFile();

            writerCore = new XmlWriterCore();
            writerCore.create(mXmlInput);
            doc = writerCore.getDocument();

            realCompare(doc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error while processing");
        }
    }
}
