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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

public class WriteToXML {
    public static final String filePath = "Foods.xml";

    public static void writeNewFoodToXml(Food food) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);

            Node rootElement = document.getElementsByTagName("food_list").item(0);

            Node parentFood = document.createElement("food");
            rootElement.appendChild(parentFood);

            // set element name to parent food
            Node name = document.createElement("name");
            name.appendChild(document.createTextNode(food.getName()));
            parentFood.appendChild(name);

            // set element calories to root element
            Node calories = document.createElement("calories");
            calories.appendChild(document.createTextNode("" + food.getCaloriesPerServing()));
            parentFood.appendChild(calories);

            Node servings = document.createElement("servings");
            name.appendChild(document.createTextNode("" + food.getNumberOfServings()));
            parentFood.appendChild(name);

            for(String ingredient : food.getIngredients()) {
                Node temp = document.createElement("ingredient");
                temp.appendChild(document.createTextNode(ingredient));
                parentFood.appendChild(temp);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Food getFoodFromName(String name) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            String xpathStr = "//food[name='" + name + "']";
            if (!nodeExists(xpathStr)) {
                return null;
            }

            Node parentFood = (Node) xpath.evaluate(xpathStr, document, XPathConstants.NODE);

            Node calories = parentFood.getFirstChild().getNextSibling();
            int cal = Integer.parseInt(calories.getTextContent());

            Node servings = parentFood.getFirstChild().getNextSibling();
            int s = Integer.parseInt(servings.getTextContent());

            Node ingredient = calories.getNextSibling();
            List<String> ingredients = new ArrayList<String>();
            while (ingredient != null) {
                ingredients.add(ingredient.getTextContent());
                ingredient = ingredient.getNextSibling();
            }

            return new Food(name, cal, s, ingredients);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean nodeExists(String xpathExpression) throws Exception
    {
        boolean matches = false;

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(filePath);

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        try {
            XPathExpression expr = xpath.compile(xpathExpression);
            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            if(nodes != null  && nodes.getLength() > 0) {
                matches = true;
            }

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return matches;
    }
}
