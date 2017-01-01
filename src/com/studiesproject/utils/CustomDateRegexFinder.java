package com.studiesproject.utils;

/**
 * Created by mrlukashem on 29.12.16.
 */
public class CustomDateRegexFinder implements IRegexFinder {

    private final String TAG = "cdate";

    private String[] mRegexArray = {
            "(((([0-2]*[0-9])|30|31)\\s)?(s|S)(tyczeń|tycznia|tyczniowi|tyczniem|tyczniu))",
            "(((([0-2]*[0-9])|30|31)\\s)?(l|L)(uty|utego|utemu|utym))",
            "(((([0-2]*[0-9])|30|31)\\s)?(m|M)(arzec|arca|arcowi|arcem|arcu))",
            "(((([0-2]*[0-9])|30|31)\\s)?(k|K)(wiecień|wietnia|wietniowi|wietniem|wietniu))",
            "(((([0-2]*[0-9])|30|31)\\s)?(m|M)(aj|aja|ajowi|ajem|aju))",
            "(((([0-2]*[0-9])|30|31)\\s)?(c|C)(zerwiec|zerwca|zerwcowi|zerwcem|zerwcu))",
            "(((([0-2]*[0-9])|30|31)\\s)?(l|L)(ipiec|ipca|ipcowi|ipcem|ipcu))",
            "(((([0-2]*[0-9])|30|31)\\s)?(s|S)(ierpień|ierpnia|ierpniowi|ierpniem|ierpniu))",
            "(((([0-2]*[0-9])|30|31)\\s)?(w|W)(rzesień|rześnia|rześniowi|rzesniem|rześniu))",
            "(((([0-2]*[0-9])|30|31)\\s)?(p|P)(aździernik|aździernika|aździernikowi|aździernikiem|aździerniku))",
            "(((([0-2]*[0-9])|30|31)\\s)?(l|L)(istopad|istopada|istopadowi|istopadem|istopadzie))",
            "(((([0-2]*[0-9])|30|31)\\s)?(g|G)(rudzień|rudnia|rudniowi|rudniem|rudniu))",
    };

    protected boolean findCustomDateInString(String toCheck) {
        boolean result = false;
        int i = 0;

        while (i < mRegexArray.length &&
                !(result = toCheck.matches(mRegexArray[i])))
            ++i;

        return result;
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public String whoAmI() {
        return RegexFinderTypes.CUSTOM_DATE_REGEX_FINDER;
    }

    @Override
    public boolean match(String toCheck) {
        return findCustomDateInString(toCheck);
    }
}
