package com.io7m.javafxhello2;

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

@Value.Immutable
@ImmutablesStyleType
public interface EventErrorType extends EventType
{
  @Override
  default EventCategory category()
  {
    return EventCategory.EVENT_ERROR;
  }

  @Value.Parameter
  String message();

  @Override
  default String describe()
  {
    return new StringBuilder(128)
      .append("Error: ")
      .append(this.message())
      .toString();
  }
}
