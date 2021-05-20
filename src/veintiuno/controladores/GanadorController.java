package veintiuno.controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import veintiuno.Juego;
import veintiuno.Jugador;
import veintiuno.MonedasNegativasException;

public class GanadorController {

  Juego juego21;
  
  @FXML
  private ImageView imagFuegos;
  
  @FXML
  private Label ganador;

  @FXML
  private VBox clasificacion;
  
  public GanadorController(Juego juego21) {
    this.juego21 = juego21;
  }

  @FXML
  public void initialize() throws FileNotFoundException {
    FileInputStream input = new FileInputStream("src/veintiuno/imagenes/fuegosVictoria.png");
    Image image = new Image(input);
    imagFuegos.setImage(image);
    
    Jugador jugadorGanador = juego21.jugadoresOrdenados.get(juego21.ganador());
    ganador.setText(jugadorGanador.getNombre());
    try {
      jugadorGanador.setMonedas(jugadorGanador.getMonedas() + juego21.getBanca());
      
    } catch (MonedasNegativasException e) {
    }
    
    juego21.jugadoresOrdenados.stream().filter(jugador -> jugador.getPuntuacionRonda() <= 21).sorted((j1, j2) -> Integer.compare(j2.getPuntuacionRonda(), j1.getPuntuacionRonda())).forEach(jugador -> {
      Label labelJugador = new Label(jugador.getNombre() + " -> " + jugador.getPuntuacionRonda());
      labelJugador.setFont(new Font("Berlin Sans FB", 13));
      
      Label labelMonedas = new Label("monedas: " + jugador.getMonedas());
      labelMonedas.setFont(new Font("Berlin Sans FB", 13));
      
      clasificacion.getChildren().addAll(labelJugador, labelMonedas);
    });
  }

  @FXML
  void jugarOtraRonda(ActionEvent event) {
    try {
      juego21.borrarPuntuaciones();
      
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      FXMLLoader fxml = new FXMLLoader(this.getClass().getResource("/veintiuno/vistas/MonedasApostar.fxml"));
      fxml.setController(new TiradaOrdenJugadoresController(juego21));

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
