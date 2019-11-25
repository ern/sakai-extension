package org.sakaiproject.kaltura.conversion.dom;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.sakaiproject.kaltura.conversion.SakaiRepository;
import org.sakaiproject.kaltura.conversion.serialize.SerializationFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CommonNode {

    protected Node node;

    @Setter protected static SerializationFactory factory;
    @Setter protected static SakaiRepository repository;

    public CommonNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(node), new StreamResult(writer));
            writer.flush();
            return writer.toString();
        } catch (Exception e) {
            log.warn("XML Node parsing failure, {}", e.getMessage());
        }
        return "";
    }

    public Node toNode(String xml) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            return doc.getFirstChild();
        } catch (Exception e) {
            log.warn("XML Node parsing failure, {}", e.getMessage());
        }
        return null;
    }
}
