package com.studiesproject.engine;

import com.sun.istack.internal.NotNull;

import java.io.IOException;

/**
 * Created by mrlukashem on 21.11.16.
 *
 * The class works similar to iterator.
 */
public class SentensesSplitter {
    private boolean mHasNext = false;
    private SplitterEngine mEngine = new SplitterEngine();
    private String mBuffer = "";
    // We set true even during object construction because we want to force to get next buffer.
    private boolean mNextCalled = true;

    public boolean setDataSourceAndPrepare(@NotNull String source) {
        return mEngine.setSourceFileAndPrepare(source);
    }

    // Do we have a next sentence?
    public boolean hasNext() {
        try {
            // A API user did't call next() yet? If he didn't, we has mBuffer filled already.
            if (!mNextCalled) {
                return true;
            }

            // Get a next sentence from engine.
            mBuffer = mEngine.getNextSentense();
            // We received new buffer so mNextCalled have to set to false.
            mNextCalled = false;
            // Return mBuffer value, if it is empty, it means we has read all source.
            return !mBuffer.isEmpty();
        } catch (IOException ioe) {
            // TODO: any stack trace? any log?
            return false;
        }
    }

    // maybe returns bool?
    public String next() {
        mNextCalled = true;
        return mBuffer;
    }
}
