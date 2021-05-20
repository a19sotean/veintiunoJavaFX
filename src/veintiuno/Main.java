package veintiuno;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Veintiuno");
    FXMLLoader fxml = new FXMLLoader(this.getClass().getResource("vistas/EmpezarJuego.fxml"));
    VBox root = fxml.<VBox>load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  public static void main(String[] args) {
    launch(args);
  }
}