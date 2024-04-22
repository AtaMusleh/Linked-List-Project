import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class InterFace extends Application {
	Menu FileMenu;
	Menu martyrMenu;
	Menu locationMenu;
	Menu statMenu;
	MenuItem insertM;
	MenuItem deleteM;
	MenuItem updateM;
	MenuItem searchM;
	MenuItem insertL;
	MenuItem deleteL;
	MenuItem updateL;
	MenuItem searchL;
	MenuItem Cfile;
	MenuItem save;
	MenuItem statM;
	Button fileC;
	MenuBar mb;
	FileChooser fileChooser;
	LocationLinkedList city;
	LocationNode n = null;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane bp = new BorderPane();
		FileMenu = new Menu("File");
		martyrMenu = new Menu("Martyrs");
		locationMenu = new Menu("Locations");
		statMenu = new Menu("Stat");
		insertM = new MenuItem("insert");
		updateM = new MenuItem("update");
		deleteM = new MenuItem("delete");
		searchM = new MenuItem("search");
		insertL = new MenuItem("insert");
		updateL = new MenuItem("update");
		deleteL = new MenuItem("delete");
		searchL = new MenuItem("search");
		save = new MenuItem("save Changes");
		Cfile = new MenuItem("Choose another file");
		statM = new MenuItem("Statistics");
		martyrMenu.getItems().addAll(insertM, updateM, deleteM, searchM);
		locationMenu.getItems().addAll(insertL, updateL, deleteL, searchL);
		FileMenu.getItems().addAll(Cfile, save);
		statMenu.getItems().addAll(statM);
		mb = new MenuBar();
		mb.setDisable(true);
		mb.getMenus().addAll(locationMenu, martyrMenu, FileMenu, statMenu);
		fileC = new Button("Choose a file");
		bp.setTop(mb);
		bp.setCenter(fileC);
		fileC.setOnAction(e -> {
			city = new LocationLinkedList();
			fileChooser = new FileChooser();
			FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(txtFilter);
			fileChooser.setTitle("Select File");
			File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile == null) {
				mb.setDisable(true);
			} else {
				mb.setDisable(false);

				// city = new LocationLinkedList();

				try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
					String line;
					while ((line = br.readLine()) != null) {

						String[] tkz = line.split(",");
						if (tkz.length == 5) {
							try {
								String name = tkz[0];
								String age = (tkz[1]).trim();
								String location = tkz[2];
								String dateStr = tkz[3];
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
								LocalDate date = LocalDate.parse(dateStr, formatter);
								char gender = tkz[4].charAt(0);
								if (!age.isEmpty()) {
									Martyr martyr = new Martyr(name, Integer.parseInt(age), date, gender);

									n = city.search(location);
									if (n == null) {
										n = new LocationNode(new Location(location));
										city.insert(n);
									}
									if (n.getLocation().getMartyrs().searchB(martyr) == null) {
										n.getLocation().getMartyrs().insert(martyr);
									}
								} else if (age.isEmpty()) {
									Martyr martyr = new Martyr(name, 0, date, gender);

									n = city.search(location);
									if (n == null) {
										n = new LocationNode(new Location(location));
										city.insert(n);
									}
									if (n.getLocation().getMartyrs().searchB(martyr) == null) {
										n.getLocation().getMartyrs().insert(martyr);
									}

								}

							} catch (Exception e5) {

							}

						}
					}
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});
		Cfile.setOnAction(e -> {
			bp.setCenter(fileC);
		});
		insertM.setOnAction(e -> {
			Label martyrs = new Label("New Martyr");
			martyrs.setFont(new Font(18));
			Label l1 = new Label("Name: ");
			Label l2 = new Label("Age: ");
			Label l3 = new Label("Date of death: ");
			Label l4 = new Label("Location: ");
			Label l5 = new Label("Gender: ");
			Label l6 = new Label();
			TextField t1 = new TextField();
			TextField t2 = new TextField();
			TextField t3 = new TextField();
			TextField t4 = new TextField();
			ComboBox<String> genderBox = new ComboBox<>();
			genderBox.getItems().addAll("M", "F");
			genderBox.setValue("M");

			Button saveB = new Button("insert");
			t3.setPromptText("yyyy-MM-dd");
			HBox h = new HBox(saveB);
			h.setAlignment(Pos.CENTER);
			HBox h2 = new HBox(l6);
			h2.setAlignment(Pos.CENTER);
			GridPane gp = new GridPane();
			gp.add(martyrs, 1, 0);
			gp.add(l1, 0, 1);
			gp.add(l2, 0, 2);
			gp.add(l3, 0, 3);
			gp.add(l4, 0, 4);
			gp.add(l5, 0, 5);
			gp.add(t1, 1, 1);
			gp.add(t2, 1, 2);
			gp.add(t3, 1, 3);
			gp.add(t4, 1, 4);
			gp.add(genderBox, 1, 5);
			gp.add(h, 1, 6);
			gp.add(h2, 1, 7);
			gp.setHgap(10);
			gp.setVgap(5);
			saveB.setOnAction(e1 -> {
				String name = t1.getText();
				String ageSTR = t2.getText();
				String datedeath = t3.getText();
				String location = t4.getText();
				String gender = genderBox.getValue();
				if (!name.isEmpty() && !datedeath.isEmpty() && !location.isEmpty() && !gender.isEmpty()) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate date = null;
					try {
						date = LocalDate.parse(datedeath, formatter);
					} catch (Exception e3) {

					}

					Martyr martyr;
					int age = 0;
					try {
						age = Integer.parseInt(ageSTR);
					} catch (NumberFormatException e4) {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("Enter a valid Age");
						alert.showAndWait();

					}
					if (age > 0) {
						martyr = new Martyr(name, age, date, gender.charAt(0));

//					if (!ageSTR.isEmpty()) {
//						martyr = new Martyr(name, age, date, gender.charAt(0));
//					} else {
//						martyr = new Martyr(name, 0, date, gender.charAt(0));
//					}
						n = city.search(location);
						if (n == null) {
							n = new LocationNode(new Location(location));
							city.insert(n);
						}
						if (date != null && n.getLocation().getMartyrs().searchB(martyr) == null) {
							n.getLocation().getMartyrs().insert(martyr);
							l6.setText("The martyr has been added sucessfully");
						} else if (date != null && n.getLocation().getMartyrs().searchB(martyr) != null)
							l6.setText("already there");
						t1.clear();
						t2.clear();
						t3.clear();
						t4.clear();
					} else if (name.isEmpty() || datedeath.isEmpty() || location.isEmpty() || gender.isEmpty()
							|| datedeath.isEmpty()) {
						l6.setText("Don't leave anything blank");

					}
				}

			});
			bp.setCenter(gp);
		});
		deleteM.setOnAction(e -> {

			Label top = new Label("Delete a Martyr");
			top.setFont(new Font(20));
			Label l1 = new Label("Name: ");
			Label l2 = new Label("Location: ");
			Button deleteB = new Button("Delete");
			ComboBox<String> cityComboBox = new ComboBox<>();
			ComboBox<String> martyrBox = new ComboBox<>();
			n = city.getFront();
			while (n != null) {
				cityComboBox.getItems().add(n.getLocation().getCity());
				n = n.getNext();
			}
			cityComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
				martyrBox.getItems().clear();
				n = city.search(newvalue);
				if (n != null) {
					MartyrNode curr = n.getLocation().getMartyrs().getFront();
					while (curr != null) {
						martyrBox.getItems().add(curr.getMartyr().getName());
						curr = curr.getNext();
					}
				}
			});
			deleteB.setOnAction(e1 -> {
				n = city.search(cityComboBox.getValue());
				if (martyrBox.getValue() != null && cityComboBox != null) {
					if (n.getLocation().getMartyrs().search(martyrBox.getValue()) != null) {
						n.getLocation().getMartyrs().delete(martyrBox.getValue());
					}

					martyrBox.setValue(null);
					cityComboBox.setValue(null);
				} else {

				}

			});

			HBox h = new HBox(10);
			h.getChildren().addAll(l1, martyrBox);
			h.setAlignment(Pos.CENTER);
			HBox h2 = new HBox(10);
			h2.getChildren().addAll(l2, cityComboBox);
			h2.setAlignment(Pos.CENTER);
			GridPane gp = new GridPane();
			VBox v = new VBox(10);
			v.getChildren().addAll(top, h2, h, deleteB);
			v.setAlignment(Pos.CENTER);
			bp.setCenter(v);
		});
		updateM.setOnAction(e -> {
			Label top = new Label("Update Martyr info");
			top.setFont(new Font(18));
			Label l1 = new Label("Name: ");
			Label l2 = new Label("Age: ");
			Label l3 = new Label("Date of death: ");
			Label l4 = new Label("Location: ");
			Label l5 = new Label("Gender: ");
			Label l6 = new Label();
			TextField t2 = new TextField();
			TextField t3 = new TextField();
			// TextField t5 = new TextField();
			ComboBox<String> genderBox = new ComboBox<>();
			genderBox.getItems().addAll("M", "F");
			Button updateB = new Button("Update");
			ComboBox<String> cityComboBox = new ComboBox<>();
			ComboBox<String> martyrBox = new ComboBox<>();
			n = city.getFront();
			while (n != null) {
				cityComboBox.getItems().add(n.getLocation().getCity());
				n = n.getNext();
			}
			updateB.setDisable(true);
			cityComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
				martyrBox.getSelectionModel().selectedItemProperty()
						.addListener((observable1, oldValue1, newValue1) -> {
							if (newValue1 != null && cityComboBox.getValue() != null) {
								updateB.setDisable(false);
								n = city.search(cityComboBox.getValue());
								MartyrNode martyrNode = n.getLocation().getMartyrs().search(newValue1);
								if (martyrNode != null) {
									Martyr martyr = martyrNode.getMartyr();
									t2.setText(String.valueOf(martyr.getAge()));
									t3.setText(martyr.getDeath().toString());
									genderBox.setValue(String.valueOf(martyr.getGender()));
								}

							}
						});
				martyrBox.getItems().clear();
				n = city.search(newvalue);
				if (n != null) {
					MartyrNode curr = n.getLocation().getMartyrs().getFront();
					while (curr != null) {
						martyrBox.getItems().add(curr.getMartyr().getName());
						curr = curr.getNext();
					}
				}
			});
			HBox h = new HBox(updateB);
			updateB.setOnAction(e2 -> {
				String name = martyrBox.getValue();
				String ageStr = t2.getText();
				String datedeath = t3.getText();
				String location = cityComboBox.getValue();
				String gender = genderBox.getValue();
				if (!name.isEmpty() && !datedeath.isEmpty() && !location.isEmpty() && !gender.isEmpty()) {

					LocalDate date = null;
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					try {
						date = LocalDate.parse(datedeath, formatter);
					} catch (Exception e22) {
						date = null;
					}
					n = city.search(location);
					if (n != null && n.getLocation().getMartyrs().search(name) != null) {
						if (n.getLocation().getMartyrs().searchDub(martyrBox.getValue(), Integer.parseInt(t2.getText()),
								date, gender.charAt(0)) == null) {
							if (date != null) {

								if (!ageStr.isEmpty()) {

									n.getLocation().getMartyrs().update(name, Integer.parseInt(ageStr), date,
											gender.charAt(0));

								} else {
									n.getLocation().getMartyrs().update(name, 0, date, gender.charAt(0));
								}
								cityComboBox.setValue(null);
								t2.clear();
								t3.clear();
								martyrBox.setValue(null);
								genderBox.setValue(null);
								l6.setText(null);
							} else {
								l6.setText("The date is null or wrong");
							}
						} else {
							l6.setText("it's the same informations, update it");

						}

					}
				}

			});
			h.setAlignment(Pos.CENTER);
			t3.setPromptText("yyyy-MM-dd");
			GridPane gp = new GridPane();
			gp.add(top, 1, 0);
			gp.add(l4, 0, 1);
			gp.add(l1, 0, 2);
			gp.add(l2, 0, 3);
			gp.add(l3, 0, 4);
			gp.add(l5, 0, 5);
			gp.add(cityComboBox, 1, 1);
			gp.add(martyrBox, 1, 2);
			gp.add(t2, 1, 3);
			gp.add(t3, 1, 4);
			gp.add(genderBox, 1, 5);
			gp.add(h, 1, 6);
			gp.add(l6, 1, 7);
			gp.setHgap(10);
			gp.setVgap(5);
			bp.setCenter(gp);
		});
		insertL.setOnAction(e -> {
			Label top = new Label("New Location");
			top.setFont(new Font(20));
			Label l1 = new Label("City name: ");
			TextField t1 = new TextField();
			Label l2 = new Label();
			Button insertB = new Button("Insert");
			HBox h = new HBox(10);
			h.getChildren().addAll(l1, t1);
			h.setAlignment(Pos.CENTER);
			VBox v = new VBox(10);
			v.getChildren().addAll(top, h, insertB, l2);
			v.setAlignment(Pos.CENTER);
			insertB.setOnAction(e1 -> {
				String name = t1.getText();
				if (!name.isEmpty()) {
					LocationNode exist = city.search(name);
					if (exist == null) {
						city.insert(new LocationNode(new Location(name)));
						l2.setText("The city have been inserted succefully");
					} else
						l2.setText("The city already exists");
				}
			});
			bp.setCenter(v);

		});
		deleteL.setOnAction(e -> {
			Label top = new Label("Delete Location");
			top.setFont(new Font(20));
			Label l1 = new Label("City");
			Button deleteB = new Button("Delete");
			Label l2 = new Label();
			ComboBox<String> cityComboBox = new ComboBox<>();
			HBox h = new HBox(10);
			h.getChildren().addAll(l1, cityComboBox);
			h.setAlignment(Pos.CENTER);
			VBox v = new VBox(10);
			v.getChildren().addAll(top, h, deleteB, l2);
			v.setAlignment(Pos.CENTER);
			n = city.getFront();
			while (n != null) {
				cityComboBox.getItems().add(n.getLocation().getCity());
				n = n.getNext();
			}

			deleteB.setOnAction(e1 -> {
				l2.setText(null);
				if (cityComboBox.getValue() != null) {
					city.delete(cityComboBox.getValue());
					cityComboBox.setValue(null);
					cityComboBox.getItems().clear();
					l2.setText("The city has been deleted");
					n = city.getFront();
					while (n != null) {
						cityComboBox.getItems().add(n.getLocation().getCity());
						n = n.getNext();
					}
				} else if (cityComboBox.getValue() == null) {

					l2.setText("Choose a city");
				}

			});
			bp.setCenter(v);

		});
		updateL.setOnAction(e -> {
			Label top = new Label("Update a Location");
			top.setFont(new Font(20));
			Label l1 = new Label("Old City");
			Label l2 = new Label("New City");
			ComboBox<String> cityComboBox = new ComboBox<>();
			TextField t2 = new TextField();
			Button updateB = new Button("Update");
			HBox h1 = new HBox(10);
			h1.getChildren().addAll(l1, cityComboBox);
			h1.setAlignment(Pos.CENTER);
			HBox h2 = new HBox(10);
			h2.getChildren().addAll(l2, t2);
			h2.setAlignment(Pos.CENTER);
			VBox v = new VBox(10);
			v.setAlignment(Pos.CENTER);
			v.getChildren().addAll(top, h1, h2, updateB);

			n = city.getFront();
			while (n != null) {
				cityComboBox.getItems().add(n.getLocation().getCity());
				n = n.getNext();
			}
			t2.setText(null);
			updateB.setOnAction(e1 -> {
				if (cityComboBox.getValue() != null && (t2.getText() != null))
					city.update(cityComboBox.getValue(), t2.getText());
				cityComboBox.setValue(null);
				cityComboBox.getItems().clear();
				t2.setText(null);
				n = city.getFront();
				while (n != null) {
					cityComboBox.getItems().add(n.getLocation().getCity());
					n = n.getNext();
				}
			});

			bp.setCenter(v);
		});
		statM.setOnAction(e -> {
			Label top = new Label("Statistics");
			top.setFont(new Font(20));
			Label l1 = new Label("Select a city to get its stats");
			ComboBox<String> cityBox = new ComboBox<>();
			Button confirmB = new Button("Confirm");
			Button next = new Button("Next city");
			Button pre = new Button("Previous city");
			Label top2 = new Label();
			Label maleL = new Label();
			Label femaleL = new Label();
			Label avgA = new Label();
			Label dateD = new Label();
			TextArea ta = new TextArea();
			ta.setEditable(false);
			Label taA = new Label("The numbers of martyrs by age: ");

			n = city.getFront();
			while (n != null) {
				cityBox.getItems().add(n.getLocation().getCity());
				n = n.getNext();
			}
			HBox h = new HBox(10);
			h.getChildren().addAll(l1, cityBox);
			h.setAlignment(Pos.CENTER);
			VBox v = new VBox(10);
			v.getChildren().addAll(top, h, confirmB);
			v.setAlignment(Pos.CENTER);
			bp.setCenter(v);
			confirmB.setOnAction(e1 -> {
				String location = cityBox.getValue();
				if (location != null) {
					n = city.search(location);
					int mC = 0, fC = 0, avg = 0;
					String date = null;
					String a = null;
					if (n.getLocation().getMartyrs().getSize() != 0) {
						mC = n.getLocation().getMartyrs().getMaleMartyrCount();
						fC = n.getLocation().getMartyrs().getFemaleMartyrCount();
						avg = n.getLocation().getMartyrs().avgAge();
						date = n.getLocation().getMartyrs().mostCommonDate();
						a = n.getLocation().getMartyrs().getMartyrsByAge();
					}
					maleL.setText("Number of male martyrs in " + location + " is: " + mC);
					femaleL.setText("The number of Female martyrs in " + location + " is: " + fC);
					avgA.setText("The average age of martyrs in " + location + " is: " + avg + " YO");
					dateD.setText("The most date with Martyrs is " + date);
					ta.setText(a);
					ta.setFont(new Font(14));
					taA.setFont(new Font(14));
					maleL.setFont(new Font(14));
					femaleL.setFont(new Font(14));
					avgA.setFont(new Font(14));
					dateD.setFont(new Font(14));
					top2.setText(n.getLocation().getCity());
					top2.setFont(new Font(20));
					VBox v1 = new VBox(10);
					v1.getChildren().add(top2);
					v1.setAlignment(Pos.CENTER);
					VBox v2 = new VBox(10);
					v2.getChildren().addAll(maleL, femaleL, avgA, dateD, taA, ta);
					HBox h1 = new HBox(10);
					h1.getChildren().addAll(pre, next);
					h1.setAlignment(Pos.CENTER);
					VBox v3 = new VBox(10);
					v3.getChildren().addAll(v1, v2, h1);

					next.setOnAction(e2 -> {

						n = n.getNext();
						if (n != null) {
							int mC1 = n.getLocation().getMartyrs().getMaleMartyrCount();
							int fC1 = n.getLocation().getMartyrs().getFemaleMartyrCount();
							int avg1 = n.getLocation().getMartyrs().avgAge();
							String date1 = n.getLocation().getMartyrs().mostCommonDate();
							String a1 = n.getLocation().getMartyrs().getMartyrsByAge();
							top2.setText(n.getLocation().getCity());
							maleL.setText("Number of male martyrs in " + n.getLocation().getCity() + " is: " + mC1);
							femaleL.setText(
									"The number of Female martyrs in " + n.getLocation().getCity() + " is: " + fC1);
							avgA.setText("The average age of martyrs in " + n.getLocation().getCity() + " is: " + avg1
									+ " YO");
							dateD.setText("The most date with Martyrs is " + date1);
							ta.setText(a1);
							ta.setFont(new Font(14));
							taA.setFont(new Font(14));
							maleL.setFont(new Font(14));
							femaleL.setFont(new Font(14));
							avgA.setFont(new Font(14));
							dateD.setFont(new Font(14));
						} else if (n == null) {
							n = city.getBack();
						}

					});
					pre.setOnAction(e3 -> {
						n = n.getPre();
						if (n != null) {
							top2.setText(n.getLocation().getCity());
							int mC1 = n.getLocation().getMartyrs().getMaleMartyrCount();
							int fC1 = n.getLocation().getMartyrs().getFemaleMartyrCount();
							int avg1 = n.getLocation().getMartyrs().avgAge();
							String date1 = n.getLocation().getMartyrs().mostCommonDate();
							String a1 = n.getLocation().getMartyrs().getMartyrsByAge();
							top2.setText(n.getLocation().getCity());
							maleL.setText("Number of male martyrs in " + n.getLocation().getCity() + " is: " + mC1);
							femaleL.setText(
									"The number of Female martyrs in " + n.getLocation().getCity() + " is: " + fC1);
							avgA.setText("The average age of martyrs in " + n.getLocation().getCity() + " is: " + avg1
									+ " YO");
							dateD.setText("The most date with Martyrs is " + date1);
							ta.setText(a1);
							ta.setFont(new Font(14));
							taA.setFont(new Font(14));
							maleL.setFont(new Font(14));
							femaleL.setFont(new Font(14));
							avgA.setFont(new Font(14));
							dateD.setFont(new Font(14));
						} else if (n == null) {
							n = city.getFront();
						}
					});
					bp.setCenter(v3);

				}
			});

		});
		save.setOnAction(e -> {
			fileChooser = new FileChooser();
			FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(txtFilter);
			fileChooser.setTitle("Select a File to save on it");
			File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile, false))) {
					LocationNode locationNode = city.getFront();
					if (locationNode != null) {
						MartyrNode martyrNode = locationNode.getLocation().getMartyrs().getFront();
						while (martyrNode != null) {
							Martyr martyr = martyrNode.getMartyr();
							writer.write(martyr.getName() + "," + martyr.getAge() + ","
									+ locationNode.getLocation().getCity() + "," + martyr.getDeath() + ","
									+ martyr.getGender());
							writer.newLine();

							martyrNode = martyrNode.getNext();
						}

						locationNode = locationNode.getNext();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		searchL.setOnAction(e -> {
			TableView<Martyr> tableView = new TableView<>();
			Label top = new Label("Choose a city");
			ComboBox<String> cityBox = new ComboBox<>();
			Button search = new Button("Search");

			n = city.getFront();
			while (n != null) {
				cityBox.getItems().add(n.getLocation().getCity());
				n = n.getNext();
			}

			TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
			nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

			TableColumn<Martyr, Integer> ageColumn = new TableColumn<>("Age");
			ageColumn.setCellValueFactory(
					cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());

			TableColumn<Martyr, String> deathColumn = new TableColumn<>("Death");
			deathColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getDeath()));

			TableColumn<Martyr, String> genderColumn = new TableColumn<>("Gender");
			genderColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getGender()));

			tableView.getColumns().addAll(nameColumn, ageColumn, deathColumn, genderColumn);
			tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			cityBox.setOnAction(e2 -> {
				String selectedCity = cityBox.getValue();
				if (selectedCity != null) {
					tableView.getItems().clear();
					n = city.search(selectedCity);
					if (n != null) {
						MartyrNode current = n.getLocation().getMartyrs().getFront();
						while (current != null) {
							tableView.getItems().add(current.getMartyr());
							current = current.getNext();
						}
					}
				}
			});

			VBox v = new VBox(10);
			v.getChildren().addAll(top, cityBox, tableView);
			v.setAlignment(Pos.CENTER);

			bp.setCenter(v);
		});

		searchM.setOnAction(e -> {
			TableView<Martyr> tableView = new TableView<>();
			Label top = new Label("Choose a city");
			ComboBox<String> cityBox = new ComboBox<>();
			TextField f1 = new TextField();
			Label l1 = new Label("Name: ");
			Button search = new Button("Search");
			Button delete = new Button("Delete");
			n = city.getFront();
			while (n != null) {
				cityBox.getItems().add(n.getLocation().getCity());
				n = n.getNext();
			}
			TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
			nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

			TableColumn<Martyr, Integer> ageColumn = new TableColumn<>("Age");
			ageColumn.setCellValueFactory(
					cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());

			TableColumn<Martyr, String> deathColumn = new TableColumn<>("Death");
			deathColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getDeath()));

			TableColumn<Martyr, String> genderColumn = new TableColumn<>("Gender");
			genderColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getGender()));

			tableView.getColumns().addAll(nameColumn, ageColumn, deathColumn, genderColumn);
			tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			HBox h1 = new HBox(10);
			h1.getChildren().addAll(top, cityBox);
			h1.setAlignment(Pos.CENTER);
			HBox h2 = new HBox(10);
			h2.getChildren().addAll(l1, f1);
			h2.setAlignment(Pos.CENTER);
			VBox v = new VBox(10);
			v.getChildren().addAll(h1, h2, search, tableView, delete);
			v.setAlignment(Pos.CENTER);
			bp.setCenter(v);

			search.setOnAction(e1 -> {
				tableView.getItems().clear();
				String cityName = cityBox.getValue();
				if (cityName != null && !cityName.isEmpty()) {
					n = city.search(cityName);
					String searchKeyword = f1.getText();
					MartyrNode current = n.getLocation().getMartyrs().getFront();
					while (current != null) {
						String martyrName = current.getMartyr().getName();
						if (martyrName.toLowerCase().contains(searchKeyword.toLowerCase())) {
							tableView.getItems().add(current.getMartyr());
						}
						current = current.getNext();
					}

				} else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("Choose a City");
					alert.showAndWait();
				}
			});
			tableView.getSelectionModel().selectedItemProperty().addListener((val, oldSelection, newSelection) -> {
				if (newSelection != null) {
					String selectedName = newSelection.getName();
					delete.setOnAction(e1 -> {
						n = city.search(cityBox.getValue());
						if (n != null) {
							n.getLocation().getMartyrs().delete(selectedName);
							tableView.getItems().remove(newSelection);
						}
					});

				}
			});

		});

		Scene s = new Scene(bp, 500, 400);
		stage.setScene(s);
		stage.show();

	}

}