import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;


public class automaticSQL {
    private Connection con;

    public automaticSQL(Connection con) {
        this.con = con;
    }

    public int chargerEtudiantBDD(Set<Eleve> eleves, Promotion prom)
            throws SQLException {
        Statement stmt = null;
        //Retrieve the max id in case a student is added during the session
        int cpt = 0;
        String max_cpt = "SELECT MAX(id) as max_cpt from student_table";
        String requete =
                "select * " +
                        "from student_table inner join promotion_student " +
                        "on student_table.id = promotion_student.student_id " +
                        "inner join promotion_table " +
                        "on promotion_student.promotion_id = promotion_table.id " +
                        "where promotion_table.name = '" + prom.getNomPromotion() + "'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(max_cpt);
            rs.next();
            cpt = rs.getInt("max_cpt");
            rs = stmt.executeQuery(requete);
            while (rs.next()) {
                int id = rs.getInt("id");
                int annee = rs.getInt("year");
                int mois = rs.getInt("month");
                int jour = rs.getInt("day");
                String prenom = rs.getString("name");
                String nomFamille = rs.getString("last_name");
                eleves.add(new Eleve(id, nomFamille,prenom, annee, mois, jour, prom));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return cpt;
    }

    public void chargerPromotion(Set<Promotion> promotions)
            throws SQLException {
        Statement stmt = null;
        String requete =
                "select * from promotion_table";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(requete);
            while (rs.next()) {
                String nomPromotion = rs.getString("name");
                int idPromotion = rs.getInt("id");
                promotions.add(new Promotion(nomPromotion, idPromotion));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

    }

    public int chargerProfesseur(Set<Professeur> professeurs)
            throws SQLException {
        //Retrieve the max id in case a lecturer is added during the session
        int cpt = 0;
        String max_cpt = "SELECT MAX(id) as max_cpt from lecturer_table";
        Statement stmt = null;
        String requete = "select * from lecturer_table";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(max_cpt);
            rs.next();
            cpt = rs.getInt("max_cpt");
            rs = stmt.executeQuery(requete);
            while (rs.next()) {
                int id = rs.getInt("id");
                String prenom = rs.getString("name");
                String nomFamille = rs.getString("last_name");
                professeurs.add(new Professeur(nomFamille, prenom, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return cpt;
    }

    public void chargerEvaluation(Set<Eleve> eleves, Set<Professeur> professeurs)
            throws SQLException {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            for (Eleve e : eleves) {
                String student_id = e.getID();
                String query = "select * from evaluation_table where student_id = '" + student_id + "'";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String sujet = rs.getString("subject");
                    Float note = rs.getFloat("mark");
                    int idProfesseur = rs.getInt("lecturer_id");
                    int index = rs.getInt("index");
                    Professeur professeur = null;
                    for (Professeur p : professeurs)
                        if (idProfesseur == p.getId())
                            professeur = p;
                    e.getEvaluations().add(new Evaluation(sujet, note, professeur, e));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    public void toutSauvegarder(Set<Promotion> promotions, Set<Professeur> professeurs)
            throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        String requete;
        try {
            stmt = con.createStatement();
            //We start with the professeurs, since they're not linked to any other table
            for(Professeur p : professeurs){
                requete = "SELECT * from lecturer_table where id = '" + p.getId() + "'";
                rs = stmt.executeQuery(requete);
                if(!rs.next()){
                    //Professeur was added during the session, we've to save it in database
                    requete = "INSERT INTO lecturer_table VALUES (default, '" + p.getPrenom() + "', '" + p.getNomFamille() + "')";
                    stmt.executeQuery(requete);
                }
                else{
                    //The lecturer was found, we've to save it's information in case they were modified
                    requete = "UPDATE lecturer_table SET name = '" + p.getPrenom() + "', last_name = '" + p.getNomFamille() + "' " +
                            "WHERE id = '" + p.getId() + "'";
                    stmt.executeQuery(requete);
                }
            }
            //Now we do the same for promotions
            for(Promotion p : promotions){
                //verify the promotion is saved in db
                requete = "SELECT id from promotion_table where name = '" + p.getNomPromotion() + "'";
                rs = stmt.executeQuery(requete);
                //the promotion wasnt found in database, we've to save it
                if(!rs.next()){
                    //We save the name of the promotion
                    requete = "INSERT INTO promotion_table VALUES (default, '" + p.getNomPromotion() + "')";
                    stmt.executeQuery(requete);
                }
                //the promotion was found, we save it's information in case they were modified (should not happen but whatever)
                else
                {
                    requete = "UPDATE promotion_table SET name = '" + p.getNomPromotion() + "' WHERE id = '" + p.getId() + "'";
                    stmt.executeQuery(requete);
                }
                //save the students of the current promotion
                for(Eleve e : p.getEleves()){
                    //Retrieve student id by removing the 2 first tokens of his id
                    int student_id = Integer.parseInt(e.getID());
                    requete = "select * from student_table where id = " + student_id + "";
                    rs = stmt.executeQuery(requete);
                    //No student with corresponding id found (the student has been added during the session)
                    if(!rs.next()){
                        //We save the student data in student_table
                        requete = "INSERT INTO student_table VALUES ('" + e.getPrenom() + "', " + student_id + ", '" + e.getNomFamille() + "', " + e.getDateNaissance().getYear() + ", " + e.getDateNaissance().getMonth() + ", " + e.getDateNaissance().getDay() + ")";
                        stmt.executeQuery(requete);
                        //We save the link between student and promotion
                        requete = "INSERT INTO promotion_student VALUES (" + student_id + ", '" + p.getId() + "')";
                        stmt.executeQuery(requete);
                    }
                    //The student has been found, we save his information which may have been modified
                    else{
                        requete = "UPDATE student_table SET name = '" + e.getPrenom() + "', last_name = '" + e.getNomFamille() + "'," +
                                "year = " + e.getDateNaissance().getYear() + ", month = " + e.getDateNaissance().getMonth() + ", day = " +
                                e.getDateNaissance().getDay() + " " +
                                "WHERE id = '" + student_id + "'";
                        stmt.executeQuery(requete);
                    }
                    //Now the student has been saved into the database we can save it's evaluations
                    int eval_index = 0;
                    for(Evaluation e2: e.getEvaluations()){

                        //We check that the evaluation is registered with the couple student_id and index
                        requete = "SELECT * FROM evaluation_table WHERE (student_id = '" + e2.getEleve().getID() + "' AND " +
                                "`index` = " + eval_index + ")";
                        rs = stmt.executeQuery(requete);
                        //Evaluation not found, we have to add it (should only happen to the last ones though)
                        if(!rs.next()){
                            //We save it in the database
                            requete = "INSERT INTO evaluation_table VALUES ('" + e2.getSubject() + "', '" + e2.getMark() + "', '"+ e2.getProfesseur().getId() + "', '" + e.getID() + "', '" + eval_index +"')";
                            stmt.executeQuery(requete);
                        }
                        //Evaluation's found, we save the modification that may've been done (since we never delete a test, the index should never be modified)
                        else{
                            requete = "UPDATE evaluation_table SET subject = '" + e2.getSubject() + "', mark = '" + e2.getMark() + "', " +
                                    "lecturer_id = '" + e2.getProfesseur().getId() + "' " +
                                    "WHERE (student_id = '" + e2.getEleve().getID() + "' AND `index` = " + eval_index + ")";
                            stmt.executeQuery(requete);
                        }
                        eval_index++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) stmt.close();
        }
    }

}
