package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;

import java.util.Random;

public class Controller implements AlternateMvcController {
  private final Model model;

  public Controller(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    int index = model.getActivePuzzleIndex();
    if (index + 1 < model.getPuzzleLibrarySize()) {
      model.setActivePuzzleIndex(index + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int index = model.getActivePuzzleIndex();
    if (index - 1 >= 0) {
      model.setActivePuzzleIndex(index - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int min_val = 0;
    int max_val = model.getPuzzleLibrarySize() - 1;
    Random ran = new Random();
    int x = ran.nextInt(max_val) + min_val;
    while (x == getIndex()) {
      x = ran.nextInt(max_val) + min_val;
    }
    model.setActivePuzzleIndex(x);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return model.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }

  @Override
  public int getIndex() {
    return model.getActivePuzzleIndex();
  }

  @Override
  public int getLibrarySize() {
    return model.getPuzzleLibrarySize();
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    return model.isLampIllegal(r, c);
  }
}
