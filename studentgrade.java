import java.util.Scanner;
class StudentGradeSystem {
    void disp() {
        String name;
        int per;
        short Total;
        long rollno;
        byte python,java,data_structure;
        Scanner Sc = new Scanner(System.in);
        System.out.println("Enter your Name : ");
        name = Sc.nextLine();
        System.out.println("Enter your Roll no. : ");
        rollno = Sc.nextLong();
        System.out.println("Enter your Python Marks. : ");
        python = Sc.nextByte();
        
        if (python>100) {
            System.out.println("Invalid Marks");
        }
        else {
        System.out.println("Enter your JAVA Marks. : ");
        }
        java = Sc.nextByte();
        if (java>100) {
            System.out.println("Invalid Marks");
        }
        else {
        System.out.println("Enter your Data Structure Marks. : ");
        }
        data_structure = Sc.nextByte();
        if (data_structure>100) {
            System.out.println("Invalid Marks");
        }
        
        
        Total = (short)(python+java+data_structure);
        
        per = Total/3;
        
        if (per>90)
        {
            System.out.println("Gradse A, Percentage : " + per);
        }
        else if (per>80)
        {
            System.out.println("Gradse B, Percentage : " + per);
        }
        else if (per>70)
        {
            System.out.println("Gradse C, Percentage : " + per);
        }
        else if (per>60)
        {
            System.out.println("Gradse D, Percentage : " + per);
        }
        else 
        {
            System.out.println("Failed in Exam. , Percentage : " + per);
        }
        
    }
    public static void main(String args[])
    {
        StudentGradeSystem SGS = new StudentGradeSystem();
        SGS.disp();
    }
}