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
import wav.tcc.view.Tcc;

/**
 *
 * @author willi
 */
public class StudentConfidenceBayesianNet
{
    public List<float[]> loadConfidenceBayesNet() 
    {
        Tcc.getInstance();
        
        try
        {
            
            List<float[]> beliefs = new ArrayList();
            
            Stream<String> stream = Files.lines( Paths.get( "C:\\Users\\willi\\Desktop\\TCC STUFF\\redes\\confianca_estudante.txt" ) );
            
            stream.forEach( line -> 
            {
                try
                {
                    String[] columns = line.split( " " );
                    
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
            /*
                    classe_tp_prob_cf,
                    classe_tp_hip_cf,
                    classe_prop_ph_cf,
                    classe_vis_dica1,
                    classe_vis_dica2,
                    classe_vis_pseudo,
                    classe_retomadas,
                    classe_nivel_compreensao,
                    classe_avaliacao_aluno,
                    nivel_confianca

            */
                    classe_tp_prob_cf.finding().enterState( columns[0] );
                    classe_tp_hip_cf.finding().enterState( columns[1] );
                    classe_prop_ph_cf.finding().enterState( columns[2] );
                    visualizou_dica1.finding().enterState( columns[3] );
                    visualizou_dica2.finding().enterState( columns[4] );
                    visualizou_pseudo.finding().enterState( columns[5] );
                    classe_retomadas.finding().enterState( columns[6] );
                    classe_nivel_compreensao.finding().enterState( columns[7] );
                    avaliacao_aluno.finding().enterState( columns[8] );
                    
                    
                    beliefs.add( nivel_confianca.getBeliefs() );
                    
                    env.finalize();
                }
                
                catch( Exception e )
                {
                    e.printStackTrace();
                }
            });
            
            
            return beliefs;
        }
        
        catch( Exception e )
        {
            e.printStackTrace();
//            env.finalize();
        }
        
        return new ArrayList();
    }
}
