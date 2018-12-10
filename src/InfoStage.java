import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InfoStage extends Stage {
	public InfoStage(FamilyMember f) {
		StackPane pane = new StackPane();
		
		Text nameText = new Text("Name: " + f.getFullName());
		Text ageText = new Text("Age: " + f.getAge());
		Text birthText = new Text("Date of Birth: " + f.getBirthDay() + "/" + f.getBirthMonth() + "/" + f.getBirthYear());
		Text deathText = new Text("Date of Death: " + f.getDeathDayString() + "/" + f.getDeathMonthString() + "/" + f.getDeathYearString());
		Text sexText = new Text("Sex: " + f.getSex());
		Text motherText = f.getMother() == null? new Text("Mother: Unkown") : new Text("Mother: " + f.getMother().getFullName());
		Text fatherText = f.getFather() == null? new Text("Father: Unkown") : new Text("Father: " + f.getFather().getFullName());
		
		String childString = "Children: ";
		ArrayList<FamilyMember> childList = f.getChildren();
		
		if(childList.size() == 0) {
			childString += "None";
		}
		
		else {
			for(int i = 0; i < childList.size(); i++) {
				if(i == childList.size() - 1)
					childString += childList.get(i).getFullName();
					
				else
					childString += childList.get(i).getFullName() + ", ";
			}
		}
		
		Text childText = new Text(childString);
		
		pane.getChildren().addAll(nameText, birthText, deathText, motherText, fatherText, childText);
		
		Scene scene = new Scene(pane, 400, 300);
		
		nameText.setTranslateY(0 - scene.getHeight() / 2 + 40);
		ageText.setTranslateY(0 - scene.getHeight() / 2 + 60);
		birthText.setTranslateY(0 - scene.getHeight() / 2 + 80);
		deathText.setTranslateY(0 - scene.getHeight() / 2 + 100);
		sexText.setTranslateY(0 - scene.getHeight() / 2 + 120);
		motherText.setTranslateY(0 - scene.getHeight() / 2 + 140);
		fatherText.setTranslateY(0 - scene.getHeight() / 2 + 160);
		childText.setTranslateY(0 - scene.getHeight() / 2 + 180);
		
		this.setTitle(f.getFullName()); // Set the stage title
	    this.setScene(scene); // Place the scene in the stage
	    this.show(); // Display the stage
	}
}
