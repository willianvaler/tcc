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
import wav.tcc.entities.Exercicio;
import wav.tcc.entities.Turma;

/**
 *
 * @author willi
 */
public class ExerciseTransactions {
    
    public List<Exercicio> getExercises()
    {
        List<Exercicio> exercicios = new ArrayList();
        ClassTransactions ct = new ClassTransactions();
        
        try
        {
            Statement st = Database.getInstance().getConnection().createStatement();

            ResultSet result = st.executeQuery( "select e.id, ea.titulo, t.id from exercicio_aluno ea, exercicio e, turma t where e.id = ea.exercicio_id and e.turma_id = t.id group by ea.exercicio_id" );
            
            while( result.next() )
            {
                Exercicio e = new Exercicio();

                e.setId( result.getInt( 1 ) );
                e.setName( result.getString( 2 ) );
                e.setTurma( ct.getTurma( result.getInt( 3 ) ) );

                exercicios.add( e );
            }
        }
        
        catch( Exception e )
        {
            e.printStackTrace();
        }
        
        return exercicios;
    }
    
}
