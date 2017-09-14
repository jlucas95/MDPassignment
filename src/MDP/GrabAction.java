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
            grabActions.add(new GrabAction(Tower.copy(tower)));
        }
        return grabActions;
    }


}
