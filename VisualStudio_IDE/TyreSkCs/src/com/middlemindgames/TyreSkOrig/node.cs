using System;

namespace com.middlemindgames.TyreSkOrig {
    /*
     * node.java
     * Victor G. Brusca 01/16/2022
     * Created on October 24, 2004, 10:57 PM by Middlemind Games
     */
    public class node {
        public node Next;
        public object Value;

        public node(object value, node next) {
            Next = next;
            Value = value;
        }
    }
}