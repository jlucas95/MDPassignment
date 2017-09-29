import MDP.*;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.jgrapht.graph.Pseudograph;

/**
 * Created by Jan on 14-9-2017.
 */


public class Main {
    Pseudograph<Action, State> g = new Pseudograph<Action, State>(State.class);


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
        DirectedWeightedPseudograph<State, Action> graph = builder.build();


    }

}