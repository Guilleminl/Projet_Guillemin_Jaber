import java.text.DecimalFormat;

public class Evaluation {
    //Attributes
    private String subject;
    private float mark;
    private Professeur professeur;
    private Eleve eleve;

    //Constructor
    Evaluation(String subject, float mark, Professeur professeur, Eleve eleve){
        this.subject=subject;
        this.professeur = professeur;
        this.mark=mark;
        this.eleve = eleve;
    }

    //Getters
    public String getSubject() {
        return subject;
    }

    public float getMark() {
        return mark;
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public Eleve getEleve() {
        return eleve;
    }

    //Setter
    public void setMark(float mark) { this.mark = mark; }

    //Hashcode, equals, toString overrided methods
    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + subject.hashCode();
        result = result * 17 + Float.valueOf(this.mark).hashCode();
        result = result * 13 + this.professeur.hashCode();
        result = result * 13 + this.eleve.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(this.getClass()!=obj.getClass()) return false;
        Evaluation other = (Evaluation) obj;
        return this.eleve.equals(other.getEleve()) &&
                this.mark==other.getMark() &&
                this.professeur.equals(other.getProfesseur()) &&
                this.subject.equals(other.getSubject());
    }

    @Override
    public String toString() {
        String message = "(";
        message += this.eleve.toString() + " " + this.professeur.toString() + " "
                + this.subject + " " + new DecimalFormat("##.00").format(this.mark);
        return message;
    }
}
