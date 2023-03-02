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

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Player)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Player c = (Player) o;

        // Compare the data members and return accordingly
        return username.equals(c.username)
                && Double.compare(gamesPlayed, c.gamesPlayed) == 0;
    }

}
