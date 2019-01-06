/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wav.tcc.net;

import java.util.ArrayList;
import java.util.List;
import norsys.netica.Environ;
import norsys.netica.Net;
import norsys.netica.Node;
import norsys.netica.Streamer;
import wav.tcc.entities.ConfidenceClassStudent;
import wav.tcc.transactions.EvaluationDataTransactions;
import wav.tcc.view.Tcc;

/**
 *
 * @author willi
 */
public class ClassEffortBayesianNet
{

    /**
     *
     * @param students
     * @return
     */
    public List<Object[]> loadEffortBayesNet(  List<ConfidenceClassStudent> students ) 
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

                    Net net = new Net( new Streamer( "C:\\Users\\willi\\Desktop\\TCC STUFF\\redes\\reco_esforco_ativ_nomesbd.neta" ) );

                    Node classe_tp_prob_ef         = net.getNode( "Classe_tp_prob_ef" );
                    Node classe_tp_hip_ef          = net.getNode( "Classe_tp_hip_ef" );
                    Node classe_cod_ef             = net.getNode( "Classe_cod_ef" );
                    Node classe_prop_ph            = net.getNode( "Classe_prop_ph" );
                    Node visualizou_pseudo         = net.getNode( "Visualizou_pseudo" );
                    Node execucao_codigo           = net.getNode( "Execucao_codigo" );
                    Node classe_nivel_detalhe_prob = net.getNode( "Classe_nivel_detalhe_prob" );
                    Node classe_nivel_compreensao  = net.getNode( "Classe_nivel_compreensao" );
                    Node classe_nivel_detalhe_hip  = net.getNode( "Classe_nivel_detalhe_hip" );
                    
                    Node nivel_esforco             = net.getNode( "Nivel_esforco" );

                    net.compile();

                    classe_tp_prob_ef.finding().enterState( student.getClasseTPprobEF() );
                    classe_tp_hip_ef.finding().enterState( student.getClasseTPhipEF() );
                    classe_prop_ph.finding().enterState( student.getClassePropPH() );
                    classe_cod_ef.finding().enterState(  student.getClasseTPcodEF() );
                    visualizou_pseudo.finding().enterState( student.getPseudo() );
                    classe_nivel_detalhe_prob.finding().enterState( student.getNivelDetalheProb() );
                    classe_nivel_detalhe_hip.finding().enterState( student.getNivelDetalheHip() );
                    classe_nivel_compreensao.finding().enterState( student.getNivelCompreensao() );
                    execucao_codigo.finding().enterState( student.getNumeroExecucoes() );
                    
                    Object[] data = { nivel_esforco.getBeliefs(), student.getNome() + " " + student.getSobrenome() };
                    
                    beliefs.add( data );
                    
                    student.setNivelEsforco( nivel_esforco.getBelief( "ALTO" ) * 100 );
                    
                    new EvaluationDataTransactions().updateEffortPercentage( student );
                    
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
