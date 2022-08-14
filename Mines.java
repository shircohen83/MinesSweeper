package mines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mines 
{//represents a board full of mines, the base of mines weeper game
	private Place[][]board; 
	private int numMines,height,width;
	private boolean showAll=false;
	public Mines(int height, int width, int numMines)
	{
		board= new Place[height][width];
		this.numMines=numMines;
		this.height=height;
		this.width=width;
		for(int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				board[i][j] = new Place();
		Random rand = new Random();
		for (int i = 0; i < this.numMines; i++) 
		{
			if(!addMine(rand.nextInt(height), rand.nextInt(width)))
				i--;//if adding the mine failed, we will add another mine
			
		}
	}
	public boolean addMine(int i, int j)
	{//adding a mine in the given place
		
		if(board[i][j].ifMine==true)
			return false;
		else
			{
				aroundMine(i, j);
				board[i][j].ifMine=true;
				return true;
			}
	}
	private class Place {
		private boolean ifOpen, ifFlag, ifMine;
		private int numOfMines=0;//saves the number of mines around
		}
	
	public boolean open(int i, int j)
	{//opens the given place, returns true if its not a mine. opens the surroundings if they arent mines
		List<Place> visited = new ArrayList<>();
		if(board[i][j].ifOpen==true)//if already open
			return true;
		if(board[i][j].ifMine==true)
			return false;
		
		board[i][j].ifOpen=true;//the cell is open
		if(board[i][j].numOfMines!=0)//there are mines around
			return true;
		//will open neighbors if mines around are 0, and the place is not open yet and the place is not a mine
		visited.add(board[i][j]);
		//for each place we will check if its in the limits and not a mine
		if(i<height-1 &&!board[i+1][j].ifMine)//down
			if(!visited.contains(board[i+1][j]))//if wasn't checked already
				open(i+1,j);
		if(i>0 &&!board[i-1][j].ifMine)//up
			if(!visited.contains(board[i-1][j]))
				open(i-1,j);
		if(j<width-1 &&!board[i][j+1].ifMine)//right
			if(!visited.contains(board[i][j+1]))
				open(i,j+1);
		if(j>0 &&!board[i][j-1].ifMine)//left
			if(!visited.contains(board[i][j-1]))
				open(i,j-1);
		if(i<height-1 &&j<width-1&&!board[i+1][j+1].ifMine)//down and right
			if(!visited.contains(board[i+1][j+1]))
				open(i+1,j+1);
		if(i>0 &&j<width-1&&!board[i-1][j+1].ifMine)//up and right
			if(!visited.contains(board[i-1][j+1]))
				open(i-1,j+1);
		if(i>0 &&j>0&&!board[i-1][j-1].ifMine)//up and left
			if(!visited.contains(board[i-1][j-1]))
				open(i-1,j-1);
		if(i<height-1&&j>0&&!board[i+1][j-1].ifMine)//down and left
			if(!visited.contains(board[i+1][j-1]))
				open(i+1,j-1);
		return true;
	}
	public void toggleFlag(int x, int y) 
	{//setting a flag in the given place
		if(board[x][y].ifFlag)//already a flag
			board[x][y].ifFlag=false;
		else
			board[x][y].ifFlag=true;	
	}
	public boolean isDone() 
	{//returns true if the game ended,if all the places that are not mine are open
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++)
			{
				if(!board[i][j].ifMine&&!board[i][j].ifOpen)//if not mine and closed
					return false;
				if(board[i][j].ifMine&&board[i][j].ifOpen)//if mine and open
					return false;
			}
		return true;	
	}
	public String get(int i, int j) 
	{
		if(!board[i][j].ifOpen&&!showAll)//if closed and show all is false
		{
			if(board[i][j].ifFlag)
				return "F";
			else
				return ".";
		}
		else//showAll true and place open,showAll true and place closed,showAll false and place open
		{
			if(board[i][j].ifMine)
				return "X";
			else
			{
				if(board[i][j].numOfMines==0)
					return " ";
				else
					return ""+ board[i][j].numOfMines;
			}
		}
	}
	public void setShowAll(boolean showAll) 
	{
		this.showAll=showAll;
	}
	public String toString() 
	{
		StringBuilder str= new StringBuilder();
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
				str.append(get(i,j));
			str.append("\n");
		}
		return str.toString();
	}
	public void aroundMine(int i, int j)
	{
		if(i<height-1 )//down
			board[i+1][j].numOfMines++;
		if(i>0)//up
			board[i-1][j].numOfMines++;
		if(j<width-1)//right
			board[i][j+1].numOfMines++;
		if(j>0)//left
			board[i][j-1].numOfMines++;
		if(i<height-1 &&j<width-1)//down and right
			board[i+1][j+1].numOfMines++;
		if(i>0 &&j<width-1)//up and right
			board[i-1][j+1].numOfMines++;
		if(i>0 &&j>0)//up and left
			board[i-1][j-1].numOfMines++;
		if(i<height-1&&j>0)//down and left
			board[i+1][j-1].numOfMines++;		
	}
	
	
	
	
	
	
	
	
	
	
	
}
