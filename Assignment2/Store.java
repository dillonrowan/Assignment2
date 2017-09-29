package database;

/**
	A class for tracking packaged mail information.
	@author Bruno Blanco
	@version 10/04/2017
 */

public class Store
{
	/**
		@param	idNo Employee's ID number (int).
		@param	first_name Employee's first name (string).
		@param 	last_name Employee's last name (string).
	*/

	public User(int custID, String trackingNo, String shipDate, String delDate, float cost, int empID)
	{
		this.customerID = custID;
		this.trackingNumber = trackingNo;
		this.shippingDate = shipDate;
		this.deliveryDate = delDate;
		this.shippingCost = cost;
		this.employeeID = empID;
	}
	/**
		Getter for private member customerID.
		@return ID of customer who shipped package.
	*/
	public int getCustomerID()
	{
		return customerID;
	}

	/**
		Getter for private member trackingNumber.
		@return package's tracking number.
	*/
	public String getTrackingNumber()
	{
		return trackingNumber;
	}

	/**
		Getter for private member shippingDate.
		@return shipping date for package.
	*/
	public String getShipDate()
	{
		return shippingDate;
	}

	/**
		Getter for private member deliveryDate.
		@return delivery date for package.
	*/
	public String getDeliveryDate()
	{
		return deliveryDate;
	}

	/**
		Getter for private member shippingCost.
		@return Cost of shipping.
	*/
	public float getShippingCost()
	{
		return shippingCost;
	}

	/**
		Getter for private member employeeID.
		@return ID of employee who completed the sale.
	*/
	public int getEmployeeID()
	{
		return employeeID;
	}

	private int customerID;
	private String trackingNumber;
	private String shippingDate;
	private String deliveryDate;
	private float shippingCost;
	private int employeeID;
}
