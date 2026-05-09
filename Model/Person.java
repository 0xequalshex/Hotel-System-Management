package Model;

public abstract class Person {
	protected int id;
	protected String name;
	protected String email;
	
	public Person(int id, String name, String email) { 
		this.id = id;
		this.name = name;
		this.email = email;	
	}

	public Person() {}
	
	public void setId(int id) {this.id = id;}
	public void setName(String name) {this.name = name;}
	public void setEmail(String email) {this.email = email;}
	
	public int getId() {return this.id;}
	public String getName() {return this.name;}
	public String getEmail() {return this.email;}
	
	public abstract void print();
}
