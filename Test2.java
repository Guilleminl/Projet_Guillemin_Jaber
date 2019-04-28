import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Test2 {
    public static void main(String[] args) {
        boolean stop = false;
        ConnexionDatabase connexionDatabase;
        Connection connection;

        Set<Promotion> promotions = new HashSet<>();
        Set<Professeur> professeurs = new HashSet<>();
        try{
            int nombre_eleve = 0;
            int nombre_professeur = 0;
            connexionDatabase = new ConnexionDatabase();
            connection = connexionDatabase.getConnection();
            automaticSQL autoSQL = new automaticSQL(connection);
            nombre_professeur = autoSQL.chargerProfesseur(professeurs);
            autoSQL.chargerPromotion(promotions);
            for(Promotion prom : promotions) {
                nombre_eleve = autoSQL.chargerEtudiantBDD(prom.getEleves(), prom);
                autoSQL.chargerEvaluation(prom.getEleves(), professeurs);
            }
            String select;
            Set<String> options = new HashSet<>();
            options.add("1");
            options.add("2");
            options.add("3");
            options.add("4");
            options.add("5");
            for(Professeur l : professeurs)
            {
                l.setPromotions(promotions);
                break;
            }
            do {
                do {
                    select = JOptionPane.showInputDialog(null, "1- Ajouter une promotion\n2- Ajouter un nouvel Eleve\n3- Ajouter un Professeur\n4- Noter un Eleve\n5- Quitter", "", JOptionPane.PLAIN_MESSAGE);
                }
                while(!options.contains(select));
                switch (select){
                    case "1":
                        promotions.add(new Promotion(JOptionPane.showInputDialog(null, "Nom de la nouvelle promotion?", "", JOptionPane.PLAIN_MESSAGE), promotions.size() + 1));
                        break;
                    case "2":
                        boolean trouver = false;
                        String prom_name = JOptionPane.showInputDialog(null,
                                "A quelle promotion l'Eleve appartient-il?", "");
                        for (Promotion p : promotions) {
                            if (p.getNomPromotion().equals(prom_name)) {
                                trouver = true;
                                nombre_eleve++;
                                int annee = 0;
                                int mois = 0;
                                int jour = 0;
                                do{
                                    try {
                                        annee = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                "Annee de naissance", ""));
                                        mois = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                "Mois de naissance", ""));
                                        if(mois<1 || mois>12)
                                            throw new IllegalStateException();
                                        jour = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                "Jour de naissance", ""));
                                        if(jour<1 || (jour>29 && mois==2) || mois>31)
                                            throw new IllegalStateException();
                                    }catch (NumberFormatException e){
                                        JOptionPane.showMessageDialog(null,"Les valeurs ne sont pas numeriques");
                                        annee = 0;
                                        mois = 0;
                                        jour = 0;
                                    }catch (IllegalStateException e){
                                        JOptionPane.showMessageDialog(null,"Les valeurs ne sont pas correctes");
                                        annee = 0;
                                        mois = 0;
                                        jour = 0;
                                    }
                                }while(annee==0 || mois==0 || jour==0);
                                p.ajouterEleve(new Eleve(nombre_eleve,
                                        JOptionPane.showInputDialog(null,"Nom de famille"),
                                        JOptionPane.showInputDialog(null,"Prenom"),
                                        annee, mois, jour,p));
                            }
                            if(!trouver)
                                JOptionPane.showMessageDialog(null,"La promotion n'existe pas");
                        }
                        break;
                    case "3":
                        nombre_professeur++;
                        professeurs.add(new Professeur(JOptionPane.showInputDialog(null,"Nom de famille"),
                                JOptionPane.showInputDialog(null,"Prenom"),
                                nombre_professeur));
                        if(nombre_professeur == 1)
                        {
                            for(Professeur p : professeurs)
                            {
                                p.setPromotions(promotions);
                                break;
                            }
                        }
                        break;
                    case "4":
                        int Id_professeur = 0;
                        try {
                            Id_professeur = Integer.parseInt(JOptionPane.showInputDialog(null, "ID du professeur?"));
                        }catch(NumberFormatException e)
                        {
                            e.printStackTrace();
                            break;
                        }
                        for(Professeur p : professeurs){
                            try {
                                if (Id_professeur == p.getId()) {
                                    p.mettreNote(JOptionPane.showInputDialog(null, "Nom de la promotion"),
                                            JOptionPane.showInputDialog(null, "ID de l'Etudiant"),
                                            Float.parseFloat(JOptionPane.showInputDialog(null, "Note de l'eleve")),
                                            Integer.parseInt(JOptionPane.showInputDialog(null, "Index de l'evaluation")),
                                            JOptionPane.showInputDialog(null, "Sujet:"));
                                }
                            }catch (NumberFormatException e){
                                JOptionPane.showMessageDialog(null,"Probleme avec les valeurs entrees");
                            }
                        }
                        break;
                    case "5":
                        stop = true;
                        break;
                }
            }
            while(!stop);
            autoSQL.toutSauvegarder(promotions, professeurs);
        }
        catch (SQLException e){ e.printStackTrace(); }
        catch (ClassNotFoundException e1){ e1.printStackTrace(); }

    }
}
