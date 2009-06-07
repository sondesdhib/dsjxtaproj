package myPackage;

import java.io.*;
import javax.xml.transform.stream.StreamResult; 
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource; 


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
    	   
    	   doc = builder.parse(new File("Version.xml"));
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
	        	  String version_id = velement.getAttribute("Version_Id");
	        	  
        	 
        	  System.out.println("Version ID:"+ version_id);
        	      
        	  NodeList vrootList = element.getElementsByTagName("Version_Root");
        	  Element vrootelement = (Element) vrootList.item(0);
        	  String versionRoot = vrootelement.getAttribute("Version_Root");
        	  //System.out.println("Version Root:"+(vroot.item(0).getNodeValue()));

        	  NodeList deplist = element.getElementsByTagName("Dependency");
        	  Element depelement = (Element) deplist.item(0);
        	  String dependency = depelement.getAttribute("Dependency");
        	  //System.out.println("Dependancy: "  + (vdep.item(0)).getNodeValue());
        	  
        	  VersionDepMap.put(version_id, dependency);
        	  VersionDirMap.put(version_id, versionRoot);
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
   
   static void insertVersion(String version,String root,String dependency)
   {
	   try {
	       File file = new File("Version.xml");
	       
	       if(file.exists()){
	         // Create a factory
	    	   factory = DocumentBuilderFactory.newInstance();
	    	   builder = factory.newDocumentBuilder();
	    	   
	    	   doc = builder.parse(file);
	    	   doc.getDocumentElement().normalize();
	 
	         // Get a list of all elements in the document
	         NodeList node = doc.getElementsByTagName("Version");
	         boolean found=false;
	         for(int i = 0; i < node.getLength(); i++)
	         {
	        	 Node firstNode = node.item(i);
	        	  //System.out.println("Workis");
	        	  if (firstNode.getNodeType() == Node.ELEMENT_NODE) 
	        	  {
	        	  
		        	  Element element = (Element) firstNode;
		        	  
		        	  NodeList VersionElements = element.getElementsByTagName("Version_Id");
		        	  Element velement = (Element) VersionElements.item(0);
		        	  String value = velement.getAttribute("Version_Id");
		        	  //System.out.println("value "+value);
		        	  NodeList vid = velement.getChildNodes();
		        	  if(value.equals(version))	        	  
		        	  {
		        		  found = true; 
		        		  break;
		        	  }
	        	  }
	         }
             if(!found)
             {
            	  
            	 Element rootE = doc.getDocumentElement();            	 
            		//create child element having tagName=number
            	 Element childElement = doc.createElement("Version");
            	 Element childElement1 = doc.createElement("Version_Id");
            	 childElement1.setAttribute("Version_Id",version);
            	 
            	 childElement.appendChild(childElement1);
            	 Element childElement2 = doc.createElement("Version_Root");
            	 childElement2.setAttribute("Version_Root",root);
            	 childElement.appendChild(childElement2);
            	 Element childElement3 = doc.createElement("Dependency");
            	 childElement3.setAttribute("Dependency",dependency);
            	 childElement.appendChild(childElement3);
            	 rootE.appendChild(childElement);
            	 
            	 TransformerFactory transfac = TransformerFactory.newInstance();
            	    Transformer trans = transfac.newTransformer();
            	 
            	    //generating string from xml tree
            	    StringWriter sw = new StringWriter();
            	    StreamResult result = new StreamResult(sw);
            	    DOMSource source = new DOMSource(doc);
            	    trans.transform(source, result);
            	    String xmlString = sw.toString();
            	 
            	    //Saving the XML content to File
            	    OutputStream f0;
            	    byte buf[] = xmlString.getBytes();
            	    f0 = new FileOutputStream("Version.xml");
            	    for(int i=0;i<buf .length;i++) {
            	 	f0.write(buf[i]);
            	    }
            		f0.close();
            		buf = null;
	 
             }
	       }
	   }
       catch(Exception e)
       {
    	   e.printStackTrace();
    	   
       }
   }
	       
   
}