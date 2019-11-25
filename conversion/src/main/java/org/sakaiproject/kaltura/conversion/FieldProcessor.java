package org.sakaiproject.kaltura.conversion;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.sakaiproject.kaltura.conversion.config.Field;
import org.sakaiproject.kaltura.conversion.dom.MediaNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FieldProcessor {

    @Autowired private SakaiRepository repository;

    public void convert(Field field) {
        for (Object id : field.getIds()) {
            String xml = repository.executeFieldDataQuery(field, id);
            String updatedXML = updateDocument(xml);
            repository.executeSaveFieldData(field, id, updatedXML);
        }
    }

    public String updateDocument(String data) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(data)));
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodes = (NodeList) xPath.evaluate("//span[@class='kaltura-lti-media']", doc, XPathConstants.NODESET);

            IntStream.range(0, nodes.getLength()).forEach(i -> {
                Node oldNode = nodes.item(i);
                Node parentNode = oldNode.getParentNode();
                if (parentNode != null) {
                    Node newNode = new MediaNode(oldNode).convertMedia();
                    Node imported = doc.importNode(newNode, true);
                    parentNode.replaceChild(imported, oldNode);
                }
            });

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            writer.flush();
            return writer.toString();
        } catch (Exception e) {
            log.warn("XML parsing failure, {}", e.getMessage());
        }
        return null;
    }

    public static String getSearchQuery(Field field) {
        StringBuilder sb = new StringBuilder("SELECT ");
        sb.append(field.getIdentifier());
        sb.append(" FROM ");
        sb.append(field.getTable());
        sb.append(" WHERE ");
        sb.append(field.getName());
        sb.append(" ");
        sb.append(field.getCriteria());
        return sb.toString();
    }

    public static String getDataQuery(Field field, Object id) {
        StringBuilder sb = new StringBuilder("SELECT ");
        sb.append(field.getName());
        sb.append(" FROM ");
        sb.append(field.getTable());
        sb.append(" WHERE ");
        sb.append(field.getIdentifier());
        sb.append(" = ");
        sb.append(id.toString());
        return sb.toString();
    }

    public static String getSaveDataQuery(Field field, Object id) {
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(field.getTable());
        sb.append(" SET ");
        sb.append(field.getName());
        sb.append(" = ? WHERE ");
        sb.append(field.getIdentifier());
        sb.append(" = ");
        sb.append(id.toString());
        return sb.toString();
    }
}
