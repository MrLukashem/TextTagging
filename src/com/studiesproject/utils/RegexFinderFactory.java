package com.studiesproject.utils;

/**
 * Created by mrlukashem on 30.11.16.
 */
public class RegexFinderFactory {
    public static IRegexFinder createFinder(String tag) {
        IRegexFinder finder = null;
        switch (tag) {
            case RegexFinderTypes.DATE_REGEX_FINDER:
                finder = new DateRegexFinder();
                break;
            case RegexFinderTypes.PHONE_NUMBER_REGEX_FINDER:
                finder = new PhoneNumberRegexFinder();
                break;
            case RegexFinderTypes.URL_REGEX_FINDER:
                finder = new URLRegexFinder();
                break;
        }

        // Change to Null Object.
        return finder;
    }
}
