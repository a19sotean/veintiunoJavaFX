package veintiuno;

public class Jugador {
  
  private String nombre;
  private int monedas = 100;
  private Dado primerDado = new Dado();
  private Dado segundoDado = new Dado();
  private int puntuacionRonda = 0;
  private int puntuacionDados = 0;
  private int victorias = 0;
  
  // Constructor.
  public Jugador(String nombre, int monedas) throws MonedasNegativasException {
    setNombre(nombre);
    setMonedas(monedas);
  }

  public Jugador(String nombre2) {
    this.nombre = nombre2;
  }


  // Métodos.
  /**
   * @return the nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * @param nombre the nombre to set
   */
  private void setNombre(String nombre) {
    if (nombre.equals("")) {
      System.err.println("El nombre de un jugador no puede ser nulo.");
      return;
    }
    this.nombre = nombre;
  }

  /**
   * @return the monedas
   */
  public int getMonedas() {
    return monedas;
  }

  /**
   * @param monedas the monedas to set
   * @throws MonedasNegativasException 
   */
  public void setMonedas(int monedas) throws MonedasNegativasException {
    if (monedas <= 0) {
      throw new MonedasNegativasException ("No hay suficientes monedas.");
    }
    this.monedas = monedas;
  }
  
  /**
   * @return the puntuacionRonda
   */
  public int getPuntuacionRonda() {
    return puntuacionRonda;
  }

  /**
   * @param puntuacionRonda the puntuacionRonda to set
   */
  public void setPuntuacionRonda(int puntuacionRonda) {
    this.puntuacionRonda = puntuacionRonda;
  }

  /**
   * @return the puntuacionDados
   */
  public int getPuntuacionDados() {
    return puntuacionDados;
  }

  /**
   * @param puntuacionDados the puntuacionDados to set
   */
  public void setPuntuacionDados(int puntuacionDados) {
    this.puntuacionDados = puntuacionDados;
  }

  /**
   * @return the victorias
   */
  public int getVictorias() {
    return victorias;
  }

  /**
   * @param victorias the victorias to set
   */
  public void setVictorias(int victorias) {
    this.victorias = victorias;
  }

  // Tira un dado para ordenar a los jugadores en el inicio de la partida.
  public void tiradaOrden() {
    primerDado.tirarDado();
    setPuntuacionDados(primerDado.getCara());
  }
  
  // A partir de la puntuación 14 que se tira solo un dado.
  public void tiradaSimple() {
    primerDado.tirarDado();
    setPuntuacionDados(primerDado.getCara());
    setPuntuacionRonda(getPuntuacionRonda() + getPuntuacionDados());
  }
  
  // Antes de la puntuación 14 que se tiran 2 dados.
  public void tiradaCompleta() {
    primerDado.tirarDado();
    segundoDado.tirarDado();
    setPuntuacionDados(primerDado.getCara() + segundoDado.getCara());
    setPuntuacionRonda(getPuntuacionRonda() + getPuntuacionDados());
  }
  
  public void partidasGanadas() {
    victorias ++;
  }
  
  public void borrarPuntuaciones() throws MonedasNegativasException {
    setPuntuacionRonda(0);
    setPuntuacionDados(0);
    setMonedas(100);
  }
  
  public void apostar(int apuesta) throws MonedasNegativasException, ApuestaNegativaException {
    if (apuesta < 0) {
      throw new ApuestaNegativaException ("La apuesta no puede ser negativa");
    }
    setMonedas(getMonedas() - apuesta);
  }

  @Override
  public String toString() {
    return "\nNombre: " + nombre + "\nMonedas: " + monedas + "\nVictorias: " + victorias + "\n";
  }
}