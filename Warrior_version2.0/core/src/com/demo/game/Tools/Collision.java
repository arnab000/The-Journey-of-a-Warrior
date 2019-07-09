package com.demo.game.Tools;


public class Collision {
    public float hero_x;
    public float enemy_x;
    public java.lang.String c_state;
    public int damage;
    boolean bool;


    public  enum String{Barbarian,Bigy,Goblin,Wizard};

    public int  getDif(float hero_x,float enemy_x,String c_state,boolean bool){
        switch (c_state){
            case Barbarian:
                if(bool ==true && enemy_x-hero_x<=70 ){
                        damage=20;
                }
                else
                    damage=0;
                break;
            case Bigy:
                if(bool=true &&   enemy_x-hero_x<=30){
                        damage=40;
                }
                else
                    damage=0;
                break;
            case Goblin:
                if(bool=true &&  enemy_x-hero_x<=30 ){
                        damage=30;
                }
                else
                    damage=0;
                break;
            case Wizard:
                if(bool=true &&  enemy_x-hero_x<=30){
                        damage=50;
                }
                else
                    damage=0;
                break;

        }
        return  damage;
    }


}
