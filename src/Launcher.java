import game.bomberman.Game;

/**
 * Launcher class containing the main method responsible to start the game
 */
public class Launcher {

    private static int WIDTH = 800, HEIGHT = 455;

    public static void main(String[] args) {
        Game game = new Game("Bomberman", WIDTH, HEIGHT);
        game.start();
    }
}
