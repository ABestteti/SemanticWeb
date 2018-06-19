<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!--
Design by Free CSS Templates
http://www.freecsstemplates.org
Released for free under a Creative Commons Attribution 2.5 License

Name: Tastelessly
Description: A very light design suitable for community sites and blogs.
Version: 1.0
Released: 20080122
-->

<?php

include_once('class.phpmailer.php');
$mail   = new PHPMailer();

$acao = $_GET["acao"];

if ($acao == "enviar") {

    $FileFormData = 'uploadedFiles/OntoLPUpload.xml';

    if (!($C=fopen($FileFormData,"w+"))) {
        echo 'Erro ao criar o arquivo de gravacão do formulário';
        exit;
    }

    fwrite($C,'<?xml version="1.0"?>'."\n");
    fwrite($C,'<OntoLP>'."\n");
    fwrite($C,'<nome>'                 . $_POST["q0"]  . '</nome>'."\n");
    fwrite($C,'<mail>'                 . $_POST["q1"]  . '</mail>'."\n");
    fwrite($C,'<instituicao>'          . $_POST["q2"]  . '</instituicao>'."\n");
    fwrite($C,'<grupo>'                . $_POST["q3"]  . '</grupo>'."\n");
    fwrite($C,'<projeto>'              . $_POST["q4"]  . '</projeto>'."\n");
    fwrite($C,'<site>'                 . $_POST["q5"]  . '</site>'."\n");
    fwrite($C,'<aplicacao>'            . $_POST["q6"]  . '</aplicacao>'."\n");
    fwrite($C,'<producaoCientifica>'   . $_POST["q7"]  . '</producaoCientifica>'."\n");
    fwrite($C,'<comentarios>'          . $_POST["q8"]  . '</comentarios>'."\n");
    fwrite($C,'<ontoAnoCronstrucao>'   . $_POST["q10"] . '</ontoAnoCronstrucao>'."\n");
    fwrite($C,'<ontoDominio>'          . $_POST["q11"] . '</ontoDominio>'."\n");
    fwrite($C,'<ontoLingua>'           . $_POST["q12"] . '</ontoLingua>'."\n");
    fwrite($C,'<ontoNumEspecialistas>' . $_POST["q13"] . '</ontoNumEspecialistas>'."\n");
    fwrite($C,'<ontoLinguagem>'        . $_POST["q14"] . '</ontoLinguagem>'."\n");
    fwrite($C,'<ontoURL>'              . $_POST["q15"] . '</ontoURL>'."\n");
    fwrite($C,'<ferrNome>'             . $_POST["q16"] . '</ferrNome>'."\n");
    fwrite($C,'<ferrLinguagem>'        . $_POST["q17"] . '</ferrLinguagem>'."\n");
    fwrite($C,'<ferrLicenca>'          . $_POST["q18"] . '</ferrLicenca>'."\n");
    fwrite($C,'<outroDescRecurso>'     . $_POST["q19"] . '</outroDescRecurso>'."\n");
    fwrite($C,'</OntoLP>'."\n");

    fclose($C);

        /*$msg	.= "<p>Nome: ".$nome."<p>";
        $msg	.= "<p>E-mail: ".$email."<p>";
        $msg	.= "<p>Grupo: ".$_POST["q2"]."<p>";
        $msg	.= "<p>Instituição: ".$_POST["q3"]."<p>";
        $msg	.= "<p>Projeto: ".$_POST["q4"]."<p> ";
        $msg	.= "<p>Site: ".$_POST["q5"]."<p>";
        $msg	.= "<p>Material para disponibilizar: ".$_POST["q6"]."<p>";
        $msg	.= "<p>Tipo material: <p>";
        foreach($_POST["q7"] as $q7)
        {
            $msg	.=   "- " . $q7 . "<BR>";
        }
        //implode(' - ', $_POST["q7"])."\n";
        //$msg	.= "<p>Outro tipo de material: ".$_POST["q21"]."<p>";
        $msg  .= "<p>Detalhes da Ontologia:<p>";
        $msg	.= "<p>Título: ".$_POST["q8"]."<p>";
        $msg	.= "<p>Domínio: ".$_POST["q9"]."<p>";
        $msg	.= "<p>Língua: ".$_POST["q10"]."<p>";
        $msg	.= "<p>Linguagem: ".$_POST["q11"]."<p>";
        $msg	.= "<p>Propósito: ".$_POST["q12"]."<p>";
        $msg	.= "<p>URL: ".$_POST["q13"]."<p>";
        $msg  .= "<p>Detalhes da Ferramenta:<p>";
        $msg	.= "<p>Nome: ".$_POST["q14"]."<p>";
        $msg	.= "<p>Linguagem: ".$_POST["q15"]."<p>";
        $msg	.= "<p>Propósito: ".$_POST["q16"]."<p>";
        $msg	.= "<p>Licença: ".$_POST["q17"]."<p>";
        $msg	.= "<p>URL: ".$_POST["q18"]."<p>";
        $msg  .= "<p>Detalhes do Recurso:<p>";
        $msg	.= "<p>Descrição: ".$_POST["21"]."<p>";
        $msg	.= "<p>URL: ".$_POST["q22"]."<p>";
        $msg	.= "<p>Referência: ".$_POST["q19"]."<p>";
        $msg	.= "<p>Comentários: ".$_POST["q20"]."<p>"; */



    //mail('portal.ontologia@gmail.com', 'Contato através do site', $msg);

    //mail($admmail, $subject, $mail_body, $header);

        /*$mail->IsSMTP(); // telling the class to use SMTP
        $mail->Host       = "smtp.pucrs.br"; // SMTP server

        $mail->From       = "ontolp@pucrs.br";
        $mail->FromName   = "Portal de Ontologia";

        $mail->Subject    = "Contato através do site";
        $mail->AltBody    = "To view the message, please use an HTML compatible email viewer!"; // optional, comment out and test

        $mail->MsgHTML($msg);

        $mail->AddAddress("portal.ontologia@gmail.com", "Portal de Ontologia");

        //$mail->AddAttachment("images/phpmailer.gif");             // attachment

        if(!$mail->Send()) {
            echo "Mailer Error: " . $mail->ErrorInfo;
        } else {
            //echo "Message sent!";}
            //echo "<a href='obrigado.php'>";}
            echo "<script language='JavaScript'> window.location.replace('obrigado.php');
            self.location = 'obrigado.php'; </script>";
          } */
    echo "<script language='JavaScript'> window.location.replace('obrigado.php');
           self.location = 'obrigado.php'; </script>";


}
?>


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="keywords" content="" />
        <meta name="description" content="" />
        <title>OntoLP | Portal de Ontologias</title>
        <link href="folha.css" rel="stylesheet" type="text/css" media="screen" />
    </head>

    <body>

        <script type="text/javascript" language=JavaScript>
            function divManager(idDiv, obj) {
                var div = document.getElementById(idDiv);
                if (obj.checked) {
                    div.style.display = "";
                } else {
                    div.style.display = "none";
                }
            }
        </script>


<?php include 'inc/topo.php' ?>

        <hr />

        <div id="page">

            <div id="content">
                <div class="post">
                    <h1 class="title">Contato</h1>
                    <div class="entry">

                        <table width="100%" cellpadding="2" cellspacing="0" class="tbmain">
                            <tr>
                                <td class="topleft" width="10" height="10">&nbsp;</td>
                                <td class="topmid">&nbsp;</td>
                                <td class="topright" width="10" height="10">&nbsp;</td>
                            </tr>
                            <tr>
                                <td class="midleft" width="10">&nbsp;&nbsp;&nbsp;</td>
                                <td class="midmid" valign="top">
                                    <form action="contato.php?acao=enviar" method="post" name="frmContato">
                                        <div id="main">
                                        <table width="520" cellpadding="5" cellspacing="0">
                                            <tr>
                                                <td width="100" class="left" ><label>Nome <span class="required">*</span></label></td>
                                                <td width="244" class="right"><input type="text" size="50" name="q0" class="text" value="" id="q0"
                                                                                     onblur="validate(this,'Required')" maxlength="100" maxsize="100" /></td>
                                            </tr>
                                            <tr>
                                                <td width="100" class="left" ><label>E-mail <span class="required">*</span></label></td>
                                                <td class="right"><input type="text" size="50" name="q1" class="text" value="" id="q1"
                                                                         onblur="validate(this,'Email')" maxlength="100" maxsize="100" /></td>
                                            </tr>
                                            <tr>
                                                <td width="100" class="left" ><label>Instituição/Orgão <span class="required">*</span></label></td>
                                                <td class="right"><input type="text" size="50" name="q2" class="text" value="" id="q2" maxlength="100" maxsize="100" /></td>
                                            </tr>
                                            <tr>
                                                <td width="100" class="left" ><label>Grupo de Pesquisa</label></td>
                                                <td class="right"><input type="text" size="50" name="q3" class="text" value="" id="q3" maxlength="100" maxsize="100" /></td>
                                            </tr>
                                            <tr>
                                                <td width="100" class="left" ><label>Projeto</label></td>
                                                <td class="right"><input type="text" size="50" name="q4" class="text" value="" id="q4" maxlength="100" maxsize="100" /></td>
                                            </tr>
                                            <tr>
                                                <td width="100" class="left" ><label>Site</label></td>
                                                <td class="right"><input type="text" size="50" name="q5" class="text" value="" id="q5" maxlength="100" maxsize="100" /></td>
                                            </tr>
                                            <tr>
                                                <td width="100" class="left" ><label>Aplicação</label></td>
                                                <td class="right"><input type="text" size="50" name="q6" class="text" value="" id="q6" maxlength="100" maxsize="100" /></td>
                                            </tr>
                                            <tr>
                                                <td width="100" class="left" valign="top"><label>Produção Científica</label></td>
                                                <td class="right"><textarea cols="50" rows="3" name="q7" class="text" id="q7" ></textarea></td>
                                            </tr>
                                            <tr>
                                                <td width="100" class="left" valign="top" ><label>Comentários</label></td>
                                                <td class="right"><textarea cols="50" rows="3" name="q8" class="text" id="q8" ></textarea></td>
                                            </tr>
                                            <tr>
                                                <td width="100" class="left" valign="top" ><label>Tipo do recurso:</label></td>
                                                <td valign="top" class="right">
                                                    <input type="checkbox" name="q9[]" class="other" id="q91" value="Ontologia"
                                                           onclick="divManager('divOntologia', this);"/><label class="left">Ontologia</label><br />
                                                    <input type="checkbox"  name="q9[]" class="other" id="q92" value="Ferramenta"
                                                           onclick="divManager('divFerramenta', this);"/><label class="left">Ferramenta</label><br />
                                                    <input type="checkbox"  name="q9[]" class="other" id="q93" value="Outro"
                                                           onclick="divManager('divOutro', this);" /><label class="left">Outro</label><br />
                                                </td>
                                            </tr>

                                        </table>

                                        <div id="divOntologia" style="display:none;">
                                            <table width="520" cellpadding="5" cellspacing="0">
                                                <tr>
                                                    <td colspan="2" class="left"><h2>Detalhes da Ontologia:</h2></td>
                                                </tr>
                                                <tr>
                                                    <td width="252" class="left" ><label>Ano de Construção <span class="required">*</span></label></td>
                                                    <td class="right"><input type="text" size="20" name="q10" class="text" value="" id="q10" maxlength="100" maxsize="100" /></td>
                                                </tr>
                                                <tr>
                                                    <td width="252" class="left" ><label>Domínio <span class="required">*</span></label></td>
                                                    <td class="right"><input type="text" size="20" name="q11" class="text" value="" id="q11" maxlength="100" maxsize="100" /></td>
                                                </tr>
                                                <tr>
                                                    <td width="252" class="left" ><label>Língua</label></td>
                                                    <td class="right"><input type="text" size="20" name="q12" class="text" value="" id="q12"  maxlength="100" maxsize="100" /> </td>
                                                </tr>
                                                <tr>
                                                    <td width="252" class="left" ><label>Número de Especialista  <span class="required">*</span></label></td>
                                                    <td class="right"><input type="text" size="20" name="q13" class="text" value="" id="q13"  maxlength="100" maxsize="100" /></td>
                                                </tr>
                                                <tr>
                                                    <td width="252" class="left" ><label>Linguagem</label></td>
                                                    <td class="right"><input type="text" size="20" name="q14" class="text" value="" id="q14" maxlength="100" maxsize="100" /></td>
                                                </tr>
                                                <tr>
                                                    <td width="252" class="left" ><label>URL</label></td>
                                                    <td class="right"><input type="text" size="20" name="q15" class="text" value="" id="q15" maxlength="100" maxsize="100" /></td>
                                                </tr>
                                            </table>
                                        </div>

                                        <div id="divFerramenta" style="display:none;">
                                            <table width="520" cellpadding="5" cellspacing="0">

                                                <tr>
                                                    <td colspan="2" class="left"><h2>Detalhes da Ferramenta:</h2></td>
                                                </tr>
                                                <tr>
                                                    <td width="252" class="left" ><label>Nome  <span class="required">*</span></label></td>
                                                    <td class="right"><input type="text" size="20" name="q16" class="text" value="" id="q16" maxlength="100" maxsize="100" /></td>
                                                </tr>
                                                <tr>
                                                    <td width="252" class="left" ><label>Linguagem</label></td>
                                                    <td class="right"><input type="text" size="20" name="q17" class="text" value="" id="q17" maxlength="100" maxsize="100" /></td>
                                                </tr>
                                                <tr>
                                                    <td width="252" class="left" ><label>Licença  <span class="required">*</span></label></td>
                                                    <td class="right"><input type="text" size="20" name="q18" class="text" value="" id="q18" maxlength="100" maxsize="100" /></td>
                                                </tr>
                                            </table>
                                        </div>

                                        <div id="divOutro" style="display:none;">
                                            <table width="520" cellpadding="5" cellspacing="0">
                                                <tr>
                                                    <td colspan="2" class="left" ><h2>Detalhes do Recurso:</h2></td>
                                                </tr>
                                                <tr>
                                                    <td width="252" class="left" valign="top"><label>Descrição do Recurso</label></td>
                                                    <td class="right"><textarea cols="30" rows="3" name="q19" class="text" id="q19" ></textarea></td>
                                                </tr>
                                            </table>
                                        </div>
                                        <!--
<tr >
<td colspan="2" class="left" ><span class="required">*</span>campos obrigatórios</td>
</tr>
                                        -->
                                        <table width="520" cellpadding="5" cellspacing="0">
                                            <tr>
                                                <td width="100" class="left" valign="top" ><label><input name="arquivo" type="file" /></label></td>
                                                <td class="right">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td width="100" class="left" valign="top" ><label><input name="arquivo" type="file" /></label></td>
                                                <td class="right">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td width="100" class="left" valign="top" ><label><input name="arquivo" type="file" /></label></td>
                                                <td class="right">&nbsp;</td>
                                            </tr>
                                        </table>
                                        <tr align= "center">
                                            <td width="100" class="left">&nbsp;</td>
                                            <td class="right">
                                                <input type="submit" class="btn" value="Enviar" /><input name="Reset" type="reset" class="btn" value="Limpar" />
                                            </td>
                                        </tr>
                                    </form>
                                </td>
                                <td class="midright" width="10">&nbsp;&nbsp;&nbsp;</td>
                            </tr>

                            <tr>
                                <td class="bottomleft" width="10" height="10">&nbsp;</td>
                                <td class="bottommid">&nbsp;</td>
                                <td class="bottomright" width="10" height="10">&nbsp;</td>
                            </tr>

                        </table>
                        <blockquote>&nbsp;</blockquote>
                    </div>
                </div>
            </div>
            <div style="clear: both;">&nbsp;</div>
        </div>
        <hr />
        <div id="footer">
            <p>&copy;2007 All Rights Reserved.&nbsp;&bull;&nbsp; Designed by <a href="http://www.freecsstemplates.org/" target="_blank">Free CSS Templates</a>.</p>
        </div>

    </body>
</html>
