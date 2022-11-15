package Tools;



import Entities.Consultation;
import Entities.Medicament;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelJTable extends AbstractTableModel {
    private String[] colonnes;
    private Object[][] lignes;

    @Override
    public String getColumnName(int column) {
        return colonnes[column];
    }

    @Override
    public int getRowCount() {
        return lignes.length;
    }

    @Override
    public int getColumnCount() {
        return colonnes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return lignes[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        lignes[row][column] = value;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 3 ;
    }

    public void loadDatasConsultations(ArrayList<Consultation> lesConsultations){
        colonnes = new String[]{"Numéro", "Date", "Nom du patient", "Nom du médecin", "Montant"};
        lignes = new Object[lesConsultations.size()][5];
        int i = 0;
        for (Consultation consultation : lesConsultations) {
            lignes[i][0] = consultation.getNumero();
            lignes[i][1] = consultation.getDate();
            lignes[i][2] = consultation.getNomPatient();
            lignes[i][3] = consultation.getNomMedecin();
            lignes[i][4] = consultation.getMontant();
            i++;
        }
        fireTableChanged(null);
    }

    public void loadDatasMedicaments(ArrayList<Medicament> lesMedicaments){
        colonnes = new String[]{"Numéro", "Nom", "Prix"};
        lignes = new Object[lesMedicaments.size()][3];
        int i = 0;
        for (Medicament medicament : lesMedicaments) {
            lignes[i][0] = medicament.getNumero();
            lignes[i][1] = medicament.getNom();
            lignes[i][2] = medicament.getPrix();
            i++;
        }
        fireTableChanged(null);
    }

    public void loadDatasMedicaments2(ArrayList<Medicament> lesMedicaments){
        colonnes = new String[]{"Numéro", "Nom", "Prix", "Quantité"};
        lignes = new Object[lesMedicaments.size()][4];
        int i = 0;
        for (Medicament medicament : lesMedicaments) {
            lignes[i][0] = medicament.getNumero();
            lignes[i][1] = medicament.getNom();
            lignes[i][2] = medicament.getPrix();
            lignes[i][3] = medicament.getQuantite();
            i++;
        }
        fireTableChanged(null);
    }

}
