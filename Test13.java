import javax.swing.*;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Test13 {
    public static void main(String[] args) {
        //Ask for extended test
        int test3 = JOptionPane.showConfirmDialog(  null,"Test 3?","Choix Test",JOptionPane.YES_NO_OPTION);
        //Creating a promotion
        Set<Promotion> promotions = new HashSet<>();
        Promotion prom = new Promotion("L3",1);
        promotions.add(prom);
        //Creating a set of professeurs
        Set<Professeur> professeurs = new HashSet<>();
        //adding 5 professeurs
        professeurs.add(new Professeur("Pierre", "Paul",1));
        professeurs.add(new Professeur("Jeanne", "Jacques",2));
        professeurs.add(new Professeur("Boul", "Bill",3));
        professeurs.add(new Professeur("Cuillere", "Fourchette",4));
        professeurs.add(new Professeur("Pince", "Crabe",5));
        //linking the promotion to the professeurs (even if its not needed there)
        for (Professeur p : professeurs){
            p.setPromotions(promotions);
            break;
        }

        //Adding 10 students to the promotion
        prom.ajouterEleve(new Eleve(1,"Lucas","Guillemin", 1998, 03,28,prom));
        prom.ajouterEleve(new Eleve(2,"Fianso","Sofiane", 1998, 04,17,prom));
        prom.ajouterEleve(new Eleve(3,"K2A","Kaaris", 1998, 11,30,prom));
        prom.ajouterEleve(new Eleve(4,"B2O","Booba", 1998, 01,15,prom));
        prom.ajouterEleve(new Eleve(5,"LesMechants","Heuss", 1998, 03,7,prom));
        prom.ajouterEleve(new Eleve(6,"Orelsan","Aurelien", 1998, 03,27,prom));
        prom.ajouterEleve(new Eleve(7,"Kenny","Tim", 1998, 06,14,prom));
        prom.ajouterEleve(new Eleve(8,"Ali","Baba", 1998, 11,11,prom));
        prom.ajouterEleve(new Eleve(9,"Chang","Chang", 1998, 03,26,prom));
        prom.ajouterEleve(new Eleve(10,"Blanka","Audd", 1998, 02,2,prom));

        //5 subjects (1 for each lecturer) and 3 notes in each
        String subject;
        Random r = new Random();
        DecimalFormat df = new DecimalFormat("#.##");
        for(Professeur l: professeurs){
            for(Eleve e : prom.getEleves()) {
                for(int i = 0 ; i < 3 ; i++) {
                    switch (l.getId()) {
                        case 1:
                            subject = "Athletisme";
                            l.mettreNote(prom.getNomPromotion(), e.getID(), r.nextFloat()*20, l.chercherEleve(e.getID(), prom.getNomPromotion()).getEvaluations().size()+1, subject);
                            break;
                        case 2:
                            subject = "Natation";
                            l.mettreNote(prom.getNomPromotion(), e.getID(), r.nextFloat()*20, l.chercherEleve(e.getID(), prom.getNomPromotion()).getEvaluations().size()+1, subject);
                            break;
                        case 3:
                            subject = "Escalade";
                            l.mettreNote(prom.getNomPromotion(), e.getID(), r.nextFloat()*20, l.chercherEleve(e.getID(), prom.getNomPromotion()).getEvaluations().size()+1, subject);
                            break;
                        case 4:
                            subject = "Badminton";
                            l.mettreNote(prom.getNomPromotion(), e.getID(), r.nextFloat()*20, l.chercherEleve(e.getID(), prom.getNomPromotion()).getEvaluations().size()+1, subject);
                            break;
                        case 5:
                            subject = "Football";
                            l.mettreNote(prom.getNomPromotion(), e.getID(), r.nextFloat()*20, l.chercherEleve(e.getID(), prom.getNomPromotion()).getEvaluations().size()+1, subject);
                            break;
                    }
                }
            }
        }
        if(test3==1) {
            //Search a eleve in particular,
            //As a reminder, the ID is the concatenation of the eleve's promotion name and his id
            //Here the only promotion is L3, and the ID goes from 1 to 10 (included)
            Eleve eleve = null;
            do {
                String ID = JOptionPane.showInputDialog(null,
                        "ID de l'eleve:", "", JOptionPane.PLAIN_MESSAGE);
                for (Promotion p : promotions) {
                    eleve = p.chercherEleve(ID);
                }
            } while (eleve == null);
            System.out.println("\n\n" + eleve.toString() + "\n\n");

            List<Eleve> eleveTrier;

            //Now we sort them by global moyenne, it returns a List since a Set has no order
            for (Promotion p : promotions) {
                eleveTrier = p.trierEleveMoyenne(true);
                for (Eleve e : eleveTrier) {
                    System.out.println(e.toString() + "\n");
                }
            }
            //Now we sort them by global moyenne (inverted), it returns a List since a Set has no order
            for (Promotion p : promotions) {
                eleveTrier = p.trierEleveMoyenne(false);
                for (Eleve e : eleveTrier) {
                    System.out.println(e.toString() + "\n");
                }
            }
            //Now we sort them by global mediane, it returns a List since a Set has no order
            for (Promotion p : promotions) {
                eleveTrier = p.trierEleveMediane(true);
                for (Eleve e : eleveTrier) {
                    System.out.println(e.toString() + "\n");
                }
            }
            //Now we sort them by global mediane (inverted), it returns a List since a Set has no order
            for (Promotion p : promotions) {
                eleveTrier = p.trierEleveMediane(false);
                for (Eleve e : eleveTrier) {
                    System.out.println(e.toString() + "\n");
                }
            }
        }
        else{
            String ID = JOptionPane.showInputDialog(null,"ID de l'eleve (bulletin)","",JOptionPane.PLAIN_MESSAGE);
            for(Promotion p : promotions){
                for(Eleve e : p.getEleves()){
                    System.out.println(e.toString() + "\n\n");
                    if(e.getID().equals(ID)){
                        Bulletin bulletin = new Bulletin(e);
                        bulletin.creerDonneeBulletin();
                        break;
                    }
                }
            }
        }

    }
}
