package Controlers;

import Entities.Consultation;
import Entities.Medicament;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlMedicament
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlMedicament() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Medicament> GetAllMedicamentsByIdConsultations(int idConsultation) throws SQLException {
        ArrayList<Medicament> lesMedicaments = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("SELECT medicament.idMedoc, medicament.nomMedoc, medicament.prixMedoc FROM medicament, prescrire, consultation WHERE consultation.idConsult = prescrire.numConsult AND medicament.idMedoc = prescrire.numMedoc AND consultation.idConsult = ?;");
            ps.setInt(1, idConsultation);
            rs = ps.executeQuery();
            while (rs.next()) {
                Medicament unMedicament = new Medicament(rs.getInt(1), rs.getString(2), rs.getDouble(3));
                lesMedicaments.add(unMedicament);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlMedicament.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesMedicaments;
    }

    public ArrayList<Medicament> getAllMedicaments() throws SQLException {
        ArrayList<Medicament> lesMedicaments = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT medicament.idMedoc, medicament.nomMedoc, medicament.prixMedoc FROM medicament;");
        rs = ps.executeQuery();
        while (rs.next()){
            Medicament unMedicament = new Medicament(rs.getInt(1), rs.getString(2), rs.getDouble(3), 0);
            lesMedicaments.add(unMedicament);
        }
        ps.close();
        rs.close();

        return lesMedicaments;
    }
}
