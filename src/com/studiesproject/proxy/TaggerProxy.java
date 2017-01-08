package com.studiesproject.proxy;

import com.studiesproject.utils.Log;
import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mrlukashem on 21.11.16.
 */
public class TaggerProxy {

    private static final int NO_ERROR = 0;

    private static final String TAG = "TaggerProxy";

    private static final String SPACE = " ";

    private String mInputFile = "";

    private String mOutputFile = "";

    public void setInputFile(@NotNull String file) {
        mInputFile = file;
    }

    public void setOutputFile(@NotNull String file) {
        mOutputFile = file;
    }

    public String getInputFile() {
        return mInputFile;
    }

    public String getOutputFile() {
        return mOutputFile;
    }

    public boolean blockRun() {
        List<String> cmdsList = new LinkedList<>();
        cmdsList.add("C:\\Users\\MrLukashem\\Downloads\\TaKIPI18\\TaKIPI18\\Windows\\takipi.exe");

        if (!mInputFile.isEmpty()) {
            cmdsList.add("-i");
            cmdsList.add(mInputFile);
        }

        if (!mOutputFile.isEmpty()) {
            cmdsList.add("-o");
            cmdsList.add(mOutputFile);
        }

        try {
            String[] sArray = new String[cmdsList.size()];
            sArray = cmdsList.toArray(sArray);

            ProcessBuilder builder = new ProcessBuilder(sArray);
            builder.directory(new File("C:\\Users\\MrLukashem\\Downloads\\TaKIPI18\\TaKIPI18\\Windows\\"));
            Log.v(TAG, "proccess dir = " + builder.directory().getAbsolutePath());
            Process proc = builder.start(); //Runtime.getRuntime().exec(sArray);

     //       proc.waitFor();

            Log.v(TAG, "Program terminated!");
            BufferedReader br = new BufferedReader(new InputStreamReader (proc.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                Log.v(TAG, line);
            }

            int err = proc.exitValue();
            if (err != NO_ERROR) {
                Log.e(TAG, "error code = " + err);
            }

            return err == NO_ERROR;
     //   } catch (InterruptedException ie) {
      //      Log.e(TAG, ie.getMessage());
       //     return false;
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage());
            return false;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }
}
