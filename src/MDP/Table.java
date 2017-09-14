package MDP;


import java.util.HashSet;

/**
 * Created by Jan on 14-9-2017.
 */
public class Table extends HashSet<Tower> {

    public Table copy() {
        Table newTable = new Table();
        for (Tower tower : this) {
            newTable.add(Tower.copy(tower));
        }
        return newTable;
    }
}
