package MDP;

import org.jgrapht.graph.DefaultWeightedEdge;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 14-9-2017.
 */
abstract public class Action extends DefaultWeightedEdge{

    Tower tower;

    double probability = 1;


    public Action(Tower tower) {
        this.tower = tower;
    }

    static List<Action> getActions(State s){
        ArrayList<Action> actions = new ArrayList<>();
        if(s.inClaw != null){
            // add dropAction(s)
            actions.addAll(DropAction.generateActions(s));
        }
        else{
            // add grabAction(s)
            actions.addAll(GrabAction.generateActions(s));
        }
        return actions;
    }

    public void setProbability(double probability) {
        if(probability >1 || probability < 0){
            throw new IllegalArgumentException("Input is not a valid probability");
        }
        this.probability = probability;
    }

    public abstract State apply(State s);
}
