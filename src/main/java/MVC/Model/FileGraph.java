package MVC.Model;

import Graph.AdjacencyList;
import Graph.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileGraph implements Model {
    String filename;

    public FileGraph(String filename) {
        this.filename = filename;
    }

    public Graph generateGraph() {
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
                    out.addConnection(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), true);
                }
                line = input.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
}