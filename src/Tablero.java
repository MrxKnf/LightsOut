public class Tablero {
    private final int filas;
    private final int columnas;
    public Casilla[][] tablero;

    public Tablero(int filas, int columnas){
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = new Casilla[this.filas][this.columnas];
    }

    //Getters y Setters
    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    //Metodos propios
    public void chetillo() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                this.tablero[i][j].estado = false;
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if(tablero[i][j].estado){
                    sb.append("● ");
                } else{
                    sb.append("○ ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
