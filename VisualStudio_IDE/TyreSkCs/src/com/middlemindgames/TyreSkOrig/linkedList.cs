using System;

namespace com.middlemindgames.TyreSkOrig {
    /*
     * linkedList.java
     * Victor G. Brusca 01/16/2022
     * Created on October 24, 2004, 10:55 PM by Middlemind Games
     */
    public class linkedList {
        public int count = 0;
        public node front = null;
        public node end = null;

        public bool isEmpty() {
            return (count == 0);
        }

        public int getCount() {
            return count;
        }

        public void append(object obj) {
            if (count == 0) {
                front = end = new node(obj, null);
            } else {
                end.Next = new node(obj, null);
                end = end.Next;
            }
            count++;
        }

        public void removeFrontNode() {
            if (count > 1) {
                front = front.Next;
                count--;
            } else {
                front = null;
                count = 0;
            }
        }
    }
}