package org.hackduke.db;

import java.sql.SQLException;

public class FoodBankDb extends BaseDb {

    private int id;
    private String address;
    private String zipCode;
    private String name;

    public boolean loadAll() {
        return executeQuery("SELECT * FROM food_bank");
    }

    public boolean loadById(int id) {
        return executeQuery("SELECT * FROM food_bank WHERE `id`=" + id);
    }

    public void insert() {
        executeUpdate("INSERT INTO food_bank (`address`, `zip_code`, `name`) VALUES (\"" + address + "\",\"" + zipCode + "\",\"" + name + "\")");
    }

    @Override
    protected void fetch() throws SQLException {
        id = result.getInt("id");
        address = result.getString("address");
        zipCode = result.getString("zip_code");
        name = result.getString("name");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
