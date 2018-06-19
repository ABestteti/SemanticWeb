/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontolpomvr;

/**
 *
 * @author Anderson
 */
public class WriteOntologyListXML {

    public static void main(String[] args) {
        /***
         * Parametros:
         * args[0]: Nome do arquivo XML de saída que conterá a lista de ontologias
         *          armazenadas no OMVR.
         */

        if (args.length > 0) {
            OMVR inst = new OMVR();

            System.out.println("Gerando arquivo XML com lista de ontologias.");
            inst.generateOMVROntologyListXML(args[0]);
        }
        else {
            System.out.println("Informe o nome da ontologia para gerar o arquivo");
        }
    }

}
