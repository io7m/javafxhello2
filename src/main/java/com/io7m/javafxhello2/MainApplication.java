package com.io7m.javafxhello2;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApplication extends Application
{
  public MainApplication()
  {

  }

  @Override
  public void start(final Stage stage)
    throws Exception
  {
    final BehaviorSubject<EventType> bus = BehaviorSubject.create();
    bus.onNext(EventInitialized.of("1.2.3"));

    final FXMLLoader loader =
      new FXMLLoader(MainApplication.class.getResource("MainLayout.fxml"));

    final AnchorPane pane = loader.load();
    final MainController controller = loader.getController();
    controller.postInitialize(bus);

    stage.setScene(new Scene(pane));
    stage.titleProperty().setValue("Example");
    stage.show();
  }
}
