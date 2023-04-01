package MVC.Model;
public class Player {

    private final String username;

    private int highscore;



    public Player( String username, int highscore) {

        this.username = username;

        this.highscore = highscore;

    }

    public String getUsername() {
        return this.username;
    }


    public int getHighScore() {return this.highscore;
    }
    public void updateHighScore(int newScore) {
        if (newScore > highscore){
            this.highscore = newScore;
        }

    }
    public void printDetails() {
        System.out.println("Username " + getUsername());
        System.out.println("High score: "+ getHighScore());
    }

    @Override
    public String toString() {
        return  username + " "  +highscore;
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
                && Double.compare(highscore, c.highscore) == 0;
    }

}
