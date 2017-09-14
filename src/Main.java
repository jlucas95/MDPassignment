import MDP.Action;
import MDP.Block;
import MDP.State;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.omg.PortableInterceptor.ACTIVE;

/**
 * Created by Jan on 14-9-2017.
 */


public class Main {
    SimpleDirectedWeightedGraph<Action, State> g = new SimpleDirectedWeightedGraph<Action, State>(State.class);


    public static void main(String[] args) {

        Block block = new Block(null);

    }

}