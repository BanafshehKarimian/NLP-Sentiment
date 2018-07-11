/**
 * Created by banafshbts on 18. 7. 8.
 */
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;


public class POStagger {

    Vector<String> tager(String s) {
        //String s= "hi! i love bts and kim taehyung very much";
        //System.out.print(s[0]);
        InputStream posModelIn = null;
        try {
            FileInputStream tokenModelIn = new FileInputStream("en-token.bin");
            TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            Tokenizer tokenizer = new TokenizerME(tokenModel);
            String tokens[] = tokenizer.tokenize(s);
            posModelIn = new FileInputStream("en-pos-maxent.bin");
            POSModel posModel = new POSModel(posModelIn);
            POSTaggerME posTagger = new POSTaggerME(posModel);

            String [] p = posTagger.tag(tokens);
            //for (int i=0;i<p.length;i++)
             // System.out.print(p[i]+"++");
            return g1(tokens,p);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.print("\n");
        //System.out.print(new Polarity().near("xx","yy","yy aa bb xx cc dd yy dd ee ff xx gg hh yy ii jj kk xx"));
        //new Polarity().near("xx","yy"," sd df xx shdjhvshg87 y");
        //System.out.print(g1("1 2 3 4 5 ".split(" "),"JJ NN NN MN".split(" ")));
        return new Vector<String>();
    }

    Vector<String> g1(String[] s, String[] p){

        Vector<String> o = new Vector<String>();
        for (int i=0;i<p.length-3;i++){

            if (p[i].equals("JJ")){
                if (p[i+1].equals("NN")|| p[i+1].equals("NNS")){

                    o.add(s[i]+" "+s[i+1]);

                }
                if(p[i+1].equals("JJ")){
                    if(!p[i+2].equals("NN")&&!p[i+2].equals("NNS")){

                        o.add(s[i]+" "+s[i+1]);
                    }
                }
            }

            if (p[i].equals("RB")||p[i].equals("RBR")||p[i].equals("RBS")){
                if (p[i+1].equals("VB")|| p[i+1].equals("VBD")||p[i+1].equals("VBN")||p[i+1].equals("VBG")){

                    o.add(s[i]+" "+s[i+1]);

                }
                if(p[i+1].equals("JJ")){
                    if(!p[i+2].equals("NN")&&!p[i+2].equals("NNS")){

                        o.add(s[i]+" "+s[i+1]);
                    }
                }
            }


            if (s[i].equals("NN")||s[i].equals("NNS")){
                if(s[i+1].equals("JJ")){
                    if(!s[i+2].equals("NN")&&!s[i+2].equals("NNS")){

                        o.add(s[i]+" "+s[i+1]);
                    }
                }
            }
        }
        return o;

    }

}
