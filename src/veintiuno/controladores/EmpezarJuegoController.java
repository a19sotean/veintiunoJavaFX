package veintiuno.controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmpezarJuegoController {

  @FXML
  private ImageView imagDados;

  @FXML
  public void initialize() throws FileNotFoundException {
    FileInputStream input = new FileInputStream("src/veintiuno/imagenes/dadosInicio.png");
    Image image = new Image(input);
    imagDados.setImage(image);
  }

  @FXML
  void empezarJuego(ActionEvent event) throws IOException {
    try {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      FXMLLoader fxml =
          new FXMLLoader(this.getClass().getResource("/veintiuno/vistas/Jugadores.fxml"));
      VBox root = fxml.<VBox>load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
    } catch (Exception e) {
      System.err.println("Error al cargar el archivo. " + e.getMessage());
    }
  }

  @FXML
  void salir(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Salir");
    alert.setContentText("¿Seguro que quieres salir?");
    Optional<ButtonType> resultado = alert.showAndWait();
    if (resultado.get() == ButtonType.OK) {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      stage.close();
    }
  }
}
