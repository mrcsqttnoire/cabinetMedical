package dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import controller.mainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.consultation.Consultation;
import model.patient_class.Patient;
import model.prescription.Prescription;
import model.rendez_vous.RendezVous;

public class PrescriptionDao {
    private Connection conn;

    public PrescriptionDao() {
        try {
            conn = db.DBConnection.getConnection();
        } catch (Exception e) {
            mainController.showAlert("Erreur : " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public boolean ajouterPrescription(Prescription prescription) {
        String sql = "INSERT INTO prescription(medicament, duree, instructions, id_consultation) VALUES (?,?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, prescription.getMedicament());
            stmt.setString(2, prescription.getDuree());
            stmt.setObject(3, prescription.getInstruction());
            if (prescription.getConsultation() != null) {
                stmt.setObject(4, prescription.getConsultation().getId());
            }

            System.out.println("prescription ajoutée avec succès");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean modifierPrescription(Prescription prescription) {
        String sql = "UPDATE prescription SET medicament = ?,duree = ? ,instructions = ?,id_consultation = ? WHERE id_ligne = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, prescription.getMedicament());
            stmt.setString(2, prescription.getDuree());
            stmt.setObject(3, prescription.getInstruction());
            stmt.setObject(4, prescription.getConsultation().getId());
            stmt.setInt(5, prescription.getId());

            System.out.println("Préscription modifiée avec succès");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean supprimerPrescription(Prescription prescription) {
        String sql = "DELETE FROM prescription WHERE id_ligne = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, prescription.getId());
            System.out.println("Préscription supprimé");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private ResultSet result;

    public ObservableList<Prescription> prescriptionGetData(Consultation c) {
        ObservableList<Prescription> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM prescription WHERE id_consultation = ?";
        try {
            PreparedStatement query = conn.prepareStatement(sql);
            query.setInt(1, c.getId());
            result = query.executeQuery();

            Prescription p;

        while (result.next()) {
            p = new Prescription(
                result.getString("medicament"),
                result.getString("duree"),
                result.getString("instructions"),
                c
            );
            p.setId(result.getInt("id_ligne"));
            listData.add(p);
        }

        } catch (Exception err) {
            err.printStackTrace();
        }

        return listData;
    }

    // public Prescription findById(int id) {
    //     Consultation c = null;
    //     String sql = "SELECT * FROM consultation WHERE id_consultation = ?";

    //     try {
    //         PreparedStatement stmt = conn.prepareStatement(sql);
    //         stmt.setInt(1, id);
    //         ResultSet result = stmt.executeQuery();

    //         if (result.next()) {
    //             int idPatient = result.getInt("id_patient");
    //             Patient patient = new PatienDao().findById(idPatient);

    //             int idRdv = result.getInt("id_rdv");
    //             boolean isRdvNull = result.wasNull();

    //             c = new Consultation(result.getObject("date_consultation", LocalDate.class),
    //                     result.getString("diagnostic"), patient);
    //             if (!isRdvNull) {
    //                 RendezVous rdv = new RendezVousDao().findById(idRdv);
    //                 c.setRendezVous(rdv);
    //             }
    //         }

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }

    //     return c;
    // }
}
