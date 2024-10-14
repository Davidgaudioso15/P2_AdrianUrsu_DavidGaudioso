import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Main main = new Main();
        main.mostrarMenu();

    }

    private void mostrarMenu() {

        Scanner scanner = new Scanner(System.in);
        int opc = 0;

        try {
            while (opc != 3) {
                System.out.println("Escull una opció:");
                System.out.println("1.- Una partida nova");
                System.out.println("2.- Reproduir una partida des d'un fitxer");
                System.out.println("3.- Sortir");

                if (scanner.hasNextInt()) {
                    opc = scanner.nextInt();

                    switch (opc) {
                        case 1:
                            System.out.println("Has seleccionat: Jugar una partida nova");
                            jugarNovaPartida();

                            break;
                        case 2:
                            System.out.println("Has seleccionat: Reproduir una partida des d'un fitxer");
                            reproduirPartida();
                            break;
                        case 3:
                            System.out.println("Sortint del programa. Adéu!");
                            break;
                        default:
                            System.out.println("Opció no vàlida. Si us plau, tria una opció entre 1 i 3.");
                    }
                } else {
                    System.out.println("Si us plau, introdueix un número vàlid.");
                    scanner.next();
                }
            }
        } catch (Exception e) {
            System.out.println("S'ha produït un error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private void jugarNovaPartida() throws Exception {

        Scanner scanner = new Scanner(System.in);
        Jugador j1 = new Jugador(iniciarJuegoBlancas()); //BLANCAS
        Jugador j2 = new Jugador(iniciarJuegoNegras());  //NEGRAS
        Torns tornn = new Torns();
        boolean blanca = true;
        String jugador = "";

        while(j1.reySigueVivo() && j2.reySigueVivo()){

            fesEnter();
            System.out.println("----------------------------------------------------------------------------------------");
            fesEnter();
            mostrarTauler(j1,j2);
            //fesEnter();

            if(blanca) jugador = "blanques"; else jugador = "negres";
            System.out.println("Torn de les " + jugador );
            System.out.println("Que peça vols moure? (Per exemple : A1,A3)");

            String entrada = "";
            //if(blanca) entrada = "B"; else entrada = "N";

            boolean correcte = false;
            while(!correcte ) {
                entrada = scanner.nextLine().toUpperCase();
                if(!entrada.isEmpty() && esLletra(entrada.charAt(0)) && !esLletra(entrada.charAt(1)) && esLletra(entrada.charAt(3)) && !esLletra(entrada.charAt(4)) && entrada.length() == 5) {
                    correcte = true;

                    //Això és perque el mètode tornToPosition accepta l'String amb aquest format : "A1,B2 B", per exemple, per saber si és el torn de les blanques o negres i en funció d'això fer els mètodes de cerca i eliminació corresponents
                    if (blanca) entrada += " B";
                    else entrada += " N";
                    try {
                        tornToPosition(entrada,j1,j2);
                        //Per no afegir B o N en el .txt es fa aquest substring
                        entrada = entrada.substring(0,5);

                        //Afegeix el torn a la llista de torns de l'objecte Torns
                        tornn.afegirtorn(entrada);

                        //Canvia el jugador
                        blanca = !blanca;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    System.out.println("El format de moviment és, per exemple, \"A1,A2\", torna-hi");
                }
            }
        }

        fesEnter();
        mostrarTauler(j1,j2);
        System.out.println("El rey ha mort, final de partida");


        //Per guardar automàticament el fitxer amb els torns i poder llegir-los, es fa el següent: Primer es concatena la data d'avui, per exemple 01102024 i després la hora actual del sistema, com 135500 (les 13h 55min 00seg)
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
        String timestamp = now.format(formatter);
        String fileName = "torn_" + timestamp;
        tornn.guardarAfitxer(fileName);

        fesEnter();

    }
    //Aquest mètode, a partir dels paràmetres, cridarà a un altre mètode per actualitzar la posició
    private void entrada(String entrada, boolean blanca, Jugador j1, Jugador j2) {

        if (blanca) entrada += " B";
        else entrada += " N";

        try {
            tornToPosition(entrada,j1,j2);
        } catch (Exception r) {
            System.out.println("No s'ha pogut fer tornTOPosition amb entrada = " + entrada + " r.getMessage() =  "+r.getMessage());
        }

        mostrarTauler(j1,j2);
        System.out.println("145");

    }

    //Aquest mètode retorna true o false si està en el rang o no per saber si es una lletra o no
    private static boolean esLletra(char c) {
        return (c >= 'a' && c <= 'h') || (c >= 'A' && c <= 'H');
    }

    private void reproduirPartida() throws IOException {
        Torns tornn = llegirTorns();
        Jugador j1 = new Jugador(iniciarJuegoBlancas());
        Jugador j2 = new Jugador(iniciarJuegoNegras());

        try{
            String too = " ";
            boolean blanca = true;
            for(int i = 1;too != null;i++){
                too = (String) tornn.agafarPrimerTorn();

                if(i%2==0){
                    System.out.println("Es el torn del jugador negre i fa el el movient "+ too.substring(0,2) + " a la posicio " + too.substring(3,5));
                    fesEnter();
                    blanca = false; //NEGRE
                }
                else{
                    System.out.println("Es el torn del jugador blanc i fa el el movient " + too.substring(0,2) + " a la posicio " + too.substring(3,5));
                    fesEnter();
                    blanca = true;        //BLANCA
                }
                entrada(too,blanca,j1,j2);
            }
        }
        catch (NoSuchElementException e) {
            System.out.println("No hi ha cap torn més en el fitxer!");
        }
    }

    private Torns<String> llegirTorns() throws IOException {
        Scanner scanner = new Scanner(System.in);
        Torns tornn;

        try {
            System.out.println("Escriu el nom del fitxer de torns que vols reproduïr: ");
            String fitxer = scanner.nextLine();
            tornn = new Torns(fitxer);
        } catch (IOException e) {
            tornn = llegirTorns();
        }

        return tornn;
    }

    private static void tornToPosition(String torn, Jugador j1, Jugador j2) throws Exception,FiJocException {

        char t = torn.charAt(torn.length() -1);
        int columna1 = 0;
        int fila1 = 0;
        int columna2 = 0;
        int fila2 = 0;

        Jugador aux = null; //JUGADOR ACTUAL
        Jugador aux2 = null; //JUGADOR PARA ELIMINAR PIEZAS

        //Per saber el jugador que ha de moure
        if(t=='B') { aux=j1; aux2=j2; } else { aux=j2; aux2=j1; }

        //aux és el Jugador que fa el moviment i aux2 per eliminar les seves peçes en el cas de moure d'una peca blanca, per exemple, a una negre

        Scanner scanner = new Scanner(System.in);

        boolean correcte = false;
        while(!correcte) {
            //Aquí es mira que el format sigui tipus : A1,A2
            if(esLletra(torn.charAt(0)) && !esLletra(torn.charAt(1)) && esLletra(torn.charAt(3)) && !esLletra(torn.charAt(4))) {

                //Es converteixen els chars en ints de dues maneres "diferents"
                columna1 = (int) torn.charAt(0) -65;
                fila1 = torn.charAt(1) - '0';
                columna2 = (int) torn.charAt(3) -65;
                fila2 = torn.charAt(4) - '0';

                correcte = true;
                aux.moverPieza(columna1, fila1, columna2, fila2);
            } else {
                System.out.println("Torna-hi, no has posat bé el moviment");
                torn = scanner.nextLine().toUpperCase();
            }
        }

        if(shaEliminar(aux,aux2,columna2,fila2)) {
            try {
                aux2.eliminarPiezaEnPosicion(columna2, fila2);
            }catch (Exception e){
                System.out.println("244   e.getMessage() = " + e.getMessage());
            }
        }
    }

    //El mètode mira si el j1 té la peça del j2
    private static boolean shaEliminar(Jugador j1, Jugador j2, int columna2, int fila2) {
        return j1.repetit(j2.getPiezasVivas(),columna2,fila2);
    }

    private void mostrarTauler(Jugador blanc, Jugador negre) {

        Pieza[][] tauler = new Pieza[8][8];

        for(int i = 0;i < blanc.getPiezasVivas().size();i++) {
            Pieza p1 = (Pieza) blanc.getPiezasVivas().get(i);
            tauler[p1.getFila()][p1.getColumna()] = p1;
        }

        for(int i = 0;i < negre.getPiezasVivas().size();i++) {
            Pieza p2 = (Pieza) negre.getPiezasVivas().get(i);
            tauler[p2.getFila()][p2.getColumna()] = p2;
        }

        for (char columna = 'A'; columna <= 'H'; columna++) {
            System.out.print("    " + " " +columna + " " + "    ");
        }

        fesEnter();

        for(int i = 0;i < tauler.length;i++) {
            System.out.print(i);
            for(int j = 0;j < tauler[i].length;j++) {
                if(tauler[i][j] != null) {
                    System.out.print("    " + "[" + tauler[i][j].getTipus() + "]" + "    ");
                } else {
                    System.out.print("    " + "[" + "buit" + "]" + " ");
                }
            }
            fesEnter();
        }
        //fesEnter();
    }

    public void fesEnter() {
        System.out.println("\n");
    }

    private ArrayList<Pieza> iniciarJuegoBlancas() {

        char[] p = iniciaJoc();

        ArrayList<Pieza> llista = new ArrayList<>();

        for(int i = 0;i < 8; i++) {
            llista.add(new Pieza(p[i], 0, i));
        }

        for (int i = 0; i < 8; i++) {
            llista.add(new Pieza('P', 1, i));
        }

        return llista;
    }

    private ArrayList<Pieza> iniciarJuegoNegras() {

        ArrayList<Pieza> llista = new ArrayList<>();

        char[] p = iniciaJoc();

        for (int i = 0; i < 8; i++) {
            llista.add(new Pieza('P', 6, i));
        }

        for(int i = 0;i < 8; i++) {
            llista.add(new Pieza(p[i],7,i));
        }

        return llista;
    }

    private char[] iniciaJoc() {
        char[] p = new char[8];

        p[0] = Pieza.TORRE;
        p[1] = Pieza.CABALLO;
        p[2] = Pieza.ALFIL;
        p[3] = Pieza.REY;
        p[4] = Pieza.REINA;
        p[5] = Pieza.ALFIL;
        p[6] = Pieza.CABALLO;
        p[7] = Pieza.TORRE;

        return p;
    }

}
