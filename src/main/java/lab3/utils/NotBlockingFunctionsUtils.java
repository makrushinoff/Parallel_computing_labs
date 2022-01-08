package lab3.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import lab3.lambda.FindElement;
import lab3.lambda.ScalarProduct;
import lab3.model.Element;
import lab3.threads.FindElementThread;
import lab3.threads.FindScalarProductThread;

public final class NotBlockingFunctionsUtils {

    private static final int ELEMENTS_PER_THREAD = 3000;

    private NotBlockingFunctionsUtils(){}

    private static ExecutorService executorService;

    public static int xorArray(Integer[] array) {
        AtomicInteger sum = new AtomicInteger();
        Arrays.stream(array).parallel()
                .forEach((num) -> {
                    int oldValue;
                    int newValue;
                    do{
                        oldValue = sum.get();
                        newValue = oldValue ^ num;
                    } while(!sum.compareAndSet(oldValue, newValue));
                });
        return sum.get();
    }

    public static Element maxElement(long[] array) throws InterruptedException {
        int threadsNumber = getThreadsNumber(array.length);
        executorService = Executors.newFixedThreadPool(threadsNumber);
        List<FindElementThread> threadsList = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            int startIndex;
            int endIndex;
            for(int j = 0; j < array.length; j+= ELEMENTS_PER_THREAD) {
                startIndex = j;
                endIndex = j + ELEMENTS_PER_THREAD >= array.length ? (array.length - 1) : (j + ELEMENTS_PER_THREAD);
                threadsList.add(new FindElementThread(getMaxElementLambda(), array, startIndex, endIndex));
            }
        }
        List<Element> resultList = getElementsResultList(threadsList);
        Element maxElement = new Element();
        maxElement.setElement(Long.MIN_VALUE);
        maxElement.setElementIndex(0);
        for(Element element : resultList) {
            if(maxElement.compareTo(element) < 0) {
                maxElement = element;
            }
        }

        return maxElement;
    }

    public static Element minElement(long[] array) throws InterruptedException {
        int threadsNumber = getThreadsNumber(array.length);
        executorService = Executors.newFixedThreadPool(threadsNumber);
        List<FindElementThread> threadsList = new ArrayList<>();
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < threadsNumber; i++) {
            for(int j = 0; j < array.length; j+= ELEMENTS_PER_THREAD) {
                startIndex = j;
                endIndex = j + ELEMENTS_PER_THREAD >= array.length ? (array.length - 1) : (j + ELEMENTS_PER_THREAD);
                threadsList.add(new FindElementThread(getMinElementLambda(), array, startIndex, endIndex));
            }
        }
        List<Element> resultList = getElementsResultList(threadsList);
        Element minElement = new Element();
        minElement.setElement(Long.MAX_VALUE);
        minElement.setElementIndex(0);
        for(Element element : resultList) {
            if(minElement.compareTo(element) > 0) {
                minElement = element;
            }
        }

        return minElement;
    }

    private static List<Element> getElementsResultList(List<FindElementThread> threadList) throws InterruptedException {
        List<Element> result = executorService.invokeAll(threadList).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
        executorService.shutdown();
        return result;
    }

    private static List<Integer> getIntegerResultList(List<FindScalarProductThread> threadList) throws InterruptedException {
        List<Integer> result = executorService.invokeAll(threadList).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
        executorService.shutdown();
        return result;
    }

    public static long mode(long[] array) {
        Map<Long, Integer> map = new HashMap<>();
        Arrays.stream(array).parallel()
                .forEach(num -> {
                    if(map.containsKey(num)) {
                        AtomicInteger atomicInteger = new AtomicInteger();
                        Integer oldValue;
                        Integer newValue;
                        do {
                            oldValue = map.get(num);
                            newValue = oldValue + 1;
                            map.remove(num);
                            map.put(num, newValue);
                        } while (atomicInteger.compareAndSet(oldValue, newValue));
                    } else {
                        map.put(num, 1);
                    }
                });
        int max = Integer.MIN_VALUE;
        AtomicLong keyMax = new AtomicLong(Long.MIN_VALUE);
        map.forEach((key, value) -> {
            if(value > max) {
                keyMax.set(key);
            }
        });
        return keyMax.get();
    }

    public static long median(long[] array) {
        return Arrays.stream(array).sorted()
                .filter(num -> {
                    if(array.length % 2 == 0){
                        return num >= (array[array.length / 2]) || num <= (array[ array.length/2 - 1]) ;
                    } else {
                        return num == array[array.length / 2];
                    }
                })
                .map(num -> {
                    if (array.length % 2 == 0) {
                        return (array[array.length / 2] + array[ array.length/2 - 1]) / 2;
                    }
                    return num;
                })
                .findFirst().getAsLong();
    }

    public static int scalarProduct(List<Integer> firstVector, List<Integer> secondVector) throws InterruptedException {
        if(firstVector.size() != secondVector.size()) {
            throw new IllegalArgumentException("Vectors should contain equal number of elements");
        }
        int threadsNumber = getThreadsNumber(firstVector.size());
        executorService = Executors.newFixedThreadPool(threadsNumber);
        List<FindScalarProductThread> threadsList = new ArrayList<>();
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < threadsNumber; i++) {
            for(int j = 0; j < firstVector.size(); j+= ELEMENTS_PER_THREAD) {
                startIndex = j;
                endIndex = j + ELEMENTS_PER_THREAD >= firstVector.size() ? (firstVector.size() - 1) : (j + ELEMENTS_PER_THREAD);
                threadsList.add(new FindScalarProductThread(getScalarProductLambda(), firstVector, secondVector, startIndex, endIndex));
            }
        }
        List<Integer> resultList = getIntegerResultList(threadsList);
        AtomicInteger resultProduct = new AtomicInteger();
        resultList.stream().parallel()
                .forEach(num -> {
                    int oldValue;
                    int newValue;
                    do {
                        oldValue = resultProduct.get();
                        newValue = num + oldValue;
                    } while(!resultProduct.compareAndSet(oldValue, newValue));
                });
        return resultProduct.get();
    }

    private static int getThreadsNumber(int arraySize) {
        int threadsNumber;
        if(arraySize < ELEMENTS_PER_THREAD) {
            threadsNumber = 1;
        } else{
            threadsNumber = arraySize / 3000;
        }

        return threadsNumber;
    }


    public static FindElement getMaxElementLambda() {
        return (long[] array, int startIndex, int endIndex) -> {
            Element element = new Element();
            element.setElement(Long.MIN_VALUE);
            element.setElementIndex(0);
            for (int i = startIndex; i <= endIndex; i++) {
                if (element.getElement() < array[i]) {
                    element.setElement(array[i]);
                    element.setElementIndex(i);
                }
            }
            return element;
        };
    }

    public static FindElement getMinElementLambda() {
        return (long[] array, int startIndex, int endIndex) -> {
            Element element = new Element();
            element.setElement(Long.MAX_VALUE);
            element.setElementIndex(0);
            for (int i = startIndex; i <= endIndex; i++) {
                if (element.getElement() > array[i]) {
                    element.setElement(array[i]);
                    element.setElementIndex(i);
                }
            }
            return element;
        };
    }

    public static ScalarProduct getScalarProductLambda() {
        return (List<Integer> firstVector, List<Integer> secondVector, int startIndex, int endIndex) -> {
            int result = 0;
            for(int i = startIndex; i < endIndex; i++) {
                result += firstVector.get(i) * secondVector.get(i);
            }
            return result;
        };
    }

}
