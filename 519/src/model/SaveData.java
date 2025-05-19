package model;
import java.io.Serializable;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;
    private int[][] mapState;
    private int steps;

    public SaveData(int[][] map, int steps) {
        this.mapState = map;
        this.steps = steps;
    }

    // Getters
    public int[][] getMapState() { return mapState; }
    public int getSteps() { return steps; }
}

