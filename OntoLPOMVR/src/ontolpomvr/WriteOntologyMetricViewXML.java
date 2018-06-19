/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontolpomvr;

/**
 *
 * @author Anderson
 */
public class WriteOntologyMetricViewXML {
    public static void main(String[] args) {
        /***
         * Parametros:
         * args[0]: Nome da ontologia armazenada no OMVR
         * args[1]: Nome do arquivo XML de saída que conterá as métricas calculadas
         *          para a ontologia informada.
         */

        if (args.length > 0) {
            OMVR inst = new OMVR();

            System.out.println("Gerando arquivo XML com informações das métricas calculadas");
            inst.generateOMVROntologyMetricViewXML(args[0],args[1]);
        }
        else {
            System.out.println("Informe o nome da ontologia e o nome do arquivo XML das métricas.");
        }
    }
}
