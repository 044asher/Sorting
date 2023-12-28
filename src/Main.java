import java.util.*;

public class Main {
    private static void swap(int[] array, int ind1, int ind2) {
        int tmp = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = tmp;
    }

    //O(n^2)   Улучшенная сортировка пузырьком
    public static int[] shuttleSort(int[] array) {
        long startTime = System.nanoTime();
        // Внешний цикл проходит по всем элементам массива
        for (int i = 1; i < array.length; i++) {
            // Если текущий элемент меньше предыдущего, меняем их местами
            if (array[i] < array[i - 1]) {
                swap(array, i, i - 1);
                // Внутренний цикл перемещается в обратном направлении,
                // мы будем в этом цикле, пока не сдвинем наш меньший элемент в начало, где ему место
                for (int j = i - 1; j > 0 && array[j] < array[j - 1]; j--) {
                    swap(array, j, j - 1);
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Shuttle sort time: " + (endTime - startTime) + " nano sec.");
        return array;
    }

    // O(n^2)
    public static int[] shellSort(int[] array){
        long startTime = System.nanoTime();

        int gap = array.length / 2; // Инициализация разрыва (gap) равным половине длины массива

        while (gap >= 1) { //Выполняем сортировку пока наш разрыв >= 1
            for (int i = 0; i < array.length; i++) { // Внешний цикл пробегает по всем элементам массива
            // Внутренний цикл выполняет сравнение и обмен элементов с шагом gap
                for (int j = i - gap; j >= 0; j = j - gap) {
                    if (array[j] > array[j + gap]) { // Если текущий элемент больше элемента с шагом gap, меняем их местами
                        swap(array, j, j + gap);
                    }
                }
            }
            // Пересчитываем разрыв
            gap = gap / 2;
        }
        long endTime = System.nanoTime();
        System.out.println("Shell sort time: " + (endTime - startTime) + " nano sec.");
        return array;
    }

    //O(log n)
    public static int[] treeSort(int[] array){
        long startTime = System.nanoTime();
        Set<Integer> treeSet = new TreeSet<>();
        for (int num : array) {
            treeSet.add(num);
        }

        int[] sortedArray = new int[treeSet.size()];
        int index = 0;
        for(int num : treeSet){
            sortedArray[index++] = num;
        }
        long endTime = System.nanoTime();
        System.out.println("Tree sort time: " + (endTime - startTime) + " nano sec.");
        return sortedArray;

    }

    //O(n^2)
    public static int[] gnomeSort(int[] array){
        long startTime = System.nanoTime();
        int index = 0;
        while(index < array.length){
            if(index == 0 || array[index] >= array[index - 1]){  // Если элемент находится в начале или больше предыдущего, двигаемся вправо
                index++;
            }
            else {  // Если элемент меньше предыдущего, меняем их местами и двигаемся влево
                swap(array, index, index - 1);
                index--;
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Gnome sort time: " + (endTime - startTime) + " nano sec.");
        return array;
    }

    //О(log n)
    public static int[] heapSort(int[] array){
        long startTime = System.nanoTime();
        int n = array.length;

        //Строим макс-кучу(для каждого узла X с ключом K ключи потомков X не меньше K)
        //                15
        //               /  \
        //              10   7
        //             / \
        //            5   3
        //Начинаем с последнего уровня и идем вверх по дереву
        for (int i = n / 2 - 1; i >= 0 ; i--) {
            heapSort(array, n, i);
        }
        // Постепенно извлекаем максимальный элемент и уменьшаем размер нашей кучи
        for (int i = n - 1; i > 0; i--) {
            swap(array, 0, i); // Обмениваем максимальный элемент(который под индексом 0) с последним элементом в массиве
            heapSort(array, i, 0);
        }
        long endTime = System.nanoTime();
        System.out.println("Heap sort time: " + (endTime - startTime) + " nano sec.");
        return array;
    }
    private static void heapSort(int[] array, int n, int i){
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if(left < n && array[left] > array[largest]){
            largest = left;
        }
        if(right < n && array[right] > array[largest]){
            largest = right;
        }
        if(largest != i){
            swap(array, i, largest);
            heapSort(array, n, largest);
        }
    }

    // O(n + k), где n - количество элементов, k - размер диапазона значений
    // О(n) - если k > n
    public static int[] countingSort(int[] array){
        long startTime = System.nanoTime();

        //Находим максимальное и минимальное значение в массиве
        int max = array[0];
        int min = array[0];

        for(int value : array){
            if(value > max){
                max = value;
            }
            if(value < min){
                min = value;
            }
        }

        //Вычисляем размер временного массива(диапазон значений)
        int range = max - min + 1;

        //Создаем временный массив для подсчета частоты встречаемости
        int[] countArray = new int[range];
        for(int value : array){
            countArray[value - min]++; //Смотрим как часто встречается элемент и ставим его в ячейку value - min
        }

        // Восстанавливаем отсортированный массив из временного массива
        int index = 0;
        for (int i = 0; i < range; i++) {
            while (countArray[i] > 0) {
                array[index++] = i + min;
                countArray[i]--;
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Counting sort time: " + (endTime - startTime) + " nano sec.");

        return array;
    }

    //В среднем случае: O(n + n^2/k + k), где n - количество элементов в массиве, k - количество ведер.
    //O(n) если данные равномерно распределены
    //O(n^2), когда все элементы попадают в одно ведро и требуется выполнить сортировку внутри него.
    public static int[] bucketSort(int[] array){
        long startTime = System.nanoTime();

        int min = array[0];
        int max = array[0];

        // Находим минимальное и максимальное значения в массиве
        for (int value : array) {
            if (value < min) {
                min = value;
            } else if (value > max) {
                max = value;
            }
        }
        // Определяем количество блоков (ведер)
        int bucketCount = (max - min) / 5 + 1; // 5 + 1 задается по стандарту в этой сортировке,
        // так как является самым продуктивным значением
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);

        // Инициализируем каждое ведро
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new LinkedList<>());
        }
        // Распределяем элементы по ведрам
        for (int value : array) {
            int bucketIndex = (value - min) * bucketCount / (max - min + 1);
            buckets.get(bucketIndex).add(value);
        }

        // Сортируем каждое ведро
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }

        // Объединяем отсортированные ведра в итоговый массив
        int index = 0;
        for (List<Integer> bucket : buckets) {
            for (int value : bucket) {
                array[index++] = value;
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Bucket sort time: " + (endTime - startTime) + " nano sec.");
        return array;
    }

    //По специальному алгоритму разделяем входной массив на подмассивы.
    //Сортируем каждый подмассив обычной сортировкой вставками.
    //Собираем отсортированные подмассивы в единый массив с помощью модифицированной сортировки слиянием.
    //Сложность О(n log n)
    public static int[] timSort(int[] array){
        int n = array.length;
        int minrun = minrunLength(n);
        //Делим массив на подмассивы размером minrun и сортируем каждый из них
        for (int i = 0; i < n; i += minrun) {
            insertionSort(array);
        }

        //Слияние отсортированных блоков. Начинаем слияние с мини-блоков (minrun) и удваиваем размер блоков
        //на каждой итерации.
        for (int size = minrun; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) { //Проходит по массиву и сливает отсортированные блоки размером size. Переменная left представляет начало текущего блока.
                int mid = left + size - 1; //индекс середины текущего блока.
                int right = Math.min((left + 2 * size - 1), (n - 1)); //Индекс конца текущего блока. Если размер блока превышает size, то right ограничивается размером массива n - 1.

                if (mid < right) {
                    mergeSort(array);
                }
            }
        }
        return array;
    }

    private static void insertionSort(int [] array){ //Устойчивая сортировка
        int n = array.length;
        for (int left = 0; left < n; left++) {
            int value = array[left]; //Запоминаем значение текущего элемента массива
            int i = left - 1; //Индекс для перемещения влево и сравнения с другими элементами массива

            for (; i >= 0; i--) { //Ставим условие чтобы і со значением -1 не попало во внутренний цикл
                if(value < array[i]){//Если вытащили элемент меньше - передвигаем больший элемент дальше.
                    array[i + 1] = array[i];//сдвигаем элементы вправо
                }
                else break;//если вытащенный элемент больше - останавливаемся
            }
            array[i + 1] = value;// В освободившееся место вставляем вытащенное значение
        }
    }

    private static void mergeSort(int[] array) {
        if (array.length <= 1) { //обрабатываем базовый случай.
            return;
        }
        int middle = array.length / 2; //делим массив пополам, на левый и правый
        int[] left = Arrays.copyOfRange(array, 0, middle);//создаем левую половину массива
        int[] right = Arrays.copyOfRange(array, middle, array.length);//создаем правую половину массива

        mergeSort(left);//делим еще пополам и так далее, пока не получим единичные элементы, или же наш обработанный базовый случай.
        mergeSort(right);

        merge(array, left, right);//объединяем левую и правую половины сначала для левой части которую делили, потом для правой и потом их вместе
    }

    private static void merge(int[] array, int[] left, int[] right) {//объединяем левую и правую части
        int i = 0, j = 0, k = 0; //создаем индекс i для массива left, j для right и k для результирующего array
        while (i < left.length && j < right.length) {//итерируем пока не дойдем до конца одного из массивов
            if (left[i] <= right[j]) {//на каждом шаге сравниваем текущие элементы
                array[k++] = left[i++];//если left[i] меньше или равно за right[j], записываем left[i] в результирующий массив array
            } else {
                array[k++] = right[j++];//иначе записываем right[j] в результирующий массив array и увеличиваем k и j.
            }
        }
    }
    private static int minrunLength(int n){
        int r = 0;
        while(n >= 64){
            r |= n & 1; // " |= " - применяется для установки бита в 1 в переменной r на позиции,
            //соответствующей младшему биту переменной n.
            // Оператор n & 1 извлекает младший бит из n.
            n >>= 1; // Сдвигает биты переменной n вправо на 1 позицию. Это эквивалентно делению n на 2.
        }
        return n + r; // n содержит все биты исходного числа, кроме 6 младших, и r содержит 6 младших битов. Таким образом, возвращается число,
        // в котором 6 младших битов заменены на соответствующие биты переменной r.
    }


    //O(n)                     LSD Version)) - least significant digit
    //O(n^2) - Если число цифр в наибольшем элементе равно n
    public static int[] radixSort(int[] array){
        long startTime = System.nanoTime();
        radixSort(array, array.length); //На каждом шаге будем сортировать числа чтобы они были отсортированы по первым k * i битам, где k – некоторая константа.
        long endTime = System.nanoTime();
        System.out.println("Radix Sort time: " + (endTime - startTime) + " nano sec.");
        return array;
    }
    private static int digit(int n, int k, int N, int M){ // Метод для извлечения k-го разряда числа n при представлении в системе счисления с основанием M и N битами в каждом разряде
        return (n >> (N * k) & (M - 1)); // Выполняем сдвиг n на k*N бит вправо (эквивалентно делению числа n на 2^(N * k))
    } //Результат сдвига "пересекается" с маской (M - 1). Маска создается для того, чтобы оставить только младшие M бит результата сдвига.
    private static void radixSort(int[] array, int N){
        int k = (32 + N - 1) / N; // Вычисляем количество проходов сортировки по N битам в каждом разряде
        int M = 1 << N; // Вычисляем основание системы счисления (операция равносильна 2^N)
        int sz = array.length;
        int[] b = new int[sz]; // Вспомогательный массив для сохранения промежуточных результатов сортировки
        int[] c = new int[M]; // Массив для подсчета количества элементов в каждой корзине

        // Проходим по всем разрядам
        for (int i = 0; i < k; i++) {
            Arrays.fill(c, 0);  // Обнуляем массив подсчета перед каждым проходом

            // Подсчитываем количество элементов в каждой корзине для текущего разряда
            for (int value : array) {
                c[digit(value, i, N, M)]++;
            }

            // Преобразуем массив c, чтобы он содержал позиции начала каждой корзины в массиве b
            for (int j = 1; j < M; j++)
                c[j] += c[j - 1]; //После этого цикла, c[j] будет содержать сумму всех элементов массива 'c' с индексами от 0 до j.

            // Распределяем элементы из массива array в массив b в соответствии с их разрядами
            for (int j = sz - 1; j >= 0; j--)
                b[--c[digit(array[j], i, N, M)]] = array[j];

            // Копируем отсортированные элементы из массива b обратно в массив array
            System.arraycopy(b, 0, array, 0, sz);
        }
    }

    //О(n^2) - в худшем случае
    //O(nlog(n)) для наилучшего случая
    public static int[] combSort(int[] array) {
        long startTime = System.nanoTime();
        int gap = array.length; //Выступает в роли шага
        boolean swapped = true; //Флаг указывающий на то, произошел ли обмен элементов

        while (gap > 1 || swapped) { //Цикл продолжается до тех пор, пока шаг больше 1 или произошёл обмен элементами.
            gap = (int) (gap / 1.3); //Уменьшаем шаг в соответствии с заданным коэффициентом.
            swapped = false; //обнуляем флаг
            for (int i = 0; i + gap < array.length; i++) { //Проходим по массиву с шагом и обмениваем элементы, если они находятся в неправильном порядке.
                if (array[i] > array[i + gap]) {
                    int temp = array[i];
                    array[i] = array[i + gap];
                    array[i + gap] = temp;
                    swapped = true;
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Comb sort time: " + (endTime - startTime) + " nano sec.");
        return array;
    }
    public static void main(String[] args) {
        int[] array = {10, 5, 6, 1, 2, 3, 4, 9, 8, 7, 2};
        System.out.println("Unsorted array: " + Arrays.toString(array) + "\n");

        int[] shuttleSortedArray = shuttleSort(Arrays.copyOf(array, array.length));
        System.out.println("Shuttle Sort: " + Arrays.toString(shuttleSortedArray) + "\n");

        int[] shellSortedArray = shellSort(Arrays.copyOf(array, array.length));
        System.out.println("Shell Sort: " + Arrays.toString(shellSortedArray) + "\n");

        int[] treeSortedArray = treeSort(Arrays.copyOf(array, array.length));
        System.out.println("ТОЛЬКО ДЛЯ УНИКАЛЬНЫХ ЭЛЕМЕНТОВ! Tree Sort: " + Arrays.toString(treeSortedArray) + "\n");

        int[] gnomeSortedArray = gnomeSort(Arrays.copyOf(array, array.length));
        System.out.println("Gnome Sort: " + Arrays.toString(gnomeSortedArray) + "\n");

        int[] heapSortedArray = heapSort(Arrays.copyOf(array, array.length));
        System.out.println("Heap Sort: " + Arrays.toString(heapSortedArray) + "\n");

        int[] countingSortedArray = countingSort(Arrays.copyOf(array, array.length));
        System.out.println("Counting Sort: " + Arrays.toString(countingSortedArray) + "\n");

        int[] bucketSortedArray = bucketSort(Arrays.copyOf(array, array.length));
        System.out.println("Bucket Sort: " + Arrays.toString(bucketSortedArray) + "\n");

        long startTime = System.nanoTime();
        int[] timSortedArray = timSort(Arrays.copyOf(array, array.length));
        long endTime = System.nanoTime();
        System.out.println("TimSort time: " + (endTime - startTime) + " nano sec.");
        System.out.println("TimSort: " + Arrays.toString(timSortedArray) + "\n");

        int[] radixSortedArray = radixSort(Arrays.copyOf(array, array.length));
        System.out.println("Radix Sort: " + Arrays.toString(radixSortedArray) + "\n");

        int[] combSortedArray = combSort(Arrays.copyOf(array, array.length));
        System.out.println("Comb Sort: " + Arrays.toString(combSortedArray) + "\n");
    }
}