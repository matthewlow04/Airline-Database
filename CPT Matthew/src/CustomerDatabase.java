import java.util.*;
import javax.swing.*;
import java.io.*;
public class CustomerDatabase {
	
	//create array list
	ArrayList <CustomerRecord> customerArray = new ArrayList <CustomerRecord>();
	
	
	public CustomerDatabase() {
		//read the file and make add customers of customer record type to the array list
		try {
			BufferedReader br = new BufferedReader(new FileReader("PeopleInfo.txt"));
			String line = br.readLine();
			while (line!=null) {
				StringTokenizer st = new StringTokenizer(line,",");
				customerArray.add(new CustomerRecord(st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken(),
													st.nextToken(),st.nextToken(),Integer.parseInt(st.nextToken()),
													Double.parseDouble(st.nextToken())));
				line = br.readLine();	
			}
		}
		catch(IOException e) { 
		}
	}
	
	//display method which puts the array into a textarea with the use of a for loop
	public JTextArea displayInfo() {
		JTextArea text = new JTextArea();
		text.append("First Name \t Last Name \t Street \t\t City \t Postal Code \t Telephone \t Age \t Income\t \n\n");
		for(int i = 0; i<customerArray.size(); i++) {
			text.append(customerArray.get(i).getFirstName()+"\t"+ customerArray.get(i).getLastName()+"\t"+customerArray.get(i).getStreet()+"\t\t"
						+ customerArray.get(i).getCity()+"\t"+ customerArray.get(i).getPostalCode()+"\t"+customerArray.get(i).getTelephone()+"\t"
						+customerArray.get(i).getAge()+"\t"+customerArray.get(i).getIncome()+"\n");
		}
		return text;

	}

	//add customer method which receives the different attributes of the customer as parameters 
	public void addCustomer(String firstName, String lastName, String street, String city, String postalCode, String telephone, int age, double income) {
		
		//use the customer index method to check if this person already exists
		int index = customerIndex(firstName,lastName);
		if(index != -1) {
			JOptionPane.showMessageDialog(null, "Customer already exists", "Error", 0);
		}
		//if they dont exist, add the person
		else {

			int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you would like to add '"+firstName+" "+lastName+"' to the database", "Confirm", JOptionPane.YES_NO_OPTION);
			
			if (confirm == 0) {
				int insertIndex = customerArray.size();//by default it adds it to the end
				
				//looks for the spot to insert the person into by comparing last names
				for(int i = 0; i<customerArray.size(); i++) {
					if(lastName.compareTo(customerArray.get(i).getLastName())<0) {
						insertIndex = i;
						break;
					}
				}
				
				//makes new customerrecord
				CustomerRecord cr = new CustomerRecord(firstName, lastName, street, city, postalCode, telephone, age, income);
				customerArray.add(insertIndex,cr);
				JOptionPane.showMessageDialog(null, firstName+" "+lastName+" was added to the database.");
			}
			//if they say no to confirm message, the person is not added
			else 
				JOptionPane.showMessageDialog(null, firstName+" "+lastName+" was not added to the database.");
			
		}
		
	}

	//method which deletes customer by receiving the customer name as parameters
	public void deleteCustomer(String firstName, String lastName) {
		
		//oppositte as add customer, check index then if they do exist, continue otherwise give error message
		int index = customerIndex(firstName,lastName);
		if(index == -1) {
			JOptionPane.showMessageDialog(null, "Customer not Found", "Error", 0);
		}
		else {
			//confirm if you would like to delete
			int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete '"+firstName+" "+lastName+"' from the database", "Confirm", JOptionPane.YES_NO_OPTION);
			if (confirm == 0) {
				JOptionPane.showMessageDialog(null, "'"+firstName+" "+lastName+"' was deleted from the database");
				customerArray.remove(index);
			}
			
			else {
				JOptionPane.showMessageDialog(null, "'"+firstName+" "+lastName+"' was not deleted from the database.");
				
			}
				
			
		}
		
	}
	
	//search customer record, same index use as delete custoemr record
	public void searchCustomer(String firstName, String lastName) {
		int index = customerIndex(firstName,lastName);
		
		if(index == -1) {
			JOptionPane.showMessageDialog(null, "Customer not Found", "Error", 0);
		}
		//add the customer to a text area which is displayed in a message dialog to show attributes
		else {
			JTextArea txt = new JTextArea();
			txt.append("Street: "+customerArray.get(index).getStreet()+"\n");
			txt.append("City: "+customerArray.get(index).getCity()+"\n");
			txt.append("Postal Code: "+customerArray.get(index).getPostalCode()+"\n");
			txt.append("Telephone: "+customerArray.get(index).getTelephone()+"\n");
			txt.append("Age: "+customerArray.get(index).getAge()+"\n");
			txt.append("Income: "+customerArray.get(index).getIncome()+"\n");
			
			JOptionPane.showMessageDialog(null, txt, firstName+" "+lastName, JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	//sort array method which returns the textarea to later be displayed, also takes an int to know how to sort it
	public JTextArea sortArray(int sortType) {
		//copy the customer array so that when you sort this one, the initial one is not lost and you don't have to re-sort by last name
		ArrayList<CustomerRecord> tempArray= new ArrayList<CustomerRecord>(customerArray);
		
		CustomerRecord temp;
		
		//if sort by age, sorts from greatest to least
		if (sortType == 0) {
			for(int i = 1; i<tempArray.size(); i++) { //starts at one because 0 is part of the sorted array 
				temp = tempArray.get(i); //stores the current sorting number into a temp
				int j = i-1;
				while (j>=0 && temp.getAge() > tempArray.get(j).getAge()) {
					tempArray.set(j+1, tempArray.get(j));
					j--;
				}
				tempArray.set(j+1, temp);
			}
		}
		//if sort by income, sorts from greatest to least

		else {
			for(int i = 1; i<tempArray.size(); i++) { //starts at one because 0 is part of the sorted array 
				temp = tempArray.get(i); //stores the current sorting number into a temp
				int j = i-1;
				while (j>=0 && temp.getIncome() > tempArray.get(j).getIncome()) {
					tempArray.set(j+1, tempArray.get(j));
					j--;
				}
				tempArray.set(j+1, temp);
			}
		}
		
		//adds the sorted area to text area using a for loop which is then returned 
		JTextArea text = new JTextArea();
		text.append("First Name\tLastName\tStreet\tCity\tPostal Code\tTelephone\t Age\t Income\t\n\n");
		for(int i = 0; i<tempArray.size(); i++) {
			text.append(tempArray.get(i).getFirstName()+"\t"+ tempArray.get(i).getLastName()+"\t"+tempArray.get(i).getStreet()+"\t"
						+ tempArray.get(i).getCity()+"\t"+ tempArray.get(i).getPostalCode()+"\t"+tempArray.get(i).getTelephone()+"\t"
						+tempArray.get(i).getAge()+"\t"+tempArray.get(i).getIncome()+"\n");
		}
		
		return text;
		
		
	}
	//method to save the changes to the array to the textdocument
	public void exitProgram() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("PeopleInfo.txt"));
			for(int i = 0; i<customerArray.size(); i++) {
				pw.println(customerArray.get(i).getFirstName()+","+customerArray.get(i).getLastName()+","+customerArray.get(i).getStreet()+","+customerArray.get(i).getCity()
						+","+customerArray.get(i).getPostalCode()+","+customerArray.get(i).getTelephone()+","+customerArray.get(i).getAge()+","+customerArray.get(i).getIncome());
			}
			pw.close();
		} 
		
		catch (IOException e) {
			
		}
	}
	
	
	public int customerIndex(String firstName, String lastName) { //method to check if this customer exists, useful in add customer, search customer, and delete customer methods
		int index = -1;
		for(int i = 0; i<customerArray.size(); i++) {
			if(customerArray.get(i).getFirstName().equalsIgnoreCase(firstName) && customerArray.get(i).getLastName().equalsIgnoreCase(lastName)) {
				index = i;
				break;
			}
		}
		
		return index;
	}
	
	

}
