package tests;

import MDP.Block;
import MDP.State;
import MDP.Table;
import MDP.Tower;
import javafx.scene.control.Tab;

import java.util.ArrayList;

/**
 * Created by Jan on 15-9-2017.
 */
public class EqualsTest {

    public static void main(String[] args) {
        Block C = new Block("C");
        Block B = new Block("B");
        Block A = new Block("A");

        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(A);
        blocks.add(B);
        blocks.add(C);

        Tower t = new Tower(blocks);
        Table table = new Table();
        table.add(t);

        State state = new State(table);

        Tower newTower = new Tower(blocks);
        Table newTable = new Table();
        newTable.add(newTower);

        State newS = new State(newTable);

        if(state.equals(newS)){
            System.out.println("equals");
        }
        else{
            System.out.println("not equal");
        }
    }
}
