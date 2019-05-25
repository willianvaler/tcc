/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wav.tcc.net;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import norsys.netica.Environ;
import norsys.netica.Net;
import norsys.netica.Node;
import norsys.netica.Streamer;
import wav.tcc.entities.Belief;
import wav.tcc.entities.ConfidenceClassStudent;
import wav.tcc.transactions.EvaluationDataTransactions;
import wav.tcc.view.Tcc;

/**
 *
 * @author willi
 */
public class StudentEffortBayesianNet
{
    public List<Object[]> loadEffortBayesNet( List<ConfidenceClassStudent> students ) 
    {
        Tcc.getInstance();
        
        try
        {
            List<Object[]> beliefs = new ArrayList();
            
            for( ConfidenceClassStudent student : students )
            {
                try
                {
                    Environ env = new Environ( null );

                    Net net = new Net( new Streamer( "C:\\Users\\willi\\Desktop\\TCC STUFF\\redes\\rede_estudante_esforcof.dne" ) );

                    Node classe_tp_prob_ef         = net.getNode( "Classe_tp_prob_ef" );
                    Node classe_tp_hip_ef          = net.getNode( "Classe_tp_hip_ef" );
                    Node classe_tp_cod_ef          = net.getNode( "Classe_tp_cod_ef" );
                    Node visualizou_pseudo         = net.getNode( "Classe_vis_pseudo" );
                    Node classe_nivel_detalhe_prob = net.getNode( "Classe_nivel_detalhe_prob" );
                    Node classe_nivel_detalhe_hip  = net.getNode( "Classe_nivel_detalhe_hip" );
                    Node classe_nivel_compreensao  = net.getNode( "Classe_nivel_compreensao" );
                    Node execucao_codigo           = net.getNode( "Classe_exec_cod" );
                    Node classe_ativ_nao           = net.getNode( "Classe_ativ_nao_realizadas" );
                    Node classe_semelhanca         = net.getNode( "Grau_semelhanca" );
                    
                    Node nivel_esforco           = net.getNode( "Nivel_esforco" );

                    net.compile();

                    classe_tp_prob_ef.finding().enterState( student.getClasseTPprobEF() );
                    classe_tp_hip_ef.finding().enterState( student.getClasseTPhipEF() );
                    classe_tp_cod_ef.finding().enterState(  student.getClasseTPcodEF() );
                    visualizou_pseudo.finding().enterState( student.getPseudo() );
                    classe_nivel_detalhe_prob.finding().enterState( student.getNivelDetalheProb() );
                    classe_nivel_detalhe_hip.finding().enterState( student.getNivelDetalheHip() );
                    classe_nivel_compreensao.finding().enterState( student.getNivelCompreensao() );
                    execucao_codigo.finding().enterState( student.getNumeroExecucoes() );
                    classe_ativ_nao.finding().enterState( student.getAtivNaoRealizadas() );
                    classe_semelhanca.finding().enterState( student.getGrauSemelhanca() );
                    
                    Object[] data = { new float[]{ nivel_esforco.getBelief( "ALTO" ), nivel_esforco.getBelief( "BAIXO" ) }, student.getNome() + " " + student.getSobrenome() };
                    
                    beliefs.add( data );
                    
                    student.setNivelEsforco( nivel_esforco.getBelief( "ALTO" ) * 100 );
                    
                    new EvaluationDataTransactions().updateEffortPercentageStudent(student );
                    
                    env.finalize();
                }
                
                catch( Exception e )
                {
                    e.printStackTrace();
                }
            }
            
            return beliefs;
        }
        
        catch( Exception e )
        {
            e.printStackTrace();
        }
        
        return new ArrayList();
    }
}
