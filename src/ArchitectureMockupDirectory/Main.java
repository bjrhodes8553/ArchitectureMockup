package ArchitectureMockupDirectory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
  public static Estimate currentEstimate;


  @Override
  public void start(Stage primaryStage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("Estimator.fxml"));
    Scene sceneMain = new Scene(root);
    primaryStage.setTitle("Estimator Tool");
    primaryStage.setScene(sceneMain);
    primaryStage.show();
  }

  //This method is called to create a new screen from clicking a button.
  public static void createNewScene(Event event, String newFileFXML) {
    Parent newRoot = null;
    try {
      newRoot = FXMLLoader.load(Main.class.getResource(newFileFXML));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Scene newScene = new Scene(newRoot);
    //newScene.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(newScene);
    window.show();


  }

  public static void main(String[] args) {
    launch(args);
  }
}