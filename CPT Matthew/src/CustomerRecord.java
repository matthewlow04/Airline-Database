
public class CustomerRecord {
	private String firstName, lastName, street, city, postalCode, telephone;
	private int age;
	private double income;
	
	public CustomerRecord(String firstName,String lastName,String street,String city,String postalCode,String telephone,int age,double income) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.postalCode = postalCode;
		this.telephone = telephone;
		this.age = age;
		this.income = income;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getStreet() {
		return street;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public int getAge() {
		return age;
	}
	
	public double getIncome() {
		return income;
	}

}
