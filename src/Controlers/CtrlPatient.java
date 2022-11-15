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

public class CtrlPatient
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlPatient() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<String> getAllPatients() throws SQLException {
        ArrayList<String> lesNomsDesPatients = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT nomPatient FROM patient;");
        rs = ps.executeQuery();
        while (rs.next()){
            String unNom = rs.getString(1);
            lesNomsDesPatients.add(unNom);
        }
        ps.close();
        rs.close();

        return lesNomsDesPatients;
    }
    public int getIdPatientByName(String nomPat) throws SQLException {
        int idPatient = 0;
        ps = cnx.prepareStatement("SELECT idPatient FROM patient WHERE nomPatient like ?;");
        ps.setString(1, nomPat);
        rs = ps.executeQuery();
        while (rs.next()){
            idPatient = rs.getInt(1);
        }
        return idPatient;
    }
}
