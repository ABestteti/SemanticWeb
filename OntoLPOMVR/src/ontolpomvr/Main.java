/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontolpomvr;

import java.util.Vector;

/**
 *
 * @author Anderson Bestteti
 * 
 */
public class Main {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //OntologyInformation ontoInf = new OntologyInformation();
        //OntologyEvaluationInformation ontoInfEval = new OntologyEvaluationInformation();

        Vector<String> teste = new Vector<String>();
        OMVR  insOMVR = new OMVR();
                
        //ReadSubmitPageOntologyInformation s = new ReadSubmitPageOntologyInformation();

        // Passa a URL/FILE de localização do arquivo OWL.
        //ontoInf.ontologyInformation(args[0]);

        //try {
        //    s.readOntologyInformationXML("c:\\temp\\OntologyInformation10.xml", ontoInf);
        //}
        //catch (Exception e) {
        //    e.printStackTrace();
        //}
        
        //System.out.println("Ontology Name.........: "+ontoInf.getOntoName());
        //System.out.println("Ontology Language.....: "+ontoInf.getOntologyLanguage());
        //System.out.println("Number of Classes.....: "+ontoInf.getOntoNumClasses());
        //System.out.println("Number of Instances...: "+ontoInf.getOntoNumInstances());
        //System.out.println("Number of Properties..: "+ontoInf.getOntoNumProperties());
        //System.out.println("Number of Axioms......: "+ontoInf.getOntoNumAxioms());
        //System.out.println("Note..................: "+ontoInf.getCommentMetaData());

        teste = insOMVR.execOMVRSPARQLQuery("select");
        
        //insOMVR.createOntologyInstanceOMVR(ontoInf);
        //insOMVR.generateOntologyEvaluationViewXML("Arte-dl");
        

        /*
        ontoInfEval.setOntologyName("Smartphone");
        ontoInfEval.setHasEvaluationCriteria("CapturaConcDominio");
        ontoInfEval.setEvaluatorName("RogerioBoff");
        ontoInfEval.setE_mail("teste@teste.com");
        ontoInfEval.setEvaluationComments("este é um comentário de teste.");
        ontoInfEval.setValue("55");

        insOMVR.createOntologyEvaluationInstanceOMVR(ontoInfEval);
         */
    }
}
