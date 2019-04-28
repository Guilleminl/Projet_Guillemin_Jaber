public class Personne {
    //This class is the basis of two other classes which shares fields (com.JavaProject.BasicClasses.Professeur and com.JavaProject.BasicClasses.Eleve)
    private String nomFamille;
    private String prenom;

    //Constructor
    public Personne(String nomFamille, String prenom) {
        this.nomFamille = nomFamille;
        this.prenom = prenom;
    }

    //Getters
    public String getNomFamille() {
        return nomFamille;
    }

    public String getPrenom() {
        return prenom;
    }

    //Hashcode, equals, toString Overrided methods

    @Override
    public int hashCode() {
        int hashcode = 1;
        hashcode = hashcode * 31 + this.nomFamille.hashCode();
        hashcode = hashcode * 31 + this.prenom.hashCode();
        return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(this.getClass()!= obj.getClass()) return false;
        Personne other = (Personne) obj;
        return this.nomFamille.equals(other.getNomFamille()) && this.prenom.equals(other.getPrenom());
    }

    @Override
    public String toString() {
        return "(" + this.prenom + ", " + this.nomFamille + ")";
    }

}
