import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by banafshbts on 18. 7. 8.
 */
public class main {


    static POStagger pos=new POStagger();
    static Cleaner c = new Cleaner();

    public static void main(String[] args) {
       c.parse("electronics");
        int tp=0;
        int fp = 0;
        int tn =0;
        int fn =0;


        BufferedWriter writer1 = null;
        BufferedWriter writer2 = null;
        try {
            writer1 = new BufferedWriter(new FileWriter("neg"));
            writer2 = new BufferedWriter(new FileWriter("pos"));
        //c.parse("books");
        //c.parse("dvd");
        ///new Cleaner().parse("dvd");
        c.dataSeperator();
        //String s = c.test.get(1);
        // = new Vector<String >();
        //x= pos.tager(s);

        //System.out.print("g="+z+"m="+c.test_lable.get(1));
        int acc =0;
        for (int i=0;i<c.test.size();i++){

            String s = c.test.get(i);
            int o = calc(pos.tager(s));
            if(o==c.test_lable.get(i)) {
                acc++;
                if (o == 1) {
                    tp++;
                    writer2.write(c.test_date.get(i)+" "+c.test.get(i));
                }
                else{
                    tn++;
                    writer1.write(c.test_date.get(i)+" "+c.test.get(i));
                }
            }
            else{

                if (o == 1) {
                    fp++;
                    writer2.write(c.test_date.get(i)+" "+c.test.get(i));
                }
                else {
                    fn++;
                    writer1.write(c.test_date.get(i)+" "+c.test.get(i));
                }

            }
            //System.out.print(c.test_label.get(i));
            System.out.print("::"+o+"\n");
          //  System.out.print(c.test.get(i)+"\n");
            if(c.test_lable.get(i)==1){
                //if(c.yearp.get(c.test_date)!=null)
                //    c.yearp.put(c.test_date.get(i),c.yearp.get(c.test_date.get(i))+1);
                //else
                    c.yearp.put(c.test_date.get(i),0);}
            else{
           // if(c.yearn.get(c.test_date)!=null)
            //    c.yearn.put(c.test_date.get(i),c.yearn.get(c.test_date.get(i))+1);
            //else
                c.yearn.put(c.test_date.get(i),0);}
            //System.out.print(c.test_date.get(i)+"\n");
                //System.out.print("c"+o+"m"+c.test_lable.get(i)+"\n");
        }
        // String[] out = pos.tager(s).replace("\n","").split("||");
        //for(int i =0 ;i<yearn.size();i++)
       // System.out.print("2000:p="+c.yearp.get(" 2000 ")+"n="+c.yearn.get(" 2000 ")+"\n");
        //System.out.print("2001:p="+c.yearp.get(" 2001 ")+"n="+c.yearn.get(" 2001 ")+"\n");
        //System.out.print("2002:p="+c.yearp.get(" 2002 ")+"n="+c.yearn.get(" 2002 ")+"\n");
        //System.out.print("2003:p="+c.yearp.get(" 2003 ")+"n="+c.yearn.get(" 2003 ")+"\n");
       // System.out.print("2004:p="+c.yearp.get(" 2004 ")+"n="+c.yearn.get(" 2004 ")+"\n");
        //System.out.print("2005:p="+c.yearp.get(" 2005 ")+"n="+c.yearn.get(" 2005 ")+"\n");
       // System.out.print("2006:p="+c.yearp.get(" 2006 ")+"n="+c.yearn.get(" 2006 ")+"\n");
       // System.out.print("2007:p="+c.yearp.get(" 2007 ")+"n="+c.yearn.get(" 2007 ")+"\n");
       // System.out.print("2008:p="+c.yearp.get(" 2008 ")+"n="+c.yearn.get(" 2008 ")+"\n");
       // System.out.print("2009:p="+c.yearp.get(" 2009 ")+"n="+c.yearn.get(" 2009 ")+"\n");
        //System.out.print(new Polarity().polarity(pos.tager(s).get(0),c.train));
        System.out.print(acc+"s"+c.test.size()+"\n");
        System.out.print("precision"+(float)tp/(tp+fp)+"\n");
        System.out.print("recall"+(float)tp/(tp+fn)+"\n");
        writer1.close();
            writer2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static int calc (Vector<String> x){

        double z = 0;
        for (int i=0;i<x.size();i++){

            z+=new Polarity().polarity(x.get(i).split(" ")[0],c.train);
            z+=new Polarity().polarity(x.get(i).split(" ")[1],c.train);
            //z+=(new Polarity().polarity(x.get(i).split(" ")[1],c.train)*new Polarity().polarity(x.get(i).split(" ")[0],c.train));
            //z+=new Polarity().polarity(x.get(i),c.all);
            //System.out.print(x.get(i)+z+"\n");

        }
        if(z<0.001)
            return -1;
        return 1;
    }


}
