
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import permeagility.util.DatabaseConnection;
import permeagility.util.QueryResult;
import permeagility.web.Weblet;

/** Test type 4: Fortunes
 */
public class fortunes extends Weblet {

    @Override public String getPage(DatabaseConnection con, HashMap<String, String> parms) {
        QueryResult qr = con.query("SELECT FROM Fortune");
        ODocument newDoc = new ODocument().field("id",13).field("message","Additional fortune added at request time");
        qr.append(newDoc);

        // Sort the new list
        Collections.sort(qr.get(), new Comparator() {
            public int compare(Object o1, Object o2) {
                ODocument d1 = (ODocument)o1;
                ODocument d2 = (ODocument)o2;
                String m1 = d1.field("message");
                String m2 = d2.field("message");
                return m1.compareTo(m2);
            }
        });

        StringBuilder sb = new StringBuilder();
        for (int i=0;i<qr.size();i++) {
            String stringvalue = qr.getStringValue(i, "message");
            if (stringvalue != null) {
                stringvalue = stringvalue.replace("<", "&lt;"); // These can mess up the display
                stringvalue = stringvalue.replace(">", "&gt;");
            }

            sb.append(row(column(qr.getStringValue(i, "id"))+column(stringvalue)));
            //System.out.println("Fortune:"+qr.getStringValue(i, "message"));
        }

        return head("Fortunes")+body(table("sortable",row(columnHeader("id")+columnHeader("message"))+sb.toString()));
    }

}
