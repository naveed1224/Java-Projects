package com.learning.compSciJavaLearning.smallProblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Maze {
    private final int rows, columns;
    private final MazeLocation start, goal;
    private Cell[][] grid;

    public Maze(int rows, int columns, MazeLocation start, MazeLocation goal, double sparseness){
        this.rows = rows;
        this.columns = columns;
        this.start = start;
        this.goal = goal;

        grid = new Cell[rows][columns];

        for (Cell[] row: grid){
            Arrays.fill(row, Cell.EMPTY);
        }

        randomlyFill(sparseness);

        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;

    }

    public Maze(){
        this(40,100, new MazeLocation(0, 0), new MazeLocation(39,99), 0.03);
    }

    private void randomlyFill(double sparsness){
        for(int row = 0; row < rows; row++){
            for(int column = 0; column < columns; column++){
                if(Math.random() < sparsness){
                    grid[row][column] = Cell.BLOCKED;
                }
            }
        }
    }

    public List<MazeLocation> successors(MazeLocation ml){
        List<MazeLocation> locations = new ArrayList<>();

        if(ml.row +1 < rows && grid[ml.row+1][ml.column] != Cell.BLOCKED){
            locations.add(new MazeLocation(ml.row+1, ml.column));
        }

        if(ml.row -1 >= 0 && grid[ml.row-1][ml.column] != Cell.BLOCKED){
            locations.add(new MazeLocation(ml.row-1, ml.column));
        }

        if(ml.column + 1 < columns && grid[ml.row][ml.column+1] != Cell.BLOCKED){
            locations.add(new MazeLocation(ml.row, ml.column+1));
        }

        if(ml.column -1 >= 0 && grid[ml.row][ml.column+1] != Cell.BLOCKED){
            locations.add(new MazeLocation(ml.row, ml.column-1));
        }
        return locations;

    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Cell[] row: grid){
            for(Cell cell: row){
                sb.append(cell.toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public boolean goalTest(MazeLocation mazeLocation){
        return goal.equals(mazeLocation);
    }

    public static void main(String[] args) {
        Maze m = new Maze();
        System.out.println(m);
    }



    public enum Cell{
        EMPTY(" "),
        BLOCKED("X"),
        START("S"),
        GOAL("G"),
        PATH("*");

        private final String code;

        Cell(String c){
            code = c;
        }

        @Override
        public String toString(){
            return code;
        }
    }

    public static class MazeLocation{
        public final int row;
        public final int column;

        public MazeLocation(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MazeLocation that = (MazeLocation) o;
            return row == that.row && column == that.column;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + column;
            result = prime * result + row;

            return result;
        }
    }
}
