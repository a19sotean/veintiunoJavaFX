package veintiuno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class Juego implements Iterable<Jugador> {
  Scanner s = new Scanner(System.in);
  public ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
  public ArrayList<Jugador> jugadoresOrdenados = new ArrayList<Jugador>();
  private int numeroJugadores;
  private int banca;

  public Juego(int numeroJugadores) throws NumeroJugadoresErroneosException {
    setNumeroJugadores(numeroJugadores);
    setBanca(banca);
  }

  /**
   * @return the numeroJugadores
   */
  public int getNumeroJugadores() {
    return numeroJugadores;
  }

  /**
   * @param numeroJugadores the numeroJugadores to set
   */
  private void setNumeroJugadores(int numeroJugadores) throws NumeroJugadoresErroneosException {
    if (numeroJugadores < 2) {
      throw new NumeroJugadoresErroneosException("Los jugadores no pueden ser menos de 2.");
    }
    this.numeroJugadores = numeroJugadores;
  }

  /**
   * @return the banca
   */
  public int getBanca() {
    return banca;
  }

  /**
   * @param banca the banca to set
   */
  public void setBanca(int banca) {
    this.banca = banca;
  }

  /**
   * Método que añade a los jugadores.
   * 
   * @param nombre
   * @param monedas
   * @throws NumeroJugadoresErroneosException
   * @throws MonedasNegativasException 
   */
  public void annadirJugador(String nombre, int monedas) throws NumeroJugadoresErroneosException, MonedasNegativasException {
    Jugador jugador = new Jugador(nombre, monedas);
    jugadores.add(jugador);
  }

  /**
   * Método para apostar las monedas antes de que comience la partida.
   * Todos los jugadores apostarán las mismas monedas.
   * 
   * @param monedas
   * @throws JugadorNoExisteException
   * @throws MonedasNegativasException 
   * @throws ApuestaNegativaException 
   */
  public void apostar(int monedas) throws JugadorNoExisteException, MonedasNegativasException, ApuestaNegativaException {
    for (Jugador j : jugadores) {
      j.apostar(monedas);
    }
    setBanca(monedas * jugadores.size());
  }
  
  public void borrarPuntuaciones() {
    for (Jugador j : jugadores) {
      j.borrarPuntuaciones();
    }
  }

  /**
   * Método para conseguir el el jugador.
   * 
   * @param nombre
   * @return
   * @throws JugadorNoExisteException
   */
  public Jugador get(String nombre) throws JugadorNoExisteException {
    try {
      return jugadores.get(jugadores.indexOf(new Jugador(nombre)));
    } catch (IndexOutOfBoundsException e) {
      throw new JugadorNoExisteException("El jugador no existe.");
    }
  }

  /**
   * Método que ordena a los jugadores antes de comenzar la partida.
   */
  public void ordenJugadores() {
    jugadoresOrdenados.clear();
    for (Jugador jugador: jugadores) {
      jugador.borrarPuntuaciones();
      jugador.tiradaOrden();
      jugadoresOrdenados.add(jugador);
      //System.out.println(jugador.getNombre() + " ha obtenido: " + jugador.getPuntuacionDados());
    }
    // Ordena según la puntuación que sacan los dados, si hay empate, va primero el que ha entrado primero a jugar.
    Collections.sort(jugadoresOrdenados, (j1, j2) -> Integer.compare(j2.getPuntuacionDados(), j1.getPuntuacionDados()));
  }

  public void jugarRonda() throws MonedasNegativasException {
    for (Jugador jugador : jugadoresOrdenados) {
      jugador.borrarPuntuaciones();
    }
    int puntuacionMax = 0;

    for (int i = 0; i < jugadoresOrdenados.size(); i++) {
      System.out.println("\nEs el turno de " + jugadoresOrdenados.get(i).getNombre());
      jugarIndividual(jugadoresOrdenados.get(i));

      if (jugadoresOrdenados.get(i).getPuntuacionRonda() > puntuacionMax && jugadoresOrdenados.get(i).getPuntuacionRonda() <= 21) {
        puntuacionMax = jugadoresOrdenados.get(i).getPuntuacionRonda();
      }
    }


    System.out.println();

    // Ganador
    int ganador = ganador();
    System.out.println("\nEl jugador ganador es: " + jugadoresOrdenados.get(ganador).getNombre());
    System.out.println("\nLa calificación ha quedado así: ");
    jugadoresOrdenados.get(ganador).partidasGanadas();
    jugadoresOrdenados.get(ganador).setMonedas(jugadoresOrdenados.get(ganador).getMonedas() + banca);
    System.out.println(jugadoresOrdenados);
  }

  public int jugarIndividual(Jugador jugador) {
    System.out.println();
    jugador.tiradaCompleta();
    System.out.println("El resultado es: " + jugador.getPuntuacionDados());
    System.out.println("Puntos acumulados: " + jugador.getPuntuacionRonda());

    while (jugador.getPuntuacionRonda() < 14) {
      System.out.print("¿Quiéres hacer otra tirada? (s/n) ");
      String tirada = s.next();
      if (tirada.toUpperCase().compareTo("S") == 0) {
        jugador.tiradaCompleta();
        System.out.println("El resultado es: " + jugador.getPuntuacionDados());
        System.out.println("Puntos acumulados: " + jugador.getPuntuacionRonda());
      } else {
        System.out.print("La puntuación final es: " + jugador.getPuntuacionRonda());
        return jugador.getPuntuacionRonda();
      }
    }
    while (jugador.getPuntuacionRonda() <= 21) {
      if (jugador.getPuntuacionRonda() == 21) {
        System.out.println("¡Puntuación máxima obtenida, felicidades!");
        return jugador.getPuntuacionRonda();
      }
      System.out.println("\nAhora solo tirarás con un dado.");
      System.out.print("¿Quiéres hacer otra tirada? (s/n) ");
      String tirada = s.next();

      if (tirada.toUpperCase().compareTo("S") == 0) {
        jugador.tiradaSimple();
        System.out.println("El resultado es: " + jugador.getPuntuacionDados());
        System.out.println("Puntos acumulados: " + jugador.getPuntuacionRonda());
      } else if (tirada.toUpperCase().compareTo("N") == 0) {
        System.out.print("La puntuación final es: " + jugador.getPuntuacionRonda());
        return jugador.getPuntuacionRonda();
      } else {
        System.err.println("Debes indicar s en caso de sí y n en caso de no.");
      }
    }
    System.out.println("\nTe has pasado de 21, has perdido :c");
    return jugador.getPuntuacionRonda();
  }

  public int ganador() {
    for (int i = 0; i < jugadoresOrdenados.size(); i++) {
      if (jugadoresOrdenados.get(i).getPuntuacionRonda() == 21) {
        return i;
      }
    }
    int jugadorAGanar = 0;
    int puntuacionMaxima = 0;
    for (int i = 0; i < jugadoresOrdenados.size(); i++) {
      if (jugadoresOrdenados.get(i).getPuntuacionRonda() > puntuacionMaxima && jugadoresOrdenados.get(i).getPuntuacionRonda() < 21) {
        puntuacionMaxima = jugadoresOrdenados.get(i).getPuntuacionRonda();
        jugadorAGanar = i;
      }
    }

    return jugadorAGanar;
  }

  @Override
  public String toString() {
    return "Jugadores " + jugadores;
  }

  @Override
  public Iterator<Jugador> iterator() {

    return jugadoresOrdenados.iterator();
  }

}
