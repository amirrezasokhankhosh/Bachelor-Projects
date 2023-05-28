package sample;

import java.util.ArrayList;

public class Set {
    public char setName;
    public ArrayList<Character> setList;

    public  Set(char setName) {
        this.setName = setName;
        setList = new ArrayList<Character>();
    }

    public void printSet(){
        System.out.println(setName + " : {");
        for (Character c: setList) {
            System.out.print(c + " , ");
        }

        System.out.println("}");
    }
}
