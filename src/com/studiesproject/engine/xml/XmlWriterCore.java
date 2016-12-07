package com.studiesproject.engine.xml;
import com.sun.istack.internal.NotNull;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by mrlukashem on 29.11.16.
 */
public class XmlWriterCore {
    private DocumentBuilderFactory mFactory;
    private DocumentBuilder mBuilder;
    private Document mDoc;

    private String mOutputFileName = "output.xml";

    public XmlWriterCore() throws ParserConfigurationException {
        mFactory = DocumentBuilderFactory.newInstance();
        mBuilder = mFactory.newDocumentBuilder();
    }

    public void create(@NotNull String name) throws IOException, SAXException {
        mOutputFileName = name;

        File file = new File(mOutputFileName);
        mDoc = mBuilder.parse(file);
        mDoc.getDocumentElement().normalize();
    }

    public Element makeElement(String name) {
        return mDoc.createElement(name);
    }

    public Element makeAndAddElement(String name) {
        Element element = mDoc.createElement(name);
        mDoc.appendChild(element);

        return element;
    }

    public Attr makeAttr(String name) {
        return mDoc.createAttribute(name);
    }

    public Attr makeAndAddAttr(String name, String value, Element element) {
        Attr attr = mDoc.createAttribute(name);
        attr.setValue(value);
        return element.setAttributeNode(attr);
    }

    public Document getDocument() {
        return mDoc;
    }
}
