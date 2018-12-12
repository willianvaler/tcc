/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wav.tcc.view;

/**
 *
 * @author willi
 */
public class Tcc 
{
    private static Tcc tcc;
    
    private Tcc()
    {
    
    }
    
    public static Tcc getInstance()
    {
        if( tcc == null )
        {
            tcc = new Tcc();
        }
        
        return tcc;
    }
    
    static
    {
        System.loadLibrary( "NeticaJ" );
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
    }
    
}
