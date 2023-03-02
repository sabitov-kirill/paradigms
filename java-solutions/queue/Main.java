package queue;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.Random;

public class Main {
    private static class Tester {
        private final ArrayQueueADT queueADT = ArrayQueueADT.create();
        private final ArrayQueue queueClass = new ArrayQueue();
        private final Deque<Object> queueModel = new ArrayDeque<>();

        private final int testCasesCount;
        private final int testCaseOperationsMin;
        private final int testCaseOperationsMax;

        public Tester(int testCasesCount, int testCaseOperationsMin, int testCaseOperationsMax) {
            this.testCasesCount = testCasesCount;
            this.testCaseOperationsMin = testCaseOperationsMin;
            this.testCaseOperationsMax = testCaseOperationsMax;
        }

        private void ensureElement(Supplier<Object> elementGetter, Object expectedElement, String queueType) {
            Object queueElement = elementGetter.get();
            assert expectedElement.equals(queueElement) :
                    "Array queue " + queueType + " first element is wrong: expected " +
                            expectedElement + ", got: " + queueElement;
        }

        private void ensureElement() {
            Object expectedElement = queueModel.removeFirst();

            ensureElement(ArrayQueueModule::element, expectedElement, "module");
            ensureElement(() -> ArrayQueueADT.element(queueADT), expectedElement, "ADT");
            ensureElement(queueClass::element, expectedElement, "class");

            ensureElement(ArrayQueueModule::dequeue, expectedElement, "module");
            ensureElement(() -> ArrayQueueADT.dequeue(queueADT), expectedElement, "ADT");
            ensureElement(queueClass::dequeue, expectedElement, "class");
        }

        private void ensureSize(IntSupplier sizeGetter, int expectedSize, String queueType) {
            int queueSize = sizeGetter.getAsInt();
            assert queueSize == expectedSize :
                    "Array queue " + queueType + " size is wrong: expected " + expectedSize + ", got: " + queueSize;
        }

        private int getSize() {
            int expectedSize = queueModel.size();
            ensureSize(ArrayQueueModule::size, expectedSize, "module");
            ensureSize(() -> ArrayQueueADT.size(queueADT), expectedSize, "ADT");
            ensureSize(queueClass::size, expectedSize, "class");

            return expectedSize;
        }

        private void ensureState() {
            do {
                ensureElement();
            } while (getSize() > 0);
        }

        private void addElement(Object element) {
            queueModel.addLast(element);

            ArrayQueueModule.enqueue(element);
            ArrayQueueADT.enqueue(queueADT, element);
            queueClass.enqueue(element);
        }

        private void clear() {
            queueModel.clear();

            ArrayQueueModule.clear();
            ArrayQueueADT.clear(queueADT);
            queueClass.clear();
        }

        public void test() {
            Random random = new Random();

            int testCasesPrintCycle = testCasesCount / 10;
            System.out.println("Testing queues.");
            for (int testCase = 1; testCase <= testCasesCount; testCase++) {
                int operationsCount = random.nextInt(testCaseOperationsMin, testCaseOperationsMax);
                int operationsPrintCycle = operationsCount / 10;

                for (int operation = 0; operation < operationsCount; operation++) {
                    addElement(random.nextInt());
                    if (operation % operationsPrintCycle == 0) {
                        System.out.print("\rTest case " + testCase + ", operation " +
                                operation + "/" + operationsCount + ".");
                    }
                }

                ensureState();
                clear();
                if (testCase % testCasesPrintCycle == 0) {
                    System.out.println("\rPassed " + testCase + "/" + testCasesCount + " repeat cycles.");
                }
            }
            System.out.println("Tests successfully passed.");
        }

        public void testWithTiming() {
            long startTime = System.currentTimeMillis();
            test();
            System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime) + " ms.");
        }
    }

    public static void main(String[] args) {
        Tester tester = new Tester(5000, 10000, 50000);
        tester.testWithTiming();
    }
}
