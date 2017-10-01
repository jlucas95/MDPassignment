package MDP;


import java.util.HashSet;
import java.util.TreeSet;

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

    @Override
    public boolean equals(Object o) {
        if(super.equals(o)) return true;

        if(o == null || o.getClass() != this.getClass()) return false;
        Table t = ((Table) o);

        for (Tower tower : this) {
            // stores whether we have already found a matching tower.
            boolean match = false;
            for (Tower tower1 : t) {
                if(tower.equals(tower1)){
                    // There can be only one matching tower.
                    if (match) {
                        return false;
                    }
                    match = true;
                }
            }
            // return false if there are no matching towers.
            if(!match){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Table{");
        for (Tower tower : this) {
             sb.append(tower.toString());
        }
        return sb.append("}").toString();
    }
}
