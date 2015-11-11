package org.hackduke.db;

import java.sql.SQLException;

public class ItemLogDb extends BaseDb {

    private int id;
    private int item_id;
    private int quantity;
    private int timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return item_id;
    }

    public void setItemId(int item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    protected void fetch() throws SQLException {
        id = result.getInt("id");
        item_id = result.getInt("item_id");
        quantity = result.getInt("quantity");
        timestamp = result.getInt("timestamp");
    }

    public boolean loadByItemId(int id) {
        return executeQuery("SELECT * FROM item_log WHERE `id`=\"" + id + "\" ORDER BY `timestamp` DESC");
    }

    public boolean loadAll() {
        return executeQuery("SELECT * FROM item_log ORDER BY `timestamp` DESC");
    }

    public void insert() {
        executeUpdate("INSERT INTO item_log (`item_id`, `quantity`, `timestamp`) VALUES (\"" + item_id + "\",\"" + quantity + "\",\"" + timestamp + "\")");
    }
}