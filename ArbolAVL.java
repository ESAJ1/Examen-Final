import java.util.Scanner;

/**
 * Implementación de un Árbol AVL 
 * Árbol binario de búsqueda balanceado donde la diferencia de altura
 * entre subárboles no excede 1
 */
public class ArbolAVL {
    
    // Clase para almacenar la información del estudiante
    private static class Estudiante {
        String nombre;
        String carnet;
        
        Estudiante(String nombre, String carnet) {
            this.nombre = nombre;
            this.carnet = carnet;
        }
        
        @Override
        public String toString() {
            return "Estudiante: " + nombre + " (Carnet: " + carnet + ")";
        }
    }
    
    // Clase interna para representar los nodos del árbol
    private static class Nodo {
        int dato;
        int altura;
        Nodo hijoIzq;
        Nodo hijoDer;
        
        Nodo(int dato) {
            this.dato = dato;
            this.altura = 1;
        }
    }
    
    private Nodo raiz;
    private Estudiante estudiante;
    
    /**
     * Establece la información del estudiante
     * @param nombre Nombre del estudiante
     * @param carnet Número de carnet
     */
    public void setEstudiante(String nombre, String carnet) {
        this.estudiante = new Estudiante(nombre, carnet);
    }
    
    /**
     * Obtiene la información del estudiante
     * @return Objeto Estudiante
     */
    public Estudiante getEstudiante() {
        return this.estudiante;
    }
    
    /**
     * Inserta un nuevo valor en el árbol AVL
     * @param valor El valor a insertar
     */
    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }
    
    /**
     * Implementación recursiva de la inserción con balanceo
     */
    private Nodo insertarRecursivo(Nodo nodo, int valor) {
        // Caso base: llegamos a una posición vacía
        if (nodo == null) {
            return new Nodo(valor);
        }
        
        // Búsqueda binaria para posicionar el nuevo valor
        if (valor < nodo.dato) {
            nodo.hijoIzq = insertarRecursivo(nodo.hijoIzq, valor);
        } else if (valor > nodo.dato) {
            nodo.hijoDer = insertarRecursivo(nodo.hijoDer, valor);
        } else {
            // Valor duplicado, retornamos el nodo sin cambios
            return nodo;
        }
        
        // Actualizar la altura del nodo actual
        actualizarAltura(nodo);
        
        // Verificar y corregir el balance
        return balancearNodo(nodo, valor);
    }
    
    /**
     * Actualiza la altura de un nodo basado en la altura de sus hijos
     */
    private void actualizarAltura(Nodo nodo) {
        int alturaIzquierda = (nodo.hijoIzq != null) ? nodo.hijoIzq.altura : 0;
        int alturaDerecha = (nodo.hijoDer != null) ? nodo.hijoDer.altura : 0;
        nodo.altura = Math.max(alturaIzquierda, alturaDerecha) + 1;
    }
    
    /**
     * Calcula el factor de balance de un nodo
     */
    private int factorBalance(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        int alturaIzquierda = (nodo.hijoIzq != null) ? nodo.hijoIzq.altura : 0;
        int alturaDerecha = (nodo.hijoDer != null) ? nodo.hijoDer.altura : 0;
        return alturaIzquierda - alturaDerecha;
    }
    
    /**
     * Realiza una rotación simple a la derecha
     */
    private Nodo rotacionDerecha(Nodo y) {
        Nodo x = y.hijoIzq;
        Nodo T2 = x.hijoDer;
        
        // Realizar rotación
        x.hijoDer = y;
        y.hijoIzq = T2;
        
        // Actualizar alturas
        actualizarAltura(y);
        actualizarAltura(x);
        
        return x;
    }
    
    /**
     * Realiza una rotación simple a la izquierda
     */
    private Nodo rotacionIzquierda(Nodo x) {
        Nodo y = x.hijoDer;
        Nodo T2 = y.hijoIzq;
        
        // Realizar rotación
        y.hijoIzq = x;
        x.hijoDer = T2;
        
        // Actualizar alturas
        actualizarAltura(x);
        actualizarAltura(y);
        
        return y;
    }
    
    /**
     * Verifica y corrige el balance del nodo según los 4 casos posibles
     */
    private Nodo balancearNodo(Nodo nodo, int valor) {
        // Obtener factor de balance
        int balance = factorBalance(nodo);
        
        // Caso 1: Desbalance izquierda-izquierda
        if (balance > 1 && valor < nodo.hijoIzq.dato) {
            System.out.println("Rotación simple derecha en nodo el" + nodo.dato);
            return rotacionDerecha(nodo);
        }
        
        // Caso 2: Desbalance derecha-derecha
        if (balance < -1 && valor > nodo.hijoDer.dato) {
            System.out.println("Rotación simple izquierda en nodo el" + nodo.dato);
            return rotacionIzquierda(nodo);
        }
        
        // Caso 3: Desbalance izquierda-derecha
        if (balance > 1 && valor > nodo.hijoIzq.dato) {
            System.out.println("Rotación doble izquierda-derecha en el nodo " + nodo.dato);
            nodo.hijoIzq = rotacionIzquierda(nodo.hijoIzq);
            return rotacionDerecha(nodo);
        }
        
        // Caso 4: Desbalance derecha-izquierda
        if (balance < -1 && valor < nodo.hijoDer.dato) {
            System.out.println("Rotación doble derecha-izquierda en el nodo " + nodo.dato);
            nodo.hijoDer = rotacionDerecha(nodo.hijoDer);
            return rotacionIzquierda(nodo);
        }
        
        // El nodo está balanceado
        return nodo;
    }
    
    /**
     * Imprime el árbol en formato jerárquico
     */
    public void mostrarArbol() {
        if (estudiante != null) {
            System.out.println("\n" + estudiante);
        }
        
        if (raiz == null) {
            System.out.println("(árbol vacío)");
        } else {
            imprimirJerarquia(raiz, "", true);
        }
    }
    
    /**
     * Método auxiliar para impresión jerárquica
     */
    private void imprimirJerarquia(Nodo nodo, String prefijo, boolean esUltimo) {
        if (nodo != null) {
            System.out.println(prefijo + (esUltimo ? "//" : "// ") + nodo.dato);
            
            // Imprimir hijo izquierdo
            imprimirJerarquia(nodo.hijoIzq, prefijo + (esUltimo ? "    " : "│   "), false);
            
            // Imprimir hijo derecho
            imprimirJerarquia(nodo.hijoDer, prefijo + (esUltimo ? "    " : "│   "), true);
        }
    }
    
    /**
     * Método principal para ejecutar el programa
     */
    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();
        // Solicitar información del estudiante
        try (Scanner scanner = new Scanner(System.in)) {
            // Solicitar información del estudiante
            System.out.println("=== SISTEMA DE ÁRBOL AVL ===");
            System.out.println("Por favor, ingrese sus datos:");
            
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Número de carnet: ");
            String carnet = scanner.nextLine();
            
        
            arbol.setEstudiante(nombre, carnet);
            
            System.out.println("\n¡Bienvenido, " + nombre + "!");
            System.out.println("Ingrese números para insertar en el árbol AVL (escriba -1 para culminar):");
            
            while (true) {
                System.out.print("=====> ");
                String entrada = scanner.nextLine();
                
                // Verificar condición de salida
                if (entrada.equalsIgnoreCase("exit") || entrada.equals("-1")) {
                    break;
                }
                
                try {
                    int numero = Integer.parseInt(entrada);
                    arbol.insertar(numero);
                    System.out.println("\nÁrbol AVL actualizado:");
                    arbol.mostrarArbol();
                    System.out.println();
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor ingrese un número entero.");
                }
            }
            
            System.out.println("\nResumen final:");
            arbol.mostrarArbol();
            System.out.println("\nFinalizado ¡Gracias por usar el sistema!");
        }
    }
}