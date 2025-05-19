import java.io.File;

public class Main {
    public static void main(String[] args) {
        File fileC = new File("C:\\");
        File fileD = new File("D:\\");
        
        // For C drive
        long totalSpaceC = fileC.getTotalSpace();
        long freeSpaceC = fileC.getFreeSpace();
        long usableSpaceC = fileC.getUsableSpace();
        
        // For D drive
        long totalSpaceD = fileD.getTotalSpace();
        long freeSpaceD = fileD.getFreeSpace();
        long usableSpaceD = fileD.getUsableSpace();
        
        System.out.printf("C drive total size: %.2f GB%n", totalSpaceC / (float)(1024 * 1024 * 1024));
        System.out.printf("C drive free space: %.2f GB%n", freeSpaceC / (float)(1024 * 1024 * 1024));
        System.out.printf("C drive usable space: %.2f GB%n", usableSpaceC / (float)(1024 * 1024 * 1024));
        
        System.out.printf("%nD drive total size: %.2f GB%n", totalSpaceD / (float)(1024 * 1024 * 1024));
        System.out.printf("D drive free space: %.2f GB%n", freeSpaceD / (float)(1024 * 1024 * 1024));
        System.out.printf("D drive usable space: %.2f GB%n", usableSpaceD / (float)(1024 * 1024 * 1024));
    }
}