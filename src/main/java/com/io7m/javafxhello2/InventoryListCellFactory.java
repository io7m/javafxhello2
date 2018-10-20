package com.io7m.javafxhello2;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public final class InventoryListCellFactory
  implements Callback<ListView<Download>, ListCell<Download>>
{
  public InventoryListCellFactory()
  {

  }

  @Override
  public ListCell<Download> call(
    final ListView<Download> param)
  {
    return new InventoryListCell();
  }
}