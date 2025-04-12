public class Main {
    public static void main(String[] args) {
        Juego juego = new Juego();
        boolean ganado = false;

        while(!juego.tiempoAgotado && !ganado){
            if(juego.turno()){
                ganado = true;
            }
        }

        if (juego.tiempoAgotado){
            System.out.println("[*] El tiempo se ha agotado.");
        } else{
            System.out.println("[★]Has ganado!");
        }
    }
}
