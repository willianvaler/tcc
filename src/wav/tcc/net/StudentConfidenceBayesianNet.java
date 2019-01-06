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
public class StudentConfidenceBayesianNet
{
    public List<Object[]> loadConfidenceBayesNet( List<ConfidenceClassStudent> students ) 
    {
        Tcc.getInstance();
        
        try
        {
            List<Object[]> beliefs = new ArrayList();
            
//            Stream<String> stream = Files.lines( Paths.get( "C:\\Users\\willi\\Desktop\\TCC STUFF\\redes\\confianca_estudante.txt" ) );
            for( ConfidenceClassStudent student : students )
            {
                try
                {
                    Environ env = new Environ( null );

                    Net net = new Net( new Streamer( "C:\\Users\\willi\\Desktop\\TCC STUFF\\redes\\rede_estudante_confianca.dne" ) );

                    Node classe_tp_prob_cf        = net.getNode( "classe_tp_prob_cf" );
                    Node classe_tp_hip_cf         = net.getNode( "classe_tp_hip_cf" );
                    Node classe_prop_ph_cf        = net.getNode( "classe_prop_ph_cf" );
                    Node visualizou_dica1         = net.getNode( "classe_vis_dica1" );
                    Node visualizou_dica2         = net.getNode( "classe_vis_dica2" );
                    Node visualizou_pseudo        = net.getNode( "classe_vis_pseudo" );
                    Node classe_retomadas         = net.getNode( "classe_retomadas" );
                    Node classe_nivel_compreensao = net.getNode( "classe_nivel_compreensao" );
                    Node avaliacao_aluno          = net.getNode( "classe_avaliacao_aluno" );
                    Node nivel_confianca          = net.getNode( "nivel_confianca" );

                    net.compile();

                    classe_tp_prob_cf.finding().enterState( student.getClasseTPprobCF() );
                    classe_tp_hip_cf.finding().enterState( student.getClasseTPhipCF() );
                    classe_prop_ph_cf.finding().enterState( student.getClassePropPH_CF() );
                    visualizou_dica1.finding().enterState( student.getDica1() );
                    visualizou_dica2.finding().enterState( student.getDica2() );
                    visualizou_pseudo.finding().enterState( student.getPseudo() );
                    classe_retomadas.finding().enterState( student.getRetomadas() );
                    classe_nivel_compreensao.finding().enterState( student.getNivelCompreensao() );
                    avaliacao_aluno.finding().enterState( student.getAvaliacaoAluno() );
                    
                    Object[] data = { nivel_confianca.getBeliefs(), student.getNome() + " " + student.getSobrenome() };
                    
                    beliefs.add( data );
                    
                    student.setNivelConfianca( nivel_confianca.getBelief( "ALTO" ) * 100 );
                    
                    new EvaluationDataTransactions().updateConfidencePercentageStudent( student );
                    
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
