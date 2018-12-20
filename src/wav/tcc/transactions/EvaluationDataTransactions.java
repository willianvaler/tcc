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
import wav.tcc.entities.ConfidenceClassStudent;
import wav.tcc.util.NetsUtil;

/**
 *
 * @author willi
 */
public class EvaluationDataTransactions 
{
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
    
    public List<ConfidenceClassStudent> getStudentsList()
    {
        /*esforço*/
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
        /*confiança*/
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
        
        return new ArrayList();
    }
}
