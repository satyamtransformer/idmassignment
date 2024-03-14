package tudelft.wis.idm_tasks;

import tudelft.wis.idm_tasks.basicJDBC.interfaces.JDBCTask2Interface;

import java.sql.*;
import java.util.*;

public class JDBCTask2 implements JDBCTask2Interface {
    private Connection conn;

    public JDBCTask2() throws SQLException {
        this.conn = getConnection();
    }

    public static void main(String[] args) {

        JDBCTask2 j = null;
        // Task 1: List all primary titles for a specific start year.
        try {
            j = new JDBCTask2();
            Collection<String> result = j.getPlayedCharacters("Aa mir Khan");
            Iterator<String> it = result.iterator();
            while(it.hasNext()){
                System.out.println(it.next());
                it.remove();
            }
            //System.out.println(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Task 2: List all the job categories for titles that include a specific string in their primary title.

        //Task 3 : List the average runtime of a specified genre.

        //Task 4: Given a person’s full name, list all the characters they have played.



    }

    @Override
    public Connection getConnection() throws SQLException {
        if (conn == null) {
            String url = "jdbc:postgresql://localhost:5432/imdb";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", ""); // change with you local password
            conn = DriverManager.getConnection(url, props);
        }
        return conn;
    }

    @Override
    public Collection<String> getTitlesPerYear(int year) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Collection<String> pmTitle= new ArrayList<>();

        try{
            conn = getConnection();
            // 1. Create SQL statement
            String sql = "SELECT t.primary_title FROM titles t WHERE t.start_year = ?";

            // 2. Create a preparred statement
            ps = conn.prepareStatement(sql);

            // 3. Insert parameter value(s) into prepared statement, the bases start from 1
            ps.setInt(1, year);

            // 4. Execute the prepared statement
            resultSet = ps.executeQuery();

            // 5. Process result

            while (resultSet.next()){
                pmTitle.add(resultSet.getString("primary_title"));
            }


        }catch(SQLException e){
            throw new RuntimeException();
        }

        return pmTitle;
    }

    @Override
    public Collection<String> getJobCategoriesFromTitles(String searchString) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Collection<String> result = new ArrayList<>();
        try{
            conn = getConnection();
            String sql = """
                SELECT c.job_category
                FROM titles t
                JOIN cast_info c ON t.title_id = c.title_id
                WHERE t.primary_title LIKE ?
                """;

            ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + searchString + "%");

            resultSet = ps.executeQuery();

            while(resultSet.next()){
                result.add(resultSet.getString("job_category"));
            }
        }catch(SQLException e){
            throw new RuntimeException();
        }
        return result;
    }

    @Override
    public double getAverageRuntimeOfGenre(String genre) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Double result = 0.0;
        try{
            conn = getConnection();
            String sql = """
            SELECT AVG(CAST(t.runtime as float)) as runtime
            FROM titles t
            JOIN titles_genres c ON t.title_id = c.title_id
            WHERE c.genre LIKE ?
            """;

            ps = conn.prepareStatement(sql);
            ps.setString(1, genre);

            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getDouble("runtime");
            }

        }catch(SQLException e){
            throw new RuntimeException();
        }
        return result;
    }

    @Override
    public Collection<String> getPlayedCharacters(String actorFullname) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Collection<String> result = new ArrayList<>();
        try{
            conn = getConnection();
            String sql = """
            SELECT tpc.character_name
            FROM persons p
            JOIN title_person_character tpc ON p.person_id = tpc.person_id
            WHERE p.full_name = ?
            """;

            ps = conn.prepareStatement(sql);
            ps.setString(1, actorFullname);

            resultSet = ps.executeQuery();
            while (resultSet.next()){
                result.add(resultSet.getString("character_name"));
            }

        }catch(SQLException e){
            throw new RuntimeException();
        }
        return result;

    }

}
