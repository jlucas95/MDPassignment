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

    public DropAction(Tower tower, double probability){
        super(tower);
        setProbability(probability);
    }

    @Override
    public State apply(State s) {
        State sPrime = State.copy(s);

        if (sPrime.inClaw == null) {
            throw new IllegalStateException("Cannot drop without something in claw");
        }

        if (tower.isEmpty()) {
            // create new tower
            Tower t = new Tower();
            sPrime.table.add(t);
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
            DropAction action = new DropAction(Tower.copy(tower));
            dropActions.add(action);
            List<DropAction> wrongActions = getWrongActions(s, action);
            action.setProbability(1 - (wrongActions.size() * 0.1));
            dropActions.addAll(wrongActions);
        }
        return dropActions;
    }

    //there is a 10% chance that a block is place on top of the wrong block if the block must
    // be place on top of another block and there are two blocks on which a bock can be placed.
    private static List<DropAction> getWrongActions(State s, DropAction a) {
        // If there are multiple blocks that could be grabbed, each wrong block has a 10% chance to be chosen.
        List<DropAction> wrongActions = new ArrayList<DropAction>();
        for (Tower tower : s.table) {
            if(!tower.equals(a.tower)){
                wrongActions.add(new DropAction(tower, .1));
            }
        }
        return wrongActions;
    }


}
