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
 * <P> O objetivo desta classe eh ler o arquivo XML gerado pela pagina de
 * submissao de avaliacoes de ontologias do portal OntoLP.
 * Parametros:
 * 2 - localização do arquivo XML que contém as informação da página de submissão
 *     de avaliações de ontologias
 * Exemplo de linha de comando:
 * java -jar OntoLPOMVR ReadSubmitPageOnotologyEvaluationInformation
 *   c:\temp\OntologyInformation.xml
 */
public class ReadSubmitPageOntologyEvaluationInformation {

    public void readSubmitPageOnotologyEvaluationInformation(String ontoLPXML,
            OntologyEvaluationInformation ontoInput)
            throws ParserConfigurationException, SAXException, IOException {
        File file = null;
        String nodeName = null;
        String nodeValue = null;

        try {
            file = new File(ontoLPXML);
        } catch (Exception e) {
            System.out.println("Erro ao abrir arquivo XML " + ontoLPXML);
            e.printStackTrace();
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(file);
        Node root = doc.getDocumentElement();

        //System.out.println("Root element " + root.getNodeName());

        NodeList children = root.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                nodeName = child.getNodeName();
                nodeValue = getElementValue(child);

                if (nodeName.equalsIgnoreCase("OntologyName")) {
                    ontoInput.setOntologyName(nodeValue);
                } else if (nodeName.equalsIgnoreCase("hasEvaluationCriteria")) {
                    ontoInput.setHasEvaluationCriteria(nodeValue);
                } else if (nodeName.equalsIgnoreCase("evaluationDate")) {
                    ontoInput.setEvaluationDate(nodeValue);
                } else if (nodeName.equalsIgnoreCase("evaluatorName")) {
                    ontoInput.setEvaluatorName(nodeValue);
                } else if (nodeName.equalsIgnoreCase("organizationName")) {
                    ontoInput.setOrganizationName(nodeValue);
                } else if (nodeName.equalsIgnoreCase("e-mail")) {
                    ontoInput.setE_mail(nodeValue);
                } else if (nodeName.equalsIgnoreCase("phone")) {
                    ontoInput.setPhone(nodeValue);
                } else if (nodeName.equalsIgnoreCase("evaluationComments")) {
                    ontoInput.setEvaluationComments(nodeValue);
                } else if (nodeName.equalsIgnoreCase("evaluationValue")) {
                    ontoInput.setValue(nodeValue);
                } 
            }
        }
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
         * Parametro:
         * args[0]: Nome do arquivo XML que contem as informações postadas pela
         *          página de submissão de avaliação de ontologias.
         */
        if (args.length > 0) {
            try {
                OMVR inst = new OMVR();
                OntologyEvaluationInformation ontoEvalInf = new OntologyEvaluationInformation();
                ReadSubmitPageOntologyEvaluationInformation readXML = new ReadSubmitPageOntologyEvaluationInformation();

                System.out.println("Criando instancia da avaliação da ontologia no OMVR a partir do arquivo "+args[0]);

                /***
                 * Carrega o arquivo XML contendo os dados enviados pela página
                 * de submissão de ontologia para em seguida criar uma nova
                 * instancio no OMVR
                 */
                readXML.readSubmitPageOnotologyEvaluationInformation(args[0], ontoEvalInf);

                /***
                 * Instancia da avaliação da ontologia no OMVR
                 */
                inst.createOntologyEvaluationInstanceOMVR(ontoEvalInf);                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            String msg;
            msg = "Numero de argumentos invalidos!\n"+
                  "execute: java -jar OntoLPOMVR.jar ReadSubmitPageOntologyEvaluationInformation "+
                  "<localização do arquivo XML que contem os dados da página de submissão avaliação de ontologias>";
            throw new IllegalArgumentException(msg);
        }
    }
}
