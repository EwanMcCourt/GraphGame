package MVC.Model;

import Graph.ALGraph;
import Graph.GraphADT;
import Graph.Node;
import Graph.Point;

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

    public GraphADT generateGraph() {
        GraphADT out = new ALGraph();
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

                    Node source;
                    if(out.getNode(Integer.parseInt(m.group(1))) == null) {
                        source = new Point(Integer.parseInt(m.group(1)));
                    } else {
                        source = out.getNode(Integer.parseInt(m.group(1)));
                    }

                    Node target;
                    if(out.getNode(Integer.parseInt(m.group(2))) == null) {
                        target = new Point(Integer.parseInt(m.group(2)));
                    } else {
                        target = out.getNode(Integer.parseInt(m.group(2)));
                    }

                    out.addConnection(source, target, Integer.parseInt(m.group(3)), true);
                }
                line = input.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
}
