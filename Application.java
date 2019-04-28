import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class Application extends JFrame {
    String IDEleve;
    String NomPromo;
    Professeur professeur;
    Set<Promotion> promo;
    Set<Professeur> profs;

    String NomEleveAjouter;
    String FamilleEleveAjouter;
    String PromoEleveAjouter;

    JFrame ajouterEtudiantFenetre;
    JFrame noteEleveFenetre;

    String sujetNoteEleve;
    String buffer;
    float noteNoteEleve;
    int indexNoteEleve;

    Application(Set<Professeur> profs, Set<Promotion> promo){
        this.promo = promo;
        this.profs = profs;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //On veut sauvegarder la base de donnée à la fin
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sauverBDD();
                quitter();
            }
        });
        this.setLayout(new BorderLayout());
        this.setSize(650,500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        //We will have a table with all the students , the information of the lecturer and a panel to add a note/see the student
        JPanel profePanel = new JPanel();
        JPanel profInfo = new JPanel();
        JPanel tableauEleve = new JPanel();
        JPanel panelNote = new JPanel();
        JPanel Panel = new JPanel();

        profePanel.setPreferredSize(new Dimension(490,490));
        profInfo.setPreferredSize(new Dimension(490,90));
        tableauEleve.setPreferredSize(new Dimension(300,400));
        panelNote.setPreferredSize(new Dimension(150,400));
        Panel.setPreferredSize(new Dimension(490,400));

        profePanel.setLayout(new BorderLayout());
        Panel.setLayout(new BorderLayout());
        String idProf = JOptionPane.showInputDialog(null,"ID prof");
        professeur = null;
        for(Professeur p : profs) {
            if (p.getId() == Integer.parseInt(idProf)){
                professeur = p;
                break;
            }
        }
        professeur.setPromotions(promo);
        //LecturerInfo panel
        profInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 20,20));

        JLabel prenom = new JLabel(professeur.getPrenom());
        JLabel nomFamille = new JLabel(professeur.getNomFamille());
        prenom.setPreferredSize(new Dimension(80,50));
        nomFamille.setPreferredSize(new Dimension(80,50));

        profInfo.add(prenom);
        profInfo.add(nomFamille);

        profePanel.add(profInfo,BorderLayout.NORTH);

        //Lecturer table
        //We need a table with field name, last name, promotion, button to see student info, button to add a note to the student
        //Data array
        int nombre_eleve = 0;
        for(Promotion p : professeur.getPromotions()){
            for(Eleve e : p.getEleves()){
                nombre_eleve++;
            }
        }
        Object[][] donneeTableProf = new Object[nombre_eleve][4];
        //The column array
        String[] columnsLecturerTable = {"Prenom", "Nom de Famille", "Promotion","ID"};
        //Set the data
        nombre_eleve = 0;
        for(Promotion p : professeur.getPromotions()){
            for(Eleve e : p.getEleves()){
                donneeTableProf[nombre_eleve][0] = e.getPrenom();
                donneeTableProf[nombre_eleve][1] = e.getNomFamille();
                donneeTableProf[nombre_eleve][2] = p.getNomPromotion();
                donneeTableProf[nombre_eleve][3] = e.getID();
                nombre_eleve++;
            }
        }
        JTable tableLecturer = new JTable(donneeTableProf,columnsLecturerTable);
        tableLecturer.setAutoCreateRowSorter(true);
        JScrollPane SP = new JScrollPane(tableLecturer);

        tableauEleve.setLayout(new FlowLayout(FlowLayout.CENTER));
        tableauEleve.add(SP);

        Panel.add(tableauEleve, BorderLayout.CENTER);


        // Mark panel
        panelNote.setLayout(new FlowLayout(FlowLayout.CENTER, 25,5));
        //setMarkPanel.setPreferredSize(new Dimension(150,400));
        //Will require to input the student ID and his promotion, then press a button to either add a note, or see his marks
        JLabel studentIdLabel = new JLabel("Eleve ID:");
        JTextField studentId = new JTextField();
        JLabel studentPromotionLabel = new JLabel("Promotion eleve:");
        JTextField studentPromotion = new JTextField();
        JButton addNote = new JButton("Ajouter note");
        JButton addStudent = new JButton("Ajouter eleve");


        studentId.setPreferredSize(new Dimension(100,30));
        studentIdLabel.setPreferredSize(new Dimension(100,50));
        studentPromotion.setPreferredSize(new Dimension(100,30));
        studentPromotionLabel.setPreferredSize(new Dimension(100,50));
        addNote.setPreferredSize(new Dimension(100,50));
        addStudent.setPreferredSize(new Dimension(100,50));

        addNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IDEleve = studentId.getText();
                NomPromo = studentPromotion.getText();
                noteEleve(IDEleve, NomPromo, professeur);
            }
        });
        addStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterEleve();
            }
        });

        panelNote.add(studentIdLabel);
        panelNote.add(studentId);
        panelNote.add(studentPromotionLabel);
        panelNote.add(studentPromotion);
        panelNote.add(addNote);
        panelNote.add(addStudent);

        Panel.add(panelNote, BorderLayout.EAST);
        profePanel.add(Panel);

        this.getContentPane().removeAll();
        this.add(profePanel);
        this.setVisible(true);
    }
    public void ajouterEleve(){
        JFrame ajouterEleve = new JFrame();
        ajouterEtudiantFenetre = ajouterEleve;
        ajouterEleve.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ajouterEleve.setSize(300,300);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        ajouterEleve.setLocation(dim.width/2-ajouterEleve.getSize().width/2, dim.height/2-ajouterEleve.getSize().height/2);
        ajouterEleve.setLayout(new FlowLayout(FlowLayout.CENTER,50,10));

        JLabel prenom = new JLabel("Prenom");
        JLabel nomFamille = new JLabel("Nom Famille");
        JLabel Promotion = new JLabel("Promotion");

        JTextField prenomInput = new JTextField();
        JTextField familleInput = new JTextField();
        Object[] promotionInput = new Object[promo.size()];
        int i = 0;
        for(Promotion p : this.promo){
            promotionInput[i] = p.getNomPromotion();
        }
        JComboBox promotionInputDropdown = new JComboBox(promotionInput);

        JButton confirm = new JButton("Confirmer");
        prenom.setPreferredSize(new Dimension(50,30));
        nomFamille.setPreferredSize(new Dimension(50,30));
        Promotion.setPreferredSize(new Dimension(50,30));
        prenomInput.setPreferredSize(new Dimension(100,30));
        familleInput.setPreferredSize(new Dimension(100,30));
        promotionInputDropdown.setPreferredSize(new Dimension(100,30));
        confirm.setPreferredSize(new Dimension(100,30));

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    NomEleveAjouter = prenomInput.getText();
                    FamilleEleveAjouter = familleInput.getText();
                    PromoEleveAjouter = (String)promotionInputDropdown.getSelectedItem();
                    for(Promotion p : promo){
                        System.out.println(p.getNomPromotion() + " " + PromoEleveAjouter);
                        if(p.getNomPromotion().equals(PromoEleveAjouter)){
                            p.ajouterEleve(new Eleve(p.maxID()+1,FamilleEleveAjouter,NomEleveAjouter,0,1,1,p));
                            ajouterEtudiantFenetre.dispose();
                        }
                    }
                } catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });

        ajouterEleve.add(prenom);
        ajouterEleve.add(prenomInput);
        ajouterEleve.add(nomFamille);
        ajouterEleve.add(familleInput);
        ajouterEleve.add(Promotion);
        ajouterEleve.add(promotionInputDropdown);
        ajouterEleve.add(confirm);

        ajouterEleve.pack();
        ajouterEleve.setVisible(true);
    }
    public void noteEleve(String ID, String NomPromo, Professeur prof){
        Eleve eleve = prof.chercherEleve(ID,NomPromo);
        if(eleve==null)
            JOptionPane.showMessageDialog(null,"Etudiant non trouvé");
        else{
            JFrame noteEleve = new JFrame();
            noteEleveFenetre = noteEleve;
            noteEleve.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            noteEleve.setSize(300,300);

            noteEleve.setLayout(new FlowLayout(FlowLayout.CENTER,50,10));

            JLabel sujet = new JLabel("Sujet");
            JLabel note = new JLabel("Note");
            JLabel index = new JLabel("Note Index");

            JTextField sujetInput = new JTextField();
            JTextField noteInput = new JTextField();
            Object[] indexInput = new Object[eleve.getEvaluations().size()+1];
            for(int i = 0 ; i < indexInput.length-1 ; i++){
                indexInput[i] = "" + i;
            }
            indexInput[indexInput.length-1] = "ajouter";
            JComboBox indexInputSelection = new JComboBox(indexInput);

            JButton confirmer = new JButton("Confirmer");
            sujet.setPreferredSize(new Dimension(50,30));
            note.setPreferredSize(new Dimension(50,30));
            index.setPreferredSize(new Dimension(50,30));
            sujetInput.setPreferredSize(new Dimension(100,30));
            noteInput.setPreferredSize(new Dimension(100,30));
            indexInputSelection.setPreferredSize(new Dimension(100,30));
            confirmer.setPreferredSize(new Dimension(100,30));

            confirmer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        sujetNoteEleve = sujetInput.getText();
                        buffer = noteInput.getText();
                        noteNoteEleve = Float.parseFloat((String)buffer);
                        buffer = (String)indexInputSelection.getSelectedItem();
                        if("ajouter".equals((String)buffer)){
                            indexNoteEleve = indexInput.length;
                        }
                        else
                            indexNoteEleve = Integer.parseInt((String)buffer);
                        professeur.mettreNote(NomPromo,ID,noteNoteEleve,indexNoteEleve,sujetNoteEleve);
                        noteEleveFenetre.dispose();
                    } catch (NumberFormatException error){
                        JOptionPane.showMessageDialog(null,"Error in inputs","Error",JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            noteEleve.add(sujet);
            noteEleve.add(sujetInput);
            noteEleve.add(note);
            noteEleve.add(noteInput);
            noteEleve.add(index);
            noteEleve.add(indexInputSelection);
            noteEleve.add(confirmer);
            noteEleve.setVisible(true);
        }
    }
    public void sauverBDD(){
        ConnexionDatabase dbConnection;
        Connection connexion;
        try{
            dbConnection = new ConnexionDatabase();
            connexion = dbConnection.getConnection();
            automaticSQL auto = new automaticSQL(connexion);
            auto.toutSauvegarder(this.promo,this.profs);
        } catch (SQLException e) { e.printStackTrace();}
        catch (ClassNotFoundException e2) { e2.printStackTrace(); }
        finally {

        }
    }
    public void quitter(){
        this.dispose();
        System.exit(0);
    }
}
