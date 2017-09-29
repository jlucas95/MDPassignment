package MDP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 14-9-2017.
 */
public class GrabAction extends Action{

    public GrabAction(Tower tower) {
        super(tower);
    }

    public GrabAction(Tower tower, double probability){
        super(tower);
        setProbability(probability);
    }

    @Override
    public State apply(State s){
        State sPrime = State.copy(s);
        // Do sanity check
        if (sPrime.inClaw != null){
            throw new IllegalStateException("Cannot grab when block in claw.");
        }

        // Find the correct tower
        for (Tower t : sPrime.table) {
            if (t.equals(tower)) {
                Block block = t.removeBlock();
                if(t.size() == 0){
                    sPrime.table.remove(t);
                }
                sPrime.inClaw = block;
                break;
            }
        }
        return sPrime;
    }

    public static List<GrabAction> generateActions(State s){
        ArrayList<GrabAction> grabActions = new ArrayList<>();

        // for tower: add grabAction to that tower
        for (Tower tower : s.table) {
            // the correct action
            GrabAction action = new GrabAction(Tower.copy(tower));
            grabActions.add(action);

            // the wrong action(s)
            List<GrabAction> wrongActions = getWrongActions(s, action);
            action.setProbability(1 - (wrongActions.size() * 0.1));
            grabActions.addAll(wrongActions);
        }
        return grabActions;
    }

    private static List<GrabAction> getWrongActions(State s, GrabAction a) {
        // If there are multiple blocks that could be grabbed, each wrong block has a 10% chance to be chosen.
        List<GrabAction> wrongActions = new ArrayList<GrabAction>();
        for (Tower tower : s.table) {
            if(!tower.equals(a.tower)){
                wrongActions.add(new GrabAction(tower, .1));
            }
        }
        return wrongActions;
    }


}
