package org.hackduke;

import org.hackduke.db.ItemDb;
import org.hackduke.db.ItemLogDb;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class LifeController {

    private String timestampAge(int timestamp) {
        int delta = (int) (System.currentTimeMillis() / 1000) - timestamp;
        if (delta > 31536000) {
            return String.format("%.2f years", (delta / 31536000.0));
        } else if (delta > 2592000) {
            return String.format("%.2f months", (delta / 2592000.0));
        } else if (delta > 604800) {
            return String.format("%.2f weeks", (delta / 604800.0));
        } else if (delta > 86400.0) {
            return String.format("%.2f days", (delta / 86400.0));
        } else {
            return "< 1 day";
        }
    }

    @RequestMapping("/life")
    public String base(Model model) {
        List<Item> items = new LinkedList<>();
        Map<Integer, Integer> quantities = new HashMap<>();
        Map<Integer, Item> itemMap = new HashMap<>();

        ItemDb itemDb = new ItemDb();
        if (itemDb.loadByFoodBankId(1)) {
            do {
                Item item = new Item();
                item.id = itemDb.getId();
                item.name = itemDb.getName();
                item.quantity = itemDb.getQuantity();
                items.add(item);
                itemMap.put(item.id, item);
                quantities.put(item.id, item.quantity);
            } while (itemDb.next());
        }

        ItemLogDb itemLogDb = new ItemLogDb();
        if (itemLogDb.loadAll()) {
            do {
                try {
                    int qty = quantities.get(itemLogDb.getItemId());
                    if (qty > 0 && itemLogDb.getQuantity() > 0) {
                        qty -= itemLogDb.getQuantity();
                        itemMap.get(itemLogDb.getItemId()).age = timestampAge(itemLogDb.getTimestamp());
                    }
                    quantities.put(itemLogDb.getItemId(), qty);
                } catch (Exception ex) {
                }
            } while (itemLogDb.next());
        }

        model.addAttribute("items", items);
        model.addAttribute("orgname", "Atlanta Community Food Bank");
        return "life";
    }

    private static class Item {
        public int id;
        public String name;
        public int quantity;
        public String age;
    }
}
