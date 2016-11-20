package com.studiesproject.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 20.11.16.
 */
public class DateRegexFinder implements IRegexFinder {

    protected final String[] mRegexArray = {
            // Y:M:D date format.
            "[0-9]+:((0*[1-9])|10|11|12):(([0-2]*[0-9])|30|31)",

            // Y-M-D date format.
            "[0-9]+-((0*[1-9])|10|11|12)-(([0-2]*[0-9])|30|31)",

            // D:M:Y date format.
            "(([0-2]*[0-9])|30|31):((0*[1-9])|10|11|12):[0-9]+",

            // D-M-Y date format.
            "(([0-2]*[0-9])|30|31)-((0*[1-9])|10|11|12)-[0-9]+",

            // D.M.Y date format.
            "(([0-2]*[0-9])|30|31)\\.((0*[1-9])|10|11|12)\\.[0-9]+",

            // Y.M.D date format.
            "[0-9]+\\.((0*[1-9])|10|11|12)\\.(([0-2]*[0-9])|30|31)",

            // D/M/Y date format.
            "(([0-2]*[0-9])|30|31)/((0*[1-9])|10|11|12)/[0-9]+",

            // Y.M.D date format.
            "[0-9]+/((0*[1-9])|10|11|12)/(([0-2]*[0-9])|30|31)",
    };

    protected boolean findDateInString(String toCheck) {
        // Temp. version
        boolean result = false;
        int i = 0;

        while (i < mRegexArray.length &&
                !(result = toCheck.matches(mRegexArray[i])))
            ++i;

        return result;
    }

    @Override
    public boolean putTagIfRegexFound(TaggedItemsArray tiarray) {
        return false;
    }

    @Override
    public String whoAmI() {
        return RegexFinderTypes.DATE_REGEX_FINDER;
    }

    @Override
    public boolean match(String toCheck) {
        return findDateInString(toCheck);
    }
}
