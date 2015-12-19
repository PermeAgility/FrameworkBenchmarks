
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.HashMap;
import permeagility.util.DatabaseConnection;
import permeagility.util.Setup;
import permeagility.web.Weblet;

/**
 *
 * @author glenn
 */
public class dbSetup extends Weblet {

    @Override
    public String getPage(DatabaseConnection con, HashMap<String, String> parms) {
        StringBuilder messages = new StringBuilder();
        String submit = parms.get("SUBMIT");
        
        if (submit != null && submit.equals("BUILD_WORLD")) {
            messages.append("Building world...");
            OSchema oschema = con.getSchema();
            OClass worldTable = Setup.checkCreateTable(oschema, "World", messages);
            if (worldTable != null) {
                Setup.checkCreateColumn(con, worldTable, "id", OType.INTEGER, messages);
                Setup.checkCreateColumn(con, worldTable, "randomNumber", OType.INTEGER, messages);
            }
            //con.update("CREATE INDEX worldid ON World.id UNIQUE");
            long timeStart = System.currentTimeMillis();
            for (int i=1;i<=10000;i++) {
                ODocument d = con.create("World");
                d.field("id",i).field("randomNumber",Math.random()*10000+1);
                d.save();
            }
            messages.append("Created 10,000 random numbers in " + (System.currentTimeMillis()-timeStart) + "ms");
        }
        
        if (submit != null && submit.equals("BUILD_FORTUNE")) {
            messages.append("Building fortune...");
            OSchema oschema = con.getSchema();
            OClass fortuneTable = Setup.checkCreateTable(oschema, "Fortune", messages);
            if (fortuneTable != null) {
                Setup.checkCreateColumn(con, fortuneTable, "id", OType.INTEGER, messages);
                Setup.checkCreateColumn(con, fortuneTable, "message", OType.STRING, messages);
            }
            //con.update("CREATE INDEX worldid ON World.id UNIQUE");
            long timeStart = System.currentTimeMillis();
            con.create("Fortune").field("id",1).field("message","fortune: No such file or directory").save();
            con.create("Fortune").field("id",2).field("message","A computer scientist is someone who fixes things that aren't broken.").save();
            con.create("Fortune").field("id",3).field("message","After enough decimal places, nobody gives a damn.").save();
            con.create("Fortune").field("id",4).field("message","A bad random number generator: 1, 1, 1, 1, 1, 4.33e+67, 1, 1, 1").save();
            con.create("Fortune").field("id",5).field("message","A computer program does what you tell it to do, not what you want it to do.").save();
            con.create("Fortune").field("id",6).field("message","Emacs is a nice operating system, but I prefer UNIX. — Tom Christaensen").save();
            con.create("Fortune").field("id",7).field("message","Any program that runs right is obsolete.").save();
            con.create("Fortune").field("id",8).field("message","A list is only as strong as its weakest link. — Donald Knuth").save();
            con.create("Fortune").field("id",9).field("message","Feature: A bug with seniority.").save();
            con.create("Fortune").field("id",10).field("message","Computers make very fast, very accurate mistakes.").save();
            con.create("Fortune").field("id",11).field("message","<script>alert(\"This should not be displayed in a browser alert box.\");</script>").save();
            con.create("Fortune").field("id",12).field("message","フレームワークのベンチマーク").save();
            messages.append("Created 12 fortunes in " + (System.currentTimeMillis()-timeStart) + "ms");
        }
        
        return head("dbSetup")+body(standardLayout(con, parms, 
                paragraph("banner","dbSetup")
                +form(submitButton(con.getLocale(),"BUILD_WORLD"))
                +form(submitButton(con.getLocale(),"BUILD_FORTUNE"))
                +messages.toString()
                ));
    }
    
}
