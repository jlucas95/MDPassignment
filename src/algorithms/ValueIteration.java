package algorithms;

import MDP.*;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by Jan on 29-9-2017.
 */
public class ValueIteration {

    private double epsilon;
    private Set<State> S;
    DirectedWeightedPseudograph<State, Action> graph;
    final double gamma = 0.9;

    public ValueIteration(DirectedWeightedPseudograph<State, Action> graph, double epsilon) {
        this.graph = graph;
        this.S = graph.vertexSet();
        this.epsilon = epsilon;
    }

    public HashMap<State, Action> run(){
        HashMap<State, Double> utility = new HashMap<State, Double>();
        HashMap<State, Action> pi = new HashMap<>();
        for (State o : S) {
            utility.put(o, 0.0);
        }

        double delta;
        do{
            delta = 0;
            HashMap<State, Double> uPrime = new HashMap<>(utility);
            for (State s : S) {
                Double newu = Double.NEGATIVE_INFINITY;
                for (Action a : graph.outgoingEdgesOf(s) ) {
                    double u = r(s, a) + gamma * sum(S, utility, (State sPrime, HashMap<State, Double> ut) ->
                            t(s,a,sPrime) * ut.get(sPrime));
                    if (u > newu){
                        newu = u;
                        pi.put(s, a);
                    }
                }
                if(Double.isInfinite(newu)) newu = 0.0;
                double difference = Math.abs(newu - utility.get(s));
                if(difference > delta){
                    delta = difference;
                }
                uPrime.put(s, newu);
            }
            utility = uPrime;
        } while (delta > epsilon);
        return pi;
    }

    private double sum(Set<State> S, HashMap<State, Double> u, BiFunction<State, HashMap<State, Double>, Double> func) {
        double sum = 0.0;

        for (State s : S) {
            sum += func.apply(s, u);
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

    private double r(State s, Action a){
        return graph.getEdgeWeight(a);
    }

}
