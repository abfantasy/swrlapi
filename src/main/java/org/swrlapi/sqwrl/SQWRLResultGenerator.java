package org.swrlapi.sqwrl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

import java.util.List;

/**
 * Interface to configure a SQWRL query result and then add data to it. See the
 * {@link org.swrlapi.sqwrl.SQWRLResultManager} class for detailed comments.
 *
 * @see org.swrlapi.sqwrl.SQWRLResultManager
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.sqwrl.SQWRLQueryEngine
 */
public interface SQWRLResultGenerator
{
	/**
	 * @param columnNames A list of column names
	 * @throws SQWRLException If an error occurs during column addition
	 */
	void addColumns(@NonNull List<@NonNull String> columnNames) throws SQWRLException;

	/**
	 * @param columnName A column name
	 * @throws SQWRLException If an error occurs during column addition
	 */
	void addColumn(@NonNull String columnName) throws SQWRLException;

	/**
	 * @param columnName            A column name
	 * @param aggregateFunctionName The name of an aggregate function
	 * @throws SQWRLException If an error occurs during column addition
	 */
	void addAggregateColumn(@NonNull String columnName, @NonNull String aggregateFunctionName) throws SQWRLException;

	/**
	 * @param orderedColumnIndex The 0-based index of an order column
	 * @param ascending          Order ascending or descending
	 * @throws SQWRLException If an error occurs
	 */
	void setOrderByColumn(int orderedColumnIndex, boolean ascending) throws SQWRLException;

	/**
	 * @return True of the result is ordered
	 */
	boolean isOrdered();

	/**
	 * @return True if the result is ordered ascending
	 */
	boolean isOrderedAscending();

	/**
	 * Indicate that duplicate columns should be removed
	 */
	void setIsDistinct();

	/**
	 * @param columnName The name of a column
	 * @throws SQWRLException If an error occurs during display name addition
	 */
	void addColumnDisplayName(@NonNull String columnName) throws SQWRLException;

	/**
	 * @return The current number of result columns
	 * @throws SQWRLException If the result is already configured
	 */
	int getCurrentNumberOfColumns() throws SQWRLException;

	/**
	 * @return True if the result is configured
	 */
	boolean isConfigured();

	/**
	 * Indicate that the result is configured
	 *
	 * @throws SQWRLException If the result is already configured
	 */
	void configured() throws SQWRLException;

	/**
	 * Add a row to the result.
	 *
	 * @param resultValues A list of SQWRL result values
	 * @throws SQWRLException If an error occurs during row addition
	 */
	void addRow(@NonNull List<@NonNull SQWRLResultValue> resultValues) throws SQWRLException;

	/**
	 * Open a row for incremental cell addition
	 *
	 * @throws SQWRLException If an error occurs opening the row
	 */
	void openRow() throws SQWRLException;

	/**
	 * @param value A cell value
	 * @throws SQWRLException If an error occurs adding the cell
	 */
	void addCell(@NonNull SQWRLResultValue value) throws SQWRLException;

	/**
	 * @throws SQWRLException If an error occurs during row closing
	 */
	void closeRow() throws SQWRLException;

	/**
	 * @return True if a row is open
	 */
	boolean isRowOpen();

	/**
	 * @return True if the result is prepared
	 */
	boolean isPrepared();

	/**
	 * Indicate that the result is prepared
	 *
	 * @throws SQWRLException If the result is not yet configured or is already prepared
	 */
	void prepared() throws SQWRLException;

	void setLimit(int limit);

	/**
	 *
	 * @param nth 1-based index
	 */
	void setNth(int nth);

	/**
	 * Exclude the nth result row
	 *
	 * @param nth 1-based index
	 */
	void setNotNth(int nth);

  /**
   * Exclude all but the first n result rows.
   *
   * @param n Number of rows to retain
   */
	void setFirst(int n);

  /**
   * Exclude all but the last result row
   */
	void setLast();

  /**
   * Exclude all but the last n result rows
   *
   * @param n Number of rows to retain
   */
	void setLast(int n);

  /**
   * Exclude the first n result rows
   *
   * @param n Number of rows to exclude
   */
	void setNotFirst(int n);

  /**
   * Exclude the last n result rows
   *
   * @param n Number of rows to exclude
   */
	void setNotLast(int n);

	/**
	 *
	 * @param nth 1-based index
	 * @param  sliceSize size of the slice
	 */
	void setNthSlice(int nth, int sliceSize);

	/**
	 *
	 * @param nth 1-based index
	 * @param  sliceSize size of the slice
	 */
	void setNotNthSlice(int nth, int sliceSize);

	/**
	 *
	 * @param nth 1-based index
	 * @param  sliceSize size of the slice
	 */
	void setNthLastSlice(int nth, int sliceSize);

	/**
	 *
	 * @param nth 1-based index
	 * @param  sliceSize size of the slice
	 */
	void setNotNthLastSlice(int nth, int sliceSize);
}
