package com.studiesproject.utils;

/**
 * Created by mrlukashem on 20.11.16.
 */
public class DateRegexFinder implements IRegexFinder {
    @Override
    public boolean putTagIfRegexFound(TaggedItemsArray tiarray) {
        return false;
    }

    @Override
    public String whoAmI() {
        return RegexFinderTypes.DATE_REGEX_FINDER;
    }
}
