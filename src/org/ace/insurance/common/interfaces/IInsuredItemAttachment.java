/**
 * 
 */
package org.ace.insurance.common.interfaces;

/**
 * The contract which defines the basic behaviors that all implementations of
 * attachment used in the insured items are obliged to comply.
 * 
 * @author ACN
 * @since 1.0.0
 * @date 2013/05/20
 */
public interface IInsuredItemAttachment {

	/**
	 * Accessor to retrieve the unique ID of the attachment.
	 * 
	 * @return the unique ID of the insured item
	 */
	public String getId();

	/**
	 * Accessor to retrieve the name of the attachment.
	 * 
	 * @return the name of the attachment
	 */
	public String getName();

	/**
	 * Accessor to retrieve the path of the attachment file.
	 * 
	 * @return the path of the attachment file
	 */
	public String getFilePath();

	/**
	 * Internal usage of the underlying technologies, not a business related
	 * behavior.
	 * 
	 * @return version number
	 */
	public int getVersion();
}
