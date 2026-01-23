package com.mycompany.rompecabezas;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * Utilidad para medir el consumo de memoria durante la ejecución de tareas.
 * 
 * Esta clase proporciona métodos para:
 * - Obtener la memoria utilizada actualmente por la JVM
 * - Medir el consumo de memoria de una tarea específica de forma aislada
 * 
 * ESTRATEGIA DE MEDICIÓN:
 * 1. Forzar múltiples ciclos de recolección de basura para limpiar memoria
 * 2. Capturar memoria base (línea base limpia)
 * 3. Ejecutar la tarea UNA SOLA VEZ
 * 4. Tomar múltiples muestras de memoria después de ejecutar
 * 5. Usar la muestra máxima (captura antes de que GC automático limpie)
 * 
 * JUSTIFICACIÓN:
 * - Múltiples GC antes aseguran línea base limpia
 * - Múltiples muestras después capturan el pico de consumo
 * - Solo se ejecuta la tarea una vez (no altera métricas)
 * - Para reportes académicos, refleja el uso máximo de memoria
 * 
 * NOTA: Los resultados pueden variar entre ejecuciones debido al
 * comportamiento no determinista del recolector de basura de la JVM.
 */
public class MemoryTracker {

    /**
     * Calcula la memoria actualmente utilizada por la JVM en bytes.
     * 
     * Usa MemoryMXBean para obtener información más precisa del heap usado.
     * 
     * @return Cantidad de memoria utilizada en bytes
     */
    public static long usedMemory() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        return heapUsage.getUsed();
    }

    /**
     * Mide el consumo de memoria de una tarea específica usando MemoryMXBean.
     * 
     * Este método ejecuta la tarea UNA SOLA VEZ y calcula el consumo de memoria
     * del heap usando la API de gestión de la JVM, que es más precisa que Runtime.
     * 
     * ESTRATEGIA:
     * - Forzar GC para establecer línea base limpia
     * - Obtener uso del heap antes (MemoryMXBean)
     * - Ejecutar la tarea una sola vez
     * - Obtener uso del heap después
     * - Calcular diferencia
     * 
     * @param task La tarea a ejecutar y medir (Runnable)
     * @return Memoria adicional consumida en bytes (siempre >= 0)
     */
    public static long measure(Runnable task) {
        // Forzar GC múltiples veces para limpiar memoria
        for (int i = 0; i < 5; i++) {
            System.gc();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // Obtener memoria usada antes (usando MemoryMXBean para mayor precisión)
        long before = usedMemory();
        
        // Ejecutar la tarea UNA SOLA VEZ
        task.run();
        
        // Obtener memoria usada después inmediatamente
        long after = usedMemory();
        
        // Calcular la diferencia
        long diff = after - before;
        
        // Retornar el máximo entre 0 y la diferencia
        return Math.max(0, diff);
    }

    /**
     * Convierte bytes a una representación legible (KB, MB, GB).
     * 
     * @param bytes Cantidad de bytes a convertir
     * @return String con formato legible (ejemplo: "2.5 MB")
     */
    public static String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " bytes";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }
}
