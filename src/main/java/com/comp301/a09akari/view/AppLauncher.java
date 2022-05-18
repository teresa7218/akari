package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.Controller;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // Model
    List<Puzzle> puzzles = SamplePuzzles.create();
    PuzzleLibrary library = new PuzzleLibraryImpl(puzzles);
    Model model = new ModelImpl(library);

    // controller
    Controller controller = new Controller(model);

    // view
    View view = new View(controller);

    // make scene
    Scene scene = new Scene(view.render());
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);

    // refresh view when model changed
    model.addObserver(
        (Model m) -> {
          scene.setRoot(view.render());
          stage.sizeToScene();
        });

    // Show the stage
    stage.setTitle("AKARI");
    stage.show();
  }
}
