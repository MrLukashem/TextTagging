package com.studiesproject.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mrlukashem on 20.11.16.
 */
public class TaggedItemsArray {
    private int INITIAL_CAPACITY = 100;

    // TODO: Switch to Pair<K, L> List rather then two lists. But maybe below solution is more performance solution?
    private List<String> mItems = new ArrayList<>(INITIAL_CAPACITY);

    private List<String> mTags = new ArrayList<>(INITIAL_CAPACITY);

    public TaggedItemsArray() {
    }

    public boolean put(String item, String tag) {
        // TODO: We shouldn't add tag if item failed while add operation.
        return mItems.add(item) && mTags.add(tag);
    }

    // TODO: Try to find better solution for below method.
    public void remove(String item) {
        int idx = -1;
        assert mItems.size() == mTags.size();

        while((idx = mItems.indexOf(item)) >= 0) {
            mItems.remove(idx);
            mTags.remove(idx);
        }
    }

    public void modify(String item, String newTag) {
        int idx = -1;
        assert mItems.size() == mTags.size();

        for (String it : mItems) {
            if (it.equals(item)) {
                mTags.set(idx, newTag);
            }

            ++idx;
        }
    }

    // TODO: Same as above.
    public List<String> findAllItemsWithTag(String tagToFind) {
        List<String> mResultList = new ArrayList<>();
        int idx = -1;
        assert mItems.size() == mTags.size();

        for (String tag : mTags) {
            if (tag.equals(tagToFind)) {
                idx = mTags.indexOf(tag);
                mResultList.add(mItems.get(idx));
            }
        }

        return mResultList;
    }

    public Iterator<String> getIterator() {
        return mItems.iterator();
    }
}
