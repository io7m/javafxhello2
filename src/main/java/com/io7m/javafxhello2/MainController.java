package com.io7m.javafxhello2;

import io.reactivex.subjects.Subject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class MainController implements Initializable
{
  private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

  private final ScheduledExecutorService executor;

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

  @FXML
  private ListView<Download> inventoryDownloads;

  private Subject<EventType> subject;

  public MainController()
  {
    this.executor = Executors.newScheduledThreadPool(1);
  }

  private static void updateDownloads(
    final ObservableList<Download> items)
  {
    for (int index = 0; index < 30; ++index) {
      final Download download = items.get(index);
      double progress = download.progress;
      if (progress >= 1.0) {
        progress = 0.0;
      } else {
        progress += 0.001;
      }

      items.set(index, new Download(download.id, download.file_name, download.status, progress));
    }
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
      case EVENT_NONE: {
        this.statusImage.setImage(null);
        break;
      }
      case EVENT_INFO: {
        this.statusImage.setImage(
          new Image(MainController.class.getResource("computer.png").toString()));
        break;
      }
      case EVENT_ERROR: {
        this.statusImage.setImage(
          new Image(MainController.class.getResource("dialog-error.png").toString()));
        break;
      }
    }


    this.statusLabel.setText(event.describe());
  }

  @Override
  public void initialize(
    final URL location,
    final ResourceBundle resources)
  {
    LOG.debug("initialize");

    final var items = FXCollections.<Download>observableArrayList();
    for (int index = 0; index < 30; ++index) {
      items.add(new Download(
        index,
        "output.txt",
        "100/1000, 2mb/s, 00:01:32 remaining",
        Math.random()));
    }

    this.inventoryDownloads.setCellFactory(new InventoryListCellFactory());
    this.inventoryDownloads.setItems(items);

    this.executor.scheduleAtFixedRate(
      () -> Platform.runLater(() -> updateDownloads(items)),
      100L,
      100L,
      TimeUnit.MILLISECONDS);
  }
}
