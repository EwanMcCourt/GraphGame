import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static Graph graph;
    static int source = 30;
    static int target = 30;

    public static void main(String[] args) {
        UI ui = new UI();
        ui.run();
    }

    public static Graph readInput(String filename) {
        Graph out = new AdjacencyList();
        BufferedReader input;
        System.out.println("reading file");
        try {
            input = new BufferedReader(new FileReader(filename));
            final Pattern p = Pattern.compile("^([0-9]+) ([0-9]+) \\{'weight': ([0-9]+)}");
            String line = input.readLine();

            while (line != null) {
                Matcher m = p.matcher(line);
                if (m.find()) {
                    System.out.format("Adding connection from %d to %d with weight %d\n",Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
                    out.addConnection(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
                }
                line = input.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return out;
    }
}
