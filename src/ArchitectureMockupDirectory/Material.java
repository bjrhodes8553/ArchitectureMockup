/*
Material object created so that it can be stored into the STEEL_MATERIALS table.
Sub-classes may be created later on, depending on if Chris requires multiple types of
materials (e.g. WOOD_MATERIALS)
Material stored into the database using method: Database_Accessor.addMaterial(Material material)
 */


package ArchitectureMockupDirectory;

public class Material {
  int material_id;
  String material_name;
  double material_cost;
  int material_quantity;

  public int getMaterial_quantity() {
    return material_quantity;
  }

  public void setMaterial_quantity(int material_quantity) {
    this.material_quantity = material_quantity;
  }

  public Material(String material_name, double material_cost) {
    this.material_name = material_name;
    this.material_cost = material_cost;
  }

  public int getMaterial_id() {
    return material_id;
  }

  public void setMaterial_id(int material_id) {
    this.material_id = material_id;
  }

  public String getMaterial_name() {
    return material_name;
  }

  public void setMaterial_name(String material_name) {
    this.material_name = material_name;
  }

  public double getMaterial_cost() {
    return material_cost;
  }

  public void setMaterial_cost(double material_cost) {
    this.material_cost = material_cost;
  }
}
