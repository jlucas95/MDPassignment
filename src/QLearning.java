import MDP.Action;
import MDP.State;
import algorithms.QLearner;

import java.util.Map;

/**
 * Created by Jan on 15-10-2017.
 */
public class QLearning {

    public static void main(String[] args) {
        // Initialize Qlearner
        QLearner qLearner = new QLearner(0.9);
        qLearner.simulate(State.getStartState(), 1000);
        Map<State, Action> policy = qLearner.getPolicy();

    }

}
