import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateMember extends Stage {
	public CreateMember(Family f, FamilyMember m, String who, FamilyManager prev) {
		StackPane pane = new StackPane();
		
		Button submit = new Button("Submit");
		
		Text fn = new Text("First Name");
		TextField firstName = new TextField();
		firstName.setMinWidth(1);
		
		Text ln = new Text("Last Name");
		TextField lastName = new TextField();
		
		Text s = new Text("Sex");
		ChoiceBox<String> sex = new ChoiceBox<String>();
		sex.getItems().addAll("Male", "Female");
		sex.setValue("Male");
		
		
		Text b = new Text("Date of Birth (dd/MM/YYYY)");
		ChoiceBox<Integer> birthDay = new ChoiceBox<Integer>();
		ChoiceBox<Integer> birthMonth = new ChoiceBox<Integer>();
		
		for(int i = 1; i <= 31; i++)
			birthDay.getItems().add(i);
		
		for(int i = 1; i <= 12; i++)
			birthMonth.getItems().add(i);
		
		TextField birthYear = new TextField();
		
		birthDay.setValue(1);
		birthMonth.setValue(1);
		
		CheckBox dead = new CheckBox("Check if deceased");
		
		Text d = new Text("Date of Death (dd/MM/YYYY)");
		ChoiceBox<Integer> deathDay = new ChoiceBox<Integer>();
		ChoiceBox<Integer> deathMonth = new ChoiceBox<Integer>();
		TextField deathYear = new TextField();
		
		for(int i = 1; i <= 31; i++)
			deathDay.getItems().add(i);
		
		for(int i = 1; i <= 12; i++)
			deathMonth.getItems().add(i);
		
		Text error = new Text("");
		
		//adds all objects into pane
		pane.getChildren().addAll(fn, firstName, ln, lastName, s, sex, b, birthDay, birthMonth, birthYear, dead, d, deathDay, deathMonth, deathYear, submit, error);
		
		fn.setTranslateX(-160);
		fn.setTranslateY(-130);
		firstName.setTranslateX(-130);
		firstName.setTranslateY(-105);
		firstName.setMaxWidth(120);
		ln.setTranslateX(-30);
		ln.setTranslateY(-130);
		lastName.setTranslateX(0);
		lastName.setTranslateY(-105);
		lastName.setMaxWidth(120);
		s.setTranslateX(95);
		s.setTranslateY(-130);
		sex.setTranslateX(120);
		sex.setTranslateY(-105);
		
		//if the member is mother and father, no need to pick sex
		if(who.equals("mother") || who.equals("father")) {
			s.setVisible(false);
			sex.setVisible(false);
			
			if(who.equals("mother"))
				sex.setValue("Female");
			
			if(who.equals("father"))
				sex.setValue("Male");
		}
		
		b.setTranslateX(-115);
		b.setTranslateY(-75);
		birthDay.setTranslateX(-170);
		birthDay.setTranslateY(-50);
		birthMonth.setTranslateX(-120);
		birthMonth.setTranslateY(-50);
		birthYear.setMaxWidth(60);
		birthYear.setTranslateX(-60);
		birthYear.setTranslateY(-50);
		
		dead.setTranslateX(70);
		dead.setTranslateY(-50);
		
		d.setVisible(false);
		d.setTranslateX(-115);
		d.setTranslateY(-15);
		deathDay.setVisible(false);
		deathDay.setTranslateX(-170);
		deathDay.setTranslateY(10);
		deathMonth.setVisible(false);
		deathMonth.setTranslateX(-120);
		deathMonth.setTranslateY(10);
		deathYear.setVisible(false);
		deathYear.setMaxWidth(60);
		deathYear.setTranslateX(-60);
		deathYear.setTranslateY(10);
		
		submit.setTranslateY(70);
		error.setTranslateY(90);
		
		dead.setOnAction(e -> {
			d.setVisible(!d.isVisible());
			deathDay.setVisible(!deathDay.isVisible());
			deathMonth.setVisible(!deathMonth.isVisible());
			deathYear.setVisible(!deathYear.isVisible());
		});
		
		
		submit.setOnAction(e -> {
			String fName = firstName.getText();
			String lName = lastName.getText();
			
			String sx = sex.getValue();
			
			if(!birthYear.getText().isEmpty() && !fName.isEmpty() && !lName.isEmpty()) {
				GregorianCalendar birth = new GregorianCalendar();
				birth.set(Calendar.YEAR, Integer.parseInt(birthYear.getText()));
				birth.set(Calendar.MONTH, birthMonth.getValue() - 1); //month is 0-indexed
				birth.set(Calendar.DAY_OF_MONTH, birthDay.getValue());
				
				if(dead.isSelected() && !(deathYear.getText() == null || deathYear.getText().trim().isEmpty())) {
					GregorianCalendar death = new GregorianCalendar();
					death.set(Calendar.YEAR, Integer.parseInt(deathYear.getText()));
					death.set(Calendar.MONTH, birthMonth.getValue() - 1); //month is 0-indexed
					death.set(Calendar.DAY_OF_MONTH, birthDay.getValue());
					
					if(who.equals("first"))
						f.addMember(new FamilyMember(fName, lName, sx, birth, death));
					
					if(who.equals("mother"))
						f.addMother(m, new FamilyMember(fName, lName, sx, birth, death));
					
					if(who.equals("father"))
						f.addFather(m, new FamilyMember(fName, lName, sx, birth, death));
					
					if(who.equals("child"))
						f.addChild(m, new FamilyMember(fName, lName, sx, birth, death));
					
					//sets button to invisible
					prev.initialize(f, "initial");
					
					//closes this window
					close();
				}
				
				else {
					if(who.equals("first"))
						f.addMember(new FamilyMember(fName, lName, sx, birth));
					
					if(who.equals("mother"))
						f.addMother(m, new FamilyMember(fName, lName, sx, birth));
					
					if(who.equals("father"))
						f.addFather(m, new FamilyMember(fName, lName, sx, birth));
					
					if(who.equals("child"))
						f.addChild(m, new FamilyMember(fName, lName, sx, birth));
					
					//sets button to invisible
					prev.initialize(f, "initial");
					
					//closes this window
					close();
				}
			}
		});
		
		Scene scene = new Scene(pane, 400, 300);
		this.setTitle("Add A Family Member"); // Set the stage title
	    this.setScene(scene); // Place the scene in the stage
	    this.show(); // Display the stage
	}
}
