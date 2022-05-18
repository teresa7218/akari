package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TitleView implements FXComponent {
  private final Controller controller;

  public TitleView(Controller c) {
    controller = c;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    Text Title = new Text("AKARI");
    Title.setFont(Font.font(30));
    layout.getChildren().add(Title);
    HBox.setHgrow(layout, Priority.ALWAYS);
    Label currentPuzzleIndex = new Label(currentIndex());
    currentPuzzleIndex.setAlignment(Pos.BASELINE_CENTER);
    layout.getChildren().add(currentPuzzleIndex);
    return layout;
  }

  private String currentIndex() {
    int i = controller.getIndex() + 1;
    return "\npuzzle " + i + " of " + controller.getLibrarySize();
  }
}
