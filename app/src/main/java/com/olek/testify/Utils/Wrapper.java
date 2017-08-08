package com.olek.testify.Utils;


public class Wrapper{
    Character character;
    public Wrapper(Character character) { this.character = character;}
    public String wrap(String s){return character + s + character;}
}