package algorithms;

import MDP.Action;
import MDP.MarkovDecisionProcessBuilder;
import MDP.State;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by Jan on 29-9-2017.
 */
public class ValueIteration {

    private double epsilon;
    private Set<State> S;
    private Set<Action> A;
    DirectedWeightedPseudograph<State, Action> graph;
    final double gamma = 0.9;
    private Function<State, Double> o;

    public ValueIteration(DirectedWeightedPseudograph<State, Action> graph, double epsilon) {
        this.graph = graph;
        this.S = graph.vertexSet();
        this.A = graph.edgeSet();
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
                for (Action a : A) {
                    double u = r(s, a) + gamma * sum( S, (State sPrime ) -> {return t(s,a,sPrime) * utility.get(sPrime);} );
                    if (u > newu){
                        newu = u;
                        pi.put(s, a);
                    }
                }
                double difference = Math.abs(newu - utility.get(s));
                if(difference > delta){
                    delta = difference;
                }
                utility.put(s, newu);
            }
            // u := u'
        }while (delta < epsilon);
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
        
    }

    private double r(State s, Action a){
        return graph.getEdgeWeight(a);
    }
}
