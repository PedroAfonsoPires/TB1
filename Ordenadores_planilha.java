import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Ordenadores_planilha {

    // Counting Sort
    public static void countingsort(Integer[] array) {
        if (array.length == 0) return;

        int max = Arrays.stream(array).max(Integer::compare).orElseThrow();
        int min = Arrays.stream(array).min(Integer::compare).orElseThrow();
        int range = max - min + 1;

        int[] count = new int[range];
        Integer[] output = new Integer[array.length];

        for (int num : array) {
            count[num - min]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = array.length - 1; i >= 0; i--) {
            output[count[array[i] - min] - 1] = array[i];
            count[array[i] - min]--;
        }

        System.arraycopy(output, 0, array, 0, array.length);
    }

    // Radix Sort
    public static void radixsort(Integer[] array) {
        if (array.length == 0) return;

        int max = Arrays.stream(array).max(Integer::compare).orElseThrow();
        if (max < 0) throw new IllegalArgumentException("Radix Sort não funciona com números negativos.");

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingsortByDigit(array, exp);
        }
    }

    private static void countingsortByDigit(Integer[] array, int exp) {
        int[] count = new int[10];
        Integer[] output = new Integer[array.length];

        for (int num : array) {
            int index = (num / exp) % 10;
            count[index]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = array.length - 1; i >= 0; i--) {
            int index = (array[i] / exp) % 10;
            output[count[index] - 1] = array[i];
            count[index]--;
        }

        System.arraycopy(output, 0, array, 0, array.length);
    }

    // Bucket Sort
    public static void bucketsort(Integer[] array) {
        if (array.length == 0) return;

        int max = Arrays.stream(array).max(Integer::compare).orElseThrow();
        int min = Arrays.stream(array).min(Integer::compare).orElseThrow();
        int bucketCount = (int) Math.sqrt(array.length);

        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int num : array) {
            int bucketIndex = (num - min) * bucketCount / (max - min + 1);
            buckets.get(bucketIndex).add(num);
        }

        int index = 0;
        for (ArrayList<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for (int num : bucket) {
                array[index++] = num;
            }
        }
    }

    // Bubble Sort
    public static <T extends Comparable<T>> void bubblesort(T[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    // Insertion Sort
    public static <T extends Comparable<T>> void insertionsort(T[] array) {
        for (int i = 1; i < array.length; i++) {
            T key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].compareTo(key) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    // Selection Sort
    public static <T extends Comparable<T>> void selectionsort(T[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j].compareTo(array[minIdx]) < 0) {
                    minIdx = j;
                }
            }

            // Evita troca desnecessária se o minIdx já for igual ao i
            if (minIdx != i) {
                T temp = array[minIdx];
                array[minIdx] = array[i];
                array[i] = temp;
            }
        }
    }

    // Shell Sort
    public static <T extends Comparable<T>> void shellsort(T[] array) {
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                T temp = array[i];
                int j;
                for (j = i; j >= gap && array[j - gap].compareTo(temp) > 0; j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
    }

    // Heap Sort
    public static <T extends Comparable<T>> void heapsort(T[] array) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            T temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapify(array, i, 0);
        }
    }

    private static <T extends Comparable<T>> void heapify(T[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && array[left].compareTo(array[largest]) > 0) {
            largest = left;
        }
        if (right < n && array[right].compareTo(array[largest]) > 0) {
            largest = right;
        }
        if (largest != i) {
            T swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            heapify(array, n, largest);
        }
    }

    // Merge Sort
    public static <T extends Comparable<T>> void mergesort(T[] array) {
        if (array.length > 1) {
            int mid = array.length / 2;
            T[] left = Arrays.copyOfRange(array, 0, mid);
            T[] right = Arrays.copyOfRange(array, mid, array.length);
            mergesort(left);
            mergesort(right);
            merge(array, left, right);
        }
    }

    private static <T extends Comparable<T>> void merge(T[] array, T[] left, T[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) <= 0) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        while (i < left.length) array[k++] = left[i++];
        while (j < right.length) array[k++] = right[j++];
    }

    // Quick Sort
    public static <T extends Comparable<T>> void quicksort(T[] array) {
        quicksort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void quicksort(T[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionMediana(array, low, high);
            quicksort(array, low, pivotIndex - 1);
            quicksort(array, pivotIndex + 1, high);
        }
    }

    private static <T extends Comparable<T>> int partitionMediana(T[] array, int low, int high) {
        int mid = low + (high - low) / 2;
        if (array[low].compareTo(array[mid]) > 0) swap(array, low, mid);
        if (array[low].compareTo(array[high]) > 0) swap(array, low, high);
        if (array[mid].compareTo(array[high]) > 0) swap(array, mid, high);

        swap(array, mid, high - 1);
        T pivot = array[high - 1];

        int i = low;
        int j = high - 2; // Ajuste para começar uma posição antes do pivô

        while (true) {
            while (i < high - 1 && array[++i].compareTo(pivot) < 0); // Limite i para não ultrapassar high - 1
            while (j > low && array[--j].compareTo(pivot) > 0);       // Limite j para não ultrapassar low
            if (i >= j) break;
            swap(array, i, j);
        }

        swap(array, i, high - 1);
        return i;
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Métodos para gerar arrays
    public static Integer[] gerarArrayCrescente(int tamanho) {
        Integer[] array = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = i;
        }
        return array;
    }

    public static Integer[] gerarArrayDecrescente(int tamanho) {
        Integer[] array = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = tamanho - i - 1;
        }
        return array;
    }

    public static Integer[] gerarArrayAleatorio(int tamanho) {
        Integer[] array = new Integer[tamanho];
        Random random = new Random();
        for (int i = 0; i < tamanho; i++) {
            array[i] = random.nextInt(tamanho);
        }
        return array;
    }

    public static Integer[] gerarArrayRepetidos(int tamanho) {
        Integer[] array = new Integer[tamanho];
        Random random = new Random();
        int valorRepetido = random.nextInt(tamanho);
        for (int i = 0; i < tamanho; i++) {
            array[i] = valorRepetido;
        }
        return array;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] metodos = {"Counting Sort", "Radix Sort", "Bucket Sort", "Bubble Sort", "Insertion Sort", "Selection Sort",
                "Shell Sort", "Heap Sort", "Merge Sort", "Quick Sort"};

        String[] tipos = {"Crescente", "Decrescente", "Aleatório", "Repetidos"};
        int[] tamanhos = {100, 1000, 10000, 100000, 1000000};
        Random random = new Random();

        while (true) {  // Loop para retornar ao menu após cada execução
            System.out.println("Escolha o método de ordenação:");
            System.out.println("0. Sair");  // Opção de saída
            for (int i = 0; i < metodos.length; i++) {
                System.out.printf("%d. %s%n", i + 1, metodos[i]);
            }
            System.out.printf("Escolha uma opção: ");
            int metodoEscolhido = scanner.nextInt();
            System.out.println("\n");

            if (metodoEscolhido == 0) {
                System.out.println("Programa encerrado.");
                break;  // Encerra o loop e finaliza o programa
            }

            System.out.println("Escolha o tipo de array:");
            for (int i = 0; i < tipos.length; i++) {
                System.out.printf("%d. %s%n", i + 1, tipos[i]);
            }
            System.out.printf("Escolha uma opção: ");
            int tipoEscolhido = scanner.nextInt();
            System.out.println("\n");

            try (PrintWriter writer = new PrintWriter(new FileWriter("resultados.csv", true))) {
                writer.println("Método,Tipo,Tamanho,Execução,Tempo(ns)");

                for (int tamanho : tamanhos) {
                    for (int execucao = 1; execucao <= 10; execucao++) {
                        Integer[] array;
                        switch (tipoEscolhido) {
                            case 1: // Crescente
                                array = gerarArrayCrescente(tamanho);
                                break;
                            case 2: // Decrescente
                                array = gerarArrayDecrescente(tamanho);
                                break;
                            case 3: // Aleatório
                                array = gerarArrayAleatorio(tamanho);
                                break;
                            case 4: // Repetidos
                                array = gerarArrayRepetidos(tamanho);
                                break;
                            default:
                                throw new IllegalArgumentException("Tipo de array inválido.");
                        }

                        long tempoInicio = System.nanoTime();
                        switch (metodoEscolhido) {
                            case 1:
                                countingsort(array);
                                break;
                            case 2:
                                radixsort(array);
                                break;
                            case 3:
                                bucketsort(array);
                                break;
                            case 4:
                                bubblesort(array);
                                break;
                            case 5:
                                insertionsort(array);
                                break;
                            case 6:
                                selectionsort(array);
                                break;
                            case 7:
                                shellsort(array);
                                break;
                            case 8:
                                heapsort(array);
                                break;
                            case 9:
                                mergesort(array);
                                break;
                            case 10:
                                quicksort(array);
                                break;
                            default:
                                throw new IllegalArgumentException("Método de ordenação inválido.");
                        }
                        long tempoFim = System.nanoTime();
                        long tempoExecucao = tempoFim - tempoInicio;

                        String metodo = metodos[metodoEscolhido - 1];
                        String tipo = tipos[tipoEscolhido - 1];

                        // Escreve o resultado no arquivo CSV
                        writer.printf("%s,%s,%d,%d,%d%n", metodo, tipo, tamanho, execucao, tempoExecucao);

                        // Exibe o resultado no console
                        System.out.printf("Método: %s, Tipo: %s, Tamanho: %d, Execução: %d, Tempo: %d ns%n", metodo, tipo, tamanho, execucao, tempoExecucao);
                    }
                }
            } catch (IOException e) {
                System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
            }
        }
        scanner.close();  // Fecha o scanner no final do programa
    }
}