package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary library;
  private int current;
  private boolean[][] lamps;
  private final boolean[] solved;
  private final List<ModelObserver> observers;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException();
    } else {
      this.library = library;
      current = 0;
      solved = new boolean[library.size()];
      observers = new ArrayList<>();
      resetPuzzle();
    }
  }

  @Override
  public void addLamp(int r, int c) {
    //    System.out.println("row " + r);
    //    System.out.println("col " + c);
    if (r < 0
        || c < 0
        || r > library.getPuzzle(current).getHeight() - 1
        || c > library.getPuzzle(current).getWidth() - 1) {
      throw new IndexOutOfBoundsException();
    } else if (library.getPuzzle(current).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      lamps[r][c] = true;
      notifyObserver();
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r < 0
        || c < 0
        || r > library.getPuzzle(current).getHeight() - 1
        || c > library.getPuzzle(current).getWidth() - 1) {
      throw new IndexOutOfBoundsException();
    } else if (library.getPuzzle(current).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      lamps[r][c] = false;
      notifyObserver();
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r < 0
        || c < 0
        || r > library.getPuzzle(current).getHeight() - 1
        || c > library.getPuzzle(current).getWidth() - 1) {
      throw new IndexOutOfBoundsException();
    } else if (library.getPuzzle(current).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else if (lamps[r][c]) {
      return true;
    } else {
      // start from the position (r,c)
      // to check if there is lamp by doing (r--,c) until reach r = 0
      // and (r++,c) until reach to top and do the same with c
      // if the position has something that is not a corridor cell type then break the loop bc the
      // light is blocked anyway
      boolean lit = false;
      // (rows++)
      for (int i = r; i < library.getPuzzle(current).getHeight(); i++) {
        if (lamps[i][c]) {
          lit = true;
        }
        if (library.getPuzzle(current).getCellType(i, c) != CellType.CORRIDOR) break;
      }
      // (rows--)
      for (int i = r; i >= 0; i--) {
        if (lamps[i][c]) {
          lit = true;
        }
        if (library.getPuzzle(current).getCellType(i, c) != CellType.CORRIDOR) break;
      }
      // col++
      for (int i = c; i < library.getPuzzle(current).getWidth(); i++) {
        if (lamps[r][i]) {
          lit = true;
        }
        if (library.getPuzzle(current).getCellType(r, i) != CellType.CORRIDOR) break;
      }
      // col--
      for (int i = c; i >= 0; i--) {
        if (lamps[r][i]) {
          lit = true;
        }
        if (library.getPuzzle(current).getCellType(r, i) != CellType.CORRIDOR) break;
      }

      return lit;
    }
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r < 0
        || c < 0
        || r > library.getPuzzle(current).getHeight() - 1
        || c > library.getPuzzle(current).getWidth() - 1) {
      throw new IndexOutOfBoundsException();
    } else if (library.getPuzzle(current).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      return lamps[r][c];
    }
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    //    System.out.println("row " + r + " column " + c);
    if (r < 0
        || c < 0
        || r > library.getPuzzle(current).getHeight() - 1
        || c > library.getPuzzle(current).getWidth() - 1) {
      throw new IndexOutOfBoundsException();
    } else if (library.getPuzzle(current).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      lamps[r][c] = false;
      if (isLit(r, c)) {
        lamps[r][c] = true;
        //        System.out.println("lamp in row " + r + " and column " + c +" is legal");
        return true;
      }
      lamps[r][c] = true;
      //      System.out.println("lamp in row " + r + " and column " + c +" is illegal");
      return false;
    }
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(current);
  }

  @Override
  public int getActivePuzzleIndex() {
    return current;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index >= library.size()) {
      throw new IllegalArgumentException();
    }
    current = index;
    lamps =
        new boolean[library.getPuzzle(current).getHeight()][library.getPuzzle(current).getWidth()];
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    lamps =
        new boolean[library.getPuzzle(current).getHeight()][library.getPuzzle(current).getWidth()];
    notifyObserver();
  }

  @Override
  public boolean isSolved() {
    Puzzle p = getActivePuzzle();
    //    int count = 0;
    for (int i = 0; i < p.getWidth(); i++) {
      //      System.out.println("column " +  i);
      for (int j = 0; j < p.getHeight(); j++) {
        //        count++;
        //        System.out.println("in loop " + count);
        //        System.out.println("row " + j);
        if (p.getCellType(j, i) == CellType.CLUE) {
          if (!isClueSatisfied(j, i)) {
            //                                    System.out.println("clue is not satisfied");
            return false;
          }
        }
        if (p.getCellType(j, i) == CellType.CORRIDOR) {
          if (!isLit(j, i)) {
            //                                    System.out.println("the cell in row " + j + "
            // column " + i + "is not lit");
            return false;
          }
          if (lamps[j][i]) {
            if (isLampIllegal(j, i)) {
              //                            System.out.println("Lamp is illegal");
              return false;
            }
          }
        }
      }
    }
    solved[current] = true;
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {

    if (r < 0
        || c < 0
        || r > library.getPuzzle(current).getHeight() - 1
        || c > library.getPuzzle(current).getWidth() - 1) {
      throw new IndexOutOfBoundsException();
    } else if (library.getPuzzle(current).getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    } else {
      int k = 0;
      if (r + 1 <= library.getPuzzle(current).getHeight() - 1 && lamps[r + 1][c]) {
        //        System.out.println("row: " + r + " col: " + c);
        k++;
      }
      if (r - 1 >= 0 && lamps[r - 1][c]) {
        k++;
      }
      if (c + 1 <= library.getPuzzle(current).getWidth() - 1 && lamps[r][c + 1]) {
        k++;
      }
      if (c - 1 >= 0 && lamps[r][c - 1]) {
        //        System.out.println("row: " + r + " col: " + c);
        k++;
      }
      return k == getActivePuzzle().getClue(r, c);
    }
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    } else {
      observers.add(observer);
    }
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    } else {
      observers.remove(observer);
    }
  }

  public void notifyObserver() {
    for (ModelObserver mo : observers) {
      mo.update(this);
    }
  }
}
