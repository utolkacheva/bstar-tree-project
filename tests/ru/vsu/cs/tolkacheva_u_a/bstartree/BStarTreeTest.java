package ru.vsu.cs.tolkacheva_u_a.bstartree;

public class BStarTreeTest {
    public static void main(String[] args) {

        int passed = 0;
        int failed = 0;

        // Тест 1: Создание дерева
        if (testTreeCreation()) {
            System.out.println("testTreeCreation - PASSED");
            passed++;
        } else {
            System.out.println("testTreeCreation - FAILED");
            failed++;
        }

        // Тест 2: Вставка элементов
        if (testInsertion()) {
            System.out.println("testInsertion - PASSED");
            passed++;
        } else {
            System.out.println("testInsertion - FAILED");
            failed++;
        }

        // Тест 3: Поиск элементов
        if (testSearch()) {
            System.out.println("testSearch - PASSED");
            passed++;
        } else {
            System.out.println("testSearch - FAILED");
            failed++;
        }



        // Тест 4: Информация о дереве
        if (testTreeInfo()) {
            System.out.println("testTreeInfo - PASSED");
            passed++;
        } else {
            System.out.println("testTreeInfo - FAILED");
            failed++;
        }

        if (failed == 0) {
            System.out.println("All tests passed");
        } else {
            System.out.println("Some tests failed.");
        }
    }

    private static boolean testTreeCreation() {
        try {
            BStarTree<Integer> tree = new BStarTree<>(3);
            return tree != null && tree.isEmpty() && tree.size() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean testInsertion() {
        try {
            BStarTree<Integer> tree = new BStarTree<>(3);
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            return tree.size() == 3 && !tree.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean testSearch() {
        try {
            BStarTree<Integer> tree = new BStarTree<>(3);
            tree.insert(10);
            tree.insert(5);
            return tree.search(10) && tree.search(5) && !tree.search(99);
        } catch (Exception e) {
            return false;
        }
    }
    private static boolean testTreeInfo() {
        try {
            BStarTree<Integer> tree = new BStarTree<>(3);

            tree.insert(8);
            tree.insert(6);
            tree.insert(10);

            boolean orderCorrect = tree.getOrder() == 3;
            boolean minKeysCorrect = tree.getMinKeys() == 2;
            boolean maxKeysCorrect = tree.getMaxKeys() == 5;
            boolean hasHeight = tree.getHeight() > 0;
            boolean hasSize = tree.size() == 3;


            return orderCorrect && minKeysCorrect && maxKeysCorrect && hasHeight && hasSize;

        } catch (Exception e) {
            System.out.println("DEBUG TreeInfo Exception: " + e.getMessage());
            return false;
        }
    }

}
