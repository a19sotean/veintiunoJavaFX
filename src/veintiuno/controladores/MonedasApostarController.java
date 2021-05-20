package veintiuno.controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import veintiuno.Juego;
import veintiuno.Jugador;

public class MonedasApostarController {
  
  Juego juego21;
  
  public MonedasApostarController (Juego nombresJugadores) {
    this.juego21 = nombresJugadores;
  }
  
  @FXML
  private ImageView imagMonedas;
  
  @FXML
  private TextField monedasApostadas;
  
  @FXML
  public void initialize() throws FileNotFoundException {
    FileInputStream input = new FileInputStream("src/veintiuno/imagenes/monedas.png");
    Image image = new Image(input);
    imagMonedas.setImage(image);
  }

  @FXML
  void continuar(ActionEvent event) {
    
    try {
      int monedas = Integer.parseInt(monedasApostadas.getText());
      
      if (monedas > 100) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("No tenéis suficientes monedas.");
        alert.showAndWait();
        monedasApostadas.setText("");
      }
      
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      FXMLLoader fxml = new FXMLLoader(this.getClass().getResource("/veintiuno/vistas/TiradaOrdenJugadores.fxml"));
      
      juego21.apostar(monedas);
      fxml.setController(new TiradaOrdenJugadoresController(juego21));
      
      VBox root = fxml.<VBox>load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      
      } catch (Exception e) {
        System.err.println("Error al cargar el archivo. " + e.getMessage());
      }
  }
}
