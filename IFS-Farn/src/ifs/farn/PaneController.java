/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifs.farn;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Leon
 * Ist die Wand worauf das Fraktal erzeugt wird
 */
public class PaneController {
    
    @FXML
    private Canvas rootPane;
    
    private GraphicsContext context;
    
    /**
     * 
     * @return
     * Initialisieren der Canvas
     */
    public static PaneController getInstance() {
        PaneController controller = new PaneController();
        controller.rootPane = new Canvas();
        controller.rootPane.setWidth(375);
        controller.rootPane.setHeight(435);
        controller.context = controller.rootPane.getGraphicsContext2D();
        controller.context.scale(1, 1);
        return controller;
    }
    
    /**
     * 
     * @return 
     * get das GUI
     */
    public Canvas getRoot() {
        return this.rootPane;
    }
    
    /**
     * 
     * @param x  x Koordinate
     * @param y  y Koordinate
     * @param W  Größe des Ovals
     * @param color Farbe des Punktes
     * 
     * erzeugt ein Oval welches ein Punkt darstellt 
     */
    public void draw(double x, double y, double W, Color color){
        this.context.setFill(color);
        this.context.fillOval(x + 187.5,435 - y, W, W);
    }
    
    /**
     * Bereinigen der Canvas
     */
    public void clear(){
        this.context.clearRect(0, 0, this.rootPane.getWidth(), this.rootPane.getHeight());
    }
    
    /**
     * 
     * @param Destination Speicherort
     * Speichert die Canvas
     */
    public void save( File Destination){
        WritableImage snap = rootPane.snapshot(new SnapshotParameters(), null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snap, null), "png", Destination);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unbekannter Fehler beim speichern des Bildes", "Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
