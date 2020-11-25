package ArchitectureMockupDirectory;

public class Labor {
  int labor_id;
  String labor_service;
  double labor_cost_per_hour;
  double labor_hours;

  public double getLabor_hours() {
    return labor_hours;
  }

  public void setLabor_hours(double labor_hours) {
    this.labor_hours = labor_hours;
  }

  public int getLabor_id() {
    return labor_id;
  }

  public void setLabor_id(int labor_id) {
    this.labor_id = labor_id;
  }

  public Labor(String labor_service, double labor_cost_per_hour) {
    this.labor_service = labor_service;
    this.labor_cost_per_hour = labor_cost_per_hour;
  }

  public String getLabor_service() {
    return labor_service;
  }

  public void setLabor_service(String labor_service) {
    this.labor_service = labor_service;
  }

  public double getLabor_cost_per_hour() {
    return labor_cost_per_hour;
  }

  public void setLabor_cost_per_hour(double labor_cost_per_hour) {
    this.labor_cost_per_hour = labor_cost_per_hour;
  }
}
