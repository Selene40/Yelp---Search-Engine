import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class populate {

    static Connection conn = null;
    static Statement stmt = null;

    public static void main(String[] args) throws Exception {
        createDBConnection();
        PreProcess.preProcess();
//        closeDBConnection();
    }

    public static void createDBConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection("jdbc:oracle:thin:Scott/tiger@localhost:1521:ss");
        conn.setAutoCommit(true);
        stmt = conn.createStatement();
    }

    public static void closeDBConnection() throws Exception {
        stmt.close();
        conn.close();
    }
}
class PreProcess {

    static Set<String> mainCatg = new HashSet<>();
    static Map<String, Set<String>> mainToSub = new HashMap<>();
    static Set<Object> attr = new HashSet<>();
    static Statement stmt = populate.stmt;
    static Connection conn = populate.conn;

    static void preProcess() throws Exception {
        //initMainCatg();
//        insertMainCatg();//1
//        initMainToSub();//1
//        insertBusnSubCatg();//1
        //insertBusnMainCatg();//no
        //insertBusnAttri();//no
//        insertAttribute();//1
//        insertSubCatg();//1
//        insertBusiness();//1
//        insertCheckin();//1
//        insertReviews();//1
    }

    private static void insertBusnMainCatg() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("YelpDataset/yelp_business.json"));
        String line;
        while ((line = br.readLine()) != null) {
            Object obj = new JSONParser().parse(line);
            JSONObject jsonObject = (JSONObject) obj;
            String bid = (String) jsonObject.get("business_id");
            // current line , all main catg
            JSONArray categories = (JSONArray) jsonObject.get("categories");
            Set<String> tmpMainCatg = new HashSet<>();
            for (int i = 0; i < categories.size(); i++) {
                String catg = (String) categories.get(i);
                if (mainCatg.contains(catg)) {
                    tmpMainCatg.add(catg);
                }
            }

            for (String mainCatg : tmpMainCatg) {
                String sql = String.format("INSERT INTO main_busi (bid, mcname) VALUES ('%s', '%s')", bid, mainCatg);
                populate.stmt.executeUpdate(sql);
            }
        }
    }

    private  static void insertBusnSubCatg() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("YelpDataset/yelp_business.json"));
        String line;
        while ((line = br.readLine()) != null) {
            Object obj = new JSONParser().parse(line);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray categories = (JSONArray) jsonObject.get("categories");
            String bid = (String) jsonObject.get("business_id");
            // sub catg in current line
            Set<String> tmpSubCatg = new HashSet<>();
            for (int i = 0; i < categories.size(); i++) {
                String catg = (String) categories.get(i);
                if (!mainCatg.contains(catg)) {
                    tmpSubCatg.add(catg);
                }
            }
            for (String subCatg : tmpSubCatg) {
                String formattedSubCatg = subCatg.replace("'", "''");
                String sql = String.format("INSERT INTO sub_busi (bid, scname) VALUES ('%s', '%s')", bid, formattedSubCatg);
                populate.stmt.executeUpdate(sql);
            }
        }
    }

    private static void insertBusnAttri() throws  Exception{
        BufferedReader br = new BufferedReader(new FileReader("YelpDataset/yelp_business.json"));
        String line;
        while ((line = br.readLine()) != null) {
            Object obj = new JSONParser().parse(line);
            JSONObject jsonObject = (JSONObject) obj;
            String bid = (String) jsonObject.get("business_id");
            JSONObject attributes = (JSONObject) jsonObject.get("attributes");
            Set<Object> tmpAttr = parseJSONObject(attributes);
            for (Object attr : tmpAttr) {
                String sql = String.format("INSERT INTO attri_busi (bid, avalue) VALUES ('%s', '%s')", bid, attr);
                populate.stmt.executeUpdate(sql);
            }
        }
    }

    private static void insertBusiness() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("YelpDataset/yelp_business.json"));
        String line;
        while ((line = br.readLine()) != null) {
            Object obj = new JSONParser().parse(line);
            JSONObject jsonObject = (JSONObject)obj;
            String bid = (String)jsonObject.get("business_id");
            String bname = ((String)jsonObject.get("name")).replace("'", "''");
            String addr = ((String)jsonObject.get("full_address")).replace("'", "''");
            String city = ((String)jsonObject.get("city")).replace("'", "''");
            String state = (String)jsonObject.get("state");
            Double stars = (Double)jsonObject.get("stars");
            Long numReviews = (Long)jsonObject.get("review_count");
            String type = "business";
            String sql = String.format("INSERT INTO Business (bid, bname, addr, city, state, stars, numReviews, type) VALUES ('%s', '%s', '%s', '%s', '%s', '%f', '%d', '%s')",
                    bid, bname, addr, city, state, stars, numReviews, type);

            try {
                stmt.executeUpdate(sql);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // populate BusinessHour
            JSONObject hours = (JSONObject)jsonObject.get("hours");
            for (Object day : hours.keySet()) {
                JSONObject hour = (JSONObject) hours.get(day);
                String open = (String) hour.get("open");
                String close = (String) hour.get("close");
                sql = String.format("INSERT INTO BusinessHour (bid, day, open, close) VALUES ('%s', '%s', '%s', '%s')",
                        bid, day, open, close);
                stmt.executeUpdate(sql);
            }
        }
    }

    private static void insertCheckin() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("YelpDataset/yelp_checkin.json"));
        String line;
        while ((line = br.readLine()) != null) {
            Object obj = new JSONParser().parse(line);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject checkinInfo = (JSONObject) jsonObject.get("checkin_info");
            String bid = (String) jsonObject.get("business_id");
            String type = "checkin";
            long sum = 0;
            for (Object time : checkinInfo.keySet()) {
                Long num = (Long) checkinInfo.get(time);
                sum += num;
            }
            String sql = String.format("UPDATE Business SET checkin_number = '%s' WHERE bid = '%s'", sum, bid);
            stmt.executeUpdate(sql);
        }
    }

    private static void insertReviews() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("YelpDataset/yelp_review.json"));
        String line;
        while ((line = br.readLine()) != null) {
            Object obj = new JSONParser().parse(line);
            JSONObject jsonObject = (JSONObject) obj;
            String type = "review";
            String userid = (String) jsonObject.get("user_id");
            String review_id = (String) jsonObject.get("review_id");
            Long stars = (Long) jsonObject.get("stars");
            String date = (String) jsonObject.get("date");
//            String text = ((String) jsonObject.get("text")).replace("'", "''");
            String business_id = (String) jsonObject.get("business_id");

            String sql = String.format("INSERT INTO Review (type, rid, stars, review_date, bid) VALUES ('%s', '%s', '%d', '%s', '%s')",
                    type, review_id, stars, date, business_id);
            stmt.executeUpdate(sql);
        }
    }

    private static void insertSubCatg() throws Exception {
        for (String mainC : mainToSub.keySet()) {
            Set<String> subCatgs = mainToSub.get(mainC);
            for (String subC : subCatgs) {
                subC = subC.replace("'", "''");
                String sql = String.format("INSERT INTO subCatg (scname, mcname) VALUES ('%s', '%s')", subC, mainC);
                try {
                    stmt.executeUpdate(sql);
                } catch (Exception e) {
                    System.out.println(sql);
                }
            }
        }
    }

    private static void initMainToSub() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("YelpDataset/yelp_business.json"));
        String line;
        Set<String> visitedTuple = new HashSet<>();
        while ((line = br.readLine()) != null) {
            Map<String, Set<String>> tmpMainToSub = new HashMap<>();
            Object obj = new JSONParser().parse(line);
            JSONObject jsonObject = (JSONObject)obj;
            JSONArray categories = (JSONArray) jsonObject.get("categories");
            JSONObject attributes = (JSONObject) jsonObject.get("attributes");
            String bid = (String)jsonObject.get("business_id");

            // main catg in current line
            Set<String> tmpMainCatg = new HashSet<>();
            for (int i = 0 ; i < categories.size(); i++) {
                String catg = (String)categories.get(i);
                if (mainCatg.contains(catg)) {
                    tmpMainCatg.add(catg);
                }
            }

            // mainCatg -> list of subCatg
            for (int i = 0; i < categories.size(); i++) {
                String catg = (String)categories.get(i);
                if (mainCatg.contains(catg)) continue;
                for (String key : tmpMainCatg) {
                    if (!tmpMainToSub.containsKey(key)) {
                        tmpMainToSub.put(key, new HashSet<>());
                    }
                    tmpMainToSub.get(key).add(catg);
                }
            }

            // attributes in current line
            Set<Object> tmpAttr = parseJSONObject(attributes);

            // insert triple tuple (mcname, scname, attr)
            for (String mainCatg : tmpMainToSub.keySet()) {
                Set<String> subCatgSet = tmpMainToSub.get(mainCatg);
                for (String subCatg : subCatgSet) {
                    for (Object attr : tmpAttr) {
                        String curTuple = mainCatg + subCatg + attr;
                        if (visitedTuple.contains(curTuple)) continue;
                        visitedTuple.add(curTuple);
                        String formattedSubCatg = subCatg.replace("'", "''");
                        String sql = String.format("INSERT INTO attr_catg (mcname, scname, avalue) VALUES ('%s', '%s', '%s')", mainCatg, formattedSubCatg, attr);
                        try {
                            stmt.executeUpdate(sql);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
//                            System.out.println(formattedSubCatg);
                        }
                    }
                }
            }
            attr.addAll(tmpAttr);

            for (String mainCatg : tmpMainToSub.keySet()) {
                if (!mainToSub.containsKey(mainCatg)) {
                    mainToSub.put(mainCatg, new HashSet<>());
                }
                mainToSub.get(mainCatg).addAll(tmpMainToSub.get(mainCatg));
            }
        }

    }

    private static void insertAttribute() throws Exception {
        for (Object o : attr) {
            String sql = String.format("INSERT INTO attribute (avalue) VALUES ('%s')", o.toString());
            stmt.executeUpdate(sql);
        }
    }

    private static Set<Object> parseJSONObject(JSONObject attributes) {
        Set<Object> res = new HashSet<>();
        for (Object key : attributes.keySet()) {
            Object value = attributes.get(key);
            if (value instanceof JSONObject) {
                Set<Object> tmp = parseJSONObject((JSONObject) value);
                for (Object tmpKey : tmp) {
                    String concatKey = (String)key + '_' + tmpKey;
                    res.add(concatKey);
                }
            } else {
                res.add(key + "_" + value);
            }
        }
        return res;
    }

    private static void initMainCatg() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("YelpDataset/mainCatg.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            mainCatg.add(line);
        }
    }

    private static void insertMainCatg() throws Exception {
        for (String catg : mainCatg) {
            String sql = String.format("INSERT INTO mainCatg(mcname) VALUES('%s')", catg);
            stmt.execute(sql);
        }
    }
}

