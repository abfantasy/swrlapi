package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLExpressionResultValue;

abstract class DefaultSQWRLExpressionResultValue extends DefaultSQWRLResultValue implements
  SQWRLExpressionResultValue
{
  @NonNull private final String rendering;

  public DefaultSQWRLExpressionResultValue(@NonNull String rendering)
  {
    this.rendering = rendering;
  }

  @NonNull @Override public String getRendering()
  {
    return this.rendering;
  }

  @Override public boolean isExpression() { return true; }

  @Override public @NonNull SQWRLExpressionResultValue asExpressionResult() throws SQWRLException
  {
    return this;
  }
}
