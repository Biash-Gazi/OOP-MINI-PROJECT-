import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AMSApp extends Application {

    private Database database = new Database();
    private Stage primaryStage;
    private List<Query> farmerQueries = new ArrayList<>();
    private String realTimeData = "Default Market Prices, Weather: Sunny, Crop Recommendations: Wheat";

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Agriculture Management System");

        showMainMenu();
        primaryStage.show();
    }

    // Main Menu with Login Options
    private void showMainMenu() {
        VBox loginOptions = new VBox(15);
        loginOptions.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d9e7d8;"); // Soft gray-green background

        Button farmerLoginBtn = new Button("Farmer Login");
        Button govLoginBtn = new Button("Government Official Login");

        // Updated colors for login buttons
        farmerLoginBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #2d5932; -fx-text-fill: white; -fx-background-radius: 10;"); 
        govLoginBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #317c3d; -fx-text-fill: white; -fx-background-radius: 10;"); 

        farmerLoginBtn.setOnAction(e -> showFarmerLoginPage());
        govLoginBtn.setOnAction(e -> showGovernmentOfficialLoginPage());

        loginOptions.getChildren().addAll(farmerLoginBtn, govLoginBtn);

        Scene scene = new Scene(loginOptions, 400, 200);
        primaryStage.setScene(scene);
    }

    // Farmer Login Page
    private void showFarmerLoginPage() {
        VBox farmerLogin = new VBox(15);
        farmerLogin.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d3e8d2;"); // Soft gray-green background

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");
        nameField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        Button signInBtn = new Button("Sign In");
        Button registerBtn = new Button("Register");
        Button backBtn = new Button("Back");

        signInBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #4C8C71; -fx-text-fill: white; -fx-background-radius: 10;");
        registerBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #3D6A58; -fx-text-fill: white; -fx-background-radius: 10;");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button

        signInBtn.setOnAction(e -> {
            Farmer farmer = database.getFarmerByName(nameField.getText());
            if (farmer != null && farmer.getPassword().equals(passwordField.getText())) {
                showFarmerMenu(farmer);
            } else {
                showAlert("Invalid name or password!", "Please try again.");
            }
        });
        registerBtn.setOnAction(e -> showFarmerRegistrationPage());
        backBtn.setOnAction(e -> showMainMenu());

        farmerLogin.getChildren().addAll(nameField, passwordField, signInBtn, registerBtn, backBtn);
        Scene scene = new Scene(farmerLogin, 400, 300);
        primaryStage.setScene(scene);
    }

    // Government Official Login Page
    private void showGovernmentOfficialLoginPage() {
        VBox govLogin = new VBox(15);
        govLogin.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d3e8d2;"); // Soft gray-green background

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        usernameField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        Button signInBtn = new Button("Sign In");
        Button backBtn = new Button("Back");

        signInBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #4C8C71; -fx-text-fill: white; -fx-background-radius: 10;");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button

        signInBtn.setOnAction(e -> {
            // Implement government login logic (if applicable)
            showAlert("Login Successful", "Welcome Government Official!");
            showGovernmentOfficialPage();  // Navigate to the government official page
        });
        backBtn.setOnAction(e -> showMainMenu());

        govLogin.getChildren().addAll(usernameField, passwordField, signInBtn, backBtn);
        Scene scene = new Scene(govLogin, 400, 300);
        primaryStage.setScene(scene);
    }

    // Farmer Registration Page
    private void showFarmerRegistrationPage() {
        VBox registerForm = new VBox(15);
        registerForm.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d3e8d2;"); // Soft gray-green background

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");
        nameField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        TextField aadharField = new TextField();
        aadharField.setPromptText("Enter Aadhar");
        aadharField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter Phone");
        phoneField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        TextField dobField = new TextField();
        dobField.setPromptText("Enter DOB");
        dobField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email");
        emailField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        TextField incomeField = new TextField();
        incomeField.setPromptText("Enter Annual Income");
        incomeField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        TextField bankField = new TextField();
        bankField.setPromptText("Enter Bank Number");
        bankField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        TextField loanField = new TextField();
        loanField.setPromptText("Enter Equipment Loan");
        loanField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        TextField landField = new TextField();
        landField.setPromptText("Enter Land Details");
        landField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        TextField pincodeField = new TextField();
        pincodeField.setPromptText("Enter Pincode");
        pincodeField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        TextField cropsField = new TextField();
        cropsField.setPromptText("Enter Crops");
        cropsField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        Button submitBtn = new Button("Register");
        Button backBtn = new Button("Back");

        submitBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #4C8C71; -fx-text-fill: white; -fx-background-radius: 10;");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button

        submitBtn.setOnAction(e -> {
            try {
                Farmer farmer = new Farmer(
                        nameField.getText(),
                        aadharField.getText(),
                        phoneField.getText(),
                        dobField.getText(),
                        emailField.getText(),
                        Double.parseDouble(incomeField.getText()),
                        bankField.getText(),
                        loanField.getText(),
                        landField.getText(),
                        pincodeField.getText(),
                        cropsField.getText(),
                        passwordField.getText()
                );
                database.addFarmer(farmer);
                showAlert("Registration Successful", "You have successfully registered!");
                showFarmerLoginPage();
            } catch (NumberFormatException ex) {
                showAlert("Invalid input!", "Please ensure all fields are correctly filled.");
            }
        });
        backBtn.setOnAction(e -> showFarmerLoginPage());

        registerForm.getChildren().addAll(nameField, aadharField, phoneField, dobField, emailField,
                incomeField, bankField, loanField, landField, pincodeField, cropsField, passwordField, submitBtn, backBtn);
        Scene scene = new Scene(registerForm, 400, 600);
        primaryStage.setScene(scene);
    }

    // Farmer Menu
    private void showFarmerMenu(Farmer farmer) {
        VBox farmerMenu = new VBox(15);
        farmerMenu.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d9e7d8;"); // Soft gray-green background

        Button realTimeDataBtn = new Button("Display Real-Time Data");
        Button queryBtn = new Button("Queries Menu");
        Button cropDetailsBtn = new Button("Enter Crop Production Details");
        Button backBtn = new Button("Back");

        realTimeDataBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #4C8C71; -fx-text-fill: white; -fx-background-radius: 10;");
        queryBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #3D6A58; -fx-text-fill: white; -fx-background-radius: 10;");
        cropDetailsBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #ff9800; -fx-text-fill: white; -fx-background-radius: 10;");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button

        realTimeDataBtn.setOnAction(e -> displayRealTimeData());
        queryBtn.setOnAction(e -> showFarmerQueryMenu(farmer));
        cropDetailsBtn.setOnAction(e -> enterCropDetails());
        backBtn.setOnAction(e -> showFarmerLoginPage());

        farmerMenu.getChildren().addAll(realTimeDataBtn, queryBtn, cropDetailsBtn, backBtn);
        Scene scene = new Scene(farmerMenu, 400, 300);
        primaryStage.setScene(scene);
    }

    // Farmer Query Menu
    private void showFarmerQueryMenu(Farmer farmer) {
        VBox queryMenu = new VBox(15);
        queryMenu.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d3e8d2;"); // Soft gray-green background

        Button viewResponsesBtn = new Button("View Responses to Queries");
        Button newQueryBtn = new Button("Submit a New Query");
        Button backBtn = new Button("Back");

        viewResponsesBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #4C8C71; -fx-text-fill: white; -fx-background-radius: 10;");
        newQueryBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #3D6A58; -fx-text-fill: white; -fx-background-radius: 10;");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button

        viewResponsesBtn.setOnAction(e -> viewQueryResponses(farmer));
        newQueryBtn.setOnAction(e -> enterFarmerQuery(farmer));
        backBtn.setOnAction(e -> showFarmerMenu(farmer));

        queryMenu.getChildren().addAll(viewResponsesBtn, newQueryBtn, backBtn);
        Scene scene = new Scene(queryMenu, 400, 300);
        primaryStage.setScene(scene);
    }

    // View Responses to Queries
    private void viewQueryResponses(Farmer farmer) {
        VBox responsesView = new VBox(20);
        responsesView.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d3e8d2;"); // Soft gray-green background

        Label responsesLabel = new Label("Responses to Your Queries:");
        responsesLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #333333;");

        // Maintain heading above the query results
        responsesView.getChildren().add(responsesLabel);

        for (Query query : farmerQueries) {
            if (query.getFarmerName().equals(farmer.getName()) && query.getResponse() != null) {
                responsesView.getChildren().add(new Label("Query: " + query.getQueryText() + " - Response: " + query.getResponse()));
            }
        }

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button
        backBtn.setOnAction(e -> showFarmerQueryMenu(farmer));

        responsesView.getChildren().add(backBtn);
        Scene scene = new Scene(responsesView, 400, 300);
        primaryStage.setScene(scene);
    }

    // Government Official Page
    private void showGovernmentOfficialPage() {
        VBox govMenu = new VBox(15);
        govMenu.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d9e7d8;"); // Soft gray-green background

        Button manageSubsidiesBtn = new Button("Manage Subsidies and Resources");
        Button updateRealTimeDataBtn = new Button("Update Real-Time Data");
        Button respondQueriesBtn = new Button("Respond to Farmer Queries");
        Button displayFarmerDetailsBtn = new Button("Display Farmer Details");
        Button backBtn = new Button("Back");

        manageSubsidiesBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #4C8C71; -fx-text-fill: white; -fx-background-radius: 10;");
        updateRealTimeDataBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #3D6A58; -fx-text-fill: white; -fx-background-radius: 10;");
        respondQueriesBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #ff9800; -fx-text-fill: white; -fx-background-radius: 10;");
        displayFarmerDetailsBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #4C8C71; -fx-text-fill: white; -fx-background-radius: 10;");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button

        manageSubsidiesBtn.setOnAction(e -> manageSubsidies());
        updateRealTimeDataBtn.setOnAction(e -> updateRealTimeData());
        respondQueriesBtn.setOnAction(e -> respondToQueries());
        displayFarmerDetailsBtn.setOnAction(e -> displayFarmerDetails());
        backBtn.setOnAction(e -> showMainMenu());

        govMenu.getChildren().addAll(manageSubsidiesBtn, updateRealTimeDataBtn, respondQueriesBtn, displayFarmerDetailsBtn, backBtn);
        Scene scene = new Scene(govMenu, 400, 300);
        primaryStage.setScene(scene);
    }

    // Display Real-Time Data
    private void displayRealTimeData() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Real-Time Data: " + realTimeData);
        alert.setTitle("Real-Time Data");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void enterFarmerQuery(Farmer farmer) {
        VBox queryBox = new VBox(20);
        queryBox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d3e8d2;"); // Soft gray-green background

        TextArea queryArea = new TextArea();
        queryArea.setPromptText("Enter your query here...");
        queryArea.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        Button submitQueryBtn = new Button("Submit Query");
        Button backBtn = new Button("Back");

        submitQueryBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #4C8C71; -fx-text-fill: white; -fx-background-radius: 10;");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button

        submitQueryBtn.setOnAction(e -> {
            Query query = new Query(queryArea.getText(), farmer.getName());
            farmerQueries.add(query);
            showAlert("Query Submitted", "Your query has been submitted.");
            showFarmerMenu(farmer);
        });
        backBtn.setOnAction(e -> showFarmerMenu(farmer));

        queryBox.getChildren().addAll(queryArea, submitQueryBtn, backBtn);
        Scene scene = new Scene(queryBox, 400, 300);
        primaryStage.setScene(scene);
    }

    private void enterCropDetails() {
        VBox cropBox = new VBox(20);
        cropBox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d3e8d2;"); // Soft gray-green background

        TextField cropTypeField = new TextField();
        cropTypeField.setPromptText("Enter Crop Type");
        cropTypeField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter Quantity Produced");
        quantityField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        Button submitCropDetailsBtn = new Button("Submit Crop Details");
        Button backBtn = new Button("Back");

        submitCropDetailsBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #4C8C71; -fx-text-fill: white; -fx-background-radius: 10;");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button

        submitCropDetailsBtn.setOnAction(e -> {
            System.out.println("Crop Type: " + cropTypeField.getText() + ", Quantity: " + quantityField.getText());
            showFarmerMenu(database.getFarmerByName(cropTypeField.getText())); // Fallback; better approach needed
        });
        backBtn.setOnAction(e -> showFarmerMenu(database.getFarmerByName(cropTypeField.getText())));

        cropBox.getChildren().addAll(cropTypeField, quantityField, submitCropDetailsBtn, backBtn);
        Scene scene = new Scene(cropBox, 400, 300);
        primaryStage.setScene(scene);
    }

    private void respondToQueries() {
        VBox responseBox = new VBox(20);
        responseBox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d3e8d2;"); // Soft gray-green background
        responseBox.setPrefHeight(400);

        Label instructionLabel = new Label("Select a Query to Respond:");
        instructionLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #333333;");

        ListView<String> queryListView = new ListView<>();

        // Populate the ListView with unanswered queries from farmers
        for (Query query : farmerQueries) {
            if (query.getResponse() == null) {
                queryListView.getItems().add(query.getQueryText());
            }
        }

        TextArea responseArea = new TextArea();
        responseArea.setPromptText("Type your response here...");
        responseArea.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-padding: 10; -fx-background-radius: 5;");

        Button submitResponseBtn = new Button("Submit Response");
        Button backBtn = new Button("Back");

        submitResponseBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #4C8C71; -fx-text-fill: white; -fx-background-radius: 10;");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button

        submitResponseBtn.setOnAction(e -> {
            String selectedQueryText = queryListView.getSelectionModel().getSelectedItem();
            if (selectedQueryText != null) {
                for (Query query : farmerQueries) {
                    if (query.getQueryText().equals(selectedQueryText)) {
                        query.setResponse(responseArea.getText());
                        showAlert("Success", "Response submitted for query: " + selectedQueryText);
                        break;
                    }
                }
            } else {
                showAlert("Error", "No query selected!");
            }
        });

        backBtn.setOnAction(e -> showGovernmentOfficialPage());

        responseBox.getChildren().addAll(instructionLabel, queryListView, responseArea, submitResponseBtn, backBtn);
        Scene scene = new Scene(responseBox, 400, 300);
        primaryStage.setScene(scene);
    }

    private void manageSubsidies() {
        VBox subsidyBox = new VBox(20);
        subsidyBox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d3e8d2;"); // Soft gray-green background
        
        Label instructionLabel = new Label("Manage Subsidies");
        instructionLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #333333;");

        ListView<String> subsidyListView = new ListView<>();
        Map<String, Double> subsidies = new HashMap<>(); // Key: Farmer Name, Value: Subsidy Amount

        // Initialize with some sample data
        subsidies.put("Farmer A", 1000.0);
        subsidies.put("Farmer B", 500.0);
        updateSubsidiesView(subsidyListView, subsidies);

        Button addSubsidyBtn = new Button("Add Subsidy");
        Button removeSubsidyBtn = new Button("Remove Subsidy");
        Button backBtn = new Button("Back");

        addSubsidyBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #4C8C71; -fx-text-fill: white; -fx-background-radius: 10;");
        removeSubsidyBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #ff4444; -fx-text-fill: white; -fx-background-radius: 10;");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button

        addSubsidyBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Subsidy");
            dialog.setHeaderText("Enter Farmer Name and Amount (comma separated):");
            dialog.setContentText("Format: Name, Amount");

            dialog.showAndWait().ifPresent(input -> {
                String[] parts = input.split(",");
                if (parts.length == 2) {
                    String farmerName = parts[0].trim();
                    double amount;
                    try {
                        amount = Double.parseDouble(parts[1].trim());
                        subsidies.put(farmerName, subsidies.getOrDefault(farmerName, 0.0) + amount);
                        updateSubsidiesView(subsidyListView, subsidies);
                    } catch (NumberFormatException ex) {
                        showAlert("Invalid Input", "Please enter a valid number for the amount.");
                    }
                } else {
                    showAlert("Invalid Input", "Please use the format: Name, Amount");
                }
            });
        });

        removeSubsidyBtn.setOnAction(e -> {
            String selectedSubsidy = subsidyListView.getSelectionModel().getSelectedItem();
            if (selectedSubsidy != null) {
                String farmerName = selectedSubsidy.split(" - ")[0];
                subsidies.remove(farmerName);
                updateSubsidiesView(subsidyListView, subsidies);
            } else {
                showAlert("No Selection", "Please select a subsidy to remove.");
            }
        });

        backBtn.setOnAction(e -> showGovernmentOfficialPage());

        subsidyBox.getChildren().addAll(instructionLabel, subsidyListView, addSubsidyBtn, removeSubsidyBtn, backBtn);
        Scene scene = new Scene(subsidyBox, 400, 300);
        primaryStage.setScene(scene);
    }

    // Helper method to update the ListView displaying subsidies
    private void updateSubsidiesView(ListView<String> subsidyListView, Map<String, Double> subsidies) {
        subsidyListView.getItems().clear();
        for (Map.Entry<String, Double> entry : subsidies.entrySet()) {
            subsidyListView.getItems().add(entry.getKey() + " - Amount: " + entry.getValue());
        }
    }

    // Helper method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateRealTimeData() {
        TextInputDialog dialog = new TextInputDialog(realTimeData);
        dialog.setTitle("Update Real-Time Data");
        dialog.setHeaderText("Enter new real-time data:");
        dialog.setContentText("Data:");

        dialog.showAndWait().ifPresent(newData -> {
            realTimeData = newData;
            showAlert("Success", "Real-Time Data Updated!");
        });
    }

    private void displayFarmerDetails() {
        VBox farmerDetailsBox = new VBox(20);
        farmerDetailsBox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #d3e8d2;"); // Soft gray-green background

        Label detailsLabel = new Label("List of Farmers:");
        detailsLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #333333;");

        ListView<String> farmerListView = new ListView<>();
        for (Farmer farmer : database.getAllFarmers()) {
            farmerListView.getItems().add(farmer.getName() + " - Aadhar: " + farmer.getAadhar() +
                    ", Phone: " + farmer.getPhone() + ", Email: " + farmer.getEmail() +
                    ", Annual Income: " + farmer.getAnnualIncome() + ", Crops: " + farmer.getCrops());
        }

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-color: #FF7F50; -fx-text-fill: white; -fx-background-radius: 10;"); // Coral color for the Back button
        backBtn.setOnAction(e -> showGovernmentOfficialPage());

        farmerDetailsBox.getChildren().addAll(detailsLabel, farmerListView, backBtn);
        Scene scene = new Scene(farmerDetailsBox, 400, 300);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Database class to manage farmers
class Database {
    private List<Farmer> farmers = new ArrayList<>();

    public void addFarmer(Farmer farmer) {
        farmers.add(farmer);
    }

    public Farmer getFarmerByName(String name) {
        for (Farmer farmer : farmers) {
            if (farmer.getName().equals(name)) {
                return farmer;
            }
        }
        return null;
    }

    public List<Farmer> getAllFarmers() {
        return farmers;
    }
}

// Farmer class
class Farmer {
    private String name;
    private String aadhar;
    private String phone;
    private String dob;
    private String email;
    private double annualIncome;
    private String bankNumber;
    private String equipmentLoan;
    private String landDetails;
    private String pincode;
    private String crops;
    private String password;

    public Farmer(String name, String aadhar, String phone, String dob, String email, double annualIncome, String bankNumber, String equipmentLoan, String landDetails, String pincode, String crops, String password) {
        this.name = name;
        this.aadhar = aadhar;
        this.phone = phone;
        this.dob = dob;
        this.email = email;
        this.annualIncome = annualIncome;
        this.bankNumber = bankNumber;
        this.equipmentLoan = equipmentLoan;
        this.landDetails = landDetails;
        this.pincode = pincode;
        this.crops = crops;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getAadhar() {
        return aadhar;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public String getCrops() {
        return crops;
    }
}

// Query class
class Query {
    private String queryText;
    private String response;
    private String farmerName;

    public Query(String queryText, String farmerName) {
        this.queryText = queryText;
        this.response = null; // Initially the response is null
        this.farmerName = farmerName;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public String getFarmerName() {
        return farmerName;
    }
}
