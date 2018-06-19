<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="keywords" content="" />
        <meta name="description" content="" /><title>OntoLP | Portal de Ontologias</title>

        <!-- Include Ext and app-specific scripts: -->
        <script type="text/javascript" src="/extjs2.0/adapter/ext/ext-base.js"></script>
        <script type="text/javascript" src="/extjs2.0/ext-all-debug.js"></script>

        <!-- Entry for pointo to OntoLPOMV Browser-->
        <script type="text/javascript" src="XmlTreeLoader.js"></script>
        <script type="text/javascript" src="ontolpomvr.js"></script>

        <!-- Include Ext stylesheets here: -->
        <link rel="stylesheet" type="text/css" href="/extjs2.0/resources/css/ext-all.css"/>
        <link rel="stylesheet" type="text/css" href="column-tree.css"/>

        <!-- OntoLP stylesheets -->
        <link href="folha.css" rel="stylesheet" type="text/css" media="screen" />

    </head>
    <body>
        <form name="links"><!-- Design by Free CSS Templates http://www.freecsstemplates.org Released for free under a Creative Commons Attribution 2.5 License Name: Tastelessly Description: A very light design suitable for community sites and blogs. Version: 1.0 Released: 20080122 -->
        </form>
        <?php include 'inc/topo.php' ?>
        <hr />
        <div id="page">
            <div id="content">
                <div class="post">
                    <h1 class="title" class="corTitulo">OntoLP - OMVR</h1>
                    <div class="ViewOMV">
                        <h3>Recursos instanciados no meta modelo OMV</h3>
                        <br />
                            <div id="treeOMV"></div>
                           <!-- <div id="dataOMV"></div>-->
                        <br />
                    </div>
                </div>
            </div>
            <div style="clear: both;">&nbsp;</div>
        </div>
        <hr />
        <div id="footer">
            <p>©2007 All Rights Reserved.&nbsp;•&nbsp; Designed by <a href="http://www.freecsstemplates.org/" target="_blank">Free
            CSS Templates</a>.</p>
        </div>
        <script type="text/javascript">
            var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
            document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
        </script>
        <script type="text/javascript">
            var pageTracker = _gat._getTracker("UA-5307915-1");
            pageTracker._trackPageview();
        </script>
</body></html>