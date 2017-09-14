package MDP;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 14-9-2017.
 */
abstract public class Action {

    Tower tower;


    public Action(Tower tower) {
        this.tower = tower;
    }

    static List<Action> getActions(State s){
        ArrayList<Action> actions = new ArrayList<>();
        if(s.inClaw != null){
            // add dropAction(s)
            DropAction.generateActions(s);
        }
        else{
            // add grabAction(s)
        }
        return actions;
    }

    public abstract State apply(State s);
}
