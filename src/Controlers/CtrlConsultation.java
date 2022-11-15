package Controlers;

import Entities.Consultation;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlConsultation
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlConsultation() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Consultation> GetAllConsultations() {
        ArrayList<Consultation> lesConsultations = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("SELECT consultation.idConsult, consultation.dateConsult, patient.nomPatient, medecin.nomMedecin, SUM(medicament.prixMedoc * prescrire.quantite - medicament.prixMedoc * prescrire.quantite * vignette.tauxRemb / 100) AS montant FROM consultation, medecin, patient, medicament, prescrire, vignette WHERE consultation.numPatient = patient.idPatient AND consultation.numMedecin = medecin.idMedecin AND prescrire.numConsult = consultation.idConsult AND prescrire.numMedoc = medicament.idMedoc AND medicament.numVignette = vignette.idVignette GROUP BY consultation.idConsult ORDER BY consultation.idConsult;");
            rs = ps.executeQuery();
            while (rs.next()) {
                Consultation uneConsultation = new Consultation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5));
                lesConsultations.add(uneConsultation);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlConsultation.class.getName()).log(Level.SEVERE, null, ex);

        }
        return lesConsultations;
    }
    public int getLastNumberOfConsultation() throws SQLException {
        int lastIdPlus1 = 0;
        ps = cnx.prepareStatement("SELECT MAX(consultation.idConsult) + 1 FROM consultation;");
        rs = ps.executeQuery();
        while (rs.next()){
            lastIdPlus1 = rs.getInt(1);
        }
        return lastIdPlus1;
    }
    public void InsertConsultation(int idConsult, String dateConsultation, int numPatient,int numMedecin) throws SQLException {
        ps = cnx.prepareStatement("INSERT INTO consultation VALUES (?,?,?,?);");
        ps.setInt(1, idConsult);
        ps.setString(2, dateConsultation);
        ps.setInt(3, numPatient);
        ps.setInt(4, numMedecin);

        ps.execute();
    }
}
