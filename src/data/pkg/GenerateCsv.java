/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.pkg;

/**
 *
 * @author Master
 */
import java.io.FileWriter;
import java.io.IOException;
import static equitrack1.pkg.FXMLDocumentController.data; 

public class GenerateCsv
{
   
 
   public static void generateCsvFile(String sFileName, Data data)
   {
	try
	{
	    FileWriter writer = new FileWriter(sFileName);
            writer.append("Deslocamento");
            writer.append(',');
	    writer.append("Força");
	    writer.append('\n');
            
            for (int i = 0; i < data.getLength(); i++) {
               writer.append(String.valueOf(data.getDeslocamento(i)));
               writer.append(',');
               writer.append(String.valueOf(data.getForca(i)));
               writer.append('\n'); 
            }
	    
 
	    //generate whatever data you want
 
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }
}
