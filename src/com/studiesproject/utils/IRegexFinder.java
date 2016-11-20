package com.studiesproject.utils;

/**
 * Created by mrlukashem on 20.11.16.
 */
public interface IRegexFinder {
    // Put tag for a item in tiarray if the item match to a regex.
    boolean putTagIfRegexFound(TaggedItemsArray tiarray);

    // Returns kind / name of regex finder.
    String whoAmI();

    boolean match(String toCheck);
}
