# 深入研究下泛型、反射、代理
                
note:补一下基础，并深入研究一下，特别是泛型。反射主要研究和泛型相关的内容。参考书籍：《Java编程思想》，《Java核心技术卷一》

## 泛型

    note:泛型使用是一件简单的事情，而如何设计泛型类则是有点难度的。本节重点也在学习如何设计泛型类。

### 泛型：适用于许多许多的类型

 * 代码使用于"某种不确定的类型"，而不是具体的接口或类。
 * 类或方法具备广泛的适用性。

#### 参数化类型
```text
    note:这么理解参数化类型吧：普通的方法参数或类的构造方法可能包含若干参数，这些参数(形参)必定是一个固定类型，
    实参是这个类型的一个实例。但参数化类型作为方法参数或类参数的时候，它是"某种不确定的类型"，当然参数化类型定义
    的方式和普通方法和类有些差别的。简言之，就是可以定义这么一个类或方法，让它接受一个不知道是什么类型的参数
    (当然不会是Object类型这么简单的方式)。
```
#### 简单泛型类

有许多原因促成了泛型的出现，而最引人注目的一个原因，就是为了创造<b>容器类</b>。 ---《Java编程思想》

    note:为了让容器灵活的持有各种类型,这是一个泛型出现的重要原因。
    
糟糕容器：
com.lingyun.generics.BadHolder
```java
public class BadHolder1 {
    private Automobile a;
    public BadHolder1(Automobile a){
        this.a=a;
    }

    public Automobile get() {
        return a;
    }
}
```
为什么它是一个糟糕的容器？作为一个容器，它应该容纳万物，它仅关心容器本身就好，比如是否能重复元素，是否排序，
如何取得元素等等。而这个容器只能容纳Automobile类型及Automobile扩展。

糟糕的解决方案：Automobile类型变为Object类型似乎可以解决容纳一切的问题，可以存放Automobile、String等等一切，
但它的糟糕之处在哪里呢？

com.lingyun.generics.BadHolder2

```java
public class BadHolder2 {
    private Object a;
    public BadHolder2(Object a){
        this.a=a;
    }

    public Object get() {
        return a;
    }
}

```
不难想像，当我们调用get方法时，我们获得的是一个Object，必定存在强制类型转换。一般说来我们实例化一个容器的时候，
是希望容器能持有某种具体类型的，比如我们希望容器是一个Automobile容器。
```text
note:这里需要区分一下泛型程序的设计者和使用者不同角色，从设计者的角度看，他希望泛型能容纳所有不确定的东西，
从使用者的角度看，则希望容器容纳他所希望的某种类型。
```
    
因此，与其使用Object，我们更喜欢暂时不指定类型，而是稍后再决定具体使用什么类型。要达到这个目的，需要使用类型参数，
用尖括号扩住，放在类名后面。然后在使用这个类的时候，再用实际的类型替换此类型参数。 ------《Java编程思想》    

com.lingyun.generics.GoodHolder
```java
public class GoodHolder<T> {
    private T a;
    public GoodHolder(T a){
        this.a=a;
    }

    public T get() {
        return a;
    }

    public void set(T a) {
        this.a = a;
    }
    public static void main(String[] args){
        
        GoodHolder<Automobile> holder=new GoodHolder<Automobile>(()->{});
        
        holder.get().run();
    }
}
```
```text
note:上例中main方法使用了lambda表达式表示一个匿名内部类。因为Automobile是一个接口类型，实例化GoodHolder<Automobile>
的时候需要具体类型。可见泛型的参数化类型使用接口也是符合语法规范的。

另外，我们使用get方法，直接获取了Automobile类型实例，可直接调用run方法。从而避免了Object类型的强制类型转换。
同时也证明了，类型参数也存在多态特性。
```
##### 基本数据类型可以作为类型参数吗？

下面的代码是报错的：
```text
Type argument can not be primitive type.
```
```java
    GoodHolder<int> intHolder=new GoodHolder<int>();
```
所以基本数据类型不可作为泛型类型参数。在认知不够充分的情况下，我决定暂且不追究原因，把这当成一条规则就好。


#### 泛型方法

实际上，可以定义一个带有类型参数的简单方法：
```java
package com.lingyun.generics;

public class ArrayAlg {
    public static<T> T getMiddle(T...a){
        return a[a.length/2];
    } 
}
```

方法调用：
```java
    String middle =ArrayAlg.<String>getMiddle("John","Q.","Public");
    String middle2 =ArrayAlg.getMiddle("John","Q.","Public");
```

```text
note:定义方法时类型变量放在返回值之前，修饰符之后。调用泛型方法时，在方法名前放类型参数，或者可以省略，因为编译器可以推断出来。
上例中，方法传入了String[]，编译器判断T实际为String。
```
偶尔出现的错误：
```java
    double d1 =ArrayAlg.getMiddle(3.14,1729,0);//wrong
    double d2 =ArrayAlg.getMiddle(3.14d,1729d,0d);//correct
```
d1报错信息：

![](https://github.com/YuxingXie/reflect/raw/master/source/1.png) 

原因：因为方法调用时需要传入一个某种类型的数组，3.14是double型，1729和0是int型，前面研究过基本数据类型不可作为类型参数，传入后编译器自动装箱为Double和Integer，
但既然他们必须是同一种类型，编译器寻找他们的共同父类或接口，找到了Number和Comparable接口，他们不可赋值为double。

所以下面的代码是正确的：
```java
    Number d3=ArrayAlg.getMiddle(3.14,1729,0);
    Comparable d4=ArrayAlg.getMiddle(3.14,1729,0);
```
#### 类型变量的限定

看下面的代码，求泛型数组的最小值：
```java
public class ArrayAlg {
    public static<T> T min(T[] a){
        if (a==null ||a.length==0) return null;
        T smallest=a[0];
        for(int i=0;i<a.length;i++){
            if (smallest.compareTo(a[i])>0) smallest=a[i];//在这里报错了，谁也不能保证传进来的参数可以调用compareTo方法
        }
        return smallest;
    }
}
```
将类型变量设置限定(bound)即可实现：
```java
    public static<T extends Comparable> T min(T[] a)...
```
Comparable是一个接口，为什么关键字不是implements而是extends呢？这是规定，类型变量限定时，无论是继承父类或实现接口都用关键字extends。

类型变量可以限定多个接口，如T extends Comparable&Serializable，这要求实例化参数类型时，实例参数必须同时实现这2个接口。但是
限定中至多有一个类(class),而且类必须是限定类型列表中的第一个。

#### 泛型代码和虚拟机

虚拟机没有泛型类型对象。就是说编译后的泛型类，在虚拟机中只是一个普通类。所以我们有必要了解一下，编译器是如何把泛型类编译为普通类的。

我们定义一个泛型类以后，都自动提供了一个相应的原始类型(raw type)。它删去了类型参数，替换为限定类型或Object。这个过程叫擦除。

所以在虚拟机中，只存在public static Comparable min(Comparable[] a)这类方法。com.lingyun.generics.GoodHolder也只存在
private Object a这样的类属性。

##### 翻译泛型表达式

我们调用泛型方法或实例化泛型类时，其实是编译器帮我们做了强制类型转换。

我们分析一下GoodHolder类中的代码：
```java
    GoodHolder<Automobile> carHolder=new GoodHolder<>(new Car());
    Automobile car=carHolder.get();
    car.run();
```
* 第一句代码在虚拟机中实际上是实例化了一个GoodHolder普通类，构造方法传入的Car实例，被赋值给Object类型的类属性a。

* 第二句代码carHolder.get()方法的原始类型实际是返回一个Obejct对象，在代码调用处，编译器悄悄把代码变成了
```java
    Automobile car=(Automobile)carHolder.get();
```
它是根据我们定义的泛型类型实例GoodHolder&lt;Automobile&gt;中的类型参数Automobile来强转的。当然，我们定义为GoodHolder&lt;Car&gt;代码也是正确的，
这个自己想想就能明白。

* 第三句，既然现在car是一个Automobile,当然可以执行Automobile的run方法了，而且是执行的Car的run方法，因为它的实际类型是Car。

#### 擦除带来的两个复杂问题

《Java核心技术 卷I》中引入了这个有点点复杂的问题，现在我来分析一下这个问题。

```java
package com.lingyun.generics;

import java.util.Date;

public class DateInterval extends Pair<Date>{
    public void setSecond(Date second){
        if(second.compareTo(getFirst())>0){
            super.setSecond(second);
        }
    }
}
```

Pair&lt;T&gt;中定义了public void setSecond(T second)方法，这里DateInterval中的public void setSecond(Date second)算写重父类方法吗？根据类型参数可以代替为任何类型的
泛型宗旨看，它应该是重写父类方法。因为这里，DateInterval继承了Pair&lt;T&gt;并把类型参数代替为Date类型。所以在DateInterval中，它父类Pair的类型参数就是Date，Pair中所有的
T都被替换为了Date，所以父类中的public void setSecond(T second)替换为了public void setSecond(Date second)。所以DateInterval中的public void setSecond(Date second)
是在重写父类方法。这样想没毛病，也符合泛型的设计宗旨。我使用的IDE(Intellij Idea)也认为这个方法是从父类继承而来的。

然而从类型擦除的角度看，却不是这么一回事。Pair&lt;T&gt;中的public void setSecond(T second)类型擦除后变成了public void setSecond(Object second),很显然这和DateInterval
中的public void setSecond(Date second)是具有两个不同方法签名的重载方法，而不是重写父类方法。

从泛型设计宗旨看，类型擦除引起的问题必须得到纠正。就是说，要让public void setSecond(Date second)成为public void setSecond(Object second)的子类，然而，该怎么做呢？

从之前的研究我们知道，编译器在我们使用泛型参数的地方，悄悄地进行了强制类型转换。在这里如果我们也采用强制类型转换这种方式，似乎应该是这样的：让编译器把DateInterval中的
public void setSecond(Date second)方法悄悄变成public void setSecond(Object second)方法，以保证它是继承自父类，然后在方法体中第一句加上强制类型转换：
```java
    //我认为虚拟机可能这么干了
    Date second_ =(Date) second;
```
然后把方法中用到second的地方都替换成second_。

当然，这是不优雅的。实际上，编译器采用了桥方法。桥方法仍然悄悄生成public void setSecond(Object second)方法，同时也保留public void setSecond(Date second)，然后在
public void setSecond(Object second)中调用public void setSecond(Date second)方法，调用的时候吧Object second强制转换为Date类型。

当虚拟机解决getter方法的泛型类型时，Pair中定义public T getSecond()被擦除变量类型后public Object getSecond(),DateInterval中的
public Date getSecond()方法与Pair中的public Object getSecond()被称作具有协变的返回类型，是一种合法的重写。这个可以看看com.lingyun.generics.Car是如何重写
com.lingyun.generics.AbstractAutomobile的doSomething方法的。注意，只有协变的返回类型而没有协变的参数类型。

当类型参数既在返回类型又在方法参数时，就结合桥方法和协变返回类型。com.lingyun.generics.Car中的public String doSomething(String doing)方法就是模仿虚拟机产生的桥方法。

#### 再次重申泛型的使用者和提供者身份

我们知道了，泛型设计者是要设计一个类型参数可以被替换的通用型类，而泛型的使用者则在使用时把类型参数实例化为他想要使用的类型。那么何时才算"使用一个泛型类"呢？前面研究过，实例化
一个泛型类，调用泛型方法都算使用。那么集成一个泛型类算什么？前面的DateInterval毫无疑问算是"使用泛型类"，因为它把类型参数实例化为Date类型。这时这个类不具备通用性，它的getSecond
返回值以及setSecond方法参数都被具体化为Date类型了。假设我们要设计一个类Interval集成Pair且仍具备可以替换的类型参数怎么办？我们应该继续使用泛型类型T,
见com.lingyun.generics.Interval：

```java
package com.lingyun.generics;

public class Interval<Q extends Comparable,S> extends Pair<Q>{
    public Q getMax(){
        return getFirst().compareTo(getSecond())>0?getFirst():getSecond();
    }
    private S s;

    public S getS() {
        return s;
    }

    public void setS(S s) {
        this.s = s;
    }
}
```

子类可以对父类的类型参数做类型限定，子类必须包含父类的泛型类型Q，子类可以增加一个泛型参数S。这些规则放到后面研究吧。

### 约束与局限性

这里有些简单，有些很抽象，需要时间去理解。暂时有一些不理解的，边研究边去理解，等理解了再来描述。

 * 不能用基本类型实例化类型参数，因为擦除后Object不能引用基本数据类型。
 
 * 运行时类型查询只适用于原始类型，很好理解，因为虚拟机中根本没有泛型类型。
 
 * 不能创建参数化类型的数组,就是说可以Pair&lt;String&gt;[] table=new Pair[10];但是不可以Pair&lt;String&gt;[] table=new Pair&lt;String&gt;[10];这个还没弄懂。
 
 * Varargs警告，出现在方法参数是数组的情况时，这和上一条产生的原因相似。
 
 * 不能实例化类型变量，既T = new T();这种代码是非法的,但是Pair<T> pair =new Pair<T>();是合法的。
 
 * 泛型类的静态上下文中类型变量无效，就是说不能有static T 的属性和方法。
 
 * 不能抛出或捕获泛型类的实例，甚至泛型类不可扩展自Throwable。
 
 * 擦除后的冲突。
 
 ### 泛型类型的继承规则
 
 
 《Java核心技术 卷I》中，作者试图证明：无论S与T有什么关系，Pair&lt;S&gt;和Pair&lt;T&gt;没有说明关系。
 
 很神奇的是，书中认为的会编译报错的代码，在我这里却编译通过了。见我写的Pair类中的main方法。
 
 但是作者的结论是正确的，确实Pair&lt;Manager&gt;和Pair&lt;Employee&gt;没有父子关系。下图可以证明：
 
 ![](https://github.com/YuxingXie/reflect/raw/master/source/2.png) 
 
 其实作者是想当然了，根据虚拟机悄悄修改我们的代码过程，Pair&lt;Employee&gt; result=ArrayAlg.minMax(topHonchos);这句代码调用时，topHonchos已被强制转换为了Employee[],所以代码是正确的。
 
 泛型类可以扩展或实现其它的泛型类，这个我已经在前面做过研究了，而且比书上的更具体。
 
 ### 通配符类型
 
 看看这个printBuddies方法：
 ```java
 package com.lingyun.generics;
 
 public class Employee implements Comparable<Employee>{
     //......other code......
     //......other code......
     //......other code......
     //......other code......
     //......other code......
     
    public static void printBuddies(Pair<Employee> p){
        Employee first=p.getFirst();
        Employee second=p.getSecond();
        System.out.println(first.getName()+ " and "+second.getName());
    }
    
    public static void main(String[] args){
            Pair<Manager> managerPair=new Pair<>();
            printBuddies(managerPair);//error
        }
}
```
我们调用时传入Pair&lt;Manager&gt;类型的参数时，代码是报错的，原因是Pair&lt;Manager&gt;和Pair&lt;Employee&gt;没有继承关系。

我们怎么建立Pair&lt;Manager&gt;和Pair&lt;Employee&gt;之间的关系呢？这时候通配符起作用了。

我不打算用这个例子了，因为《Java编程思想》中对通配符的描述更好，所以我们换它的例子。
```java
package com.lingyun.generics;

public class CovariantArrays {

    public static void main(String[] args){
        Fruit[] fruits= new Apple[10];
        fruits[0] = new Apple();
        fruits[1] = new Jonathan();
        fruits[0] = new Fruit();
        fruits[0] = new Orange();
    }
}

class Fruit{}
class Apple extends Fruit{}
class Jonathan extends Apple{}
class Orange extends Fruit{}
```

我省略了try-catch块，程序编译正确，运行的时候在fruits[0] = new Fruit();和fruits[0] = new Orange();时会抛出java.lang.ArrayStoreException异常。

这个例子是想告诉我们，即便我们申明了一个Fruit[]，但它初始化为了一个Apple[10]，那么它就不是一个Fruit[]而是一个Apple[]。但这是一种运行时错误。编译器是通过的。

要记住这一点，不要以为你声明了Fruit[]就可以认为数组里可以随心所欲放Fruit，因为初始化的时候决定了数组元素必须是Apple。这一点数组和类是不一样的,下面的代码就不会有问题：

```java
    Fruit fruit =new Apple();
    fruit = new Orange();
```

好吧，我们不用数组，而使用容器类：

 ![](https://github.com/YuxingXie/reflect/raw/master/source/3.png)
 
 情况不对，编译错误。我们前面研究过，List&lt;Fruit&gt;和ArrayList&lt;Apple&gt;没有父子关系，所以会报错。
  
我们确实需要这样一个容器(或方法)，它可以让元素向上转型。com.lingyun.generics.Employee的public static void printBuddies(Pair&lt;Employee&gt; p)方法就是这种场景，
它希望方法参数是Pair&lt;Employee&gt;，并且实际上传入Pair&lt;Manager&gt;也应该要成立。

 这时，书上告诉我要用通配符解决问题：
 ```java
package com.lingyun.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericAndCovariance {
    public static void main(String[] args){
        List<? extends Fruit> flist= new ArrayList<Apple>();//有讲究
//        flist.add(new Apple());//不是吧，这也报错，我能传什么进去？
//        flist.add(new Fruit());//wrong
//        flist.add(new Object());//啥都不行？
        flist.add(null);//难道只能传null
        Fruit f=flist.get(0);//correct
    }
}
```

当然，看上面的代码，事情没那么简单的。

先看看这句：
```java
    List<? extends Fruit> flist= new ArrayList<Apple>();
```
首先我们要明确一个概念，通配符是我们使用者使用一个泛型类时使用的技术，而不是定义一个泛型类使用的技术，我们不能定义一个带通配符的泛型类，但是好像通配符可以放在方法定义的形参中。
实例化一个泛型类，就要把类型参数实例化为一个具体的类，所以通配符不能放在赋值语句的右边，右边一定是一个参数类型具体的类。就是说，通配符一定是放在赋值语句的左边。当然也可以放在方法的形参上，道理类似。

现在言归正传，我们看到GenericAndCovariance的main方法中，我们实例化了一个通配符类型参数的List,然而调用add方法时，我们却不能传入除了null以外的任何类型的实例，Object也不行。只有
Fruit f=flist.get(0);这一句是合法的。

书上说：
```text
List<? extends Fruit>可以合法地指向一个List<Orange>。一旦执行这种类型的向上转型，你就将丢失掉向其中传递任何对象的能力，甚至传递Object也不行。
```
我理解为：
```text

List<? extends Fruit>不是任何一种参数类型的List，所以使用者不能将调用任何方法中包含参数化类型的任何方法，但是可以调用返回值是参数化类型但参数中不包含参数化类型的的方法。

我们看看这句代码：flist.add(new Apple());根据编译器翻译泛型代码所做的工作，实际上是要把new Apple()强制转换为? extends Fruit，当然不存在这样的类型。所以编译当然不会通过。

但是编译器却允许? extends Fruit作为引用类型(就是放在赋值语句左边的类型)存在，这时它代表的是Fruit类型。

用同样的道理解释似乎行不通，因为flist.get(0)也存在强制转换为? extends Fruit的问题，为什么Fruit f=flist.get(0)就合法呢？

是不是可以认定，编译器对通配符类型代码的编译有不一样的规则呢？我暂且认定是的。

```

现在看起来似乎通配符没什么用。书中举了一个例子，似乎也仅仅说明通配符只在向上转型时作为引用类型时有用。当然就这一个简单的作用也解决了泛型类型不能向上转型的问题。就是说之前的经典问题，即
不能把ArrayList&lt;Apple&gt;赋值给引用类型List&lt;Fruit&gt;的问题得到解决。仅此而已。

书中的例子(略做改动)：
```java
package com.lingyun.generics;

import javax.xml.ws.Holder;

public class GoodHolder<T> {
    private T a;
    public GoodHolder(T a){
        this.a=a;
    }

    public T get() {
        return a;
    }

    public void set(T a) {
        this.a = a;
    }
    public static void main(String[] args){


        GoodHolder<Apple> appleHolder=new GoodHolder<>(new Apple());

        Apple d = appleHolder.get();

        appleHolder.set(d);

        //GoodHolder<Fruit> fruitGoodHolder=appleHolder;//cannot upcast
        GoodHolder<? extends Fruit> fruitHolder=appleHolder;//通配符是这样用的

        Fruit p=fruitHolder.get();

        d=(Apple)fruitHolder.get();

        try {
            Orange c=(Orange)fruitHolder.get();
        }catch (Exception e){
            //fruitHolder.set(new Apple());//不能这样用我感到非常遗憾
            System.out.println(e);
            System.out.println(fruitHolder.equals(appleHolder));
            System.out.println(p.equals(d));
        }
    }
}
```

不能调用参数包含类型参数的方法，是一个巨大的遗憾，然而，下一小节《逆变》似乎解决了这个问题。

#### 逆变

下段文字和代码摘自《Java编程思想》：

  还可以走另外一条路，即使用<b>超类通配符</b>。这里，可以声明通配符是由某个特定类的任何基类来界定的，方法指定&lt;? super MyClass&gt;,甚至或者使用类型参数:&lt;? super T&gt;
(尽管你不能对泛型参数给出一个超类型边界；即不能声明&lt;T super MyClass&gt;)。这使得你可以安全地传递一个类型对象到泛型类型中。因此，有了超类通配符，就可以向Collection中写入了：
```java
package com.lingyun.generics;

import java.util.List;

public class SuperTypeWildCards {
    public void writeTo(List<? super Apple> apples){
        apples.add(new Apple());
        apples.add(new Jonathan());
        //apples.add(new Fruit());//Error
    }
}

```

先解释一下，? super Apple不是表示"Apple的基类类型"，而是表示"以Apple为基类的类型"。从这个意义上讲，它和? extends Apple似乎没什么不同。但从代码可以看出，它解决了我们之前提出的问题：
不能调用参数包含类型参数的方法。现在，通过? super Apple作为参数形参，可以调用List的add(T)方法了。

既然如此，那我觉得方法这样写才更有用：

```java

    public void usefulWriteTo(List<? super Fruit> fruits){
        fruits.add(new Apple());
        fruits.add(new Jonathan());
        fruits.add(new Fruit());
        Object s=fruits.get(0);
    }
    public static void main(String[] args){
        SuperTypeWildCards a = new SuperTypeWildCards();
        //List<? super  Fruit> fruits=new ArrayList<Apple>();
        List<? super  Fruit> fruits=new ArrayList<Fruit>();
        a.usefulWriteTo(fruits);
    }
```
不过似乎问题解决得并不完美，首先，在main方法中使用usefulWriteTo时,我们的方法只能接受类型参数为Fruit的List，却不能接受Apple类型参数List；其次，之前一直使用正常的Fruit作为get方法的
引用类型代码已经不能用了，只能用Object引用。这相当于引出了新问题。

这个问题是否在下小节的《无界通配符》中解决呢？可以先带着这个疑问。

另外书中的例子GenericReading类中使用? extend T的用法值得看一下，这里就不列出来了。

#### 无界通配符

我是带着前一小节的问题来研究这一小节的。我试图解决两个问题：

 * 怎么让usefulWriteTo能够接收一个List&lt;Apple&gt;；
 
 * 怎么通过get方法获得的实例用Fruit引用。

先不看书，直奔主题，我写一个无界通配符的类测试是否能达成的目的：

```java
package com.lingyun.generics;

import java.util.ArrayList;
import java.util.List;

public class MyUnbounderWildcards {
    
    public static void main(String[] args){
        List<?> fruits= new ArrayList<Apple>();
        //fruits.add(new Apple());//error
        //Apple apple=fruits.get(0);//error
    }
}
```

现实是很残酷的，无界通配符不能解决我提出的任何一个问题，甚至还不如extends通配符和超类通配符，它们至少能解决一个问题。

List实际上表示"持有任何Object类型的原生List",而List&lt;?&gt;表示"具有某种特定类型的非原生List，只是我们不知道那种类型是什么"。------《Java编程思想》

List和List&lt;?&gt;在表现上几乎无差异，仅仅是编译器的警告不一样。

在各种通配符的用法上，根据之前所学：

* 如果我们要使用参数列表中带类型参数的方法，就要用超类型通配符；
* 如果我们要使用引用类型为类型参数，就要使用extends通配符；

然而如果我们同时要使用参数列表中带类型参数的方法，也要使用类型参数为引用类型，应该怎么办呢？则需要使用超类型通配符并使用强制类型转换。

```java
package com.lingyun.generics;

import java.util.ArrayList;
import java.util.List;

public class MyWildcards {

    public static void main(String[] args){

        List<? super Fruit> apples=new ArrayList<Fruit>();
        Fruit apple =addAndGetFirst(apples,new Apple());
    }
    public static<T>  T addAndGetFirst(List<? super T> tlist,T t){
        tlist.add(t);
//        return tlist.get(0);
        return (T)tlist.get(0);
    }

}
```
因为使用了超类型通配符，程序损失了可协变的参数，方法必须传入List&lg;Fruit&gt;而不能传入List&lg;Apple&gt;。既要保持可协变参数，又要使用参数列表中带类型参数的方法，在通配符的使用前提下
是无法办到的，这个大家自己思考一下。因为使用超类型通配符，当我们使用引用类型为类型参数的类型的方法时，编译器会捕获(关于捕获稍后会研究)一个超类型通配符类型，举例，上面代码中的tlist.get(0)
编译器捕获了一个? super T类型，如果我们要返回类型参数的类型(即T类型)，我们可以进行强制类型转换。

为了程序的可协变参数(泛型类型的向上引用)，Java引入了extends通配符，然而它却带来了恶果，不能调用方法中包含类型参数类型的方法。或者依场景使用各种通配符才是最好的选择吧。

上例MyWildcards中，

    Fruit apple =addAndGetFirst(apples,new Apple());
    
其实可以改为

    Apple apple =addAndGetFirst(apples,new Apple());
    
这其中似乎隐藏了一些玄机，在下一节的《捕获转换》看看是否能够解开这个玄机。