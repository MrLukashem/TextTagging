package com.studiesproject.proxy;

import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mrlukashem on 21.11.16.
 */
public class TaggerProxy {

    private static final String SPACE = " ";

    private String mInputFile = "";

    private String mOutputFile = "";

    public void setInputFile(@NotNull String file) {
        mInputFile = file;
    }

    public void setOutputFile(@NotNull String file) {
        mOutputFile = file;
    }

    public boolean blockRun() {
        List<String> cmdsList = new LinkedList<>();
        cmdsList.add("C:\\Downloads\\tapiki.exe");

        if (!mInputFile.isEmpty()) {
            cmdsList.add("-i");
            cmdsList.add(SPACE);
            cmdsList.add(mInputFile);
            cmdsList.add(SPACE);
        }

        if (!mOutputFile.isEmpty()) {
            cmdsList.add("-o");
            cmdsList.add(SPACE);
            cmdsList.add(mOutputFile);
            cmdsList.add(SPACE);
        }

        try {
            Process proc = Runtime.getRuntime().exec((String[]) cmdsList.toArray());
            proc.waitFor();
        } catch (InterruptedException ie) {
            return false;
        } catch (IOException ioe) {
            return false;
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
