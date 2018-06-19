/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontolpomvr;

/**
 * Esta classe e responsavel por oferecer servicos de instanciacao de novos
 * metadados sobre ontologias enviadas para o Portal OntoLP, bem como servicos
 * de recuperacao de informacoes de metadados para apresentacao na camada de
 * apresentacao.
 * @author Anderson Bestteti
 * @version 1.0
 */
// Java standard API Imports
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;

// Jena API  Imports
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

// Library for Metrics Calculus from Larissa Freitas
import metricasowl.*;

public class OMVR {

    private OntModel omvrModel;
    private OntClass ontologyClass;
    private OntClass ontologyMetricClass;
    private OntClass ontologyEvaluationClass;

    private final String omvrFile = "file:omvr/OMVR_v2.4.owl";
    private final String omvrFileOut = "omvr/OMVR_v2.4.owl";
    private final String nsOMV = "http://omv.ontoware.org/2005/05/ontology#";
    private Individual inst;
    private FileOutputStream tmpOMVR = null;

    /**
     * Metodo responsavel por criar uma nova instancia de metadados a partir
     * da ontologia enviada para o Portal OntoLP
     * @param pOntoInf
     */
    public void createOntologyInstanceOMVR(OntologyInformation pOntoInf) {

        omvrModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        omvrModel.read(omvrFile, "RDF/XML");

        ontologyClass = omvrModel.getOntClass(nsOMV + "Ontology");

        /*for (Iterator i = omvrModel.listNamedClasses(); i.hasNext();) {
        ontologyClass = (OntClass) i.next();
        System.out.println("Classe: "+ontologyClass.toString());
        }

        for (Iterator i = ontologyClass.listComments(null);i.hasNext();) {
        System.out.println("Ontology Comment: "+i.next());
        }*/

        /**
         * Cria uma nova instância da classe Ontology a partir de informacoes
         * da ontologia enviada através do portal OntoLP.
         */
        inst = omvrModel.createIndividual(nsOMV + pOntoInf.getOntoName(), ontologyClass);

        inst.setComment("Ontologia enviada pelo portal OntoLP em " + getDateTime(), "pt");

        inst.setPropertyValue((Property) omvrModel.getProperty(nsOMV + "numberOfClasses"),
                (Literal) omvrModel.createTypedLiteral(pOntoInf.getOntoNumClasses(),
                XSDDatatype.XSDinteger));

        inst.setPropertyValue((Property) omvrModel.getProperty(nsOMV + "numberOfProperties"),
                (Literal) omvrModel.createTypedLiteral(pOntoInf.getOntoNumProperties(),
                XSDDatatype.XSDinteger));

        inst.setPropertyValue((Property) omvrModel.getProperty(nsOMV + "numberOfIndividuals"),
                (Literal) omvrModel.createTypedLiteral(pOntoInf.getOntoNumInstances(),
                XSDDatatype.XSDinteger));

        inst.setPropertyValue((Property) omvrModel.getProperty(nsOMV + "numberOfAxioms"),
                (Literal) omvrModel.createTypedLiteral(pOntoInf.getOntoNumAxioms(),
                XSDDatatype.XSDinteger));

        if (pOntoInf.getDescription() != null) {
            inst.setPropertyValue((Property) omvrModel.getProperty(nsOMV + "description"),
                    (Literal) omvrModel.createTypedLiteral(pOntoInf.getDescription(),
                    XSDDatatype.XSDstring));
        }

        if (pOntoInf.getOntoOntologyFile() != null) {
            inst.setPropertyValue((Property) omvrModel.getProperty(nsOMV + "ontologyFile"),
                    (Literal) omvrModel.createTypedLiteral(pOntoInf.getOntoOntologyFile(),
                    XSDDatatype.XSDstring));
        }

        if (pOntoInf.getCommentMetaData() != null) {
            inst.setPropertyValue((Property) omvrModel.getProperty(nsOMV + "notes"),
                    (Literal) omvrModel.createTypedLiteral(pOntoInf.getCommentMetaData(),
                    XSDDatatype.XSDstring));
        }

        for (int i = 0; i < pOntoInf.getNaturalLanguage().length && pOntoInf.getNaturalLanguage()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "naturalLanguage"),
                    pOntoInf.getNaturalLanguage()[i],
                    "pt");
        }

        inst.addProperty((Property) omvrModel.getProperty(nsOMV + "name"),
                pOntoInf.getOntoName(), "pt");

        inst.addProperty((Property) omvrModel.getProperty(nsOMV + "acronym"),
                pOntoInf.getOntoName(),
                XSDDatatype.XSDstring);

        inst.addProperty((Property) omvrModel.getProperty(nsOMV + "resourceLocator"),
                pOntoInf.getResourceLocator(),
                XSDDatatype.XSDstring);

        inst.addProperty((Property) omvrModel.getProperty(nsOMV + "URI"),
                pOntoInf.getResourceLocator(),
                XSDDatatype.XSDstring);

        inst.addProperty((Property) omvrModel.getProperty(nsOMV + "hasOntologyLanguage"),
                (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getOntologyLanguage()));

        inst.addProperty((Property) omvrModel.getProperty(nsOMV + "hasOntologySyntax"),
                (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getOntologySyntax()));

        if (pOntoInf.getCreationDate() != null) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "creationDate"),
                    pOntoInf.getCreationDate(),
                    XSDDatatype.XSDstring);
        }

        if (pOntoInf.getDocumentation() != null) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "documentation"),
                    pOntoInf.getDocumentation(),
                    XSDDatatype.XSDstring);
        }

        /***
         * DESCARTADO DA PAGINA DE UPLOAD
         *
        if (pOntoInf.getModificationDate() != null) {
        inst.addProperty((Property)omvrModel.getProperty(nsOMV+"modificationDate"),
        pOntoInf.getModificationDate(),
        XSDDatatype.XSDstring);
        }*/
        /***
         * DESCARTADO DA PAGINA DE UPLOAD
         *
        if (pOntoInf.getStatus() != null) {
        inst.addProperty((Property)omvrModel.getProperty(nsOMV+"status"),
        pOntoInf.getStatus(),
        XSDDatatype.XSDstring);
        }*/
        if (pOntoInf.getHasFormalityLevel() != null) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "hasFormalityLevel"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getHasFormalityLevel()));
        }

        if (pOntoInf.getHasLicense() != null) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "hasLicense"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getHasLicense()));
        }

        if (pOntoInf.getVersion() != null) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "version"),
                    pOntoInf.getVersion(),
                    XSDDatatype.XSDstring);
        }

        if (pOntoInf.getHasPriorVersion() != null) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "hasPriorVersion"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getHasPriorVersion()));
        }

        inst.addProperty((Property) omvrModel.getProperty(nsOMV + "isOfType"),
                (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getIsOfType()));

        for (int i = 0; i < pOntoInf.getKeyClasses().length && pOntoInf.getKeyClasses()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "keyClasses"),
                    pOntoInf.getKeyClasses()[i],
                    "pt");
        }

        /***
         * DESCARTADO DA PAGINA DE UPLOAD
         *
        for (int i=0; i < pOntoInf.getKeyWords().length && pOntoInf.getKeyWords()[i] != null; i++) {
        inst.addProperty((Property)omvrModel.getProperty(nsOMV+"keywords"),
        pOntoInf.getKeyWords()[i],
        "pt");
        }*/
        /***
         * DESCARTADO DA PAGINA DE UPLOAD
         *
        for (int i=0; i < pOntoInf.getKnownUsage().length && pOntoInf.getKnownUsage()[i] != null; i++) {
        inst.addProperty((Property)omvrModel.getProperty(nsOMV+"knownUsage"),
        pOntoInf.getKnownUsage()[i],
        "pt");
        }*/
        if (pOntoInf.getReference() != null) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "reference"),
                    pOntoInf.getReference(),
                    "pt");
        }

        for (int i = 0; i < pOntoInf.getConformsTKRParadigm().length && pOntoInf.getConformsTKRParadigm()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "conformsToKnowledgeRepresentationParadigm"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getConformsTKRParadigm()[i]));
        }

        for (int i = 0; i < pOntoInf.getDesignedForOntoTask().length && pOntoInf.getDesignedForOntoTask()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "designedForOntologyTask"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getDesignedForOntoTask()[i]));
        }

        for (int i = 0; i < pOntoInf.getEndorsedBy().length && pOntoInf.getEndorsedBy()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "endorsedBy"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getEndorsedBy()[i]));
        }

        for (int i = 0; i < pOntoInf.getHasContributor().length && pOntoInf.getHasContributor()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "hasContributor"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getHasContributor()[i]));
        }

        for (int i = 0; i < pOntoInf.getHasCreator().length && pOntoInf.getHasCreator()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "hasCreator"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getHasCreator()[i]));
        }


        for (int i = 0; i < pOntoInf.getHasDomain().length && pOntoInf.getHasDomain()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "hasDomain"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getHasDomain()[i]));
        }

        for (int i = 0; i < pOntoInf.getIsBackwardCompatibleWith().length && pOntoInf.getIsBackwardCompatibleWith()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "isBackwardCompatibleWith"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getIsBackwardCompatibleWith()[i]));
        }

        for (int i = 0; i < pOntoInf.getIsBasedOn().length && pOntoInf.getIsBasedOn()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "isBasedOn"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getIsBasedOn()[i]));
        }

        /***
         * DESCARTADO DA PAGINA DE UPLOAD
         *
        for (int i=0; i < pOntoInf.getOntoIsIncompatibleWith().length && pOntoInf.getOntoIsIncompatibleWith()[i] != null; i++) {
        inst.addProperty((Property)omvrModel.getProperty(nsOMV+"isIncompatibleWith"),
        (RDFNode)omvrModel.createResource(nsOMV+pOntoInf.getOntoIsIncompatibleWith()[i]));
        }*/
        for (int i = 0; i < pOntoInf.getUsedByProject().length && pOntoInf.getUsedByProject()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "usedByProject"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getUsedByProject()[i]));
        }

        for (int i = 0; i < pOntoInf.getUsedOntologyEngineeringMethodology().length && pOntoInf.getUsedOntologyEngineeringMethodology()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "usedOntologyEngineeringMethodology"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getUsedOntologyEngineeringMethodology()[i]));
        }

        for (int i = 0; i < pOntoInf.getUsedOntologyEngineeringTool().length && pOntoInf.getUsedOntologyEngineeringTool()[i] != null; i++) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "usedOntologyEngineeringTool"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getUsedOntologyEngineeringTool()[i]));
        }

        /***
         * Calcula as métricas a partir da ferramenta de metricas do trabalho
         * de mestrado da Larissa Freitas
         */
        createOntologyMetricInstance(pOntoInf);

        /***
         * DESCARTADO DA PAGINA DE UPLOAD
         *
        for (int i=0; i < pOntoInf.getUserImports().length && pOntoInf.getUserImports()[i] != null; i++) {
        inst.addProperty((Property)omvrModel.getProperty(nsOMV+"useImports"),
        (RDFNode)omvrModel.createResource(nsOMV+pOntoInf.getUserImports()[i]));
        }*/
        //for (Iterator i = ontologyClass.listDeclaredProperties(); i.hasNext();) {
        //    System.out.println(" - "+((Resource)i.next()).getURI());
        //}

        try {            
            tmpOMVR = new FileOutputStream(omvrFileOut);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OMVR.class.getName()).log(Level.SEVERE, null, ex);
        }

        omvrModel.write(tmpOMVR, "RDF/XML");
        omvrModel.close();
    }

    private void createOntologyMetricInstance(OntologyInformation pOntoInf) {
        /***
         * Este metodo cria as instancias da classe OntologyMetrics a partir
         * dos calculos de metricas realizados pela ferramenta de metricas
         * desenvolvido no trabalho de mestrado da Larissa Freitas
         */
        OntClass ontoMetricClass;
        String ontologyMetricInstName;
        Vector<String> metricName = new Vector<String>();
        Vector<Double> metricValue = new Vector<Double>();
        ontologyMetricInstName = pOntoInf.getOntoName();
        try {
            /***
             * Obtem o calculo das métricas para ontologia que esta sendo
             * instanciada no OMVR.
             */
            Metrics.metrics(pOntoInf.getFileName());
        } catch (MalformedURLException ex) {
            Logger.getLogger(OMVR.class.getName()).log(Level.SEVERE, null, ex);
        }
        metricName = Metrics.metrics_names;
        metricValue = Metrics.metrics_values;

        ontoMetricClass = omvrModel.getOntClass(nsOMV + "OntologyMetric");

        for (int i=0; i<metricName.size();i++) {
            /**
             * Cria uma nova instância da classe OntologyMetric para cada ocorrencia
             * da metrica armazenadas no array metricName
             */
            inst = omvrModel.createIndividual(nsOMV + (ontologyMetricInstName + i), ontoMetricClass);

            inst.setComment("Metrica obtida em " + getDateTime(), "pt");

            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "scoredOntology"),
                    (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getOntoName()));

            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "usedMetric"),
                    (RDFNode) omvrModel.createResource(nsOMV + metricName.elementAt(i).replace('(', '_')));

            inst.setPropertyValue((Property) omvrModel.getProperty(nsOMV + "creationDate"),
                    (Literal) omvrModel.createTypedLiteral(getDate(),
                    XSDDatatype.XSDstring));

            inst.setPropertyValue((Property) omvrModel.getProperty(nsOMV + "value"),
                    (Literal) omvrModel.createTypedLiteral(metricValue.elementAt(i),
                    XSDDatatype.XSDfloat));
        }
    }

    public void createOntologyEvaluationInstanceOMVR(OntologyEvaluationInformation pOntoInf) {
        OntClass ontoEvalClass;

        omvrModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        omvrModel.read(omvrFile, "RDF/XML");

        ontoEvalClass = omvrModel.getOntClass(nsOMV + "OntologyEvaluation");

        inst = omvrModel.createIndividual(nsOMV + pOntoInf.getOntologyName()+
                "_"+getDateTime("ddHHmmss"), ontoEvalClass);

        inst.setComment("Avaliacão enviada pelo portal OntoLP em " + getDateTime(), "pt");

        inst.addProperty((Property) omvrModel.getProperty(nsOMV + "hasEvaluationCriteria"),
                         (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getHasEvaluationCriteria()));

        inst.addProperty((Property) omvrModel.getProperty(nsOMV + "evaluatedOntology"),
                         (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getOntologyName()));

        //inst.addProperty((Property) omvrModel.getProperty(nsOMV + "evaluatedOntology"),
        //                 (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getOntologyName()));

        inst.addProperty((Property) omvrModel.getProperty(nsOMV + "hasEvaluator"),
                         (RDFNode) omvrModel.createResource(nsOMV + pOntoInf.getEvaluatorName()));

        if (pOntoInf.getEvaluationComments() != null) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "evaluationComments"),
                    pOntoInf.getEvaluationComments(),
                    XSDDatatype.XSDstring);
        }

        if (pOntoInf.getValue() != null) {
            inst.addProperty((Property) omvrModel.getProperty(nsOMV + "evaluationValue"),
                    pOntoInf.getValue(),
                    XSDDatatype.XSDfloat);
        }

        inst.addProperty((Property) omvrModel.getProperty(nsOMV + "evaluationDate"),
                 pOntoInf.getEvaluationDate(),
                 XSDDatatype.XSDdate);

        try {
            tmpOMVR = new FileOutputStream(omvrFileOut);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OMVR.class.getName()).log(Level.SEVERE, null, ex);
        }

        omvrModel.write(tmpOMVR, "RDF/XML");
        omvrModel.close();
    }

    private static String getDateTime() {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

        return sdf.format(cal.getTime());
    }

    private static String getDateTime(String pDateFormat) {
        String DATE_FORMAT_NOW = pDateFormat;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

        return sdf.format(cal.getTime());
    }

    private static String getDate() {
        String DATE_FORMAT_NOW = "dd/MM/yyyy";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

        return sdf.format(cal.getTime());
    }

    public void generateOMVROntologyTreeXML(String fileOut) {
        /***
         * Método responsável por criar um arquivo XML que será apresentados
         * como uma árvore no interface de navegação de ontologias do portal
         * OntoLP.
         * Retona o nome do arquivo XML gerado
         */

        File fileXMLOntologies;
        BufferedWriter out;

        omvrModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        omvrModel.read(omvrFile, "RDF/XML");

        try {
            fileXMLOntologies = new File(fileOut);
            out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(fileXMLOntologies), "UTF8"));

            ontologyClass = omvrModel.getOntClass(nsOMV + "Ontology");

            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<root>");
            out.newLine();
            out.write("   <item parent_id=\"0\" id=\"node_root\" state=\"open\"><content><name onclick=\"javascript:location.reload(true)\" icon=\"css/images/db_omvr.gif\"><![CDATA[Ontologias Armazenadas]]></name></content></item>");
            out.newLine();
            int nodeId = 1;
            int subNodeId = 1;
            for (Iterator i = ontologyClass.listInstances(); i.hasNext();) {
                Individual ind = (Individual) i.next();
                out.write("      <item parent_id=\"node_root\" id=\"node_" + nodeId + "\"><content><name onclick=\"getURL('consultaOntologia.php?onto="+ ind.getLocalName() +"')\" icon=\"css/images/icon_ontology.gif\"><![CDATA[" + ind.getLocalName() + "]]></name></content></item>");
                out.newLine();
                out.write("         <item parent_id=\"node_" + nodeId + "\" id=\"subNode_" + (subNodeId++) + "\"><content><name onclick=\"getURL('consultaAvaliacoes.php?onto=" + ind.getLocalName() + "')\"><![CDATA[Avaliações]]></name></content></item>");
                out.newLine();
                out.write("         <item parent_id=\"node_" + nodeId + "\" id=\"subNode_" + (subNodeId++) + "\"><content><name onclick=\"getURL('apresentaMetricas.php?onto="+ ind.getLocalName() +"')\"><![CDATA[Métricas]]></name></content></item>");
                out.newLine();
                nodeId++;
            }
            out.write("</root>");
            out.close();
        } catch (UnsupportedEncodingException ue) {
            System.out.println(ue.getMessage()+": Unsupported Encoding Exception.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        omvrModel.close();
    }

    public void generateOMVROntologyTreeXML(String fileOut, Vector<String> pResultSet) {
        /***
         * Método responsável por criar um arquivo XML que será apresentados
         * como uma árvore no interface de navegação de ontologias do portal
         * OntoLP.
         * As ontologias listadas no XML são resultado da consulta avançada de
         * ontologias.
         */

        File fileXMLOntologies;
        BufferedWriter out;

        try {
            fileXMLOntologies = new File(fileOut);
            out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(fileXMLOntologies), "UTF8"));

            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<root>");
            out.newLine();
            out.write("   <item parent_id=\"0\" id=\"node_root\" state=\"open\"><content><name onclick=\"javascript:location.reload(true)\" icon=\"css/images/db_omvr.gif\"><![CDATA[Ontologias Armazenadas]]></name></content></item>");
            out.newLine();
            int nodeId = 1;
            int subNodeId = 1;
            for (int i=0; i<pResultSet.size();i++) {
                out.write("      <item parent_id=\"node_root\" id=\"node_" + nodeId + "\"><content><name onclick=\"getURL('consultaOntologia.php?onto="+ pResultSet.elementAt(i) +"')\" icon=\"css/images/icon_ontology.gif\"><![CDATA[" + pResultSet.elementAt(i) + "]]></name></content></item>");
                out.newLine();
                out.write("         <item parent_id=\"node_" + nodeId + "\" id=\"subNode_" + (subNodeId++) + "\"><content><name onclick=\"getURL('consultaAvaliacoes.php?onto=" + pResultSet.elementAt(i) + "')\"><![CDATA[Avaliações]]></name></content></item>");
                out.newLine();
                out.write("         <item parent_id=\"node_" + nodeId + "\" id=\"subNode_" + (subNodeId++) + "\"><content><name onclick=\"getURL('apresentaMetricas.php?onto="+ pResultSet.elementAt(i) +"')\"><![CDATA[Métricas]]></name></content></item>");
                out.newLine();
                nodeId++;
            }
            out.write("</root>");
            out.close();
        } catch (UnsupportedEncodingException ue) {
            System.out.println(ue.getMessage()+": Unsupported Encoding Exception.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
    }

    public void generateOMVROntologyListXML(String fileOut) {
        /***
         * Método responsável por criar um arquivo XML que será apresentados
         * como no dropdown list da pagina de pesquisa avancada de ontologias
         * do portal OntoLP.
         */
        File fileXMLOntologies;
        BufferedWriter out;

        omvrModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        omvrModel.read(omvrFile, "RDF/XML");

        try {
            fileXMLOntologies = new File(fileOut);
            out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(fileXMLOntologies), "UTF8"));

            ontologyClass = omvrModel.getOntClass(nsOMV + "Ontology");

            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<Ontologies>");
            out.newLine();
            for (Iterator i = ontologyClass.listInstances(); i.hasNext();) {
                Individual ind = (Individual) i.next();
                out.write("   <Ontology>" + ind.getLocalName() + "</Ontology>");
                out.newLine();
            }
            out.write("</Ontologies>");
            out.close();
        } catch (UnsupportedEncodingException ue) {
            System.out.println("Unsupported Encoding Exception.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        omvrModel.close();
    }
    public void generateOMVROntologyViewXML(String pOntoName, String fileOut) {
        /***
         * Este metodo e responsavel por gerar o arquivo XML que contem as
         * informacoes de uma instancia da OMVR com os metadados da ontologia
         * solicitada.
         */
        
        File fileXMLOntologies;
        BufferedWriter out;
        RDFNode nd;
        String propertyName;

        omvrModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        omvrModel.read(omvrFile, "RDF/XML");
        ontologyClass = omvrModel.getOntClass(nsOMV + "Ontology");

        try {
            fileXMLOntologies = new File(fileOut);
            out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(fileXMLOntologies), "UTF8"));

            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<OntoLPOMVR>");
            out.newLine();

            for (Iterator i = ontologyClass.listInstances(); i.hasNext();) {
                Individual ind = (Individual) i.next();

                if (ind.getLocalName().equalsIgnoreCase(pOntoName)) {

                    /*for (ExtendedIterator<OntProperty> k = ontologyClass.listDeclaredProperties(); k.hasNext();) {
                    OntProperty ontProp = k.next();
                    propertyName = ontProp.getLocalName();

                    if (ind.getCardinality(ontProp) > 0) {
                    for (Iterator j = ind.listPropertyValues(omvrModel.getProperty(nsOMV + propertyName)); j.hasNext();) {
                    nd = (RDFNode) j.next();
                    if (nd != null) {
                    if (nd.isLiteral()) {
                    out.write("  <" + propertyName + ">" + ((Literal) nd).getLexicalForm() + "</" + propertyName + ">");
                    } else if (nd.isResource()) {
                    out.write("  <" + propertyName + ">" + ((Resource) nd).getLocalName() + "</" + propertyName + ">");
                    }
                    }
                    out.newLine();
                    }
                    } else {
                    nd = ind.getPropertyValue((Property) omvrModel.getProperty(nsOMV + propertyName));
                    if (nd != null) {
                    if (nd.isLiteral()) {
                    out.write("  <" + propertyName + ">" + ((Literal) nd).getLexicalForm() + "</" + propertyName + ">");
                    } else if (nd.isResource()) {

                    out.write("  <" + propertyName + ">" + ((Resource) nd).getLocalName() + "</" + propertyName + ">");
                    }
                    out.newLine();
                    }
                    }
                    }*/

                    out.write("  <OntologyName>" + ind.getLocalName() + "</OntologyName>");
                    out.newLine();

                    writePropertyValue(out, ind, "name");
                    writePropertyValue(out, ind, "hasOntologySyntax");
                    writePropertyValue(out, ind, "acronym");
                    writePropertyValue(out, ind, "version");
                    writePropertyValue(out, ind, "creationDate");
                    writePropertyValue(out, ind, "modificationDate");
                    writePropertyValue(out, ind, "description");
                    writePropertyValue(out, ind, "documentation");
                    writePropertyValue(out, ind, "reference");
                    writePropertyValue(out, ind, "hasFormalityLevel");
                    writePropertyValue(out, ind, "isOfType");
                    writePropertyValue(out, ind, "hasLicense");
                    writePropertyValue(out, ind, "hasPriorVersion");
                    writePropertyValues(out, ind, "keyClasses", "keyClass");
                    writePropertyValues(out, ind, "naturalLanguage", "language");
                    writePropertyValue(out, ind, "reference");
                    writePropertyValue(out, ind, "ontologyFile");
                    writePropertyValues(out, ind, "conformsToKnowledgeRepresentationParadigm", "knowledgeRepParadigm");
                    writePropertyValues(out, ind, "designedForOntologyTask", "ontologyTask");
                    writePropertyValues(out, ind, "endorsedBy", "endorsed");
                    writePropertyValues(out, ind, "hasContributor", "contributor");
                    writePropertyValues(out, ind, "hasCreator", "creator");
                    writePropertyValues(out, ind, "hasDomain", "domain");
                    writePropertyValues(out, ind, "isBackwardCompatibleWith", "backwardCompatible");
                    writePropertyValues(out, ind, "isBasedOn", "basedOn");
                    writePropertyValues(out, ind, "usedByProject", "project");
                    writePropertyValues(out, ind, "usedOntologyEngineeringMethodology", "methodology");
                    writePropertyValues(out, ind, "usedOntologyEngineeringTool", "engimeeringTool");

                    break;
                }
            }
            out.write("</OntoLPOMVR>");
            out.newLine();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        omvrModel.close();
    }

    private void writePropertyValues(BufferedWriter pOut, Individual pInd,
            String pPropertyName,
            String pSubProperty) throws IOException {
        /***
         * Este metodo e responsavel por recuperar todos os valores da propriedade
         * informada no parametro pPropertyName. Cada valor sera recuperado e
         * gravado no XML de saida, representado pelo parametro pOut
         */
        RDFNode nd;

        pOut.write("  <" + pPropertyName + ">");
        pOut.newLine();
        for (Iterator j = pInd.listPropertyValues(omvrModel.getProperty(nsOMV + pPropertyName)); j.hasNext();) {
            nd = (RDFNode) j.next();
            if (nd != null) {
                if (nd.isLiteral()) {
                    pOut.write("    <" + pSubProperty + ">" +
                            ((Literal) nd).getLexicalForm() +
                            "</" + pSubProperty + ">");
                } else if (nd.isResource()) {
                    pOut.write("    <" + pSubProperty + ">" +
                            ((Resource) nd).getLocalName() +
                            "</" + pSubProperty + ">");
                }
            }
            pOut.newLine();
        }
        pOut.write("  </" + pPropertyName + ">");
        pOut.newLine();
    }

    private void writePropertyValue(BufferedWriter pOut, Individual pInd,
            String pPropertyName) throws IOException {
        /***
         * Este metodo e responsavel por o valor da propriedade
         * informada no parametro pPropertyName. O valor recuperado e
         * gravado no XML de saida, representado pelo parametro pOut
         */
        RDFNode nd;

        nd = pInd.getPropertyValue((Property) omvrModel.getProperty(nsOMV + pPropertyName));
        if (nd != null) {
            if (nd.isLiteral()) {
                pOut.write("  <" + pPropertyName + ">" + ((Literal) nd).getLexicalForm() + "</" + pPropertyName + ">");
            } else if (nd.isResource()) {
                pOut.write("  <" + pPropertyName + ">" + ((Resource) nd).getLocalName() + "</" + pPropertyName + ">");
            }
            pOut.newLine();
        }
    }

    private String getPropertyValue(Individual pInd, String pPropertyName) {
        /***
         * Este metodo e responsavel por retornar o valor da propriedade
         * informada no parametro pPropertyName.
         */
        RDFNode nd;
        String propertyValue = "undefined";

        nd = pInd.getPropertyValue((Property) omvrModel.getProperty(nsOMV + pPropertyName));
        if (nd != null) {
            if (nd.isLiteral()) {
                propertyValue = ((Literal) nd).getLexicalForm();
            } else if (nd.isResource()) {
                propertyValue = ((Resource) nd).getLocalName();
            }
        }

        return propertyValue;
    }


    public void generateOMVROntologyMetricViewXML(String pOntoName, String fileOut) {
        /***
         * Este metodo e responsavel por gerar o arquivo XML que contem as
         * informacoes das métricas calculadas para a ontologia.
         */

        File fileXMLOntologies;
        BufferedWriter out;
        RDFNode nd;
        String ontologyName;
        String metricInstanceID;
        String ontologyFile = null;

        omvrModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        omvrModel.read(omvrFile, "RDF/XML");
        ontologyMetricClass = omvrModel.getOntClass(nsOMV + "OntologyMetric");

        try {
            fileXMLOntologies = new File(fileOut);
            out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(fileXMLOntologies), "UTF8"));

            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<OntologyMetric>");
            out.newLine();

            for (Iterator i = ontologyMetricClass.listInstances(); i.hasNext();) {
                Individual ind = (Individual) i.next();
                nd = ind.getPropertyValue((Property) omvrModel.getProperty(nsOMV + "scoredOntology"));

                if (nd != null) {
                    ontologyName = ((Resource) nd).getLocalName();

                    if (pOntoName.equalsIgnoreCase(ontologyName)) {

                        if (ontologyFile == null) {                           
                           ontologyFile = getOMVRInformation("Ontology",ontologyName,"ontologyFile");
                           /***
                            * Uma vez obtido o nome do arquivo, sera removido a sua extensão
                            * original para substitui-la pela extensão do grafico da ontologia,
                            * o qual é gerado pela pagina de submissão de ontologias.
                            */
                           ontologyFile = ontologyFile.substring(0, ontologyFile.indexOf('.'))+".png";
                           
                        }

                        metricInstanceID = getPropertyValue(ind,"usedMetric");

                        out.write(" <usedMetric>");
                        out.newLine();
                                         
                        out.write("  <usedMetric>");
                        out.write(getMetricInformation(metricInstanceID,"name"));
                        out.write("  </usedMetric>");
                        out.newLine();

                        writePropertyValue(out,ind,"scoredOntology");
                        writePropertyValue(out,ind,"creationDate");
                        writePropertyValue(out,ind,"value");
                        writePropertyValue(out,ind,"comments");

                        out.write("  <metricDescription>");
                        out.write(getMetricInformation(metricInstanceID,"usages"));
                        out.write("</metricDescription>");
                        out.newLine();

                        out.write("  <metricChart>");
                        out.write(ontologyFile);
                        out.write("</metricChart>");
                        out.newLine();
                        
                        out.write(" </usedMetric>");
                        out.newLine();
                    }
                }
            }
            out.write("</OntologyMetric>");
            out.newLine();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        omvrModel.close();
    }

    public void generateOMVROntologyEvaluationViewXML(String pOntoName, String fileOut) {
        /***
         * Este metodo e responsavel por gerar o arquivo XML que contem as
         * informacoes das métricas calculadas para a ontologia.
         */

        File fileXMLOntologies;
        BufferedWriter out;
        RDFNode nd;
        String ontologyName;
        String partyInstance;

        omvrModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        omvrModel.read(omvrFile, "RDF/XML");
        ontologyEvaluationClass = omvrModel.getOntClass(nsOMV + "OntologyEvaluation");

        try {
            fileXMLOntologies = new File(fileOut);
            out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(fileXMLOntologies), "UTF8"));

            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<OntologyEvaluation>");
            out.newLine();

            for (Iterator i = ontologyEvaluationClass.listInstances(); i.hasNext();) {
                Individual ind = (Individual) i.next();
                nd = ind.getPropertyValue((Property) omvrModel.getProperty(nsOMV + "evaluatedOntology"));

                if (nd != null) {
                    ontologyName = ((Resource) nd).getLocalName();

                    if (pOntoName.equalsIgnoreCase(ontologyName)) {
                        out.write(" <evaluationCriteria>");out.newLine();

                        out.write("  <hasEvaluationCriteria>");
                        out.write(getOMVRInformation("EvaluationCriteria",getPropertyValue(ind,"hasEvaluationCriteria"),"criteriaQuestion"));
                        out.write("</hasEvaluationCriteria>");out.newLine();

                        writePropertyValue(out,ind,"evaluatedOntology");
                        writePropertyValue(out,ind,"evaluationDate");
                        writePropertyValue(out,ind,"evaluationValue");
                        writePropertyValue(out,ind,"evaluationComments");

                        out.write("  <hasEvaluator>");out.newLine();
                        out.write("   <evaluator>");
                        out.write(getOMVRInformation("Person",getPropertyValue(ind,"hasEvaluator"),"firstName")+
                                  " "+
                                  getOMVRInformation("Person",getPropertyValue(ind,"hasEvaluator"),"lastName"));
                        out.write("</evaluator>");out.newLine();
                        out.write("  </hasEvaluator>");out.newLine();

                        out.write("  <mailAddress>");
                        out.write(getOMVRInformation("Person",getPropertyValue(ind,"hasEvaluator"),"eMail"));
                        out.write("</mailAddress>");out.newLine();

                        out.write("  <organization>");
                        out.write(getOMVRInformation("Person",getPropertyValue(ind,"hasEvaluator"),"hasAffiliatedParty"));
                        out.write("</organization>");out.newLine();

                        out.write(" </evaluationCriteria>");out.newLine();
                    }
                }
            }
            out.write("</OntologyEvaluation>");
            out.newLine();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        omvrModel.close();
    }

    public String getMetricInformation(String pMetricInstance, String pProperty) {
        OntClass metricClass = omvrModel.getOntClass(nsOMV + "Metric");
        String   metricInformation = "undefined";

        for (Iterator i = metricClass.listInstances(); i.hasNext();) {
            Individual ind = (Individual) i.next();

            if (pMetricInstance.equalsIgnoreCase(ind.getLocalName())) {
               metricInformation = getPropertyValue(ind,pProperty);
               break;
            }
        }

        return metricInformation;
    }

    public String getOMVRInformation(String pClassName, String pClassInstance, String pProperty) {
        /***
         * Este metodo e resposavel por recurperar o valor de uma propriedade
         * da instancia de uma classe.
         */
        OntClass metricClass = omvrModel.getOntClass(nsOMV + pClassName);
        String   someInformation = "undefined";

        for (Iterator i = metricClass.listInstances(); i.hasNext();) {
            Individual ind = (Individual) i.next();

            if (ind.getLocalName() != null) {
                if (pClassInstance.equalsIgnoreCase(ind.getLocalName())) {
                   someInformation = getPropertyValue(ind,pProperty);
                   break;
                }
            }
        }

        return someInformation;
    }

    public Vector<String> execOMVRSPARQLQuery(String pQuery) {
        String queryStmt;
        String prolog = "PREFIX omvr: <"+nsOMV+">";
        Vector<String> resultSet = new Vector<String>();

        omvrModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        omvrModel.read(omvrFile, "RDF/XML");

        queryStmt = prolog + System.getProperty("line.separator")+pQuery;

        //Query query = QueryFactory.create(queryStmt);
        QueryExecution qexec = QueryExecutionFactory.create(queryStmt, omvrModel);

        try {
            // Assumption: it's a SELECT query.
            ResultSet rs = qexec.execSelect() ;

            // The order of results is undefined.
            for ( ; rs.hasNext() ; )
            {
                QuerySolution rb = rs.nextSolution() ;

                // Get title - variable names do not include the '?' (or '$')
                RDFNode x = rb.get("ontology") ;

                // Check the type of the result value
                if ( x.isLiteral() )
                {
                    Literal titleStr = (Literal)x  ;
                    resultSet.add(titleStr.getLexicalForm()) ;
                }
                else if (x.isResource()) {
                   resultSet.add(((Resource) x).getLocalName());
                }
            }
        }
        finally
        {
            // QueryExecution objects should be closed to free any system resources
            qexec.close() ;
        }

        return resultSet;
    }
}