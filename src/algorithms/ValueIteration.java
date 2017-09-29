package algorithms;

import MDP.*;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by Jan on 29-9-2017.
 */
public class ValueIteration {

    private double epsilon;
    private Set<State> S;

//    private Set<Action> A;

    DirectedWeightedPseudograph<State, Action> graph;
    final double gamma = 0.9;
    private Function<State, Double> o;

    public ValueIteration(DirectedWeightedPseudograph<State, Action> graph, double epsilon) {
        this.graph = graph;
        this.S = graph.vertexSet();

//        this.A = graph.edgeSet();

        this.epsilon = epsilon;
    }

    public HashMap<State, Action> run(){
        HashMap<State, Double> utility = new HashMap<State, Double>();
        HashMap<State, Action> pi = new HashMap<>();
        for (State o : S) {
            utility.put(o, 0.0);
        }

        double delta = 0;
        do{
            for (State s : S) {
                Double newu = Double.NEGATIVE_INFINITY;
                for (Action a : graph.outgoingEdgesOf(s) ) {
                    double u = r(s, a) + gamma * sum(S, (State sPrime ) -> t(s,a,sPrime) * utility.get(sPrime));
                    if (u > newu){
                        newu = u;
                        pi.put(s, a);
                    }
                }
                double difference = Math.abs(newu - utility.get(s));
                if(difference > delta){
                    delta = difference;
                }
                utility.put(s, newu);  // u'(s) := u;
            }
            // u := u'
        } while (delta < epsilon);
        return pi;
    }

    private double sum(Set<State> S, Function<State, Double> func) {
        double sum = 0.0;
        for (State s : S) {
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

    private double r(State s, Action a){
        /*
        if we get A(s) instead of A (all possible Actions in any given state)
        then we don't need to check if a is in S of if there is an action on an edge for state s
        we can just immediately take the reward, by
        getting the edge for which the action is in s is possible and getting the reward from that.

        no wait, doesn't work, how does it work if we have the wrong action.
         */

        return graph.getEdgeWeight(a);

//        for (Action action : graph.outgoingEdgesOf(s)) {
//            if (action.equals(a)) return graph.getEdgeWeight(action);
//        }
//        return 0;
//        throw new IllegalArgumentException("Arguments of method are fucked, check that shit out yo!");
    }

}
