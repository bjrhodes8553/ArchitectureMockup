package ArchitectureMockupDirectory;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.h2.engine.Database;

public class Controller {

  @FXML
  private TextField textfield_clientName;

  @FXML
  private TextField textfield_clientaddress;

  @FXML
  private TextField textfield_product;

  @FXML
  private TextField textfield_estimator;

  @FXML
  private Button button_generate_profile;

  @FXML
  private TextField textfield_budget;

  @FXML
  private TextField textfield_client_lookup;

  @FXML
  private TextField textfield_add_mat_dat;

  @FXML
  private TextField textfield_add_mat_cost;

  @FXML
  private TextField textfield_add_lab_dat;

  @FXML
  private TextField textfield_add_lab_cost;

  @FXML
  private TableColumn<?, ?> col_material_name;

  @FXML
  private TableColumn<?, ?> col_material_cost;

  @FXML
  private Button button_add_material;

  @FXML
  private Button button_search;

  @FXML
  private Button button_add_mat_dat;

  @FXML
  private Button button_add_lab_dat;

  @FXML
  private TextField textfield_material_quantity;

  @FXML
  private TableColumn<?, ?> col_added_material_name;

  @FXML
  private TableColumn<?, ?> col_material_quantity;

  @FXML
  private Button button_remove_material;

  @FXML
  private TableColumn<?, ?> col_labor_name;

  @FXML
  private TableColumn<?, ?> col_labor_cost;

  @FXML
  private TextField textfield_num_of_hours;

  @FXML
  private Button button_add_labor;

  @FXML
  private TableColumn<?, ?> col_added_labor;

  @FXML
  private TableColumn<?, ?> col_num_of_hours;

  @FXML
  private Button button_remove_labor;

  @FXML
  private TableView<Material> tableview_materials;

  @FXML
  private TableView<Material> tableview_added_materials;

  @FXML
  private TableView<Labor> tableview_labor;

  @FXML
  private TableView<Labor> tableview_added_labor;

  @FXML
  private TextArea textarea_estimate_report;

  @FXML
  private Button button_get_estimate;

  @FXML
  private Label label_estimate;

  @FXML
  private Label label_warning;


  public void initialize(){
    //Populates the tableview containing all the materials in the database
    col_material_name.setCellValueFactory(new PropertyValueFactory<>("material_name"));
    col_material_cost.setCellValueFactory(new PropertyValueFactory<>("material_cost"));
    tableview_materials.getItems().addAll(Database_Accessor.getMaterials());


    //Populates the tableview containing all the labor services in the database
    col_labor_name.setCellValueFactory(new PropertyValueFactory<>("labor_service"));
    col_labor_cost.setCellValueFactory(new PropertyValueFactory<>("labor_cost_per_hour"));
    tableview_labor.getItems().addAll(Database_Accessor.getLabor());

    //Populates the tablieview of the materials the clients project requires
    col_added_material_name.setCellValueFactory(new PropertyValueFactory<>("material_name"));
    col_material_quantity.setCellValueFactory(new PropertyValueFactory<>("material_quantity"));


    //Populates the tableview of the labor services the clients project requires
    col_added_labor.setCellValueFactory(new PropertyValueFactory<>("labor_service"));
    col_num_of_hours.setCellValueFactory(new PropertyValueFactory<>("labor_hours"));

}
  // Method that populates the textare in the tab "Generate Estimate"
  @FXML
  void generate_estimate(MouseEvent event) {
    // This looks like a paradox, but it is just updating the Main.currentEstimate object with updated
    // info from the database before generating an updated estimate.
    Main.currentEstimate = Database_Accessor.getEstimate(Main.currentEstimate.getClient());

    double total_cost = totalEstimate();
    double difference_of_estimate_and_budget;
    double budget = Main.currentEstimate.getBudget();

    difference_of_estimate_and_budget = budget-total_cost;
    textarea_estimate_report.clear();
    if(difference_of_estimate_and_budget>0){
      textarea_estimate_report.appendText("THIS PROJECT IS WITHIN THE CLIENTS BUDGET"
          + " WITH A SURPLUS OF: $"+ difference_of_estimate_and_budget+"\n\n");
      label_estimate.setTextFill(Color.web("#00a37f")); //label turns green (surplus)
    }else{
      System.out.println("THIS PROJECT IS NOT WITHIN THE CLIENTS BUDGET,"
          + " THERE IS AN OVERAGE OF: $" + difference_of_estimate_and_budget+ "\n");
      label_estimate.setTextFill(Color.web("#a32d00"));  //label turns red (overage)
    }
    textarea_estimate_report.appendText("CLIENT: "+ Main.currentEstimate.getClient() + " "
        + "\nFOR THE PRODUCTION OF: "+ Main.currentEstimate.getProduct() +
        "\nTOTAL COST OF MATERIALS: $"+ Main.currentEstimate.getMaterial_cost()+
        "\nTOTAL COST OF LABOR: $"+ Main.currentEstimate.getLabor_cost()+
        "\nCLIENTS BUDGET: $"+ Main.currentEstimate.getBudget()+
        "\nTOTAL ESTIMATED COST: $"+ Main.currentEstimate.getTotal_cost());

    String castTotalString = String.valueOf(total_cost);
    label_estimate.setTextFill(Color.web("#0076a3"));
    label_estimate.setText("$"+castTotalString);
  }

  //Method adds the (labor * # of hours) to clients estimate
  @FXML
  void add_labor(MouseEvent event) {
    ArrayList<Labor> addLaborList = new ArrayList<>();
    double sum_of_labor =0;
    double hours_times_cost = 0.0;
    double hours = Double.parseDouble(textfield_num_of_hours.getText());
    Labor addLabor = tableview_labor.getSelectionModel().getSelectedItem();
    addLaborList.add(addLabor);
    textfield_num_of_hours.clear();
    tableview_added_labor.getItems().clear();
    tableview_added_labor.getItems().addAll(addLaborList);
    for(Labor x: addLaborList){
      hours_times_cost = x.getLabor_cost_per_hour()*hours;
      sum_of_labor= sum_of_labor+hours_times_cost;
      x.setLabor_hours(hours);
    }
    Database_Accessor.updateEstimateWithLabor(sum_of_labor);
    Main.currentEstimate.setLabor_cost(sum_of_labor);

  }

  //Method adds the (material * quantity) selected and adds it to the clients estimate
  @FXML
  void add_material(MouseEvent event) {
    ArrayList<Material> addMaterialsList = new ArrayList<>();
    double sum_of_materials =0;
    double quantity_times_cost = 0.0;
    double current_material_amount = Main.currentEstimate.getMaterial_cost();
    double updatedMaterial = 0.0;
    int quantity = Integer.parseInt(textfield_material_quantity.getText());

    Material addMaterial = tableview_materials.getSelectionModel().getSelectedItem();
    addMaterialsList.add(addMaterial);

    tableview_added_materials.getItems().clear();
    tableview_added_materials.getItems().addAll(addMaterialsList);
    textfield_material_quantity.clear();
    for(Material x: addMaterialsList){
      quantity_times_cost = x.getMaterial_cost()*quantity;
      sum_of_materials= sum_of_materials+quantity_times_cost;
      x.setMaterial_quantity(quantity);

    }
    updatedMaterial = sum_of_materials+current_material_amount;
    Database_Accessor.updateEstimateWithMaterials(updatedMaterial);
    Main.currentEstimate.setMaterial_cost(updatedMaterial);

  }

  //Generates a profile for the client and stores their profile into the database.
  //Executes when "Generate Profile" button is pressed.
  @FXML
  void generate_profile(MouseEvent event) {
    String client = textfield_clientName.getText();
    String address = textfield_clientaddress.getText();
    String product = textfield_product.getText();
    String estimator = textfield_estimator.getText();
    double material_cost = 0;
    double labor_cost =0;
    double budget = Double.parseDouble(textfield_budget.getText());
    LocalDate date = LocalDate.now();
    double total_cost = 0;
    Estimate newEstimate = new Estimate(client, address, product, estimator, budget, date, material_cost, labor_cost, total_cost);
    Database_Accessor.addEstimate(newEstimate);
    Main.currentEstimate = newEstimate;
    if(newEstimate != null){
      label_warning.setTextFill(Color.web("#0076a3"));
      label_warning.setText("Profile Located");
    }
    textfield_clientName.clear();
    textfield_clientaddress.clear();
    textfield_product.clear();
    textfield_estimator.clear();
    textfield_budget.clear();
  }

  //Method totals the estimate based on the cost of the materials and labors
  public double totalEstimate(){
    double material_cost = Main.currentEstimate.getMaterial_cost();
    double labor_cost = Main.currentEstimate.getLabor_cost();
    double total = material_cost+labor_cost;
    Database_Accessor.updateEstimateWithTotal(total);
    Main.currentEstimate.setTotal_cost(total);
    return total;
  }

  @FXML
  void remove_labor(MouseEvent event) {
    double removeLaborAmount = 0.0;
    double currentLaborAmount = Database_Accessor.getEstimate(Main.currentEstimate.getClient()).getLabor_cost();
    double updateLaborAmount = 0.0;
    Labor removeLabor = tableview_added_labor.getSelectionModel().getSelectedItem();
    tableview_added_materials.getItems().remove(removeLabor);
    removeLaborAmount=(removeLabor.getLabor_cost_per_hour())*(removeLabor.getLabor_hours());
    updateLaborAmount = currentLaborAmount-removeLaborAmount;
    Database_Accessor.updateEstimateWithLabor(updateLaborAmount);
  }

  //Removes material from clients estimation
  @FXML
  void remove_material(MouseEvent event) {
    Material removeMaterial = tableview_added_materials.getSelectionModel().getSelectedItem();
    tableview_added_materials.getItems().remove(removeMaterial);
  }

  //Searches for the estimation entered by user on "Generate Profile" tab
  @FXML
  void search_for_estimate(MouseEvent event) {
    String client = textfield_client_lookup.getText();
    Main.currentEstimate = Database_Accessor.getEstimate(client);
    textfield_client_lookup.clear();
    if(Main.currentEstimate != null){
      label_warning.setTextFill(Color.web("#0076a3"));
      label_warning.setText("Profile Located");
    }

  }

  //Adds material to the database
  @FXML
  void add_material_to_database(MouseEvent event) {
    String materialName = textfield_add_mat_dat.getText();
    double materialCost = Double.parseDouble(textfield_add_mat_cost.getText());
    Material addMaterial = new Material(materialName, materialCost);
    tableview_materials.getItems().add(addMaterial);
    Database_Accessor.addMaterial(addMaterial);
    textfield_add_mat_dat.clear();
    textfield_add_mat_cost.clear();
  }

  //Add labor object to database into LABOR table
  @FXML
  void add_labor_to_database(MouseEvent event) {
    String laborName = textfield_add_lab_dat.getText();
    double laborCost = Double.parseDouble(textfield_add_lab_cost.getText());
    Labor addLabor = new Labor(laborName, laborCost);
    tableview_labor.getItems().add(addLabor);
    Database_Accessor.addLabor(addLabor);
    textfield_add_lab_dat.clear();
    textfield_add_lab_cost.clear();

  }

}
