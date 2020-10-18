package com.example.delish;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.StringReader;
import java.io.StringWriter;

public class WriteToXML {
    public static final String filePath = "C:\\Users\\Deepti\\source\\repos\\Delish\\app\\src\\main\\java\\com\\example\\delish\\Foods.xml";
    public void writeToXMLFile() {
        try {

            /*
            SAMPLE SERIALIZATION:
                <food name="soup">
                    <calories>1000</calories>
                    <ingredient>potato</ingredient>
                    <ingredient>carrot</ingredient>
                </food>
             */

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);

            // Get food as the first node with elementId "soup"
            Node food = document.getElementById("soup");

            //add a new node "calories" to soup, and set it to 1000
            Element calories = document.createElement("calories");
            calories.appendChild(document.createTextNode("1000"));
            food.appendChild(calories);

            // add a new node "ingredient" to soup and set it to potato
            Element ingredient = document.createElement("ingredient");
            ingredient.appendChild(document.createTextNode("potato"));
            food.appendChild(ingredient);

            //add a new node "ingredient" to soup and set it to carrot
            Element ingredient2 = document.createElement("ingredient");
            ingredient2.appendChild(document.createTextNode("carrot"));
            food.appendChild(ingredient2);

            // loop through the food node and modify its children
            NodeList nodes = food.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node element = nodes.item(i);

                //remove an ingredient if it matches "potato"
                if ("potato".equals(element.getTextContent())) {
                    food.removeChild(element);
                }
            }

            // write the DOM object to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource domSource = new DOMSource(document);

            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("The XML File was ");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }
}
