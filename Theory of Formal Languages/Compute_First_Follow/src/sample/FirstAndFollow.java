package sample;

import java.util.*;

public class FirstAndFollow {

    private ArrayList<Set> first ;
    private ArrayList<Set> follow;
    private ArrayList<String> production;

    private int  productionCount;
    char[] doneFirst;
    char[] doneFollow;


    public FirstAndFollow(ArrayList<String> production , int productionCount ){
        first = new ArrayList<Set>();
        follow = new ArrayList<Set>();

        this.production = production;
        this.productionCount =productionCount;

        doneFirst = new char[productionCount];
        doneFollow = new char[productionCount];

    }

    public void runAdjusmentOnProduction(){
        ArrayList<String> list = new ArrayList<String>();
        for (String s: production) {
            if(s.indexOf('#') != -1){
                checkForNull(s.charAt(0),list);
            }
        }

        for (String s: list) {
            if(!production.contains(s)){
                production.add(s);
                productionCount++;
            }
        }
    }

    private void checkForNull(char c , ArrayList<String> list){
        for (String s2: production) {
            if( s2.charAt(0) == c){
                int index =  s2.indexOf(c, 1);
                if(index != -1){
                    StringBuilder sb = new StringBuilder(s2);
                    sb.deleteCharAt(index);
                    String np = sb.toString();
                    if(!production.contains(np)) {
                        list.add(np);
                    }
                }
            }
        }
    }

    public ArrayList<Set> calculateFirst(){
        int point1 = 0 , point2 , ptr = -1 , temp = 0;
        boolean flag = false;

        for (int i = 0; i < productionCount; i++) {
            char c = production.get(i).charAt(0);
            Set set = new Set(c);

            point2 = 0;
            flag = false;

            for (int j = 0; j <= ptr ; j++) {
                if (c == doneFirst[j])
                    flag = true;
            }

            if (flag)
                continue;

            findFirst(set , c ,0 , 0);
            ArrayList<Character> list = new ArrayList<Character>();
            for(Character chr : set.setList){
                if(!list.contains(chr)){
                    list.add(chr);
                }
            }
            set.setList = list;
            first.add(set);
            ptr++;

            doneFirst[ptr] = c;
        }


        return first;
    }

    public ArrayList<Set> calculateFollow(){
        int point1 = 0 , point2 , ptr = -1 , temp = 0;
        boolean flag = false;

        for (int i = 0; i < productionCount; i++) {
            char c = production.get(i).charAt(0);
            Set set = new Set(c);

            findFollow(set , c);
            follow.add(set);
            ptr++;

        }

        return follow;
    }

    private void findFirst(Set set,char c ,int p1, int p2) {
        if(Character.isLowerCase(c) ||  !Character.isLetter(c)){
            set.setList.add(c);
        }

        for (int i = 0; i <productionCount ; i++) {
            if (production.get(i).charAt(0) == c){
                if(production.get(i).charAt(2) == '#'){
                    if (production.get(p1).charAt(p2) == '\0'){
                        set.setList.add('#');
                    }else if (production.get(p1).charAt(p2) != '\0' && (p1 != 0 || p2 != 0)){
                        findFirst(set , production.get(p1).charAt(p2) , p1 , (p2 + 1));
                    }else {
                        set.setList.add('#');
                    }
                }else if (Character.isLowerCase(production.get(i).charAt(2)) ||
                        !Character.isLetter(production.get(i).charAt(2))){
                    set.setList.add(production.get(i).charAt(2));
                }else {
                    findFirst(set , production.get(i).charAt(2) , i , 3);
                }
            }
        }
    }

    private void  findFollow(Set set , char c){
        if (production.get(0).charAt(0) == c){
            set.setList.add('$');
        }


        for (int i = 0; i < productionCount; i++) {
            for (int j = 2; j <production.get(i).length() ; j++) {
                if(production.get(i).charAt(j) == c){
                    if (j+1 != production.get(i).length()){
                        // calculate first set of the next Non-terminal in the production
                        followFirst(set , production.get(i).charAt(j+1));
                    }

                    if (j+1 == production.get(i).length() && c != production.get(i).charAt(0)){
                        //calculate follow set of the Non-terminal in L.H.S of production
                        findFollow(set , production.get(i).charAt(0));
                    }
                }
            }
        }
    }


    private void followFirst(Set set, char c) {
        ArrayList<Character> list = getFirstOf(c);
        for (Character ch:
                list) {
            if(ch != '#' && !set.setList.contains(ch)){
                set.setList.add(ch);
            }
        }
    }

    private ArrayList<Character> getFirstOf(char c){
        if (Character.isLowerCase(c) ||  !Character.isLetter(c)){
            ArrayList<Character> l = new ArrayList<Character>();
            l.add(c);
            return l;
        }

        for (Set s:
                first) {
            if (s.setName == c)
                return s.setList;
        }

        return null;
    }
}
