import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Torns <E>{
    private class NodeTorn{
        public E moviment;
        public NodeTorn seguent;
        public NodeTorn(E moviment, NodeTorn seguent)
        {
            this.moviment = moviment;
            this.seguent = seguent;
        }
    }
    private NodeTorn llistatTorns; // seqüència enllaçada de torns
// amb capçalera

    public Torns(String nomfitxer) throws IOException {
        try{
            carregardesdeFitxer(nomfitxer);
        } catch (Exception e) {
            System.out.println("Error al llegir el fitxer : "+nomfitxer);
        }
    }

    public void afegirtorn(E torn){ // TODO PREGUNTAR COMO SE GUARDA UN TORN
        llistatTorns.add(torn);
    }

    public E agafarPrimerTorn() {

        if(llistatTorns.isEmpty()){
            throw new NoSuchElementException("La llista està buida, no es pot llegir la llista de torns");
        }
        E primerelement =  llistatTorns.get(0);
        llistatTorns.remove(0);
        return primerelement;
    }

    public void guardarAfitxer(String nomfitxer){
            try(FileWriter fitxer = new FileWriter("src/Torns/" + nomfitxer + ".txt")) {
                for (int i =0;i<llistatTorns.size();i++){
                    String torn = llistatTorns.get(i).toString();
                    String part1 = torn.substring(0, torn.length()/2);
                    String part2 = torn.substring(torn.length()/2);
                    part1 = part1 + part2;
                    fitxer.write(part1 + "\n");
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
            while ((linea=fitxer.readLine())!=null){
                llistatTorns.add((E) linea);
            }
            fitxer.close();
        }
        catch (IOException e){
            System.out.println("Error al carregar el fitxer : " + nomfitxer + ".txt");
        }

    }


    public String imprimirTorns(){
        String r = "";
        for(int i =0;i<llistatTorns.size();i++){
           r+=llistatTorns.get(i).toString()+"\n";
        }
        return r;

    }



}
