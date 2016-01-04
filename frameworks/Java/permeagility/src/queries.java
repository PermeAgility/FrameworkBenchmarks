
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.HashMap;
import permeagility.plus.json.JSONArray;
import permeagility.plus.json.JSONObject;
import permeagility.util.DatabaseConnection;
import permeagility.web.Download;

/** Test type 3: Multiple Database Queries
 */
public class queries extends Download {

    @Override public String getContentType() { return "application/json"; }
    
    @Override public String getContentDisposition() { return null; }
    
    @Override public byte[] getBytes(DatabaseConnection con, HashMap<String, String> parms) {
         String q = parms.get("queries");
        int qn = -1;
        try { 
            qn = Integer.parseInt(q);
        } catch (Exception e) {
        }
        if (qn < 1) qn = 500;

//        JSONArray ja = new JSONArray();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        String comma = "";
        for (int i=0; i<qn; i++) {
//            JSONObject jo = new JSONObject();
            ODocument d = con.queryDocument("SELECT FROM World WHERE id="+Math.random()*10000);
            if (d != null) {
                sb.append(comma+"{\"id\":"+d.field("id")+",\"randomNumber\":+"+d.field("randomNumber")+"}");
                if (comma.isEmpty()) comma = ",";
                //jo.put("id", );
                //jo.put("randomNumber", );
                //ja.put(jo);
            }
        }
//        return ja.toString().getBytes();
        sb.append("]");
        return sb.toString().getBytes();
    }

}
