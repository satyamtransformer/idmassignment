/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tudelft.wis.idm_tasks.BGJPA;

import tudelft.wis.idm_tasks.boardGameTracker.BgtException;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements an in-memory POJO database using Linked Lists. This is sooooo
 * insanely bad, it could even be for Osiris...
 *
 * @author Christoph Lofi, Alexnadra Neagu
 */
public class BgtDataManager implements tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager {

    private List<Player> players = new LinkedList<Player>();
    private List<BoardGame> games = new LinkedList<BoardGame>();
    private List<PlaySession> sessions = new LinkedList<PlaySession>();

    @Override
    public tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player createNewPlayer(String name, String nickname) throws BgtException {
        Player
            player = new Player(name, nickname);
        players.add(player);
        return player;
    }

    @Override
    public tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame createNewBoardgame(String name, String bggURL) throws BgtException {
        BoardGame game = new BoardGame(name, bggURL);
        games.add(game);
        return game;
    }

    @Override
    public tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession createNewPlaySession(Date date, tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player host, tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame game, int playtime, Collection<tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player> players, tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player winner) throws BgtException {
        PlaySession
            session = new PlaySession(date, host, game, playtime, players, winner);
        sessions.add(session);
        return session;
    }

    @Override
    public Collection<tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player> findPlayersByName(String name) throws BgtException {
        Collection<tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player> result = new LinkedList<tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player>();
        for (Player player : players) {
            if (player.getPlayerName().contains(name)) {
                result.add(player);
            }
        }
        return result;
    }

    @Override
    public Collection<tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame> findGamesByName(String name) throws BgtException {
        Collection<tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame> result = new LinkedList<tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame>();
        for (BoardGame game : games) {
            if (game.getName().contains(name)) {
                result.add(game);
            }
        }
        return result;
    }

    @Override
    public Collection<tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession> findSessionByDate(Date date) throws BgtException {
        Collection<tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession> result = new LinkedList<tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession>();
        for (PlaySession session : sessions) {
            if (session.getDate().equals(date)) {
                result.add(session);
            }
        }
        return result;
    }

    @Override
    public void persistPlayer(tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player player) {
     // Do nothing as there is no DB...
    }

    @Override
    public void persistPlaySession(tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession session) {
     // Do nothing as there is no DB...
    }

    @Override
    public void persistBoardGame(tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame game) {
     // Do nothing as there is no DB...
    }

}
