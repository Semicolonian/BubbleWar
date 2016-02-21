package com.bubblewar.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bubblewar.game.HexagonTile.TileType;

public class Unit extends DynamicGameObject{

	
	public float radius;
	
	float maxHP;
	float currentHP;
	public float Attack;
	float Defense;
	
	public Unit (float x, float y, float radius,Fraction fraction) {
		super(x, y, radius,fraction);
		velocity = new Vector2(MathUtils.random((float)-1.0,(float)1.0),MathUtils.random((float)-1.0,(float)1.0)).nor();
		this.radius = radius;
		
		maxHP=100;
		currentHP=maxHP;
		Attack=2;
		Defense=1;
		
	}
	
	public void updateUnit(){
		this.position=position.add(velocity);
		this.bounds.x=this.position.x;
		this.bounds.y=this.position.y;
	}
	
	public void handleCollisionMap(Vector2 vecStart, Vector2 vecEnd){
		
		
		Vector2 vecLine = new Vector2(vecEnd.x-vecStart.x,vecEnd.y-vecStart.y).nor();
		Vector2 norm = new Vector2(-1*(vecEnd.y-vecStart.y),vecEnd.x-vecStart.x).nor();
		
		//components of the new Speed
		Vector2 temp1= new Vector2(vecLine.cpy().scl((this.velocity.cpy().len()*this.velocity.cpy().nor().dot(vecLine))));
		Vector2 temp2= new Vector2(norm.cpy().scl((this.velocity.cpy().len()*this.velocity.cpy().nor().dot(norm))));
		
		this.velocity=temp1.add(temp2.scl(-1));
		
		//correct to not fall out of map due to a mistake
		//this.position=this.position.cpy().add(temp2.scl(5));//works at least for slow speed
		

	}
	
	public void handleCollisionEnemy(Vector2 position){
		
		
		Vector2 vecLine = new Vector2(position.x-this.position.x,position.y-this.position.y).nor();
		Vector2 norm = new Vector2(-1*(position.y-this.position.y),position.x-this.position.x).nor();
		
		//components of the new Speed
		Vector2 temp1= new Vector2(vecLine.cpy().scl((this.velocity.cpy().len()*this.velocity.cpy().nor().dot(vecLine))));
		Vector2 temp2= new Vector2(norm.cpy().scl((this.velocity.cpy().len()*this.velocity.cpy().nor().dot(norm))));
		
		this.velocity=temp1.add(temp2.scl(-1));
		
		//correct to not fall out of map due to a mistake
		//this.position=this.position.cpy().add(temp2.scl(5));//works at least for slow speed
	

	}

	public void handleCollisionFriend(Vector2 position1, Vector2 velocity){
		
		
		Vector2 vecLine = new Vector2(position.x-this.position.x,position.y-this.position.y).nor();
		Vector2 norm = new Vector2(-1*(position.y-this.position.y),position.x-this.position.x).nor();
		
		//components of the new Speed
		Vector2 temp1= new Vector2(vecLine.cpy().scl((this.velocity.cpy().len()*this.velocity.cpy().nor().dot(vecLine))));
		Vector2 temp2= new Vector2(norm.cpy().scl((this.velocity.cpy().len()*this.velocity.cpy().nor().dot(norm))));
		
		this.velocity=temp1.add(temp2.scl(-1));
		
		//correct to not fall out of map due to a mistake
		//this.position=this.position.cpy().add(temp2.scl(5));//works at least for slow speed
		
	}
	
	public void fight(Unit enemy){
		int luck=MathUtils.random(0,100);
		float temp;
		
		if(luck>50){
			temp=(enemy.Attack+((enemy.Attack/100)*(luck-50)))-this.Defense;
			if(temp<0){
				temp=0;
			}
			this.currentHP=this.currentHP-temp;
			
			temp=this.Attack-(((enemy.Defense/100)*(luck-50))+this.Defense);
			if(temp<0){
				temp=0;
			}
			enemy.currentHP=enemy.currentHP-temp;
		}
		else{
			temp=(this.Attack+((this.Attack/100)*(50-luck)))-enemy.Defense;
			if(temp<0){
				temp=0;
			}
			enemy.currentHP=enemy.currentHP-temp;
			
			temp=enemy.Attack-(((this.Defense/100)*(50-luck))+enemy.Defense);
			if(temp<0){
				temp=0;
			}
			this.currentHP=this.currentHP-temp;
		}
		
		
		
		
	}
	
	public void levelUP(Unit enemy){
		this.radius=this.radius+1;
		this.maxHP=this.maxHP+2;
		
		if(this.radius<=10){
			this.Attack=2;
		}
		else if(this.radius==11){
			this.Attack=3;
		}
		else if(this.radius==12){
			this.Attack=4;
		}
		else if(this.radius>12&&this.radius<=22){
			this.Attack=this.Attack+(float)0.1;
		}
		else if(this.radius>22&&this.radius<=42){
			this.Attack=this.Attack+(float)0.05;
		}
		else if(this.radius>45){
			this.Attack=6;
		}
		
		if (this.radius<45) {
			if (this.currentHP + (enemy.radius + 20) > this.maxHP) {//(2*(cruuentEnemyRadius-spawnradius)+startHP)/2
				this.currentHP = this.maxHP;
			} else {
				this.currentHP = this.currentHP + (enemy.radius + 20);
			}
		}
		else{
			if (this.currentHP + (enemy.radius + 10) > this.maxHP) {//(2*(cruuentEnemyRadius-spawnradius)+startHP)/2
				this.currentHP = this.maxHP;
			} else {
				this.currentHP = this.currentHP + (enemy.radius + 10);
			}
		}
		if(this.radius>50){
			this.radius=50;
		}
		
		if(this.maxHP>300){
			this.maxHP=300;
		}
		this.bounds=new Circle(this.position, this.radius);
	}
}
