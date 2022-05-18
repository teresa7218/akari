package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.Controller;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Puzzle;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameBoardView implements FXComponent {
  private final Controller controller;
  private final int SIDE = 40;

  public GameBoardView(Controller c) {
    controller = c;
  }

  @Override
  public Parent render() {
    GridPane board = new GridPane();
    board.setMinSize(100, 100);
    board.setPrefSize(200, 400);
    board.setPadding(new Insets(10, 10, 10, 10));
    board.setHgap(10);
    board.setVgap(10);
    board.getStyleClass().add("board");
    // Fill up the board with tiles
    Puzzle p = controller.getActivePuzzle();
    int cols = p.getWidth();
    int rows = p.getHeight();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        final int column = j;
        final int row = i;
        Rectangle box = new Rectangle(SIDE, SIDE);
        box.setStroke(Color.BLACK);
        box.setFill(Color.WHITE);
        if (p.getCellType(i, j) == CellType.CORRIDOR) {
          box.setTranslateX((column) * SIDE);
          box.setTranslateY((row) * SIDE);
          if (controller.isLamp(row, column)) {
            if (controller.isLampIllegal(row, column)) {
              box.setFill(Color.RED);
            } else {
              box.setFill(Color.ORANGE);
            }
          } else if (controller.isLit(row, column)) {
            box.setFill(Color.YELLOW);
          }
          box.setOnMouseClicked(
              new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                  MouseButton button = event.getButton();
                  if (button == MouseButton.PRIMARY) {
                    controller.clickCell(row, column);
                  }
                }
              });
          board.getChildren().add(box);
        } else if (p.getCellType(i, j) == CellType.WALL) {
          box.setTranslateX((column) * SIDE);
          box.setTranslateY((row) * SIDE);
          box.setFill(Color.BLACK);
          board.getChildren().add(box);
        } else {
          if (controller.isClueSatisfied(row, column)) {
            box.setFill(Color.LIMEGREEN);
          } else {
            box.setFill(Color.GRAY);
          }
          StackPane stack = new StackPane();
          Text l = new Text(String.valueOf(p.getClue(row, column)));

          stack.setTranslateX((column) * SIDE);
          stack.setTranslateY((row) * SIDE);
          stack.getChildren().add(box);
          stack.getChildren().add(l);
          board.getChildren().add(stack);
        }
      }
    }

    return board;
  }
}
