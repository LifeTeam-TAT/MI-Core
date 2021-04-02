package org.ace.insurance.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zaw Than Oo
 * @Use It is used to sort the entities which have datet.
 */
public interface IDateSorter extends Serializable {
	public Date getSortingDate();
}
