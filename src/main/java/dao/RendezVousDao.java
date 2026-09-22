package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import controller.mainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.patient_class.Patient;
import model.rendez_vous.RendezVous;

public class RendezVousDao {
    private Connection conn;

    public RendezVousDao() {
        try {
            conn = db.DBConnection.getConnection();
        } catch (Exception e) {
            mainController.showAlert("Erreur : " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public boolean ajouterRdv(RendezVous rdv) {
        String sql = "INSERT INTO rendez_vous(date_rdv, heure_rdv, motif, statut, id_patient) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, rdv.getDateRdv());
            stmt.setObject(2, rdv.getHeureRdv());
            stmt.setString(3, rdv.getMotif());
            stmt.setString(4, rdv.getStatus());
            stmt.setObject(5, rdv.getPatient().getId());

            System.out.println("Rendez-vous ajouté avec succès");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean modifierRdv(RendezVous rdv) {
        String sql = "UPDATE rendez_vous SET date_rdv = ?, heure_rdv = ?, motif = ?, statut = ?, id_patient = ? WHERE id_rdv = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, rdv.getDateRdv());
            stmt.setObject(2, rdv.getHeureRdv());
            stmt.setString(3, rdv.getMotif());
            stmt.setString(4, rdv.getStatus());
            stmt.setObject(5, rdv.getPatient().getId());
            stmt.setInt(6, rdv.getId());

            System.out.println("Rendez-vous modifié avec succès");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean supprimerRendezVous(RendezVous rdv) {
        String sql = "DELETE FROM rendez_vous WHERE id_rdv = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, rdv.getId());
            System.out.println("Rendez-vous supprimé");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private ResultSet result;

    public ObservableList<RendezVous> rendezVousGetData() {
        ObservableList<RendezVous> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM rendez_vous ORDER BY CASE WHEN statut = 'Planifié' THEN 1 WHEN statut = 'Honoré' THEN 2 END, date_rdv, heure_rdv ASC";
        try {
            Statement query = conn.createStatement();
            result = query.executeQuery(sql);

            RendezVous rdv;

            while (result.next()) {
                int idPatient = result.getInt("id_patient");
                Patient patient = new PatientDao().findById(idPatient);
                rdv = new RendezVous(result.getObject("date_rdv", LocalDate.class),
                        result.getObject("heure_rdv", LocalTime.class), result.getString("motif"),
                        result.getString("statut"), patient);
                rdv.setId(result.getInt("id_rdv"));
                listData.add(rdv);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }

        return listData;

    }

    public RendezVous findById(int id) {
        RendezVous rdv = null;
        String sql = "SELECT * FROM rendez_vous WHERE id_rdv = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                int idPatient = result.getInt("id_patient");
                Patient patient = new PatientDao().findById(idPatient);

                rdv = new RendezVous(
                        result.getObject("date_rdv", LocalDate.class),
                        result.getObject("heure_rdv", LocalTime.class),
                        result.getString("motif"),
                        result.getString("statut"),
                        patient);
                rdv.setId(result.getInt("id_rdv"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rdv;
    }

}
