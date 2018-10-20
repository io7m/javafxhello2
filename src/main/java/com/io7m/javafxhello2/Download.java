package com.io7m.javafxhello2;

import java.util.Objects;

public final class Download
{
  final int id;
  final String file_name;
  final String status;
  final double progress;

  public Download(
    final int id,
    final String file_name,
    final String status,
    final double progress)
  {
    this.id = id;
    this.file_name = Objects.requireNonNull(file_name, "file_name");
    this.status = Objects.requireNonNull(status, "status");
    this.progress = progress;
  }
}
