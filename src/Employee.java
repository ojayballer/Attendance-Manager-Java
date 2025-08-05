import java.util.Date;

public class Employee{
  private  String name;
   private int id;
   private double salary;
   double x;
   boolean[][] attendance;
   static String companyname;
   Date date;

   Employee(String name,int id,double salary,double x){
       date=new Date();
       System.out.println("today is "+date);
       this.name=name;
       this.x=x;
       this.id=id;
       this.salary=salary;
       System.out.println("FUCK YOU!!!");
       boolean[][] attendance =new boolean[12][31];
       System.out.println(this.x);

   }


   public String getName(){
       return name;
   }
    public void setName(String name){
        this.name=name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
       if(salary>=0)
         this.salary = salary;
       else
           System.out.println("invalid salary!");
    }
    public  double CalculateBonus(){
       return (0.5)*this.salary;
    }

    public boolean[][] getAttendance() {
        return attendance;
    }

    public static void PrintCompanyInfo(){
        System.out.println("Company Name : "+companyname);

   }
   public  void markAttendance(int month,int day,boolean present){
 if(month>=0&&month<12&&day>=0&&day<31)
     attendance[month][day]=present;

   }
   public  void Displaydetails() {
       System.out.println("Name : " + name + " ID :" + id + " SALARY: " + salary);

   }
    }