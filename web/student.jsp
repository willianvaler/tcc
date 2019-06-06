<%-- 
    Document   : student.jsp
    Created on : 08/10/2018, 22:15:12
    Author     : willi
--%>

<%@page import="wav.tcc.entities.Module"%>
<%@page import="wav.tcc.transactions.ModulesTransactions"%>
<%@page import="wav.tcc.entities.Turma"%>
<%@page import="java.util.List"%>
<%@page import="wav.tcc.transactions.ClassTransactions"%>
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
            body {
              text-align: center;
            }

            .gauge {
              width:200px; height:160px;
              display: inline-block;
              margin: 1em;
            }

            p {
              display: block;
              width: 450px;
              margin: 2em auto;
              text-align: left;
            }
            
            #container
            {
                position: relative;
                width: 100%;
                height: 100%;
            }
          </style>
    </head>
    <body>
        <div class="page-content">
            <div class="row">
                <jsp:include page = "aside.jsp" />
                <div class="col-md-10">
                    <div class="content-box-large">
                        <div class="panel-body">
                            <select id="classes" style="width: 40%">
                                <%
                                    List<Turma> turmas = new ClassTransactions().getTurmas();
                                    
                                    for( Turma t : turmas )
                                    {
                                %>
                                        <option value="<%=t.getId()%>"> <%=t.getName() + " - " + t.getId()%> </option>
                                <%
                                    }
                                %>
                            </select>
                            <button id="teste" class="teste" style="width: 60px">OK</button>

                            <table id="container" style="width: 100%" > </table>
                        </div>
                    </div>
                </div>
                <script src="js/raphael-2.1.4.min.js"></script>
                <script src="js/justgage.js"></script>
                <script src="js/jquery.js"></script>

                <script>
                    function addGauge( id, value, title, label )
                    {
                        new JustGage({
                            id: id,
                            value: value,
                            min: 0,
                            max: 100,
                            title: title,
                            label: label,
                            gaugeColor: "#d31800",
                            levelColors: [ "#026607"]
                        });
                    }
                    
                    $(document).on( "click", "#teste", function(){   
                        var e = document.getElementById( "classes" );
                        var id = e.options[e.selectedIndex].value;
                    
                        $.get( "ClassServlet", {ClassId:id}, function( data ){

                            $( '#container' ).empty();
                            
                            $.each( data, function( index, item ) {
                                
                                var confidence = item[0];
                                var effort = item[1];
                                var name = item[2];
                                
                                var idConfidence = "co" + index;
                                var idEffort = "ef" + index;
                                
                                $( '#container' ).prepend( $( '<tr>' +
                                                                  '<td><div><h3>' + name + '</h3></div></td>' +
                                                                  '<td><div class="gauge" id="' + idConfidence + '"></div></td>' + 
                                                                  '<td><div class="gauge" id="' + idEffort     + '"></div></td>' +
                                                               '</tr>' ) );
                                                       
                                addGauge( idConfidence, confidence * 100, "Confiança", "Belief" );
                                addGauge( idEffort, effort * 100, "Esforço", "Belief" );
                            });
                        });
                    });

                </script>
            </div>
        </div>
    </body>
</html>
