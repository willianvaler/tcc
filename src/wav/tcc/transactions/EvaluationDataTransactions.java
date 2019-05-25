/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wav.tcc.transactions;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import wav.tcc.database.Database;
import wav.tcc.entities.ConfidenceClassStudent;
import wav.tcc.util.NetsUtil;

/**
 *
 * @author willi
 */
public class EvaluationDataTransactions 
{
    public List<ConfidenceClassStudent> loadReadyClassData( int exerciseId )
    {
        List<ConfidenceClassStudent> students = new ArrayList();
        
        try 
        {
            Statement st = Database.getInstance().getConnection().createStatement();
            
            ResultSet result = st.executeQuery( " select dm.id," +
                                                " ea.exercicio_id, ea.titulo, u.nome, u.sobrenome, dm.classe_tp_prob_ef, dm.classe_tp_hip_ef, dm.classe_tp_cod_ef, " +
                                                "       dm.classe_prop_ph, dm.visualizou_pseudo, dm.execucao_codigo, dm.classe_nivel_detalhe_prob, " +
                                                "       dm.classe_nivel_compreensao, classe_nivel_detalhe_hip, dm.classe_tp_prob_cf, dm.classe_tp_hip_cf, " +
                                                "       dm.classe_prop_ph, dm.visualizou_pseudo, dm.visualizou_dica1, dm.visualizou_dica2, " +
                                                "	   dm.classe_nivel_compreensao, dm.classe_retomadas, dm.classe_avaliacao_aluno " +
                                                " from exercicio_aluno ea, dados_modafet_exercicio dm, usuario u " +
                                                " where ea.id=dm.exercicio_aluno_id and ea.usuario_id_aluno=u.id and ea.exercicio_id = " + exerciseId +
                    
                                                " order by dm.classe_tp_prob_ef, dm.classe_tp_hip_ef, dm.classe_tp_cod_ef, " +
                                                "       dm.classe_prop_ph, dm.visualizou_pseudo, dm.execucao_codigo, dm.classe_nivel_detalhe_prob, " +
                                                "       dm.classe_nivel_compreensao, classe_nivel_detalhe_hip " );
            
            while( result.next() )
            {
                ConfidenceClassStudent student = new ConfidenceClassStudent();
                
                /*GENERAL DATA*/
                student.setDataId( result.getInt( "id" ) );
                student.setNome( result.getString( "nome" ) );
                student.setSobrenome( result.getString( "sobrenome" ) );
                student.setNivelCompreensao( result.getString( "classe_nivel_compreensao" ) );
                student.setClassePropPH( result.getString( "classe_prop_ph" ) );
                student.setDica1( result.getString( "visualizou_dica1" ) );
                student.setDica2( result.getString( "visualizou_dica2" ) );
                student.setPseudo( result.getString( "visualizou_pseudo" ) );

                /*CONFIANÇA*/
                student.setClasseTPprobCF( result.getString( "classe_tp_prob_cf" ) );
                student.setClasseTPhipCF( result.getString( "classe_tp_hip_cf" ) );
                student.setRetomadas( result.getString( "classe_retomadas" ) );
                student.setAvaliacaoAluno( result.getString( "classe_avaliacao_aluno" ) );

                /*ESFORÇO*/
                student.setClasseTPprobEF( result.getString( "classe_tp_prob_ef" ) );
                student.setClasseTPhipEF(  result.getString( "classe_tp_hip_ef" ) );
                student.setClasseTPcodEF( result.getString( "classe_tp_cod_ef" ) );
                student.setNivelDetalheHip( result.getString( "classe_nivel_detalhe_hip" ) );
                student.setNivelDetalheProb( result.getString( "classe_nivel_detalhe_prob" ) );
                student.setNumeroExecucoes( result.getString( "execucao_codigo" ) );

                students.add( student );
                
            }
            
        }
        
        catch( Exception e )
        {
            e.printStackTrace();
        }
        
        return students;
    }
    
    /**
     * 
     * @param classId
     * @return 
     */
    public List<ConfidenceClassStudent> loadReadyStudentData( int classId )
    {
        List<ConfidenceClassStudent> students = new ArrayList();
        
        try 
        {
            Statement st = Database.getInstance().getConnection().createStatement();
            
            ResultSet result = st.executeQuery( " select dm.id," 
                                                    + " u.nome, u.sobrenome, "
                                                    + " dm.classe_tp_prob_ef, "
                                                    + " dm.classe_tp_hip_ef, "
                                                    + " dm.classe_tp_cod_ef, "
                                                    + " dm.classe_tp_ph_ef, "
                                                    + " dm.classe_nivel_detalhe_prob, " 
                                                    + " dm.classe_nivel_detalhe_hip, "
                                                    + " dm.classe_nivel_compreensao, "
                                                    + " dm.classe_prop_ph_cf, "
                                                    + " dm.classe_tp_prob_cf, "
                                                    + " dm.classe_tp_hip_cf, " 
                                                    + " dm.classe_vis_pseudo, "
                                                    + " dm.classe_vis_dica1, "
                                                    + " dm.classe_vis_dica2, " 
                                                    + " dm.classe_exec_codigo, "
                                                    + " dm.classe_retomadas, "
                                                    + " dm.classe_avaliacao_aluno,"
                                                    + " dm.classe_ativ_nao_realizadas, "
                                                    + " dm.grau_semelhanca " 
                                                + " from turma t, dados_modafet_estudante dm, usuario u, turma_has_usuario tm " 
                                                + " where t.id = dm.turma_id and tm.usuario_id = u.id and dm.usuario_id = u.id and tm.usuario_id = dm.usuario_id"
                                                + " and t.id = tm.turma_id and dm.turma_id = " + classId );
            while( result.next() )
            {
                ConfidenceClassStudent student = new ConfidenceClassStudent();
                
                /*GENERAL DATA*/
                student.setDataId( result.getInt( "id" ) );
                student.setNome( result.getString( "nome" ) );
                student.setSobrenome( result.getString( "sobrenome" ) );
                student.setNivelCompreensao( result.getString( "classe_nivel_compreensao" ) );
                student.setDica1( result.getString( "classe_vis_dica1" ) );
                student.setDica2( result.getString( "classe_vis_dica2" ) );
                student.setPseudo( result.getString( "classe_vis_pseudo" ) );

                /*CONFIANÇA*/
                student.setClasseTPprobCF( result.getString( "classe_tp_prob_cf" ) );
                student.setClasseTPhipCF( result.getString( "classe_tp_hip_cf" ) );
                student.setRetomadas( result.getString( "classe_retomadas" ) );
                student.setAvaliacaoAluno( result.getString( "classe_avaliacao_aluno" ) );
                student.setClassePropPH_CF( result.getString( "classe_prop_ph_cf" ) );

                /*ESFORÇO*/
                student.setClasseTPprobEF( result.getString( "classe_tp_prob_ef" ) );
                student.setClasseTPhipEF(  result.getString( "classe_tp_hip_ef" ) );
                student.setClasseTPcodEF( result.getString( "classe_tp_cod_ef" ) );
                student.setNivelDetalheHip( result.getString( "classe_nivel_detalhe_hip" ) );
                student.setNivelDetalheProb( result.getString( "classe_nivel_detalhe_prob" ) );
                student.setNumeroExecucoes( result.getString( "classe_exec_codigo" ) );
                student.setClasseTPphEF( result.getString( "classe_tp_ph_ef" ) );
                student.setAtivNaoRealizadas( result.getString( "classe_ativ_nao_realizadas" ) );
                student.setGrauSemelhanca( result.getString( "grau_semelhanca" ) );

                students.add( student );
            }
            
        }
        
        catch( Exception e )
        {
            e.printStackTrace();
        }
        
        return students;
    }
    
    /**
     * 
     * @param student 
     */
    public void updateConfidencePercentage( ConfidenceClassStudent student )
    {
        try 
        {
            Statement st = Database.getInstance().getConnection().createStatement();
            
            st.executeUpdate( "update dados_modafet_exercicio dm set dm.nivel_confianca =  " + student.getNivelConfianca() + "  where  dm.id = " + student.getDataId() );
        }
        
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    /**
     * 
     * @param student 
     */
    public void updateEffortPercentage( ConfidenceClassStudent student )
    {
        try 
        {
            Statement st = Database.getInstance().getConnection().createStatement();
            
            st.executeUpdate( "update dados_modafet_exercicio dm set dm.nivel_esforco =  " + student.getNivelEsforco() + "  where  dm.id = " + student.getDataId() );
        }
        
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void updateConfidencePercentageStudent( ConfidenceClassStudent student )
    {
        try 
        {
            Statement st = Database.getInstance().getConnection().createStatement();
            
            st.executeUpdate( "update dados_modafet_estudante dm set dm.nivel_confianca =  " + student.getNivelConfianca() + "  where  dm.id = " + student.getDataId() );
        }
        
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    /**
     * 
     * @param student 
     */
    public void updateEffortPercentageStudent( ConfidenceClassStudent student )
    {
        try 
        {
            Statement st = Database.getInstance().getConnection().createStatement();
            
            st.executeUpdate( "update dados_modafet_estudante dm set dm.nivel_esforco =  " + student.getNivelEsforco() + "  where  dm.id = " + student.getDataId() );
        }
        
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    /**
     * carrega os dados brutos para serem inseridos na rede dos exercicios
     * @param classId
     * @return 
     */
    public List<ConfidenceClassStudent> loadClassData( int classId )
    {
        List<ConfidenceClassStudent> students = new ArrayList();
        
        List<Integer> detalhePontoChave = new ArrayList();
        List<Integer> detalheHipotese = new ArrayList();
        List<Integer> detalheCodigo = new ArrayList();
        List<Integer> nivelCompreensao = new ArrayList();
        List<Integer> minutosPontoChave = new ArrayList();
        List<Integer> minutosHipotese = new ArrayList();
        List<Integer> minutosCodigo = new ArrayList();
        List<Integer> numeroCompilacoes = new ArrayList();
        List<Integer> compilacoesErro = new ArrayList();
        List<Integer> numeroExecucoes = new ArrayList();
        List<Integer> retomadas = new ArrayList();
        List<String> nome = new ArrayList();
        List<String> sobrenome = new ArrayList();
        List<Integer> dica1 = new ArrayList();
        List<Integer> dica2 = new ArrayList();
        List<Integer> pseudo = new ArrayList();
        List<Integer> avaliacaoAluno = new ArrayList();
        
        try 
        {
            Statement st = Database.getInstance().getConnection().createStatement();
            
            ResultSet result = st.executeQuery( "select ec.nivel_detalhe_pontos_chave, ec.nivel_detalhe_hipotese, ec.nivel_detalhe_codigo_fonte, ec.nivel_compreensao, " +
                                        " ec.minutos_pontos_chave, ec.minutos_hipotese, ec.minutos_codigo, ec.numero_compilacoes, ec.compilacoes_com_erros, " +
                                        " ec.numero_execucoes, ec.retomadas, u.nome, u.sobrenome," +
                                            " ( select count(*) from log_visualizacao_dica log, exercicio_dica dica "
                                                    + " where log.exercicio_aluno_id = u.id and log.exercicio_aluno_id = usuario_id_aluno "
                                                    + " and dica.sequencia = 1 and dica.id = log.exercicio_dica_id) as dica1," +   
                                            " ( select count(*) from log_visualizacao_dica log, exercicio_dica dica "
                                                    + " where log.exercicio_aluno_id = u.id and log.exercicio_aluno_id = usuario_id_aluno "
                                                    + " and dica.sequencia = 2 and dica.id = log.exercicio_dica_id) as dica2," +
                                            " ( select count(*) from log_visualizacao_pseudocodigo ps where ps.exercicio_aluno_id = ea.id ) as pseudo, ea.exercicio_avaliacao_aluno_id" +
                                        " from exercicio_correcao ec, exercicio_aluno ea, usuario u " +
                                        " where ec.exercicio_gerado_id = ea.id and ea.exercicio_id = " + classId + " and u.id=usuario_id_aluno ;" );
            while( result.next() )
            {
                detalhePontoChave.add( result.getInt( 1 ) );
                detalheHipotese.add( result.getInt( 2 ) );
                detalheCodigo.add( result.getInt( 3 ) );
                nivelCompreensao.add( result.getInt( 4 ) );
                minutosPontoChave.add( result.getInt( 5 ) );
                minutosHipotese.add( result.getInt( 6 ) );
                minutosCodigo.add( result.getInt( 7 ) );
                numeroCompilacoes.add( result.getInt( 8 ) );
                compilacoesErro.add( result.getInt(9 ) );
                numeroExecucoes.add( result.getInt( 10 ) );
                retomadas.add( result.getInt( 11 ) );
                
                nome.add( result.getString( 12 ) );
                sobrenome.add( result.getString( 13 ) );
                
                dica1.add( result.getInt( 14 ) );
                dica2.add( result.getInt( 15 ) );
                pseudo.add( result.getInt( 16 ) );
                
                avaliacaoAluno.add( result.getInt( 17 ) );
            }
            
            if (!nome.isEmpty()) 
            {
                double probMediaDobrada = NetsUtil.calculateDoubleAverage( minutosPontoChave );
                double hipMediaDobrada = NetsUtil.calculateDoubleAverage( minutosHipotese );

                int [] qDetalhePontoChave = NetsUtil.getQuartiles( detalhePontoChave );
                int [] qDetalheHipotese = NetsUtil.getQuartiles( detalheHipotese );
                int [] qDetalheCodigo = NetsUtil.getQuartiles( detalheCodigo );

                for( int i = 0; i < nome.size(); i++ )
                {
                    ConfidenceClassStudent student = new ConfidenceClassStudent();

                    double proportion = NetsUtil.calculateProportion( minutosPontoChave.get(i), minutosHipotese.get(i), minutosCodigo.get(i) );

                    int retomada = retomadas.get( i );

                    /*GENERAL DATA*/
                    student.setNome( nome.get( i ) );
                    student.setSobrenome( sobrenome.get( i ) );
                    student.setNivelCompreensao( NetsUtil.getNivel( nivelCompreensao.get( i ) ) );
                    student.setClassePropPH( ( proportion <= 0.20 || proportion >= 0.80 ? "DESPROPORCIONAL" : "PROPORCIONAL" ) );
                    student.setDica1( ( dica1.get( i ) > 0 ? "S" : "N" ) );
                    student.setDica2( ( dica2.get( i ) > 0 ? "S" : "N" ) );
                    student.setPseudo( ( pseudo.get( i ) > 0 ? "S" : "N" ) );

                    /*CONFIANÇA*/
                    student.setClasseTPprobCF( ( minutosPontoChave.get( i ) >= probMediaDobrada ? "ACIMA" : "NORMAL" ) );
                    student.setClasseTPhipCF( ( minutosHipotese.get( i ) >= hipMediaDobrada ? "ACIMA" : "NORMAL" ) );
                    student.setRetomadas( ( retomada == 0 ? "NENHUMA" : ( retomada <= 2 ? "POUCAS" : "MUITAS" ) ) );
                    student.setAvaliacaoAluno( NetsUtil.getAvaliacaoEstudante( avaliacaoAluno.get( i ) ) );

                    /*ESFORÇO*/
                    student.setClasseTPprobEF( NetsUtil.getQuartileEvaluation( qDetalhePontoChave[0], qDetalhePontoChave[1], minutosPontoChave.get( i ) ) );
                    student.setClasseTPhipEF(  NetsUtil.getQuartileEvaluation( qDetalheHipotese[0],   qDetalheHipotese[1],   minutosHipotese.get( i ) ) );
                    student.setClasseTPcodEF(  NetsUtil.getQuartileEvaluation( qDetalheCodigo[0],     qDetalheCodigo[1],     minutosCodigo.get( i ) ) );
                    student.setNivelDetalheHip( NetsUtil.getNivel( detalheHipotese.get( i ) ) );
                    student.setNivelDetalheProb( NetsUtil.getNivel( detalhePontoChave.get( i ) ) );
                    student.setNumeroExecucoes( ( numeroExecucoes.get( i ) >= 1 ? "S" : "N" ) );

                    students.add( student );
                }
            }
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return students;
    }
    
    /**
     * treina a rede com os dados carregados do metodo loadStudentData
     * @param classId
     * @return 
     */
    public List<ConfidenceClassStudent> loadStudentData( int classId )
    {
        List<ConfidenceClassStudent> students = new ArrayList();
        
        HashMap<Integer, List<ConfidenceClassStudent>> map = getStudentData( classId );
        
        int countExercises = countExercisesfromClass( classId );
        
        int maxPoints = countExercises * 3;
        
        Set<Integer> set = map.keySet();
        
        for( Integer i : set )
        {
            ArrayList<ConfidenceClassStudent> userData = (ArrayList) map.get( i );
            
            int percentNotDone = ( 100 * ( countExercises - userData.size() ) ) / countExercises;
            
            int countDica1 = 0, countDica2 = 0, countPseudo = 0, classe_tp_prob_ef = 0, classe_tp_hip_ef = 0, clase_tp_ph_ef = 0, 
                    classe_tp_cod_ef = 0, classe_nivel_detalhe_prob = 0, classe_nivel_detalhe_hip = 0, classe_nivel_compreensao = 0,
                    classe_avaliacao_aluno = 0, classe_tp_prob_cf = 0, classe_tp_hip_cf = 0, classe_prop_ph = 0;
            
            ConfidenceClassStudent tempStudent = null;
            
            /*
                Para resumir as variáveis de classes para o problema e hipótese relacionadas a confiança (classe_tp_prob_cf, classe_tp_hip_cf) 
            usa-se uma regra que conta o número de vezes que foi acima, se nenhuma vez ocorreu, a classe é NENHUMA, menos de 50%, 
            POUCAS e mais de 50%, MUITAS. O mesmo se aplica ao campo da proporção (classe_prop_ph), para o qual avalia-se de foi
            mais de 50% das vezes desproporcional, se foi menos de 50% ou nenhuma vez desproporcional.
            */
            for( ConfidenceClassStudent student : userData )
            {
                if ( student.getDica1 ().equals( "S" ) ) { countDica1++; }
                if ( student.getDica2 ().equals( "S" ) ) { countDica2++; }
                if ( student.getPseudo().equals( "S" ) ) { countPseudo++; }
                
                if ( student.getClasseTPprobCF().equals( "ACIMA" ) )           { classe_tp_prob_cf++; }
                if ( student.getClasseTPhipCF ().equals( "ACIMA" ) )           { classe_tp_hip_cf++; }
                if ( student.getClassePropPH  ().equals( "DESPROPORCIONAL" ) ) { classe_prop_ph++; }
                
                //############## effort ##############
                classe_tp_prob_ef          += NetsUtil.getWeight( student.getClasseTPprobEF()   );
                classe_tp_hip_ef           += NetsUtil.getWeight( student.getClasseTPhipEF()    );
                clase_tp_ph_ef             += NetsUtil.getWeight( student.getClasseTPphEF()     );
                classe_tp_cod_ef           += NetsUtil.getWeight( student.getClasseTPcodEF()    );
                
                //############## general data ##############
                classe_nivel_detalhe_prob  += NetsUtil.getWeight( student.getNivelDetalheProb() );
                classe_nivel_detalhe_hip   += NetsUtil.getWeight( student.getNivelDetalheHip()  );
                classe_nivel_compreensao   += NetsUtil.getWeight( student.getNivelCompreensao() );
                classe_avaliacao_aluno     += NetsUtil.getWeightStudentEvaluation( student.getAvaliacaoAluno() );
                
                tempStudent = student;
            }
            
            ConfidenceClassStudent student = new ConfidenceClassStudent();
            
            student.setNome( tempStudent.getNome() );
            student.setSobrenome( tempStudent.getSobrenome() );
            
            student.setPseudo( NetsUtil.getPseudoAvaliacao( countPseudo, countExercises ) );
            student.setDica1 ( NetsUtil.getDicaAvaliacao( countDica1, countExercises ) );
            student.setDica2 ( NetsUtil.getDicaAvaliacao( countDica2, countExercises ) );
            
            student.setClasseTPprobCF( NetsUtil.getDicaAvaliacao( classe_tp_prob_cf, countExercises ) );
            student.setClasseTPhipCF( NetsUtil.getDicaAvaliacao( classe_tp_hip_cf, countExercises ) );
            student.setClassePropPH( NetsUtil.getDicaAvaliacao( classe_prop_ph, countExercises ) );
            
            student.setClasseTPprobEF( NetsUtil.getPointsCategory( classe_tp_prob_ef, maxPoints ) );
            student.setClasseTPhipEF( NetsUtil.getPointsCategory( classe_tp_hip_ef, maxPoints ) );
            student.setClasseTPphEF( NetsUtil.getPointsCategory( clase_tp_ph_ef, maxPoints ) );
            student.setClasseTPcodEF( NetsUtil.getPointsCategory( classe_tp_cod_ef, maxPoints ) );
            
            student.setNivelDetalheProb( NetsUtil.getPointsCategory( classe_nivel_detalhe_prob, maxPoints ) );
            student.setNivelDetalheHip( NetsUtil.getPointsCategory( classe_nivel_detalhe_hip, maxPoints ) );
            student.setNivelCompreensao( NetsUtil.getPointsCategory( classe_nivel_compreensao, maxPoints ) );
            student.setAvaliacaoAluno( NetsUtil.getEvaluationCategory( classe_avaliacao_aluno, maxPoints ) );
        }
        
        return students;
    }
    
    /**
     * Carrega os dados brutos para serem inseridos na rede do estudante
     * Esses dados são dependentes do treinamento da rede e de suas inserções na tabela dados_modafet_exercicio;
     * os dados são inseridos nessa tabela e serão carregados todos os dados de exercícios de um único aluno,
     * para então gerar uma pontuação dada a quantidade de respostas para que então esses dados sejam inseridos na dados_modafet_estudante
     * 
     * @param classId
     * @return 
     */
    public HashMap<Integer, List<ConfidenceClassStudent>> getStudentData( int classId )
    {
        HashMap<Integer, List<ConfidenceClassStudent>> map = new HashMap();
        
        try 
        {
            Statement st = Database.getInstance().getConnection().createStatement();
            
            ResultSet result = st.executeQuery( "select u.id as student_id, u.nome, u.sobrenome, dm.* from dados_modafet_exercicio dm, exercicio_aluno ea, usuario u, turma t, turma_has_usuario tm "
                    + "where dm.exercicio_aluno_id = ea.id and tm.usuario_id = ea.usuario_id_aluno and ea.usuario_id_aluno = u.id and t.id = tm.turma_id and tm.usuario_id = u.id and t.id = " + classId + "; ");
            
            while( result.next() )
            {
                int studentId = result.getInt( "student_id" );
                
                if ( map.containsKey( studentId ) ) 
                {
                    map.get( studentId ).add( getStudentFromResult( result ) );
                }
                
                else
                {
                    List<ConfidenceClassStudent> values = new ArrayList();
                    
                    values.add( getStudentFromResult( result ) );
                    
                    map.put( studentId, values );
                }
            }
        } 
        
        catch ( Exception e ) 
        {
            e.printStackTrace();
        }
        
        return map;
    }
    
    /**
     * Monta o objeto ConfidenceClassStudent a partir do ResultSet
     * 
     * @param result
     * @return
     * @throws Exception 
     */
    public ConfidenceClassStudent getStudentFromResult( ResultSet result ) throws Exception
    {
        ConfidenceClassStudent student = new ConfidenceClassStudent();
        
        /*GENERAL DATA*/
        student.setDataId( result.getInt( "id" ) );
        student.setNome( result.getString( "nome" ) );
        student.setSobrenome( result.getString( "sobrenome" ) );
        student.setNivelCompreensao( result.getString( "classe_nivel_compreensao" ) );
        student.setClassePropPH( result.getString( "classe_prop_ph" ) );
        student.setDica1( result.getString( "visualizou_dica1" ) );
        student.setDica2( result.getString( "visualizou_dica2" ) );
        student.setPseudo( result.getString( "visualizou_pseudo" ) );

        /*CONFIANÇA*/
        student.setClasseTPprobCF( result.getString( "classe_tp_prob_cf" ) );
        student.setClasseTPhipCF( result.getString( "classe_tp_hip_cf" ) );
        student.setRetomadas( result.getString( "classe_retomadas" ) );
        student.setAvaliacaoAluno( result.getString( "classe_avaliacao_aluno" ) );

        /*ESFORÇO*/
        student.setClasseTPprobEF( result.getString( "classe_tp_prob_ef" ) );
        student.setClasseTPhipEF(  result.getString( "classe_tp_hip_ef" ) );
        student.setClasseTPcodEF( result.getString( "classe_tp_cod_ef" ) );
        student.setNivelDetalheHip( result.getString( "classe_nivel_detalhe_hip" ) );
        student.setNivelDetalheProb( result.getString( "classe_nivel_detalhe_prob" ) );
        student.setNumeroExecucoes( result.getString( "execucao_codigo" ) );
        
        return student;
    }
    
    /**
     * 
     * @param classId
     * @return 
     */
    public int countExercisesfromClass( int classId )
    {
        int count = 0;
        
        try 
        {
            Statement st = Database.getInstance().getConnection().createStatement();
            
            ResultSet result = st.executeQuery( "select count(*) from exercicio where turma_id = " + classId );
            
            if (result.next() ) 
            {
                count = result.getInt( 1 );
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return count;
    }
}
