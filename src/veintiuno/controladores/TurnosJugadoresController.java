package veintiuno.controladores;

import java.util.Iterator;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import veintiuno.Juego;
import veintiuno.Jugador;

public class TurnosJugadoresController {

  Juego juego21;
  Iterator<Jugador> jugadores;
  Jugador jugadorActual;

  @FXML
  private TextField jugador;

  @FXML
  private TextField resultado;

  @FXML
  private TextField puntosAcumulados;
  
  public TurnosJugadoresController(Juego juego21, Iterator<Jugador> jugadores) {
    this.juego21 = juego21;
    this.jugadores = jugadores;
    this.jugadorActual = jugadores.next();
  }
  
  @FXML
  public void initialize() {
    jugador.setText(jugadorActual.getNombre());
  }

  @FXML
  void tirarDado(ActionEvent event) {
    if (jugadorActual.getPuntuacionRonda() <= 14) {
      jugadorActual.tiradaCompleta();
    } else {
      jugadorActual.tiradaSimple();
    }
    resultado.setText(jugadorActual.getPuntuacionDados() + "");
    puntosAcumulados.setText(jugadorActual.getPuntuacionRonda() + "");
    
    if (jugadorActual.getPuntuacionRonda() > 21) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Has perdido");
      alert.setContentText("Tus puntos han superado 21, lo siento pero has perdido ;c");
      Optional<ButtonType> confirmar = alert.showAndWait();
      if (confirmar.get() == ButtonType.OK) {
        dejarDeTirar(event);
      }
    }
  }

  @FXML
  void dejarDeTirar(ActionEvent event) {
    try {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      FXMLLoader fxml;
      
      if (jugadores.hasNext()) {
        fxml = new FXMLLoader(this.getClass().getResource("/veintiuno/vistas/TurnosJugadores.fxml"));
        fxml.setController(new TurnosJugadoresController(juego21, jugadores));
      } else {
        fxml = new FXMLLoader(this.getClass().getResource("/veintiuno/vistas/Ganador.fxml"));
        fxml.setController(new GanadorController(juego21));
      }
      
      VBox root = fxml.<VBox>load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      
      } catch (Exception e) {
        System.err.println("Error al cargar el archivo. " + e.getMessage());
      }
  }

}
