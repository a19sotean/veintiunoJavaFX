package veintiuno.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import veintiuno.Juego;
import veintiuno.Jugador;
import veintiuno.MonedasNegativasException;
import veintiuno.NumeroJugadoresErroneosException;

public class JugadoresController {

  @FXML
  private TextField numJugadores;

  @FXML
  private FlowPane contenido;
  
  Juego juego21;

  ArrayList<TextField> nombres = new ArrayList<TextField>();

  @FXML
  void añadir(ActionEvent event) {
    try {
      int jugadores = Integer.parseInt(numJugadores.getText());
      juego21 = new Juego(jugadores);
      if (jugadores >= 2) {
        for (int i = 0; i < jugadores; i++) {
          TextField ponerNombre = new TextField();
          ponerNombre.setFont(new Font("Berlin Sans FB", 14));
          nombres.add(ponerNombre);
          contenido.getChildren().add(ponerNombre);
        }
      }

    } catch (NumeroJugadoresErroneosException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText("Los jugadores no pueden ser inferiores a 2.");
      alert.showAndWait();
      numJugadores.setText("");
      
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText("No has introducido un número.");
      alert.showAndWait();
      numJugadores.setText("");
    }
  }

  @FXML
  void continuar(ActionEvent event) {
    try {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      FXMLLoader fxml = new FXMLLoader(this.getClass().getResource("/veintiuno/vistas/MonedasApostar.fxml"));

      nombres.stream().forEach(nombre -> {
        try {
          juego21.annadirJugador(nombre.getText(), 100);
          
        } catch (NumeroJugadoresErroneosException e) {
          
        } catch (MonedasNegativasException e) {
        }
      });
      fxml.setController(new MonedasApostarController(juego21));

      VBox root = fxml.<VBox>load();
      Scene scene = new Scene(root);
      stage.setScene(scene);

    } catch (Exception e) {
      System.err.println("Error al cargar el archivo. " + e.getMessage());
    }
  }

}
