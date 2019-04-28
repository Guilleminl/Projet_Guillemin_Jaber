import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BulletinSwing extends JFrame {
    private JPanel panelBase;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton fermerBouton;
    private JButton afficherGraph;

    public BulletinSwing(JTable table, String titre, String[] sujets, Object[][] donnees, String[] colonnes){
        super(titre);
        this.table = table;
        //Resizing table's column to fit data
        changerTailleColonnes();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        //JPanel definition
        panelBase = new JPanel();
        panelBase.setLayout(new FlowLayout());
        //To divide the screen in 3/4 for the table and 1/4 for the buttons, we need to
        //add 2 panels to the layout of the root panel, with prefered size
        JPanel tablePanel = new JPanel();
        JPanel boutonsPanel = new JPanel();

        //For the tablePanel, we can add a gird layout with 1 column and 1 line,
        // which will take all space available for the table
        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
        tablePanel.setPreferredSize(new Dimension(680,250));
        //ScrollPane definition
        this.scrollPane = new JScrollPane(this.table);
        this.scrollPane.setPreferredSize(new Dimension(680,250));
        //Set the scrollPane to be
        //Add the scrollPane to the tablePanel
        tablePanel.add(scrollPane);

        //For the button Panel, we want the two button to be centered and have a little gap between them
        //For that we use a flowlayout with center attribute
        boutonsPanel.setPreferredSize(new Dimension(700,50));
        boutonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100,0));
        //Buttons definition
        this.fermerBouton = new JButton("Fermer");
        this.afficherGraph = new JButton("Graphique");
        //We want the buttons to have the same size for design purpose
        this.fermerBouton.setPreferredSize(new Dimension(100,50));
        this.afficherGraph.setPreferredSize(new Dimension(100,50));
        //Add the effects on click of the buttons
        this.fermerBouton.addActionListener(e -> System.exit(0));
        this.afficherGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                montrerGraph(sujets, donnees, colonnes);
            }
        });
        //Add the buttons to the buttonsPanel
        boutonsPanel.add(afficherGraph);
        boutonsPanel.add(fermerBouton);

        //Now add the subpanels to the root panel
        panelBase.add(tablePanel);
        panelBase.add(boutonsPanel);

        this.add(panelBase, BorderLayout.CENTER);
        //Set the size of the frame
        this.setSize(700,400);

    }
    public void changerTailleColonnes() {
        final TableColumnModel modeleColonne = table.getColumnModel();
        for (int colonne = 0; colonne < table.getColumnCount(); colonne++) {
            int largeur = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, colonne);
                Component comp = table.prepareRenderer(renderer, row, colonne);
                largeur = Math.max(comp.getPreferredSize().width +1 , largeur);
            }
            if(largeur > 300)
                largeur=300;
            modeleColonne.getColumn(colonne).setPreferredWidth(largeur);
        }
    }
    public void montrerGraph(String[] sujet, Object[][] donnee, String[] colonne){
        JFrame fenetre = new JFrame();
        fenetre.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //Creating the Chart and adding it to a frame
        JPanel panel = new JPanel();
        DefaultCategoryDataset setDonnees = new DefaultCategoryDataset();
        for(int i = 0 ; i < sujet.length ; i++){
            for(int j = 1; j < colonne.length ; j++){
                setDonnees.setValue((double)donnee[i][j], colonne[j], sujet[i]);
            }
        }
        JFreeChart chart = ChartFactory.createBarChart("Resume","Sujet",
                "Note/20", setDonnees, PlotOrientation.VERTICAL, true,true,false);
        ChartPanel CP = new ChartPanel(chart);
        panel.add(CP);
        panel.validate();
        fenetre.add(panel);
        fenetre.pack();
        fenetre.setVisible(true);
    }
}
