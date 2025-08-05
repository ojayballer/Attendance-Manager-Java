import javax.management.relation.Role;

public class Manager extends Employee {

    Manager(String name, int id, double salary,double x) {
        super(name, id, salary,x);
    }

    @Override
     public double CalculateBonus(){
         return (10/100)*getSalary();
     }
     @Override
     public  void Displaydetails() {
         super.Displaydetails();
         System.out.println("Role : Manager");
     }
     }