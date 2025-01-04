package ie.lyit.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import ie.lyit.hotel.*;

public class EmployeeSerializer {
    private ArrayList<Employee> employees;
	
	private final String FILENAME = "employee_list.bin";	
	private File employeesFile;

	// Default Constructor
	public EmployeeSerializer(){
		employees = new ArrayList<Employee>();
		employeesFile = new File(FILENAME);		
	}	
	public void add() {
		Employee employee = new Employee();
		employee.read();
		employees.add(employee);
	}
	
	public void list(){
		String employeesToList="";

		// for every employee object in employees
		for(Employee tmpEmployee : employees) {
			employeesToList+=tmpEmployee;
			employeesToList+="\n";
		}
		
		// Display the list in a messageDialog
	    JOptionPane.showMessageDialog(null, employeesToList);	
	}
	
	public Employee view(){
		String employeeToViewAsString = JOptionPane.showInputDialog("Number of employee:");
	    if (employeeToViewAsString == null || employeeToViewAsString.trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Invalid input. Please provide a valid employee number.");
	        return null;
	    }

	    try {
	        int employeeToView = Integer.parseInt(employeeToViewAsString);

	        for (Employee tmpEmployee : employees) {
	            if (tmpEmployee.getNumber() == employeeToView) {
	                JOptionPane.showMessageDialog(null, tmpEmployee);
	                return tmpEmployee;
	            }
	        }
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Invalid number format. Please enter a numeric value.");
	    }

	    return null;
		
	}
	
	public void delete(){	
		Employee tmpEmployee = view();

		if(tmpEmployee != null)
			employees.remove(tmpEmployee);		
	}

	
	public void edit(){	
		// Call view() to find, display, & return the book to edit
		Employee tmpEmployee = view();

		// If the book != null, i.e. it was found then...
	    if(tmpEmployee != null){
		   // get it's index
		   int index=employees.indexOf(tmpEmployee);
		   // read in a new book and...
		   tmpEmployee.read();
		   // reset the object in books
		   employees.set(index, tmpEmployee);
	    }
	}
	
	public void serializeEmployees(){
		ObjectOutputStream os=null;
		try {
			// Serialize the ArrayList...
			FileOutputStream fileStream = new FileOutputStream(employeesFile);
		
			os = new ObjectOutputStream(fileStream);
				
			os.writeObject(employees);
		}
		catch(FileNotFoundException fNFE){
			System.out.println("Cannot create file to store employees.");
		}
		catch(IOException ioE){
			 System.out.println("Cannot write to " + FILENAME + ".");
		}
		finally {
			try {
				os.close();
			}
			catch(IOException ioE){
				System.out.println(ioE.getMessage());
			}
		}
	}
	
	public void deserializeEmployees() {
	    ObjectInputStream is = null;
	    try {
	        // Deserialize the ArrayList...
	        FileInputStream fileStream = new FileInputStream(FILENAME);
	        is = new ObjectInputStream(fileStream);
	        employees = (ArrayList<Employee>) is.readObject();
	    } catch (FileNotFoundException fNFE) {
	        System.out.println("File not found. Starting with an empty employee list.");
	        employees = new ArrayList<>();
	    } catch (IOException ioE) {
	        System.out.println("Cannot read from " + FILENAME + ".");
	    } catch (ClassNotFoundException cnfE) {
	        System.out.println("Class not found: " + cnfE.getMessage());
	    } finally {
	        if (is != null) {
	            try {
	                is.close();
	            } catch (IOException ioE) {
	                System.out.println(ioE.getMessage());
	            }
	        }
	    }
	}

}