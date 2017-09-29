package MDP;
/*
 * Created by Jan on 14-9-2017.
 */

import org.jgrapht.graph.DirectedWeightedPseudograph;

import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Builds a markov decision process from a given starting state
 */
public class MarkovDecisionProcessBuilder {
    private State start;

    private State goalState = getGoal();

    private State getGoal() {

        Block A = new Block("A");
        Block B = new Block("B");
        Block C = new Block("C");
        Tower tower = new Tower();
        tower.addBlock(A);
        tower.addBlock(C);
        tower.addBlock(B);

        Table table = new Table();
        table.add(tower);

        return new State(table);
    }

    public MarkovDecisionProcessBuilder(State start) {
        this.start = start;

    }

    public DirectedWeightedPseudograph<State, Action> build(){

        DirectedWeightedPseudograph<State, Action> graph = new DirectedWeightedPseudograph<>(Action.class );
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
                    toExpand.push(newS);
                }
                graph.addEdge(s, getEdge(graph, newS), action);
                graph.setEdgeWeight(action, determineReward(s, action, newS));
            }
        }

        AbsorbingGoalState(graph);
        return graph;
    }

    private void AbsorbingGoalState(DirectedWeightedPseudograph<State, Action> graph) {
        State goal = getGoal();
        Set<Action> actions = graph.outgoingEdgesOf(goal);
        if(actions.size() == 0) throw new IllegalArgumentException("Graph does not contain goal state");
        graph.removeAllEdges(actions);
    }

    private State getEdge(DirectedWeightedPseudograph<State, Action> graph, State s){
        Set<State> states = graph.vertexSet();
        for(State state : states){
            if(state.equals(s)){
                return state;
            }
        }
        throw new IllegalArgumentException("State does not exist in graph");
    }

    private boolean inGraph(DirectedWeightedPseudograph<State, Action> graph, State s) {
        Set<State> states = graph.vertexSet();
        for (State state : states) {
            if (state.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private double determineReward(State s, Action a, State sPrime){
        double reward;
        // if wrong move -> reward -10
        if(a.probability == 0.1) reward = -10;
        // if reaches goal state -> reward +100
        else if(sPrime.equals(goalState)) reward = 100;
        // else -> reward -1
        else reward = -1;
        return reward;
    }

}
