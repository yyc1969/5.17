package model;

import java.io.Serializable;
import java.sql.Array;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;


/**
 * This class is to record the map of one game. For example:
 */
public class MapModel implements Serializable {
    int[][] matrix;
    int[][] matrixInitial;
    private String mapType;
    private int steps;

    // constructor; input a two-dimensional array; new MapModel(twoDimensionalMatrix)
    public MapModel(int[][] matrix) {
        this.matrix = matrix;
        this.matrixInitial = new int[matrix.length][matrix[0].length];//备份该地图种类的初始信息
        copyMatrix(matrix, this.matrixInitial);//初始化类属性matrix，地图种类由构造方法传递
    }
    //把目标矩阵传递给初始矩阵
    public static void copyMatrix(int[][] initialMatrix, int[][] destinationMatrix)
    {
        for (int i = 0; i < initialMatrix.length; i++)
        {
            for (int j = 0; j < initialMatrix[0].length; j++)
            {
                destinationMatrix[i][j] = initialMatrix[i][j];
            }
        }
    }

    public void setMatrix(int matrix[][])
    {
        copyMatrix(matrix, this.matrix);
    }

    public  int[][] getMatrixInitial()
    {
        return matrixInitial;
    }

    public int getWidth() {
        return this.matrix[0].length;
    }

    public int getHeight() {
        return this.matrix.length;
    }

    public int getId(int row, int col) {
        return matrix[row][col];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < matrix[0].length;
    }

    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < matrix.length;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    // 在MapModel.java中添加
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                Files.newOutputStream(Paths.get(filename)))) {
            oos.writeObject(this);
        }
    }
}