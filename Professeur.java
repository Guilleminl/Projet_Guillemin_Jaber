import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class Professeur extends Personne {

    private static Set<Promotion> promotions = new HashSet();
    private int id;

    //Constructor

    public Professeur(String nomFamille, String prenom, int id){
        super(nomFamille,prenom);
        this.id = id;
    }

    //Getter
    public int getId() {
        return id;
    }

    @Override
    public String getNomFamille() {
        return super.getNomFamille();
    }

    @Override
    public String getPrenom() {
        return super.getPrenom();
    }

    public Set<Promotion> getPromotions(){
        return this.promotions;
    }

    //Setter
    public static void setPromotions(Set<Promotion> promotions) {
        Professeur.promotions = promotions;
    }

    //Methods
    public Eleve chercherEleve(String ID, String nomPromotion){
        for(Promotion p1:this.promotions){
            if(p1.getNomPromotion().equals(nomPromotion)){
                return p1.chercherEleve(ID);
            }
        }
        return null;
    }

    public void mettreNote(String nomPromotion, String IDeleve, float note, int index, String sujet) throws IllegalStateException, IndexOutOfBoundsException{
        try {
            Eleve temp = chercherEleve(IDeleve, nomPromotion);
            if(temp == null)
                throw new IllegalStateException();
            if (index < 0)
                throw new IndexOutOfBoundsException();
            if (index < temp.getEvaluations().size()) {
                temp.getEvaluations().get(index).setMark(note);
            } else
                temp.getEvaluations().add(new Evaluation(sujet, note, this, temp));
        }
        catch (IllegalStateException e){
            JOptionPane.showMessageDialog(null,"Eleve non trouvé","",JOptionPane.PLAIN_MESSAGE);
        }
        catch (IndexOutOfBoundsException e2){
            JOptionPane.showMessageDialog(null,"Index non valide","", JOptionPane.PLAIN_MESSAGE);
        }
    }


    //Hashcode equals and toString methods
    @Override
    public int hashCode() {
        return super.hashCode()*11 + this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(this.getClass() != obj.getClass()) return false;
        Professeur other = (Professeur)obj;
        return other.getPrenom().equals(this.getPrenom()) &&
                other.getNomFamille().equals(this.getNomFamille()) && this.id==other.getId();
    }

    @Override
    public String toString() {
        return super.toString() + " id: " + this.id;
    }


}
