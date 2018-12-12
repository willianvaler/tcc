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
import wav.tcc.view.Tcc;

/**
 *
 * @author willi
 */
public class StudentEffortBayesianNet
{
    public List<float[]> loadEffortBayesNet() 
    {
        Tcc.getInstance();
        
        try
        {
            List<float[]> beliefs = new ArrayList();
            
            Stream<String> stream = Files.lines( Paths.get( "C:\\Users\\willi\\Desktop\\TCC STUFF\\redes\\esforco_estudante.txt" ) );
            
            stream.forEach( line -> 
            {
                try
                {
                    String[] columns = line.split( " " );
                    
                    Environ env = new Environ( null );

                    Net net = new Net( new Streamer( "C:\\Users\\willi\\Desktop\\TCC STUFF\\redes\\rede_estudante_esforcof.dne" ) );

                    Node classe_tp_prob_ef         = net.getNode( "Classe_tp_prob_ef" );
                    Node classe_tp_hip_ef          = net.getNode( "Classe_tp_hip_ef" );
                    Node classe_cod_ef             = net.getNode( "Classe_tp_cod_ef" );
                    Node visualizou_pseudo         = net.getNode( "Classe_vis_pseudo" );
                    Node classe_nivel_detalhe_prob = net.getNode( "Classe_nivel_detalhe_prob" );
                    Node classe_nivel_detalhe_hip  = net.getNode( "Classe_nivel_detalhe_hip" );
                    Node classe_nivel_compreensao  = net.getNode( "Classe_nivel_compreensao" );
                    Node execucao_codigo           = net.getNode( "Classe_exec_cod" );
                    Node classe_ativ_nao           = net.getNode( "Classe_ativ_nao_realizadas" );
                    Node classe_semelhanca         = net.getNode( "Grau_semelhanca" );
                    
                    Node nivel_confianca           = net.getNode( "Nivel_esforco" );

                    net.compile();
            /*
                    Classe_tp_prob_ef,
                    Classe_tp_hip_ef,
                    Classe_tp_cod_ef,
                    Classe_vis_pseudo,
                    Classe_nivel_detalhe_prob,
                    Classe_nivel_detalhe_hip,
                    Classe_nivel_compreensao,
                    Classe_exec_cod,
                    Classe_ativ_nao_realizadas,
                    Grau_semelhanca,
                    Nivel_esforco

            */
                    classe_tp_prob_ef.finding().enterState( columns[0] );
                    classe_tp_hip_ef.finding().enterState( columns[1] );
                    classe_cod_ef.finding().enterState(  columns[2] );
                    visualizou_pseudo.finding().enterState( columns[3] );
                    classe_nivel_detalhe_prob.finding().enterState( columns[4] );
                    classe_nivel_detalhe_hip.finding().enterState( columns[5] );
                    classe_nivel_compreensao.finding().enterState( columns[6] );
                    execucao_codigo.finding().enterState( columns[7] );
                    classe_ativ_nao.finding().enterState( columns[8] );
                    classe_semelhanca.finding().enterState( columns[9] );
                    
                    
                    beliefs.add( nivel_confianca.getBeliefs());
                    
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
        }
        
        return new ArrayList();
    }
}
