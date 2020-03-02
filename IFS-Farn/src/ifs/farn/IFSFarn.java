/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifs.farn;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Leon
 * Die Main des IFS-Farns
 */
public class IFSFarn extends Application {
    
    /**
     * 
     * @param stage
     * @throws Exception 
     * Startet die Stage und ruft den FXML loader auf
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLDocumentController fxmlDocumentcontroller = FXMLDocumentController.getInstance();

        Parent root = fxmlDocumentcontroller.getRoot();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("IFS-Farn");
        stage.setResizable(false);
        stage.setHeight(480);
        stage.setWidth(586);
        stage.show();
        Image icon = new Image("file:chart.png");
        stage.getIcons().add(icon);

        fxmlDocumentcontroller.init();
    }

    /**
     * @param args the command line arguments
     * main method
     */
    public static void main(String[] args) {

        launch(args);

    }
    
}
