import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PrintStage extends Stage {
	StackPane pane;
	Scene scene;
	
	public PrintStage(FamilyMember member) {
		pane = new StackPane();
		
		print(member);
		
		scene = new Scene(pane, 600, 700);
		
		this.setTitle(member.getFullName() + "'s Family Tree"); // Set the stage title
	    this.setScene(scene); // Place the scene in the stage
	    this.show(); // Display the stage
	}
	
	public void print(FamilyMember m) {
		Text info = new Text();
		info.setText(m.getFullName() + "\n" + m.getBirthDay() + "/" + m.getBirthMonth() + "/" + m.getBirthYear() + "\n - \n" + m.getDeathDayString() + "/" + m.getDeathMonthString() + "/" + m.getDeathYearString());
		info.setX(0);
		info.setTranslateX(0);
		info.setY(0);
		info.setTranslateY(0);
		
		pane.getChildren().addAll(info);
		
		if(m.getMother() != null)
			printParent(m.getMother(), 0, 0, -200, -200);
		
		if(m.getFather() != null)
			printParent(m.getFather(), 0, 0, 200, -200);
		
		int numChild = 0;
		for(FamilyMember c: m.getChildren())
			numChild++;
		
		numChild++;
		
		int childCount = 0;
		for(FamilyMember c: m.getChildren()) {
			childCount++;
			printChild(c, 0, 0, -400 + 200 *childCount, 200);
		}
	}
	
	public void printParent(FamilyMember m, int prevX, int prevY, int X, int Y) {
		if(m == null)
			return;
		
		Text info = new Text();
		info.setText(m.getFullName() + "\n" + m.getBirthDay() + "/" + m.getBirthMonth() + "/" + m.getBirthYear() + "\n - \n" + m.getDeathDayString() + "/" + m.getDeathMonthString() + "/" + m.getDeathYearString());
		
		pane.getChildren().addAll(info);
		info.setTranslateX(X);
		info.setTranslateY(Y);
		
		Line line = new Line(prevX - 60, prevY - 60, X + 80, Y+80);
		pane.getChildren().addAll(line);
	}
	
	public void printChild(FamilyMember m, int prevX, int prevY, int X, int Y) {
		Text info = new Text();
		info.setText(m.getFullName() + "\n" + m.getBirthDay() + "/" + m.getBirthMonth() + "/" + m.getBirthYear() + "\n - \n" + m.getDeathDayString() + "/" + m.getDeathMonthString() + "/" + m.getDeathYearString());
		
		pane.getChildren().addAll(info);
		info.setTranslateX(X);
		info.setTranslateY(Y);
		
		Line line = new Line(prevX + 60, prevY + 60, X - 80, Y - 80);
		pane.getChildren().addAll(line);
	}
}
