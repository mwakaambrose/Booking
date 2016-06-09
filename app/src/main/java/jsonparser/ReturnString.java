package jsonparser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReturnString {
    public static String[] roomDescriptions;
    public static String[] parseData(String s){
        String[] items = null;
        roomDescriptions = null;
        try{
            JSONArray jsonArray = new JSONArray(s);
            items = new String[jsonArray.length()];
            roomDescriptions = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                items[i] = object.getString("roomtype")+"\t\t"+object.getString("price")+"\n"+object.getString("hotel");
                roomDescriptions[i] = object.getString("description");
            }
            return items;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
