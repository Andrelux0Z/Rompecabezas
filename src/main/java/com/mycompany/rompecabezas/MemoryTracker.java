package com.mycompany.rompecabezas;

import java.lang.management.ManagementFactory;

// Clase para medir uso de memoria
public class MemoryTracker {

    // Retorna memoria heap usada actualmente
    public static long usedMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    // Mide bytes asignados durante la ejecución de una tarea
    public static long measure(Runnable task) {
        java.lang.management.ThreadMXBean baseBean = ManagementFactory.getThreadMXBean();

        if (!(baseBean instanceof com.sun.management.ThreadMXBean)) {
            throw new UnsupportedOperationException("Tracking de memoria no disponible en esta JVM.");
        }

        com.sun.management.ThreadMXBean bean = (com.sun.management.ThreadMXBean) baseBean;

        if (!bean.isThreadAllocatedMemorySupported()) {
            throw new UnsupportedOperationException("Tracking de memoria no soportado.");
        }

        if (!bean.isThreadAllocatedMemoryEnabled()) {
            bean.setThreadAllocatedMemoryEnabled(true);
        }

        long threadId = Thread.currentThread().getId();
        long before = bean.getThreadAllocatedBytes(threadId);

        task.run();

        long after = bean.getThreadAllocatedBytes(threadId);

        long diff = after - before;
        return Math.max(0, diff);
    }

    // Convierte bytes a formato legible
    public static String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " bytes";
        } else if (bytes < 1024L * 1024L) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024L * 1024L * 1024L) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }
}
