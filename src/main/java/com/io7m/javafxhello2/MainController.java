package com.io7m.javafxhello2;

import io.reactivex.Observable;
import io.reactivex.subjects.Subject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class MainController
{
  private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

  @FXML
  private ToggleGroup sidebarGroup;

  @FXML
  private Pane catalogPane;

  @FXML
  private Pane inventoryPane;

  @FXML
  private Pane settingsPane;

  @FXML
  private ImageView statusImage;

  @FXML
  private Label statusLabel;

  private Subject<EventType> subject;

  public MainController()
  {

  }

  @FXML
  public void initialize()
  {
    LOG.debug("initialize");
  }

  public void onMenuSelected()
  {
    LOG.debug("onMenuSelected");
  }

  public void onMenuQuit()
  {
    LOG.debug("onMenuQuit");
    System.exit(0);
  }

  public void onMenuError()
  {
    LOG.debug("onMenuError");
    this.subject.onNext(EventError.of("Something failed!"));
  }

  public void onCatalogSelected()
  {
    LOG.debug("onCatalogSelected: {}", this.sidebarGroup.getSelectedToggle());
    this.catalogPane.toFront();
    this.catalogPane.setVisible(true);
  }

  public void onInventorySelected()
  {
    LOG.debug("onInventorySelected: {}", this.sidebarGroup.getSelectedToggle());
    this.inventoryPane.toFront();
    this.inventoryPane.setVisible(true);
  }

  public void onSettingsSelected()
  {
    LOG.debug("onSettingsSelected: {}", this.sidebarGroup.getSelectedToggle());
    this.settingsPane.toFront();
    this.settingsPane.setVisible(true);
  }

  public void postInitialize(
    final Subject<EventType> bus)
  {
    LOG.debug("postInitialize");
    this.subject = Objects.requireNonNull(bus, "bus");
    bus.subscribe(event -> Platform.runLater(() -> this.onStatusEvent(event)));
  }

  private void onStatusEvent(
    final EventType event)
  {
    switch (event.category()) {
      case EVENT_NONE:
      {
        this.statusImage.setImage(null);
        break;
      }
      case EVENT_INFO:
      {
        this.statusImage.setImage(
          new Image(MainController.class.getResource("computer.png").toString()));
        break;
      }
      case EVENT_ERROR:
      {
        this.statusImage.setImage(
          new Image(MainController.class.getResource("dialog-error.png").toString()));
        break;
      }
    }


    this.statusLabel.setText(event.describe());
  }
}