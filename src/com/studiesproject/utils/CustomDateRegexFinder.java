package com.studiesproject.utils;

import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by mrlukashem on 29.12.16.
 */
public class CustomDateRegexFinder implements IRegexFinder {

    private final boolean DEBUG = true;

    private final String TAG = "cdate";

    private String[] mRegexArray = {
            "(((([0-2]*[0-9])|30|31)\\s)?(s|S)(tyczeń|tycznia|tyczniowi|tyczniem|tyczniu))(\\s[0-9]+)?",
            "(((([0-2]*[0-9])|30|31)\\s)?(l|L)(uty|utego|utemu|utym))(\\s[0-9]+)?",
            "(((([0-2]*[0-9])|30|31)\\s)?(m|M)(arzec|arca|arcowi|arcem|arcu))(\\s[0-9]+)?",
            "(((([0-2]*[0-9])|30|31)\\s)?(k|K)(wiecień|wietnia|wietniowi|wietniem|wietniu))(\\s[0-9]+)?",
            "(((([0-2]*[0-9])|30|31)\\s)?(m|M)(aj|aja|ajowi|ajem|aju))(\\s[0-9]+)?",
            "(((([0-2]*[0-9])|30|31)\\s)?(c|C)(zerwiec|zerwca|zerwcowi|zerwcem|zerwcu))(\\s[0-9]+)?",
            "(((([0-2]*[0-9])|30|31)\\s)?(l|L)(ipiec|ipca|ipcowi|ipcem|ipcu))(\\s[0-9]+)?",
            "(((([0-2]*[0-9])|30|31)\\s)?(s|S)(ierpień|ierpnia|ierpniowi|ierpniem|ierpniu))(\\s[0-9]+)?",
            "(((([0-2]*[0-9])|30|31)\\s)?(w|W)(rzesień|rześnia|rześniowi|rzesniem|rześniu))(\\s[0-9]+)?",
            "(((([0-2]*[0-9])|30|31)\\s)?(p|P)(aździernik|aździernika|aździernikowi|aździernikiem|aździerniku))(\\s[0-9]+)?",
            "(((([0-2]*[0-9])|30|31)\\s)?(l|L)(istopad|istopada|istopadowi|istopadem|istopadzie))(\\s[0-9]+)?",
            "(((([0-2]*[0-9])|30|31)\\s)?(g|G)(rudzień|rudnia|rudniowi|rudniem|rudniu))(\\s[0-9]+)?",
    };

    private boolean isCDataApplied(Node node) {
        NodeList children = node.getChildNodes();

        Node child;
        for (int i = 0; i < children.getLength(); i++) {
            child = children.item(i);
            if (child.getNodeName().equals(TAG)) {
                return true;
            }
        }

        return false;
    }

    private Node getElementNameChild(Node node) {
        NodeList children = node.getChildNodes();

        Node child;
        for (int i = 0; i < children.getLength(); i++) {
            child = children.item(i);
            if (child.getNodeName().equals("element_name")) {
                return child;
            }
        }

        return null;
    }

    private String getElementNameContentFromLex(Node lex) {
        Node elementDesc;
        Node elementName;
        String content = "";

        elementDesc = lex.getParentNode();
        elementName = getElementNameChild(elementDesc);
        if (elementName != null) {
            content += elementName.getTextContent();
        }

        return content;
    }

    private boolean applyTagAndRemoveChildren(Document document, Node nodeToApplyTag, Node typeNode, String content
            , Node... nodes) {

        if (!nodeToApplyTag.getNodeName().equals("element_desc") && !typeNode.getNodeName().equals("element_type")) {
            if (DEBUG) {
                Assert.assertTrue(false);
            }

            return false;
        }

        Node cDataNodeTag = document.createElement(TAG);
        cDataNodeTag.setTextContent(content);
        nodeToApplyTag.appendChild(cDataNodeTag);
        typeNode.setTextContent(TAG);

        NodeList children = nodeToApplyTag.getChildNodes();
        Node child = null;
        for (int i = 0; i < children.getLength(); i++) {
            if ((child = children.item(i)).getNodeName().equals("element_name")) {
                child.setTextContent(content);
            }
        }

        // Remove redudant.
        for (Node toRemove : nodes) {
            toRemove.getParentNode().removeChild(toRemove);
        }

        return true;
    }

    private boolean findCustomDateInString(String toCheck) {
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

    public boolean applyTagsToXmlFile(Document document) {
        NodeList nodeList = document.getElementsByTagName("element_type");
        String content;
        Node lex;
        Node tempPrevious;
        Node tempThird;
        Node tempSecond;
        Node tempFirst;
        boolean found;

        // First process dates with numbers example: 23 Styczeń 1992.
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (isCDataApplied(nodeList.item(i))) {
                continue;
            }

            content = nodeList.item(i).getTextContent();
            if (content.equals("tnum:integer")) { // Perhaps it is a date.
                // First case.
                int thirdElementOfDateIdx = i + 2;
                if (nodeList.getLength() > thirdElementOfDateIdx) {
                    // i + 2 element. Example: 11 stycznia 1992; 1992 is third element.
                    tempFirst = nodeList.item(thirdElementOfDateIdx - 2);
                    tempSecond = nodeList.item(thirdElementOfDateIdx - 1);
                    tempThird = nodeList.item(thirdElementOfDateIdx);
                    if (tempThird.getTextContent().equals("tnum:integer") && !isCDataApplied(tempThird)
                            && !isCDataApplied(tempThird)) {

                        String dateContentString = "";

                        lex = tempFirst.getParentNode();
                        dateContentString += getElementNameContentFromLex(lex);
                        dateContentString += " ";

                        lex = tempSecond.getParentNode();
                        dateContentString += getElementNameContentFromLex(lex);
                        dateContentString += " ";

                        lex = tempThird.getParentNode();
                        dateContentString += getElementNameContentFromLex(lex);

                        found = findCustomDateInString(dateContentString);
                        if (found) {
                            Node elementDescFirst = tempFirst.getParentNode().getParentNode();
                            Node elementDescSecond = tempSecond.getParentNode().getParentNode();
                            Node elementDescThird = tempThird.getParentNode().getParentNode();
                            applyTagAndRemoveChildren(document, elementDescFirst, tempFirst, dateContentString,
                                    elementDescThird, elementDescSecond);

                            i = i + 2;
                            continue;
                        }
                    }
                }

                // styczen 1992
                int secondElementOfDateIdx = i + 1;
                if (nodeList.getLength() > secondElementOfDateIdx) {

                    // i + 1.
                    tempFirst = nodeList.item(secondElementOfDateIdx - 1);
                    tempSecond = nodeList.item(secondElementOfDateIdx);

                    if (!isCDataApplied(tempFirst) && !isCDataApplied(tempSecond)) {
                        String dateContentString = "";

                        lex = tempFirst.getParentNode();
                        dateContentString += getElementNameContentFromLex(lex);
                        dateContentString += " ";

                        lex = tempSecond.getParentNode();
                        dateContentString += getElementNameContentFromLex(lex);

                        found = findCustomDateInString(dateContentString);
                        if (found) {
                            Node elementDescFirst = tempFirst.getParentNode().getParentNode();
                            Node elementDescSecond = tempSecond.getParentNode().getParentNode();
                            applyTagAndRemoveChildren(document, elementDescFirst, tempFirst, dateContentString
                                    , elementDescSecond);

                            i = i + 1;
                            continue;
                        }
                    }
                }

                // 22 stycznia
                int previousElementOfDateIdx = i - 1;
                if (previousElementOfDateIdx >= 0) {
                    tempPrevious = nodeList.item(previousElementOfDateIdx);
                    tempFirst = nodeList.item(previousElementOfDateIdx + 1);

                    content = tempFirst.getTextContent();
                    if (content.equals("tnum:integer") && !isCDataApplied(tempPrevious)
                            && !tempPrevious.getTextContent().equals("tnum:integer")) {
                        String dateContentString = "";

                        lex = tempPrevious.getParentNode();
                        dateContentString += getElementNameContentFromLex(lex);
                        dateContentString += " ";

                        lex = tempFirst.getParentNode();
                        dateContentString += getElementNameContentFromLex(lex);

                        found = findCustomDateInString(dateContentString);
                        if (found) {
                            Node elementDescFirst = tempFirst.getParentNode().getParentNode();
                            Node elementDescPrevious = tempPrevious.getParentNode().getParentNode();
                            applyTagAndRemoveChildren(document, elementDescPrevious, tempPrevious, dateContentString
                                    , elementDescFirst);
                        }
                    }
                }


            }

        }

        // Proccess dates with no numbers example: w Styczniu było zimno

        return false;
    }
}
