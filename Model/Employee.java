package enums;
import enums.JobRole;
public class Employee {
	private double  salary;
    private JobRole job;

    public Employee(int id, String name, String email, double salary, JobRole job) {
        super(id, name, email);
        this.salary = salary;
        this.job    = job;
    }

    public Employee() {
        super();
    }

    public double  getSalary()              { 
    	return this.salary; 
    }
    public JobRole getJob()                 {
    	return this.job;    
    }
    
    public void    setSalary(double salary) { 
    	this.salary = salary; 
    }
    public void    setJob(JobRole job)      {
    	this.job = job;    
    }

    public void performDuties() {
        System.out.println("  " + this.name + " [" + this.job.getLabel() + "]: " + this.job.getDuty());
    }

    @Override
    public void print() {
        System.out.println("  ID     : " + this.id);
        System.out.println("  Name   : " + this.name);
        System.out.println("  Email  : " + this.email);
        System.out.println("  Salary : $" + this.salary);
        System.out.println("  Job    : " + this.job.getLabel());
    }

    public String toCsv() {
        return this.id + "," + this.name + "," + this.email + "," + this.salary + "," + this.job.name();
    }
}

