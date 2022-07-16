
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;

public class Main {
    private static int niveau;

    public static String pathToXml(String path) {

        File directory = new File(path);
        String xmlstring = "\n" + "<directory name = " + "\"" + directory.getName() + "\"" + ">";
        File[] liste = directory.listFiles();
        for (File f : liste) {
            if (f.isFile()) {
                xmlstring = xmlstring + "\n" + "\t" + "<file name = " + "\"" + f.getName() + "\"" + "/>";
            } else if (f.isDirectory()) {
                xmlstring = xmlstring + pathToXml(f.getAbsolutePath());
            }
        }

        xmlstring = xmlstring + "\n" + "</directory>" + "\n";
        return xmlstring;

    }

    public static Composant inserer(Element e) {
        Dossier vElement = new Dossier(e.getAttribute("name"), niveau);
        NodeList nodes = e.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) nodes.item(i);
                if (el.getNodeName().equals("file")) {
                    Composant myFile;
                    myFile = new Fichier(el.getAttribute("name"), vElement.getLevel() + 1);
                    vElement.addComposant(myFile);
                } else if (el.getNodeName().equals("directory")) {
                    niveau = vElement.getLevel() + 1;
                    vElement.addComposant(inserer(el));
                    niveau--;
                }
            }
        }
        return vElement;
    }

    public static Composant xmlToDoc(String xmlstring) throws ParserConfigurationException, SAXException, IOException {
        String xmlStr = "<?xml version=\"1.0\"?>" + xmlstring;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append(xmlStr);
        ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
        Document doc = builder.parse(input);
        Element element = doc.getDocumentElement();
        return inserer(element);
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        String path = args[0];
        String xmlString1 = pathToXml(path);
        System.out.println(xmlString1);
        // String textPathXml = pathToXml(path);
        // Document textDocument = StringXmlToXMLDocument(textPathXml);
        // System.out.println(textPathXml);
        // System.out.println(textDocument);
        Composant racine = xmlToDoc(xmlString1);
        racine.operation();

    }
}
