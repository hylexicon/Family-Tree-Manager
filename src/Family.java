import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

public class Family {
	private ArrayList<FamilyMember> members;
	
	public Family() {
		members = new ArrayList<FamilyMember>();
	}
	
	public void addMember(FamilyMember member) {
		members.add(member);
	}
	
	public void addMother(FamilyMember member, FamilyMember mother) {
		member.addMother(mother);
		
		mother.addChild(member);
		
		members.add(mother);
	}
	
	public void addFather(FamilyMember member, FamilyMember father) {
		member.addFather(father);
		
		father.addChild(member);
		
		members.add(father);
	}
	
	public void addChild(FamilyMember member, FamilyMember child) {
		member.addChild(child);
	
		if(member.getSex() == "Male")
			child.addFather(member);
		
		else
			child.addMother(member);
		
		members.add(child);
	}
	
	public int size() {
		return members.size();
	}
	
	public FamilyMember eldest() {
		Calendar oldest = members.get(0).getBirth();
		FamilyMember eldest = members.get(0);
		
		for(FamilyMember f: members) {
			if(f.getDeath() == null) {
				Calendar birth = f.getBirth();
				
				if(birth.compareTo(oldest) < 0) {
					oldest = birth;
					eldest = f;
				}
			}
		}
		
		return eldest;
	}
	
	public boolean search(FamilyMember f) {
		for(FamilyMember fam: members) {
			if(fam.equals(f))
				return true;
		}
		
		return false;
	}
	
	public ArrayList<FamilyMember> getMembers(){
		return members;
	}
	
	public LinkedList<FamilyMember> getLiving() {
		LinkedList<FamilyMember> alive = new LinkedList<FamilyMember>();
		
		for(FamilyMember f: members) {
			if(f.alive())
				alive.addFirst(f);
		}
		
		return alive;
	}
	
	public LinkedList<FamilyMember> getDeceased() {
		LinkedList<FamilyMember> dead = new LinkedList<FamilyMember>();
		
		for(FamilyMember f: members) {
			if(!f.alive())
				dead.addFirst(f);
		}
		
		return dead;
	}
	
	public LinkedList<FamilyMember> getAdults() {
		LinkedList<FamilyMember> adults = new LinkedList<FamilyMember>();
		
		for(FamilyMember f: getLiving())
			if(f.getAge() > 18)
				adults.addFirst(f);
		
		return adults;

	}
	
	public LinkedList<FamilyMember> getChildren() {
		LinkedList<FamilyMember> children = new LinkedList<FamilyMember>();
		
		for(FamilyMember f: getLiving())
			if(f.getAge() < 18)
				children.addFirst(f);
		
		return children;
	}
	
	public void delete(FamilyMember member) {
		FamilyMember mother = member.getMother();
		FamilyMember father = member.getFather();
		
		for(FamilyMember m: member.getChildren()) {
			if(member.getSex() == "Male")
				m.removeFather();
			
			else
				m.removeMother();
		}
		
		members.remove(member);
	}
	
	public String getRelationTo(FamilyMember from, FamilyMember to) {
		//get generations to common ancestor/descendant
		FamilyMember ancestor = commonAncestor(from, to);
		FamilyMember descendant = commonDescendant(from, to);
		FamilyMember common;
		
		String str = ""; //stores result string
		
		common = ancestor == null? descendant == null? null: descendant: ancestor;
		
		if(ancestor == from)
			common = descendant;
		
		if(descendant == from)
			common = ancestor;
		
		if(ancestor == null && descendant == null)
			return "No detected relation";
			
		//get generations from from to common
		Integer generations = generationsTo(0, from, common);
		
		if(generations == null)
			return "No detected relation";
		
		//generation of to in comparison to from
		Integer generationsToDest = generationsTo(generations, common, to);
		
		//direct bloodline
		if(generationsToDest == generations) {
			if(generations == 1)
				str = to.getSex() == "Male"? "father" : "mother";
			
			if(generations == 2)
				str = to.getSex() == "Male"? "grandfather" : "grandmother";
			
			if(generations > 2) {
				str = to.getSex() == "Male"? "great grandfather" : "great grandmother";
				
				for(int i = 0; i < generations - 3; i++)
					str = "great-" + str;
			}
			
			if(generations == 0)
				str = "self";
			
			if(generations == -1)
				str = to.getSex() == "Male"? "son" : "daughter";
			
			if(generations == -2)
				str = to.getSex() == "Male"? "grandson" : "granddaughter";
			
			if(generations < -2) {
				str = to.getSex() == "Male"? "great grandson" : "great granddaughter";
				
				for(int i = 0; i > generations + 3; i--)
					str = "great-" + str;
			}	
		}
		
		//derived relationships from descendants
		else if(generations < 0) {
			if(generations == -1) {
				if(generationsToDest == 0)
					str = "partner";
				
				if(generationsToDest == 1)
					str = to.getSex() == "Male"? "father-in-law" : "mother-in-law";
			}
			
			if(generations == -2 && generationsToDest == -1)
				str = to.getSex() == "Male"? "son-in-law" : "daughter-in-law";
		}
		
		//derived relationships from ancestors
		else if(generations > 0) {
			if(generations == 1 && generationsToDest == 0)
				str = to.getSex() == "Male"? "brother": "sister";
			
			if(generations == 1 && generationsToDest == -1)
				str = to.getSex() == "Male"? "nephew": "niece";
			
			if(generations == 1 && generationsToDest == -2)
				str = to.getSex() == "Male"? "grandnephew": "grandniece";
			
			if(generations == 1 && generationsToDest == -3)
				str = to.getSex() == "Male"? "great grandnephew": "great grandniece";
			
			if(generations == 1 && generationsToDest < -3) {
				str = to.getSex() == "Male"? "great grandnephew": "great grandniece";
				
				for(int i = 0; i > generationsToDest; i--)
					str = "great-" + str;
			}
			
			if(generations > 1) {
				if(generationsToDest == generations - 1) {
					if(generations == 2)
						str = to.getSex() == "Male"? "uncle": "aunt";
					
					if(generations == 3)
						str = to.getSex() == "Male"? "great uncle": "great aunt";
					
					if(generations >= 4) {
						str = to.getSex() == "Male"? "great grand uncle": "great grand aunt";
						
						for(int i = 0; i < generations - 4; i++)
							str = "great-" + str;
					}
				}
				
				else {
					if(generationsToDest != 0)
						str = ordinal(generations - 1) + " cousin " + Math.abs(generationsToDest) + " removed";
					
					else
						str = ordinal(generations - 1) + " cousin";
				}
			}
		}
		
		if(str.isEmpty())
			str = "non blood-related relative";
		
		return to.getFullName() + " is " + from.getFullName() + "'s " + str + ".";
	}
	
	private static String ordinal(int i) {
	    String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
	    switch (i % 100) {
	    case 11:
	    case 12:
	    case 13:
	        return i + "th";
	    default:
	        return i + sufixes[i % 10];

	    }
	}
	
	public FamilyMember commonAncestor(FamilyMember f1, FamilyMember f2) {
		if(f1 == null || f2 == null)
			return null;
		
		if(f1.equals(f2))
			return f1;
		
		FamilyMember[] relations = new FamilyMember[4];
		relations[0] = commonAncestor(f1.getFather(), f2);
		relations[1] = commonAncestor(f1.getMother(), f2);
		relations[2] = commonAncestor(f1, f2.getFather());
		relations[3] = commonAncestor(f1, f2.getMother());
		
		for(FamilyMember m: relations)
			if(m != null)
				return m;
		
		return null;
	}
	
	//mainly for those who are not blood related
	public FamilyMember commonDescendant(FamilyMember f1, FamilyMember f2) {
		if(f1 == null || f2 == null)
			return null;
		
		if(f1.equals(f2))
			return f1;
		
		ArrayList<FamilyMember> relations = new ArrayList<FamilyMember>();
		
		for(FamilyMember c: f1.getChildren())
			relations.add(commonDescendant(c, f2));
		
		for(FamilyMember c: f2.getChildren())
			relations.add(commonDescendant(f1, c));
		
		for(FamilyMember m: relations)
			if(m != null)
				return m;
		
		return null;
	}
	
	public Integer generationsTo(Integer gen, FamilyMember from, FamilyMember to) {
		Integer up = generationsUp(gen, from, to);
		Integer down = generationsDown(gen, from, to);
		
		return up == null? down == null? null: down: up;
	}
	
	public Integer generationsUp(Integer gen, FamilyMember from, FamilyMember to) {
		if(from == null || to == null)
			return null;
		
		if(from.equals(to))
			return gen;
			
		Integer mother = generationsUp(gen + 1, from.getMother(), to);
		Integer father = generationsUp(gen + 1, from.getFather(), to);
		
		return mother == null? father == null? null: father : mother;
	}
	
	public Integer generationsDown(Integer gen, FamilyMember from, FamilyMember to) {
		if(from == null || to == null)
			return null;
		
		if(from.equals(to))
			return gen;
		
		ArrayList<Integer> children = new ArrayList<Integer>();
		
		for(FamilyMember c: from.getChildren())
			 children.add(generationsDown(gen - 1, c, to));
		
		for(Integer i: children)
			if(i != null)
				return i;
		
		return null;	
	}
}
