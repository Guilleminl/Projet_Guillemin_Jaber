import javax.swing.*;
import java.util.ArrayList;

public class Bulletin {
    //Attributes
    private Eleve eleve;
    private Promotion promotion;
    //Constructor
    public Bulletin(Eleve s){
        this.eleve = s;
        this.promotion = s.getPromotion();
    }

    //Methods
    public void creerDonneeBulletin(){
        String[] colonnes = {"Sujet","Min_Moyenne","Moyenne","Max_Moyenne","Min_Mediane","Mediane","Max_Mediane"};
        Object[][] donnees;

        //Getting all the subjects the eleve passed exams for
        //Not taking care if a eleve has less marks than another, since they should all have the same number of test
        ArrayList<String> sujets = new ArrayList<>();

        //Retrieving the averages, medians and the list of subject
        for(Evaluation e : this.eleve.getEvaluations()){
            //Getting the list of subject
            if(!sujets.contains(e.getSubject())){
                sujets.add(e.getSubject());
            }
        }
        //Now we have the list of subjects we can initialise the data array
        String[] tableau_sujet = new String[sujets.size()+1];
        tableau_sujet[0] = "Global";
        int i = 1;
        for(String s : sujets) {
            tableau_sujet[i] = s;
            i++;
        }
        donnees = new Object[tableau_sujet.length][7];
        for(i = 0 ; i < tableau_sujet.length ; i++ ){
            donnees[i][0] =tableau_sujet[i];
            donnees[i][1] = 20.0;
            if(i==0) {
                donnees[i][2] = this.eleve.moyenne();
                donnees[i][5] = this.eleve.mediane();
            }
            else
            {
                donnees[i][2] = this.eleve.moyenne_matiere(tableau_sujet[i]);
                donnees[i][5] = this.eleve.mediane_matiere(tableau_sujet[i]);
            }
            donnees[i][3] = 0.0;
            donnees[i][4] = 20.0;
            donnees[i][6] = 0.0;
        }
        for(Eleve e : this.promotion.getEleves()){
            //Getting the averages
            if(e.moyenne()<(double)donnees[0][1])
                donnees[0][1] = e.moyenne();
            if(e.moyenne()>(double)donnees[0][3])
                donnees[0][3] = e.moyenne();
            //Getting the medians
            if(e.mediane()<(double)donnees[0][4])
                donnees[0][4] = e.mediane();
            if(e.mediane()>(double)donnees[0][6])
                donnees[0][6] = e.mediane();
            for(int j = 1; j < tableau_sujet.length ; j++){
                if(e.moyenne_matiere(tableau_sujet[j])<(double)donnees[j][1])
                    donnees[j][1] = e.moyenne_matiere(tableau_sujet[j]);
                if(e.moyenne_matiere(tableau_sujet[j])>(double)donnees[j][3])
                    donnees[j][3] = e.moyenne_matiere(tableau_sujet[j]);
                if(e.mediane_matiere(tableau_sujet[j])<(double)donnees[j][4])
                    donnees[j][4] = e.mediane_matiere(tableau_sujet[j]);
                if(e.mediane_matiere(tableau_sujet[j])>(double)donnees[j][6])
                    donnees[j][6] = e.mediane_matiere(tableau_sujet[j]);
            }
        }
        montrerBulletin(donnees, colonnes, tableau_sujet);

    }
    public void montrerBulletin(Object[][] donnees, String[] colonnes, String[] sujets){
        //Create the table with the data previously generated
        JTable table = new JTable(donnees,colonnes);
        BulletinSwing bulletinSwing = new BulletinSwing(table, "Bulletin de " + this.eleve.getPrenom() + " " + this.eleve.getNomFamille(),sujets,donnees,colonnes);
        bulletinSwing.setVisible(true);
    }

}
