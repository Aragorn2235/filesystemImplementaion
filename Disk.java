import java.util.*;
import java.io.*;

class Disk {

    public final static int BLOCK_SIZE = 512;
  
    public final static int NUM_BLOCKS = 1000;

    private int readCount = 0;
	private int writeCount = 0;
	private File fileName;
	RandomAccessFile disk;
	public Disk() {
		try {
			fileName = new File("DISK");
			disk = new RandomAccessFile(fileName, "rw");
			System.out.println("file opened");
		}
		catch (IOException e) {
			System.err.println ("Unable to start the disk");
			System.exit(1);
		}
	}

	private void seek(int blocknum) throws IOException {
		if (blocknum < 0 || blocknum >= NUM_BLOCKS) 
			throw new RuntimeException ("Attempt to read block " +
					       blocknum + " is out of range");
		disk.seek((long)(blocknum * BLOCK_SIZE));
	}

	public void read(int blocknum, byte[] buffer) {

		try {
			seek(blocknum*512);
			disk.read(buffer);
		}
		catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
		readCount++;
	} 


	public void write(int blocknum, byte[] buffer) {

		try {
			seek(blocknum*512);
			disk.write(buffer);
		}
		catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
		writeCount++;
	}
	

 


}
