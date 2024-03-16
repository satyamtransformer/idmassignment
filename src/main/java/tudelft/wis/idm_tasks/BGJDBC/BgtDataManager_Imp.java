package tudelft.wis.idm_tasks.BGJDBC;

import tudelft.wis.idm_solutions.BoardGameTracker.POJO_Implementation.PlaySession_Imp;
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
            props.setProperty("password", "Lepel3400SQL"); // change with you local password
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

            sql = "SELECT p.player_id FROM Player p WHERE p.name = ? AND p.nickName = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, nickname);
            ResultSet idResult = ps.executeQuery();
            while(idResult.next()){
                int id = idResult.getInt("player_id");
                player = new Player_Imp(id, player.getPlayerName(), player.getPlayerNickName());
            }
        }catch(SQLException e){
            throw new BgtException("text");
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
                int id = resultSet.getInt("player_id");
                Player p = new Player_Imp(id, n, nn);
                // Now we load all the gameCollection.
                sql = """
                    SELECT *
                    FROM BoardGame bg
                    WHERE EXISTS(
                        SELECT *
                        FROM PlayedBy pb
                        WHERE pb.game_id = bg.game_id AND pb.player_id = ?
                    );
                     """;
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet games = ps.executeQuery();
                while(games.next()){
                    int g_id = games.getInt("game_id");
                    String g_name = games.getString("name");
                    String url = games.getString("bggURL");
                    BoardGame bg = new BoardGame_Imp(g_id, g_name, url);
                    p.getGameCollection().add(bg);
                }
                results.add(p);
            }
        }catch(SQLException e){
            throw new BgtException("text");
        } finally {
            // Close resources
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    @Override
    public BoardGame createNewBoardgame(String name, String bggURL) throws BgtException {
        BoardGame_Imp game = new BoardGame_Imp(name, bggURL);

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

            sql = "SELECT g.game_id FROM BoardGame g WHERE g.name = ? AND g.bggURL = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, bggURL);
            ResultSet idResult = ps.executeQuery();
            while(idResult.next()){
                int id = idResult.getInt("game_id");
                game = new BoardGame_Imp(id, name, bggURL);
            }
        }catch(SQLException e){
            throw new BgtException("text");
        }

        return game;
    }

    //TODO: Satyam
    @Override
    public Collection<BoardGame> findGamesByName(String name) throws BgtException {
        List<BoardGame> results = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM BoardGame WHERE name = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String gameName = resultSet.getString("name");
                String bggURL = resultSet.getString("bggURL");
                int id = resultSet.getInt("game_id");
                results.add(new BoardGame_Imp(id, gameName, bggURL));
            }
        } catch (SQLException e) {
            throw new BgtException(e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    //TODO: Implement if time
    @Override
    public PlaySession createNewPlaySession(Date date, Player host, BoardGame game, int playtime, Collection<Player> players, Player winner) throws BgtException {
        if (date == null || host == null || game == null || players == null) {
            throw new IllegalArgumentException("Date, host, game, and players cannot be null");
        }

        PlaySession_Imp session = new PlaySession_Imp(date, host, game, playtime, players, winner);

        // Here you would typically persist the session in the database
        // Example: databaseManager.persistPlaySession(session);

        return session;
    }

    //TODO: Implement if time
    @Override
    public Collection<PlaySession> findSessionByDate(Date date) throws BgtException {
        return null;
    }

    //TODO DONE: FIXED THIS
    @Override
    public void persistPlayer(Player player) throws BgtException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String selectPlayerSql = "SELECT COUNT(*) FROM Player WHERE id = ?";
            ps = conn.prepareStatement(selectPlayerSql);
            ps.setInt(1, player.getPlayerId());
            rs = ps.executeQuery();
            rs.next();
            int playerCount = rs.getInt(1);
            if (playerCount > 0) {
                String updatePlayerSql = "UPDATE Player SET name = ? WHERE id = ?";
                ps = conn.prepareStatement(updatePlayerSql);
                ps.setString(1, player.getPlayerName());
                ps.setInt(2, player.getPlayerId());
                ps.executeUpdate();
                for (BoardGame b : player.getGameCollection()) {
                    BoardGame_Imp bg = (BoardGame_Imp) b;
                    String selectPlayedBySql = "SELECT COUNT(*) FROM PlayedBy WHERE player_id = ? AND game_id = ?";
                    ps = conn.prepareStatement(selectPlayedBySql);
                    ps.setInt(1, player.getPlayerId());
                    ps.setInt(2, bg.getGameId());
                    rs = ps.executeQuery();
                    rs.next();
                    int associationCount = rs.getInt(1);
                    if (associationCount == 0) {
                        String insertPlayedBySql = "INSERT INTO PlayedBy (player_id, game_id) VALUES (?, ?)";
                        ps = conn.prepareStatement(insertPlayedBySql);
                        ps.setInt(1, player.getPlayerId());
                        ps.setInt(2, bg.getGameId());
                        ps.executeUpdate();
                    }
                }
            } else {
                String insertPlayerSql = "INSERT INTO Player (id, name) VALUES (?, ?)";
                ps = conn.prepareStatement(insertPlayerSql);
                ps.setInt(1, player.getPlayerId());
                ps.setString(2, player.getPlayerName());
                ps.executeUpdate();
                for (BoardGame b : player.getGameCollection()) {
                    BoardGame_Imp bg = (BoardGame_Imp) b;
                    String insertPlayedBySql = "INSERT INTO PlayedBy (player_id, game_id) VALUES (?, ?)";
                    ps = conn.prepareStatement(insertPlayedBySql);
                    ps.setInt(1, player.getPlayerId());
                    ps.setInt(2, bg.getGameId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new BgtException("Error persisting player: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO: Sil
    @Override
    public void persistPlaySession(PlaySession session) {

    }

    //TODO: Sil
    @Override
    public void persistBoardGame(BoardGame game) throws BgtException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String selectSql = "SELECT id FROM BoardGame WHERE name = ?";
            ps = conn.prepareStatement(selectSql);
            ps.setString(1, game.getName());
            rs = ps.executeQuery();
            if (rs.next()) {
                int gameId = rs.getInt("id");
                String updateSql = "UPDATE BoardGame SET bggURL = ? WHERE id = ?";
                ps = conn.prepareStatement(updateSql);
                ps.setString(1, game.getBGG_URL());
                ps.setInt(2, gameId);
                ps.executeUpdate();
            } else {
                String insertSql = "INSERT INTO BoardGame (name, bggURL) VALUES (?, ?)";
                ps = conn.prepareStatement(insertSql);
                ps.setString(1, game.getName());
                ps.setString(2, game.getBGG_URL());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new BgtException("Error persisting board game: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
