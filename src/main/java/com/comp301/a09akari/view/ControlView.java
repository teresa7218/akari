package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.Controller;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlView implements FXComponent {
  private final Controller controller;

  public ControlView(Controller c) {
    controller = c;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    layout.setPadding(new Insets(15, 12, 15, 12));
    layout.setSpacing(10);

    Button buttonPrevious = new Button("Previous Puzzle");
    buttonPrevious.setPrefSize(200, 20);
    buttonPrevious.setOnAction(
        (ActionEvent event) -> {
          controller.clickPrevPuzzle();
        });

    Button buttonReset = new Button("Reset");
    buttonReset.setPrefSize(200, 20);
    buttonReset.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });

    Button buttonRandom = new Button("Random Puzzle");
    buttonRandom.setPrefSize(200, 20);
    buttonRandom.setOnAction(
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });

    Button buttonNext = new Button("Next Puzzle");
    buttonNext.setPrefSize(200, 20);
    buttonNext.setOnAction(
        (ActionEvent event) -> {
          controller.clickNextPuzzle();
        });
    layout.getChildren().addAll(buttonReset, buttonRandom, buttonPrevious, buttonNext);
    return layout;
  }
}
