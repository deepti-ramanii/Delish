package com.example.delish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

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

import org.w3c.dom.Attr;
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
            /*
            SAMPLE SERIALIZATION:
            <food_list>                         -> root
                <food>                          -> parentNode
                    <name>soup</name>           -> element
                    <calories>1000</calories>
                    <ingredient>potato</ingredient>
                    <ingredient>carrot</ingredient>
                </food>
            </food_list>

             */

    public static boolean editIngredientsInXml(Food food, String changedIngredient, boolean isAdded ) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            String xpathStr = "";

            //if the node doesn't exist, exit and return false
            xpathStr = "/food_list/food[name = " + food.getName() + " ]";
            if(!checkIfNodeExists(document, xpathStr)) {
                return false;
            }

            Node parentFood = (Node) xpath.evaluate(xpathStr, document, XPathConstants.NODE);

            //add the ingredient
            if(isAdded) {
                Node newIngredient = document.createElement("ingredient");
                newIngredient.appendChild(document.createTextNode(changedIngredient));
                parentFood.appendChild(newIngredient);
            }

            //remove the ingredient if it exists
            else {
                xpathStr = "/food_list/food[ingredient = '" + changedIngredient + "']";
                //if the ingredient doesn't exist, exit and return false
                if(!checkIfNodeExists(document, xpathStr)) {
                    return false;
                }

                Node ingredient = (Node)xpath.evaluate(xpathStr, document, XPathConstants.NODE);
                parentFood.removeChild(ingredient);
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
        return true;
    }

    public static boolean writeNewFoodToXml(Food food) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);

            String xpathStr = "";

            Node rootElement = document.getElementsByTagName("food_list").item(0);

            //if the food already exists, return false and exit
            xpathStr = "/food_list/food[name = " + food.getName() + " ]";
            if(checkIfNodeExists(document, xpathStr)) {
                return false;
            }

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
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private static boolean checkIfNodeExists(Document document, String xpathExpression) throws Exception
    {
        boolean matches = false;

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
