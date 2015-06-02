/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package equitrack1.pkg;


import data.pkg.GenerateCsv;
import static data.pkg.GenerateCsv.generateCsvFile;
import static data.pkg.ReadWriteExcelFile.writeXLSFile;
import static data.pkg.ReadWriteExcelFile.writeXLSXFile;
import static equitrack1.pkg.FXMLDocumentController.data;
import static equitrack1.pkg.FXMLDocumentController.prompt;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

/**
 * FXML Controller class
 *
 * @author Master
 */
public class PromptController implements Initializable {
    @FXML
    private Button saveCsv;
    @FXML
    private TextField saveFileName;
    
    private String fileName;
    
    public GenerateCsv gen;
    
    @FXML
    private Button path;
    
    private String pathS;
    
    @FXML // fx:id="myChoices"
    private ChoiceBox<String> myChoices; // Value injected by FXMLLoader
    @FXML
    private Label folder;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert myChoices != null : "fx:id=\"myChoices\" was not injected: check your FXML file 'foo.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected
        // => you can add items to "myChoices" here:
        // 
        myChoices.setItems(FXCollections.observableArrayList());
        myChoices.getItems().add(".csv");
        myChoices.getItems().add(".xlsx");
        myChoices.getItems().add(".xls");
    }    
    
    

    @FXML
    private void onClickSaveCsv(ActionEvent event) throws IOException {
        
        String prefix;
        String extension;
      
        extension = myChoices.getValue();
        
        switch(extension){
            case ".csv" : generateCsvFile(pathS + saveFileName.getText() + extension, data);
                break;
            case ".xls" : writeXLSFile(pathS + saveFileName.getText() + extension, data);
                break;
            case ".xlsx" : writeXLSXFile(pathS + saveFileName.getText() + extension, data);
                break;
                
        }
        
        
        System.out.println("Saved");
        FXMLDocumentController.prompt.close();
    }

    @FXML
    private void onClickPath(ActionEvent event) {
        
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(prompt);

        pathS = selectedDirectory.getAbsolutePath() + "\\";
        folder.setText(pathS);
    }
    
}
