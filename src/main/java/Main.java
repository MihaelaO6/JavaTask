import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;


class Comparator1 implements Comparator<JSONObject> {
    @Override
    public int compare(JSONObject obj1, JSONObject obj2){
        String first = " ";
        try {
            first = (String) obj1.get("name");
        }catch (JSONException e){
            e.printStackTrace();
        }
        String sec = " ";
        try {
            sec = (String) obj2.get("name");
        }catch (JSONException e){
            e.printStackTrace();
        }
        assert first !=null;
        assert sec !=null;
        return  first.compareTo(sec);
    }
}
public class Main {
    public static void print(String name,double grade,String desc) throws IOException,JSONException{
        System.out.print("Person name:" + name + "\n");
        System.out.print("    Grade: " + grade + "\n");
        System.out.print("    " + desc.substring(0, 10) + "...\n");

    }
    public static String getJson(String link) throws IOException, JSONException {
        InputStream inputStream = new URL(link).openStream();
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream));
            String lineText;
            String json = new String(" ");
            while ((lineText = bufReader.readLine()) != null) //read line by line
                json += lineText+"\n";
            return json;    // Returning json
        } catch (Exception e) {
            return null;
        } finally {
            inputStream.close();
        }
    }
    public static void main(String[] args) throws IOException, JSONException {
        String data = new String(Files.readAllBytes(Paths.get("./demo.json")));
        double total = 0.0,total1=0.0;
        int counterD = 0, counterI = 0;

        JSONArray jsonArray = new JSONArray(data);
        ArrayList<JSONObject> list1 = new ArrayList<>();

        for (int i = 0; i<jsonArray.length();i++) {
            String str  = jsonArray.get(i).toString();
            JSONObject obj = new JSONObject(str);
            list1.add(obj);
        }
        list1.sort(new Comparator1());
        jsonArray = new JSONArray(list1);
        System.out.print(". Student \n");
        for (int i = 0; i<jsonArray.length();i++){
            JSONObject object1 = new JSONObject(jsonArray.get(i).toString());
            String name = object1.getString("name");
            boolean student = object1.getBoolean("student");
            double grade = object1.getDouble("grades");
            String desc = object1.getString("description");
            if(student){
                print(name,grade,desc);
                total+=grade;
                counterD++;
            }
        }
        for (int i = 0; i<jsonArray.length();i++){
            JSONObject object1 = new JSONObject(jsonArray.get(i).toString());
            String name = object1.getString("name");
            boolean student = object1.getBoolean("student");
            double grade = object1.getDouble("grades");
            String desc = object1.getString("description");
            if(!student){
                print(name,grade,desc);
                total1+=grade;
            }
        }
        double av = total/counterD;
        System.out.print("Total students: $"+counterD+"\nNot students: $"+counterI+"\n");
        System.out.print("Avg grade: "+av);
    }
}