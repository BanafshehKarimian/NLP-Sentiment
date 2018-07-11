import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import static java.lang.Float.NaN;

/**
 * Created by banafshbts on 18. 7. 8.
 */
public class Polarity {
    Map<String,Double> polarities =new HashMap<String,Double>();
    //Vector<String> data = new Vector<String>();
    static double log(int x, int base)
    {
        return  (Math.log(x) / Math.log(base));
    }
    float PMI(String w1, String w2, Vector<String> text){
        float ret = (float) 0.0;
        int h1=1;
        int h2=1;
        int h3=1;
        for (int i=0;i<text.size();i++) {
            h1 += (near(w1, w2, text.get(i))+near(w2, w1, text.get(i)));
            h2+=hits(w1,text.get(i));
            h3+=hits(w2,text.get(i));
        }
        ret = (float) ( log(h1,2)-log(h2,2)-log(h3,2));
        //System.out.print("h1"+h1+"h2"+h2+"h3"+h3+"\n");
        //ret = h1/(h2*h3);
        return ret;
    }
    float PMI2(String w1, String w2, String w3, Vector<String> text){
        float ret = (float) 0.0;
        int h1=1;
        int h2=1;
        int h3=1;
        int h4 =1;
        int h5 =1;
        for (int i=0;i<text.size();i++) {
            h1 += hits(w1,text.get(i));
            h2+=hits(w2,text.get(i));
            h3+=hits(w3,text.get(i));
            h4+=Math.max(near(w1, w2, text.get(i)),near(w2, w1, text.get(i)));
            h5+=Math.max(near(w1, w3, text.get(i)),near(w3, w1, text.get(i)));
        }
        //ret = (float) ( log(h1,2)-log(h2,2)-log(h3,2));
        ret = (float) log((h4*h3)/(h5*h2),2);
        //System.out.print("h1"+h1+"h2"+h2+"h3"+h3+"\n");
        //ret = h1/(h2*h3);
        return ret;
    }
    int near(String w1,String w2,String text){

        //NEAR??
        int n=0;
        String[] x = text.split(w1);
        int limit = 6;
        if(x.length==1)
            return 0;
        for (int i=0;i<x.length;i++){

            String []y=x[i].split(w2);
            if(y.length==1||y.length==0) {
                continue;
            }
         //   System.out.print(y[0].split(" ").length);
            if(i!=0&&i!=x.length-1)
                if(y[0].split(" ").length<limit||y[y.length-1].split(" ").length<limit)
                    n++;
            if(i==0)
                 if(y[y.length-1].split(" ").length<limit)
                        n++;
            if(i==x.length-1)
                    if(y[0].split(" ").length<limit)
                        n++;
        }
        return n;
    }
    int hits(String w,String text){

        return text.split(w).length-1;

    }
    double polarity(String w1,Vector<String> text){
/*
        double out =PMI(w1.split(" ")[0],"good",text)-PMI(w1.split(" ")[0],"poor",text);
        double out1 = (PMI(w1.split(" ")[1],"good",text)-PMI(w1.split(" ")[1],"poor",text));

        //out +=;
        if (!Double.isNaN(out)&&!Double.isNaN(out1))
            return out+out1;
        if(!Double.isNaN(out))
            return out;
        if(!Double.isNaN(out1))
            return out1;*/
        double out =PMI(w1,"good",text)-PMI(w1,"out",text);//(PMI(w1,"crap",text)+PMI(w1,"burned",text)+PMI(w1,"poorly",text)+PMI(w1,"poor",text)+PMI(w1,"waste",text)+PMI(w1,"ever",text));
        double out2 = PMI(w1,"excellant",text)-PMI(w1,"not",text);///PMI2(w1,"good","out",text);

        if(!Double.isNaN(out)&&!Double.isNaN(out2))
            return Math.min(out,out2);
        if (!Double.isNaN(out))
            return out;
        if(!Double.isNaN(out2))
            return out2;
        else
            return 0;

    }
}
