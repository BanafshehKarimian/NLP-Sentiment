import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by banafshbts on 18. 7. 8.
 */
public class Cleaner {
    public static String text_negetive ="";
    public static String text_positive ="";
    public static Vector<String> train =new Vector<String>();
    public static Vector<String> test =new Vector<String>();
    public static Vector<Integer> train_lable =new Vector<Integer>();
    public static Vector<String> all =new Vector<String>();
    public static Vector<String> all_date =new Vector<String>();
    public static Vector<Integer> all_lable =new Vector<Integer>();
    public static Vector<Integer> test_lable =new Vector<Integer>();
    public static Vector<String> test_date =new Vector<String>();
    public static Vector<String> negetive =new Vector<String>();
    public static Vector<String> positive =new Vector<String>();
    public static Vector<String> negetive_date =new Vector<String>();
    public static Vector<String> positive_date =new Vector<String>();
    public static Vector<String> negetive_product =new Vector<String>();
    public static Vector<String> positive_product =new Vector<String>();

    public static Map<String, Integer> yearn = new HashMap<>();
    public static Map<String, Integer> yearp = new HashMap<>();



    String read(String name){


        InputStream in = getClass().getResourceAsStream("./"+name);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        String res = "";
        try {
            while ((line = reader.readLine()) != null) {
                res+=line.replace(")","").replace("+","").replace("(","").replace("[","").replace("-"," ").replace("*","")+"\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    String separate_date(String str){

        String name = str.substring(str.indexOf("<date>") + 1, str.indexOf("</date>"));
        return name.replace("\n"," ").replace("date> ","");
    }
    String separate_name(String str){

        String date = str.substring(str.indexOf("<product_name>") + 1, str.indexOf("</product_name>"));
        return date.replace("\n"," ").replace("product_name>"," ");
    }
    String separate_txt(String str){

        String txt =str.substring(str.indexOf("<review_text>") + 1, str.indexOf("</review_text>"));
        return txt.replace("review_text>"," ");
    }
    void parse(String s){
        text_negetive = read("sorted_data_acl/"+s+"/negative.review");
        text_positive = read("sorted_data_acl/"+s+"/positive.review");
        String n[] = text_negetive.split("</review>");
        String p[] = text_positive.split("</review>");
        for (int i=0;i<n.length-1;i++){

            negetive.add(separate_txt(n[i]).replace("\n"," "));
            negetive_date.add(separate_date(n[i]));
            negetive_product.add(separate_name(n[i]));

        }
        for (int i=0;i<p.length-1;i++){

            positive.add(separate_txt(p[i]).replace("\n"," "));
            positive_date.add(separate_date(p[i]));
            positive_product.add(separate_name(p[i]));

        }
        //System.out.print(new Polarity().polarity("time",negetive));
        //writer(s);
    }

    void writer(String s){

        BufferedWriter writer1 = null;
        BufferedWriter writer2 = null;
        try {
            writer1 = new BufferedWriter(new FileWriter(s+"neg"));
            writer2 = new BufferedWriter(new FileWriter(s+"pos"));
            for (int i=0;i<positive.size();i++){

                writer2.write(positive_date.get(i)+"++"+positive_product.get(i)+"++"+positive.get(i));//+"||"+positive_product.get(i)+"||"+positive.get(i));
                writer2.newLine();

            }
            for (int i=0;i<negetive.size();i++){

                writer1.write(negetive_date.get(i)+"++"+negetive_product.get(i)+"++"+negetive.get(i));//+"||"+negetive_product.get(i)+"||"+negetive.get(i));
                writer1.newLine();

            }

            writer1.close();
            writer2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    void dataSeperator(){

        for (int i=0;i<negetive.size();i++){

            if(i%4==0){
                test_lable.add(-1);
                test.add(negetive.get(i));
                //System.out.print(negetive_date.get(i)+"\n");
                test_date.add(negetive_date.get(i));////"20"+(negetive_date.get(i).split("20")[1]).replace(" ",""));
                yearn.put(test_date.get(test_date.size()-1),0);
                //        System.out.print(test_date.get(test_date.size()-1)+"\n");
                //System.out.print("200"+negetive_date.get(i).split("200")[1]);
            }
            if(i%4!=0)
                train.add(negetive.get(i));
            all.add(negetive.get(i));
            all_lable.add(-1);
        }
        for (int i=0;i<positive.size();i++){

            if(i%4==0){
                test.add(positive.get(i));
                test_lable.add(1);
                test_date.add(positive_date.get(i));//"20"+(positive_date.get(i).split("20")[1]).replace(" ",""));
                yearp.put(test_date.get(test_date.size()-1),0);
          //      System.out.print(test_date.get(test_date.size()-1)+"\n");

            }
            if(i%4!=0)
                train.add(positive.get(i));
            all.add(positive.get(i));
            all_lable.add(1);
        }

    }


}
