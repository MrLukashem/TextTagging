package com.studiesproject.engine;

import com.studiesproject.utils.Log;
import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by mrlukashem on 21.11.16.
 */
public class SplitterEngine {
    private static final String TAG = "SPLITTER_ENGINE";

    private String mFileName = "";
    private String mOld = "";

    private BufferedReader mReader;
    private boolean mPrepared;

    private int mCatterInfo = 0;

    private final String mSentenceSeparatorRegex = "((.*)\\.(.*))+";

    // Parse a string buffer and returns it in array.
    // array[0] = a sentence.
    // array[1] = rest of buffer.
    private String[] parse(String stringBuffer) {
        String moreDataString = "";
        if (!isSentenseSeparator(stringBuffer)) {
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
        }

        return catSentense(stringBuffer);
    }

    private String[] catSentense(String buffer) {
        String[] resultArray = buffer.split("\\.|\\?");
        if (resultArray.length > 2) {
            int idx = 0;
            for (int i = 0 ; i < mCatterInfo; i++) {
                idx = buffer.indexOf('.', idx + 1);
            }

            resultArray = new String[2];
            resultArray[0] = buffer.substring(0, idx).trim();
            resultArray[1] = buffer.substring(idx + 1, buffer.length()).trim();
        }

        return resultArray;
    }

    private boolean isSentenseSeparator(String line) {
        // more complex operations will be put soon.
        boolean separator = line.matches(mSentenceSeparatorRegex);
        mCatterInfo = 0;

        if (line.contains(".")) {
            String[] separatedStrings = line.split("\\.|\\?");
            if (separatedStrings.length == 0) {
                return false;
            }  else if (separatedStrings.length == 1) {
                return true;
            } else {
                for (int i = 1; i < separatedStrings.length; i++) {
                    mCatterInfo++;

                    if (Character.isUpperCase(separatedStrings[i].trim().charAt(0))
                            || Character.isDigit(separatedStrings[i].trim().charAt(0))) {
                        return true;
                    }
                }

                return false;
            }
        }

        return false;
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
            Log.e(TAG, "io exception SplitterEngine::prepare");
            ioe.printStackTrace();
            mPrepared = false;

            return mPrepared;
        }
    }

    private String copyArrayToString(String in, String[] array, int startIdx) {
        String out = "";
        for (int i = startIdx; i < array.length; i++) {
            out += array[i] + ".";
        }

        return out;
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
        // if mOld has sentence already don't read next line from a text file.
        if (!isSentenseSeparator(line)) {
            String temp = mReader.readLine();
            if (temp == null) {
                mReader.close();
                return "";
            }

            line += temp;
        }

        String[] resultArray = parse(line);
        if (resultArray.length > 1) {
            mOld = copyArrayToString(mOld, resultArray, 1);
            //mOld = resultArray[0];
            return resultArray[0];
        } else if (resultArray.length == 1) {
            // We should clear mOld String because we can have infinite loop in the case.
            mOld = "";
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
