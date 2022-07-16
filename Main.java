
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;

public class Main {
    private static int level;

    // Méthode qui permet de tranformer un path en un string format xml
    public static String pathToXml(String path) {

        File directory = new File(path);
        String stringXML = "\n" + "<directory name = " + "\"" + directory.getName() + "\"" + ">";
        File[] liste = directory.listFiles();
        for (File f : liste) {
            if (f.isFile()) {
                stringXML = stringXML + "\n" + "\t" + "<file name = " + "\"" + f.getName() + "\"" + "/>";
            } else if (f.isDirectory()) {
                stringXML = stringXML + pathToXml(f.getAbsolutePath());
            }
        }

        stringXML = stringXML + "\n" + "</directory>" + "\n";
        return stringXML;

    }

    public static Composant inserer(Element e) {
        Dossier vElement = new Dossier(e.getAttribute("name"), level);
        NodeList nodes = e.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) nodes.item(i);
                if (el.getNodeName().equals("file")) {
                    Composant myFile;
                    myFile = new Fichier(el.getAttribute("name"), vElement.getLevel() + 1);
                    vElement.addComposant(myFile);
                } else if (el.getNodeName().equals("directory")) {
                    level = vElement.getLevel() + 1;
                    vElement.addComposant(inserer(el));
                    level--;
                }
            }
        }
        return vElement;
    }

    // Création du composant à partir du String renvoyer par la méthode
    // pathToXML()

    public static Composant xmlToComposant(String xmlstring)
            throws ParserConfigurationException, SAXException, IOException {
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
        String xmlString = pathToXml(path);
        System.out.println(xmlString);
        Composant racine = xmlToComposant(xmlString);
        racine.operation();

    }
}
