package MVC.Model;

import java.io.*;
import java.util.*;

public class Leaderboard{
    private static final List<Player> leaderboard = new ArrayList<>();

    private static final File file = new File("src/main/resources/MVC/Model/leaderboard.txt");


    public static void addPlayer(String givenUsername) {
        Leaderboard.loadPlayers();

        FileWriter writing;
        try {

            writing = new FileWriter(file, true);

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();



                //Checks if players file is empty before writing to a new line
                if (line == null) {
                    writing.append(givenUsername).append(" ").append(String.valueOf(0));
                } else {
                    writing.append("\n").append(givenUsername).append(" ").append(String.valueOf(0));
                }



            writing.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadPlayers();
        savePlayers();
    }

    public static void savePlayers() {
        FileWriter writing;
        try {

            writing = new FileWriter(file);

            //Writes and saves all players in the leaderboard array to the text file
            for (Player player : leaderboard) {
                writing.append(player.getUsername()).append(" ").append(String.valueOf(player.getHighScore())).append("\n");
            }
            writing.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Player> loadPlayers() {
        try {
            leaderboard.clear(); //makes sure the leaderboard array is empty before adding anything new
            Scanner input = new Scanner(file);

            while (input.hasNext()) { //adds each players details to the array

                String username = input.next();

                int highScore = input.nextInt();

                leaderboard.add(new Player(username,highScore));
            }
            input.close();

        } catch (InputMismatchException | FileNotFoundException e) {
            System.err.format("load players error Sorry, either the file does not exist or a vital component from them is missing\n");
        }
        return leaderboard;
    }


    public static ArrayList<String> getTopTenPlayers() {

        List<Player> allPlayers;
        ArrayList<String> topPlayers = new ArrayList<>();
        allPlayers = loadPlayers();

        allPlayers.sort(Comparator.comparing(Player::getHighScore, Comparator.reverseOrder())); //sorts all the players into an order based on their highscore
        if (allPlayers.size()<10){ //Checks if there is already less than 10 players so we do not try and add a player that does not exist

            for (Player allPlayer : allPlayers) {

                topPlayers.add(allPlayer.toString());

            }
        }else{


            for (int i = 0; i < 10; i++) {
                topPlayers.add(allPlayers.get(i).toString());
            }
        }
        return topPlayers;
    }


    public static Player loadPlayer(String givenUsername){
        loadPlayers();
        Player playerInfo = null;

        try {
            Scanner reader = new Scanner(file);
            for (Player player : leaderboard) { //loops through all players and when the given username matches a player it returns that players information

                if (player.getUsername().equals(givenUsername)) {

                    playerInfo = player;

                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return playerInfo;
    }





}
