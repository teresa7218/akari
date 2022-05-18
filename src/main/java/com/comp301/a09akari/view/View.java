package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.Controller;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class View implements FXComponent {
  private final Controller controller;

  public View(Controller c) {
    controller = c;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();
    layout.getChildren().add(new TitleView(controller).render());
    layout.getChildren().add(new GameBoardView(controller).render());
    layout.getChildren().add(new MessageView(controller).render());
    layout.getChildren().add(new ControlView(controller).render());
    return layout;
  }
}
