/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tudelft.wis.idm_tasks.BGJPA;

import jakarta.persistence.*;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;

import java.util.ArrayList;
import java.util.Collection;

/**
 * POJO (Plain Old Java Object) Implementation without any database
 * functionality.
 *
 * @author Christoph Lofi, Alexandra Neagu
 */
@Entity
@Table
public class Player implements tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String nickName;
    @OneToMany
    private Collection<BoardGame> gameCollection = new ArrayList<>();

    /**
     * Instantiates a new Player POJO.
     *
     * @param name     name
     * @param nickName nickname
     */
    public Player(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
    }

    public Player() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setGameCollection(Collection<BoardGame> gameCollection) {
        this.gameCollection = gameCollection;
    }

    @Override
    public String getPlayerName() {
        return name;
    }

    @Override
    public String getPlayerNickName() {
        return nickName;
    }

    @Override
    public Collection<BoardGame> getGameCollection() {
        return gameCollection;
    }


    @Override
    public String toVerboseString() {
        String result = name;
        if (nickName != null) {
            result = result + " (" + nickName + ")";
        }
        return result;
    }

    @Override
    public int getPlayerId() {
        return this.id;
    }

}
