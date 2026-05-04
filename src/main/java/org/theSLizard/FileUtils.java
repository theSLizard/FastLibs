package org.theSLizard;

import java.io.File;

public class FileUtils {
    /** Recursively prints all sub‑directories of the supplied folder. */
    public static void findDirectories(File dir) {
        // Safety check: only work on existing directories
        if (dir == null || !dir.isDirectory()) return;

        File[] entries = dir.listFiles();          // list files & dirs in this folder
        if (entries == null) return;               // I/O error, or not a directory

        for (File f : entries) {
            if (f.isDirectory()) {                 // we only care about directories
                System.out.println(f.getAbsolutePath());

                // Recurse into the sub‑directory
                findDirectories(f);
            }
        }
    }
}
