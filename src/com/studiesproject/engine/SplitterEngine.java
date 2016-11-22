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

    public void setSourceFile(@NotNull String fileName) {
        mFileName = fileName;
    }

    public String getNextSentense() {
        try (InputStream in = Files.newInputStream(Paths.get(mFileName));
             BufferedReader bf = new BufferedReader(new InputStreamReader(in))) {
            String line;

            while ((line = bf.readLine()) != null) {
                line
                System.out.println(line);
            }
        } catch (IOException ioe) {
            System.out.println("IOException in SplitterEngine::getNextSentense");
        }

        return null;
    }
}
