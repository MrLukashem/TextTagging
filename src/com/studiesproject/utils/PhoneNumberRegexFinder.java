package com.studiesproject.utils;

import java.util.Iterator;

/**
 * Created by mrlukashem on 20.11.16.
 */
public class PhoneNumberRegexFinder implements IRegexFinder {

    // xx xxx xx xx and +48 xx xxx xx xx
    private final String mRegex = "(\\+48\\s*)*[0-9]{2}\\s*[0-9]{3}\\s*[0-9]{2}\\s*[0-9]{2}";

    private final String TAG = "PN";

    protected boolean findPhoneNumberInString(String toCheck) {
        return toCheck.matches(mRegex);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public String whoAmI() {
        return RegexFinderTypes.PHONE_NUMBER_REGEX_FINDER;
    }

    @Override
    public boolean match(String toCheck) {
        return findPhoneNumberInString(toCheck);
    }
}
