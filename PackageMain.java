package database;

/**
	A class for tracking packaged mail information.
	@author Bruno Blanco
	@version 09/20/2017
 */

import java.util.*;
import java.io.*;

public class PackageMain
{
	public static void main(String[] args) throws Exception
	{
		/*Varibles:
		ArrayList list1 - List to hold packages imported and created by user.
		ArrayList list2 - List to hold packages when searching.
		ListIterator it - Iterator to traverse list1.
		File file - file for initial import.
		Scanner sc - reads input from user.
		boolean done - program loop control variable, becomes true if user
						enter 6 in menu option.
		int choice - variable to hold user's input choice from menu.
		int location - variable to hold position to be deleted from array.
		int volume - variable to hold package's volume for package construction.
		float weight - same as volume, additionally holds one boundary for search for weight.
		float weight2 - holds second boundary for search for weight.
		float tempWeight - value used for comparison of weight for search.
		String trackingNo, type, spec, mClass - see volume.
		String[] types - Array with all possible package types, this is done to
							reduce the user input checking. Input only needs to
							be an integer within the range.
		String[] specification - see String[] types.
		String[] mailingClass - String[] types.
		String dashes - String with 65x'-', for display formatting.
		*/
		ArrayList<Package> list1 = new ArrayList<Package>();
		ArrayList<Package> list2 = new ArrayList<Package>();
		ListIterator<Package> it;// = list1.listIterator();
		File file = new File("packages.txt");
		Scanner sc = new Scanner(System.in);
		boolean done = false;
		int choice, location, volume;
		float weight, weight2, tempWeight;
		String trackingNo, type, spec, mClass;
		String[] types = {"Postcard", "Letter", "Envelope", "Packet", "Box", "Crate", "Drum", "Roll", "Tube"};
		String[] specification = {"Fragile", "Books", "Catalogs", "Do-Not-Bend", "N/A"};
		String[] mailingClass = {"First-Class", "Priority", "Retail", "Ground", "Metro"};
		String dashes = new String(new char[65]).replace("\0", "-");

		//Checks if file for initial database values exists, then imports records.
		if(file.exists())
		{
			FileReader fr = new FileReader("packages.txt");
			Scanner inFile = new Scanner(fr);
			while (inFile.hasNextLine())
			{
				// Grabs next line from file, breaks the long string into an array.
				String line = inFile.nextLine();
				String[] words = line.split(" ");

				/* Basic validation for file correctness, in case each line is separated by an empty line.
				*/
				if (words.length == 6)
				{
					// Assigns values to variables to import.
					trackingNo = words[0];
					type = words[1];
					spec = words[2];
					mClass = words[3];
					weight = Float.parseFloat(words[4]);
					volume = Integer.parseInt(words[5]);
					Package package1 = new Package(trackingNo, type, spec, mClass, weight, volume);
					list1.add(package1);
				}
			}
			inFile.close();
		}

		do // Shows menu and requests user input until 6 is entered.
		{
			menu();
			do // Asks for user's input until input is valid.
			{
				System.out.print("Value must be between 1 - 6: ");
				choice = getInt();
			}while (!(0 < choice && choice < 7));

			switch(choice)
			{
				case 1:
				// Uses iterator it print Packages.
					it = list1.listIterator();
					if (it.hasNext())
					{
						printHeader();
						while (it.hasNext())
						{
							Package tempPackage = it.next();
							tempPackage.showPackage();
						}

						System.out.println(dashes);
					}
					else
						System.out.println("No packages to display.");
					break;

				case 2:
				/* Add package to database, firsts requests all Package information from user, then creates a new Package, then adds it to list1 */

					// Asks for tracking number verifying string has valid length.
					do
					{
						System.out.print("Enter tracking number: ");
						trackingNo = sc.next();
					} while (trackingNo.length() != 5);

					// Asks user for type from menu until valid input is received.
					do
					{
						typeMenu();
						choice = getInt();
					} while (!(0 < choice && choice < 10));
					type = types[choice-1];

					// Asks user for specification from menu until valid input is received.
					do
					{
						specMenu();
						choice = getInt();
					} while (!(0 < choice && choice < 6));
					spec = specification[choice-1];

					// Asks user for mailing class from menu until valid input is received.
					do
					{
						mailingClassMenu();
						choice = getInt();
					} while (!(0 < choice && choice < 6));
					mClass = mailingClass[choice-1];

					System.out.print("Enter package's weight: ");
					weight = getFloat();
					System.out.print("Enter package's volume: ");
					volume = getInt();

					Package package1 = new Package(trackingNo, type, spec, mClass, weight, volume);
					list1.add(package1);
					break;

				case 3:
				/* Remove package from database and echoes number removed.
				*/
					System.out.print("Enter tracking number to be removed: ");
					trackingNo = sc.next();

					int numRemoved = 0;

					for (int i = 0; i < list1.size(); i++)
						if (trackingNo.equals(list1.get(i).getTrackingNo()))
						{
							numRemoved++;
							System.out.println("Removing package.");
							list1.remove(i);
						}

					System.out.println(numRemoved+" pacakages removed.");
					break;

				case 4:
				// Search for a package by tracking number, then display.
					System.out.print("Enter tracking number to be searched: ");
					trackingNo = sc.next();

					// Only checks list1 if tracking number is valid.
					if (trackingNo.length() == 5)
					{
						/* Adds all packages which match the tracking number are added to list2, as there is no check for duplicates within the list.
						*/
						for (Package tempPackage : list1)
							if (trackingNo.equals(tempPackage.getTrackingNo()))
								list2.add(tempPackage);

						// Same as case 1 above, prints all values in list2.
						it = list2.listIterator();
						if (it.hasNext())
						{
							printHeader();
							while (it.hasNext())
							{
								Package tempPackage = it.next();
								tempPackage.showPackage();
							}
							System.out.println(dashes);
							list2.clear(); // Resets list2 for future use.
						}
						else // No packages matching tracking number found.
							System.out.println("No packages match this tracking number.");
					}
					else // User input not correct length.
						System.out.println("Tracking number input invalid.");
					break;

				case 5:
				// Prints all packages within a weight range.
					System.out.print("Enter first weight boundary: ");
					weight = getFloat();
					System.out.print("Enter second weight boundary: ");
					weight2 = getFloat();

					// Forces weight2 to be upper-bound and weight to be lower-bound.
					if (weight > weight2)
					{
						tempWeight = weight;
						weight = weight2;
						weight2 = tempWeight;
					}

					// Adds all Packages within bounds to list2.
					for (int i = 0; i < list1.size(); i++)
					{
						tempWeight = list1.get(i).getWeightInOz();
						if (weight <= tempWeight && tempWeight <= weight2)
							list2.add(list1.get(i));
					}

					// Displays all Packages in list2.
					it = list2.listIterator();
					if (it.hasNext())
					{
						printHeader();
						while (it.hasNext())
						{
							Package tempPackage = it.next();
							tempPackage.showPackage();
						}
						System.out.println(dashes);
						list2.clear(); // Resets list2 for later use.
					}
					else // Output in case no Packages are within specified range.
						System.out.println("No packages in the specified range.");
					break;

				default: // 6 - Exit program, stops outer loop
					done = true;
					break;
			}
		} while (!done);

		// Write list1 to file.
		PrintWriter outFile = new PrintWriter("packages.txt");
		for(Package tempPackage : list1)
			tempPackage.showPackage(outFile);
		outFile.close();
	}

	/**
		Prints menu for user to navigate program.
	*/
	public static void menu()
	{
		System.out.println("\nAvailable options:");
		System.out.println("1. Show all packages");
		System.out.println("2. Add package to database");
		System.out.println("3. Remove package from database");
		System.out.println("4. Search for a package by tracking number.");
		System.out.println("5. Show a list of packages within a weight range");
		System.out.println("6. Exit the program\n");
	}

	/**
		Prints menu for user to choose a type.
	*/
	public static void typeMenu()
	{
		System.out.println("\nType of package:");
		System.out.println("1. Postcard");
		System.out.println("2. Letter");
		System.out.println("3. Envelope");
		System.out.println("4. Packet");
		System.out.println("5. Box");
		System.out.println("6. Crate");
		System.out.println("7. Drum");
		System.out.println("8. Roll");
		System.out.println("9. Tube");
	}

	/**
		Prints menu for user to choose a specification.
	*/
	public static void specMenu()
	{
		System.out.println("\nSpecification of package:");
		System.out.println("1. Fragile");
		System.out.println("2. Books");
		System.out.println("3. Catalogs");
		System.out.println("4. Do-Not-bend");
		System.out.println("5. N/A");
	}

	/**
		Prints menu for user to choose a mailing class.
	*/
	public static void mailingClassMenu()
	{
		System.out.println("\nPackage's mailing class:");
		System.out.println("1. First-Class");
		System.out.println("2. Priority");
		System.out.println("3. Retail");
		System.out.println("4. Ground");
		System.out.println("5. Metro");
	}

	/**
		Prints header for console output.
	*/
	public static void printHeader()
	{
		String dashes = new String(new char[65]).replace("\0", "-");
		System.out.println(dashes);
		System.out.println("|TRACKING #|    TYPE|SPECIFICATION|      CLASS|   WEIGHT| VOLUME|");
		System.out.println(dashes);
	}

	/**
		Validates user's input to int.
		@return Integer received from user.
	*/
	public static int getInt()
	{
		int integer;
		Scanner sc = new Scanner(System.in);

		while (!sc.hasNextInt())
		{
			System.out.println("Input not an integer.");
			sc.next();
		}
		integer = sc.nextInt();
		return integer;
	}

	/**
		Validates user's input to float.
		@return Float received from user.
	*/
	public static float getFloat()
	{
		float decimal;
		Scanner sc = new Scanner(System.in);

		while (!sc.hasNextFloat())
		{
			System.out.println("Input not an decimal number.");
			sc.next();
		}
		decimal = sc.nextFloat();
		return decimal;
	}
}