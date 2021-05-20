module veintiunojavafx {
  requires transitive javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  requires javafx.base;

  opens veintiuno to javafx.fxml;
  opens veintiuno.controladores to javafx.fxml;
  
  exports veintiuno;
  exports veintiuno.controladores;
}