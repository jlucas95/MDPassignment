import MDP.*;
import algorithms.PolicyIteration;
import algorithms.ValueIteration;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.jgrapht.graph.Pseudograph;

import java.util.HashMap;

/**
 * Created by Jan on 14-9-2017.
 */


public class Main {
    Pseudograph<Action, State> g = new Pseudograph<Action, State>(State.class);


    public static void main(String[] args) {

        State start = State.getStartState();
        MarkovDecisionProcessBuilder builder = new MarkovDecisionProcessBuilder(start);
        DirectedWeightedPseudograph<State, Action> graph = builder.build();

        ValueIteration vi = new ValueIteration(graph,0.01);
        HashMap<State, Action> runVi = vi.run();

        PolicyIteration pi = new PolicyIteration(graph);
        HashMap<State, Action> runPi = pi.run();

//        for (HashMap.Entry entry : runPi.entrySet()) {
//            System.out.println(entry.getValue());
//        }
    }

}