/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontolpomvr;

/**
 *
 * @author Anderson
 */
public class WriteOntologyEvaluationViewXML {
    public static void main(String[] args) {

        if (args.length > 0) {
            OMVR inst = new OMVR();

            System.out.println("Gerando arquivo XML com informações das avaliações");
            inst.generateOMVROntologyEvaluationViewXML(args[0],args[1]);
        }
        else {
            System.out.println("Informe o nome da ontologia para gerar o arquivo");
        }
    }

}
