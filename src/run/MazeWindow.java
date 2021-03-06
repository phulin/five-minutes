/*
 * Object: MashWindow
 * The view for a minigame in which the player must complete a maze. 
 */
package run;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import core.Player;

public class MazeWindow extends Window {

    private float[] goalPos = new float[2];
    private Image goalSprite;
    private Rectangle goalShape;

    private Path maze;

    // private float gridSize;

    // to be implemented when we have different maze minigames
    // private final String MINIGAME_TYPE = "";

    public MazeWindow(Player player) throws SlickException {
        super(player);
        goalSprite = new Image("resources/tunnel.png");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g, Player player) throws SlickException {
        this.displayMinigameBackground(g, player);
        g.drawImage(goalSprite, goalPos[0], goalPos[1]);
        player.render(container, game, g, playerPos[0], playerPos[1]);

        g.setColor(Color.white);
        g.draw(maze);

        g.setColor(Color.white);
        PlayGameState state = (PlayGameState) (game.getCurrentState());
        UnicodeFont uFont = state.uFont;
        g.setFont(state.uFont);
        g.drawString("Exit the maze", 100 + player.windowPos[0], 65);
        g.setColor(Color.black);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game, Player player) throws SlickException {
        super.init(container, game, player);
        playerPos[0] = player.windowPos[0] + 200;
        playerPos[1] = player.windowPos[1] + 170;

        goalPos[0] = player.windowPos[0] + 70;
        goalPos[1] = player.windowPos[1] + 100;
        goalShape = new Rectangle(goalPos[0], goalPos[1], 40, 40);

        maze = new Path(player.windowPos[0] + 100, player.windowPos[1] + 100);
        maze.lineTo(player.windowPos[0] + 300, player.windowPos[1] + 100);
        maze.lineTo(player.windowPos[0] + 300, player.windowPos[1] + 300);
        maze.lineTo(player.windowPos[0] + 100, player.windowPos[1] + 300);
        maze.lineTo(player.windowPos[0] + 100, player.windowPos[1] + 150);
        maze.lineTo(player.windowPos[0] + 250, player.windowPos[1] + 150);
        maze.lineTo(player.windowPos[0] + 250, player.windowPos[1] + 250);
        maze.lineTo(player.windowPos[0] + 150, player.windowPos[1] + 250);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta, Player player) throws SlickException {
        Input input = container.getInput();

        float moveValue = delta * .2f;
        if (input.isKeyDown(player.getButton("left"))) {
            if (!(new Rectangle(playerPos[0] - moveValue, playerPos[1], player.pWidth, player.pHeight).intersects(maze))) {
                playerPos[0] -= moveValue;
            }
        }
        if (input.isKeyDown(player.getButton("right"))) {
            if (!(new Rectangle(playerPos[0] + moveValue, playerPos[1], player.pWidth, player.pHeight).intersects(maze))) {
                playerPos[0] += moveValue;
            }
        }
        if (input.isKeyDown(player.getButton("up"))) {
            if (!(new Rectangle(playerPos[0], playerPos[1] - moveValue, player.pWidth, player.pHeight).intersects(maze))) {
                playerPos[1] -= moveValue;
            }
        }
        if (input.isKeyDown(player.getButton("down"))) {
            if (!(new Rectangle(playerPos[0], playerPos[1] + moveValue, player.pWidth, player.pHeight).intersects(maze))) {
                playerPos[1] += moveValue;
            }
        }

        if ((new Rectangle(playerPos[0], playerPos[1], 20, 20).intersects(goalShape))) {
            this.over = true;
        }
    }
}
