package ie.lyit.testers;

import ie.lyit.serialize.EmployeeSerializer;

public class EmployeeSerializerTester {

	public static void main(String[] args) {
		EmployeeSerializer bookSerializer = new EmployeeSerializer();
	
		// Add two records to the ArrayList
		bookSerializer.add();
		bookSerializer.add();
				
		// Write the ArrayList to File
		bookSerializer.serializeEmployees();

		
		// Read the ArrayList from the File
		bookSerializer.deserializeEmployees();
		
		// List all the books in the ArrayList
		bookSerializer.list();
	}
}