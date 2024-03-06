package tudelft.wis.idm_tasks.BGImplementation;

import tudelft.wis.idm_tasks.boardGameTracker.BgtException;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

public class BgtDataManager_Imp implements BgtDataManager {
    private Connection conn;
    public BgtDataManager_Imp() throws SQLException {
        this.conn = getConnection();
    }

    public  Connection getConnection() throws SQLException {
        if (conn == null) {
            String url = "jdbc:postgresql://localhost:5432/bgame";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "Optimusprime11p"); // change with you local password
            conn = DriverManager.getConnection(url, props);
        }
        return conn;
    }


    public void createTables() throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Collection<String> pmTitle= new ArrayList<>();

        try{
            conn = getConnection();
            String sql = """


"""; //TODO make a table for all entities in the database

            String url = "jdbc:postgresql://localhost:5432/imdb";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", ""); // change with you local password
            conn = DriverManager.getConnection(url, props);

            ps = conn.prepareStatement(sql);
            ps.executeQuery();



        }catch(SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public Player_Imp createNewPlayer(String name, String nickname) throws BgtException {
        Player_Imp player = new Player_Imp(name, nickname);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Collection<String> pmTitle= new ArrayList<>();

        

        return player;
    }

    @Override
    public Collection<Player> findPlayersByName(String name) throws BgtException {
        return null;
    }

    @Override
    public BoardGame createNewBoardgame(String name, String bggURL) throws BgtException {
        return null;
    }

    @Override
    public Collection<BoardGame> findGamesByName(String name) throws BgtException {
        return null;
    }

    @Override
    public PlaySession createNewPlaySession(Date date, Player host, BoardGame game, int playtime, Collection<Player> players, Player winner) throws BgtException {
        return null;
    }

    @Override
    public Collection<PlaySession> findSessionByDate(Date date) throws BgtException {
        return null;
    }

    @Override
    public void persistPlayer(Player player) {

    }

    @Override
    public void persistPlaySession(PlaySession session) {

    }

    @Override
    public void persistBoardGame(BoardGame game) {

    }
}
