package com.middlemindgames.TyreSkOrig;

/*
 * node.java
 * Victor G. Brusca 01/16/2022
 * Created on October 24, 2004, 10:57 PM by Middlemind Games
 */
public class node {
    public node Next;
    public Object Value;

    public node(Object value, node next) {
        Next = next;
        Value = value;
    }
}