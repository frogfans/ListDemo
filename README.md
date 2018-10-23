# ArrayList和LinkedList的性能对比

## 一、 数据结构

### ArrayList
![](https://github.com/frogfans/ListDemo/blob/master/image/arraylist.png)

ArrayList在内存中是连续的、单向的、有序的。

ArrayList中维护了一个按照下标顺序的一维数组，数组中每个item指向对应的value。
```
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    transient Object[] elementData;
    
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }
    
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
```

当我们通过下标进行指定位置增加或者删除操作时，先由下标index从item数组找到对应item，然后在其后面增加一个item或者删除该item，接着从这个下标开始向后，把剩下的item通过复制，向前或者向后移动一位。
```
    // 增加
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        ensureCapacityInternal(size + 1);  // Increments modCount!!
        System.arraycopy(elementData, index, elementData, index + 1,
                         size - index);
        elementData[index] = element;
        size++;
    }

    // 删除
    public E remove(int index) {
        rangeCheck(index);

        modCount++;
        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }
```

当我们通过下标进行指定位置查找或者修改操作时，先由下标index从item数组获取对应item，返回或者修改item指向的对象地址。
```
    // 查找
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }
    
    // 修改
    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }
    
    E elementData(int index) {
        return (E) elementData[index];
    }
```

---
### LinkedList
![](https://github.com/frogfans/ListDemo/blob/master/image/linkedlist.png)

LinkedList在内存中是不连续的、双向的、有序的。

LinkedList中的每一个item，称为node，它包含三个部分：当前node的value，指向上一个node的指针prev、指向下一个node的指针next。由于LinkedList还保存了第一个node称为first和最后个node称为last，所以他还是双向的。
```
    transient Node<E> first;
    transient Node<E> last;
    public LinkedList() {
    }
    
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
```

当我们通过下标进行指定位置增加或者删除操作时，会从first node开始，不断地由node的next找到下一个node，直到到达目标node，如果是增加操作，则创建一个node，将上一个node的next指向新node的prev，将下一个node的prev指向新node的next，这样就在list中间重新连接起来；如果是删除操作，则删除当前node，将上一个node的next指向下一个node的prev。
```
    // 增加
    public void add(int index, E element) {
        checkPositionIndex(index);

        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }
    
    void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
    }

    void linkBefore(E e, Node<E> succ) {
        // assert succ != null;
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
        modCount++;
    }
    
    // 删除
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }
    
    E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }
```

当我们通过下标进行指定位置查找或者修改操作时，会从first node或者last node（LinkedList.get(int index)方法中，会对下标值进行判断，如果小于长度的一半，则从first开始，否则从last开始，这是对双向性的利用）开始，不断地由node的next去找下一个node的地址，直到到达目标node，返回value或者修改value指向的对象地址。
```
    // 查找
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }
    
    // 修改
    public E set(int index, E element) {
        checkElementIndex(index);
        Node<E> x = node(index);
        E oldVal = x.item;
        x.item = element;
        return oldVal;
    }
    
    Node<E> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
```

---
## 二、 性能对比

我们构造相同长度的ArrayList和LinkedList，由于增加操作会改变数组长度，导致时间增加，我们用一次增加一次删除交替进行，并分别统计二者时间。由于修改操作的实现过程基于查找操作，我们仅以查找为例。

对于增加/删除操作：



对于查找操作：



---
## 三、 结论

