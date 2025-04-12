public class Casilla {
    protected boolean estado;
    protected Casilla casillaIzquierda;
    protected Casilla casillaDerecha;
    protected Casilla casillaArriba;
    protected Casilla casillaAbajo;

    public Casilla(boolean estado, Casilla casillaIzquierda, Casilla casillaDerecha, Casilla casillaArriba, Casilla casillaAbajo){
        this.estado = estado;
        this.casillaIzquierda = casillaIzquierda;
        this.casillaDerecha = casillaDerecha;
        this.casillaArriba = casillaArriba;
        this.casillaAbajo = casillaAbajo;
    }

    public Casilla(boolean estado){
        this.estado = estado;
    }

    //Getters y Setters
    public boolean getEstado(){
        return this.estado;
    }

    public Casilla getCasillaIzquierda(){
        return this.casillaIzquierda;
    }

    public Casilla getCasillaDerecha(){
        return this.casillaDerecha;
    }

    public Casilla getCasillaArriba(){
        return this.casillaArriba;
    }

    public Casilla getCasillaAbajo(){
        return this.casillaAbajo;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setCasillaIzquierda(Casilla casillaIzquierda) {
        this.casillaIzquierda = casillaIzquierda;
    }

    public void setCasillaDerecha(Casilla casillaDerecha) {
        this.casillaDerecha = casillaDerecha;
    }

    public void setCasillaArriba(Casilla casillaArriba) {
        this.casillaArriba = casillaArriba;
    }

    public void setCasillaAbajo(Casilla casillaAbajo) {
        this.casillaAbajo = casillaAbajo;
    }
}
