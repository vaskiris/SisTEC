/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package equitrack1.pkg;

import data.pkg.Data;
import gnu.io.SerialPort;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import serialcom.SerialTest;

/**
 *
 * @author Master
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private LineChart<Number, Number> chart; //mudou o static
    @FXML
    private Label label;
    @FXML
    private Button start;
    @FXML
    private Button stop;
    @FXML
    private Button reset;
    @FXML
    private Button update;
    @FXML
    private MenuItem export;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem open;
    @FXML
    private Label comText;
    @FXML
    public TextField serialComSetup;
    @FXML
    private Button aplicarTeste;
    @FXML
    private Button calibrate;
    @FXML
    public ChoiceBox<?> tipoTeste;
    @FXML
    private ChoiceBox<?> velocidade;
    @FXML
    private ChoiceBox<?> aquisicao;
    
    public static int calibration;
    public static double[] calibrationArray;
    private static LineChart<Number, Number> chartTemp;
    private  SerialTest s = new SerialTest();
    private String[] info = new String[6];
    private int i = 0;
    private String infoS = new String();
    private SerialPort serialP;
    public static Data data = new Data();
    public static Data dataOpen = new Data();
    private BufferedReader input;
    public static Stage prompt = new Stage();
    public static Stage openPrompt = new Stage();
    public static Stage window = new Stage();
   
    
    //private XYChart.Series series = new XYChart.Series();

    //private  XYChart.Series series2 = new XYChart.Series();
    
    
    private static ObservableList<XYChart.Series<Number, Number>> lineChartData2 = FXCollections.observableArrayList();
    @FXML
    private Button MotorUp;
    @FXML
    private Button MotorDown;
    @FXML
    private AnchorPane dataSelection;
    @FXML
    private TextField velocityT;
    
    
    @FXML
    private void onClickStart(ActionEvent event) {
        infoS ="\n Started!";
        label.setText(infoS);
        s.writetoport("E1%");
        
    }
    @FXML
    private void onClickStop(ActionEvent event) {
        s.writetoport("E0%");
        infoS =  "\n Stoped!";
        label.setText(infoS);
    }
   
    @FXML
    private void onClickReset(ActionEvent event) {
        data.resetAll();
        data = new Data();
        s.i = 0;
        infoS =  "\n Data Reset";
        s.writetoport("R%");
        label.setText(infoS);
    }
    @FXML
    private void onClickExp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("prompt.fxml"));
        
        Scene scene = new Scene(root);
        
        prompt.setScene(scene);
        prompt.setResizable(false);
        prompt.show(); 
    }
    public void updateChart(){
        
            Platform.runLater(new Runnable(){
            @Override
            public void run() {
                ObservableList<XYChart.Series<Number, Number>> lineChartData = FXCollections.observableArrayList();
                LineChart.Series<Number, Number> series = new LineChart.Series<>();

                for (int i = 0; i < data.getForcaSize()-1; i++) {
                        series.getData().add(new XYChart.Data(data.getDeslocamento(i), data.getForca(i)));
                }
                lineChartData.add(series);
                chart.setData(lineChartData);
                chart.getXAxis().setLabel("Deslocamento (mm)");
                chart.getYAxis().setLabel("Forca (N)");
                chart.setLegendVisible(false);
                
                }
            
            });
        
        
        
    }
    public void updateOpenChart(){
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                ObservableList<XYChart.Series<Number, Number>> lineChartData = FXCollections.observableArrayList();
                LineChart.Series<Number, Number> series2 = new LineChart.Series<>();

                for (int i = 0; i < dataOpen.getForcaSize(); i++) {
                        series2.getData().add(new XYChart.Data(dataOpen.getDeslocamento(i)*0.011, dataOpen.getForca(i)*0.00192-0.21));
                }
                
                lineChartData.add(series2);
                chart.setData(lineChartData);  
                chart.getXAxis().setLabel("Deslocamento (mm)");
                chart.getYAxis().setLabel("Forca (N)");
                chart.setLegendVisible(false);
            }
        });
    }
    
    @FXML
    private void onClickClose(ActionEvent event) {
        s.close();
        System.out.println("Serial Closed");
        window.close();
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        window.setTitle("EquiTRCK");
        s.initialize(this);
        
        
    }   

    @FXML
    private void onClickOpen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("OpenPrompt.fxml"));
        
        Scene scene = new Scene(root);
        openPrompt.setScene(scene);
        openPrompt.setResizable(false);
        
        openPrompt.show();
        
    }


    @FXML
    private void onClickSendTest(ActionEvent event) {
        
        String velT = velocityT.getText();
        String teste = tipoTeste.getValue().toString().substring(0, 1);
        String aq = aquisicao.getValue().toString().substring(0, 1);
        String ini =  "T" + teste + "V" + velT + "A" + aq + "%";
        System.out.println(ini);
        System.out.println(velT);
        s.writetoport(ini);
    }

    @FXML
    private void onClickCalibrate(ActionEvent event) {
        calibration = 1;
        s.writetoport("C%");    
    }  

    @FXML
    private void onClickMotorUp(ActionEvent event) {
        s.writetoport("U%");
    }

    @FXML
    private void onClickMotorDown(ActionEvent event) {
        s.writetoport("D%");
    }
     @FXML
    public void onClickUpdate (ActionEvent event) {
        
        try {
            updateOpenChart();
            infoS = "\n Chart Update Done";
            label.setText(infoS);
            } 
        catch (Exception e) {
            System.err.println(e.toString());
            infoS =  "\n Chart Update Error: No Data.";
            label.setText(infoS);
            }
    }
    
}
