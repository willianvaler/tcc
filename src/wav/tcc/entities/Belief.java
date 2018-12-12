/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wav.tcc.entities;

/**
 *
 * @author willi
 */
public class Belief
{
    public float b1;
    public float b2;

    public Belief(float b1, float b2)
    {
        this.b1 = b1;
        this.b2 = b2;
    }

    public float getB1()
    {
        return b1;
    }

    public float getB2()
    {
        return b2;
    }

    @Override
    public String toString()
    {
        return b1 + ", " + b2 + ";";
    }
}
