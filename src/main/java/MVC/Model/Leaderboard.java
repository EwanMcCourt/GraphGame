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
            leaderboard.clear();
            Scanner input = new Scanner(file);

            while (input.hasNext()) {

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

        List<Player> allPlayers = new ArrayList<>();
        ArrayList<String> topPlayers = new ArrayList<>();
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {


                String username = input.next();

                int highScore = input.nextInt();

                allPlayers.add(new Player( username, highScore));
            }
            input.close();

        } catch (InputMismatchException | FileNotFoundException e) {
            System.err.format("Sorry, either the file does not exist or a vital component from them is missing\n");
        }
        allPlayers.sort(Comparator.comparing(Player::getHighScore, Comparator.reverseOrder()));
        if (allPlayers.size()<10){

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
            for (Player player : leaderboard) {

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
