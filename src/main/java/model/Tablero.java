package model;

import javafx.scene.control.Button;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Clase que maneja la lógica del juego del gato
public class Tablero {
    // Matriz que representa el tablero 3x3
    private String[][] tablero;
    
    // Contador de movimientos realizados
    private int movimientos;
    
    // Los dos jugadores
    private Jugador jugador1;
    private Jugador jugador2;
    
    // Jugador que tiene el turno actual
    private Jugador jugadorActual;

    // Constructor
    public Tablero(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugadorActual = jugador1; // El jugador 1 siempre empieza
        this.tablero = new String[3][3];
        this.movimientos = 0;
        limpiarTablero();
    }

    private void limpiarTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                tablero[fila][columna] = "";
            }
        }
        movimientos = 0;
    }

    public boolean hacerMovimiento(int fila, int columna) {
        // Verificar que la posición sea válida y esté vacía
        if (fila >= 0 && fila < 3 && columna >= 0 && columna < 3 && 
            tablero[fila][columna].isEmpty()) {
            tablero[fila][columna] = jugadorActual.getSimbolo();
            movimientos++;
            return true;
        }
        return false;
    }

    public void cambiarTurno() {
        if (jugadorActual == jugador1) {
            jugadorActual = jugador2;
        } else {
            jugadorActual = jugador1;
        }
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public String verificarGanador() {
        // Verificar filas
        for (int fila = 0; fila < 3; fila++) {
            if (!tablero[fila][0].isEmpty() && 
                tablero[fila][0].equals(tablero[fila][1]) && 
                tablero[fila][0].equals(tablero[fila][2])) {
                return tablero[fila][0];
            }
        }


        for (int columna = 0; columna < 3; columna++) {
            if (!tablero[0][columna].isEmpty() && 
                tablero[0][columna].equals(tablero[1][columna]) && 
                tablero[0][columna].equals(tablero[2][columna])) {
                return tablero[0][columna];
            }
        }

        if (!tablero[0][0].isEmpty() && 
            tablero[0][0].equals(tablero[1][1]) && 
            tablero[0][0].equals(tablero[2][2])) {
            return tablero[0][0];
        }

        if (!tablero[0][2].isEmpty() && 
            tablero[0][2].equals(tablero[1][1]) && 
            tablero[0][2].equals(tablero[2][0])) {
            return tablero[0][2];
        }

        return null; // No hay ganador
    }

    public boolean esEmpate() {
        return movimientos == 9 && verificarGanador() == null;
    }

    public boolean juegoTerminado() {
        return verificarGanador() != null || esEmpate();
    }

    public void reiniciarJuego() {
        limpiarTablero();
        jugadorActual = jugador1; // El jugador 1 siempre empieza
    }

    public String[][] getTablero() {
        return tablero;
    }

    public int getMovimientos() {
        return movimientos;
    }

    public Jugador obtenerJugadorPorSimbolo(String simbolo) {
        if (jugador1.getSimbolo().equals(simbolo)) {
            return jugador1;
        } else if (jugador2.getSimbolo().equals(simbolo)) {
            return jugador2;
        }
        return null;
    }

}
