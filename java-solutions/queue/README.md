#### Домашнее задание 3. Очередь на массиве

1. Определите модель и найдите инвариант структуры
   данных «[очередь](http://ru.wikipedia.org/wiki/%D0%9E%D1%87%D0%B5%D1%80%D0%B5%D0%B4%D1%8C_(%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5))».
    * Определите функции, которые необходимы для реализации очереди.
    * Найдите их пред- и постусловия, при условии, что очередь не содержит `null`.
2. Реализуйте классы, представляющие **циклическую** очередь на основе массива.
    * Класс `ArrayQueueModule` должен реализовывать один экземпляр очереди с использованием переменных класса.
    * Класс `ArrayQueueADT` должен реализовывать очередь в виде абстрактного типа данных (с явной передачей ссылки на
      экземпляр очереди).
    * Класс `ArrayQueue` должен реализовывать очередь в виде класса (с неявной передачей ссылки на экземпляр очереди).
    * Должны быть реализованы следующие функции (процедуры) / методы:
        * `enqueue` – добавить элемент в очередь;
        * `element` – первый элемент в очереди;
        * `dequeue` – удалить и вернуть первый элемент в очереди;
        * `size` – текущий размер очереди;
        * `isEmpty` – является ли очередь пустой;
        * `clear` – удалить все элементы из очереди.
    * Модель, инвариант, пред- и постусловия записываются в исходном коде в виде комментариев.
    * Обратите внимание на инкапсуляцию данных и кода во всех трех реализациях.
3. Напишите тесты к реализованным классам.

#### Домашнее задание 4. Очереди

1. Определите интерфейс очереди `Queue` и опишите его контракт.
2. Реализуйте класс `LinkedQueue` — очередь на связном списке.
3. Выделите общие части классов `LinkedQueue` и `ArrayQueue` в базовый класс `AbstractQueue`.

Это домашнее задание _связано_ с предыдущим.