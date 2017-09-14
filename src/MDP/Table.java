package MDP;

import java.util.ArrayList;

/**
 * Created by Jan on 14-9-2017.
 */
public class Table extends ArrayList<Tower>{

    public Table copy() {
        Table newTable = new Table();
        for (Tower tower : this) {
            newTable.add(Tower.copy(tower));
        }
        return newTable;
    }
}
