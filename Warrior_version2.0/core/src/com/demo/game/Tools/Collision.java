package com.demo.game.Tools;


public class Collision {
    public float hero_x;
    public float enemy_x;
    public java.lang.String c_state;
    public int damage;
    boolean bool;


    public  enum String{demon,Barbarian,Goblin,Wizard,fireball,firebomb};

    public int  getDif(float hero_x,float enemy_x,float hero_y,float enemy_y,String c_state,boolean bool){
        switch (c_state){
            case demon:
                if(bool ==true && enemy_x-hero_x<=70 ){
                        damage=20;
                }
                else
                    damage=0;
                break;
            case Barbarian:

                if(bool==true &&   enemy_x-hero_x<=30 && enemy_y-hero_y<25 ){
                        damage=40;
                }
                else
                    damage=0;
                break;
            case Goblin:
                System.out.println(enemy_x-hero_x+" "+hero_y);
                if(bool==true &&  enemy_x-hero_x<=70 && hero_y!=0 ){
                        damage=30;
                }
                else
                    damage=0;
                break;
            case Wizard:
                if(bool==true &&  enemy_x-hero_x<=70){
                        damage=50;
                }
                else
                    damage=0;
                break;
        case fireball:
            //System.out.println(enemy_x+"f "+hero_x+"p "+hero_y);
            if(enemy_x>hero_x && enemy_x-hero_x<=20 && hero_y-enemy_y<=20)
                damage=20;
            else
                damage=0;
            break;
            case firebomb:
                //System.out.println(enemy_x+"f "+hero_x+"p "+hero_y);
                if(enemy_x>hero_x && enemy_x-hero_x<=20 && enemy_y-hero_y<=20&& enemy_y-hero_y>0)
                    damage=20;
                else
                    damage=0;
                break;


        }
        return  damage;
    }


}
