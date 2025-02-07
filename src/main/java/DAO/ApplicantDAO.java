package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB.DatabaseUtil;
import bean.Applicant;

public class ApplicantDAO {

    public static boolean addApplicant(int memberId, boolean agree) {
        String sql = "INSERT INTO applicant (member_id, applied_at, agree) VALUES (?, NOW(), ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            stmt.setBoolean(2, agree);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding applicant with member ID " + memberId);
            e.printStackTrace();
            return false;
        }
    }

    public List<Applicant> getAllPendingApplicants() {
        String sql = "SELECT* FROM get_pending_applicants()";
        List<Applicant> applicants = new ArrayList<>();

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Applicant applicant = new Applicant(
                		rs.getInt("member_id"),
                		rs.getString("member_name"),
                		rs.getInt("role"),
                		rs.getInt("application_id"),
                		rs.getBoolean("agree"),
                		rs.getBoolean("approve")
                );
                applicants.add(applicant);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving applicants");
            e.printStackTrace();
        }
        return applicants;
    }

    public boolean approveApplicantApplication(int applicationId) {
        String updateApplicantSql = "UPDATE applicant SET approve = TRUE WHERE id = ?";
        String updateMemberSql = "UPDATE member SET role_id = 3 WHERE id = (SELECT member_id FROM applicant WHERE id = ?)";

        try (Connection connection = DatabaseUtil.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt1 = connection.prepareStatement(updateApplicantSql);
                 PreparedStatement stmt2 = connection.prepareStatement(updateMemberSql)) {

                stmt1.setInt(1, applicationId);
                stmt2.setInt(1, applicationId);

                int updatedApplicant = stmt1.executeUpdate();
                int updatedMember = stmt2.executeUpdate();

                if (updatedApplicant > 0 && updatedMember > 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error approving applicant with ID " + applicationId);
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean rejectApplicantApplication(int applicationId) {
        // Your database logic for rejecting the application
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "UPDATE applicant SET approve = False WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, applicationId);
                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getApplicationStatus(int memberId) {
        String sql = "SELECT approve FROM applicant WHERE member_id = ?";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, memberId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Boolean approve = rs.getObject("approve", Boolean.class);
                    
                    if (approve == null) {
                        return 1; 
                    } else if (!approve) {
                        return 2; 
                    } else {
                        return 3; 
                    }
                } else {
                    return 3; 
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking application status for member ID " + memberId);
            e.printStackTrace();
            return -1; // Return -1 for any SQL exception as an error indicator
        }
    }

}

