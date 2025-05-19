package memoryinspection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MemoryLayoutInspector {
    private static int staticField = 10;  // Class variable
    private int instanceField = 10;       // Instance variable

    public static void main(String[] args) {
        try {
            inspectMemoryLayout();
        } catch (Exception e) {
            System.err.println("Memory inspection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void inspectMemoryLayout() throws Exception {
        // Warning: Unsafe usage is not recommended in production code
        System.out.println("WARNING: sun.misc.Unsafe usage is potentially dangerous");

        // Get fields using reflection
        Field staticFieldRef = MemoryLayoutInspector.class.getDeclaredField("staticField");
        Field instanceFieldRef = MemoryLayoutInspector.class.getDeclaredField("instanceField");

        // Print basic field information
        System.out.println("\nField Metadata:");
        printFieldInfo(staticFieldRef);
        printFieldInfo(instanceFieldRef);

        // Get memory offsets (Unsafe required)
        try {
            Object unsafe = getUnsafeInstance();
            Class<?> unsafeClass = unsafe.getClass();

            // Get offset methods
            long staticOffset = (long) unsafeClass.getMethod("staticFieldOffset", Field.class)
                    .invoke(unsafe, staticFieldRef);
            long instanceOffset = (long) unsafeClass.getMethod("objectFieldOffset", Field.class)
                    .invoke(unsafe, instanceFieldRef);

            System.out.println("\nMemory Offsets:");
            System.out.println("Static field offset: " + staticOffset);
            System.out.println("Instance field offset: " + instanceOffset);
        } catch (Exception e) {
            System.err.println("Unsafe operations not supported in this environment");
            throw e;
        }
    }

    private static void printFieldInfo(Field field) {
        System.out.println("Field '" + field.getName() + "': " +
                "Type=" + field.getType().getSimpleName() + ", " +
                "Modifiers=" + Modifier.toString(field.getModifiers()));
    }

    /**
     * Gets Unsafe instance reflectively (not recommended for production)
     */
    private static Object getUnsafeInstance() throws Exception {
        try {
            Field theUnsafe = Class.forName("sun.misc.Unsafe").getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return theUnsafe.get(null);
        } catch (Exception e) {
            throw new IllegalStateException("Unsafe not available", e);
        }
    }
}