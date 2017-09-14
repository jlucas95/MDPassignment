package MDP;

import java.util.ArrayList;

/**
 * Created by Jan on 14-9-2017.
 */
public class Tower {
    /**
     * Ordered list with the blocks in this tower.
     * First block is on the bottom of the tower
     */
    private ArrayList<Block> blocks = new ArrayList<Block>();

    public Tower(){}

    public Tower(ArrayList<Block> blocks) {
        this.blocks.addAll(blocks);
    }

    public Tower(Block block) {
        this.blocks.add(block);
    }

    public Block removeBlock(){
        return blocks.remove(blocks.size() - 1);
    }

    public void addBlock(Block block){
        blocks.add(block);
    }

    public Block getTopBlock(){
        try{
            return blocks.get(blocks.size() - 1);
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tower tower = (Tower) o;

        return blocks.equals(tower.blocks);
    }

    @Override
    public int hashCode() {
        return blocks.hashCode();
    }

    static Tower copy(Tower tower){
        return new Tower(tower.blocks);
    }
}
