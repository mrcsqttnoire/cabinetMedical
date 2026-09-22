package dao;

import java.sql.*;
import java.time.LocalDate;

import controller.mainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.consultation.Consultation;
import model.patient_class.Patient;
import model.rendez_vous.RendezVous;

public class ConsultationDao {
    private Connection conn;

    public ConsultationDao() {
        try {
            conn = db.DBConnection.getConnection();
        } catch (Exception e) {
            mainController.showAlert("Erreur : " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public boolean ajouterConsultation(Consultation consultation) {
        String sql = "INSERT INTO consultation(date_consultation, diagnostic, tension, temperature, poid, id_patient, id_rdv) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, consultation.getDateConsultation());
            stmt.setString(2, consultation.getDiagnostique());
            stmt.setString(3, consultation.getTension());
            stmt.setString(4, consultation.getTemperature());
            stmt.setString(5, consultation.getPoid());
            stmt.setObject(6, consultation.getPatient().getId());
            if (consultation.getRendezVous() != null) {
                stmt.setObject(7, consultation.getRendezVous().getId());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            System.out.println("Consultation ajoutée avec succès");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean modifierConsultation(Consultation consultation) {
        String sql = "UPDATE consultation SET date_consultation = ?, diagnostic = ?, tension = ?,temperature = ?, poid = ?, id_patient = ?, id_rdv = ? WHERE id_consultation = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, consultation.getDateConsultation());
            stmt.setString(2, consultation.getDiagnostique());
            stmt.setString(3, consultation.getTension());
            stmt.setString(4, consultation.getTemperature());
            stmt.setString(5, consultation.getPoid());
            stmt.setObject(6, consultation.getPatient().getId());
            if (consultation.getRendezVous() != null) {
                stmt.setObject(7, consultation.getRendezVous().getId());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }
            stmt.setInt(8, consultation.getId());

            System.out.println("Consultation modifiée avec succès");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean supprimerConsultation(Consultation consultaion) {
        String sql = "DELETE FROM consultation WHERE id_consultation = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, consultaion.getId());
            System.out.println("Consultation supprimé");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private ResultSet result;

    public ObservableList<Consultation> consultationGetData() {
        ObservableList<Consultation> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM consultation";
        try {
            Statement query = conn.createStatement();
            result = query.executeQuery(sql);

            Consultation c;

            while (result.next()) {
                int idRdv = result.getInt("id_rdv");
                boolean isRdvNull = result.wasNull();

                int idPatient = result.getInt("id_patient");
                Patient patient = new PatientDao().findById(idPatient);

                c = new Consultation(
                        result.getObject("date_consultation",
                                LocalDate.class),
                        result.getString("diagnostic"),
                        result.getString("tension"),
                        result.getString("temperature"),
                        result.getString("poid"),
                        patient);
                c.setId(result.getInt("id_consultation"));
                if (!isRdvNull) {
                    RendezVous rdv = new RendezVousDao().findById(idRdv);
                    c.setRendezVous(rdv);
                }

                listData.add(c);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }

        return listData;

    }

    public Consultation findById(int id) {
        Consultation c = null;
        String sql = "SELECT * FROM consultation WHERE id_consultation = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                int idPatient = result.getInt("id_patient");
                Patient patient = new PatientDao().findById(idPatient);

                int idRdv = result.getInt("id_rdv");
                boolean isRdvNull = result.wasNull();

                c = new Consultation(
                        result.getObject("date_consultation",
                                LocalDate.class),
                        result.getString("diagnostic"),
                        result.getString("tension"),
                        result.getString("temperature"),
                        result.getString("poid"),
                        patient);
                c.setId(result.getInt("id_consultation"));
                if (!isRdvNull) {
                    RendezVous rdv = new RendezVousDao().findById(idRdv);
                    c.setRendezVous(rdv);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }
}
