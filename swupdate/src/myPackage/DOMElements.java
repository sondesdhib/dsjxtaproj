package myPackage;

import java.io.*;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class DOMElements{
	static DocumentBuilderFactory factory;
	static Document doc;
	static DocumentBuilder builder;
	NodeList VersionList;
	HashMap<String, String> VersionDirMap;
	HashMap<String, String> VersionDepMap;
   void Parse(String xmlFile){
     try {
       File file = new File(xmlFile);
       
       if(file.exists()){
         // Create a factory
    	   factory = DocumentBuilderFactory.newInstance();
    	   builder = factory.newDocumentBuilder();
    	   
    	   doc = builder.parse(new File(xmlFile));
    	   doc.getDocumentElement().normalize();
 
         // Get a list of all elements in the document
         NodeList node = doc.getElementsByTagName("Version");       
         //System.out.println("Nodes in Version details - " + node.getLength());
         //System.out.println("XML Elements: ");
         VersionDirMap = new HashMap<String, String>();
         VersionDepMap = new HashMap<String, String>();
         
         for (int i = 0; i < node.getLength(); i++) {
        	  Node firstNode = node.item(i);
        	  //System.out.println("Workis");
        	  if (firstNode.getNodeType() == Node.ELEMENT_NODE) 
        	  {
        	  
        	  Element element = (Element) firstNode;
        	  NodeList VersionElements = element.getElementsByTagName("Version_Id");
        	  Element velement = (Element) VersionElements.item(0);
        	  NodeList vid = velement.getChildNodes();
        	  //System.out.println("Version ID:"+ (vid.item(0).getNodeValue()));
        	      
        	  NodeList vrootList = element.getElementsByTagName("Version_Root");
        	  Element vrootelement = (Element) vrootList.item(0);
        	  NodeList vroot = vrootelement.getChildNodes();
        	  //System.out.println("Version Root:"+(vroot.item(0).getNodeValue()));

        	  NodeList deplist = element.getElementsByTagName("Dependancy");
        	  Element depelement = (Element) deplist.item(0);
        	  NodeList vdep = depelement.getChildNodes();
        	  //System.out.println("Dependancy: "  + (vdep.item(0)).getNodeValue());
        	  
        	  VersionDepMap.put(vid.item(0).getNodeValue(), vdep.item(0).getNodeValue());
        	  VersionDirMap.put(vid.item(0).getNodeValue(), vroot.item(0).getNodeValue());
        	 }
        	}
       }
       else{
         System.out.print("File not found!");
       }
     }
     catch (Exception e) {
       System.out.println(e.toString());
     }
   }
   
   HashMap<String,String> GetDir()
   {
	   return VersionDirMap;
   }
   
   HashMap<String,String> GetDependancy()
   {
	   return VersionDirMap;
   }
   
}