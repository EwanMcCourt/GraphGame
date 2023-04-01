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


    public int getHighScore() {return this.highscore;}

    public void updateHighScore(int newScore) {
        if (newScore > highscore){ // checks if the new score is better than the current high score
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


    //https://www.geeksforgeeks.org/overriding-equals-method-in-java/
    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Player)) {
            return false;
        }

        Player c = (Player) o;

        return username.equals(c.username)
                && Double.compare(highscore, c.highscore) == 0;
    }

}
