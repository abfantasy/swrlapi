package org.protege.swrlapi.ext;

import java.net.URI;

import org.protege.swrlapi.xsd.XSDDate;
import org.protege.swrlapi.xsd.XSDDateTime;
import org.protege.swrlapi.xsd.XSDDuration;
import org.protege.swrlapi.xsd.XSDTime;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * Factory for constructing SWRLAPI literals.
 */
public interface SWRLAPILiteralFactory
{
	SWRLAPILiteral getSWRLAPILiteral(OWLLiteral literal);

	SWRLAPILiteral getSWRLAPILiteral(String literal, OWLDatatype datatype);

	SWRLAPILiteral getSWRLAPILiteral(String value);

	SWRLAPILiteral getSWRLAPILiteral(boolean value);

	SWRLAPILiteral getSWRLAPILiteral(double value);

	SWRLAPILiteral getSWRLAPILiteral(float value);

	SWRLAPILiteral getSWRLAPILiteral(int value);

	SWRLAPILiteral getSWRLAPILiteral(byte b);

	SWRLAPILiteral getSWRLAPILiteral(short s);

	SWRLAPILiteral getSWRLAPILiteral(URI uri);

	SWRLAPILiteral getSWRLAPILiteral(XSDDate date);

	SWRLAPILiteral getSWRLAPILiteral(XSDTime time);

	SWRLAPILiteral getSWRLAPILiteral(XSDDateTime datetime);

	SWRLAPILiteral getSWRLAPILiteral(XSDDuration duration);
}
