/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tudelft.wis.idm_tasks.BGJPA;

import jakarta.persistence.*;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;

/**
 * POJO (Plain Old Java Object) Implementation without any database
 * functionality.
 *
 * @author Christoph Lofi, Alexandra Neagu
 */
@Entity
@Table
public class BoardGame_JPA implements BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long ID;

    /**
     * The Name.
     */
    String name;
    /**
     * The BoardGameGeek.com URL of the boardgame.
     */
    String bggURL;

    public BoardGame_JPA() {

    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBGG_URL() {
        return bggURL;
    }

    @Override
    public String toVerboseString() {
        return name + " (" + bggURL + ")";
    }

    /**
     * Instantiates a new Board game POJO.
     *
     * @param name   the name
     * @param bggURL the BoardGameGeek.com URL of the boardgame
     */
    public BoardGame_JPA(String name, String bggURL) {
        this.name = name;
        this.bggURL = bggURL;
    }

}
