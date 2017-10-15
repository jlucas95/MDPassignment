package algorithms;

import java.util.*;
import java.util.stream.Stream;

import MDP.MarkovDecisionProcessBuilder;
import MDP.Action;
import MDP.State;
import util.Tuple;
import org.jgrapht.graph.DirectedWeightedPseudograph;

public class QLearner {

	private DirectedWeightedPseudograph<State, MDP.Action> MDP;
	private Map<Tuple<State, Action>, Double> Q;
	private Map<Tuple<State, Action>, Double> lambda;
	private double gamma;
	

	public QLearner(double gamma) {
		this.MDP = new MarkovDecisionProcessBuilder(State.getStartState(), false).build();
        this.gamma = gamma;
        initialize();
    }
	
	private void initialize() {

//		Get all states from the graph and assign 0.0 as a value
		this.Q = new HashMap<>();
		this.lambda = new HashMap<>();
		for (State state : this.MDP.vertexSet()) {
			for (MDP.Action action : MDP.outgoingEdgesOf(state)) {
				this.Q.put(new Tuple<>(state, action), 0.0);
				this.lambda.put(new Tuple<>(state, action), 1.0);
			}
		}

	}

    public void simulate(State start, int steps){
        State s = start;
        for (int i = 0; i < steps; i++) {
            // choose an action a ∈ A(s)
            Action a = chooseAction(s);

            // d execute a, observe the reward and identify the new state s' ∈ S
            Tuple<State, Double> actionResult = executeAction(s, a);
            State sPrime = actionResult.getS();
            double r = actionResult.getT();

            //Recalculate Q
            Tuple<State, Action> sa = new Tuple<>(s, a);
            Q.put(sa, calculateNewQ(s, a, r, sPrime));

            // Update lambda -- Done by decaying lambda --
            lambda.put(sa, lambda.get(sa) * 0.9);
            s = sPrime;

            // Return to start state when the goal state has been reached
            if (s.equals(State.getGoalState())) s = start;
        }
    }

    private Double calculateNewQ(State s, Action a, double r, State sPrime) {
        Tuple<State, Action> sa = new Tuple<>(s, a);
        Double oldQ = this.Q.get(sa);
        double lamdba = this.lambda.get(sa);
        //            r(s, a,s')+γ· max (a'∈A(s')) {Q(s', a')} − Q(s, a)
        double max = Double.MIN_VALUE;
        for (Action aPrime : MDP.outgoingEdgesOf(sPrime)) {
            Double Qval = this.Q.get(new Tuple<>(sPrime, aPrime));
            if (Qval > max){
                max = Qval;
            }
        }
        return oldQ + lamdba * (r + gamma * max - oldQ);
    }


    private Action chooseAction(State state) {
        Set<Action> actions = MDP.outgoingEdgesOf(state);
        Optional<Action> first = actions.stream()
                .skip((int) (actions.size() * Math.random()))
                .findFirst();
        if(first.isPresent()){
            return first.get();
        }
        throw new IllegalStateException("no action possible in state");
    }

    private Tuple<State, Double> executeAction(State state, Action action) {
        Action realAction = getAction(state, action);
        double reward = MDP.getEdgeWeight(realAction);
        State newState = MDP.getEdgeTarget(realAction);
        return new Tuple<>(newState, reward);
    }

    private Action getAction(State state, Action action){
        Stream<Action> actions = MDP.outgoingEdgesOf(state).stream().filter(a -> a.ID == action.ID);
        double p = Math.random();
        double probSum = 0.0;
        Iterator<Action> iterator = actions.iterator();
        while (iterator.hasNext()) {
            Action next = iterator.next();
            probSum += next.getProbability();
            if(p <= probSum) return next;
        }
        throw new IllegalStateException("No action could be selected");
    }

    public Map<State, Action> getPolicy(){
        HashMap<State, Action> map = new HashMap<>();
        for(State s : MDP.vertexSet()){
            Set<Action> A = MDP.outgoingEdgesOf(s);
            double max = -Double.MAX_VALUE;
            Action action = null;
            for (Action a : A) {
                double q = this.Q.get(new Tuple<>(s, a));
                if (q > max) {
                    max = q;
                    action = a;
                }
            }
            map.put(s, action);
        }
        return map;
    }

}
