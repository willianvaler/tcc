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
import wav.tcc.entities.Belief;
import wav.tcc.entities.ConfidenceClassStudent;
import wav.tcc.view.Tcc;

/**
 *
 * @author willi
 */
public class ClassConfidenceBayesianNet 
{
    
    
    /**
     * 
     * @param students
     * @return 
     */
    public List<float[]> loadConfidenceBayesNet( List<ConfidenceClassStudent> students ) 
    {
        Tcc.getInstance();
        
        try
        {
            List<float[]> beliefs = new ArrayList();
            
            String tpProb = "";
            String tpHip = "";
            String propPh = "";
            String visDi1 = "";
            String visDi2 = "";
            String visPseudo = "";
            String nivelCompreensao = "";
            String retomadas = "";
            String avaliacaoAluno = "";
            
            
            for( ConfidenceClassStudent student : students )
            {
                try
                {
                    Environ env = new Environ( null );

                    Net net = new Net( new Streamer( "C:\\Users\\willi\\Desktop\\TCC STUFF\\redes\\reco_confianca_ativ_nomesbd.neta" ) );

                    Node classe_tp_prob_cf       = net.getNode( "Classe_tp_prob_cf" );
                    Node classe_tp_hip_cf        = net.getNode( "Classe_tp_hip_cf" );
                    Node classe_prop_ph          = net.getNode( "Classe_prop_ph" );
                    Node visualizou_dica1        = net.getNode( "Visualizou_dica1" );
                    Node visualizou_dica2        = net.getNode( "Visualizou_dica2" );
                    Node visualizou_pseudo       = net.getNode( "Visualizou_pseudo" );
                    Node classe_nivel_comprensao = net.getNode( "Classe_nivel_comprensao" );
                    Node classe_retomadas        = net.getNode( "Classe_retomadas" );
                    Node avaliacao_aluno         = net.getNode( "Classe_avaliacao_aluno" );
                    Node nivel_confianca         = net.getNode( "Nivel_confianca" );

                    net.compile();

                    classe_tp_prob_cf.finding().enterState( student.getClasseTPprobCF() );
                    classe_tp_hip_cf.finding().enterState( student.getClasseTPhipCF() );
                    classe_prop_ph.finding().enterState( student.getClassePropPH() );
                    visualizou_dica1.finding().enterState( student.getDica1() );
                    visualizou_dica2.finding().enterState( student.getDica2() );
                    visualizou_pseudo.finding().enterState( student.getPseudo() );
                    classe_retomadas.finding().enterState( student.getRetomadas() );
                    classe_nivel_comprensao.finding().enterState( student.getNivelCompreensao() );
                    avaliacao_aluno.finding().enterState( student.getAvaliacaoAluno() );
                    
                    beliefs.add( nivel_confianca.getBeliefs() );
                    
                    tpProb = "TP_PROP: ACIMA: " + classe_tp_prob_cf.getBelief( "ACIMA" ) + "; NORMAL:" + classe_tp_prob_cf.getBelief( "NORMAL" );
                    tpHip  = "TP_HIP: ACIMA: " + classe_tp_hip_cf.getBelief(  "ACIMA" ) + "; NORMAL:" + classe_tp_hip_cf.getBelief(  "NORMAL" );
                    propPh = "PROP_PH: PROPORCIONAL: " + classe_prop_ph.getBelief( "PROPORCIONAL" ) + "; DESPROPORCIONAL: " + classe_prop_ph.getBelief( "DESPROPORCIONAL" );
                    visDi1 = "Visualizou dica 1: SIM:"  + visualizou_dica1.getBelief( "S" ) + "; NÃO: " + visualizou_dica1.getBelief( "N" );
                    visDi2 = "Visualizou dica 2: SIM:"  + visualizou_dica2.getBelief( "S" ) + "; NÃO: " + visualizou_dica2.getBelief( "N" );
                    visPseudo = "Visualizou pseudo: SIM:"  + visualizou_pseudo.getBelief( "S" ) + "; NÃO: " + visualizou_pseudo.getBelief( "N" );
                    nivelCompreensao = "Nível compreenção: BAIXO: " + classe_nivel_comprensao.getBelief( "BAIXO" ) + "; MEDIO:" 
                                                    + classe_nivel_comprensao.getBelief( "MEDIO" ) + " ; ALTO: " + classe_nivel_comprensao.getBelief( "ALTO" );
                    retomadas = "Retomadas: NENHUMA: " + classe_retomadas.getBelief( "NENHUMA" ) + "; POUCAS: " + classe_retomadas.getBelief( "POUCAS" ) + "; MUITAS:" + classe_retomadas.getBelief( "MUITAS" );
                    avaliacaoAluno = "Avaliação aluno: DIFÍCIL: " + avaliacao_aluno.getBelief( "DIFICIL" ) + "; MÉDIO: " + avaliacao_aluno.getBelief( "MEDIO" ) + "; FÁCIL: " + avaliacao_aluno.getBelief( "FACIL" );
                    
                    env.finalize();
                }
                
                catch( Exception e )
                {
                    e.printStackTrace();
                }
            }
            System.out.println( avaliacaoAluno );
            System.out.println( tpProb );
            System.out.println( tpHip );
            System.out.println( propPh );
            System.out.println( nivelCompreensao );
            System.out.println( retomadas );
            
            return beliefs;
        }
        
        catch( Exception e )
        {
            e.printStackTrace();
        }
        
        return new ArrayList();
    }
    
}
