import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GUI implements ActionListener,WindowListener,KeyListener{
	private CustomerDatabase cd;
	private JFrame startFrame, mainFrame, fAdd, fSearch, fSort, fDisplay, fSortDisplay;
	//buttons
	private JPanel p1, p2, pAdd, pSearch, pSort, pDisplay, pSortDisplay;
	private JButton btnAdd, btnDelete, btnSearch, btnDisplay, btnSort, btnExit, btnStart, btnAge, btnIncome, btnBack;
	private JButton btnOk, btnClear, btnCancel;
	//current button integer tells the program what the last button that was pressed is so that the ok, clear, back, and cancel buttons can be used for multiple methods
	private int currentButton;
	//boolean as to whether or not the customer is valid (helps for error trapping)
	private boolean validCustomer;
	
	//add variables
	private JLabel[] lblAdd = new JLabel[8];
	private JTextField[] txtAdd = new JTextField[8];

	
	//search variables
	private JLabel[] lblSearch = new JLabel[2];
	private JTextField[] txtSearch = new JTextField[2];
	
	//fonts and colours 
	private Color clrSky = new Color(204,234,247);
	private Font textFont = new Font("Times New Roman", Font.BOLD, 22);
	private Font headerFont = new Font("Helvetica", Font.BOLD,40);
	private Font smallText = new Font("Times New Roman", Font.PLAIN, 12);
	
	//constructor which runs the start frame and has an object of customer database
	public GUI(){
		cd = new CustomerDatabase();	
		startFrame();
	
	}
	
	//first frame that runs when the method is opened
	public void startFrame() {
		startFrame = new JFrame();
		p1 = new JPanel();
		p1.setLayout(null);
		
		//properties
		startFrame.setContentPane(p1);
		startFrame.setResizable(false);
		startFrame.setVisible(true);
		startFrame.setBounds(100,100,750,700);
		startFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		startFrame.addWindowListener(this);
		p1.setBackground(clrSky);
		
		//adding labels and icons
		JLabel lblHeader = new JLabel("Welcome to Low Airlines");
		lblHeader.setFont(headerFont);
		lblHeader.setBounds(100,100,550,200);
		p1.add(lblHeader);
		
		Icon icon = new ImageIcon("planeflying.gif");
		JLabel lblPlane = new JLabel(icon);
		lblPlane.setBounds(100,300,300,300);
		p1.add(lblPlane);
		
		JLabel lblFooter = new JLabel("Click start to get started!");
		lblFooter.setFont(smallText);
		lblFooter.setBounds(100,600,300,100);
		p1.add(lblFooter);
		
		//add button
		btnStart = new JButton("Start");
		btnStart.setFont(textFont);
		btnStart.setBounds(500,300,200,200);
		btnStart.addActionListener(this);
		p1.add(btnStart);
		
	}
	
	//frame that is opened after the first frame
	public void mainFrame() {
		mainFrame = new JFrame();
		p2 = new JPanel();
		p2.setLayout(null);
		mainFrame.setContentPane(p2);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		mainFrame.setBounds(100,100,750,700);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(this);
		p2.setBackground(clrSky);
		
		//add labels,buttons, and headers
		JLabel lblHeader = new JLabel();
		lblHeader.setText("Low Airlines");	
		lblHeader.setFont(headerFont);
		lblHeader.setBounds(100,50,550,100);
		p2.add(lblHeader);
		
		JLabel lblHeader2 = new JLabel();
		lblHeader2.setText("Database");	
		lblHeader2.setFont(headerFont);
		lblHeader2.setBounds(100,150,550,100);
		p2.add(lblHeader2);
		
		Icon icon = new ImageIcon("airplane.png");
		JLabel lblPlane = new JLabel(icon);
		lblPlane.setBounds(400,50,250,200);
		p2.add(lblPlane);
		
		btnAdd = addButton("Add a Person", 100,300);
		btnDelete = addButton("Delete a Person",300,300);
		btnSearch = addButton("Search a Person",500,300);
		btnDisplay = addButton("Display Customers", 100,450);
		btnSort = addButton("Sort Customers", 300,450);
		btnExit = addButton("Exit", 500,450);

	}
	
	//add button method
	public JButton addButton(String msg, int x, int y) {
		JButton btn = new JButton();
			btn = new JButton(msg);
			btn.setBounds(x,y,150,100);
			btn.addActionListener(this);
			mainFrame.add(btn);
		return btn;
	}
	
	 //when add customer button is pressed
	public void addCustomer() {
		
		//set up panel and frame
		pAdd = new JPanel();
		pAdd.setLayout(null);
		fAdd = new JFrame("Enter the information");
		fAdd.setContentPane(pAdd);
		
		fAdd.setVisible(true);
		fAdd.setBounds(300, 300, 300, 400);
		fAdd.setResizable(false);
		
		//add buttons
		btnOk = new JButton("Ok");
		btnOk.setBounds(10,300,75,50);
		btnOk.addActionListener(this);
		fAdd.add(btnOk);
		
		btnClear = new JButton("Clear");
		btnClear.setBounds(110,300,75,50);
		btnClear.addActionListener(this);
		fAdd.add(btnClear);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(210,300,75,50);
		btnCancel.addActionListener(this);
		fAdd.add(btnCancel);
		
		//Add the different labels
		for(int i = 0; i<lblAdd.length;i++) {
			lblAdd[i] = new JLabel();
			lblAdd[i].setBounds(10, 30+30*i, 100, 25);
			
			pAdd.add(lblAdd[i]);
		}
	
		lblAdd[0].setText("First Name: ");
		lblAdd[1].setText("Last Name: ");
		lblAdd[2].setText("Street: ");
		lblAdd[3].setText("City: ");
		lblAdd[4].setText("Postal Code: ");
		lblAdd[5].setText("Telephone: ");
		lblAdd[6].setText("Age: ");
		lblAdd[7].setText("Income: ");
		
		//add textfields
		for(int i = 0; i<txtAdd.length;i++) {
			txtAdd[i] = new JTextField();
			txtAdd[i].setBounds(110, 30+30*i, 180, 25);
			txtAdd[i].addKeyListener(this);
			pAdd.add(txtAdd[i]);
		}
	}
	//when the ok button of add customer is pressed
	public void addCustomerOk() { 
		
		//the customer will be added unless an error occurs in which the boolean becomes false
		validCustomer = true;
		//check to see which textfields are blank
		for(int i = 0; i<txtAdd.length; i++) {
			if(txtAdd[i].getText().equals("")) {
				validCustomer = false;
				txtAdd[i].setBackground(Color.red);
				txtAdd[i].requestFocus();
			}
		}
		
		//if any single textbox is empty
		if(validCustomer == false)
			JOptionPane.showMessageDialog(null, "Fill in empty textboxes", "Something went wrong", JOptionPane.WARNING_MESSAGE);
		
		//initialize checker so that the program knows whether the income textfield or the age textfield causes the exception
		double checker = -1;
		
		try {
			
			if (Integer.parseInt(txtAdd[6].getText())<=0) { //if age is less than or equal to 0, invokes error also if it's a letter, goes to catch
				validCustomer = false;
				JOptionPane.showMessageDialog(null, "Enter a positive integer for age", "Something went wrong", JOptionPane.WARNING_MESSAGE);

				txtAdd[6].setText("");
				txtAdd[6].setBackground(Color.red);

			}
			//check for income errors now
			checker--;
			if (Integer.parseInt(txtAdd[7].getText())<0) { //if income is less than 0, invokes error also if it's a letter, goes to catch
				validCustomer = false;
				JOptionPane.showMessageDialog(null, "Enter a positive integer for income", "Something went wrong", JOptionPane.WARNING_MESSAGE);
				txtAdd[7].setText("");
				txtAdd[7].setBackground(Color.red);

			}
			
		}
		catch(NumberFormatException n) {
			if (checker == -1) { //if error comes from age
				validCustomer = false;
				JOptionPane.showMessageDialog(null, "Enter a positive integer for age", "Something went wrong", JOptionPane.WARNING_MESSAGE);
				txtAdd[6].setBackground(Color.red);
				txtAdd[6].setText("");
				txtAdd[6].requestFocus();
			}
			else { //if error comes from income
				validCustomer = false;
				JOptionPane.showMessageDialog(null, "Enter a positive number for income", "Something went wrong", JOptionPane.WARNING_MESSAGE);
				txtAdd[7].setBackground(Color.red);
				txtAdd[7].setText("");
				txtAdd[7].requestFocus();
			}
			
		}
		//if no errors have occured, a customer is added
		if (validCustomer){
			cd.addCustomer(txtAdd[0].getText(), txtAdd[1].getText(), txtAdd[2].getText(), txtAdd[3].getText(), txtAdd[4].getText(), txtAdd[5].getText(), Integer.parseInt(txtAdd[6].getText()), Double.parseDouble(txtAdd[7].getText()));
			
			//ask to try again
			int confirm = JOptionPane.showConfirmDialog(null,"Would you like to try again?","Try again", JOptionPane.YES_NO_OPTION);
			
			//if yes, clears the text so that they can add new customer
			if (confirm == 0) {

				for(int i = 0; i<txtAdd.length; i++) {
					txtAdd[i].setText("");
				}
				
			}
			
			else {
				fAdd.setVisible(false);
			}
			
		}
			
	}
	
	public void searchCustomer() { //for either search or delete functions
		
		//initialize panel and frame 
		pSearch = new JPanel();
		pSearch.setLayout(null);
		fSearch = new JFrame("Enter the information");
		fSearch.setContentPane(pSearch);
		
		fSearch.setVisible(true);
		fSearch.setBounds(300, 300, 300, 200);
		fSearch.setResizable(false);
		
		//create labels
		for(int i = 0; i<lblSearch.length;i++) {
			lblSearch[i] = new JLabel();
			lblSearch[i].setBounds(10, 30+30*i, 100, 25);
			
			pSearch.add(lblSearch[i]);
		}
		
		lblSearch[0].setText("First Name: ");
		lblSearch[1].setText("Last Name: ");
		
		//create textfields
		for(int i = 0; i<txtSearch.length;i++) {
			txtSearch[i] = new JTextField();
			txtSearch[i].setBounds(110, 30+30*i, 180, 25);
			txtSearch[i].addKeyListener(this);
			pSearch.add(txtSearch[i]);
		}
		//create buttons
		btnOk = new JButton("Ok");
		btnOk.setBounds(10,100,75,50);
		btnOk.addActionListener(this);
		fSearch.add(btnOk);
		
		btnClear = new JButton("Clear");
		btnClear.setBounds(110,100,75,50);
		btnClear.addActionListener(this);
		fSearch.add(btnClear);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(210,100,75,50);
		btnCancel.addActionListener(this);
		fSearch.add(btnCancel);
		
	}
	
	//if ok is pressed in the search method, pass a string to tell whether or not it will be used for searching or deleting a customer
	public void searchCustomerOk(String searchOrDelete) {
		
		//if no errors, look up the customer
		validCustomer = true;
		
		//find empty textfields
		for(int i = 0; i<txtSearch.length; i++) {
			if(txtSearch[i].getText().equals("")) {
				validCustomer = false;
				txtSearch[i].setBackground(Color.red);
				txtSearch[i].requestFocus();
			}
		}
		
		//if no empty textfields either search or delete the customer
		if(validCustomer) {
			if(searchOrDelete.equals("search")) 
				cd.searchCustomer(txtSearch[0].getText(), txtSearch[1].getText());
				//fSearch.setVisible(false);
				
			else
					cd.deleteCustomer(txtSearch[0].getText(), txtSearch[1].getText());
			
			//try again
			int confirm = JOptionPane.showConfirmDialog(null,"Would you like to try again?","Try again", JOptionPane.YES_NO_OPTION);
			
			if (confirm == 0) {
				for(int i = 0; i<txtSearch.length; i++) {
					txtSearch[i].setText("");
				}
			}
			
			else {
				fSearch.setVisible(false);
			}
					
		}
		//if valid customer != true, then error message
		else {
			JOptionPane.showMessageDialog(null, "Fill in empty textboxes", "Something went wrong", JOptionPane.WARNING_MESSAGE);
		}
		
		
	}
	public void displayCustomers(JTextArea text) {
		//scrollbar
		JScrollPane sp = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		
		//set up panel and frame
		pDisplay = new JPanel();
		pDisplay.setLayout(null);
		fDisplay = new JFrame("Customers");
		fDisplay.setContentPane(pDisplay);
		pDisplay.add(sp);

		fDisplay.setVisible(true);
		fDisplay.setBounds(100,100,700,700);
		fDisplay.setResizable(false);
		
		//set up textfield
		text.setBounds(150,150,500,500);
		
		text.setEditable(false);
		sp.setBounds(150,150,400,400);
		
		//add button back
		btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		btnBack.setBounds(250,600,200,50);
		pDisplay.add(btnBack);
		
		
	}
	//method to find which sorting method
	public void sortArray() {
		
		
		//panel and frame
		pSort = new JPanel();
		pSort.setLayout(null);
		fSort = new JFrame("Choose Variable to Sort By");
		fSort.setContentPane(pSort);
		
		fSort.setVisible(true);
		fSort.setBounds(300, 300, 300, 200);
		fSort.setResizable(false);
		
		//add buttons
		btnAge = new JButton("Age");
		btnAge.setBounds(50,50,100,100);
		btnAge.addActionListener(this);
		pSort.add(btnAge);
		
		btnIncome = new JButton("Income");
		btnIncome.setBounds(150,50,100,100);
		btnIncome.addActionListener(this);
		pSort.add(btnIncome);
		
		
		
		
	}
	
	//the method which displays the sorted array
	public void sortArrayDisplay(JTextArea text) {
		//scroll bar
		JScrollPane sp = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//panel and frame
		pSortDisplay = new JPanel();
		pSortDisplay.setLayout(null);
		fSortDisplay = new JFrame("Customers");
		fSortDisplay.setContentPane(pSortDisplay);
		pSortDisplay.add(sp);

		fSortDisplay.setVisible(true);
		fSortDisplay.setBounds(100,100,700,700);
		fSortDisplay.setResizable(false);
		
		//textfield 
		text.setBounds(150,150,500,500);
		text.setEditable(false);
		sp.setBounds(150,150,400,400);
		
		//add button
		btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		btnBack.setBounds(250,600,200,50);
		pSortDisplay.add(btnBack);
	}
	
	//action performed method
	public void actionPerformed(ActionEvent e) {
		
	
		if(e.getSource()==btnAdd) {
			currentButton = 1; //so that clear ok and cancel know which button it's using
			addCustomer();

		}
		else if(e.getSource()==btnBack) {
			if(currentButton == 4)
				fDisplay.setVisible(false);
			else
				fSortDisplay.setVisible(false);
				
		}
		else if(e.getSource()==btnDisplay) {
			//tell the action performed which button had been pressed before
			currentButton = 4;
			displayCustomers(cd.displayInfo());
		}
		else if(e.getSource()==btnSort) {

			//tell the action performed which button had been pressed before
			currentButton = 5;
			sortArray();
		}
		else if(e.getSource()==btnDelete) {
			//tell the action performed which button had been pressed before
			currentButton = 2;
			searchCustomer();
			
		}
		else if(e.getSource()==btnSearch) {
			currentButton = 3;
			searchCustomer();
		}
		
		else if(e.getSource()==btnOk) {
			//finds the button that had been pressed before and uses the corresponding ok  method
			if(currentButton == 1) 
				addCustomerOk();
			
			else if(currentButton == 2)
				searchCustomerOk("delete");

			else
				searchCustomerOk("search");
			
		}
		
		else if(e.getSource()==btnClear) {
			//clears the corresponding textfields
			if(currentButton == 1) {
				for(int i = 0; i<txtAdd.length; i++) {
					txtAdd[i].setText("");
				}
			}
			else {
				for(int i = 0; i<txtSearch.length; i++) {
					txtSearch[i].setText("");
				}
			}
			
		}
		else if(e.getSource()==btnCancel) {
			if(currentButton == 1) {
				for(int i = 0; i<txtAdd.length; i++) {
					txtAdd[i].setText("");
				}
				fAdd.setVisible(false);
			}
			
			else {
				for(int i = 0; i<txtSearch.length; i++) {
					txtSearch[i].setText("");
				}
				fSearch.setVisible(false);
			}
			
		}
		
		else if(e.getSource()==btnStart) {
			//when the start button is pressed, the mainframe is opened and the first frame is hidden
			startFrame.setVisible(false);
			mainFrame();
		}
		
		else if(e.getSource()==btnAge) {
			//tells the sort array  display method which method it would like to use (0 stands for age)
			sortArrayDisplay(cd.sortArray(0));
			
		}
		else if(e.getSource()==btnIncome) {
			//tells the sort array  display method which method it would like to use (1 stands for income)

			sortArrayDisplay(cd.sortArray(1));
		}
		// if the source doesnt come from any other buttons, it means the exit button was pressed
		else {
			
			int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(confirm == 0) {
				//call the exit program method which saves the array (sorted by last name) to the same text file
				cd.exitProgram();
				JOptionPane.showMessageDialog(null, "Thank you for using Low Airlines",null,JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			}
		}
		
	}
	
	//if window is attempted to be closed
	public void windowClosing(WindowEvent e) {
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(confirm == 0) {
			//call the exit program method which saves the array (sorted by last name) to the same text file

			cd.exitProgram();
			JOptionPane.showMessageDialog(null, "Thank you for using Low Airlines",null,JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}
	}
	
	public void windowClosed(WindowEvent e) {	
		
	}
	public void windowIconified(WindowEvent e) {
		
	}
	public void windowDeiconified(WindowEvent e) {
		
	}
	public void windowActivated(WindowEvent e) {
		
	}
	public void windowDeactivated(WindowEvent e) {	
		
	}
	public void windowOpened(WindowEvent e) {	
		
	}
	public void keyTyped(KeyEvent e) {
	
	}
	//to make the current textfield white (if there was an error it would've been red)
	public void keyPressed(KeyEvent e) {
		
		//if else statement to find which panel (add or search) to make white
		if(currentButton == 1) {
			for(int i=0;i<txtAdd.length;i++) {
				if(e.getSource()==txtAdd[i]) {
					txtAdd[i].setBackground(Color.white);
					break;
				}
			}	
		}
		else {
			for(int i=0;i<txtSearch.length;i++) {
				if(e.getSource()==txtSearch[i]) {
					txtSearch[i].setBackground(Color.white);
					break;
				}
			}	
		}
		
	}
	public void keyReleased(KeyEvent e) {
		
	}	
}
