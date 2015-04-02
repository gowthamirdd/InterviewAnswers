
import java.io.*;
import java.nio.channels.FileChannel;


/**
 * Created by Gowthami on 4/2/15.
 */
public class FileSystemExample{

    public static void main (String[] args){
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String arguments;
        FileSystemExample fileSystemExample = new FileSystemExample();
        System.out.print("$");
        String path = null;
        try {
            while((arguments = br.readLine()) != null){
                String [] splitArguments = arguments.split(" ");
                String command = splitArguments[0];
                if(command.equalsIgnoreCase("mkdir")){
                    if(splitArguments.length < 2 || splitArguments.length > 2){
                        throw new RuntimeException("Creating directory fails since arguments on console doesn't match. The lenght is : " + splitArguments.length);
                    }
                    path = splitArguments[1];
                    fileSystemExample.createDirectory(splitArguments[1]);
                }
                else if(command.equalsIgnoreCase("exit")){
                    if(path!=null) {
                        File file = new File(path);
                        fileSystemExample.deleteDirectory(file);
                    }
                    System.exit(0);
                }
                else if(command.equalsIgnoreCase("create")){
                    if(splitArguments.length < 2 || splitArguments.length > 2){
                        throw new RuntimeException("Creating file fails since arguments on console doesn't match. The lenght is : " + splitArguments.length);
                    }
                    fileSystemExample.createFile(splitArguments[1]);
                }
                else if(command.equalsIgnoreCase("write")){
                    if(splitArguments.length < 3 || splitArguments.length > 3){
                        throw new RuntimeException("writing to file fails since arguments on console doesn't match. The lenght is : " + splitArguments.length);
                    }
                    fileSystemExample.writeToFile(splitArguments[1],splitArguments[2]);
                }
                else if(command.equalsIgnoreCase("cat")){
                    if(splitArguments.length < 2 || splitArguments.length > 2){
                        throw new RuntimeException("Creating file fails since arguments on console doesn't match. The lenght is : " + splitArguments.length);
                    }
                    fileSystemExample.cat(splitArguments[1]);
                }
                else if(command.equalsIgnoreCase("cp")){
                    if(splitArguments.length < 3 || splitArguments.length > 3){
                        throw new RuntimeException("copying files fails since arguments on console doesn't match. The lenght is : " + splitArguments.length);
                    }
                    fileSystemExample.copyFiles(splitArguments[1], splitArguments[2]);
                }
                else if(command.equalsIgnoreCase("cd")){
                    if(splitArguments.length < 3 || splitArguments.length > 3){
                        throw new RuntimeException("copying directories fails since arguments on console doesn't match. The lenght is : " + splitArguments.length);
                    }
                    File sourceDir = new File(splitArguments[1]);
                    File destDir = new File(splitArguments[2]);
                    fileSystemExample.copyDirectory(sourceDir, destDir);
                }
                else if(command.equalsIgnoreCase("find")){
                    if(splitArguments.length < 2 || splitArguments.length > 2){
                        throw new RuntimeException("Finding file fails since arguments on console doesn't match. The lenght is : " + splitArguments.length);
                    }
                    File dir = new File(".");
                    fileSystemExample.findFile(splitArguments[1], dir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDirectory(String path) throws IOException {
        if(new File(path).exists()){
            throw new IOException(path + " already exists");
        }else {
            File file = new File(path);
            file.mkdir();
        }
    }

    private void createFile(String fileName) throws IOException {
        File file = new File(fileName);
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

    private void writeToFile(String path, String content) throws IOException {
        File file = new File(path);
        if(file.exists()){
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(content);
            writer.close();
        } else{
            file.getParentFile().mkdirs();
            file.createNewFile();
            writeToFile(path,content);
        }
    }

    private void cat(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            throw new IOException(path + " not found in file system");
        } else{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while((line = bufferedReader.readLine()) != null){
                System.out.println(line);
            }
        }
    }

    private void copyFiles(String source, String destination) throws IOException {
        File sourceFile = new File(source);
        File destinationFile = new File(destination);

        if(!sourceFile.exists()){
            throw new IOException(source + " not found in file system.");
        } else{
            if(!destinationFile.exists()){
                destinationFile.getParentFile().mkdirs();
                destinationFile.createNewFile();
            }
            FileChannel src = new FileInputStream(sourceFile).getChannel();
            FileChannel dest = new FileOutputStream(destinationFile).getChannel();
            dest.transferFrom(src,0,src.size());
        }
    }

    private void copyDirectory(File sourceDir, File destDir) throws IOException {
        if(!sourceDir.exists()){
            throw new IOException(sourceDir + " not found in file system.");
        }
        if(sourceDir.isDirectory()){
            if(!destDir.exists()){
                destDir.mkdir();
            }
            for(String file : sourceDir.list()){
                File srcFile = new File(sourceDir, file);
                File destFile = new File(destDir,file);
                copyDirectory(srcFile,destFile);
            }
        } else{
            FileChannel src = new FileInputStream(sourceDir).getChannel();
            FileChannel dest = new FileOutputStream(destDir).getChannel();
            dest.transferFrom(src,0,src.size());
        }
    }

    private void findFile(String filePath, File dir) throws IOException {
        File [] files = dir.listFiles();
        if(files != null){
            for(File file : files){
                if(file.isDirectory()){
                    findFile(filePath,file);
                } else{
                    if(filePath.equalsIgnoreCase(file.getName())){
                        System.out.println(filePath + " is found at " + file.getParentFile());
                    }
                }
            }
        }
    }

    private void deleteDirectory(File filePath){
        String[] files = filePath.list();

        for(String file : files){
            File currentFile = new File(filePath.getPath(),file);
            currentFile.delete();
        }

        filePath.delete();
    }
}

