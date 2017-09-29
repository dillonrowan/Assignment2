package database;

/**
	A class for tracking packaged mail information.
	@author Bruno Blanco
	@version 10/04/2017
 */

public class User implements Comparable
{
	/**
		@param	idNo Employee's ID number (int).
		@param	first_name Employee's first name (string).
		@param 	last_name Employee's last name (string).
	*/

	public User(int idNo, String first_name, String last_name)
	{
		this.ID = idNo;
		this.fName = first_name;
		this.lName = last_name;
	}
	/**
		Getter for private member ID.
		@return User's ID.
	*/
	public int getID()
	{
		return ID;
	}

	/**
		Getter for private member fname.
		@return User's first name.
	*/
	public String getFirstName()
	{
		return fName;
	}

	/**
		Getter for private member lname.
		@return User's last name.
	*/
	public String getLastName()
	{
		return lName;
	}

	private int ID;
	private String fName;
	private String lName;
}
