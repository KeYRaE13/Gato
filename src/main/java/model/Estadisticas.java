package model;

public class Estadisticas {
    private int victoriasX;
    private int victoriasO;
    private int empates;

    public void incrementarVictoriaX() { victoriasX++; }
    public void incrementarVictoriaO() { victoriasO++; }
    public void incrementarEmpate() { empates++; }

    public int getVictoriasX() { return victoriasX; }
    public int getVictoriasO() { return victoriasO; }
    public int getEmpates() { return empates; }
}

