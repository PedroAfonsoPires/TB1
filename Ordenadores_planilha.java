import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Ordenadores {

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
            T temp = array[minIdx];
            array[minIdx] = array[i];
            array[i] = temp;
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
    public static <T extends Comparable<T>> void quicksort(T[] array, int lo, int hi) {
        if (lo < hi) {
            int pi = partition(array, lo, hi);
            quicksort(array, lo, pi - 1);
            quicksort(array, pi + 1, hi);
        }
    }

    private static <T extends Comparable<T>> int partition(T[] array, int lo, int hi) {
        T pivot = array[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        T temp = array[i + 1];
        array[i + 1] = array[hi];
        array[hi] = temp;
        return i + 1;
    }
    // Método de medição de tempo
    public static <T extends Comparable<T>> long medirTempo(T[] array, String metodo) {
        long inicio = System.nanoTime();
        switch (metodo) {
            case "bubblesort": bubblesort(array); break;
            case "insertionsort": insertionsort(array); break;
            case "selectionsort": selectionsort(array); break;
            case "shellsort": shellsort(array); break;
            case "heapsort": heapsort(array); break;
            case "mergesort": mergesort(array); break;
            case "quicksort": quicksort(array, 0, array.length - 1); break;
            case "radixsort": radixsort((Integer[]) array); break;
            case "countingsort": countingsort((Integer[]) array); break;
            case "bucketsort": bucketsort((Integer[]) array); break;
            default: throw new IllegalArgumentException("Método de ordenação inválido: " + metodo);
        }
        return System.nanoTime() - inicio;
    }

    // Método para criar arrays baseados nas opções do usuário
    public static Integer[] criarArray(int n, String tipo) {
        Integer[] array = new Integer[n];
        switch (tipo.toLowerCase()) {
            case "crescente":
                for (int i = 0; i < n; i++) array[i] = i;
                break;
            case "decrescente":
                for (int i = 0; i < n; i++) array[i] = n - i - 1;
                break;
            case "aleatorio":
                Random random = new Random();
                for (int i = 0; i < n; i++) array[i] = random.nextInt(1000);
                break;
            case "repetidos":
                for (int i = 0; i < n; i++) array[i] = (i % 5);
                break;
            default:
                throw new IllegalArgumentException("Tipo de array inválido: " + tipo);
        }
        return array;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] metodos = {"bubblesort", "insertionsort", "selectionsort", "shellsort", "heapsort", "mergesort", "quicksort", "radixsort", "countingsort", "bucketsort"};
        ArrayList<String> resultados = new ArrayList<>(); // Lista para armazenar resultados

        while (true) {
            System.out.println("\nEscolha o método de ordenação ou digite 'sair' para encerrar:");
            for (int i = 0; i < metodos.length; i++) {
                System.out.println((i + 1) + ". " + metodos[i]);
            }

            System.out.print("Escolha: ");
            String metodoEscolha = scanner.next();

            if (metodoEscolha.equalsIgnoreCase("sair")) break;

            int metodoIndex;
            try {
                metodoIndex = Integer.parseInt(metodoEscolha) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            if (metodoIndex < 0 || metodoIndex >= metodos.length) {
                System.out.println("Opção inválida.");
                continue;
            }

            String metodo = metodos[metodoIndex];

            System.out.println("\nEscolha o tipo de array:");
            System.out.println("1. Crescente");
            System.out.println("2. Decrescente");
            System.out.println("3. Aleatório");
            System.out.println("4. Repetidos");

            System.out.print("Escolha: ");
            String tipoEscolha = scanner.next();

            String tipo;
            switch (tipoEscolha) {
                case "1": tipo = "crescente"; break;
                case "2": tipo = "decrescente"; break;
                case "3": tipo = "aleatorio"; break;
                case "4": tipo = "repetidos"; break;
                default: System.out.println("Opção inválida."); continue;
            }

            int[] tamanhos = {100, 1000, 10000, 100000, 1000000};
            for (int tamanho : tamanhos) {
                Integer[] array = criarArray(tamanho, tipo);
                Integer[] copia = Arrays.copyOf(array, array.length);
                long tempo = medirTempo(copia, metodo);

                // Adiciona o resultado no formato desejado
                resultados.add(metodo + " - " + tipo + " - " + tamanho + " - " + tempo);
                System.out.println(metodo + " com array " + tipo + " de tamanho " + tamanho + ": " + tempo + " ns");
            }
        }

        // Salvar resultados em um arquivo CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter("resultados.csv"))) {
            writer.println("Metodo,Tipo de Array,Tamanho,Tempo (ns)"); // Cabeçalho
            for (String linha : resultados) {
                writer.println(linha.replace(" - ", ",")); // Converte para CSV
            }
            System.out.println("Resultados salvos em 'resultados.csv'.");
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo CSV: " + e.getMessage());
        }

        scanner.close();
    }
}
