package studentmanagement.dao;

import studentmanagement.DBConnection;
import studentmanagement.model.Marks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarksDAO {

    // =========================================================
    // ADD MARKS
    // =========================================================
    public boolean add(Marks mark) {

        String column = getSubjectColumn(mark.getSubjectId());

        if (column == null) {
            System.out.println(
                    "Invalid subject ID: " + mark.getSubjectId()
            );
            return false;
        }

        /*
         * First check whether this student already has
         * a marks record.
         */
        String checkSql =
                "SELECT mark_id FROM marks " +
                        "WHERE student_id = ? " +
                        "ORDER BY mark_id LIMIT 1";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement checkStmt =
                        conn.prepareStatement(checkSql)
        ) {

            checkStmt.setInt(1, mark.getStudentId());

            try (ResultSet rs = checkStmt.executeQuery()) {

                if (rs.next()) {

                    int markId =
                            rs.getInt("mark_id");

                    return updateSubjectMark(
                            markId,
                            column,
                            mark.getMarks()
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println("Error checking existing marks:");
            e.printStackTrace();
            return false;
        }

        // No existing record → create one
        String insertSql =
                "INSERT INTO marks (student_id, " +
                        column + ") VALUES (?, ?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(insertSql)
        ) {

            stmt.setInt(
                    1,
                    mark.getStudentId()
            );

            stmt.setDouble(
                    2,
                    mark.getMarks()
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error adding marks:");
            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // GET ALL MARKS
    // =========================================================
    public List<Marks> getAll() {

        List<Marks> marksList =
                new ArrayList<>();

        String sql =
                "SELECT mark_id, student_id, " +
                        "organization, operating_system, " +
                        "oop, networking, ethics " +
                        "FROM marks " +
                        "ORDER BY mark_id";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql);
                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                addMarkIfExists(
                        marksList,
                        rs,
                        2,
                        "oop"
                );

                addMarkIfExists(
                        marksList,
                        rs,
                        3,
                        "ethics"
                );

                addMarkIfExists(
                        marksList,
                        rs,
                        4,
                        "operating_system"
                );

                addMarkIfExists(
                        marksList,
                        rs,
                        5,
                        "organization"
                );

                /*
                 * networking currently has no matching
                 * subject in your subjects table.
                 *
                 * We intentionally do not display it
                 * until a subject ID is assigned to it.
                 */
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading all marks:"
            );

            e.printStackTrace();
        }

        return marksList;
    }


    // =========================================================
    // GET MARKS BY STUDENT ID
    // =========================================================
    public List<Marks> getMarksByStudentId(
            int studentId
    ) {

        List<Marks> marksList =
                new ArrayList<>();

        String sql =
                "SELECT mark_id, student_id, " +
                        "organization, operating_system, " +
                        "oop, networking, ethics " +
                        "FROM marks " +
                        "WHERE student_id = ? " +
                        "ORDER BY mark_id";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    studentId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                while (rs.next()) {

                    addMarkIfExists(
                            marksList,
                            rs,
                            2,
                            "oop"
                    );

                    addMarkIfExists(
                            marksList,
                            rs,
                            3,
                            "ethics"
                    );

                    addMarkIfExists(
                            marksList,
                            rs,
                            4,
                            "operating_system"
                    );

                    addMarkIfExists(
                            marksList,
                            rs,
                            5,
                            "organization"
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading student's marks:"
            );

            e.printStackTrace();
        }

        return marksList;
    }


    // =========================================================
    // ADD MARK IF COLUMN HAS VALUE
    // =========================================================
    private void addMarkIfExists(
            List<Marks> list,
            ResultSet rs,
            int subjectId,
            String column
    ) throws SQLException {

        Object value =
                rs.getObject(column);

        if (value == null) {
            return;
        }

        double mark =
                rs.getDouble(column);

        Marks marks =
                new Marks(
                        rs.getInt("mark_id"),
                        rs.getInt("student_id"),
                        subjectId,
                        mark
                );

        list.add(marks);
    }


    // =========================================================
    // UPDATE MARKS
    // =========================================================
    public boolean update(
            Marks mark
    ) {

        String column =
                getSubjectColumn(
                        mark.getSubjectId()
                );

        if (column == null) {

            System.out.println(
                    "Invalid subject ID: "
                            + mark.getSubjectId()
            );

            return false;
        }

        String sql =
                "UPDATE marks SET " +
                        "student_id = ?, " +
                        column + " = ? " +
                        "WHERE mark_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    mark.getStudentId()
            );

            stmt.setDouble(
                    2,
                    mark.getMarks()
            );

            stmt.setInt(
                    3,
                    mark.getMarkId()
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating marks:"
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // UPDATE ONE SUBJECT MARK
    // =========================================================
    private boolean updateSubjectMark(
            int markId,
            String column,
            double marks
    ) {

        String sql =
                "UPDATE marks SET " +
                        column + " = ? " +
                        "WHERE mark_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setDouble(
                    1,
                    marks
            );

            stmt.setInt(
                    2,
                    markId
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating subject mark:"
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // DELETE MARK RECORD
    // =========================================================
    public boolean delete(
            int markId
    ) {

        String sql =
                "DELETE FROM marks " +
                        "WHERE mark_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    markId
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting marks:"
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // COUNT TOTAL MARK RECORDS
    // =========================================================
    public int count() {

        String sql =
                "SELECT COUNT(*) FROM marks";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql);
                ResultSet rs =
                        stmt.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error counting marks:"
            );

            e.printStackTrace();
        }

        return 0;
    }


    // =========================================================
    // COUNT MARK RECORDS FOR STUDENT
    // =========================================================
    public int countByStudentId(
            int studentId
    ) {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM marks " +
                        "WHERE student_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    studentId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error counting student's marks:"
            );

            e.printStackTrace();
        }

        return 0;
    }


    // =========================================================
    // SUBJECT ID → MARKS COLUMN
    // =========================================================
    private String getSubjectColumn(
            int subjectId
    ) {

        switch (subjectId) {

            /*
             * subjects table:
             *
             * 2 = object oriented programming
             * 3 = ethics
             * 4 = Java Programming
             * 5 = python
             */

            case 2:
                return "oop";

            case 3:
                return "ethics";

            case 4:
                return "operating_system";

            case 5:
                return "organization";

            default:
                return null;
        }
    }
}