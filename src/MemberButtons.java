import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MemberButtons extends Stage {
	Family fam;
	FamilyMember mem;
	
	public MemberButtons(Family f, FamilyMember m, String operation, FamilyManager main) {
		fam = f;
		mem = m;
		
		VBox window = new VBox();
		
		StackPane pane = new StackPane();
		Text theText = new Text();
		pane.getChildren().addAll(theText);
		
		TableView<FamilyMember> members = new TableView<FamilyMember>();
		
		TableColumn<FamilyMember, String> givenCol = new TableColumn<>("First Name");
		givenCol.setMinWidth(100);
		givenCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirstName()));
		
		TableColumn<FamilyMember, String> lastCol = new TableColumn<>("Last Name");
		lastCol.setMinWidth(100);
		lastCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getSurname()));
		
		TableColumn<FamilyMember, String> sexCol = new TableColumn<>("Sex");
		sexCol.setMaxWidth(100);
		sexCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getSex()));
		
		TableColumn<FamilyMember, String> birthCol = new TableColumn<>("Date of Birth");
		birthCol.setMaxWidth(100);
		
		TableColumn<FamilyMember, String> birthDayCol = new TableColumn<>("Day");
		birthDayCol.setCellValueFactory(e -> new SimpleStringProperty(Integer.toString(e.getValue().getBirthDay())));
		
		TableColumn<FamilyMember, String> birthMonthCol = new TableColumn<>("Month");
		birthMonthCol.setCellValueFactory(e -> new SimpleStringProperty(Integer.toString(e.getValue().getBirthMonth())));
		
		TableColumn<FamilyMember, String> birthYearCol = new TableColumn<>("Year");
		birthYearCol.setCellValueFactory(e -> new SimpleStringProperty(Integer.toString(e.getValue().getBirthYear())));
		
		birthCol.getColumns().addAll(birthDayCol, birthMonthCol, birthYearCol);
		
		TableColumn<FamilyMember, String> deathCol = new TableColumn<>("Date of Death");
		deathCol.setMaxWidth(100);
		
		TableColumn<FamilyMember, String> deathDayCol = new TableColumn<>("Day");
		deathDayCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getDeathDayString()));
		
		TableColumn<FamilyMember, String> deathMonthCol = new TableColumn<>("Month");
		deathMonthCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getDeathMonthString()));
		
		TableColumn<FamilyMember, String> deathYearCol = new TableColumn<>("Year");
		deathYearCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getDeathYearString()));
		
		deathCol.getColumns().addAll(deathDayCol, deathMonthCol, deathYearCol);
		
		TableColumn<FamilyMember, Button> choiceCol = new TableColumn<>("");
		Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>> cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Choose This Member");
					
					@Override
					public void updateItem(Button item, boolean empty) {
						super.updateItem(item, empty);
						
						if (empty) {
								setGraphic(null);
								setText(null);
						} 
							
						else {
							btn.setOnAction(event -> {
								FamilyMember member = getTableView().getItems().get(getIndex());
								
								if(operation.equals("relation"))
									theText.setText(f.getRelationTo(m, member));
								
								if(operation.equals("commonAncestor"))
									theText.setText("Common Ancestor: " + f.commonAncestor(m, member).getFullName());
								
								if(operation.equals("commonDescendant"))
									theText.setText("Common Descendant : " + f.commonDescendant(m, member).getFullName());
								
								if(operation.equals("child")) {
									f.addChild(m, member);
									main.initialize(f, "initial");
									close();
								}
								
								if(operation.equals("mother")) {
									f.addMother(m, member);
									main.initialize(f, "initial");
									close();
								}
								
								if(operation.equals("father")) {
									f.addFather(m, member);
									main.initialize(f, "initial");
									close();
								}
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		choiceCol.setCellFactory(cellFactory);
		
		members.getColumns().addAll(givenCol, lastCol, sexCol, birthCol, deathCol, choiceCol);
		
		members.setItems(getObservableMembers());
		
		if(operation.equals("father"))
			members.setItems(getObservableMaleMembers());
		
		if(operation.equals("mother"))
			members.setItems(getObservableFemaleMembers());
		
		window.getChildren().addAll(members, pane);
		
		Scene scene = new Scene(window, 600, 600);
		
		this.setTitle("Choose Second Member"); // Set the stage title
	    this.setScene(scene); // Place the scene in the stage
	    this.show(); // Display the stage
	}
	
	public ObservableList<FamilyMember> getObservableMembers(){
		ObservableList<FamilyMember> rows = FXCollections.observableArrayList();
		
		for(FamilyMember m: fam.getMembers())
			if(!m.equals(mem))
				rows.add(m);
		
		return rows;
	}
	
	public ObservableList<FamilyMember> getObservableMaleMembers(){
		ObservableList<FamilyMember> rows = FXCollections.observableArrayList();
		
		for(FamilyMember m: fam.getMembers())
			if(!m.equals(mem) && m.getSex().equals("Male"))
				rows.add(m);
		
		return rows;
	}
	
	public ObservableList<FamilyMember> getObservableFemaleMembers(){
		ObservableList<FamilyMember> rows = FXCollections.observableArrayList();
		
		for(FamilyMember m: fam.getMembers())
			if(!m.equals(mem) && m.getSex().equals("Female"))
				rows.add(m);
		
		return rows;
	}
}
