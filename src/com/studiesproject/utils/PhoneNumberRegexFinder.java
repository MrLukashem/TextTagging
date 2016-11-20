package com.studiesproject.utils;

/**
 * Created by mrlukashem on 20.11.16.
 */
public class PhoneNumberRegexFinder implements IRegexFinder {
    @Override
    public boolean putTagIfRegexFound(TaggedItemsArray tiarray) {
        return false;
    }

    @Override
    public String whoAmI() {
        return RegexFinderTypes.PHONE_NUMBER_REGEX_FINDER;
    }
}
