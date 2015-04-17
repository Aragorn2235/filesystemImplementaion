
import java.io.*;
import java.util.*;

class TestFS 
{
   
    private static FileSystem fs;

  
    private static Hashtable vars = new Hashtable();

    public static void main(String [] args) throws FileNotFoundException{
    

       
        if (args.length > 1) System.err.println ("Usage: TestFS [filename]");

      
        boolean fromFile = (args.length==1);

      
        fs = new FileSystem();

      
        BufferedReader data = null;

     
        if (fromFile) {
            try {
                data = new BufferedReader (new FileReader(new File(args[0])));
            }
            catch (FileNotFoundException e) {
                System.err.println("Error: File " + args[0] + " not found.");
                System.exit(1);
            }
        }
        else data = new BufferedReader (new FileReader("C:\\Users\\avinash\\Desktop\\test.txt"));

      
        for (;;) {
            try {
              
                if (!fromFile) {
                    System.out.print("--> ");
                    System.out.flush();
                }

                
                String line = data.readLine();
                

               
                if (line == null) System.exit(0);  
                line = line.trim();                
                if (line.length() == 0) {          
                    System.out.println();
                    continue;
                }

                if (line.startsWith("//")) {
                    if (fromFile)
                        System.out.println(line);
                    continue;
                }
                if (line.startsWith("/*")) continue;
                if (fromFile) System.out.println("--> " + line);

          
                String target = null;
                int equals = line.indexOf('=');
                if (equals > 0) {
                    target = line.substring(0,equals).trim();
                    line = line.substring(equals+1).trim();
                }

             
                StringTokenizer cmds = new StringTokenizer (line);
                String cmd = cmds.nextToken();

                long result = 0;
                if (cmd.equalsIgnoreCase("formatDisk")
                        || cmd.equalsIgnoreCase("format"))
                {
                   
                    result = fs.formatDisk();
                }
                else if (cmd.equalsIgnoreCase("create")) {
                    result = fs.create(cmds.nextToken());
                  
                }
                else if (cmd.equalsIgnoreCase("open")) {
                	
                    result = fs.open(cmds.nextToken());
                } 
                else if (cmd.equalsIgnoreCase("inumber")) {
                    result = fs.inumber(cmds.nextToken());
                } 
                else if (cmd.equalsIgnoreCase("read")) {
                    String arg1 = cmds.nextToken();
                    int arg2 = nextValue(cmds);
                    result = readTest(arg1,arg2);
                } 
                else if (cmd.equalsIgnoreCase("write")) {
                   String arg1 = cmds.nextToken();
                   String arg2 = cmds.nextToken();
                    
                    result = writeTest(arg1,arg2,arg2.length());
                } 
                else if (cmd.equalsIgnoreCase("delete")) {
                    result = fs.delete(cmds.nextToken());
                } 
                else if (cmd.equalsIgnoreCase("quit")) {
                    System.exit(0);
                } else if(cmd.equalsIgnoreCase("bitmap")){
                	fs.showBit();
                }else if(cmd.equalsIgnoreCase("close")){
                	fs.close(cmds.nextToken());
                }else if(cmd.equalsIgnoreCase("superblock")){
                	fs.superB();
                }
                else{
                    System.out.println("unknown command");
                    continue;
                }

               
                if (target == null)
                    System.out.println("    Result is " + result);
                else {
                    vars.put(target,new Long(result));
                    System.out.println("    " + target + " = " + result);
                }
            }
          
            catch (NumberFormatException e) {
                System.out.println("Incorrect argument type");
            }
         
            catch (NoSuchElementException e) {
                System.out.println("Incorrect number of elements");
            }
            catch (IOException e) {
                System.err.println(e);
            }
        }
    } // main
    static private int nextValue(StringTokenizer cmds)
    {
        String arg = cmds.nextToken();
        Object val = vars.get(arg);
        return
            (val == null) ?  Integer.parseInt(arg) : ((Integer)val).intValue();
    }

   
    private static int readTest(String fd, int size) {
        byte[] buffer = new byte[size];
        int length;
        for (int i = 0; i < size; i++)
            buffer[i] = (byte) '*';
        length = fs.read(fd, buffer);
        for (int i = 0; i < length; i++) 
            showchar(buffer[i]);
        if (length != -1) System.out.println();
        return length;
    }

    private static int writeTest (String filename, String str, int size) {
        byte[] buffer = new byte[size];

        for (int i = 0; i < buffer.length; i++) 
            buffer[i] = (byte)str.charAt(i % str.length());

        return fs.write(filename, buffer);
    }
  
    private static void showchar(byte b) {
        if (b < 0) {
            System.out.print("M-");
            b += 0x80;             
        }
        if (b >= ' ' && b <= '~') {
            System.out.print((char)b);
            return;
        }
        switch (b) {
            case '\0': System.out.print("\\0"); return;
            case '\n': System.out.print("\\n"); return;
            case '\r': System.out.print("\\r"); return;
            case '\b': System.out.print("\\b"); return;
            case 0x7f: System.out.print("\\?"); return;
            default:   System.out.print("^" + (char)(b + '@')); return;
        }
    }
}
