package org.hackduke;

import org.hackduke.db.ItemDb;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class HelloWorldController {

    @RequestMapping("/add_item")
    public String addItem(@RequestParam("item_name") String itemName) {
        ItemDb itemDb = new ItemDb();
        itemDb.setFoodBankId(1);
        itemDb.setName(itemName);
        itemDb.setQuantity(0);
        itemDb.insert();
        itemDb.close();
        return "redirect:/dashboard";
    }

    @RequestMapping("/delete_item")
    public String deleteItem(@RequestParam("item") int id) {
        ItemDb itemDb = new ItemDb();
        itemDb.setId(id);
        itemDb.delete();
        itemDb.close();
        return "redirect:/dashboard";
    }

    @RequestMapping("/qty_item")
    public String updateItem(@RequestParam("item") int id, @RequestParam("qty") int qty) {
        ItemDb itemDb = new ItemDb();
        itemDb.loadById(id);
        itemDb.setQuantity(itemDb.getQuantity() + qty);
        itemDb.updateQuantity();
        itemDb.close();
        return "redirect:/dashboard";
    }

    @RequestMapping("/dashboard")
    public String greeting(Model model) {
        List<Item> items = new LinkedList<>();
        List<Map<String, Object>> topTen = new LinkedList<>();

        ItemDb itemDb = new ItemDb();
        if (itemDb.loadByFoodBankId(1)) {
            do {
                Item item = new Item();
                item.id = itemDb.getId();
                item.name = itemDb.getName();
                item.quantity = itemDb.getQuantity();
                items.add(item);
            } while (itemDb.next());
        }
        if (itemDb.loadTopTen()) {
            do {
                HashMap<String, Object> map = new HashMap<>();
                map.put("label", itemDb.getName());
                map.put("data", itemDb.getQuantity());
                topTen.add(map);
            } while (itemDb.next());
        }
        itemDb.close();

        int totalQty = 0;
        int qtyInPie = 0;

        for (Map<String, Object> it : topTen) {
            qtyInPie += (Integer) it.get("data");
        }
        for (Item it : items) {
            totalQty += it.quantity;
        }
        HashMap<String, Object> other = new HashMap<>();
        other.put("label", "Other");
        other.put("data", totalQty - qtyInPie);
        topTen.add(other);

        model.addAttribute("topten", topTen);
        model.addAttribute("items", items);
        model.addAttribute("orgname", "Atlanta Community Food Bank");
        return "tables";
    }

    private static class Item {
        public int id;
        public String name;
        public int quantity;
    }
}
