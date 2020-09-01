import java.util.*;

import static java.lang.System.*;

/*
 * The Pig game
 * See http://en.wikipedia.org/wiki/Pig_%28dice_game%29
 *
 */
public class Pig {


    public static void main(String[] args) {
        new Pig().program();
    }

    // The only allowed instance variables (i.e. declared outside any method)
    // Accessible from any method
    final Scanner sc = new Scanner(in);
    final Random rand = new Random();
    // Declaring
    List<Player> playerList = new ArrayList<Player>();
    Player current;
    final int winPts = 20;

    void program() {
        // Displaying welcomeMsg() with points required to win
        welcomeMsg(winPts);
        // Displaying numPlayersMsg()
        numPlayersMsg();
        // Calling startingPlayer() to assign a random player to Player current
        startingPlayer();
        // Displaying getPlayerChoice() with current and passing it to choicePlayer()
        choicePlayer(getPlayerChoice(current));

    }

    // ---- Game logic methods --------------

    // Randomized dice 1-6
    public int diceResult() {
        return rand.nextInt(6) + 1;
    }
    // Return length of playerList
    public int numPlayers() {
        return playerList.size() - 1;
    }
    // Assigns a random Player to Player current
    public void startingPlayer() {
        // Gets Player with randomized index, needs numPlayers() to get last index of playerList
        current = playerList.get(rand.nextInt(numPlayers()));
    }
    // Assigns a new Player to Player current
    public void nextPlayer() {
        // If Player current is the last index of playerList, Player current becomes Player with index 0
        if (playerList.indexOf(current) == numPlayers()) {
            current = playerList.get(0);
        // Assigns Player current to Player in playerList with index+1, next Player in playerList
        } else {
            current = playerList.get(playerList.indexOf(current) + 1);
        }

    }
    // Player current rolls the dice
    public void roll() {
        // Assigns value of diceResult() to int diceThrow
        int diceThrow = diceResult();
        // Adds Player current.roundPts with result from diceResult + previous roundPts
        current.roundPts = current.roundPts + diceThrow;
        // Displaying roundMsg() with result of current round of Player current
        roundMsg(diceThrow, current);
        // Calls win() to see if Player current won
        win();
        // Displays getPlayerChoice(current)
        choicePlayer(getPlayerChoice(current));
    }

    public void next() {
        // Adds roundPts to totalPts, so the Player saves their points
        current.totalPts = current.roundPts;
    }
    // Takes input and decides which actions to take
    public void choicePlayer(String choiceInput) {
        if (choiceInput.equals("r")) {
            roll();
        }
        if (choiceInput.equals("n")) {
            // Saves roundPts to totalPts and assigns new Player current
            next();
            nextPlayer();
            statusMsg();
            // Displays getPlayerChoice() with the new Player current
            choicePlayer(getPlayerChoice(current));
        }
        if (choiceInput.equals("q")) {
            // Exits program with boolean aborted = true
            gameOverMsg(current, true);
        } else {
            // If input is invalid calls itself again with new input
            out.println("Try again. ");
            out.println("Commands are: r = roll , n = next, q = quit");
            choicePlayer(getPlayerChoice(current));
        }
    }

    public void win() {
        // Checks if Player current won with roundPts with boolean aborted = false
        if (current.totalPts + current.roundPts >= winPts) {
            gameOverMsg(current, false);
            exit(0);
        }
    }


//  //
    // ---- IO methods ------------------

    void welcomeMsg(int winPoints) {
        out.println("Welcome to PIG!");
        out.println("First player to get " + winPoints + " points will win!");
        out.println("Commands are: r = roll , n = next, q = quit");
        out.println();
    }
    // Asks how many players are playing
    void numPlayersMsg() {
        out.println("How many players? ");
        int numPlayers = 0;
        try {
            numPlayers = Integer.parseInt(sc.nextLine());

        } catch (NumberFormatException numberFormatException) {
            System.out.println("Try again. ");
            numPlayersMsg();
        }
        // Asks what players name are
        for (int i = 0; i < numPlayers; i++) {
            addPlayerMsg(i + 1);
        }
    }

    void addPlayerMsg(int numPlayer) {
        out.println("Name of Player" + numPlayer + ": ");
        String playerName = sc.nextLine();
        // Creates a Player with 0 totalPts and roundPts
        playerList.add(new Player(playerName, 0, 0));
    }

    void statusMsg() {
        out.print("Points: ");
        // Displays all players totalPts
        for (Player player : playerList) {
            out.print(player.name + " = " + player.totalPts + " ");
        }
        out.println();
    }

    void roundMsg(int result, Player current) {
        // Diplays result of round
        if (result > 1) {
            out.println("Got " + result + " running total are " + current.roundPts);
        // Player current loses all roundPts and selects next player as Player current
        } else {
            current.roundPts = 0;
            nextPlayer();
            out.println("Got 1 lost it all!");
        }
    }

    void gameOverMsg(Player player, boolean aborted) {
        // If any player quits before anyone reached 20 Pts
        if (aborted) {
            out.println("Aborted");
        }
        // If Player current reached 20 Pts
        else {
            out.println("Game over! Winner is player " + player.name + " with "
                    + (player.totalPts + player.roundPts) + " points");
        }
    }

    String getPlayerChoice(Player player) {
        // Asks Player current what choice to make
        out.print("Player is " + player.name + " > ");
        return sc.nextLine();
    }

    // ---------- Class -------------
    // Class representing the concept of a player
    // Use the class to create (instantiate) Player objects
    class Player {
        String name;     // Default null
        int totalPts;    // Total points for all rounds, default 0
        int roundPts;    // Points for a single round, default 0

        // Constructor for Player
        public Player(String name, int totalPts, int roundPts) {
            this.name = name;
            this.totalPts = totalPts;
            this.roundPts = roundPts;
        }

    }

    // ----- Testing -----------------
    // Here you run your tests i.e. call your game logic methods
    // to see that they really work (IO methods not tested here)
    void test() {
        // This is hard coded test data
        // An array of (no name) Players (probably don't need any name to test)


        // TODO Use for testing of logcial methods (i.e. non-IO methods)

        exit(0);   // End program
    }
}



