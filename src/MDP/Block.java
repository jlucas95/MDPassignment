package MDP;

/**
 * Created by Jan on 14-9-2017.
 */
public class Block {
    final String name;


    public Block(String name) {
        if(name == null){
            throw new IllegalArgumentException("Name argument can not be null");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "MDP.Block{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block block = (Block) o;

        return name.equals(block.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
