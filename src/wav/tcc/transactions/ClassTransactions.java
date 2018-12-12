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
import wav.tcc.entities.Turma;

/**
 *
 * @author willi
 */
public class ClassTransactions
{
    public List<Turma> getTurmas()
    {
        List<Turma> turmas = new ArrayList();
        
        try
        {
            Statement st = Database.getInstance().getConnection().createStatement();

            
            /*
            select ec.* from turma t, turma_has_usuario th, usuario u, exercicio_aluno ea, exercicio_correcao ec where t.id = 5 and t.id = th.turma_id 
and u.id = th.usuario_id and ea.usuario_id_aluno = u.id and ec.exercicio_gerado_id = ea.id ;*/
            ResultSet result = st.executeQuery( "select id, semestre from turma" );
            
            while( result.next() )
            {
                Turma t = new Turma();
                
                t.setId( result.getInt( "id" ) );
                t.setName( result.getString( "semestre" ) );
                
                turmas.add( t );
            }
        }
        
        catch( Exception e )
        {
            e.printStackTrace();
        }
        
        return turmas;
    }
}
