import java.nio.file.Path;
import java.util.*;

public class Juego {
    protected Tablero tablero;
    protected boolean tiempoAgotado = false;
    private final long tiempoInicio;
    private static final Path ARCHIVO_CONFIG = Path.of("C:\\Users\\Mario\\IdeaProjects\\LightsOut\\src\\config.txt");
    private final List<Integer> CONFIGURACIONES = new ArrayList<>();

    public Juego(){
        this.cargarConfiguraciones();

        if (this.CONFIGURACIONES.getFirst() == 0){
            aleatorizarTablero();
        } else{
            this.tablero = new Tablero(CONFIGURACIONES.getFirst(),CONFIGURACIONES.getFirst());
            inicializarTablero();
        }

        this.tiempoInicio = System.currentTimeMillis();

    }

    //Metodos propios

    private void cargarConfiguraciones(){
        FileHandling.lines(ARCHIVO_CONFIG).forEach(linea ->{
            this.CONFIGURACIONES.add(Integer.parseInt(linea.toString().split("=")[1]));
        });

        if (this.CONFIGURACIONES.getFirst() == 0){
            System.out.println("[!] El tamaño del tablero no está definido en el fichero de configuración, el tablero tendrá un tamaño aleatorio.");
        } else if (this.CONFIGURACIONES.getFirst() < 4 || this.CONFIGURACIONES.getFirst() > 9){
            System.out.println("[!] No se pudo inicializar el juego: el tamaño del tablero excede los límites (min: 4, max: 9).");
            System.exit(0);
        }
    }

    public void inicializarTablero(){
        Random rd = new Random();
        for (int i = 0; i < this.tablero.getFilas(); i++) {
            for (int j = 0; j < this.tablero.getColumnas(); j++) {
                this.tablero.tablero[i][j] = new Casilla(rd.nextInt(2) != 0);
                Casilla casillaActual = this.tablero.tablero[i][j];

                if(j == 0){
                    casillaActual.casillaIzquierda = null;
                } else if (j == this.tablero.getColumnas() - 1){
                    casillaActual.casillaDerecha = null;
                    casillaActual.casillaIzquierda = this.tablero.tablero[i][j-1];
                } else{
                    casillaActual.casillaIzquierda = this.tablero.tablero[i][j-1];
                    this.tablero.tablero[i][j-1].casillaDerecha = casillaActual;
                }

                if (i == 0){
                    casillaActual.casillaArriba = null;
                } else if(i == this.tablero.getFilas() - 1){
                    casillaActual.casillaAbajo = null;
                    casillaActual.casillaArriba = this.tablero.tablero[i-1][j];
                } else{
                    casillaActual.casillaArriba = this.tablero.tablero[i-1][j];
                    this.tablero.tablero[i-1][j].casillaAbajo = casillaActual;
                }
            }
        }
    }

    public void inicializarTablero(int casillasActivas){
        Random rd = new Random();
        for (int i = 0; i < this.tablero.getFilas(); i++) {
            for (int j = 0; j < this.tablero.getColumnas(); j++) {
                this.tablero.tablero[i][j] = new Casilla(false);
                normalizarCasilla(this.tablero.tablero[i][j], i, j);
            }
        }

        for (int i = 0; i < casillasActivas; i++) {
            this.tablero.tablero[rd.nextInt(this.tablero.getFilas())][rd.nextInt(this.tablero.getColumnas())].estado = true;
        }
    }

    private void aleatorizarTablero(){
        Random rd = new Random();
        Scanner sc = new Scanner(System.in);
        int tamaño = rd.nextInt(4,10);
        int casillasActivas = 0;

        this.tablero = new Tablero(tamaño,tamaño);

        System.out.println("[*] Introduce el número de casillas activas (0 para aleatorio) (min: 1, max: " + ((tamaño*tamaño)) + ")");

        try{
            casillasActivas = sc.nextInt();
        } catch(InputMismatchException e){
            System.out.println("[!] No es un número");
            System.exit(0);
        }

        if (casillasActivas == 0){
            inicializarTablero();
        } else if (casillasActivas < 0 || casillasActivas > tamaño*tamaño){
            System.out.println("[!] El número de casillas activas está fuera de los límites del tablero");
            System.exit(0);
        } else{
            inicializarTablero(casillasActivas);
        }
    }

    public boolean comprobarTablero(){
        for (int i = 0; i < this.tablero.getFilas(); i++) {
            for (int j = 0; j < this.tablero.getColumnas(); j++) {
                if(this.tablero.tablero[i][j].getEstado()){
                    return false;
                }
            }
        }
        return true;
    }

    public void normalizarCasilla(Casilla casilla, int fila, int columna){
        if(columna == 0){
            casilla.casillaIzquierda = null;
        } else if (columna == this.tablero.getColumnas() - 1){
            casilla.casillaDerecha = null;
            casilla.casillaIzquierda = this.tablero.tablero[fila][columna-1];
        } else{
            casilla.casillaIzquierda = this.tablero.tablero[fila][columna-1];
            this.tablero.tablero[fila][columna-1].casillaDerecha = casilla;
        }

        if (fila == 0){
            casilla.casillaArriba = null;
        } else if(fila == this.tablero.getFilas() - 1){
            casilla.casillaAbajo = null;
            casilla.casillaArriba = this.tablero.tablero[fila-1][columna];
        } else{
            casilla.casillaArriba = this.tablero.tablero[fila-1][columna];
            this.tablero.tablero[fila-1][columna].casillaAbajo = casilla;
        }
    }

    public int[] pedirCoordenadas(){

        while (true){
            Scanner sc = new Scanner(System.in);
            int[] coordenadas = null;
            String entrada;
            System.out.println("Introduce las coordenadas [fila columna] (e.g: 2 5)> ");
            entrada = sc.nextLine();

            if(entrada.equals("0 0")){
                System.out.println("[!] Saliendo...");
                System.exit(0);
            }

            try {
                coordenadas = Arrays.stream(entrada.split(" ")).mapToInt(Integer::parseInt).toArray();
            } catch (NumberFormatException e) {
                System.out.println("[!] Coordenadas no válidas.");
                continue;
            }
            if (coordenadas.length != 2){
                System.out.println("[!] Falta una coordenada.");
                continue;
            } else if(coordenadas[0] > this.tablero.getFilas() ||coordenadas[0] == 0 || coordenadas[1] > this.tablero.getColumnas() || coordenadas[1] == 0){
                System.out.println("[!] Coordenadas fuera de los límites del tablero.");
                continue;
            }
            return coordenadas;
        }
    }

    public void procesarCoordenadas(int[] coordenadas){
        Casilla target = this.tablero.tablero[coordenadas[0]-1][coordenadas[1]-1];
        target.setEstado(!target.getEstado());
        if (target.getCasillaIzquierda() != null){
            target.getCasillaIzquierda().setEstado(!target.getCasillaIzquierda().getEstado());
        }
        if (target.getCasillaDerecha() != null){
            target.getCasillaDerecha().setEstado(!target.getCasillaDerecha().getEstado());
        }
        if (target.getCasillaArriba() != null){
            target.getCasillaArriba().setEstado(!target.getCasillaArriba().getEstado());
        }
        if (target.getCasillaAbajo() != null){
            target.getCasillaAbajo().setEstado(!target.getCasillaAbajo().getEstado());
        }
    }

    public boolean turno(){
        int[] coordenadas = pedirCoordenadas();
        procesarCoordenadas(coordenadas);
        System.out.println(this.tablero);
        if(this.CONFIGURACIONES.get(1) != 0){
            comprobarTiempo();
        }
        return comprobarTablero();
    }

    public void comprobarTiempo(){

        if(System.currentTimeMillis() - this.tiempoInicio >= (this.CONFIGURACIONES.get(1) * 1000)){
            this.tiempoAgotado = true;
        }
    }
}