package com.bubblewar.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.bubblewar.game.Fraction;

public class Army {

	public Vector2 headquaterPosition;
	public int ARMY_SIZE = 500;
	public Unit[] units= new Unit[ARMY_SIZE];
	boolean isHQFree;
	Circle spawnRadius;
	public int realArmySize=1;
	
	
	public Fraction fraction;
	
	public Army(Vector2 headquaterPosition, Fraction fraction){
		this.headquaterPosition=headquaterPosition;;
		this.fraction=fraction;
		isHQFree=false;
		spawnRadius= new Circle(headquaterPosition, 1);
		units[0]=new Unit(headquaterPosition.x,headquaterPosition.y,10,fraction);
		
	}
	
	public void updateArmy(){
		fillArmy();
		
		for(int i=0; i<realArmySize;i++){
			if(units[i]!=null){
				units[i].updateUnit();
			}
				
		}
	}
	
	void fillArmy(){
		
		for(int i=0; i<units.length;i++){
			if(units[i]==null){
				if(isHQFree()){
					units[i]=new Unit(headquaterPosition.x,headquaterPosition.y,10,fraction);
					realArmySize++;
					if(realArmySize>ARMY_SIZE){
						realArmySize=ARMY_SIZE;
					}
				}
				isHQFree=false;
			}
		}
	}
	
	boolean isHQFree(){
		isHQFree=true;
		for(int j=0;j<GameScreen.armys.length;j++){
			for(int i =0; i<GameScreen.armys[j].realArmySize;i++){
				if(GameScreen.armys[j].units[i]==null){
					
				}
				else if(GameScreen.armys[j].units[i].bounds.overlaps( this.spawnRadius)){
					isHQFree=false;
				}
			}
		}
		return isHQFree;
	}
	
}
