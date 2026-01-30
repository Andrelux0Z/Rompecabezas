package com.mycompany.rompecabezas;

import java.lang.management.ManagementFactory;

public class MemoryTracker {

    /**
     * Heap usado instantáneo (NO es "allocated bytes").
     * Se deja por compatibilidad.
     */
    public static long usedMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    /**
     * Mide bytes ASIGNADOS (allocated) por el hilo actual durante task.run().
     * Ejecuta la tarea UNA SOLA VEZ.
     *
     * @param task Runnable a medir
     * @return bytes asignados por el hilo durante task.run() (>= 0)
     */
    public static long measure(Runnable task) {
        java.lang.management.ThreadMXBean baseBean = ManagementFactory.getThreadMXBean();

        // Las APIs de allocated bytes están en com.sun.management.ThreadMXBean
        if (!(baseBean instanceof com.sun.management.ThreadMXBean)) {
            throw new UnsupportedOperationException(
                "Allocated-bytes tracking no disponible en esta JVM (ThreadMXBean no es com.sun.management.ThreadMXBean)."
            );
        }

        com.sun.management.ThreadMXBean bean = (com.sun.management.ThreadMXBean) baseBean;

        if (!bean.isThreadAllocatedMemorySupported()) {
            throw new UnsupportedOperationException("Allocated-bytes tracking no soportado por esta JVM.");
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

    /**
     * Convierte bytes a una representación legible (KB, MB, GB).
     */
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
