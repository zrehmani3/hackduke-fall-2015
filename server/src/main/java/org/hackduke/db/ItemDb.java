package org.hackduke.db;

import java.sql.SQLException;

public class ItemDb extends BaseDb {

    private int id;
    private int foodBankId;
    private String name;
    private int quantity;

    public boolean loadAll() {
        return executeQuery("SELECT * FROM item");
    }

    public boolean loadById(int id) {
        return executeQuery("SELECT * FROM item WHERE `id`=" + id);
    }

    public boolean loadByFoodBankId(int id) {
        return executeQuery("SELECT * FROM item WHERE `food_bank_id`=" + id);
    }

    public boolean loadTopTen() {
        return executeQuery("SELECT * FROM item ORDER BY quantity DESC LIMIT 10");
    }

    public void insert() {
        executeUpdate("INSERT INTO item (`food_bank_id`, `name`, `quantity`) VALUES (\"" + foodBankId + "\",\"" + name + "\",\"" + quantity + "\")");
    }

    public void updateQuantity() {
        executeUpdate("UPDATE item SET quantity=\"" + quantity + "\" WHERE `id`=\"" + id + "\"");
    }

    public void delete() {
        executeUpdate("DELETE FROM item WHERE `id`=" + id);
    }

    @Override
    protected void fetch() throws SQLException {
        id = result.getInt("id");
        foodBankId = result.getInt("food_bank_id");
        name = result.getString("name");
        quantity = result.getInt("quantity");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFoodBankId() {
        return foodBankId;
    }

    public void setFoodBankId(int foodBankId) {
        this.foodBankId = foodBankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
