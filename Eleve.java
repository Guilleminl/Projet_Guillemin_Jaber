import java.text.DecimalFormat;
import java.util.*;

public class Eleve extends Personne {
    //They have a com.JavaProject.BasicClasses.Date of birth stored
    private Date dateNaissance;

    //Since we'll use a database, the students need an ID field
    private String ID;

    //They have a multiple evaluations to which they can access
    private List<Evaluation> evaluations;

    /* They belong to a promotion, and they know to which one */
    private Promotion promotion;

    //Constructor
    public Eleve(int id, String nomFamille, String prenom, int annee, int mois, int jour, Promotion promotion){
        super(nomFamille,prenom);
        this.dateNaissance = new Date(annee, mois, jour);
        this.promotion=promotion;
        this.ID = Integer.toString(id);
        this.evaluations = new ArrayList();
        promotion.ajouterEleve(this);
    }

    //Getters
    public  String getID() { return ID; }

    public Date getDateNaissance() { return dateNaissance; }

    @Override
    public String getNomFamille() {
        return super.getNomFamille();
    }

    @Override
    public String getPrenom() {
        return super.getPrenom();
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public Promotion getPromotion(){
        return promotion;
    }

    public Set<Professeur> getProfesseurs(){
        if(this.evaluations.size()==0) return null;
        Set<Professeur> professeurs = new HashSet();
        for(Evaluation e1 : this.evaluations){
            professeurs.add(e1.getProfesseur());
        }
        return professeurs;
    }

    //Median and Average, Global and with a subject specified
    public Double moyenne() throws IllegalStateException{
        if(evaluations.size()==0) throw new IllegalStateException("Pas de test");
        float moyenne = 0;
        for (Evaluation e1 : this.evaluations){
            moyenne += e1.getMark();
        }
        return Double.valueOf(new DecimalFormat("#.##").format(moyenne/this.evaluations.size()).replace(",","."));
    }

    public Double mediane() throws IllegalStateException {
        Collections.sort(this.evaluations, Comparator.comparing(Evaluation::getMark));
        if(evaluations.size()==0) {
            throw new IllegalStateException("Pas de test");
        }
        if(this.evaluations.size()%2!=0)
            return Double.valueOf(new DecimalFormat("#.##").format(this.evaluations.get((int)Math.floor(this.evaluations.size()/2)).getMark()).replace(",","."));
        else{
            return Double.valueOf(new DecimalFormat("#.##").format(((this.evaluations.get((this.evaluations.size()/2)).getMark() +
                    this.evaluations.get((this.evaluations.size()/2)- 1 ).getMark())/2)).replace(",","."));
        }
    }

    public Double moyenne_matiere(String sujet) throws IllegalStateException{
        if(evaluations.size()==0) throw new IllegalStateException("Pas de test");
        double moyenne = 0;
        int cpt = 0;
        for(Evaluation e1 : this.evaluations){
            if(e1.getSubject().equals(sujet)){
                cpt++;
                moyenne+=e1.getMark();
            }
        }
        return Double.valueOf(new DecimalFormat("#.##").format(moyenne/cpt).replace(",","."));
    }

    public Double mediane_matiere(String matiere) throws IllegalStateException {
        if(evaluations.size()==0) throw new IllegalStateException("No test registered");
        ArrayList<Evaluation> temp = new ArrayList<>();
        for(Evaluation e : evaluations){
            if(e.getSubject().equals(matiere)){
                temp.add(e);
            }
        }
        if(temp.size()==0) throw new IllegalStateException("No mark on this subject");
        Collections.sort(temp,Comparator.comparing(Evaluation::getMark));
        if(temp.size()%2!=0)
            return Double.valueOf(new DecimalFormat("#.##").format(temp.get((int)Math.floor(temp.size()/2)).getMark()).replace(",","."));
        else
            return Double.valueOf(new DecimalFormat("#.##").format(((temp.get((temp.size()/2)).getMark() +
                    temp.get((temp.size()/2)- 1 ).getMark())/2)).replace(",","."));
    }

    //Hashcode equals, toString overrided methods
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + this.ID.hashCode();
        result = result * 31 * this.dateNaissance.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(this.getClass() != obj.getClass()) return false;
        Eleve other = (Eleve) obj;
        return this.promotion.equals(other.getPromotion()) &&
                this.ID.equals(other.getID()) &&
                this.dateNaissance.equals(other.getDateNaissance()) &&
                super.getPrenom().equals(other.getPrenom()) &&
                super.getNomFamille().equals(other.getNomFamille());
    }

    @Override
    public String toString() {
        String message = super.toString();
        message += " id: " + this.ID;
        message += "\npromotion: " + this.promotion.getNomPromotion();
        message += "\nnotes:";
        for(Evaluation e1 : this.evaluations){
            message += " " + e1.getSubject() + " " + new DecimalFormat("##.00").format(e1.getMark());
        }
        if(this.evaluations.size()>0) {
            message += "\nmoyenne = " + moyenne();
            message += "\nmediane = " + mediane();
            message += "\nprofesseur(s): " + getProfesseurs().toString();
        }
        return message;
    }

}
