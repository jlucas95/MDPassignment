import MDP.*;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.omg.PortableInterceptor.ACTIVE;

/**
 * Created by Jan on 14-9-2017.
 */


public class Main {
    SimpleDirectedWeightedGraph<Action, State> g = new SimpleDirectedWeightedGraph<Action, State>(State.class);


    public static void main(String[] args) {

        Block a = new Block("A");
        Block b = new Block("B");
        Block c = new Block("C");

        Tower tower = new Tower();
        tower.addBlock(c);
        tower.addBlock(b);
        tower.addBlock(a);

        Table table = new Table();
        table.add(tower);

        State start = new State(table);
        MarkovDecisionProcessBuilder builder = new MarkovDecisionProcessBuilder(start);
        DefaultDirectedWeightedGraph graph = builder.build();


    }

}