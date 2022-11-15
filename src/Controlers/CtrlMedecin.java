package Controlers;

import Entities.Medicament;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlMedecin
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlMedecin() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<String> getAllMedecins() throws SQLException {
        ArrayList<String> lesNomsDesMedecins = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT nomMedecin FROM medecin;");
        rs = ps.executeQuery();
        while (rs.next()){
            String unNom = rs.getString(1);
            lesNomsDesMedecins.add(unNom);
        }
        ps.close();
        rs.close();

        return lesNomsDesMedecins;
    }

    public int getIdMedecinByName(String nomMed) throws SQLException {
        int idMedecin = 0;
        ps = cnx.prepareStatement("SELECT idMedecin FROM medecin WHERE nomMedecin like ?;");
        ps.setString(1, nomMed);
        rs = ps.executeQuery();
        while (rs.next()){
            idMedecin = rs.getInt(1);
        }
        return idMedecin;
    }
}
