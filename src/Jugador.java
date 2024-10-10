import java.util.ArrayList;

public class Jugador <E extends ItipoPieza> {
    //private ArrayList<E> piezasVivas;
    private class NodePieza{
        public E pieza;
        public NodePieza seguent;
        public NodePieza(E pieza, NodePieza seguent)
        {
            this.pieza = pieza;
            this.seguent = seguent;
        }
    }
    private NodePieza piezasVivas; // seqüència enllaçada de peces amb capçalera
// no té sentit que el llistat de peces sigui null, com a mínim tindrà una peça

    public Jugador(ArrayList<E> piezasInicials){

        piezasVivas = new NodePieza(null,null);

        // la cabeza que es al referencia
        NodePieza aux = piezasVivas;

        for(int i=0;i<piezasInicials.size();i++){

             aux.seguent = new NodePieza(piezasInicials.get(i),null);
             aux = aux.seguent;

        }
    }

    public ArrayList<E> getPiezasVivas(){
        ArrayList array = new ArrayList<>();
        NodePieza aux = piezasVivas.seguent;
        while(aux !=null){
            array.add(aux.pieza);
            aux = aux.seguent;
        }
        return array;
    }

    private E buscarenPoscions(int Fila, int Columna){
        NodePieza aux = piezasVivas.seguent;
        while (aux != null) {
            if (Fila == aux.pieza.getFila() && Columna == aux.pieza.getColumna()) {
                return aux.pieza;
            }
            aux = aux.seguent;
        }
        throw new RuntimeException("La llista està buida solo tiene la cabezera");
    }



    public void moverPieza(int columnaAnterior, int filaAnterior, int nuevaColumna, int nuevaFila) throws Exception {
        E buscar = buscarenPoscions(filaAnterior,columnaAnterior); //Busca la peça a moure en la llista de peces del mateix Jugador
        if(buscar != null){ //Si la troba
            if(this.repetit(getPiezasVivas(),nuevaColumna,nuevaFila)) //Si és del mateix color
                throw new Exception("Es una peça del mateix color, no es pot fer el moviment.");
            buscar.setPosicion(nuevaFila,nuevaColumna); //Si no és del mateix color, canvia els atributs de la peça moguda en la llista
        } else {
            throw new Exception();
        }
    }

    public boolean eliminarPiezaEnPosicion(int columna, int fila) throws Exception {
        if(columna < 0 || fila < 0){
            return false;
        }
        else {
            NodePieza aux = piezasVivas;
            while (aux.seguent != null) {
                if (aux.seguent.pieza.fiJoc()) {

                    throw new FiJocException("El rey esta mort");

                }
                else if (fila == aux.seguent.pieza.getFila() && columna == aux.seguent.pieza.getColumna()) {
                    aux.seguent = aux.seguent.seguent;
                    return true;

                }
                else {
                    aux = aux.seguent;
                }
            }
        }
        return false;
    }

    public boolean reySigueVivo(char rey){
        if (isEmpty()) {
            for (int i = 0; i < piezasVivas.size(); i++) {
                if (piezasVivas.get(i).getTipus() == rey) {
                    return true;
                }
            }
        }
        return false;
    }

    //El mètode verifica si en l'ArrayList donat hi ha algun de repetit a partir de la posició
    public boolean repetit(ArrayList<E> a, int col, int fil) {
        for (int i = 0; i < a.size(); i++) {
            Pieza p = (Pieza) a.get(i);
            if (p.getFila() == fil && p.getColumna() == col) {
                return true;
            }
        }
        return false;
    }
    //El mètode verifica si la llista és buida o no
    public boolean isEmpty() {
        return !piezasVivas.isEmpty();
    }


    public String toString(){
        if (isEmpty()) {
            String r = " ";
            for (int i = 0; i < piezasVivas.size(); i++) {
                r += piezasVivas.get(i).toString() + ", ";
            }
            return r;
        }
        return "La llista està buida en toString de Jugador";
    }
}
