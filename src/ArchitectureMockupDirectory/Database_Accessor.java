package ArchitectureMockupDirectory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Database_Accessor {
  private static String JDBC_DRIVER = "org.h2.Driver";
  private final static String DB_URL =  "jdbc:h2:C:/Users/feesh/Desktop/Fall 20/Architecture/res/HarmonicEnvironments";
  private final static String pass = "";
  private final static String user = "";
  private static Connection conn = null;
  private static Statement stmt = null;



  public static Estimate getEstimate(String clientName) {
    Estimate currentEstimate = new Estimate("", " ",
        "", "",0, LocalDate.now(),
        0, 0, 0);
    try {
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();
      String sql = "SELECT * FROM ESTIMATE WHERE CLIENT='" + clientName + "'";
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        currentEstimate = new Estimate(
            rs.getInt(1),
            rs.getString(2),
            rs.getString(3),
            rs.getString(4),
            rs.getString(5),
            Double.parseDouble(rs.getString(6)),
            LocalDate.parse(rs.getString(7)),
            Double.parseDouble(rs.getString(8)),
            Double.parseDouble(rs.getString(9)),
            Double.parseDouble(rs.getString(10)));
      } else {
        System.out.println("No estimate was found");
      }
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return currentEstimate;
  }

  public static void addMaterial(Material material) {
    try {
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();
      String sql =
          "INSERT INTO STEEL_MATERIALS(material_name, material_cost)"
              + "VALUES('"
              + material.getMaterial_name()
              + "','"
              + material.getMaterial_cost()
              + "');";
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public static void addLabor(Labor labor) {
    try {
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();
      String sql =
          "INSERT INTO LABOR(LABOR_SERVICE, LABOR_COST_PER_HOUR)"
              + "VALUES('"
              + labor.getLabor_service()
              + "','"
              + labor.getLabor_cost_per_hour()
              + "');";
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public static void addEstimate(Estimate estimate) {
    try {
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();
      String sql =
          "INSERT INTO ESTIMATE(CLIENT, ADDRESS, PRODUCT, ESTIMATOR, BUDGET, "
              + "DATE_OF_ESTIMATE, MATERIAL_COST, LABOR_COST, TOTAL_COST)"
              + "VALUES('"
              + estimate.getClient()
              + "','"
              + estimate.getAddress()
              + "','"
              + estimate.getProduct()
              + "','"
              + estimate.getEstimator()
              + "','"
              + estimate.getBudget()
              + "','"
              + estimate.getDate().toString()
              + "','"
              + estimate.getMaterial_cost()
              + "','"
              + estimate.getLabor_cost()
              + "','"
              + estimate.getTotal_cost()
              + "');";
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public static void updateEstimateWithMaterials(double materialCost){
    Connection conn = null;
    Statement stmt = null;
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, user, pass);

      stmt = conn.createStatement();
      String sql =
          "UPDATE ESTIMATE "
              + " SET MATERIAL_COST = '" + materialCost + "'"
              + " WHERE ESTIMATE_ID = (SELECT max(ESTIMATE_ID)from ESTIMATE)";
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void updateEstimateWithLabor(double laborCost){
    Connection conn = null;
    Statement stmt = null;
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, user, pass);

      stmt = conn.createStatement();
      String sql =
          "UPDATE ESTIMATE "
              + " SET LABOR_COST = '" + laborCost + "'"
              + " WHERE ESTIMATE_ID = (SELECT max(ESTIMATE_ID)from ESTIMATE)";
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void updateEstimateWithTotal(double total){
    Connection conn = null;
    Statement stmt = null;
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, user, pass);

      stmt = conn.createStatement();
      String sql =
          "UPDATE ESTIMATE "
              + " SET TOTAL_COST = '" + total + "'"
              + " WHERE ESTIMATE_ID = (SELECT max(ESTIMATE_ID)from ESTIMATE)";
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public static ArrayList<Material> getMaterials() {
    ArrayList<Material> materials = new ArrayList<>();
    try {
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();
      String sql = "SELECT * FROM STEEL_MATERIALS";
      ResultSet rs = stmt.executeQuery(sql);
      while(rs.next()) {
        materials.add(
            new Material(rs.getString(2),
            Double.parseDouble(rs.getString(3))));

      }
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return materials;
  }

  public static ArrayList<Labor> getLabor() {
    ArrayList<Labor> laborList = new ArrayList<>();
    try {
      conn = DriverManager.getConnection(DB_URL, user, pass);
      stmt = conn.createStatement();
      String sql = "SELECT * FROM LABOR";
      ResultSet rs = stmt.executeQuery(sql);
      while(rs.next()) {
        laborList.add(
            new Labor(rs.getString(2),
                Double.parseDouble(rs.getString(3))));

      }
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return laborList;
  }











}


