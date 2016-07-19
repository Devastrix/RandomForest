/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ran;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author user
 */
public class Endsem {
    /////// total 61878
    /**
     * @param args the command line arguments
     */
    
    public static int[][] train = new int[70000][95];
    public static int[] target = new int[70000];
    
    public static int give(NodeMul rot, int nod)
    {
        if (rot.ter != -1) return rot.ter;
        if (train[nod][rot.name] < rot.nameval) return give(rot.child[0], nod);
        else return give(rot.child[1], nod);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here

        String csvFile = "train.csv";
        BufferedReader br = null;
        String line = "";
        int j,counter=0,i;
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            // training set
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                
                for(i = 0; i < country.length-1; i++) {
                    
                   train[counter][i] = Integer.parseInt(country[i]); 
                }
                target[counter] = Integer.parseInt(country[i].substring(country[i].indexOf("_")+1)) - 1;
                counter++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       
        System.out.println(counter);
        
        int mtry=18,ntree=150;
        NodeMul root[] = new NodeMul[ntree];
        int m;
        for (m = 0; m < ntree; m++) {
            System.out.println("tree -> " + m);
            root[m] = new NodeMul();
            ArrayList<Integer> ar = new ArrayList<Integer>();
            Random ran = new Random(System.currentTimeMillis());
            for (i = 0; i < counter; i++) {
                j = ran.nextInt(61878);
                ar.add(j%61878);
                //System.out.println(j);
            }
            root[m].fun(ar, 1, root[m], mtry);
        }
        
        int haha = 0, tot=0;
        for (i = 0; i < counter; i += 10) {
            int coun[] = new int[9];
            for (j = 0; j < 9; j++)
                coun[j] = 0;
            for (j = 0; j < ntree; j++) { ////// trees
                //if (give(root[j], i) == target[i]) {
                  //  haha++;
                //}
                //tot++;
                coun[give(root[j], i)]++;
            }
            int mam=-1,ind=-1;
            for (j = 0; j < 9; j++) { ///// classes
                if (coun[j] > mam) {
                    ind = j;
                    mam = coun[j];
                }
            }
            if (ind == target[i])
                haha++;
            tot++;
        }
        System.out.println(haha + " " + tot);
        //root.show(root);
    }

}
