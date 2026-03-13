package Repositorio;
import model.Partida;
import java.sql.*;

public class Repositorio{

        private String dbPath;

        public Repositorio(String dbPath) {
            this.dbPath = dbPath;
        }

        public void guardarPartida(Partida partida) throws SQLException {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            String sql = "CREATE TABLE IF NOT EXISTS partidas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "jugador1," +
                    "jugador2," +
                    "puntaje1," +
                    "puntaje2," +
                    "empate," +
                    "fechaHora,";
            conn.createStatement().execute(sql);

            sql = "INSERT INTO partidas (jugador1, jugador2, puntaje1, puntaje2, empate, fechaHora, estadoJson) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, partida.jugador1);
            stmt.setString(2, partida.jugador2);
            stmt.setInt(3, partida.puntaje1);
            stmt.setInt(4, partida.puntaje2);
            stmt.setInt(5, partida.empate);
            stmt.setString(6, partida.fechaHora);
            stmt.setString(7, partida.estadoJson);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        }
}

