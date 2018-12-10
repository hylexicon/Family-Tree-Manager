import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FamilyMember{
	private String firstName;
	private String surname;
	private String sex;
	private boolean alive;
	private Calendar birth;
	private Calendar death;
	private FamilyMember mother;
	private FamilyMember father;
	private ArrayList<FamilyMember> children;
	
	public FamilyMember(String first, String last, String s, Calendar b) {
		firstName = first;
		surname = last;
		sex = s;
		alive = true;
		birth = b;
		death = null;
		
		mother = null;
		father = null;
		
		children = new ArrayList<FamilyMember>();
	}
	
	public FamilyMember(String first, String last, String s, Calendar b, Calendar d) {
		firstName = first;
		surname = last;
		sex = s;
		alive = false;
		birth = b;
		death = d;
		
		mother = null;
		father = null;
		
		children = new ArrayList<FamilyMember>();
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getFullName() {
		return firstName + " " + surname;
	}
	
	public String getSex() {
		return sex;
	}
	
	public boolean alive() {
		return alive;
	}
	
	public Calendar getBirth() {
		return birth;
	}
	
	public int getBirthDay() {
		return birth.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getBirthMonth() {
		return birth.get(Calendar.MONTH) + 1;
	}
	
	public int getBirthYear() {
		return birth.get(Calendar.YEAR);
	}
	
	public Calendar getDeath() {
		return death;
	}
	
	public int getDeathDay() {
		return death.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getDeathMonth() {
		return death.get(Calendar.MONTH) + 1;
	}
	
	public int getDeathYear() {
		return death.get(Calendar.YEAR);
	}
	
	public String getDeathDayString() {
		if(alive)
			return "N/A";
		
		return Integer.toString(death.get(Calendar.DAY_OF_MONTH));
	}
	
	public String getDeathMonthString() {
		if(alive)
			return "N/A";
		
		return Integer.toString(death.get(Calendar.MONTH));
	}
	
	public String getDeathYearString() {
		if(alive)
			return "N/A";
		
		return Integer.toString(death.get(Calendar.YEAR));
	}
	
	public FamilyMember getMother() {
		return mother;
	}
	
	public FamilyMember getFather() {
		return father;
	}
	
	public ArrayList<FamilyMember> getChildren() {
		return children;
	}
	
	public void addMother(FamilyMember member) {
		mother = member;
	}
	
	public void addFather(FamilyMember member) {
		father = member;
	}
	
	public void addChild(FamilyMember member) {
		children.add(member);
	}
	
	public void removeMother() {
		mother = null;
	}
	
	public void removeFather() {
		father = null;
	}
	
	public void removeChild(FamilyMember member) {
		for(int i = 0; i < children.size(); i++)
			if(children.get(i).equals(member))
				children.remove(i);
	}
	
	public int getAge() {
		int age;
		Calendar current = new GregorianCalendar();
		
		System.out.println(current.get(Calendar.YEAR));
		if(death == null) {
			age = current.get(Calendar.YEAR) - getBirthYear();
			
			if(current.get(Calendar.MONTH) < getBirthMonth() || (current.get(Calendar.MONTH) == getBirthMonth() && current.get(Calendar.DAY_OF_MONTH) < getBirthDay()))
				age--;
		}
		
		else {
			age = death.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
			
			if(getDeathMonth() < getBirthMonth() || (getDeathMonth() == getBirthMonth() && getDeathDay() < getBirthDay()))
				age--;
		}
		
		return age;
	}
}
