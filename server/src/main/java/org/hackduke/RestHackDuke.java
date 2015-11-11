package org.hackduke;

import org.hackduke.db.ItemDb;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class RestHackDuke {

    @RequestMapping(value = "/items", produces = {"application/json"})
    public List<Map<String, Object>> items() {
        List<Map<String, Object>> items = new LinkedList<>();

        ItemDb itemDb = new ItemDb();
        if (itemDb.loadByFoodBankId(1)) {
            do {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", itemDb.getName());
                map.put("id", itemDb.getId());
                items.add(map);
            } while (itemDb.next());
        }
        itemDb.close();

        return items;
    }

    @RequestMapping(value = "/additemrest", method = RequestMethod.POST)
    public void addItem(@RequestParam("name") String itemName, @RequestParam("qty") int qty) {
        ItemDb itemDb = new ItemDb();
        itemDb.setFoodBankId(1);
        itemDb.setName(itemName);
        itemDb.setQuantity(qty);
        itemDb.insert();
        itemDb.close();
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String handleFileUpload(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("/tmp/test.jpg")));
                stream.write(bytes);
                stream.close();

                Runtime.getRuntime().exec("convert -type grayscale -brightness-contrast 5x35% /tmp/test.jpg /tmp/test-processed.jpg").waitFor();
                Process p = Runtime.getRuntime().exec("/home/ubuntu/a.out /tmp/test-processed.jpg");

                StringBuilder result = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String in;
                while ((in = reader.readLine()) != null) {
                    result.append(in);
                }
                String thing = result.toString().replaceAll(" ", "").toUpperCase();
                System.out.println(thing);
                Pattern pattern = Pattern.compile("ORN");
                Matcher matcher = pattern.matcher(thing);
                int corn = 0;
                while (matcher.find()) {
                    corn++;
                }
                pattern = Pattern.compile("EA");
                matcher = pattern.matcher(thing);
                int peas = 0;
                while (matcher.find()) {
                    peas++;
                }
                pattern = Pattern.compile("SLICE|CARROT");
                matcher = pattern.matcher(thing);
                int carrots = matcher.find() ? 1 : 0;

                return "{\"peas\":" + peas + ",\"carrots\":" + carrots + ",\"corn\":" + corn + "}";
            } catch (Exception e) {
                e.printStackTrace();
                return "You failed to upload => " + e.getMessage();
            }
        } else {
            return "You failed to upload because the file was empty.";
        }
    }
}
