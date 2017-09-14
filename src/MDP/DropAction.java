package MDP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 14-9-2017.
 */
public class DropAction extends Action {

    public DropAction(Tower tower) {
        super(tower);
    }

    @Override
    public State apply(State s) {
        State sPrime = State.copy(s);

        if (sPrime.inClaw == null) {
            throw new IllegalStateException("Cannot drop without something in claw");
        }

        // find tower
        for (Tower t : sPrime.table) {
            if(t.equals(tower)){
                t.addBlock(sPrime.inClaw);
                sPrime.inClaw = null;
                break;
            }
        }
        return sPrime;
    }

    public static List<DropAction> generateActions(State s) {
        ArrayList<DropAction> dropActions = new ArrayList<>();

        // add drop on table
        dropActions.add(new DropAction(new Tower()));


        // for tower: add drop on tower
        for (Tower tower : s.table) {
            dropActions.add(new DropAction(Tower.copy(tower)));
        }
        return dropActions;
    }


}
