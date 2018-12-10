import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FamilyManager extends Application {
	FamilyManager app = this;
	
	Stage stage = new Stage();
	
	Family fam = new Family();
	
	//buttons
	Button addFirstBut = new Button("Add First Family Member");
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage){
		stage = primaryStage;

		Pane pane = new StackPane();
		
		pane.getChildren().addAll(addFirstBut);
		addFirstBut.setOnAction(e-> {
			Family fam = new Family();
			
			Stage addMemberStage = new CreateMember(fam, null, "first", this);
			addMemberStage.show();
		});
		
		Scene scene = new Scene(pane);
	    stage.setTitle("Family Tree Manager"); // Set the stage title
	    stage.setScene(scene); // Place the scene in the stage
	    stage.setMaximized(true);
	    stage.show(); // Display the stage
	}
	
	public void initialize(Family f, String filter) {
		fam = f;
		
		addFirstBut.setVisible(false);
		
		Button addNewMember = new Button("Add Another Member");
		addNewMember.setOnAction(e -> {
			Stage addMemberStage = new CreateMember(f, null, "first", this);
			addMemberStage.show();
		});
		
		Button reset = new Button("Return");
		reset.setOnAction(e -> {
			initialize(f, "initial");
		});
		
		Button filterAdults = new Button("Filter Adults");
		filterAdults.setOnAction(e -> {
			initialize(f, "adults");
		});
		
		Button filterChildren = new Button("Filter Children");
		filterChildren.setOnAction(e -> {
			initialize(f, "children");
		});
		
		Button filterLiving = new Button("Filter Living");
		filterLiving.setOnAction(e -> {
			initialize(f, "living");
		});
		
		Button filterDeceased = new Button("Filter Deceased");
		filterDeceased.setOnAction(e -> {
			initialize(f, "deceased");
		});
		
		TableView<FamilyMember> members = new TableView<FamilyMember>();
		
		VBox display = new VBox();

		Pane pane = new StackPane();
		
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
		
		TableColumn<FamilyMember, Button> printCol = new TableColumn<>("");
		Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>> cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Print Tree");
					
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
								
								Stage printStage = new PrintStage(member);
								printStage.show();
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		printCol.setCellFactory(cellFactory);
		
		TableColumn<FamilyMember, Button> infoCol = new TableColumn<>("");
		cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Get Information");
					
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
								
								Stage addMemberStage = new InfoStage(member);
								addMemberStage.show();
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		infoCol.setCellFactory(cellFactory);
		
		TableColumn<FamilyMember, Button> fatherCol = new TableColumn<>("");
		cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Add Father");
					
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
								
								Stage addMemberStage = new CreateMember(fam, member, "father", app);
								addMemberStage.show();
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		fatherCol.setCellFactory(cellFactory);
		
		TableColumn<FamilyMember, Button> existingFatherCol = new TableColumn<>("");
		cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Add Existing Father");
					
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
								
								Stage select= new MemberButtons(fam, member, "father", app);
								select.show();
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		existingFatherCol.setCellFactory(cellFactory);
		
		TableColumn<FamilyMember, Button> motherCol = new TableColumn<>("");
		cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Add Mother");
					
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
								
								Stage addMemberStage = new CreateMember(fam, member, "mother", app);
								addMemberStage.show();
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		motherCol.setCellFactory(cellFactory);
		
		TableColumn<FamilyMember, Button> existingMotherCol = new TableColumn<>("");
		cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Add Existing Mother");
					
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
								
								Stage select= new MemberButtons(fam, member, "mother", app);
								select.show();
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		existingMotherCol.setCellFactory(cellFactory);
		
		TableColumn<FamilyMember, Button> childCol = new TableColumn<>("");
		cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Create Child");
					
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
								
								Stage addMemberStage = new CreateMember(fam, member, "child", app);
								addMemberStage.show();
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		childCol.setCellFactory(cellFactory);
		
		TableColumn<FamilyMember, Button> existingChildCol = new TableColumn<>("");
		cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Add Existing Child");
					
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
								
								Stage select= new MemberButtons(fam, member, "child", app);
								select.show();
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		existingChildCol.setCellFactory(cellFactory);
		
		TableColumn<FamilyMember, Button> deleteCol = new TableColumn<>("");
		cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Delete");
					
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
								
								fam.delete(member);
								initialize(fam, "initial");
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		deleteCol.setCellFactory(cellFactory);
		
		TableColumn<FamilyMember, Button> relationCol = new TableColumn<>("");
		cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Get Relation");
					
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
								
								MemberButtons memStage = new MemberButtons(fam, member, "relation", app);
								memStage.show();
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		relationCol.setCellFactory(cellFactory);
		
		TableColumn<FamilyMember, Button> commonAncestorCol = new TableColumn<>("");
		cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Common Ancestor");
					
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
								
								MemberButtons memStage = new MemberButtons(fam, member, "commonAncestor", app);
								memStage.show();
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		commonAncestorCol.setCellFactory(cellFactory);
		
		TableColumn<FamilyMember, Button> commonDescendantCol = new TableColumn<>("");
		cellFactory = new Callback<TableColumn<FamilyMember, Button>, TableCell<FamilyMember, Button>>() {
			@Override
			public TableCell<FamilyMember, Button> call(TableColumn<FamilyMember, Button> arg0) {
				final TableCell<FamilyMember, Button> cell = new TableCell<FamilyMember, Button>() {
					final Button btn = new Button("Common Descendant");
					
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
								
								MemberButtons memStage = new MemberButtons(fam, member, "commonDescendant", app);
								memStage.show();
							});
							
							setGraphic(btn);
							setText(null);
						}
					}
				};
				
				return cell;
			}
		};
		commonDescendantCol.setCellFactory(cellFactory);
		
		if(filter.equals("initial"))
			members.setItems(getObservableMembers());
		
		if(filter.equals("adults"))
			members.setItems(getObservableAdultMembers());
		
		if(filter.equals("children"))
			members.setItems(getObservableChildMembers());
		
		if(filter.equals("living"))
			members.setItems(getObservableLivingMembers());
		
		if(filter.equals("deceased"))
			members.setItems(getObservableDeceasedMembers());
		
		members.getColumns().addAll(givenCol, lastCol, sexCol, birthCol, deathCol, infoCol, deleteCol, fatherCol, existingFatherCol, motherCol, existingMotherCol, childCol, existingChildCol, relationCol, commonAncestorCol, commonDescendantCol, printCol);
		members.setMinHeight(stage.getHeight() * .8);
		
		pane.getChildren().addAll(addNewMember, reset, filterAdults, filterChildren, filterLiving, filterDeceased);
		pane.setMinHeight(stage.getHeight() * .2);
		
		display.getChildren().addAll(members, pane);
		display.setMinHeight(stage.getHeight());
		display.setMinWidth(stage.getWidth());
		
		addNewMember.setTranslateX(-250);
		reset.setTranslateX(-150);
		filterAdults.setTranslateX(-50);
		filterChildren.setTranslateX(50);
		filterLiving.setTranslateX(150);
		filterDeceased.setTranslateX(250);
		
		Scene scene = new Scene(display);
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}
	
	public ObservableList<FamilyMember> getObservableMembers(){
		ObservableList<FamilyMember> rows = FXCollections.observableArrayList();
		
		for(FamilyMember m: fam.getMembers())
			rows.add(m);
		
		return rows;
	}
	
	public ObservableList<FamilyMember> getObservableAdultMembers(){
		ObservableList<FamilyMember> rows = FXCollections.observableArrayList();
		
		for(FamilyMember m: fam.getAdults())
			rows.add(m);
		
		return rows;
	}
	
	public ObservableList<FamilyMember> getObservableChildMembers(){
		ObservableList<FamilyMember> rows = FXCollections.observableArrayList();
		
		for(FamilyMember m: fam.getChildren())
			rows.add(m);
		
		return rows;
	}
	
	public ObservableList<FamilyMember> getObservableLivingMembers(){
		ObservableList<FamilyMember> rows = FXCollections.observableArrayList();
		
		for(FamilyMember m: fam.getLiving())
			rows.add(m);
		
		return rows;
	}
	
	public ObservableList<FamilyMember> getObservableDeceasedMembers(){
		ObservableList<FamilyMember> rows = FXCollections.observableArrayList();
		
		for(FamilyMember m: fam.getDeceased())
			rows.add(m);
		
		return rows;
	}
}
