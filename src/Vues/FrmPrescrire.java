package Vues;

import Controlers.*;
import Tools.ModelJTable;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmPrescrire extends JFrame
{
    private JPanel pnlRoot;
    private JLabel lblTitre;
    private JLabel lblNumero;
    private JLabel lblDate;
    private JLabel lblNomMedecin;
    private JTextField txtNumeroConsultation;
    private JComboBox cboPatients;
    private JComboBox cboMedecins;
    private JButton btnInserer;
    private JTable tblMedicaments;
    private JPanel pnlDate;
    private JLabel lblNomPatient;
    private JLabel lblMedicaments;
    private JDateChooser dcDateConsultation;
    private CtrlConsultation ctrlConsultation;
    private CtrlMedicament ctrlMedicament;
    private CtrlMedecin ctrlMedecin;
    private CtrlPatient ctrlPatient;
    private CtrlPrescrire ctrlPrescrire;
    private ModelJTable modelJTable;

    public FrmPrescrire()
    {
        this.setTitle("Prescrire");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        ctrlConsultation = new CtrlConsultation();
        ctrlMedicament = new CtrlMedicament();
        ctrlMedecin = new CtrlMedecin();
        ctrlPatient = new CtrlPatient();
        ctrlPrescrire = new CtrlPrescrire();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                dcDateConsultation = new JDateChooser();
                dcDateConsultation.setDateFormatString("yyyy-MM-dd");
                pnlDate.add(dcDateConsultation);

                // A vous de jouer
                try {
                    txtNumeroConsultation.setText(""+ctrlConsultation.getLastNumberOfConsultation()+"");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                modelJTable = new ModelJTable();
                try {
                    modelJTable.loadDatasMedicaments2(ctrlMedicament.getAllMedicaments());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                tblMedicaments.setModel(modelJTable);

                try {
                    for (String nomMedecin : ctrlMedecin.getAllMedecins()){
                        cboMedecins.addItem(nomMedecin);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    for (String nomPatient : ctrlPatient.getAllPatients()){
                        cboPatients.addItem(nomPatient);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnInserer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // A vous de jouer
                if (dcDateConsultation.getDate() == null){
                    JOptionPane.showMessageDialog(null, "Veuillez entrer une date !");
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateConsult = sdf.format(dcDateConsultation.getDate());
                    int idPatient = 0;
                    int idMedecin = 0;
                    try {
                        idPatient = ctrlPatient.getIdPatientByName(cboPatients.getSelectedItem().toString());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        idMedecin = ctrlMedecin.getIdMedecinByName(cboMedecins.getSelectedItem().toString());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        ctrlConsultation.InsertConsultation(Integer.parseInt(txtNumeroConsultation.getText()), dateConsult, idPatient, idMedecin);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    for(int i=0;i<tblMedicaments.getRowCount();i++)
                        if (Integer.parseInt((String) tblMedicaments.getValueAt(i ,3).toString()) != 0){
                            try {
                                ctrlPrescrire.InsertPrescrire(Integer.parseInt(txtNumeroConsultation.getText()), Integer.parseInt((String) tblMedicaments.getValueAt(i ,0).toString()), (int) Double.parseDouble((String) tblMedicaments.getValueAt(i ,3).toString()));
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    dispose();
                    FrmPrescrire frmPrescrire = new FrmPrescrire();
                    frmPrescrire.setVisible(true);

                }

            }
        });
    }
}
