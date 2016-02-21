package com.bubblewar.game;


import com.bubblewar.game.HexagonTile.TileType;
import com.badlogic.gdx.math.Vector2;

public class HexagonMap {
	
	int MAX_MAP_SIZE =10000;
	
	public int width;
	public int height;
	float posX;
	float posY;
	float hexRadius;
	public int index=0;
	public HexagonTile[] Map= new HexagonTile[MAX_MAP_SIZE];
	
	public int lineIndex;
	int[] lineIndexes;
	boolean endlich;
	public Vector2[] mapBounds;
	TileType type;
	
	
	public HexagonMap(int width, int height, float posX, float posY, float hexRadius, TileType type){
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.posY = posY;
		this.hexRadius = hexRadius;
		this.type = type;
		
		mapBounds = new Vector2[this.width*this.height*120];
		lineIndex=0;
		lineIndexes = new int[7];
		endlich=false;
		
		buildMap();
		
	}
	
	public void buildMap(){
		
		index=0;
		float MapX=0;
		float MapY=0;
		for(int i = 0; i<width ;i++){
			if(i%2==0){
				
				MapX=(float)(posX+(i*1.5*hexRadius));
				for(int j=0; j<height; j++){
					MapY=(float)(posY-(j*Math.sqrt(3)*hexRadius));
					Map[index]= new HexagonTile(MapX, MapY, hexRadius,type,null);
					index++;
				}
			}
			else{
				MapX=(float)(posX+(1.5*hexRadius)+((i-1)*1.5*hexRadius));
				for(int j=0; j<height-1; j++){
					MapY=(float)(posY-(Math.sqrt(3)*0.5*hexRadius)-(j*Math.sqrt(3)*hexRadius));
					Map[index]= new HexagonTile(MapX, MapY, hexRadius,type,null);
					index++;
				}
			}
		}
		
	}
	
	
	public void findMapBounds(){
		
		for(int i=0; i<this.width*this.height*120;i++){
			mapBounds[i]=null;
		}
			
		for(int i=0; i<this.index;i++){
			if(this.Map[i].tileType==TileType.LAND||this.Map[i].tileType==TileType.HQ){
				for(int j=0;j<lineIndexes.length;j++){
					lineIndexes[j]=0;
				}
				int p=0;
				for(int j=0;j<this.index;j++){
					if(i!=j){
						if(Math.sqrt((this.Map[i].position.x-this.Map[j].position.x)*(this.Map[i].position.x-this.Map[j].position.x)+
								(this.Map[i].position.y-this.Map[j].position.y)*(this.Map[i].position.y-this.Map[j].position.y))<2*this.hexRadius){
							if(this.Map[j].tileType==TileType.LAND||this.Map[j].tileType==TileType.HQ){
								lineIndexes[p]=j;
								if(p==5){
									j=this.index;
								}
								p++;
							}
						}
					}
				}
				
			boolean[] helper={true,true,true,true,true,true};
			

			if(lineIndexes[0]==0){
				if(this.Map[0].tileType==TileType.LAND||this.Map[0].tileType==TileType.HQ){
					if(i==1){
						//dont draw line 0
						helper[0]=false;
					}
					else if(i==this.height){
						//dont draw line 5
						helper[5]=false;
					}
					
				}
				
			}
			
			if(true){
				
				
				for(int q=0; q<p;q++){
					
					
					
					if((this.Map[i].position.x+this.hexRadius<this.Map[lineIndexes[q]].position.x)
							&& (this.Map[i].position.y<this.Map[lineIndexes[q]].position.y)){
						//dont draw lines 1
						helper[1]=false;
					}
					else if((this.Map[i].position.x+this.hexRadius<this.Map[lineIndexes[q]].position.x)
							&& (this.Map[i].position.y>this.Map[lineIndexes[q]].position.y)){
						//dont draw lines 2
						helper[2]=false;
					}
					else if(this.Map[i].position.y-this.hexRadius>this.Map[lineIndexes[q]].position.y){
						//dont draw lines 3
						helper[3]=false;
					}
					else if((this.Map[i].position.x-this.hexRadius>this.Map[lineIndexes[q]].position.x)
							&& (this.Map[i].position.y>this.Map[lineIndexes[q]].position.y)){
						//dont draw lines 4
						helper[4]=false;
					}
					else if((this.Map[i].position.x-this.hexRadius>this.Map[lineIndexes[q]].position.x)
							&& (this.Map[i].position.y<this.Map[lineIndexes[q]].position.y)){
						//dont draw lines 5
						helper[5]=false;
					}
					else if(this.Map[i].position.y+this.hexRadius<this.Map[lineIndexes[q]].position.y){
						//draw lines 0
						helper[0]=false;
					}
				}

			}

				if(helper[0]){
					Vector2 temp1 =new Vector2(this.Map[i].position.x-(float)(this.hexRadius*0.5),this.Map[i].position.y+(float)(Math.sqrt(3)*0.5)*this.hexRadius );
					Vector2 temp2 =new Vector2(this.Map[i].position.x+(float)(this.hexRadius*0.5),this.Map[i].position.y+(float)(Math.sqrt(3)*0.5)*this.hexRadius );
					
					mapBounds[lineIndex]=temp1;
					mapBounds[lineIndex+1]=temp2;
					lineIndex=lineIndex+2;
				}
				if(helper[1]){
					Vector2 temp1 =new Vector2(this.Map[i].position.x+(float)(this.hexRadius*0.5),this.Map[i].position.y+(float)(Math.sqrt(3)*0.5)*this.hexRadius );
					Vector2 temp2 =new Vector2(this.Map[i].position.x+this.hexRadius,this.Map[i].position.y);
					
					mapBounds[lineIndex]=temp1;
					mapBounds[lineIndex+1]=temp2;
					lineIndex=lineIndex+2;
				}
				if(helper[2]){
					Vector2 temp1 =new Vector2(this.Map[i].position.x+(float)(this.hexRadius),this.Map[i].position.y);
					Vector2 temp2 =new Vector2(this.Map[i].position.x+(float)(this.hexRadius*0.5),this.Map[i].position.y-(float)(Math.sqrt(3)*0.5)*this.hexRadius);
					
					mapBounds[lineIndex]=temp1;
					mapBounds[lineIndex+1]=temp2;
					lineIndex=lineIndex+2;
				}
				if(helper[3]){
					
					Vector2 temp1 =new Vector2(this.Map[i].position.x+(float)(this.hexRadius*0.5),this.Map[i].position.y-(float)(Math.sqrt(3)*0.5)*this.hexRadius);
					Vector2 temp2 =new Vector2(this.Map[i].position.x-(float)(this.hexRadius*0.5),this.Map[i].position.y-(float)(Math.sqrt(3)*0.5)*this.hexRadius);
					
					mapBounds[lineIndex]=temp1;
					mapBounds[lineIndex+1]=temp2;
					lineIndex=lineIndex+2;
				}
				if(helper[4]){
					
					Vector2 temp1 =new Vector2(this.Map[i].position.x-(float)(this.hexRadius*0.5),this.Map[i].position.y-(float)(Math.sqrt(3)*0.5)*this.hexRadius);
					Vector2 temp2 =new Vector2(this.Map[i].position.x-(this.hexRadius),this.Map[i].position.y);
					
					
					mapBounds[lineIndex]=temp1;
					mapBounds[lineIndex+1]=temp2;
					lineIndex=lineIndex+2;
				}
				if(helper[5]){
					
					Vector2 temp1 =new Vector2(this.Map[i].position.x-(this.hexRadius),this.Map[i].position.y);
					Vector2 temp2 =new Vector2(this.Map[i].position.x-(float)(this.hexRadius*0.5),this.Map[i].position.y+(float)(Math.sqrt(3)*0.5)*this.hexRadius );
					
					mapBounds[lineIndex]=temp1;
					mapBounds[lineIndex+1]=temp2;
					lineIndex=lineIndex+2;
				}

			
			}


		}
		lineIndex=lineIndex-2;
	}
	
	
}
