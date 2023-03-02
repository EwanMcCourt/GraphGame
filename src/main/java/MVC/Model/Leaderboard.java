package MVC.Model;
import MVC.View.FXView;

import java.io.*;
import java.util.*;

public class Leaderboard{
    static List<Player> leaderboard = new ArrayList<>();

    static File file = new File("src/main/resources/MVC/Model/leaderboard.txt");


    public static void addPlayer() {
        Leaderboard.loadPlayers();
        Scanner scan = new Scanner(System.in);

        FileWriter writing = null;
        try {
            Scanner input = new Scanner(file);
            writing = new FileWriter(file, true);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();


            System.out.println("Please enter your unique username");
            String username = scan.nextLine();
            while (username.length() == 0){
                System.out.println("Invalid input, please try again");
                username = scan.nextLine();
            }

            //Checks if username already exists
            while (input.hasNext()) {
                String check = input.next();
                while (username.equals(check)) {
                    System.out.println("This username exists already, please try again");
                    username = scan.nextLine();
                }
            }

            //Checks if players file is empty before writing to a new line
            if (line == null) {
                writing.append( username + " " + 0 );
            } else {
                writing.append("\n" + username + " " +0 );
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

            for (int i = 0; i < leaderboard.size(); i++) {
                writing.append(leaderboard.get(i).getUsername() + " "  + leaderboard.get(i).getGamesPlayed() + "\n");
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

                int gamesPlayed = input.nextInt();

                leaderboard.add(new Player(username,gamesPlayed));
            }
            input.close();

        } catch (InputMismatchException | FileNotFoundException e) {
            System.err.format("load players error Sorry, either the file does not exist or a vital component from them is missing\n");
        }
        return leaderboard;
    }


    public static ArrayList getTopTenPlayers() {

        List<Player> allPlayers = new ArrayList<>();
        ArrayList<String> topPlayers = new ArrayList<>();
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {


                String username = input.next();

                int gamesPlayed = input.nextInt();

                allPlayers.add(new Player( username, gamesPlayed));
            }
            input.close();

        } catch (InputMismatchException | FileNotFoundException e) {
            System.err.format("Sorry, either the file does not exist or a vital component from them is missing\n");
        }
        allPlayers.sort(Comparator.comparing(Player::getGamesPlayed, Comparator.reverseOrder()));
        if (allPlayers.size()<10){

            for (int i = 0; i < allPlayers.size(); i++) {

                topPlayers.add(allPlayers.get(i).toString());

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
        Scanner reader= null;

        try {
            reader = new Scanner(file);
            for(int i =0;i<leaderboard.size();i++){

                if(leaderboard.get(i).getUsername().equals(givenUsername)){

                    playerInfo =leaderboard.get(i);

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        reader.close();

        return playerInfo;
    }





}
