/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package equitrack1.pkg;


import static data.pkg.ReadWriteExcelFile.readXLSFile;
import static data.pkg.ReadWriteExcelFile.readXLSXFile;
import static equitrack1.pkg.FXMLDocumentController.dataOpen;
import static equitrack1.pkg.FXMLDocumentController.openPrompt;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

/**
 *
 * @author Master
 */
public class OpenPromptController {
    
    @FXML
    private Button openFile;
    @FXML
    private Button searchFile;
    @FXML
    private Label openFolder;
    
    private String pathOpen;
    private File file;
    

    @FXML
    private void onClickOpenFile(ActionEvent event) throws IOException {
        
        String extension = pathOpen.substring(pathOpen.lastIndexOf('.') + 1);
        switch(extension){
            
            case "xls" : readXLSFile(pathOpen);
                         System.out.println("open xls");
                break;
            case "xlsx" :   readXLSXFile(pathOpen);
                            System.out.println("open xlsx");
                break;
                
        }
        System.out.println(dataOpen.getDeslocamento(0) + " " + dataOpen.getForca(0));
        
        FXMLDocumentController.openPrompt.close();
        
        
    }

    @FXML
    private void onClickFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter xlsxFilter = 
                        new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
        FileChooser.ExtensionFilter xlsFilter = 
                        new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls");
        
        fileChooser.getExtensionFilters().add(xlsxFilter);
        fileChooser.getExtensionFilters().add(xlsFilter);

        fileChooser.setTitle("Open Resource File");
        
        file = fileChooser.showOpenDialog(openPrompt);
        pathOpen = file.getAbsolutePath();
        openFolder.setText(pathOpen);
    }
    
}
