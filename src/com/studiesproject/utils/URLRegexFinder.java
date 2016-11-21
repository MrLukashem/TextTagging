package com.studiesproject.utils;

/**
 * Created by MrLukashem on 21.11.2016.
 */
public class URLRegexFinder implements IRegexFinder {

    private final String TAG = "URL";

    private final String mRegex = "http://[a-zA-Z0-9]]*";

    private boolean findURLinString(String toCheck) {
        return toCheck.matches(mRegex);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public String whoAmI() {
        return RegexFinderTypes.URL_REGEX_FINDER;
    }

    @Override
    public boolean match(String toCheck) {
        return findURLinString(toCheck);
    }
}
