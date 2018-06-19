/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontolpomvr;

import java.io.File;
import java.io.IOException;
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
 *
 * <P> O objetivo desta classe eh ler o arquivo XML gerado pela pagina de
 * submissao de ontologias do portal OntoLP.
 * Exemplos de manipulacao de arquivos XML:
 * http://labe.felk.cvut.cz/~xfaigl/mep/xml/java-xml.htm
 * http://www.developerfusion.com/code/2064/a-simple-way-to-read-an-xml-file-in-java/
 *
 */
public class ReadSubmitPageInformation {

    public void readOntologyInformationXML(String ontoLPXML, 
                                           OntologyInformation ontoInput)
    throws ParserConfigurationException, SAXException, IOException {
        File file        = null;
        String nodeName  = null;
        String nodeValue = null;
        
        try {
            file = new File(ontoLPXML);
        }
        catch (Exception e) {
            System.out.println("Erro ao abrir arquivo XML "+ontoLPXML);
            e.printStackTrace();
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(file);
        Node root = doc.getDocumentElement();

        //System.out.println("Root element " + root.getNodeName());
        
        NodeList children = root.getChildNodes();

        for (int i=0;i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
               nodeName = child.getNodeName();
               nodeValue = getElementValue(child);

               if (nodeName.equalsIgnoreCase("OntologyName")) {
                   ontoInput.setOntoName(nodeValue);
               }
               if (nodeName.equalsIgnoreCase("OntologySyntax")) {
                   ontoInput.setOntologySyntax(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("acronym")) {
                   ontoInput.setOntoAcronym(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("creationDate")) {
                   ontoInput.setCreationDate(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("modificationDate")) {
                   ontoInput.setModificationDate(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("description")) {
                   ontoInput.setDescription(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("documentation")) {
                   ontoInput.setDocumentation(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("reference")) {
                   ontoInput.setReference(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("status")) {
                   ontoInput.setStatus(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("hasFormalityLevel")) {
                   ontoInput.setHasFormalityLevel(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("hasLicense")) {
                   ontoInput.setHasLicense(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("version")) {
                   ontoInput.setVersion(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("hasPriorVersion")) {
                   ontoInput.setHasPriorVersion(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("isOfType")) {
                   ontoInput.setIsOfType(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("keyClasses")) {
                   ontoInput.setKeyClasses(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("keyWords")) {
                   ontoInput.setKeyWords(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("knownUsage")) {
                   ontoInput.setKnownUsage(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("naturalLanguage")) {
                   ontoInput.setNaturalLanguage(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("reference")) {
                   ontoInput.setReference(nodeValue);
               }
               else if (nodeName.equalsIgnoreCase("conformsToKnowledgeRepresentationParadigm")) {
                   ontoInput.setConformsTKRParadigm(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("designedForOntologyTask")) {
                   ontoInput.setDesignedForOntoTask(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("endorsedBy")) {
                   ontoInput.setEndorsedBy(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("hasContributor")) {
                   ontoInput.setHasContributor(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("hasCriator")) {
                   ontoInput.setHasCreator(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("hasDomain")) {
                   ontoInput.setHasDomain(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("isBackwardCompatibleWith")) {
                   ontoInput.setIsBackwardCompatibleWith(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("isBasedOn")) {
                   ontoInput.setIsBasedOn(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("isIncompatibleWith")) {
                   ontoInput.setIsIncompatibleWith(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("usedByProject")) {
                   ontoInput.setUsedByProject(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("usedOntologyEngineeringMethodology")) {
                   ontoInput.setUsedOntologyEngineeringMethodology(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("usedOntologyEngineeringTool")) {
                   ontoInput.setUsedOntologyEngineeringTool(getSubChildrenValues(child.getChildNodes()));
               }
               else if (nodeName.equalsIgnoreCase("useImports")) {
                   ontoInput.setUserImports(getSubChildrenValues(child.getChildNodes()));
               }
            }
        }
    }

    private final static String getElementValue( Node elem ) {
        Node kid;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
                    if( kid.getNodeType() == Node.TEXT_NODE  ){
                        return kid.getNodeValue();
                    }
                }
            }
        }
        return null;
    }

    private String[] getSubChildrenValues(NodeList subChildren) {
        String[] subChildrenValueString;
        String teste =null;
        int i = 0;

        subChildrenValueString = new String[subChildren.getLength()];

        for (int j=0;j < subChildren.getLength();j++) {
            Node subChild = subChildren.item(j);
            if (subChild.getNodeType() == Node.ELEMENT_NODE) {
               teste = getElementValue(subChild);
               subChildrenValueString[i] = teste;
               i++;
            }
        }
        return subChildrenValueString;
    }
}
