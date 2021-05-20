package veintiuno.controladores;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import veintiuno.Juego;
import veintiuno.Jugador;

public class TiradaOrdenJugadoresController {
  
  Juego juego21;
  ArrayList<TextField> numTirada = new ArrayList<TextField>();

  public TiradaOrdenJugadoresController(Juego juego21) {
    this.juego21 = juego21;
  }
  
  @FXML
  private VBox contenido;
  
  @FXML
  void lanzarDados(ActionEvent event) {
    juego21.ordenJugadores();
    int contador = 0;
    for (Jugador jugador : juego21) {
      contador++;
      Label nombreJugador = new Label(contador + ". " + jugador.getNombre());
      nombreJugador.setFont(new Font("Berlin Sans FB", 14));
      contenido.getChildren().add(nombreJugador);
    }
  }

  @FXML
  void continuar(ActionEvent event) {
    try {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      FXMLLoader fxml = new FXMLLoader(this.getClass().getResource("/veintiuno/vistas/TurnosJugadores.fxml"));
      
      fxml.setController(new TurnosJugadoresController(juego21, juego21.iterator()));
      
      VBox root = fxml.<VBox>load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      
      } catch (Exception e) {
        System.err.println("Error al cargar el archivo. " + e.getMessage());
      }
  }
}
