package Model;

public class Guest extends Person {
	private int discount;
	
	public Guest(int id, String name, String email, int discount) { 
		super(id, name, email);
		this.discount = discount;
	}
	
	public Guest() { 
		super();
	}
	
	public void setDiscount(int discount) {this.discount = discount;}
	public int getDiscount() {return this.discount;}
	
	@Override
	public void print() { 
		System.out.println("   ID    :" + this.id);
		System.out.println("   Name  :" + this.name);
		System.out.println("   Email :" + this.email);
		System.out.println("   Discount :" + this.discount + "%");
	}
	public void print(String label) { 
		System.out.println("  [" + label + "]");
		this.print();
	}
	
	public String toCsv() { 
		return this.id + "," + this.name + "," + this.email + "," + this.discount;
	}
}
