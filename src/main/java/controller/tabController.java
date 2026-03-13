package controller;

import application.App;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Jugador;
import model.Tablero;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import model.Partida;
import Repositorio.Repositorio;
import java.net.URL;
import java.util.ResourceBundle;

public class tabController{


    private Tablero tablero;
    private Jugador jugador1;
    private Jugador jugador2;

    private Button[][] botonesTablero;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane gridTablero;

    @FXML
    private Label lblCierre;

    @FXML
    private Label lblnombrejugadordos;

    @FXML
    private Label lblnombrejugadoruno;

    @FXML
    private Label lblpuntajejugadordos;

    @FXML
    private Label lblpuntajejugadoruno;

    @FXML
    private Label lblturno;

    @FXML
    private ImageView imgJugador1;

    @FXML
    private ImageView imgJugador2;

    @FXML
    private Button btnJuegoNuevo;

    @FXML
    private Button btnVolver;

    @FXML
    void click(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void enter(MouseEvent event) {
        lblCierre.setStyle("-fx-text-fill: red;");
    }

    @FXML
    void exited(MouseEvent event) {
        lblCierre.setStyle("-fx-text-fill: white;");
    }

    @FXML
    void juegoNuevo(javafx.event.ActionEvent event) {
        reiniciarJuego();
    }

    @FXML
    void volver(javafx.event.ActionEvent event) {

        final Node node = (Node) event.getSource();
        final Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        cargarPantallaPrincipal();
    }

    private void cargarPantallaPrincipal() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 500, 400);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.centerOnScreen();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        botonesTablero = new Button[3][3];
        

        gridTablero.setHgap(10);
        gridTablero.setVgap(10);
        gridTablero.setAlignment(Pos.CENTER);

        crearBotonesTablero();
    }

    private void crearBotonesTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                Button boton = new Button();
                boton.setFont(Font.font("Arial", FontWeight.BOLD, 40));
                boton.setPrefSize(80, 80);
                boton.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-border-color: rgba(255,255,255,0.8); -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);");
                final int f = fila;
                final int c = columna;
                boton.setOnAction(actionEvent -> {
                    if (boton.getText().isEmpty() && !tablero.juegoTerminado()) {
                        hacerMovimiento(f, c, boton);
                    }
                });
                boton.setOnMouseEntered(e -> {
                    if (boton.getText().isEmpty()) {
                        boton.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-text-fill: white; -fx-border-color: rgba(255,255,255,1); -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 2);");
                    }
                });
                boton.setOnMouseExited(e -> {
                    if (boton.getText().isEmpty()) {
                        boton.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-border-color: rgba(255,255,255,0.8); -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);");
                    }
                });
                botonesTablero[fila][columna] = boton;
                gridTablero.add(boton, columna, fila);
            }
        }
    }

    private void hacerMovimiento(int fila, int columna, Button boton) {
        if (tablero.hacerMovimiento(fila, columna)) {

            boton.setText(tablero.getJugadorActual().getSimbolo());

            String simbolo = tablero.getJugadorActual().getSimbolo();
            if (simbolo.equals("X")) {
                boton.setStyle("-fx-background-color: rgba(255,107,107,0.3); -fx-text-fill: #ff6b6b; -fx-border-color: #ff6b6b; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(255,107,107,0.4), 5, 0, 0, 2); -fx-font-weight: bold;");
            } else {
                boton.setStyle("-fx-background-color: rgba(102,126,234,0.3); -fx-text-fill: #667eea; -fx-border-color: #667eea; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(102,126,234,0.4), 5, 0, 0, 2); -fx-font-weight: bold;");
            }
            String ganador = tablero.verificarGanador();
            if (ganador != null) {
                manejarVictoria(ganador);
                return;
            }
            if (tablero.esEmpate()) {
                manejarEmpate();
                return;
            }
            tablero.cambiarTurno();
            actualizarTurno();

            if (tablero.getJugadorActual().isComputadora()) {
                hacerMovimientoComputadora();
            }
        }
    }

    private void hacerMovimientoComputadora() {

        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                if (botonesTablero[fila][columna].getText().isEmpty()) {
                    hacerMovimiento(fila, columna, botonesTablero[fila][columna]);
                    return;
                }
            }
        }
    }

    private void manejarVictoria(String simboloGanador) {
        Jugador ganador = tablero.obtenerJugadorPorSimbolo(simboloGanador);
        ganador.incrementarVictoria();
        actualizarPuntajes();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Victoria!");
        alert.setHeaderText(null);
        alert.setContentText("¡" + ganador.getNombre() + " ha ganado!");
        alert.showAndWait();
        
        deshabilitarTablero();
    }

    private void manejarEmpate() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Empate!");
        alert.setHeaderText(null);
        alert.setContentText("El juego ha terminado en empate.");
        alert.showAndWait();
        
        deshabilitarTablero();
    }

    private void deshabilitarTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                botonesTablero[fila][columna].setDisable(true);
            }
        }
    }

    private void habilitarTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                botonesTablero[fila][columna].setDisable(false);
            }
        }
    }

    private void actualizarTurno() {
        Jugador jugadorActual = tablero.getJugadorActual();
        lblturno.setText("Turno: " + jugadorActual.getNombre());

        try {
            if (jugadorActual == jugador1) {
                imgJugador1.setImage(new Image(getClass().getResourceAsStream("/images/JugadorAuxillar.png")));
                imgJugador2.setImage(new Image(getClass().getResourceAsStream("/images/JugadorCirculo.png")));
            } else {
                imgJugador1.setImage(new Image(getClass().getResourceAsStream("/images/JugadorEquis.png")));
                imgJugador2.setImage(new Image(getClass().getResourceAsStream("/images/JugadorAuxillar.png")));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar imágenes: " + e.getMessage());
        }
    }

    private void actualizarPuntajes() {
        lblpuntajejugadoruno.setText(String.valueOf(jugador1.getWins()));
        lblpuntajejugadordos.setText(String.valueOf(jugador2.getWins()));
    }

    private void reiniciarJuego() {

        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                botonesTablero[fila][columna].setText("");
                botonesTablero[fila][columna].setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-border-color: rgba(255,255,255,0.8); -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);");
            }
        }
        tablero.reiniciarJuego();
        habilitarTablero();
        actualizarTurno();
    }

    void inicio(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.tablero = new Tablero(jugador1, jugador2);

        lblnombrejugadoruno.setText(jugador1.getNombre());
        lblnombrejugadordos.setText(jugador2.getNombre());

        actualizarPuntajes();
        configurarImagenesIniciales();
        actualizarTurno();
    }

    private void configurarImagenesIniciales() {
        try {
            if (jugador1.getSimbolo().equals("X")) {
                imgJugador1.setImage(new Image(getClass().getResourceAsStream("/images/JugadorEquis.png")));
                imgJugador2.setImage(new Image(getClass().getResourceAsStream("/images/JugadorCirculo.png")));
            } else {
                imgJugador1.setImage(new Image(getClass().getResourceAsStream("/images/JugadorCirculo.png")));
                imgJugador2.setImage(new Image(getClass().getResourceAsStream("/images/JugadorEquis.png")));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar imágenes iniciales: " + e.getMessage());
        }
    }
    public MenuBar MenuGuardar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Opciones");
        MenuItem guardar = new MenuItem("Guardar");

        guardar.setOnAction(e -> guardarPartidaEnBD());

        menu.getItems().add(guardar);
        menuBar.getMenus().add(menu);
        return menuBar;
    }

    private void guardarPartidaBD() {

        String estadoJson = gson.toJson(tablero.getTablero());

        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        String fechaHora = ahora.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        model.Partida partida = new model.Partida(
                jugador1.getNombre(),
                jugador2.getNombre(),
                jugador1.getVictorias(),
                jugador2.getVictorias(),
                tablero.esEmpate() ? 1 : 0,
                fechaHora,
                estadoJson
        );

        try {
            Repositorio.Repositorio repo = new Repositorio("tic_tac_toe.db");
            repo.guardarPartida(partida);
            mostrarAlerta("Guardado", "La partida se ha guardado correctamente.");
        } catch (Exception ex) {
            mostrarAlerta("Error", "No se pudo guardar la partida.");
            ex.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}