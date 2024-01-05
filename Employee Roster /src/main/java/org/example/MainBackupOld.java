package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;


public class MainBackupOld {
  public static void main(String[] args) throws IOException, URISyntaxException {
    // read in string
    URL url =  MainBackupOld.class.getResource("/demo.json");
    String data = new String(Files.readAllBytes(Paths.get(url.toURI())));

    //read as json array
    JSONArray jsonArray = new JSONArray(data);
    for (int i = 0; i < jsonArray.length(); i++) {

      // parse in json object
      JSONObject object = jsonArray.getJSONObject(i);

      // or

      String str  = jsonArray.get(i).toString();
      JSONObject object1 = new JSONObject(str);

      String name = object1.getString("firstname");
      String lastname = object1.getString("lastname");
      String phone = object1.getString("phone");
      int age = object1.getInt("age");


      System.out.println("First name: " + name);
      System.out.println("Last name: " + lastname);
      System.out.println("Age: " + age);
      System.out.println("Phone: " + phone);

    }

  }

}