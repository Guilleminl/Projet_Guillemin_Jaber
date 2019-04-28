import java.util.*;

public class Promotion {
    //A promotion has a nomPromotion, contains eleves (no duplicates so we use a hashset), and an id since we use a database
    private String nomPromotion;
    private int id;
    private Set<Eleve> eleves = new HashSet();

    //Constructor
    public Promotion(String nomPromotion, int id){
        this.id = id;
        this.nomPromotion = nomPromotion;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getNomPromotion() {
        return nomPromotion;
    }

    public Set<Eleve> getEleves() {
        return eleves;
    }

    //Setter, only one, for the nomPromotion, even if it should not be modified
    public void setNomPromotion(String nomPromotion) {
        this.nomPromotion = nomPromotion;
    }

    //Methods
    public void ajouterEleve(Eleve s){
        this.eleves.add(s);
    }

    public Eleve chercherEleve(String ID){
        for(Eleve s1 : this.eleves){
            if(s1.getID().equals(ID))
                return s1;
        }
        return null;
    }
    //Inverted because it will go from lowest to highest
    public List<Eleve> trierEleveMoyenne(boolean inverse){
        List<Eleve> eleveTrier = new ArrayList();
        for(Eleve s : this.eleves)
            eleveTrier.add(s);
        if(inverse)
            eleveTrier.sort(Comparator.comparing(Eleve::moyenne));
        else
            eleveTrier.sort(Comparator.comparing((Eleve::moyenne)).reversed());
        return eleveTrier;
    }

    public List<Eleve> trierEleveMediane(boolean inverse){
        List<Eleve> eleveTrier = new ArrayList();
        for(Eleve s : this.eleves)
            eleveTrier.add(s);
        if(inverse)
            eleveTrier.sort(Comparator.comparing(Eleve::mediane));
        else
            eleveTrier.sort(Comparator.comparing(Eleve::mediane).reversed());
        return eleveTrier;
    }

    public int maxID(){
        int maxID = 0;
        for(Eleve e : this.eleves){
            if(Integer.parseInt(e.getID())>maxID){
                maxID = Integer.parseInt(e.getID());
            }
        }
        return maxID;
    }


    //Hashcode, equals and toString overrided methods
    @Override
    public int hashCode() {
        int resultat = super.hashCode();
        resultat = resultat * 13 + this.id;
        resultat = resultat * 31 + this.nomPromotion.hashCode();
        return resultat;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this.getClass()!=obj.getClass()) return false;
        Promotion other = (Promotion) obj;
        return this.id==other.getId() && this.nomPromotion.equals(other.nomPromotion);
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "nomPromotion='" + nomPromotion + '\'' +
                ", id=" + id +
                ", nombre d'eleves=" + this.eleves.size() + '}';
    }
}