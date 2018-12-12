/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wav.tcc.loaders;

import wav.tcc.net.ClassConfidenceBayesianNet;
import norsys.netica.*;

/**
 *
 * @author willi
 */
public class NeticaDataLoader 
{
    public static void loadModel1() throws NeticaException
    {
//        new ClassConfidenceBayesianNet().loadConfidenceBayesNet();
    }
    
    public static void loadExample()
    {
        try
        {
            Environ env = new Environ( null );
            
            Net net = new Net( new Streamer( "src/tcc/Teste_aprovado.neta" ) );
            
            Node cursinho = net.getNode( "Cursinho" );
            Node ibl = net.getNode( "IBL" );
            Node aprovado = net.getNode( "Aprovado" );
            
            net.compile();
            
            
            System.out.println( "approval probability belief: " + aprovado.getBelief( "Sim" ) );
        }
        
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}
