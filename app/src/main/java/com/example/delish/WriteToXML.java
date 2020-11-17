package com.example.delish;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToXML extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static void writeNewFoodToXml(Food food) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(context.getResources().openRawResource(R.raw.food_inventory));

            Node rootElement = document.getDocumentElement();

            //add a new empty food item
            Element newFood = document.createElement("food");

            //add a name element to the parent node
            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(food.getName()));
            newFood.appendChild(name);

            //add a calorie element to the parent node
            Element calories = document.createElement("calories");
            calories.appendChild(document.createTextNode(Integer.toString(food.getCalories())));
            newFood.appendChild(calories);

            //attach the new food item to the root node
            rootElement.appendChild(newFood);

            //TODO: write the DOM object to the file
            String newFoodStr = newFood.toString();
            Toast.makeText(context, newFoodStr, Toast.LENGTH_LONG).show();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Food getFoodFromName(String name) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(context.getResources().openRawResource(R.raw.food_inventory));

            XPathFactory xpathfactory = XPathFactory.newInstance();
            XPath xpath = xpathfactory.newXPath();
            XPathExpression xpathExpression = xpath.compile("/food_inventory/food[@name='" + name + "']/calories");
            Element calories = (Element)xpathExpression.evaluate(document, XPathConstants.NODE);
            int cal = Integer.parseInt(calories.getTextContent());

            return new Food(name, cal);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //check if the node exists in the given document
    public static boolean nodeExists(String xpathExpression) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(context.getResources().openRawResource(R.raw.food_inventory));

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        boolean matches = false;

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
