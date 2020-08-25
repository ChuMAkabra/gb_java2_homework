package Assignment_01;

import java.util.ArrayList;
import java.util.Collections;

/** 3.	Задача:
 a.	Даны классы Fruit, Apple extends Fruit, Orange extends Fruit;
 b.	Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
 c.	Для хранения фруктов внутри коробки можно использовать ArrayList;
 d.	Сделать метод getWeight(), который высчитывает вес коробки, зная вес одного фрукта и их количество: вес яблока – 1.0f, апельсина – 1.5f (единицы измерения не важны);
 e.	Внутри класса Box сделать метод compare(), который позволяет сравнить текущую коробку с той, которую подадут в compare() в качестве параметра. true – если их массы равны, false в противоположном случае. Можно сравнивать коробки с яблоками и апельсинами;
 f.	Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую. Помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами. Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в первой;
 g.	Не забываем про метод добавления фрукта в коробку.
 **/

public class Box<T extends Fruit> {
    private final ArrayList<T> fruitBox = new ArrayList<>();
    private float weight;

    @SafeVarargs
    public Box(T... fruits) {
        Collections.addAll(fruitBox, fruits);
        calculateWeight();
    }

    // вопреки заданию сделал метод getWeight простым геттером, а calculateWeight методом подсчета
    public void calculateWeight() {
        if (fruitBox.size() > 0) {
            weight = fruitBox.size() * fruitBox.get(0).getWeight();
        }
        else weight = 0;
    }

    public float getWeight() {
        return weight;
    }

    public boolean compare(Box<?> fruitBox) {
        return (Math.abs(this.getWeight() - fruitBox.getWeight()) < 0.0001);
    }

    public void transfer(Box<T> fruitBox) {
        while (this.fruitBox.size() > 0) {
            fruitBox.add(this.fruitBox.remove(0));
        }
        weight = 0;
    }

    private void add(T obj) {
        fruitBox.add(obj);
        calculateWeight();
    }

    @Override
    public String toString() {
        return "Box. Count: " + fruitBox.size() + ". Weight: " + getWeight() + " kilos";
    }
}