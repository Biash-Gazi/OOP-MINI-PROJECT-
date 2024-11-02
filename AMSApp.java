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
        VBox loginOptions = new VBox(10);
        Button farmerLoginBtn = new Button("Farmer Login");
        Button govLoginBtn = new Button("Government Official Login");

        farmerLoginBtn.setOnAction(e -> showFarmerLoginPage());
        govLoginBtn.setOnAction(e -> showGovernmentOfficialPage());

        loginOptions.getChildren().addAll(farmerLoginBtn, govLoginBtn);

        Scene scene = new Scene(loginOptions, 400, 200);
        primaryStage.setScene(scene);
    }

    // Farmer Login Page
    private void showFarmerLoginPage() {
        VBox farmerLogin = new VBox(10);
        TextField nameField = new TextField("Name");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button signInBtn = new Button("Sign In");
        Button registerBtn = new Button("Register");
        Button backBtn = new Button("Back");

        signInBtn.setOnAction(e -> {
            Farmer farmer = database.getFarmerByName(nameField.getText());
            if (farmer != null && farmer.getPassword().equals(passwordField.getText())) {
                showFarmerMenu(farmer);
            } else {
                System.out.println("Invalid name or password!");
            }
        });
        registerBtn.setOnAction(e -> showFarmerRegistrationPage());
        backBtn.setOnAction(e -> showMainMenu());

        farmerLogin.getChildren().addAll(nameField, passwordField, signInBtn, registerBtn, backBtn);
        Scene scene = new Scene(farmerLogin, 400, 200);
        primaryStage.setScene(scene);
    }

    // Farmer Registration Page
    private void showFarmerRegistrationPage() {
        VBox registerForm = new VBox(10);
        TextField nameField = new TextField("Name");
        TextField aadharField = new TextField("Aadhar");
        TextField phoneField = new TextField("Phone");
        TextField dobField = new TextField("DOB");
        TextField emailField = new TextField("Email");
        TextField incomeField = new TextField("Annual Income");
        TextField bankField = new TextField("Bank Number");
        TextField loanField = new TextField("Equipment Loan");
        TextField landField = new TextField("Land Details");
        TextField pincodeField = new TextField("Pincode");
        TextField cropsField = new TextField("Crops");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button submitBtn = new Button("Register");
        Button backBtn = new Button("Back");

        submitBtn.setOnAction(e -> {
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
            showFarmerLoginPage();
        });
        backBtn.setOnAction(e -> showFarmerLoginPage());

        registerForm.getChildren().addAll(nameField, aadharField, phoneField, dobField, emailField,
                incomeField, bankField, loanField, landField, pincodeField, cropsField, passwordField, submitBtn, backBtn);
        Scene scene = new Scene(registerForm, 400, 500);
        primaryStage.setScene(scene);
    }

    // Farmer Menu
    private void showFarmerMenu(Farmer farmer) {
        VBox farmerMenu = new VBox(10);
        Button realTimeDataBtn = new Button("Display Real-Time Data");
        Button queryBtn = new Button("Queries Menu");
        Button cropDetailsBtn = new Button("Enter Crop Production Details");
        Button backBtn = new Button("Back");

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
        VBox queryMenu = new VBox(10);
        Button viewResponsesBtn = new Button("View Responses to Queries");
        Button newQueryBtn = new Button("Submit a New Query");
        Button backBtn = new Button("Back");

        viewResponsesBtn.setOnAction(e -> viewQueryResponses(farmer));
        newQueryBtn.setOnAction(e -> enterFarmerQuery(farmer));
        backBtn.setOnAction(e -> showFarmerMenu(farmer));

        queryMenu.getChildren().addAll(viewResponsesBtn, newQueryBtn, backBtn);
        Scene scene = new Scene(queryMenu, 400, 300);
        primaryStage.setScene(scene);
    }

    // View Responses to Queries
    private void viewQueryResponses(Farmer farmer) {
        VBox responsesView = new VBox(10);
        Label responsesLabel = new Label("Responses to Your Queries:");

        for (Query query : farmerQueries) {
            if (query.getFarmerName().equals(farmer.getName()) && query.getResponse() != null) {
                responsesView.getChildren().add(new Label("Query: " + query.getQueryText() + " - Response: " + query.getResponse()));
            }
        }

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> showFarmerQueryMenu(farmer));

        responsesView.getChildren().add(backBtn);
        Scene scene = new Scene(responsesView, 400, 300);
        primaryStage.setScene(scene);
    }

    // Government Official Page
    private void showGovernmentOfficialPage() {
        VBox govMenu = new VBox(10);
        Button manageSubsidiesBtn = new Button("Manage Subsidies and Resources");
        Button updateRealTimeDataBtn = new Button("Update Real-Time Data");
        Button respondQueriesBtn = new Button("Respond to Farmer Queries");
        Button displayFarmerDetailsBtn = new Button("Display Farmer Details");
        Button backBtn = new Button("Back");

        manageSubsidiesBtn.setOnAction(e -> manageSubsidies());
        updateRealTimeDataBtn.setOnAction(e -> updateRealTimeData());
        respondQueriesBtn.setOnAction(e -> respondToQueries());
        displayFarmerDetailsBtn.setOnAction(e -> displayFarmerDetails());
        backBtn.setOnAction(e -> showMainMenu());

        govMenu.getChildren().addAll(manageSubsidiesBtn, updateRealTimeDataBtn, respondQueriesBtn, displayFarmerDetailsBtn, backBtn);
        Scene scene = new Scene(govMenu, 400, 300);
        primaryStage.setScene(scene);
    }

    private void displayRealTimeData() {
        System.out.println("Real-Time Data: " + realTimeData);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Real-Time Data: " + realTimeData);
        alert.showAndWait();
    }

    private void enterFarmerQuery(Farmer farmer) {
        VBox queryBox = new VBox(10);
        TextArea queryArea = new TextArea("Enter your query here...");
        Button submitQueryBtn = new Button("Submit Query");
        Button backBtn = new Button("Back");

        submitQueryBtn.setOnAction(e -> {
            Query query = new Query(queryArea.getText(), farmer.getName());
            farmerQueries.add(query);
            System.out.println("Query Submitted: " + queryArea.getText());
            showFarmerMenu(farmer); // Navigate to the farmer menu
        });
        backBtn.setOnAction(e -> showFarmerMenu(farmer));

        queryBox.getChildren().addAll(queryArea, submitQueryBtn, backBtn);
        Scene scene = new Scene(queryBox, 400, 300);
        primaryStage.setScene(scene);
    }

    private void enterCropDetails() {
        VBox cropBox = new VBox(10);
        TextField cropTypeField = new TextField("Crop Type");
        TextField quantityField = new TextField("Quantity Produced");
        Button submitCropDetailsBtn = new Button("Submit Crop Details");
        Button backBtn = new Button("Back");

        submitCropDetailsBtn.setOnAction(e -> {
            // Handle crop detail submission logic here
            System.out.println("Crop Type: " + cropTypeField.getText() + ", Quantity: " + quantityField.getText());
            showFarmerMenu(database.getFarmerByName(cropTypeField.getText())); // Navigate to the farmer menu
        });
        backBtn.setOnAction(e -> showFarmerMenu(database.getFarmerByName(cropTypeField.getText())));

        cropBox.getChildren().addAll(cropTypeField, quantityField, submitCropDetailsBtn, backBtn);
        Scene scene = new Scene(cropBox, 400, 300);
        primaryStage.setScene(scene);
    }

    private void respondToQueries() {
        VBox responseBox = new VBox(10);
        Label instructionLabel = new Label("Select a Query to Respond:");
        ListView<String> queryListView = new ListView<>();

        // Populate the ListView with unanswered queries from farmers
        for (Query query : farmerQueries) {
            if (query.getResponse() == null) {
                queryListView.getItems().add(query.getQueryText());
            }
        }

        TextArea responseArea = new TextArea("Type your response here...");
        Button submitResponseBtn = new Button("Submit Response");
        Button backBtn = new Button("Back");

        submitResponseBtn.setOnAction(e -> {
            String selectedQueryText = queryListView.getSelectionModel().getSelectedItem();
            if (selectedQueryText != null) {
                for (Query query : farmerQueries) {
                    if (query.getQueryText().equals(selectedQueryText)) {
                        query.setResponse(responseArea.getText());
                        System.out.println("Response submitted for query: " + selectedQueryText);
                        break;
                    }
                }
            } else {
                System.out.println("No query selected!");
            }
        });

        backBtn.setOnAction(e -> showGovernmentOfficialPage());

        responseBox.getChildren().addAll(instructionLabel, queryListView, responseArea, submitResponseBtn, backBtn);
        Scene scene = new Scene(responseBox, 400, 300);
        primaryStage.setScene(scene);
    }

    private void manageSubsidies() {
        // Simple demonstration of subsidy management
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Subsidy Management: Not implemented yet.");
        alert.showAndWait();
    }

    private void updateRealTimeData() {
        // Simple demonstration of updating real-time data
        TextInputDialog dialog = new TextInputDialog(realTimeData);
        dialog.setTitle("Update Real-Time Data");
        dialog.setHeaderText("Enter new real-time data:");
        dialog.setContentText("Data:");

        dialog.showAndWait().ifPresent(newData -> {
            realTimeData = newData;
            System.out.println("Real-Time Data Updated: " + realTimeData);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Real-Time Data Updated!");
            alert.showAndWait();
        });
    }

    private void displayFarmerDetails() {
        VBox farmerDetailsBox = new VBox(10);
        Label detailsLabel = new Label("List of Farmers:");

        ListView<String> farmerListView = new ListView<>();
        for (Farmer farmer : database.getAllFarmers()) {
            farmerListView.getItems().add(farmer.getName() + " - Aadhar: " + farmer.getAadhar() +
                    ", Phone: " + farmer.getPhone() + ", Email: " + farmer.getEmail() +
                    ", Annual Income: " + farmer.getAnnualIncome() + ", Crops: " + farmer.getCrops());
        }

        Button backBtn = new Button("Back");
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

    // Getters for other fields can be added here
}

// Query class
class Query {
    private String queryText;
    private String response;
    private String farmerName;

    public Query(String queryText, String farmerName) {
        this.queryText = queryText;
        this.response = null; // Initially, the response is null
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
