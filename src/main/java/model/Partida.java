package model;

public class Partida {

        public String jugador1;
        public String jugador2;
        public int puntaje1;
        public int puntaje2;
        public int empate;
        public String fechaHora;
        public String estadoJson;

        public Partida(String jugador1, String jugador2, int puntaje1, int puntaje2, int empate, String fechaHora, String estadoJson) {
            this.jugador1 = jugador1;
            this.jugador2 = jugador2;
            this.puntaje1 = puntaje1;
            this.puntaje2 = puntaje2;
            this.empate = empate;
            this.fechaHora = fechaHora;
            this.estadoJson = estadoJson;
        }


}

