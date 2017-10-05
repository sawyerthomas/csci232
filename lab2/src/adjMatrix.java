

import java.io.*;
import java.util.Scanner;

public class adjMatrix {
	
	static int[][] matrix;
	
	public static void readMatrix(String fname)
	{
        // The name of the file to open.
        String fileName = "matrix.txt";
        
        
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("what is the dimension of your matrix?");
        
        int n = sc.nextInt();
        
        

        // This will reference one line at a time
        String line = null;
        String[] arr = new String[n];
        int i = 0;
        matrix = new int[n][n];
        

        try {
            // FileReader reads text files in the default encoding.
            
        	
        	FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	
            	//System.out.println(line);
            	arr[i] = line;
            	i++;
               
            } 
            
            
            
            

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        for (int j = 0; j<arr.length; j++)
        {
        	for (int z=0; z<arr.length; z++)
        	{
        		
        		matrix[j][z] =  Character.getNumericValue(arr[j].charAt(z));
        		
        	}
        }
        
       
    }
	
    public static void main(String [] args) {
    	
    	readMatrix("matrix.txt");
    	
    	for (int i=0; i<matrix.length; i++)
    		
    	{
    		System.out.println();
    		for (int j=0; j<matrix.length; j++)
    		{
    			System.out.print(matrix[i][j]);
    		}
    	}
    	
    	
    }
    

       
    
}
