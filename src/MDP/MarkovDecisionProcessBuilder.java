package MDP;

/**
 * Created by Jan on 14-9-2017.
 */

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Builds a markov decision process from a given starting state
 */
public class MarkovDecisionProcessBuilder {
    private State start;

    public MarkovDecisionProcessBuilder(State start) {
        this.start = start;

    }

    public DefaultDirectedWeightedGraph build(){

        DefaultDirectedWeightedGraph<State, Action> graph = new DefaultDirectedWeightedGraph<State, Action>(Action.class );
        graph.addVertex(start);

        Stack<State> toExpand = new Stack<>();
        toExpand.push(start);

        while (!toExpand.isEmpty()){
            State s = toExpand.pop();
            List<Action> actions = Action.getActions(s);

            for (Action action : actions) {
                State newS = action.apply(s);
                if (!inGraph(graph, newS)) {
                    graph.addVertex(newS);
                    graph.addEdge(s, newS, action);
                    graph.setEdgeWeight(action, determineReward(s, action, newS));
                    toExpand.push(newS);
                }
            }

        }
        return graph;
    }

    private boolean inGraph(DefaultDirectedWeightedGraph<State, Action> graph, State s) {
        Set<State> states = graph.vertexSet();
        for (State state : states) {
            if (state.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private double determineReward(State s, Action a, State sPrime){
        return 1.0;
    }

}
