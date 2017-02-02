package matrix;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.text.DecimalFormat;

public class MatrixTest
{
    public static void main(String[] args)
    {
        Scanner kb = new Scanner(System.in);
        double[][] temp;
        Matrix[] matrices = new Matrix[6];
        
        String input = ""; //arbitrary initialization
        int index1 = 0;
        int index2 = 0;
        char choice = 'H';
        
        do
        {
            switch (choice)
            {
                //Addition
                case 'A': System.out.println("Format: [A]+[B]");
                          System.out.print("Enter number of matrix A: ");
                          index1 = kb.nextInt() - 1;
                          kb.nextLine();
                          
                          System.out.print("Enter number of matrix B: ");
                          index2 = kb.nextInt() - 1;
                          kb.nextLine();
                          
                          matrices[5] = add(matrices[index1], matrices[index2]);
                          matrices[5].printMatrix();
                          break;
                    
                case 'D': System.out.print("Enter number of matrix: ");
                          index1 = kb.nextInt() - 1;
                          kb.nextLine();
                          
                          DecimalFormat df = new DecimalFormat("#,##0.00");
                    
                          double det = ref(matrices[index1]);
                          det = (double)Math.round(det * 100) / 100;
                          System.out.println("det(m" + (index1 + 1) + ") = " +
                                             df.format(det));
                          break;
                    
                case 'E': System.out.println("       Matrix Edit Menu       ");
                          System.out.println("------------------------------");
                          
                          for (int i = 0; i < matrices.length; i++)
                          {    
                              if (matrices[i] != null && !matrices[i].isEmpty())
                              {
                                  Matrix tempMatrix = matrices[i];
                                  double[][] m1 = tempMatrix.getArray();
                                  System.out.println("m" + (i+1) + " = " + m1.length + 
                                                   "x" + m1[0].length);
                              }
                              else
                                  System.out.println("m" + (i+1) + " = EMPTY");
                          }
                          System.out.println();
                          
                          System.out.print("Enter number of matrix to edit (1 = m1, 2 = m2, etc.): ");
                          int num = kb.nextInt() - 1;
                          kb.nextLine();
                          
                          temp = create();
                          matrices[num] = new Matrix(temp);
                          break;
                   
                //Inverse    
                case 'I': System.out.print("Enter number of matrix to invert: ");
                          index1 = kb.nextInt() - 1;
                          kb.nextLine();
                          
                          double det2 = ref(matrices[index1]);
                          det2 = (double)Math.round(det2 * 100) / 100;
                          
                          if (det2 == 0)
                          {
                              System.out.println("Selected matrix is not invertible! " + 
                                                 "[det(m" + (index1 + 1) + ") = 0]");
                          }
                          else
                          {
                          
                              temp = createAugmented(matrices[index1]);
                              matrices[5] = new Matrix(temp);
                              matrices[5] = rref(matrices[5]);
                          
                              temp = matrices[5].getArray();
                              double[][] result = matrices[index1].getArray();
                          
                              for (int i = 0; i < temp.length; i++)
                              {
                                  for (int j = temp[0].length / 2; j < temp[0].length; j++)
                                  {
                                      result[i][j - (temp[0].length / 2)] = temp[i][j];
                                  }
                              }
                          
                              matrices[5] = new Matrix(result);
                              matrices[5].printMatrix();
                          }
                          break;
                    
                //Multiplication    
                case 'M': System.out.println("Format: [A]*[B]");
                          System.out.print("Enter number of matrix A: ");
                          index1 = kb.nextInt() - 1;
                          kb.nextLine();
                          
                          System.out.print("Enter number of matrix B: ");
                          index2 = kb.nextInt() - 1;
                          kb.nextLine();
                          
                          matrices[5] = multiply(matrices[index1], matrices[index2]);
                          matrices[5].printMatrix();
                          break;
                    
                case 'P': System.out.println("       Matrix Print Menu      ");
                          System.out.println("------------------------------");
                          
                          for (int i = 0; i < matrices.length; i++)
                          {    
                              if (matrices[i] != null && !matrices[i].isEmpty())
                              {
                                  Matrix tempMatrix = matrices[i];
                                  double[][] m1 = tempMatrix.getArray();
                                  System.out.println("m" + (i+1) + " = " + m1.length + 
                                                   "x" + m1[0].length);
                              }
                              else
                                  System.out.println("m" + (i+1) + " = EMPTY");
                          }
                          System.out.println();
                    
                          System.out.print("Enter number of matrix to print (1 = m1, 2 = m2, etc.): ");
                          index1 = kb.nextInt() - 1;
                          kb.nextLine();
                          
                          System.out.print("Enter 1 for decimal printout or 2 for fractional printout: ");
                          index2 = kb.nextInt();
                          kb.nextLine();
                          
                          if (index2 == 2)
                          {
                              matrices[index1].printMatrixFractions();
                          }
                          else
                              matrices[index1].printMatrix();
                          
                          break;
                    
                //Reduced Row-Echelon Form    
                case 'R': System.out.print("Enter number of matrix to row-reduce: ");
                          index1 = kb.nextInt() - 1;
                          kb.nextLine();
                    
                          matrices[5] = rref(matrices[index1]);
                          matrices[5].printMatrix();
                          break;
                    
                case 'S': System.out.print("Enter number of matrix to multiply: ");
                          index1 = kb.nextInt() - 1;
                          kb.nextLine();
                          
                          System.out.print("Enter number to multiply matrix by: ");
                          int k = kb.nextInt();
                          kb.nextLine();
                    
                          matrices[index1].scalarMultiply(k);
                          matrices[index1].printMatrix();
                          break;
                    
                //Transpose    
                case 'T': System.out.print("Enter number of matrix to transpose: ");
                          index1 = kb.nextInt() - 1;
                          kb.nextLine();
                    
                          matrices[5] = transpose(matrices[index1]);
                          matrices[5].printMatrix();
                          break;
                    
                case 'H': System.out.println("A  Matrix addition");
                          System.out.println("D  Determinant of matrix");
                          System.out.println("E  Edit matrices");
                          System.out.println("I  Inverse of matrix");
                          System.out.println("M  Matrix multiplication");
                          System.out.println("P  Print out matrix");
                          System.out.println("R  Reduced Row-Echelon Form");
                          System.out.println("S  Scalar multiplication");
                          System.out.println("T  Transpose of matrix");
                          System.out.println("H  Display this message");
                          System.out.println("X  Exit the program");
            }
            
            System.out.println();  // line of space
            System.out.print("Command? ");
            input = kb.nextLine();
            choice = Character.toUpperCase(input.charAt(0));
        }
        while (choice != 'X');
    }
    
    
    public static double[][] create()
    {
        Scanner kb = new Scanner(System.in);
        double[][] array;
        String input;
        int row;
        int col;
        int temp;
        
        System.out.print("Enter number of rows: ");
        row = kb.nextInt();
        kb.nextLine();
        
        System.out.print("Enter number of columns: ");
        col = kb.nextInt();
        kb.nextLine();
        System.out.println();
        
        array = new double[row][col];
        
        for (int i = 0; i < row; i++)
        {
            System.out.print("Enter elements of row " + 
                               (i+1) + ", separated by spaces: ");
        
            input = kb.nextLine();
            
            StringTokenizer st = new StringTokenizer(input);
            
            for (temp = 0; temp < col; temp++)
            {
                if (st.hasMoreTokens())
                    array[i][temp] = Integer.parseInt(st.nextToken());
            }
        }
        
        return array;
    }
    
    public static double[][] createAugmented(Matrix matrix1)
    {
        double[][] A = matrix1.getArray();
        int rowsA = A.length;
        int colsA = A[0].length;
        
        double[][] ret = new double[rowsA][2*colsA];
        
        for (int i = 0; i < rowsA; i++)
        {
            for (int j = 0; j < colsA; j++)
                ret[i][j] = A[i][j];
        }
        
        for (int i = 0; i < rowsA; i++)
        {
            for (int j = colsA; j < ret[0].length; j++)
            {
                if (i == j - colsA)
                    ret[i][j] = 1;
                else
                    ret[i][j] = 0;
            }
        }
        
        return ret;
    }
    
    public static Matrix add(Matrix matrix1, Matrix matrix2)
    {
        double[][] A = matrix1.getArray();
        double[][] B = matrix2.getArray();
        
        int rowsA = A.length;
        int colsA = A[0].length;
        int rowsB = B.length;
        int colsB = B[0].length;
        
        double[][] temp = new double[rowsA][colsB];
        Matrix result = new Matrix(temp);
        
        if (rowsA != rowsB || colsA != colsB)
        {
            System.out.println("Cannot add (invalid dimensions)");
        }
        
        else
        {
            for (int row = 0; row < rowsA; row++)
            {
                for (int col = 0; col < colsA; col++)
                    temp[row][col] = A[row][col] + B[row][col];
            }
            
            result = new Matrix(temp);
        }
        
        return result;
    }
    
    public static Matrix multiply(Matrix matrix1, Matrix matrix2)
    {
        double[][] A = matrix1.getArray();
        double[][] B = matrix2.getArray();
        
        int rowsA = A.length;
        int colsA = A[0].length;
        int rowsB = B.length;
        int colsB = B[0].length;
        
        double[][] temp = new double[rowsA][colsB];
        Matrix result = new Matrix(temp);
        
        if (colsA != rowsB)
        {
            System.out.println("Cannot multiply (invalid dimensions)");
        }
        
        else
        {
            for (int row = 0; row < rowsA; row++)
            {
                for (int col = 0; col < colsB; col++)
                {
                    for (int i = 0; i < colsA; i++)
                        temp[row][col] += A[row][i] * B[i][col];
                }
            }
            
            result = new Matrix(temp);
        }

        return result;
    }
    
    public static Matrix transpose(Matrix matrix1)
    {
        double[][] A = matrix1.getArray();
        int rowsA = A.length;
        int colsA = A[0].length;
        
        double[][] temp = new double[colsA][rowsA];
        Matrix result = new Matrix(temp);
        
        for (int i = 0; i < colsA; i++)
        {
            for (int j = 0; j < rowsA; j++)
            {
                temp[i][j] = A[j][i];
            }
        }
        
        result = new Matrix(temp);
        return result;
    }
    
    public static double ref(Matrix matrix1)
    {
        Matrix ret = matrix1;
        double total = 1.0;
        
        for (int i = 1; i <= matrix1.getRowLength() - 1; i++)
        {
            for (int j = 1; j <= matrix1.getRowLength(); j++)
            {
                total *= ref(ret, i, j, i);
            }
        }
        
        double[][] array = matrix1.getArray();
        
        for (int i = 0; i < array.length; i++)
        {
            total *= array[i][i];
        }

        return total;
    }
    
    public static double ref(Matrix matrix1, int rowA, int rowB, int col)
    {
        rowA--;
        rowB--;
        col--;
        double coeff = 0.0;
        double ret = 1.0;
        double[][] array = matrix1.getArray();
        
        if (array[rowA][col] == 0 || array[rowB][col] == 0)
            return ret;
        
        if (array[rowA][col] != 1)
        {
            coeff = 1 / array[rowA][col];
            ret = array[rowA][col];
            
            for (int j = 0; j < array[0].length; j++)
            {
                array[rowA][j] *= coeff;
            }
        }
        
        if (rowA >= rowB)
        {
            matrix1 = new Matrix(array);
            return ret;
        }

        coeff = (array[rowB][col] / array[rowA][col]) * -1;
        
        for (int j = 0; j < array[0].length; j++)
        {
            array[rowB][j] += (coeff * array[rowA][j]);
        }
        
        matrix1 = new Matrix(array);
        return ret;
    }
    
    public static Matrix rref(Matrix matrix1)
    {
        Matrix ret = matrix1;
        for (int i = 1; i <= matrix1.getRowLength(); i++)
        {
            for (int j = 1; j <= matrix1.getRowLength(); j++)
            {
                    ret = rref(ret, i, j, i);
            }
        }

        return ret;
    }
    
    public static Matrix rref(Matrix matrix1, int rowA, int rowB, int col)
    {
        rowA--;
        rowB--;
        col--;
        double coeff = 0.0;
        double[][] array = matrix1.getArray();
        
        if (array[rowA][col] == 0 || array[rowB][col] == 0)
            return matrix1;
        
        if (array[rowA][col] != 1)
        {
            coeff = 1 / array[rowA][col];
            
            for (int j = 0; j < array[0].length; j++)
            {
                array[rowA][j] *= coeff;
            }
        }
        
        if (rowA == rowB)
            return new Matrix(array);

        coeff = (array[rowB][col] / array[rowA][col]) * -1;
        
        for (int j = 0; j < array[0].length; j++)
        {
            array[rowB][j] += (coeff * array[rowA][j]);
        }
        
        return new Matrix(array);
    }
}