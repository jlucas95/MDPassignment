package algorithms;

import MDP.Action;
import MDP.State;
import org.ejml.simple.SimpleMatrix;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PolicyIteration {

    private DirectedWeightedPseudograph<State, Action> graph;
    private Set<State> S;
    private double gamma = 0.9;

    public PolicyIteration(DirectedWeightedPseudograph<State, Action> g) {
        this.graph = g;
        this.S = graph.vertexSet();
    }

    public HashMap<State, Action> run() {
        HashMap<State, Action> pi = initPolicy(graph);
        boolean changed;

        do {
            HashMap<State, Double> utility = calculateUtilities(pi);
            for (HashMap.Entry entry : utility.entrySet()) {
                System.out.println(entry.getKey() + " || " + entry.getValue());
            }
            changed = false;
            for (State s : S) {
                if (!graph.outgoingEdgesOf(s).isEmpty()) {
                    Action a = argmax(graph.outgoingEdgesOf(s), (Action aPrime) ->
                            r(s, aPrime) + gamma * sum(S, (State sPrime) -> t(s, aPrime, sPrime) * u(s, utility)));
                    if (pi.get(s) != a) {
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

    private HashMap<State, Double> calculateUtilities(HashMap<State, Action> pi) {
        HashMap<State, Double> utility = new HashMap<>();

        SimpleMatrix rewards = new SimpleMatrix(S.size(), 1);
        SimpleMatrix utilityWeights = new SimpleMatrix(S.size(), S.size());

        State[] states = S.toArray(new State[S.size()]);

        // iterate for loop with i so that the index of where to put something in the matrix can be inferred
        for (int i = 0; i < S.size(); i++) {
            // "-1 * .." because we take reward to the order side of the equals sign in the utility formula:
            rewards.set(i,0, (r(states[i],pi.get(states[i]))) );

            Set<Action> actions = graph.outgoingEdgesOf(states[i]);
            for (Action action : actions) {
                int colIndex = Arrays.asList(states).indexOf(graph.getEdgeTarget(action));
                double weight = this.gamma * action.getProbability();
                utilityWeights.set(i, colIndex, weight * -1);
            }
            utilityWeights.set(i,i,1.0);
        }
        SimpleMatrix utilities = utilityWeights.invert().solve(rewards);

        for (int i = 0; i < states.length; i++) {
            utility.put(states[i],utilities.get(i,0));
        }

        return utility;
    }

    public Action argmax(Set<Action> As, Function<Action, Double> func) {
        double u = Double.NEGATIVE_INFINITY;
        Action a = null;
        for (Action ac : As) {
            Double val = func.apply(ac);
            if (val > u) {
                u = val;
                a = ac;
            }
        }
        if (a != null) return a;
        throw new IllegalStateException("something went wrong");
    }

    public double sum(Set<State> states, Function<State, Double> func) {
        double sum = 0;
        for (State s : states) {
            sum += func.apply(s);
        }
        return sum;
    }

    private double t(State s, Action a, State sPrime) {
        Set<Action> edges = graph.getAllEdges(s, sPrime);
        for (Action action : edges) {
            if (action.equals(a)) {
                return action.getProbability();
            }
        }
        return 0.0;
    }

    private double u(State s, HashMap<State, Double> utility) {
        return utility.get(s);
    }

    private double r(State s, Action a) {
        if (a == null){
            return 0;
        }
        return graph.getEdgeWeight(a);
    }
}
