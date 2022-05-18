package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;
  private final int rows;
  private final int cols;

  public PuzzleImpl(int[][] board) {
    if (board.length > 0 && board[0].length > 0) {
      this.board = board;
      rows = board.length;
      cols = board[0].length;
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public int getWidth() {
    return cols;
  }

  @Override
  public int getHeight() {
    return rows;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r > rows - 1 || c > cols - 1) {
      throw new IndexOutOfBoundsException();
    } else if (board[r][c] == 0) {
      return CellType.CLUE;
    } else if (board[r][c] == 1) {
      return CellType.CLUE;
    } else if (board[r][c] == 2) {
      return CellType.CLUE;
    } else if (board[r][c] == 3) {
      return CellType.CLUE;
    } else if (board[r][c] == 4) {
      return CellType.CLUE;
    } else if (board[r][c] == 5) {
      return CellType.WALL;
    } else if (board[r][c] == 6) {
      return CellType.CORRIDOR;
    }
    return null;
  }

  @Override
  public int getClue(int r, int c) {
    if (r > rows - 1 || c > cols - 1) {
      throw new IndexOutOfBoundsException();
    } else if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    } else {
      return board[r][c];
    }
  }
}
