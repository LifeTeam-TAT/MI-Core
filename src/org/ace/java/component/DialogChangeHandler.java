//package org.ace.java.component;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import javax.xml.xpath.XPath;
//import javax.xml.xpath.XPathConstants;
//import javax.xml.xpath.XPathExpressionException;
//import javax.xml.xpath.XPathFactory;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//public class DialogChangeHandler {
//
//	public static void main(String[] args) {
//		List<String> list = getFileNames("D:\\Development\\Workspace\\FNI\\fnilp\\WebContent", new ArrayList<String>());
//		System.out.println(list.size());
//		changeDialogOncomplete(list);
//
//	}
//
//	private static List<String> getFileNames(String sDir, List<String> list) {
//		File[] faFiles = new File(sDir).listFiles();
//		for (File file : faFiles) {
//			if (file.getName().matches("^(.*?)")) {
//				if (file.getAbsolutePath().contains("xhtml")) {
//					list.add(file.getAbsolutePath());
//				}
//			}
//			if (file.isDirectory()) {
//				getFileNames(file.getAbsolutePath(), list);
//			}
//		}
//
//		return list;
//	}
//
//	private static void changeDialogOncomplete(List<String> files) {
//		try {
//			Document doc = null;
//			XPath xpath = null;
//			NodeList nodes = null;
//			Node node = null;
//			StringBuffer value = null;
//			Transformer xformer = TransformerFactory.newInstance().newTransformer();
//			xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//
//			for (String filePath : files) {
//				// 1- Build the doc from the XML file
//				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filePath);
//
//				// 2- Locate the node(s) with xpath
//				xpath = XPathFactory.newInstance().newXPath();
//
//				// 3- Make the change on the selected nodes
//				nodes = (NodeList) xpath.evaluate("//*[contains(@oncomplete, '.show()')]", doc, XPathConstants.NODESET);
//				for (int idx = 0; idx < nodes.getLength(); idx++) {
//					node = nodes.item(idx).getAttributes().getNamedItem("oncomplete");
//					value = new StringBuffer(node.getNodeValue());
//					node.setNodeValue("PF('" + value.substring(0, value.lastIndexOf(".show()")) + "').show();");
//				}
//
//				nodes = (NodeList) xpath.evaluate("//*[contains(@oncomplete, '.hide()')]", doc, XPathConstants.NODESET);
//				for (int idx = 0; idx < nodes.getLength(); idx++) {
//					node = nodes.item(idx).getAttributes().getNamedItem("oncomplete");
//					value = new StringBuffer(node.getNodeValue());
//					node.setNodeValue("PF('" + value.substring(0, value.lastIndexOf(".hide()")) + "').hide();");
//				}
//
//				// 4- Save the result to a new XML doc
//				xformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
//
//				System.out.println("End : " + filePath);
//			}
//
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//		} catch (TransformerConfigurationException e) {
//			e.printStackTrace();
//		} catch (TransformerException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
