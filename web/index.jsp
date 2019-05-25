<%-- 
    Document   : index
    Created on : 08/09/2018, 19:09:20
    Author     : willi
--%>

<%@page import="wav.tcc.transactions.ExerciseTransactions"%>
<%@page import="wav.tcc.entities.Exercicio"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>TCC Willian Valer</title>
        <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
        <link rel="stylesheet" href="css/styles1.css">
        <link rel="stylesheet" href="css/styles1.min.css">
        
         <style>
            body 
            {
                text-align: center;
            }

            .gauge 
            {
                width:200px; height:160px;
                display: inline-block;
                margin: 1em;
                position: relative;
            }

            p {
                display: block;
                margin: 2em auto;
                text-align: left;
                font-size: 10pt;
                font-weight: bold;
            }
            
            #details
            {
                z-index: 2;
                width:400px;
                height: 250px;
                position: absolute; 
                margin-left: 30%; 
                background-color: white; 
                box-shadow: 5px 10px 18px #888888; 
                border-radius: 10px;
                visibility: hidden;
            }
            
            #container
            {
                position: relative;
                width: 100%;
                height: 100%;
                z-index: 1;
            }
            
            #studentContainer
            {
                position: relative;
                width: 100%;
                height: 100%;
                z-index: 1;
            }
          </style>
    </head>
    <body>
<!--        <ol>
            <%%>
            <li></li>
            <%%>
        </ol>-->
        <div class="page-content" style="position: relative;">
            <div class="row" style="position: relative;z-index: 1;">
                <jsp:include page = "aside.jsp" />
                <div class="col-md-10" style="position: relative;z-index: 1;">
                    <div class="content-box-large" style="position: relative;">
                        <div class="panel-body" style="position: relative;z-index: 1;">
                            <select id="exercises" style="width: 90%">
                                <%
                                    List<Exercicio> exercicios = new ExerciseTransactions().getExercises();
                                    
                                    for( Exercicio e : exercicios )
                                    {
                                %>
                                    <option value="<%=e.getId()%>"> <%=e.getId() + " - " + e.getName() + " - " + e.getTurma().getName()%> </option>
                                <%
                                    }
                                %>
                            </select>
                            <button id="teste" style="width: 60px">OK</button>
<!--                            <div id="details" class="panel-body">
                                <h2>Informações gerais:</h2>
                                <table style="width: 300px;float: right;">
                                    <tr>
                                        <td style="width: 150px;"><p>Visualizou Dicas:</p></td>
                                        <td style="width: 150px;"><p>90% Não</p></td>
                                    </tr>
                                    <tr>
                                        <td style="width: 150px;"><p>Visualizou Pseudo:</p></td>
                                        <td style="width: 150px;"><p>85% Não</p></td>
                                    </tr>
                                    <tr>
                                        <td style="width: 150px;"><p>Executou Código</p></td>
                                        <td style="width: 150px;"><p>98% Sim</p></td>
                                    </tr>
                                    <tr>
                                        <td style="width: 150px;"><p>Nível de Compreensão:</p></td>
                                        <td style="width: 150px;"><p>Médio</p></td>
                                    </tr>
                                    <tr>
                                        <td style="width: 150px;"><p>Retomadas</p></td>
                                        <td style="width: 150px;"><p>Poucas</p></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><button onclick="$('#details').css('visibility', 'hidden');">Fechar</button></td>
                                    </tr>
                                </table>
                            </div>-->
                            <table id="container" > </table>
                            <table id="studentContainer" > </table>
                        </div>
                    </div>
                </div>
                <script src="js/raphael-2.1.4.min.js"></script>
                <script src="js/justgage.js"></script>
                <script src="js/jquery.js"></script>

                <script>
                    function addGauge( id, value, title )
                    {
                        new JustGage({
                            id: id,
                            value: value,
                            min: 0,
                            max: 100,
                            title: title,
                            label: "",
                            gaugeColor: "#d31800",
                            levelColors: [ "#026607"]
                        });
                    }
                    
                    $(document).on( "click", "#teste", function()
                    {   
                        var e = document.getElementById( "exercises" );
                        var id = e.options[e.selectedIndex].value;
                        
                        $.get( "ConfidenceServlet", {ExerciseId:id}, function( data )
                        {

                            $( '#container' ).empty();
                            
                            $.each( data, function( index, item ) 
                            {
                                var confidence = item[0];
                                var effort = item[1];
                                var name = item[2];
                                var idConfidence = "co" + index;
                                var idEffort = "ef" + index;
                                
                                $( '#container' ).prepend( $( '<tr>' +
                                                                  '<td><div><h3>' + name + '</h3></div></td>' +
                                                                  '<td><div class="gauge" id="' + idConfidence + '"></div></td>' + 
                                                                  '<td><div class="gauge" id="' + idEffort     + '"></div></td>' +
                                                               '</tr>' + 
                                                               '<tr>' +
                                                               '<td></td>' +
                                                               '</tr>') );
                                                       
                                addGauge( idConfidence, confidence *100, "Confiança" );
                                addGauge( idEffort, effort * 100, "Esforço" );
                            });
                        });
                    });
                    
                    $(document).on( "click", "#moreInformation", function()
                    { 
                        $('#details').css('visibility', 'visible');
                    });

                </script>
            </div>
        </div>
    </body>
</html>
