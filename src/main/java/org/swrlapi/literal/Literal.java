package org.swrlapi.literal;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.exceptions.LiteralException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;

/**
 * Wraps an OWLAPI literal to provide additional convenience methods used by the SWRLAPI.
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public interface Literal
{
  boolean isNumeric();

  boolean isByte();

  boolean isShort();

  boolean isInt();

  boolean isLong();

  boolean isFloat();

  boolean isDouble();

  boolean isString();

  boolean isBoolean();

  boolean isAnyURI();

  boolean isTime();

  boolean isDate();

  boolean isDateTime();

  boolean isDuration();

  boolean isDecimal();

  boolean isInteger();

  boolean isNegativeInteger();

  boolean isPositiveInteger();

  boolean isNonNegativeInteger();

  boolean isNonPositiveInteger();

  boolean isUnsignedLong();

  boolean isUnsignedInt();

  boolean isUnsignedShort();

  boolean isUnsignedByte();

  boolean isRDFPlainLiteral();

  boolean isRDFSLiteral();

  boolean isRDFXMLLiteral();

  byte getByte() throws LiteralException;

  short getShort() throws LiteralException;

  int getInt() throws LiteralException;

  long getLong() throws LiteralException;

  float getFloat() throws LiteralException;

  double getDouble() throws LiteralException;

  boolean getBoolean() throws LiteralException;

  @NonNull String getString() throws LiteralException;

  @NonNull URI getAnyURI() throws LiteralException;

  @NonNull XSDTime getTime() throws LiteralException;

  @NonNull XSDDate getDate() throws LiteralException;

  @NonNull XSDDateTime getDateTime() throws LiteralException;

  @NonNull XSDDuration getDuration() throws LiteralException;

  @NonNull BigDecimal getDecimal() throws LiteralException;

  @NonNull BigInteger getInteger() throws LiteralException;

  @NonNull BigInteger getNonNegativeInteger() throws LiteralException;

  @NonNull BigInteger getNonPositiveInteger() throws LiteralException;

  @NonNull BigInteger getNegativeInteger() throws LiteralException;

  @NonNull BigInteger getPositiveInteger() throws LiteralException;

  @NonNull long getUnsignedLong() throws LiteralException;

  @NonNull long getUnsignedInt() throws LiteralException;

  @NonNull int getUnsignedShort() throws LiteralException;

  @NonNull short getUnsignedByte() throws LiteralException;

  @NonNull String getRDFPlainLiteral() throws LiteralException;

  @NonNull String getRDFSLiteral() throws LiteralException;

  @NonNull String getRDFXMLLiteral() throws LiteralException;

  @NonNull String getValue();

  boolean isComparable();

  boolean isQuotableType();

   @NonNull @SideEffectFree @Override String toString();

  @NonNull String toQuotedString();

  @NonNull OWLLiteral getOWLLiteral();

  @NonNull OWLDatatype getOWLDatatype();

  @NonNull String getOWLDatatypeName();
}
