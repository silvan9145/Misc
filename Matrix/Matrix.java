package matrix;

import java.text.DecimalFormat;

public class Matrix
{
    private double[][] mat;
    
    public Matrix(double[][] arr)
    {
        mat = arr;
    }
    
    public void printMatrix()
    {
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        
        for (int i = 0; i < mat.length; i++)
        {
            System.out.print("| ");
            
            for (int col = 0; col < mat[i].length; col++)
            {
                System.out.print(df.format(mat[i][col]) + " ");
            }
            
            System.out.println("|");
        }
    }
    
    public void printMatrixFractions()
    {
        DecimalFormat df = new DecimalFormat("#0");
        
        for (int i = 0; i < mat.length; i++)
        {
            System.out.print("| ");
            
            for (int j = 0; j < mat[i].length; j++)
            {
                if ((int) mat[i][j] != mat[i][j])
                {
                    double[] frac = printFraction(mat[i][j]);
                    System.out.print(df.format(frac[0]) + "/" + df.format(frac[1]) + " ");
                }
                else
                    System.out.print(mat[i][j] + " ");
            }
            
            System.out.println("|");
        }
    }
    
    //Modified from http://jonisalonen.com/2012/converting-decimal-numbers-to-ratios/ 
    public double[] printFraction(double x)
    {
        double[] ret = new double[2];
        boolean negative = false;
        if (x < 0)
        {
            x *= -1;
            negative = true;
        }
        
        double tolerance = 1.0E-6;
        double h1 = 1, h2 = 0;
        double k1 = 0, k2 = 1;
        double b = x;
        
        do
        {
            double a = Math.floor(b);
            double aux = h1;
            h1 = a*h1+h2;
            h2 = aux;
            aux = k1;
            k1 = a*k1+k2;
            k2 = aux;
            b = 1/(b-a);
        }
        while (Math.abs(x-h1/k1) > x*tolerance);
        
        if (negative)
            ret[0] = -h1;
        else
            ret[0] = h1;
        ret[1] = k1;
        return ret;
    }
    
    public int getRowLength()
    {
        return mat.length;
    }
    
    public int getColumnLength()
    {
        return mat[0].length;
    }
    
    public boolean isEmpty()
    {
        boolean answer = true;
        
        for (int i = 0; i < mat.length; i++)
        {
            for (int j = 0; j < mat[i].length; j++)
            {
                if (mat[i][j] != 0)
                    answer = false;
            }
        }
        
        return answer;
    }
    
    public double[][] getArray()
    {
        return mat;
    }
    
    public void scalarMultiply(double k)
    {
        for (int i = 0; i < mat.length; i++)
        {
            for (int j = 0; j < mat[i].length; j++)
            {
                mat[i][j] = k * mat[i][j];
            }
        }
    }
}
