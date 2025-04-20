import java.io.*;
import java.util.*;

public class MergeSort {

    public static int[] createRandomArray(int arrayLength) {
        Random rand = new Random();
        int[] array = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            array[i] = rand.nextInt(101); 
        }
        return array;
    }

    public static void writeArrayToFile(int[] array, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int num : array) {
                writer.println(num);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + filename);
            e.printStackTrace();
        }
    }

    public static int[] readFileToArray(String filename) {
        List<Integer> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    list.add(Integer.parseInt(line));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            e.printStackTrace();
        }
        // Convert list to array
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static void bubbleSort(int[] array) {
        boolean swapped;
        for (int i = 0; i < array.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }
    
    public static void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
    
        int[] L = new int[n1];
        int[] R = new int[n2];
    
        for (int i = 0; i < n1; ++i)
            L[i] = array[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = array[mid + 1 + j];
    
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k++] = L[i++];
            } else {
                array[k++] = R[j++];
            }
        }
    
        while (i < n1) array[k++] = L[i++];
        while (j < n2) array[k++] = R[j++];
    }
    

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
    
        System.out.println("Choose an option:");
        System.out.println("1 - Generate random array and save to file");
        System.out.println("2 - Read array from file, sort it, and save to new file");
        System.out.print("Enter choice (1 or 2): ");
    
        int choice = scnr.nextInt();
        scnr.nextLine(); // consume newline
    
        if (choice == 1) {
            System.out.print("Enter array length: ");
            int length = scnr.nextInt();
            scnr.nextLine(); // consume newline
            System.out.print("Enter filename (ex. array1.txt) to save the array: ");
            String filename = scnr.nextLine();
    
            int[] array = createRandomArray(length);
            writeArrayToFile(array, filename);
            System.out.println("Random array written to " + filename + ". Now please run again with this file as your input to sort it with Merge Sort and Bubble Sort.");
    
        } else if (choice == 2) {
            System.out.print("Enter filename to read: ");
            String inputFile = scnr.nextLine();
            System.out.print("Enter filename (ex. merge1.txt) to save Merge Sort result: ");
            String mergeFile = scnr.nextLine();
            System.out.print("Enter filename (ex. bubble1.txt) to save Bubble Sort result: ");
            String bubbleFile = scnr.nextLine();
    
            int[] original = readFileToArray(inputFile);
            int[] mergeArr = Arrays.copyOf(original, original.length);
            int[] bubbleArr = Arrays.copyOf(original, original.length);
    
            long startMerge = System.nanoTime();
            mergeSort(mergeArr, 0, mergeArr.length - 1);
            long durationMerge = System.nanoTime() - startMerge;
            System.out.println("Merge Sort took " + durationMerge + " ns");
            writeArrayToFile(mergeArr, mergeFile);
            System.out.println("Sorted array via Merge Sort saved to " + mergeFile);
    
            long startBubble = System.nanoTime();
            bubbleSort(bubbleArr);
            long durationBubble = System.nanoTime() - startBubble;
            System.out.println("Bubble Sort took " + durationBubble + " ns");
            writeArrayToFile(bubbleArr, bubbleFile);
            System.out.println("Sorted array via Bubble Sort saved to " + bubbleFile);

            if (durationMerge < durationBubble) {
                System.out.println("Merge sort was " + (durationBubble - durationMerge) + " ns faster.");
            }
    
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
