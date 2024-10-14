import java.io.*;
import java.util.NoSuchElementException;

public class Torns <E>{
    private class NodeTorn{

        public E moviment;
        public NodeTorn seguent;

        public NodeTorn(E moviment, NodeTorn seguent) {
            this.moviment = moviment;
            this.seguent = seguent;
        }

    }

    private NodeTorn llistatTorns; // seqüència enllaçada de torns
// amb capçalera

    public Torns(){
        llistatTorns = new NodeTorn(null,null);
    }

    public Torns(String nomfitxer) throws IOException {
        llistatTorns = new NodeTorn((E) nomfitxer,null);
        try{
            carregardesdeFitxer(nomfitxer);
        } catch (Exception e) {
            System.out.println("Error al llegir el fitxer : "+nomfitxer);
        }
    }

    public void afegirtorn(E torn){ // TODO PREGUNTAR COMO SE GUARDA UN TORN

        if(segNull())
            this.llistatTorns.seguent = new NodeTorn(torn, null);
        else {
            NodeTorn u = ultim();
            u.seguent = new NodeTorn(torn, null);
        }
    }

    private NodeTorn ultim() {
        NodeTorn aux = llistatTorns;
        while(aux.seguent != null){
            aux = aux.seguent;
        }
        return aux;
    }

    private boolean segNull() {
        return llistatTorns.seguent == null;
    }

    public E agafarPrimerTorn() { //NO COMPROBADO

        if(segNull()){
            throw new NoSuchElementException("La llista està buida, no es pot llegir la llista de torns");
        }
        E primerelement =  llistatTorns.seguent.moviment;
        llistatTorns.seguent = llistatTorns.seguent.seguent;
        return primerelement;
    }

    public void guardarAfitxer(String nomfitxer){ //NO COMPROBADO, EN TEORÍA FUNCIONA BIEN
            try(FileWriter fitxer = new FileWriter("src/Torns/" + nomfitxer + ".txt")) {
                NodeTorn aux = llistatTorns.seguent;
                while(aux != null) {
                    String torn = (String) aux.moviment;
                    String part1 = torn.substring(0, torn.length()/2);
                    String part2 = torn.substring(torn.length()/2);
                    part1 = part1 + part2;
                    fitxer.write(part1 + "\n");
                    aux = aux.seguent;
                }
                fitxer.close();
            }
            catch (IOException e){
                System.out.println("Error al guardar en el fitxer : " + nomfitxer);
            }
    }


    private void carregardesdeFitxer(String nomfitxer){

        try{
            File f = new File("src/Torns/" + nomfitxer + ".txt");
            BufferedReader fitxer = new BufferedReader(new FileReader(f));
            String linea;
            NodeTorn aux = llistatTorns;
            while ((linea=fitxer.readLine())!=null){

                aux.seguent = new NodeTorn((E) linea,null);

                //llistatTorns.add((E) linea);
                aux = aux.seguent;
            }
            fitxer.close();
        }
        catch (IOException e){
            System.out.println("Error al carregar el fitxer : " + nomfitxer + ".txt");
        }

    }


    public String imprimirTorns(){
        String r = "";
        if(!segNull()){
            NodeTorn aux = llistatTorns;
            while (aux !=null){
                r+= aux.moviment.toString();
                aux = aux.seguent;
            }
        }
        return r;
    }

}
