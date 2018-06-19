/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontolpomvr;

/**
 *
 * @author Anderson
 */
public class WriteOntologyXMLTree {
    public static void main(String[] args) {
        /***
         * Parametros:
         * args[1]: Nome do arquivo XML de saída que conterá as ontologias
         * armazenadas no OMVR. O formato do XML segue o padrao definido pela
         * biblioteca Javascript que monta a arvore de navegação da página
         * de pesquisa de ontologias.
         */

        OMVR inst;
        try {
            System.out.println("Gerando arquivo XML: "+args[0]);
            inst = new OMVR();
            inst.generateOMVROntologyTreeXML(args[0]);
        }
        catch (Exception e) {
            System.out.println("Erro:\n"+e.getMessage());
            System.exit(-1);
        }
    }
}
