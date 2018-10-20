package com.io7m.javafxhello2;

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

@Value.Immutable
@ImmutablesStyleType
public interface EventInitializedType extends EventType
{
  @Override
  default EventCategory category()
  {
    return EventCategory.EVENT_INFO;
  }

  @Value.Parameter
  String version();

  @Override
  default String describe()
  {
    return new StringBuilder(128)
      .append("Application ")
      .append(this.version())
      .append(" initialized")
      .toString();
  }
}
