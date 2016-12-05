package com.studiesproject.tagger;

import com.studiesproject.engine.SentensesSplitter;
import com.studiesproject.utils.IRegexFinder;
import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 30.11.16.
 */
public class LocalTagger {
    private String mInFileName;
    private String mOutFileName;

    private List<IRegexFinder> mRegexFindersList = new ArrayList<>();

    // TODO: It is neceserry method?
    private void throwIfEmpty(@NotNull String testMe) throws IOException {
        if (testMe.isEmpty())
            throw new IOException("File name is not set");
    }

    public LocalTagger() {}

    public LocalTagger(@NotNull String in, @NotNull String out) {
        mInFileName = in;
        mOutFileName = out;
    }

    public boolean saveDocument() throws IOException {
        return false;
    }

    public boolean startProcessing() throws IOException {
        SentensesSplitter splitter = new SentensesSplitter();
        splitter.setDataSourceAndPrepare(mOutFileName);

        String sentenceRef = null;
        while (splitter.hasNext()) {
            sentenceRef = splitter.next();

            // TODO: Do we need below check?
            if (sentenceRef != null) {
                mRegexFindersList.forEach(
                        (IRegexFinder finder) -> {
                            
                        }
                );
            }
        }

        return false;
    }
}
