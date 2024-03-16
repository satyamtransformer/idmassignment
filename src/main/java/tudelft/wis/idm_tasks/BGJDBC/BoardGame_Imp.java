package tudelft.wis.idm_tasks.BGJDBC;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;

public class BoardGame_Imp implements BoardGame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * The Name.
     */
    String name;
    /**
     * The BoardGameGeek.com URL of the boardgame.
     */
    String bggURL;

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

    public int getGameId() {
        return this.id;
    }

    /**
     * Instantiates a new Board game POJO.
     *
     * @param name   the name
     * @param bggURL the BoardGameGeek.com URL of the boardgame
     */
    public BoardGame_Imp(String name, String bggURL) {
        this.name = name;
        this.bggURL = bggURL;
    }
    public BoardGame_Imp(int id, String name, String bggURL) {
        this.id = id;
        this.name = name;
        this.bggURL = bggURL;
    }
}
