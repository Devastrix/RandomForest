/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ran;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Administrator
 */

public class NodeMul {
    public NodeMul child[];
    public int name;
    public int ter;
    public int nameval;
    
    NodeMul()
    {
        name = -1;
        ter = -1;
        nameval = -1;
        child = new NodeMul[2];
        
        int i;
        for (i = 0; i < 2; i++)
            child[i] = null;
    }
    
    void fun(ArrayList<Integer> ha, int dep, NodeMul roo, int mtry)
    {
       // System.out.println("dep -> " + dep);
        int in=-1,ma=-1,i,f,l,j,k,flag=-1;
        int coun[] = new int[9];
        
        k = ha.size();
        
        for (i = 0; i < 9; i++)
            coun[i] = 0;
        for (i = 0; i < k; i++) 
            coun[Endsem.target[ha.get(i)]]++;
        
        for (i = 0; i < 9; i++) {
            if (coun[i] > ma) {
                ma = coun[i];
                in = i;
            }
        }
        
        if (dep == 10 || ma >= k/2) {
            roo.ter = in;
       //     System.out.println("here -> " + no + " " + in);
            return;
        }
        else {
            double Ij=0,I=0,pi,pij, gain, abi = -100000000.0;   /////  jhfjg
            int ind=-1;
            for (i = 0; i < 9; i++) {
                if (coun[i] != 0) {
                    pi = (double)coun[i]/(double)k;
                    I += pi*Math.log10(pi);
                }
            }
            I = -I;
            //System.out.println(I);
            Random ran = new Random();
            int att[] = new int[mtry];
            for (i = 0; i < mtry; i++) {
                att[i] = ran.nextInt(93);
                att[i] = att[i]%93;
                //att[i] = i;
            }
            for (i = 0; i < mtry; i++) {
                double ult = 0;
                ArrayList<Integer> tem = new ArrayList<Integer>();
                for (j = 0; j < k; j++) {
                    f = tem.size();
                    flag = 0;
                    for (l = 0; l < f; l++) {
                        if (tem.get(l) == Endsem.train[ha.get(j)][att[i]]) {
                            flag = 1;
                            break;
                        }    
                    }
                    if (flag == 0) {
                        tem.add(Endsem.train[ha.get(j)][att[i]]);
                        int tot = 0;
                        for (l = 0; l < 9; l++) {
                            coun[l] = 0;
                        }
                        f = Endsem.train[ha.get(j)][att[i]];
                        for (l = 0; l < k; l++) {
                            if (Endsem.train[ha.get(l)][att[i]] == f) { 
                                coun[Endsem.target[ha.get(l)]]++;
                                tot++;
                            }
                        }
                        Ij = 0;
                        for (l = 0; l < 9; l++) {
                            if (coun[l] != 0) {
                                pij = (double)coun[l]/(double)tot;
                                Ij += pij*Math.log10(pij);
                            }
                        }
                        Ij = - Ij;
                        ult += ((double)tot/(double)k)*Ij;
                    }
                }
                gain = I - ult;
                if (gain > abi) {
                    abi = gain;
                    ind = att[i];
                }
                //System.out.println(gain);
            }
            roo.name = ind;
            int mini=100000, maxi=-1;
            for (i = 0; i < k; i++) {
                if (Endsem.train[ha.get(i)][ind] > maxi)
                    maxi = Endsem.train[ha.get(i)][ind];
                if (Endsem.train[ha.get(i)][ind] < mini)
                    mini = Endsem.train[ha.get(i)][ind];
            }
            mini = (mini+maxi)/2;
            roo.nameval = mini;
            ArrayList<Integer> one = new ArrayList<Integer>();
            ArrayList<Integer> two = new ArrayList<Integer>();
            for (i = 0; i < k; i++) {
                if (Endsem.train[ha.get(i)][ind] > mini)
                    two.add(ha.get(i));
                else 
                    one.add(ha.get(i));
            }
            
         //   System.out.println("dep -> " + dep + " " + no);
           // System.out.println(one.size() + " " + two.size());
            roo.child[0] = new NodeMul();
            roo.child[0].fun(one, dep+1, roo.child[0], mtry);
            
            roo.child[1] = new NodeMul();
            roo.child[1].fun(two, dep+1, roo.child[1], mtry);
        }
        //System.out.println(ar.size());
    }
    void show(NodeMul root)
    {
        if (root.child[0] != null) {
            show(root.child[0]);
            show(root.child[1]);
        }
        else {
            System.out.println("class -> " + root.ter);
        }
    }
    /*
    int check(String a)
    {
        for (int i = 0; i < prch; i++) {
            if (name[i].equals(a)) return i;
        }
        return -1;
    }
    int create(String a)
    {
        child[prch] = new NodeMul();
        name[prch++] = a;
        return prch-1;
    }*/
}