package com.studiesproject.utils;

import java.util.Iterator;

/**
 * Created by mrlukashem on 20.11.16.
 */
public interface IRegexFinder {
    String getTag();

    // Put tag for a item in tiarray if the item match to a regex.
    default boolean putTagIfRegexFound(TaggedItemsArray tiarray) {
        Iterator<String> itr = tiarray.getIterator();
        boolean anyRegexFound = false;

        while (itr.hasNext()) {
            String item = itr.next();

            if (match(item)) {
                tiarray.modify(item, getTag());
                anyRegexFound = true;
            }
        }

        return anyRegexFound;
    }

    // Returns kind / name of regex finder.
    String whoAmI();

    boolean match(String toCheck);
}
