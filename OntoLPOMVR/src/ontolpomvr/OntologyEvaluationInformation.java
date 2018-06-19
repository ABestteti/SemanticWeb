/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontolpomvr;

import java.io.Serializable;

/**
 *
 * @author Anderson
 */
public class OntologyEvaluationInformation implements Serializable {

    private String hasEvaluationCriteria = null;
    private String OntologyName          = null;
    private String evaluationDate        = null;
    private String evaluatorName         = null;
    private String organizationName      = null;
    private String e_mail                = null;
    private String phone                 = null;
    private String evaluationComments    = null;
    private String value                 = null;

    /**
     * @return the hasEvaluationCriteria
     */
    public String getHasEvaluationCriteria() {
        return hasEvaluationCriteria;
    }

    /**
     * @param hasEvaluationCriteria the hasEvaluationCriteria to set
     */
    public void setHasEvaluationCriteria(String hasEvaluationCriteria) {
        this.hasEvaluationCriteria = hasEvaluationCriteria;
    }

    /**
     * @return the OntologyName
     */
    public String getOntologyName() {
        return OntologyName;
    }

    /**
     * @param OntologyName the OntologyName to set
     */
    public void setOntologyName(String OntologyName) {
        this.OntologyName = OntologyName;
    }

    /**
     * @return the evaluationDate
     */
    public String getEvaluationDate() {
        return evaluationDate;
    }

    /**
     * @param evaluationDate the evaluationDate to set
     */
    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    /**
     * @return the evaluatorName
     */
    public String getEvaluatorName() {
        return evaluatorName;
    }

    /**
     * @param evaluatorName the evaluatorName to set
     */
    public void setEvaluatorName(String evaluatorName) {
        this.evaluatorName = evaluatorName;
    }

    /**
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * @return the e_mail
     */
    public String getE_mail() {
        return e_mail;
    }

    /**
     * @param e_mail the e_mail to set
     */
    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the evaluationComments
     */
    public String getEvaluationComments() {
        return evaluationComments;
    }

    /**
     * @param evaluationComments the evaluationComments to set
     */
    public void setEvaluationComments(String evaluationComments) {
        this.evaluationComments = evaluationComments;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
