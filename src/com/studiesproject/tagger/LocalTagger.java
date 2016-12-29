package com.studiesproject.tagger;

import com.studiesproject.engine.SentensesSplitter;
import com.studiesproject.engine.xml.XmlWriterCore;
import com.studiesproject.utils.*;
import com.sun.istack.internal.NotNull;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mrlukashem on 30.11.16.
 */
public class LocalTagger {
    private static final String TAG = "LocalTagger";
    private String mInFileName;
    private String mOutFileName;

    private final char ELEMENT_SEPARATOR = ' ';

    private List<IRegexFinder> mRegexFindersList = new ArrayList<>();

    // TODO: It is neceserry method?
    private void throwIfEmpty(@NotNull String testMe) throws IOException {
        if (testMe.isEmpty())
            throw new IOException("File name is not set");
    }

    private void setupFindersList() {
        mRegexFindersList.add(RegexFinderFactory.createFinder(RegexFinderTypes.DATE_REGEX_FINDER));
        mRegexFindersList.add(RegexFinderFactory.createFinder(RegexFinderTypes.PHONE_NUMBER_REGEX_FINDER));
        mRegexFindersList.add(RegexFinderFactory.createFinder(RegexFinderTypes.URL_REGEX_FINDER));
    }

    // Results last word in sentence.
    private String getLastElement(String sentence, int tryNumbers) {
        for (int i = sentence.length(); i >= 0; i--) {
            if (sentence.charAt(i) == ELEMENT_SEPARATOR) {
                if (tryNumbers == 0) {
                    return sentence.substring(i + 1);
                }

                --tryNumbers;
            }
        }

        return sentence;
    }

    private void putAdditionalTagsToFile(Document document) {
        NodeList nodeList = document.getElementsByTagName("element_name");
        mRegexFindersList.forEach(
                finder -> {
                    // To reduce references objects.
                    Node parent;
                    Element newElement;
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);
                        if (finder.match(node.getTextContent())) {
                            parent = node.getParentNode();
                            newElement = document.createElement(finder.getTag());
                            newElement.setTextContent(node.getTextContent());
                            parent.appendChild(newElement);
                        }
                    }
                }
        );
    }

    private boolean saveDocument(Document document) {
        try {
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer =
                    transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result =
                    new StreamResult(new File(mOutFileName));
            transformer.transform(source, result);
            // Output to console for testing
            StreamResult consoleResult =
                    new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (TransformerException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void modify(Document document, String oldTag, String newTag) {
        NodeList nodeList = document.getElementsByTagName(oldTag);
        Element element;
        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            document.renameNode(element, element.getNamespaceURI(), newTag);
        }
    }

    private void removeRedundant(Document document) {
        NodeList nodeList = document.getElementsByTagName("tok");
        NodeList children;
        Node node;
        Node child;
        boolean isFirstLexVisited = false;
        // O (n^2) : (
        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            children = node.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                child = children.item(j);
                if (child.getNodeName().equals("lex")) {
                    if (isFirstLexVisited) {
                        node.removeChild(child);
                    } else {
                        isFirstLexVisited = true;
                    }
                }
            }
            isFirstLexVisited = false;
        }
    }

    private boolean isProperNoun(Node content) {
        boolean isProperNoun =
                (content.getTextContent().length() > 0
                        && Character.isUpperCase(content.getTextContent().charAt(0)));

        if (isProperNoun) {
            if (content.getTextContent().equals("Microsoft")) {
                int i = 0;
            }
            NodeList list = content.getParentNode().getChildNodes();
            Node node;
            boolean oneLexOnly = true;

            for (int i = 0; i < list.getLength(); i++) {
                node = list.item(i);
                if (node.getNodeName().equals("lex")) {
                    Node ctag = node.getLastChild();
                    String s = ctag.getTextContent();
                    oneLexOnly &= s.contains("subst");
                }
            }

            return oneLexOnly;
        } else {
            return false;
        }
    }

    private boolean isTaggedAsProperNoun(Node parentNode) {
        NodeList childNodes = parentNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeName().equals("proper_noun")) {
                return true;
            }
        }

        return false;
    }

    private List<String> findFirstOccurrence(Document document) {
        List<String> resultList = new ArrayList<>(2);

        NodeList nodeList = document.getElementsByTagName("element_name");
        Node node;
        String content;

        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            if (isTaggedAsProperNoun(node.getParentNode())) {
                continue;
            }

            content = node.getTextContent();

            if (content.equals("Microsoft")) {
                int d = 0;
            }
            // Probably a proper noun if true.
            if (isProperNoun(node)) {
                resultList.add(content);
            } else {
                if (!resultList.isEmpty()) {
                    return resultList;
                }
            }
        }

        return resultList;
    }

    private boolean properNounExam(Document document, List<String> list) {
        NodeList nodeList = document.getElementsByTagName("element_name");
        String content;
        String listContent;
        Iterator<String> itr = list.iterator();
        boolean firstElement = true;

        for (int i = 0; i < nodeList.getLength(); i++) {
            content = nodeList.item(i).getTextContent();

            boolean hasNext = itr.hasNext();
            if (hasNext) {
                listContent = itr.next();
                if (firstElement && listContent.equals(content)) {
                    // First OK.
                    firstElement = false;
                    //     } else if (hasNext && itr.next().equals(content)){
                    //            Next OK.
                } else if (firstElement && !listContent.equals(content)) {
                    itr = list.iterator();
                } else if (!listContent.equals(content)) {
                    return false;
                }
            }

            if (!itr.hasNext()) {
                // Reset the iterator.
                itr = list.iterator();
                firstElement = true;
            }
        }

        return true;
    }

    private boolean applyProperNounTag(Document document, List<String> list) {
        NodeList nodeList = document.getElementsByTagName("element_name");
        Node parent;
        Element newElement;
        String content;
        Iterator<String> itr = list.iterator();
        boolean tagApplied = false;

        for (int i = 0; i < nodeList.getLength(); i++) {
            content = nodeList.item(i).getTextContent();

            if (!itr.hasNext()) {
                itr = list.iterator();
            }

            if (itr.hasNext()) {
                if (itr.next().equals(content)) {
                    parent = nodeList.item(i).getParentNode();
                    newElement = document.createElement("proper_noun");
                    newElement.setTextContent(content);
                    parent.appendChild(newElement);
                    tagApplied = true;
                } else {
                    itr = list.iterator();
                }
            }
        }

        return tagApplied;
    }

    private boolean findProperNouns(Document document) {
        List<String> properNoun = findFirstOccurrence(document);

        boolean pass = false;
        if (properNoun.size() > 0) {
            pass = properNounExam(document, properNoun);
        }

        return (pass && applyProperNounTag(document, properNoun));
    }

    private void prepareXmlFIle(Document document) {
        modify(document, "orth", "element_name");
        modify(document, "chunk", "sentence");
        modify(document, "tok", "element_desc");

        for(;;) {
            if (!findProperNouns(document))
                break;
        }

        removeRedundant(document);
    }

    public LocalTagger() {
        mInFileName = "input.xml";
        mOutFileName = "output.xml";

        setupFindersList();
    }

    public LocalTagger(@NotNull String in, @NotNull String out) {
        mInFileName = in;
        mOutFileName = out;

        setupFindersList();
    }

    public boolean startProcessing() throws IOException, TransformerException {
        XmlWriterCore writerCore = null;
        Document doc;
        try {
            writerCore = new XmlWriterCore();
            writerCore.create("spidersweb_article_out.xml");
            doc = writerCore.getDocument();

            prepareXmlFIle(doc);
            putAdditionalTagsToFile(doc);

            saveDocument(doc);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            Log.e(TAG, "Error while processing");
            return false;
        } catch (SAXException e) {
            e.printStackTrace();
            Log.e(TAG, "Error while processing");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error while processing");
            return false;
        }

        return true;
    }
}
