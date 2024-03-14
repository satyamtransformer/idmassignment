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

    public static void main(String[] args) {

        try {
            BgtDataManager_Imp t = new BgtDataManager_Imp();
            t.createTables();
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

    }

    public Connection getConnection() throws SQLException {
        if (conn == null) {
            String url = "jdbc:postgresql://localhost:5432/bgame";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "Satyam1234"); // change with you local password
            conn = DriverManager.getConnection(url, props);
        }
        return conn;
    }


    public void createTables() throws SQLException{
        Connection conn = getConnection();
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
                )
        
                CREATE TABLE BoardGame(
                    game_id SERIAL PRIMARY KEY,
                    name VARCHAR(100),
                    bggURL VARCHAR(100)
                )
        
                CREATE TABLE PlayedBy(
                    player_id INT,
                    game_id INT,
                    FOREIGN KEY (player_id) REFERENCES Player(player_id),
                    FOREIGN KEY (game_id) REFERENCES BoardGame(game_id),
                    PRIMARY KEY (player_id, game_id)
                )
"""; //TODO make a table for all entities in the database


            ps = conn.prepareStatement(sql);
            ps.executeQuery();



        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    //TODO : Aadi
    @Override
    public Player_Imp createNewPlayer(String name, String nickname) throws BgtException {
        Player_Imp player = new Player_Imp(name, nickname);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Collection<String> pmTitle= new ArrayList<>();

        

        return player;
    }

    //TODO : Aadi
    @Override
    public Collection<Player> findPlayersByName(String name) throws BgtException {
        return null;
    }

    //TODO : Aadi
    @Override
    public BoardGame createNewBoardgame(String name, String bggURL) throws BgtException {
        return null;
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
