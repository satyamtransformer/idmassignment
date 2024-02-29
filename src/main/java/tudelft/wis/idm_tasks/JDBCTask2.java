package tudelft.wis.idm_tasks;

import tudelft.wis.idm_tasks.basicJDBC.interfaces.JDBCTask2Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

public class JDBCTask2 implements JDBCTask2Interface {
    private Connection conn;

    public JDBCTask2() throws SQLException {
        this.conn = getConnection();
    }

    public static void main(String[] args) {

        JDBCTask2 j = null;
        try {
            j = new JDBCTask2();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            JDBCTask2 hdbc = new JDBCTask2();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            j.getTitlesPerYear(2022);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Connection getConnection() throws SQLException {
        if (conn == null) {
            String url = "jdbc:postgresql://localhost:5432/imdb";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "Satyam1234");
            conn = DriverManager.getConnection(url, props);
        }
        return conn;
    }

    @Override
    public Collection<String> getTitlesPerYear(int year) throws SQLException {
        Connection conn = getConnection();

        conn.setAutoCommit(false);
        PreparedStatement changeNameStmt = conn.prepareStatement(
            "SELECT * FROM persons"
        );
//        changeNameStmt.setString(1, "Jean Grey-Summers");
//        changeNameStmt.setString(2, "Jean Grey");
//        changeNameStmt.executeUpdate();
//        changeNameStmt.setString(1, "Scott Grey-Summers");
//        changeNameStmt.setString (2, "Scott Summers");
//        changeNameStmt.executeUpdate();
        conn.commit();
        return null;
    }

    @Override
    public Collection<String> getJobCategoriesFromTitles(String searchString) {
        return null;
    }

    @Override
    public double getAverageRuntimeOfGenre(String genre) {
        return 0;
    }

    @Override
    public Collection<String> getPlayedCharacters(String actorFullname) {
        return null;
    }

}
