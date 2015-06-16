/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.pkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Master
 */
public class LogFile {
    
    private String logName;
    
    public LogFile(String name){
        logName = name;
    }
    
    
    public void createLog() throws FileNotFoundException, IOException{
        File parentDir = new File(logName);
        parentDir.mkdir();
        String hash = "Log";
        String fileName = hash + ".txt";
        File file = new File(parentDir, fileName);
        file.createNewFile();
           
    }
    
    public void writeLog(String t){
        
    }
    
    public void readLast(){
        
    }
}
