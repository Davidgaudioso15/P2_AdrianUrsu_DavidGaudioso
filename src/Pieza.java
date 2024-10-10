public class Pieza implements ItipoPieza {

    public static final char PEON = 'P';
    public static final char CABALLO = 'C';
    public static final char ALFIL = 'A';
    public static final char TORRE= 'T';
    public static final char REINA = 'D';
    public static final char REY = 'R';

    private int columna;
    private int fila;
    private final char tipo;

    public Pieza(char tipo, int fila, int columna){
        checkTipo(tipo,fila,columna);
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
    }

    private void checkTipo(char tipo, int fila, int columna) throws IllegalArgumentException {
        if (fila < 0 || fila > 8) {
            throw new IllegalArgumentException("mal introducido la fila");
        }
        if (columna < 0 || columna > 8) {
            throw new IllegalArgumentException("mal introducido la columna");
        }
        if (tipo < ALFIL || tipo > TORRE) {
            throw new IllegalArgumentException("mal introducido el tipo");
        }
    }

    @Override
    public char getTipus(){
        return tipo;
    }
    @Override
    public int getFila() {
        return fila;
    }

    @Override
    public int getColumna() {
        return columna;
    }

    @Override
    public void setPosicion(int fila, int columna) throws RuntimeException {
        if(fila < 0 || fila > 8 && columna < 0 || columna > 8){
            throw new RuntimeException();
        }
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public boolean fiJoc() {
        if(tipo == REY){
            return true;
        }
        return false;
    }

    public boolean equals(Object o) {
        Pieza p = (Pieza) o;
        if(this.getTipus() == p.getTipus() && this.fila == p.getFila() && this.columna == p.getColumna()){
            return true;
        }
        return false;
    }
}
