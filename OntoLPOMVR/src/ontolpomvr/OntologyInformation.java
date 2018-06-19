/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontolpomvr;

import java.io.Serializable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

// Jena API  Imports
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 *
 * @author anderson
 */
public class OntologyInformation implements Serializable {

    private OntModel ontInput     = null;
    private Ontology ontoInstance = null;

    // Atributos para alimentar informações da classe Ontology do OMVR.
    private int    ontoNumInstances       = 0;
    private int    ontoNumClasses         = 0;
    private int    ontoNumProperties      = 0;
    private int    ontoNumAxioms          = 0;
    private String ontoName               = null;
    private String ontoNotes              = null;
    private String fileName               = null;
    private String ontoOntologyFile       = null;
    private String ontoResourceLocator    = null;
    private String ontoOntologySyntax     = null;
    private String ontoAcronym            = null;
    private String ontoDescription        = null;
    private String[] ontoNaturalLanguage  = null;
    private String ontoCreationDate       = null;
    private String ontoDocumentation      = null;
    private String ontoModificationDate   = null;
    private String ontoStatus             = null;
    private String ontoHasFormalityLevel  = null;
    private String ontoHasLicense         = null;
    private String ontoVersion            = null;
    private String ontoHasPriorVersion    = null;
    private String ontoIsOfType           = null;
    private String[] ontoKeyClasses       = null;
    private String[] ontoKeyWords         = null;
    private String[] ontoKnownUsage       = null;
    private String ontoReference          = null;
    private String[] ontoConformsTKRP     = null;
    private String[] ontoDesignerForOntoTask = null;
    private String[] ontoEndorsedBy          = null;
    private String[] ontoHasContributor      = null;
    private String[] ontoHasCreator          = null;
    private String[] ontoHasDomain           = null;
    private String[] ontoIsBackwardCompatibleWith = null;
    private String[] ontoIsBasedOn                = null;
    private String[] ontoIsIncompatibleWith       = null;
    private String[] ontoUsedByProject            = null;
    private String[] ontoUsedOntologyEngineeringMethodology = null;
    private String[] ontoUsedOntologyEngineeringTool        = null;
    private String[] ontoUserImports                        = null;
    
    @SuppressWarnings("empty-statement")
    public void ontologyInformation(String pOntoInput) {
        
        InputStream fp;

        if (pOntoInput == null) {
            throw new IllegalArgumentException("Atenção: arquivo OWL da ontologia não foi informado.");
        }
        
        setFileName((String) pOntoInput);

        /***
         * Recupera o nome do arquivo OWL da ontologia para armazenar na
         * OMVR, classe Ontology, dataProperty ontologyFile
         */
        setOntoOntologyFile(getFileName());

        if (!pOntoInput.startsWith("file:") && !pOntoInput.startsWith("http:")) {
            throw new IllegalArgumentException("Deve ser informado \"file:\" ou \"http:\" + [caminho] + arquivo." +
                    " Arquivo informado: "+pOntoInput);
        }

        if ((fp = FileManager.get().open(getFileName())) == null)
        {
            throw new IllegalArgumentException("Arquivo: "+getFileName()+" não encontrado.");
        }
        else {
            try {
                fp.close();
            } catch (IOException ex) {
                Logger.getLogger(OntologyInformation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (getFileName().endsWith(".owl")) {
           ontInput = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
           setOntologySyntax("OWL-XML");
        }
        else if (getFileName().endsWith(".rdfs") || getFileName().endsWith(".rdf")) {
           ontInput = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
           setOntologySyntax("RDF-XML");
        }
        else if (getFileName().endsWith(".daml")) {
           ontInput = ModelFactory.createOntologyModel(OntModelSpec.DAML_MEM);
           setOntologySyntax("DAML");
        }

        System.out.println("Processando arquivo: "+getFileName());

        ontInput.read(getFileName(),"RDF/XML");

        if (!ontInput.isEmpty())
        {
            OntClass c = null;
            
            /**
             * Criar a referencia para a ontologia que esta sendo processada
             */
            setOntology();

            for (Iterator j = ontInput.listRestrictions(); j.hasNext();j.next()) {
                ontoNumAxioms++;
            }

            for (Iterator i = ontInput.listNamedClasses(); i.hasNext();) {
                c = (OntClass) i.next();

                ontoNumClasses++;
                for (Iterator j = c.listInstances(); j.hasNext(); j.next()) {
                    ontoNumInstances++;
                }

                for (Iterator j = c.listDeclaredProperties(true); j.hasNext();j.next()) {
                    ontoNumProperties++;
                }
            }

            setResourceLocator();
        }
    }

    public String getOntoFileName() {
        return fileName;
    }

    public String getOntologyLanguage() {
        return ontInput.getProfile().getLabel().replace(' ', '-');
    }

    public int getOntoNumClasses() {
        return ontoNumClasses;
    }

    public int getOntoNumInstances() {
        return ontoNumInstances;
    }

    public int getOntoNumProperties() {
        return ontoNumProperties;
    }

    public int getOntoNumAxioms() {
        return ontoNumAxioms;
    }

    public void setOntoName(String pOntoName) {
        ontoName = pOntoName;
    }

    public String getOntoName() {
        return ontoName;
    }

    public void setOntologySyntax(String pOntoSyntax) {
        ontoOntologySyntax = pOntoSyntax;
    }
    public String getOntologySyntax() {
        return ontoOntologySyntax;        
    }

    public void setDescription(String pOntoDescription) {
        ontoDescription = pOntoDescription;
    }
    
    public String getDescription() {
        return ontoDescription;        
    }

    public void setNaturalLanguage(String[] pOntoNaturalLanguage) {
        ontoNaturalLanguage = pOntoNaturalLanguage;
    }

    public String[] getNaturalLanguage() {
        /***
         * Inicializacao com valor padrao null
         * para evitar problemas no momento de criar
         * uma nova instancia no OMVR.
         */
        if (ontoNaturalLanguage == null) {
            ontoNaturalLanguage = new String[1];
            ontoNaturalLanguage[0] = null;
        }
        return ontoNaturalLanguage;
    }

    public void setCreationDate(String pOntoCreationDate) {
        ontoCreationDate = pOntoCreationDate;
    }

    public String getCreationDate() {
        return ontoCreationDate;
    }

    public void setDocumentation(String pOntoDocumentation) {
        ontoDocumentation = pOntoDocumentation;
    }

    public String getDocumentation() {
        return ontoDocumentation;
    }

    public void setModificationDate(String pOntoModificationDate) {
        ontoModificationDate = pOntoModificationDate;
    }

    public String getModificationDate() {
        return ontoModificationDate;
    }

    public void setStatus(String pOntoStatus) {
        ontoStatus = pOntoStatus;
    }

    public String getStatus() {
        return ontoStatus;
    }

    public void setHasFormalityLevel(String pOntoHasFormalityLevel) {
        if (pOntoHasFormalityLevel != null) {
            if (pOntoHasFormalityLevel.equalsIgnoreCase("Esquema")) {
                ontoHasFormalityLevel = "Schema";
            } else if (pOntoHasFormalityLevel.equalsIgnoreCase("Taxonomia")) {
                ontoHasFormalityLevel = "Taxonomy";
            } else if (pOntoHasFormalityLevel.equalsIgnoreCase("Terminologia")) {
                ontoHasFormalityLevel = "Terminology";
            } else if (pOntoHasFormalityLevel.equalsIgnoreCase("Tesauro")) {
                ontoHasFormalityLevel = "Thesaurus";
            } else if (pOntoHasFormalityLevel.equalsIgnoreCase("Vocabulário")) {
                ontoHasFormalityLevel = "Vocabulary";
            } else {
                ontoHasFormalityLevel = pOntoHasFormalityLevel;
            }
        }
    }

    public String getHasFormalityLevel() {
        return ontoHasFormalityLevel;
    }

    public void setHasLicense(String pOntoHasLicense) {
        if (pOntoHasLicense.equalsIgnoreCase("Academic Free License (AFL)")) {
            ontoHasLicense = "AcademicFreeLicense_AFL";
        } else if (pOntoHasLicense.equalsIgnoreCase("Apple Public Source License (APSL)")) {
            ontoHasLicense = "ApplePublicSourceLicense_APSL";
        } else if (pOntoHasLicense.equalsIgnoreCase("Common Public License (CPL)")) {
            ontoHasLicense = "CommonPublicLicense_CPL";
        } else if (pOntoHasLicense.equalsIgnoreCase("Creative Commons (By NC SA)")) {
            ontoHasLicense = "CreativeCommon_By-NC-SA2.5BR";
        } else if (pOntoHasLicense.equalsIgnoreCase("General Public License (GPL)")) {
            ontoHasLicense = "GeneralPublicLicense_GPL";
        } else if (pOntoHasLicense.equalsIgnoreCase("IBM Public License (IBM PL)")) {
            ontoHasLicense = "IBMPublicLicenseIBM_PL";
        } else if (pOntoHasLicense.equalsIgnoreCase("INTEL Open Source License (INTEL OSL)")) {
            ontoHasLicense = "INTELOpenSourceLicenseINTEL_OSL";
        } else if (pOntoHasLicense.equalsIgnoreCase("Lesser General Public License (LGPL)")) {
            ontoHasLicense = "LesserGeneralPublicLicense_LGPL";
        } else if (pOntoHasLicense.equalsIgnoreCase("Modified BSD License (mBSD)")) {
            ontoHasLicense = "ModifiedBSDLicense_mBSD";
        } else if (pOntoHasLicense.equalsIgnoreCase("Open Software License (OSL)")) {
            ontoHasLicense = "OpenSoftwareLicense_OSL";
        } else {
            ontoHasLicense = pOntoHasLicense;
        }
    }

    public String getHasLicense() {
        return ontoHasLicense;
    }

    public void setHasPriorVersion(String pOntoHasPriorVersion) {
        ontoHasPriorVersion = pOntoHasPriorVersion;
    }

    public String getHasPriorVersion() {
        return ontoHasPriorVersion;
    }

    public void setIsOfType(String pOntoIsOfType) {
        if (pOntoIsOfType != null) {
            if (pOntoIsOfType.equalsIgnoreCase("Aplicação")) {
                ontoIsOfType = "ApplicationOntology";
            } else if (pOntoIsOfType.equalsIgnoreCase("Core")) {
                ontoIsOfType = "CoreOntology";
            } else if (pOntoIsOfType.equalsIgnoreCase("Domínio")) {
                ontoIsOfType = "DomainOntology";
            } else if (pOntoIsOfType.equalsIgnoreCase("Tarefa")) {
                ontoIsOfType = "TaskOntology";
            } else if (pOntoIsOfType.equalsIgnoreCase("Topo")) {
                ontoIsOfType = "UpperLevelOntology";
            }
        }
    }

    public String getIsOfType() {
        return ontoIsOfType;
    }

    public void setKeyClasses(String[] pOntoKeyClasses) {
        ontoKeyClasses = pOntoKeyClasses;
    }

    public String[] getKeyClasses() {
        /***
         * Inicializacao com valor padrao null
         * para evitar problemas no momento de criar
         * uma nova instancia no OMVR.
         */
        if (ontoKeyClasses == null) {
            ontoKeyClasses = new String[1];
            ontoKeyClasses[0] = null;
        }
        return ontoKeyClasses;
    }

    public void setKeyWords(String[] pOntoKeyWords) {
        ontoKeyWords = pOntoKeyWords;
    }

    public String[] getKeyWords() {
        return ontoKeyWords;
    }

    public void setKnownUsage(String[] pOntoKnownUsage) {
        ontoKnownUsage = pOntoKnownUsage;
    }

    public String[] getKnownUsage() {
        return ontoKnownUsage;
    }

    public void setReference(String pOntoReference) {
        ontoReference = pOntoReference;
    }

    public String getReference() {
        return ontoReference;
    }

    public void setConformsTKRParadigm(String[] pOntoConformsTKRP) {
        ontoConformsTKRP = pOntoConformsTKRP;
    }

    public String[] getConformsTKRParadigm() {
        return ontoConformsTKRP;
    }

    public void setDesignedForOntoTask(String[] pOntoTask) {
        ontoDesignerForOntoTask = pOntoTask;
    }

    public String[] getDesignedForOntoTask() {
        return ontoDesignerForOntoTask;
    }

    public void setEndorsedBy(String[] pOntoEndorsedBy) {
        ontoEndorsedBy = pOntoEndorsedBy;
    }

    public String[] getEndorsedBy() {
        return ontoEndorsedBy;
    }

    public void setHasContributor(String[] pOntoHasContributor) {
        ontoHasContributor = pOntoHasContributor;
    }

    public String[] getHasContributor() {
        return ontoHasContributor;
    }

    public void setHasCreator(String[] pOntoHasCreator) {
        ontoHasCreator = pOntoHasCreator;
    }

    public String[] getHasCreator() {
        return ontoHasCreator;
    }

    public void setHasDomain(String[] pOntoHasDomain) {
        for (int i=0;i<pOntoHasDomain.length;i++) {
            if (pOntoHasDomain[i] != null) {
                if (pOntoHasDomain[i].equalsIgnoreCase("Tecnologia da Informação")) {
                    pOntoHasDomain[i] = "Tecnologia_da_informação";
                }
            }
        }
        ontoHasDomain = pOntoHasDomain;
    }

    public String[] getHasDomain() {
        return ontoHasDomain;
    }

    public void setIsBackwardCompatibleWith(String[] pOntoIsBackCompatibleWith) {
        ontoIsBackwardCompatibleWith = pOntoIsBackCompatibleWith;
    }

    public String[] getIsBackwardCompatibleWith() {
        return ontoIsBackwardCompatibleWith;
    }

    public void setIsBasedOn(String[] pOntoIsBasedOn) {
        ontoIsBasedOn = pOntoIsBasedOn;
    }

    public String[] getIsBasedOn() {
        return ontoIsBasedOn;
    }

    public String getCommentMetaData() {
        /**
         * Informacao obtida do metadado da ontologia
         */
        try {
           return ontoInstance.getComment(null);
        }
        catch (Exception e) {
            return "Comentário não informado na ontologia.";
        }
    }

    private void setResourceLocator() {
        /**
         * Informacao obtida do metadado da ontologia
         */
       try {
           ontoResourceLocator = ontoInstance.getURI();
       }
       catch (Exception e) {
           ontoResourceLocator = "Informação não encontrada na ontologia.";
       }
    }

    public String getResourceLocator() {
        return ontoResourceLocator;
    }
    
    private void setOntology() {
        String nsTmp = null;

        /**
         * <p> Recupera a URI base do arquivo da ontologia com objetivo de
         * recuperar o valor da propriedade de meta-dados comment do arquivo
         * OWL.
         * O resultado final eh uma referencia da instancia da ontologia que
         * esta sendo lida.
         * O objeto ontoInstance sera usado por outros metodos que recuperam
         * informacoes de meta-dados da ontologia, como por exemplo:
         * - Comment
         * - URI da ontologia
         * - Default Namespace
         */
        for (Iterator i = ontInput.getBaseModel().listNameSpaces();i.hasNext();) {
            nsTmp = (String) i.next();
            nsTmp = nsTmp.substring(0, nsTmp.indexOf('#'));
            ontoInstance  = (Ontology) ontInput.getOntology(nsTmp);

            if (ontoInstance != null)
               break;
       }
    }

    public String[] getOntoIsIncompatibleWith() {
        return ontoIsIncompatibleWith;
    }

    public void setIsIncompatibleWith(String[] ontoIsIncompatibleWith) {
        this.ontoIsIncompatibleWith = ontoIsIncompatibleWith;
    }

    /**
     * @return the ontoUsedByProject
     */
    public String[] getUsedByProject() {
        return ontoUsedByProject;
    }

    /**
     * @param ontoUsedByProject the ontoUsedByProject to set
     */
    public void setUsedByProject(String[] ontoUsedByProject) {
        this.ontoUsedByProject = ontoUsedByProject;
    }

    /**
     * @return the ontoUsedOntologyEngineeringMethodology
     */
    public String[] getUsedOntologyEngineeringMethodology() {
        return ontoUsedOntologyEngineeringMethodology;
    }

    /**
     * @param ontoUsedOntologyEngineeringMethodology the ontoUsedOntologyEngineeringMethodology to set
     */
    public void setUsedOntologyEngineeringMethodology(String[] ontoUsedOntologyEngineeringMethodology) {
        this.ontoUsedOntologyEngineeringMethodology = ontoUsedOntologyEngineeringMethodology;
    }

    /**
     * @return the ontoUsedOntologyEngineeringTool
     */
    public String[] getUsedOntologyEngineeringTool() {
        return ontoUsedOntologyEngineeringTool;
    }

    /**
     * @param ontoUsedOntologyEngineeringTool the ontoUsedOntologyEngineeringTool to set
     */
    public void setUsedOntologyEngineeringTool(String[] ontoUsedOntologyEngineeringTool) {
        this.ontoUsedOntologyEngineeringTool = ontoUsedOntologyEngineeringTool;
    }

    /**
     * @return the ontoUserImports
     */
    public String[] getUserImports() {
        return ontoUserImports;
    }

    /**
     * @param ontoUserImports the ontoUserImports to set
     */
    public void setUserImports(String[] ontoUserImports) {
        this.ontoUserImports = ontoUserImports;
    }

    /**
     * @return the ontoAcronym
     */
    public String getOntoAcronym() {
        return ontoAcronym;
    }

    /**
     * @param ontoAcronym the ontoAcronym to set
     */
    public void setOntoAcronym(String ontoAcronym) {
        this.ontoAcronym = ontoAcronym;
    }

    /**
     * @return the ontoVersion
     */
    public String getVersion() {
        return ontoVersion;
    }

    /**
     * @param ontoVersion the ontoVersion to set
     */
    public void setVersion(String ontoVersion) {
        this.ontoVersion = ontoVersion;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the ontoOntologyFile
     */
    public String getOntoOntologyFile() {
        return ontoOntologyFile;
    }

    /**
     * @param ontoOntologyFile the ontoOntologyFile to set
     */
    public void setOntoOntologyFile(String ontoOntologyFile) {
        String[] fileTemp = ontoOntologyFile.split("/");
        this.ontoOntologyFile = fileTemp[(fileTemp.length)-1];
    }
}