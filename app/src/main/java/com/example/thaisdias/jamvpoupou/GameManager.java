package com.example.thaisdias.jamvpoupou;

/**
 * Created by Thaís Dias on 30/09/2016.
 */
public class GameManager {

    int hunger = 50;
    int thirsty = 50;

    void gibFood()
    {
        hunger += 25;
    }

    void gibSoda()
    {
        thirsty += 25;
    }

}
