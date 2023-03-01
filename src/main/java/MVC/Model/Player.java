package MVC.Model;
public class Player {

    private String username;
    private int gamesPlayed;




    public Player( String username, int gamesPlayed) {

        this.username = username;

        this.gamesPlayed = gamesPlayed;

    }

    public String getUsername() {
        return this.username;
    }


    public int getGamesPlayed() {
        return this.gamesPlayed;
    }
    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }


    public void printDetails() {
        System.out.println("Username " + getUsername());
        System.out.println("Games played: "+ getGamesPlayed());

    }

    @Override
    public String toString() {
        return  username + " " + gamesPlayed;
    }
}
