import java.io.IOException;


public class FileSystem {
	int blockInt=1;
	int count1=Disk.BLOCK_SIZE/Inode.SIZE;
	SuperBlock sb=new SuperBlock();
	FileTable ft=new FileTable();
	SuperBlock Block=new SuperBlock();
	InodeBlock table=new InodeBlock();
	Disk dis=new Disk();
	long currentfd;
	int count=1;
	
    public int formatDisk() {
    	try{
    	for(int i=0;i<count1;i++)
    		table.node[i]=null;
    	}catch(NullPointerException e ){
    		System.out.println("Disk is formatted");
    	}
		return 0;
	} 
    public long getInumb(int i){
    	
    	return table.node[i].owner;
    }
    

    public long create(String name) throws IOException {
    	int temp=1;
    	int t=ft.allocate();
    	if(t==-1){
    		//System.out.println("file table exceeded");
    		return -1;
    	}
    
    	//System.out.println(currentfd+"cu");
    	
    	while(!(table.node[temp].flags==null)){
    		temp++;
    	}
    	table.node[temp].flags=name;//name
    	table.node[temp].fileSize=dis.disk.getFilePointer();//next pointer
    	dis.disk.seek((temp-1)*512);
    	currentfd=dis.disk.getFilePointer();
    	table.node[temp].owner=currentfd;//current address
    	System.out.println(table.node[temp].flags);
    	//count++;
    	ft.add(temp,t);
    	//System.out.println(currentfd);
		return currentfd;
	} 
    

    public int inumber(String filename) {
    	int temp=1;
    	//System.out.println(table.node[temp].flags);
    	//System.out.println(filename);
    	while(!((table.node[temp].flags).equals(filename)) && temp < count1){
    		temp++;
    	}
		return temp;
	} 
    

    public long open(String filename) {
    	try{
    	int temp1=ft.allocate();
		if(temp1==-1)
			return -1;
		else{
    	  
    	int temp=1;
    	//System.out.println(filename);
    	while(!((table.node[temp].flags).equals(filename)) && temp < count1){
    		temp++;
    	}
    	ft.add(temp,temp1);
    	return table.node[temp].owner;
    	
		}
    	}catch(NullPointerException e){
    		System.out.println(filename+" "+"doesn't exist");
    		return -1;
    	}
	} 
    

    public int read(String filename, byte[] buffer) {
    	//dis.read(inumber(filename), buffer);
    	try{
    		dis.read(inumber(filename), buffer);
    	}catch(NullPointerException e){
    		System.out.println(filename+" "+"doesn't exist");
    		return -1;
    	}
		return buffer.length;
		
	} 
    
  
    public int write(String filename, byte[] buffer) {
    	try{
    		dis.write(inumber(filename), buffer);
    	}catch(NullPointerException e){
    		System.out.println(filename+" "+"doesn't exist");
    		return -1;
    	}
		return buffer.length;
	} 
    
   


 
    public int delete(String filename) {
    	
    	try{
    		//close(filename);
        	int temp=inumber(filename);
        	table.node[temp].flags=null;
    	}catch(NullPointerException e){
    		//System.out.println(filename+" "+"doesn't exist");
    		return -1;
    	}
		return 0;
	} 
    public void superB() throws IOException{
    	long currentfd1;
    	int temp=1;
    	while(!(table.node[temp].flags==null)){
    		temp++;
    	}
    	dis.disk.seek((temp-1)*512);
    	currentfd1=dis.disk.getFilePointer();
        sb.freeList=currentfd1;
        sb.size=dis.NUM_BLOCKS;
        sb.iSize=dis.BLOCK_SIZE/Inode.SIZE;
        sb.toString();
    	
    }
    public void close(String filename){
    	
    		int c=ft.search(inumber(filename));
        	ft.bitmap[c][0]=0;
    
    	
    	
    }
    public void showBit(){
    	System.out.println("File Table");
    	for(int i=0;i<ft.MAX_FILES;i++){
    		
    		System.out.println(ft.bitmap[i][0] +" "+ft.bitmap[i][1]);
    	}
    }
}
