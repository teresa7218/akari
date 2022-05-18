package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.Controller;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class MessageView implements FXComponent {
  private final Controller controller;

  public MessageView(Controller controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    Pane layout = new Pane();
    Label text = new Label("CONGRATS! YOU'VE SOLVED IT!");
    text.setFont(Font.font(40));
    if (controller.isSolved()) {
      layout.getChildren().add(text);
    }
    layout.setPrefSize(100, 200);
    return layout;
  }
}
