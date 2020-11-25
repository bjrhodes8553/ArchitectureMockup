package ArchitectureMockupDirectory;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
  private TableColumn<?, ?> col_material_name;

  @FXML
  private TableColumn<?, ?> col_material_cost;

  @FXML
  private Button button_add_material;

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




  public void initialize(){
    col_material_name.setCellValueFactory(new PropertyValueFactory<>("material_name"));
    col_material_cost.setCellValueFactory(new PropertyValueFactory<>("material_cost"));
    tableview_materials.getItems().addAll(Database_Accessor.getMaterials());


    col_labor_name.setCellValueFactory(new PropertyValueFactory<>("labor_service"));
    col_labor_cost.setCellValueFactory(new PropertyValueFactory<>("labor_cost_per_hour"));
    ArrayList<Labor> laborList = new ArrayList<>();
   tableview_labor.getItems().addAll(Database_Accessor.getLabor());


    col_added_material_name.setCellValueFactory(new PropertyValueFactory<>("material_name"));
    col_material_quantity.setCellValueFactory(new PropertyValueFactory<>("material_quantity"));

    col_added_labor.setCellValueFactory(new PropertyValueFactory<>("labor_service"));
    col_num_of_hours.setCellValueFactory(new PropertyValueFactory<>("labor_hours"));

}
  @FXML
  void generate_estimate(MouseEvent event) {
    String client = textfield_clientName.getText();
    double total_cost = totalEstimate(client);
    double difference_of_estimate_and_budget;
    Database_Accessor.updateEstimateWithTotal(total_cost);
    Estimate currentEstimate = new Estimate("", " ",
        "", "",0, LocalDate.now(),
        0, 0, 0);
    currentEstimate = Database_Accessor.getEstimate(client);
    difference_of_estimate_and_budget = currentEstimate.getBudget()-currentEstimate.getTotal_cost();
    textarea_estimate_report.appendText("ESTIMATE REPORT FOR "+ client + ", "
        + "\nFOR THE PRODUCTION OF "+ currentEstimate.getProduct() +
        "\n TOTAL COST OF MATERIALS: $"+ currentEstimate.getMaterial_cost()+
        "\nTOTAL COST OF LABOR: $"+ currentEstimate.getLabor_cost()+
        "\nTOTAL ESTIMATED COST: $"+ currentEstimate.getTotal_cost());

  }

  @FXML
  void add_labor(MouseEvent event) {
    ArrayList<Labor> addLaborList = new ArrayList<>();
    double sum_of_labor =0;
    double hours_times_cost = 0.0;
    double hours = Double.parseDouble(textfield_num_of_hours.getText());
    Labor addLabor = tableview_labor.getSelectionModel().getSelectedItem();
    addLaborList.add(addLabor);
    tableview_added_labor.getItems().clear();
    tableview_added_labor.getItems().addAll(addLaborList);
    for(Labor x: addLaborList){
      hours_times_cost = x.getLabor_cost_per_hour()*hours;
      sum_of_labor= sum_of_labor+hours_times_cost;
      x.setLabor_hours(hours);
    }
    Database_Accessor.updateEstimateWithLabor(sum_of_labor);

  }

  @FXML
  void add_material(MouseEvent event) {
    ArrayList<Material> addMaterialsList = new ArrayList<>();
    double sum_of_materials =0;
    double quantity_times_cost = 0.0;
    int quantity = Integer.parseInt(textfield_material_quantity.getText());
    Material addMaterial = tableview_materials.getSelectionModel().getSelectedItem();
    addMaterialsList.add(addMaterial);
    tableview_added_materials.getItems().clear();
    tableview_added_materials.getItems().addAll(addMaterialsList);
    for(Material x: addMaterialsList){
      quantity_times_cost = x.getMaterial_cost()*quantity;
      sum_of_materials= sum_of_materials+quantity_times_cost;
      x.setMaterial_quantity(quantity);

    }
    Database_Accessor.updateEstimateWithMaterials(sum_of_materials);

  }

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

  }

  public double totalEstimate(String client){
    Estimate currentEstimate = new Estimate("", " ",
        "", "",0, LocalDate.now(),
        0, 0, 0);
    currentEstimate = Database_Accessor.getEstimate(client);
    double material_cost = currentEstimate.getMaterial_cost();
    double labor_cost = currentEstimate.getLabor_cost();
    double total = material_cost+labor_cost;
    return total;
  }

  @FXML
  void remove_labor(MouseEvent event) {
    Labor removeLabor = tableview_added_labor.getSelectionModel().getSelectedItem();
    tableview_added_materials.getItems().remove(removeLabor);




  }

  @FXML
  void remove_material(MouseEvent event) {
    Material removeMaterial = tableview_added_materials.getSelectionModel().getSelectedItem();
    tableview_added_materials.getItems().remove(removeMaterial);

  }

}
