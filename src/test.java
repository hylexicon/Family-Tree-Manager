import java.util.Calendar;
import java.util.GregorianCalendar;

public class test {
	public static void main(String[] args) {
		Family fam = new Family();
		
		Calendar c1 = new GregorianCalendar();
		c1.set(Calendar.YEAR, 1955);
		c1.set(Calendar.MONTH, 3);
		c1.set(Calendar.DAY_OF_MONTH, 25);
		
		FamilyMember m1 = new FamilyMember("Nate", "Dylan", "Male", c1);
		
		Calendar c2 = new GregorianCalendar();
		c2.set(Calendar.YEAR, 1955);
		c2.set(Calendar.MONTH, 3);
		c2.set(Calendar.DAY_OF_MONTH, 25);
		
		FamilyMember m2 = new FamilyMember("Jim", "Dylan", "Male", c2);
		
		Calendar c3 = new GregorianCalendar();
		c3.set(Calendar.YEAR, 1955);
		c3.set(Calendar.MONTH, 3);
		c3.set(Calendar.DAY_OF_MONTH, 25);
		
		FamilyMember m3 = new FamilyMember("Bob", "Dylan", "Male", c3);
		
		Calendar c4 = new GregorianCalendar();
		c4.set(Calendar.YEAR, 1955);
		c4.set(Calendar.MONTH, 3);
		c4.set(Calendar.DAY_OF_MONTH, 25);
		
		FamilyMember m4 = new FamilyMember("Amanda", "Dylan", "Female", c4);
		
		Calendar c5 = new GregorianCalendar();
		c5.set(Calendar.YEAR, 1955);
		c5.set(Calendar.MONTH, 3);
		c5.set(Calendar.DAY_OF_MONTH, 25);
		FamilyMember m5 = new FamilyMember("Branna", "Rushmore", "Female", c5);
		
		fam.addMember(m3);
		fam.addMember(m1);
		fam.addChild(m3, m1);
		
		FamilyMember m = m1;
		System.out.print(m.getFullName() + "\n" + m.getBirthDay() + "/" + m.getBirthMonth() + "/" + m.getBirthYear() + "\n - \n" + m.getDeathDayString() + "/" + m.getDeathMonthString() + "/" + m.getDeathYearString());
	}
}
