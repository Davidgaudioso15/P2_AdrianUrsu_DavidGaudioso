import java.util.ArrayList;

public class Jugador <E extends ItipoPieza> {
    private class NodePieza{
        public E pieza;
        public NodePieza seguent;
        public NodePieza(E pieza, NodePieza seguent)
        {
            this.pieza = pieza;
            this.seguent = seguent;
        }
    }

    private NodePieza piezasVivas; // seqüència enllaçada de peces SENSE capçalera
// piezasVivas sempre serà un nodePieza
// en el nostre cas piezasVivas no pot ser null, com a mínim tindrem una peça

    public Jugador(ArrayList<E> piezasInicials){ //COMRPOBADO BIEN

        piezasVivas = new NodePieza(null,null);

        // la cabeza que es al referencia
        NodePieza aux = piezasVivas;
        for(int i=0;i<piezasInicials.size();i++){
             aux.seguent = new NodePieza(piezasInicials.get(i),null);
             aux = aux.seguent;
        }
        piezasVivas = piezasVivas.seguent; //NO BORRAR
    }

    public ArrayList<E> getPiezasVivas(){ //EN TEORIA FUNCIONA, NO COMPROBADO
        ArrayList array = new ArrayList<>();
        NodePieza aux = piezasVivas;
        while(aux !=null){
            array.add(aux.pieza);
            aux = aux.seguent;
        }
        return array;
    }

    private E buscarenPoscions(int fila, int columna){ //EN TEORIA FUNCIONA, NO COMPROBADO
        NodePieza aux = piezasVivas;
        while (aux != null) {
            //System.out.println("BUSCO UNA PIEZA EN LA FILA = " + fila + " Y COLUMNA = " + columna + ",  Y HE ENCONTRADO DE MOMENTO LA PIEZA EN FILA = "+ aux.pieza.getFila() + " Y COLUMNA = " + aux.pieza.getColumna());
            if (fila == aux.pieza.getFila() && columna == aux.pieza.getColumna()) {
                return aux.pieza;
            }
            aux = aux.seguent;
        }
        return null;
    }

    public void moverPieza(int columnaAnterior, int filaAnterior, int nuevaColumna, int nuevaFila) throws Exception { //EN TEORIA FUNCIONA, NO COMPROBADO
        E buscar = buscarenPoscions(filaAnterior,columnaAnterior); //Busca la peça a moure en la llista de peces del mateix Jugador
        if(buscar != null){ //Si la troba
            if(this.repetit(getPiezasVivas(),nuevaColumna,nuevaFila)) //Si és del mateix color
                throw new Exception("Es una peça del mateix color, no es pot fer el moviment.");
            buscar.setPosicion(nuevaFila,nuevaColumna); //Si no és del mateix color, canvia els atributs de la peça moguda en la llista
        } else {
            throw new Exception("61 La peça buscada no existeix");
        }
    }

    public boolean eliminarPiezaEnPosicion(int columna, int fila) throws Exception { //EN TEORIA FUNCIONA, NO COMPROBADO
        if(columna < 0 || fila < 0){
            return false;
        }
        else {

            //Cas en ser la primera (SENSE CAPÇALERA!!!!!!)
            if(piezasVivas.pieza.getFila() == fila && piezasVivas.pieza.getColumna() == columna){
                piezasVivas = piezasVivas.seguent;
                return true;
            }

            NodePieza aux = piezasVivas.seguent; //TODO PREGUNTAR
            while (aux != null) {
                if (fila == aux.seguent.pieza.getFila() && columna == aux.seguent.pieza.getColumna()) {
                    if (aux.seguent.pieza.fiJoc()) {
                        aux.seguent = aux.seguent.seguent; //Elimina el rei
                        throw new FiJocException("El rey esta mort");
                    }
                    aux.seguent = aux.seguent.seguent; //Elimina la peça buscada
                    return true;
                }
                else {
                    aux = aux.seguent; //Segueix buscant
                }
            }
        }
        return false;
    }

    public boolean reySigueVivo(){ //COMPROBADO BIEN
        if (isEmpty()) {
            NodePieza aux = piezasVivas;
            while(aux!=null) {
                if (aux.pieza.getTipus() == Pieza.REY) {
                    return true;
                }
                aux = aux.seguent;
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
        return !(piezasVivas ==null);
    }


    public void mostraPeces(){ //COMPROBADO BIEN
        if (isEmpty()) {
            String r = " ";
            NodePieza aux = piezasVivas;
            while(aux!=null) {
                r += "["+ aux.pieza.getTipus() + "," + aux.pieza.getFila() + "," + aux.pieza.getColumna()  + "]" + ", ";
                aux = aux.seguent;
            }
            System.out.println(r);
    }
    }
}
