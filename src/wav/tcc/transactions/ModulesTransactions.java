/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wav.tcc.transactions;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import wav.tcc.database.Database;
import wav.tcc.entities.Module;

/**
 *
 * @author willi
 */
public class ModulesTransactions
{
    /**
     * 
     * @return 
     */
    public List<Module> getModules()
    {
        List<Module> modules = new ArrayList();
        
        try
        {
            Statement st = Database.getInstance().getConnection().createStatement();

            ResultSet result = st.executeQuery( "select id, descricao from exercicio_modulo" );
            
            while( result.next() )
            {
                Module m = new Module();
                
                m.setId( result.getInt( "id" ) );
                m.setDescricao( result.getString( "descricao" ) );
                
                modules.add( m );
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        
        return modules;
    }
}
