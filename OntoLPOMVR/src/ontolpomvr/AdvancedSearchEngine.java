/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontolpomvr;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Anderson
 */
public class AdvancedSearchEngine {

    private static String buildSPARQLQuery(String searchCriteriaXML)
            throws ParserConfigurationException, SAXException, IOException {
        File file = null;
        String LF = System.getProperty("line.separator");
        String nodeName = null;
        String nodeValue = null;
        String queryStmt = "";
        String queryFilter = " FILTER ("+LF;
        String opLogic    = "";

        try {
            file = new File(searchCriteriaXML);
        } catch (Exception e) {
            System.out.println("Erro ao abrir arquivo XML " + searchCriteriaXML);
            e.printStackTrace();
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(file);
        Node root = doc.getDocumentElement();

        //System.out.println("Root element " + root.getNodeName());

        NodeList children = root.getChildNodes();

        queryStmt = "SELECT ?ontology " + LF +
                    "WHERE { ?ontology a omvr:Ontology; " + LF;

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                nodeName = child.getNodeName();
                nodeValue = getElementValue(child);

                if (nodeName.equalsIgnoreCase("metodologia") &&
                    nodeValue != null) {
                    queryStmt   += "OPTIONAL{?ontology omvr:usedOntologyEngineeringMethodology ?usedOntologyEngineeringMethodology;}"+LF;
                    queryFilter += "REGEX(STR(?usedOntologyEngineeringMethodology),\""+nodeValue+"\") "+LF;
                    opLogic = " || " + LF;
                } else if (nodeName.equalsIgnoreCase("dominio") &&
                    nodeValue != null) {
                    queryStmt   += "OPTIONAL{?ontology omvr:hasDomain ?hasDomain;}"+LF;
                    queryFilter += opLogic + "REGEX(STR(?hasDomain),\""+nodeValue+"\") "+LF;
                    opLogic = " || " + LF;
                } else if (nodeName.equalsIgnoreCase("projOntologias") &&
                    nodeValue != null) {
                    queryStmt   += "OPTIONAL{?ontology omvr:usedByProject ?usedByProject.}"+LF;
                    queryFilter += opLogic + "REGEX(STR(?usedByProject),\""+nodeValue+"\") "+LF;
                }
            }
        }
        
        queryStmt   += queryFilter + ")}" + LF + "ORDER BY ?ontology";

        return queryStmt;
    }

    private final static String getElementValue(Node elem) {
        Node kid;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling()) {
                    if (kid.getNodeType() == Node.TEXT_NODE) {
                        return kid.getNodeValue();
                    }
                }
            }
        }
        return null;
    }

    private String[] getSubChildrenValues(NodeList subChildren) {
        String[] subChildrenValueString;
        String teste = null;
        int i = 0;

        subChildrenValueString = new String[subChildren.getLength()];

        for (int j = 0; j < subChildren.getLength(); j++) {
            Node subChild = subChildren.item(j);
            if (subChild.getNodeType() == Node.ELEMENT_NODE) {
                teste = getElementValue(subChild);
                subChildrenValueString[i] = teste;
                i++;
            }
        }

        return subChildrenValueString;
    }

    public static void main(String[] args) {
        /***
         * Parametros:
         * args[0]: Nome do arquivo XML com os critérios de pesquinsa avançada.
         * args[1]: Nome do arquivo XML para montar a árvore das ontologias
         * que foram recuperadas pela consulta SPARQL
         */
        Vector<String> ontologiesList = new Vector<String>();
        String queryStmt = null;
        OMVR   inst;
        if (args.length > 0) {
            try {
                System.out.println("Processando critérios de seleção do arquivo XML: "+args[0]);
                queryStmt = buildSPARQLQuery(args[0]);
                inst = new OMVR();
                ontologiesList = inst.execOMVRSPARQLQuery(queryStmt);
                inst.generateOMVROntologyTreeXML(args[1], ontologiesList);
            }
            catch (Exception e) {
                System.out.println("Erro:\n"+e.getMessage());
                e.printStackTrace();
                System.exit(-1);
            }
        }
        else {
            String msg;
            msg = "Numero de argumentos invalidos!\n"+
                  "execute: java -jar OntoLPOMVR.jar ontolpomvt.AdvancedSearchEngine "+
                  "<nome arquivo XML contendo os critérios de pesquisa> "+
                  "<nome do arquivo >";
            throw new IllegalArgumentException(msg);
        }
    }
}
