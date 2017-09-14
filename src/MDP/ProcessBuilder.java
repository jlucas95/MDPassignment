package MDP;

/**
 * Created by Jan on 14-9-2017.
 */

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Stack;

/**
 * Builds a markov decision process from a given starting state
 */
public class ProcessBuilder {
    private DefaultDirectedWeightedGraph<State, Action> graph = new DefaultDirectedWeightedGraph<State, Action>(Action.class );
    private State start;

    public ProcessBuilder(State start) {
        this.start = start;
        graph.addVertex(start);
    }

    public DefaultDirectedWeightedGraph build(){

        Stack<State> toExpand = new Stack<>();
        toExpand.push(start);

        while (!toExpand.isEmpty()){
            State s = toExpand.pop();
            List<Action> actions = Action.getActions(s);

            for (Action action : actions) {
                State newS = action.apply(s);
                if (!graph.containsVertex(newS)) {
                    graph.addVertex(newS);
                    graph.addEdge(s, newS, action);
                    graph.setEdgeWeight(action, determineReward(s, action, newS));
                    toExpand.push(newS);
                }
            }

        }
    }

    private double determineReward(State s, Action a, State sPrime){
        throw new NotImplementedException();
    }
}
