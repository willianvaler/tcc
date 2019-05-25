/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wav.tcc.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author willi
 */
public class NetsUtil
{
    /**
     * calcula o primeiro e o terceiro quartil e retorna o valor limite de cada um
     * 
     * @param integers
     * @return 
     */
    public static int[] getQuartiles( List<Integer> integers )
    {
        int[] quartiles = new int[2];
        
        int firstQuartile = Math.round( Float.parseFloat( String.valueOf( ( integers.size() + 1 ) * 0.25 ) ) );
        int thirdQuartile = Math.round( Float.parseFloat( String.valueOf( ( integers.size() + 1 ) * 0.75 ) ) );

        List<Integer> orderedList = new ArrayList( integers );

        Collections.sort( orderedList );

        quartiles[0] = orderedList.get( firstQuartile );
        quartiles[1] = orderedList.get( thirdQuartile );
        
        return quartiles;
    }
    
    /**
     * 
     * @param firtsQuartile
     * @param thirdQaurtile
     * @param value
     * @return 
     */
    public static String getQuartileEvaluation( int firtsQuartile, int thirdQaurtile, int value )
    {
        if ( value <= firtsQuartile )
        {
            return "BAIXO";
        }
        
        else if( value > firtsQuartile && value < thirdQaurtile )
        {
            return "MEDIO";
        }
        
        else
        {
            return "ALTO";
        }
    }
    
    /**
     * Retorna a media geral dobrada
     * 
     * @param values
     * @return 
     */
    public static double calculateDoubleAverage( List<Integer> values )
    {
        int count = 0;
        
        for( int i : values )
        {
            count += i;
        }
        
        return ( count / values.size() ) * 2;
    }
    
    /**
     * calcula a proporcao do tempo gasto entre problematizacao e hipotese 
     * com o tempo gasto codificando
     * 
     * @param problem
     * @param hypothesis
     * @param code
     * @return 
     */
    public static double calculateProportion( double problem, double hypothesis, double code )
    {
        return (problem + hypothesis) / (problem + hypothesis + code);
    }
    
    /**
     * Retorna a string equivalente a avaliacao do estudante
     * 
     * @param avaliacao
     * @return 
     */
    public static String getAvaliacaoEstudante( int avaliacao )
    {
        /** 1 Fácil, 2 Médio, 3 Difícil **/
        switch ( avaliacao )
        {
            case 1:
                return "FACIL";
            case 2:
                return "MEDIO";
            case 3:
            default:
                return "DIFICIL";
        }
    }
    
    /**
     * 
     * @param evaluation
     * @return 
     */
    public static int getWeightStudentEvaluation( String evaluation )
    {
        /** 1 Fácil, 2 Médio, 3 Difícil **/
        switch ( evaluation )
        {
            case "FACIL":
                return 1;
            case "MEDIO":
                return 2;
            case "DIFICIL":
            default:
                return 3;
        }
    }
    
    /**
     * Retorna o nível da compreesao do aluno
     * 
     * @param nivel
     * @return 
     */
    public static String getNivel( int nivel )
    {
        switch ( nivel )
        {
            case 1:
                return "BAIXO";
            case 2:
                return "MEDIO";
            default:
                return "ALTO";
        }
    }
    
    /**
     * 
     * @param description
     * @return 
     */
    public static int getWeight( String description )
    {
        switch ( description )
        {
            case "BAIXO":
                return 1;
            case "MEDIO":
                return 2;
            case "ALTO":
                return 3;
            default:
                return 1;
        }
    }
    
    /**
     * 
     * @param countDicas
     * @param countMaxExercicios
     * @return 
     */
    public static String getDicaAvaliacao( int countDicas, int countMaxExercicios )
    {
        if ( countDicas == 0 ) { return "NENHUMA"; }
        else if( countDicas < ( countMaxExercicios / 2) ) { return "POUCAS"; }
        else { return "MUITAS"; }
    }
    
    /**
     * 
     * @param countPseudo
     * @param countMaxExercicios
     * @return 
     */
    public static String getPseudoAvaliacao( int countPseudo, int countMaxExercicios )
    {
        if ( countPseudo == 0 ) { return "NENHUMA"; }
        else if( countPseudo < ( countMaxExercicios * 0.3) ) { return "POUCAS"; }
        else { return "MUITAS"; }
    }
    
    /**
     * returns the right category depending of student points
     * 
     * @param studentPoints
     * @param maxPoints
     * @return 
     */
    public static String getPointsCategory( double studentPoints, double maxPoints )
    {
        double percent = ( studentPoints / maxPoints ) * 100;

        if (percent <= 25 ) { return "BAIXO"; }
        else if ( percent > 25 && percent < 50 ){ return "MEDIO_BAIXO"; }
        else if ( percent >= 50 && percent < 75 ){ return "MEDIO_ALTO"; }
        else { return "ALTO"; }
    }
    
    /**
     * 
     * @param studentPoints
     * @param maxPoints
     * @return 
     */
    public static String getEvaluationCategory( double studentPoints, double maxPoints )
    {
        double percent = ( studentPoints / maxPoints ) * 100;

        if (percent <= 25 ) { return "FACIL"; }
        else if ( percent > 25 && percent < 50 ){ return "MEDIO_FACIL"; }
        else if ( percent >= 50 && percent < 75 ){ return "MEDIO_DIFICIL"; }
        else { return "DIFCIL"; }
    }
    
//    public static String get
}
