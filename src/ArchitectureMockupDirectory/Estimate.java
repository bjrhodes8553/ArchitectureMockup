/*
Estimate class to create an Estimate object containing all the information
needed to create an estimate for the client.
The ESTIMATE table in the database takes this object as an argument when storing
the estimate information into the table.
Stored into database using method: Database_Accessor.addEstimate(Estimate estimate)
 */

package ArchitectureMockupDirectory;
import java.time.LocalDate;

public class Estimate {
  int estimate_id;
  String client;
  String address;
  String product;
  String estimator;
  double budget;
  LocalDate date;
  double material_cost;
  double labor_cost;
  double total_cost;

  public Estimate(int estimate_id, String client, String address, String product,
      String estimator, double budget, LocalDate date, double material_cost, double labor_cost,
      double total_cost) {
    this.estimate_id = estimate_id;
    this.client = client;
    this.address = address;
    this.product = product;
    this.estimator = estimator;
    this.budget = budget;
    this.date = date;
    this.material_cost = material_cost;
    this.labor_cost = labor_cost;
    this.total_cost = total_cost;
  }

  public Estimate(String client, String address, String product, String estimator, double budget,
      LocalDate date, double material_cost, double labor_cost, double total_cost) {
    this.client = client;
    this.address = address;
    this.product = product;
    this.estimator = estimator;
    this.budget = budget;
    this.date = date;
    this.material_cost = material_cost;
    this.labor_cost = labor_cost;
    this.total_cost = total_cost;
  }

  public int getEstimate_id() {
    return estimate_id;
  }

  public void setEstimate_id(int estimate_id) {
    this.estimate_id = estimate_id;
  }

  public String getClient() {
    return client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public String getEstimator() {
    return estimator;
  }

  public void setEstimator(String estimator) {
    this.estimator = estimator;
  }

  public double getBudget() {
    return budget;
  }

  public void setBudget(double budget) {
    this.budget = budget;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public double getMaterial_cost() {
    return material_cost;
  }

  public void setMaterial_cost(double material_cost) {
    this.material_cost = material_cost;
  }

  public double getLabor_cost() {
    return labor_cost;
  }

  public void setLabor_cost(double labor_cost) {
    this.labor_cost = labor_cost;
  }

  public double getTotal_cost() {
    return total_cost;
  }

  public void setTotal_cost(double total_cost) {
    this.total_cost = total_cost;
  }
}
