package com.io7m.javafxhello2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class InventoryListCell extends ListCell<Download>
{
  private static final Logger LOG = LoggerFactory.getLogger(InventoryListCell.class);

  @FXML
  private Label fileName;

  @FXML
  private Label status;

  @FXML
  private ProgressBar progress;

  @FXML
  private Button stopOrResume;

  @FXML
  private Pane listViewContainer;

  private FXMLLoader loader;

  @Override
  protected void updateItem(
    final Download item,
    final boolean empty)
  {
    super.updateItem(item, empty);

    if (empty || item == null) {
      this.setText(null);
      this.setGraphic(null);
    } else {
      if (this.loader == null) {
        this.loader = new FXMLLoader(InventoryListCell.class.getResource("InventoryListView.fxml"));
        this.loader.setController(this);

        try {
          this.loader.load();
        } catch (final IOException e) {
          e.printStackTrace();
        }
      }

      this.fileName.setText(item.file_name);
      this.progress.setProgress(item.progress);
      this.status.setText(item.status);
      this.stopOrResume.setGraphic(
        new ImageView(new Image(InventoryListCell.class.getResource("dialog-error.png").toString())));
      this.stopOrResume.setOnAction(event -> LOG.debug("cancel {}", Integer.valueOf(item.id)));

      this.setText(null);
      this.setGraphic(this.listViewContainer);
    }
  }
}