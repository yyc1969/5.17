package model;

import java.io.Serializable;
import java.util.Date;

public class GameData implements Serializable {
    private int[][] currentMap;    // 当前地图状态
    private int currentSteps;      // 当前步数
    private int bestScore;         // 历史最佳成绩
    private Date lastSaved;        // 最后保存的数据

    public GameData(int[][] map, int steps, int bestScore) {
        this.currentMap = map;
        this.currentSteps = steps;
        this.bestScore = bestScore;
        this.lastSaved = new Date();
    }

    // 更新数据并覆盖旧数据
    public void updateData(int[][] newMap, int newSteps) {
        this.currentMap = newMap;
        this.currentSteps = newSteps;
        this.lastSaved = new Date();//把旧数据替换为新数据

        // 如果当前成绩更好，更新最佳成绩
        if (newSteps < bestScore || bestScore == 0) {
            this.bestScore = newSteps;
        }
    }

    // Getters
    public int[][] getCurrentMap() { return currentMap; }
    public int getCurrentSteps() { return currentSteps; }
    public int getBestScore() { return bestScore; }
    public Date getLastSaved() { return lastSaved; }
}