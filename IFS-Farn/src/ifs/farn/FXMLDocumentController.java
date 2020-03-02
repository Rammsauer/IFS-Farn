/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifs.farn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Leon
 * Der Controller zu der FXML Datei FXMLDocument
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField IterationText;
    @FXML
    private ColorPicker Color1;
    @FXML
    private ColorPicker Color2;
    @FXML
    private ColorPicker Color3;
    @FXML
    private ColorPicker Color4;
    @FXML
    private Button Clear;
    @FXML
    private Button Create;
    @FXML
    private RadioButton Radio1;
    @FXML
    private RadioButton Radio4;
    @FXML
    private MenuBar Menue;
    @FXML
    private MenuItem Open;
    @FXML
    SplitPane secondPane;
    @FXML
    private ProgressBar Progress;
    
    AnchorPane rootPane;
    PaneController PaneController;
    
    private double[][] a = new double[4][7];
    private int  open = 0;
    private int iter = 0;
    
    /**
     *
     * Initialisiere 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }    
    
    /**
     *
     * @param rootPane
     * Setzt das GUI
     */
    public void setPane(AnchorPane rootPane) {
        this.rootPane = rootPane;
    }
    
    /**
     *
     * @return
     * Gibt das GUI zurück
     */
    public Parent getRoot() {
        return rootPane;
    }
    
    /**
     * 
     * Setzt beim verändern der Farbe die jeweiligen ColorPicker auf sichtbar oder nicht
    */
    @FXML
    private void onecolor(ActionEvent event){
        if(Radio4.isSelected()){
            Radio4.setSelected(false);
        }
        Color2.setVisible(false);
        Color3.setVisible(false);
        Color4.setVisible(false);
    }
    @FXML
    private void fourcolor(ActionEvent event){
        if(Radio1.isSelected()){
            Radio1.setSelected(false);
        }
        Color2.setVisible(true);
        Color3.setVisible(true);
        Color4.setVisible(true);
    }
    
    /**
     *
     * @param event
     * @throws FileNotFoundException 
     * Öffnet die Tabelle zum generieren des Fraktals
     */
    @FXML
    private void Openfile(ActionEvent event) throws FileNotFoundException{
        JFileChooser jf = new JFileChooser();
        Scanner in = null;       
        if(jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File sf = jf.getSelectedFile();
            in = new Scanner(sf);
            int l = 0,ln = 1;
            final int MAX_LINES = 4;
            while(in.hasNextLine() && ln <= MAX_LINES){
                String line = in.nextLine();
                String[] cache = line.split(",");
                for(int i = 0; i < cache.length; i++){
                    a[l][i] = Double.parseDouble(cache[i]);
                }
                l++;
                ln++;
            }
        }
        open++;
    }
    
    /**
     *
     * @return
     * FXML Loader um das GUI zu laden
     */
    public static FXMLDocumentController getInstance() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(FXMLDocumentController.class.getResource(
                "FXMLDocument.fxml"));
            AnchorPane rootPane = (AnchorPane) loader.load();
            FXMLDocumentController fxmldocumentController = loader.<FXMLDocumentController>getController();
            fxmldocumentController.setPane(rootPane);
            return fxmldocumentController;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     *Initialisiert die Oberfläche mit den jeweiligen Startwerten
     */
    public void init() {
        PaneController = PaneController.getInstance();
        secondPane.getItems().set(1, PaneController.getRoot());
        Color1.setValue(Color.BLACK);
        Color2.setValue(Color.BLUE);
        Color3.setValue(Color.RED);
        Color4.setValue(Color.GREEN);
        PaneController.clear();
        IterationText.setText("2500");
    }
    
    /**
     * 
     * @param event 
     * Speichert das erzeugte Fraktal
     */
    @FXML
    private void SaveFile(ActionEvent event){
        JFileChooser jf = new JFileChooser();
        if(jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            String s = jf.getSelectedFile() + " ";
            if(s.endsWith("\\")){
                File Destination = new File(jf.getSelectedFile() + "01" + ".png");
                PaneController.save(Destination);
            }
            else{
                File Destination = new File(jf.getSelectedFile() + ".png");
                PaneController.save(Destination);
            }
        }
    }
    
    /**
     * 
     * @param event
     * Erzeugt das jeweilige Fraktal 
     */
    @FXML
    private void CreatePicture(ActionEvent event){
        if(open > 0){
            try{
                iter = Integer.parseInt(IterationText.getText());
                if(!(Color2.isVisible())){
                    this.matrix(Color1.getValue(), Color1.getValue(), Color1.getValue(), Color1.getValue());
                }
                else{
                    this.matrix(Color1.getValue(), Color2.getValue(), Color3.getValue(), Color4.getValue());
                }
            }
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Bitte geben Sie nur Zahlen ein!", "Falsche Iterationseingabe!",JOptionPane.WARNING_MESSAGE);
                IterationText.setText("2500");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie erst eine Datei aus!", "Dateipfad fehlt!",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 
     * @param event
     * Bereinigt die Pane
     */
    @FXML
    private void ClearPicture(ActionEvent event){
        PaneController.clear();       
    }
    
    /**
     *
     * @param c1 = Farbe 1
     * @param c2 = Farbe 2
     * @param c3 = Farbe 3
     * @param c4 = Farbe 4
     * 
     * Berechnet jeden einzelnen Punkt und übergibt ihn an in die Pane
     * 
     */
    public void matrix(Color c1, Color c2, Color c3, Color c4){
        double x = 0, y = 0, g = 1, t =38, u = 33;
        Progress.setProgress(0);
        System.out.println("draw");
        PaneController.draw(x, y, g, c1);
        for(int i = 0; i < iter; i++){
            int n = (int) (Math.random()*100);
            if((n > -1) && (n < 3)){
               double l = x;
               x = ((a[0][0] * x) + (a[0][1] * y) + a[0][4]);
               y = ((a[0][2] * l) + (a[0][3] * y) + a[0][5]);
               PaneController.draw(x*t, y*u, g, c1);
            }
            else if((n >= 3) && (n < 13)){
               double l = x;
               x = ((a[1][0] * x) + (a[1][1] * y) + a[1][4]);
               y = ((a[1][2] * l) + (a[1][3] * y) + a[1][5]);
               PaneController.draw(x*t, y*u, g, c2);
            }
            else if((n >= 13) && (n < 24)){
               double l = x;
               x = ((a[2][0] * x) + (a[2][1] * y) + a[2][4]);
               y = ((a[2][2] * l) + (a[2][3] * y) + a[2][5]);
               PaneController.draw(x*t, y*u, g, c3); 
            }
            else if((n >= 24) && (n < 101)){
               double l = x;
               x = ((a[3][0] * x) + (a[3][1] * y) + a[3][4]);
               y = ((a[3][2] * l) + (a[3][3] * y) + a[3][5]);
               PaneController.draw(x*t, y*u, g, c4);
            }
            double pro = (double)(i+1) / 10;
            Progress.setProgress(pro);
        }
    }
    
    /**
     *
     * @param event
     * Event wenn man Hilfe anfordert
     */
    @FXML
    public void AboutAction(ActionEvent event){
        JOptionPane.showMessageDialog(null, "Dies ist ein Leon Rudolph und Simon Ruff Programm!", "I'm gonna help you!",JOptionPane.INFORMATION_MESSAGE);
    }
}
