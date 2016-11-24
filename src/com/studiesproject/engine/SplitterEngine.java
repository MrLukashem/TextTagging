package com.studiesproject.engine;

import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by mrlukashem on 21.11.16.
 */
public class SplitterEngine {
    private String mFileName = "";
    private String mOld = "";

    private BufferedReader mReader;
    private boolean mPrepared;

    // Parse a string buffer and returns it in array.
    // array[0] = a sentense.
    // array[1] = rest of buffer.
    private String[] parse(String stringBuffer) {
        String moreDataString = "";

        while (!isSentenseSeparator(moreDataString)) {
            // Read next line because we haven't found sentense separator yet.
            moreDataString = feedMeMoreData();
            // String is empty, it does mean we've read all text file or get a exception. So we need stop reading.
            if (moreDataString.isEmpty()) {
                break;
            }

            // add new string
            stringBuffer += moreDataString;
        }

        return catSentense(stringBuffer);
    }

    private String[] catSentense(String buffer) {
        return buffer.split("\\.");
    }

    private boolean isSentenseSeparator(String line) {
        // more complex operations will be put soon.
        return line.matches("\\.");
    }

    private String feedMeMoreData() {
        String line = "";
        try {
            if ((line = mReader.readLine()) != null) {
                return line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private boolean prepare() {
        try {
            InputStream in = Files.newInputStream(Paths.get(mFileName));
            mReader = new BufferedReader(new InputStreamReader(in));

            mPrepared = true;
            return mPrepared;
        } catch (IOException ioe) {
            System.out.println("io exception SplitterEngine::prepare");
            ioe.printStackTrace();
            mPrepared = false;

            return mPrepared;
        }
    }

    public boolean setSourceFileAndPrepare(@NotNull String fileName) {
        mFileName = fileName;
        return prepare();
    }

    public String getNextSentense() throws IOException {
        if (!isPrepared()) {
            // Has to be filled;
            return "";
        }

        String line = mOld;
        String temp = mReader.readLine();
        if (temp == null) {
            return "";
        }

        line += temp;

        String[] resultArray = parse(line);
        if (resultArray.length == 2) {
            mOld = resultArray[0];
            return resultArray[1];
        } else if (resultArray.length == 1) {
            mReader.close();
            // we have no more data. So next time we should return empty String.
            return resultArray[0];
        } else {
            mReader.close();
            // Maybe should we throw a exception? It is unexpected behaviour...
            return "";
        }
    }

    public boolean isPrepared() {
        return mPrepared;
    }
}
