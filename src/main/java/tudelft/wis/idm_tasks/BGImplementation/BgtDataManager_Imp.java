package tudelft.wis.idm_tasks.BGImplementation;

import tudelft.wis.idm_tasks.boardGameTracker.BgtException;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class BgtDataManager_Imp implements BgtDataManager {
    private Connection conn;
    public BgtDataManager_Imp() throws SQLException {
        this.conn = getConnection();
    }

    public static void main(String[] args) {

        try {
            BgtDataManager_Imp t = new BgtDataManager_Imp();
            // Fill in to test.
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    public Connection getConnection() throws SQLException {
        if (conn == null) {
            String url = "jdbc:postgresql://localhost:5432/bgame";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", ""); // change with you local password
            conn = DriverManager.getConnection(url, props);
        }
        return conn;
    }


    public void createTables() throws SQLException{
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Collection<String> pmTitle= new ArrayList<>();

        try{
            conn = getConnection();
            String sql = """
            CREATE TABLE Player(
                player_id SERIAL PRIMARY KEY,
                name VARCHAR(100),
                nickName VARCHAR(100)
            );
            
            CREATE TABLE BoardGame(
                game_id SERIAL PRIMARY KEY,
                name VARCHAR(100),
                bggURL VARCHAR(100)
            );
            
            CREATE TABLE PlayedBy(
                player_id INT,
                game_id INT,
                FOREIGN KEY (player_id) REFERENCES Player(player_id),
                FOREIGN KEY (game_id) REFERENCES BoardGame(game_id),
                PRIMARY KEY (player_id, game_id)
            ); """;
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new SQLException();
        }
    }

    @Override
    public Player createNewPlayer(String name, String nickname) throws BgtException {
        Player player = new Player_Imp(name, nickname);

        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Collection<String> pmTitle= new ArrayList<>();

        try{
            conn = getConnection();
            String sql = """
            INSERT INTO Player (name, nickName)
            VALUES(?, ?);
             """;
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, nickname);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new BgtException();
        }

        return player;
    }

    @Override
    public Collection<Player> findPlayersByName(String name) throws BgtException {
        List<Player> results= new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Collection<String> pmTitle= new ArrayList<>();

        try{
            conn = getConnection();
            String sql = """
            SELECT *
            FROM Player
            WHERE name = ?;
             """;
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            resultSet = ps.executeQuery();
            while(resultSet.next()){
                String n = resultSet.getString("name");
                String nn = resultSet.getString("nickName");
                results.add(new Player_Imp(n, nn));
            }
        }catch(SQLException e){
            throw new BgtException();
        }

        return results;
    }

    @Override
    public BoardGame createNewBoardgame(String name, String bggURL) throws BgtException {
        BoardGame game = new BoardGame_Imp(name, bggURL);

        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Collection<String> pmTitle= new ArrayList<>();

        try{
            conn = getConnection();
            String sql = """
            INSERT INTO BoardGame (name, bggURL)
            VALUES(?, ?);
             """;
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, bggURL);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new BgtException();
        }

        return game;
    }

    //TODO: Satyam
    @Override
    public Collection<BoardGame> findGamesByName(String name) throws BgtException {
        return null;
    }

    //TODO: Satyam
    @Override
    public PlaySession createNewPlaySession(Date date, Player host, BoardGame game, int playtime, Collection<Player> players, Player winner) throws BgtException {
        return null;
    }

    //TODO: Satyam
    @Override
    public Collection<PlaySession> findSessionByDate(Date date) throws BgtException {
        return null;
    }

    //TODO: Sil
    @Override
    public void persistPlayer(Player player) {

    }

    //TODO: Sil
    @Override
    public void persistPlaySession(PlaySession session) {

    }

    //TODO: Sil
    @Override
    public void persistBoardGame(BoardGame game) {

    }
}
