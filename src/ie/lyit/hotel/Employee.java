
package ie.lyit.hotel;

import java.io.Serializable;
import java.text.DecimalFormat;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//INHERITANCE - Employee IS-A Person, and CAN-DO Payable methods
public class Employee extends Person implements Payable, Serializable {
    private Date dob;		// Employee has name&phoneNo from Person		
    private Date startDate;	// AND dob, startdate,salary, & number
    private double salary;	
    private int number;

	private static int nextNumber=10000;	

	public final double MAX_SALARY = 150000.00;		
	
	// Default Constructor
	// Called when object is created like this ==> 
	//    Employee e1 = new Employee();	
	public Employee() {
		super();// NOTE:Don't have to put this in,  it will be called anyway
		dob=new Date();
		startDate=new Date();
		salary=0.0;
		// Set number to static nextNumber before incrementing nextNumber		
		number=nextNumber++;
	}

	// Overloaded Initialization Constructor
	// Called when object is created like this ==>
	//	  Name name = Name("Mr","Joe","Doe");
	//    Employee e2 = new Employee(name,"087 1234567",
    //								 new Date(25,12,1970),
	//                               new Date(10,11,2009),
	//                               55000);
	public Employee(Name name, String phoneNumber, 
			        Date dob, Date startDate,
			        double salary) {
		// Call super class constructor - Passing parameters required by Person ONLY!		
		super(name,phoneNumber);
		// And then initialise Employees own instance variables
		this.dob=dob; // Set instance variable to parameter
	    this.startDate=startDate;
	    this.salary=salary;
	    // Set number to static nextNumber before incrementing nextNumber
	    number=nextNumber++;
	}	
	
	// OVERRIDING the Person toString() method
	// Calling Persons toString() method, and adding additional bits
	@Override
	public String toString() {
		DecimalFormat df=new DecimalFormat("#.00");

		return number + " " + super.toString() + "\t$" + df.format(salary);	
	}

	// equals() method
	// ==> Called when comparing one Employee with another
	//     if(e1.equals(e2))
	// Because Employee has a unique number then it is 
	// sufficient to compare the number fiel;
	@Override
	public boolean equals(Object obj) {
		Employee eObj;
		if(obj instanceof Employee)
			eObj = (Employee)obj;
		else 
		   return false;

		return (this.number==eObj.number);		
	}	

	   // get() methods
		public Date getStartDate(){
			return startDate;
		}	
		public double getSalary(){
			return salary;
		}
		public int getNumber(){
			return number;
		}	

	   // set() methods
		// You shouldn't be able to setNumber() as it is unique, 
		// so don't provide a setNumber() method
		public void setStartDate(Date startDate){
			this.startDate=startDate;
		}
		public void setSalary(int salary){
			this.salary=salary;
		}
	
	@Override
	public double calculatePay(double taxPercentage) {
		// calculate and return the wage as salary/12 less taxPercentage
		double pay=salary/12;
		pay -= (pay * (taxPercentage/100));
		return pay;
	}

	@Override
	public double incrementSalary(double incrementAmount) {
		// add incrementAmount to, and return the new salary
		// salary should not go over a maximum salary of �150,000
		salary += incrementAmount;
			
		if(salary > MAX_SALARY)
			salary = MAX_SALARY;
			
		return salary;
	}

	public void read() {
        JTextField txtName = new JTextField();
        txtName.setText(this.getName().toString());
        txtName.requestFocus();
        JTextField txtPhoneNo = new JTextField();
        JTextField txtDob = new JTextField();
        JTextField txtStartDate = new JTextField();
        JTextField txtSalary = new JTextField();
        // Remove txtNumber as it's auto-generated

        Object[] message = {
                "Name:", txtName,
                "Phone Number:", txtPhoneNo,
                "Date of Birth:", txtDob,
                "Start Date:", txtStartDate,
                "Salary:", txtSalary,
        };

        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        int option = JOptionPane.showConfirmDialog(dialog, message, "Enter Employee Details", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String fullName = txtName.getText();
            String[] nameParts = fullName.split(" ", 3);
            if (nameParts.length == 3) {
                this.name = new Name(nameParts[0], nameParts[1], nameParts[2]);
            } else {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid full name.");
                return;
            }
            this.phoneNo = txtPhoneNo.getText();
            this.dob = Date.parse(txtDob.getText());
            this.startDate = Date.parse(txtStartDate.getText());
            try {
                this.salary = Double.parseDouble(txtSalary.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(dialog, "Invalid salary format.");
                return;
            }
        }
    }
}

	

