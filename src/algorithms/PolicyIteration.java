package algorithms;

import MDP.Action;
import MDP.State;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class PolicyIteration {

    DirectedWeightedPseudograph<State, Action> graph;
    Set<State> S;
    double gamma = 0.9;

    public PolicyIteration(DirectedWeightedPseudograph<State, Action> g){
        this.graph = g;
        this.S = graph.vertexSet();
    }

    public HashMap<State, Action> run(){
        HashMap<State, Action> pi = initPolicy(graph);
        boolean changed;
        do{
            HashMap<State, Double> utility = calculateUtility(pi);
            changed = false;
            for (State s : S) {
                if (!graph.outgoingEdgesOf(s).isEmpty()){
                    Action a = argmax(graph.outgoingEdgesOf(s), (Action aPrime) -> {return
                            r(s, aPrime) + gamma * sum(S, (State sPrime) -> {return t(s, aPrime, sPrime) * u(s, utility);});});
                    if(pi.get(s) != a) {
                        pi.put(s, a);
                        changed = true;
                    }
                }
            }
        } while (changed);
        return pi;
    }

    private HashMap<State, Action> initPolicy(DirectedWeightedPseudograph<State, Action> graph) {
        HashMap<State, Action> policy = new HashMap<>();
        for (State state : S) {
            Set<Action> outgoingEdges = graph.outgoingEdgesOf(state);
            if (!outgoingEdges.isEmpty()) {
                policy.put(state, (Action) outgoingEdges.toArray()[0]);
            }
        }
        return policy;
    }

    private HashMap<State,Double> calculateUtility(HashMap<State, Action> pi) {
        HashMap<State, Double> utility = new HashMap<>();
        for (State state : S) {
            // fucked if state is absorbing state b/c that doesn't have a policy (or possible outgoing action);
            if (!graph.outgoingEdgesOf(state).isEmpty()) {
                utility.put(state, graph.getEdgeWeight(pi.get(state)));
            }
        }
        return utility;
    }

    private double u(State s, HashMap<State, Double> pi) {
        return pi.get(s);
    }

    public Action argmax(Set<Action> As, Function<Action, Double> func){
        double u = Double.NEGATIVE_INFINITY;
        Action a = null;
        for (Action ac : As) {
            Double val = func.apply(ac);
            if(val > u){
                u = val;
                a = ac;
            }
        }
        if(a != null) return a;
        throw new IllegalStateException("something went wrong");
    }

    public double sum(Set<State> states, Function<State, Double> func){
        double sum = 0;
        for (State s : states) {
            sum += func.apply(s);
        }
        return sum;
    }

    private double t(State s, Action a, State sPrime){
        /*
        can't it just be: a.getProbability(); ??
         */

        Set<Action> edges = graph.getAllEdges(s, sPrime);

        for (Action action : edges) {
            if(action.equals(a)){
                return action.getProbability();
            }
        }
        return 0.0;
    }


    private double r(State s, Action a) {
        return graph.getEdgeWeight(a);
    }
}
