import java.util.*;
import java.io.*;

class FileTable {
	//FileSystem f=new FileSystem();
    public static final int MAX_FILES =4; 
    public int bitmap[][];
 public FileTable(){
	bitmap = new int[MAX_FILES][2];
	for(int i = 0; i < MAX_FILES; i++){
	    bitmap[i][0] = 0;
	    bitmap[i][1] = 0;
	}
    }
    public int allocate(){
	for(int i = 0; i < MAX_FILES; i++){
	    if(bitmap[i][0] == 0)
		return i;
	}
	System.out.println("Cannot open file... filetable is full.");
	return -1;
    }
    public int search(int inumber){
    	for(int i = 0; i < MAX_FILES; i++){
    	    if(bitmap[i][1] == inumber)
    		return i;
    	}
    	return -1;
    }
    public int add( int inumber, int fd){
	if(bitmap[fd][0] != 0)
	    return -1;
	bitmap[fd][0] = 1;
	bitmap[fd][1]=inumber;
	return 0;  
    }
    

    public void free(int fd){
	bitmap[fd][0] = 0;
    }

	

    public int getInode(int fd){
	if(bitmap[fd][0] == 0){
	    return -1;
	}
	else{
	    return bitmap[fd][1] ;
	}
    }






    }

