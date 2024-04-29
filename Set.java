import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

@SuppressWarnings("unchecked")

public class Set<E> implements Iterable<E>
{
  private ArrayList<E> list = new ArrayList<E>();

  public Set<E> add(E x)
  {
    if (!has(x))
    {
      list.add(x);
    }
    return this;
  }
  public E get()
  {
    return list.get(0);
  }
  public Set<E> combine(Set<E> s)
  {
    for (E x : s)
    {
      add(x);
    }
    return this;
  }
  public Set<E> remove(E x)
  {
    list.remove(x);
    return this;
  }
  public Set<E> intersect(Set<E> s)
  {
    Set<E> i = new Set();
    for (E x : this)
    {
      if (!s.has(x))
      {
        i.add(x);
      }
    }
    subtract(i);
    return this;
  }
  public Set<E> subtract(Set<E> s)
  {
    for (E x : s)
    {
      remove(x);
    }
    return this;
  }
  public Set<Set<E>> power()
  {
    Set<Set<E>> p = new Set();
    Set<E> s = Set.copy(this);
    p.add(emptySet());

    for (E x : s)
    {
      Set<E> sub = new Set();
      sub.add(x);
      p.add(Set.copy(sub));
      for (E y : s)
      {
        sub.add(y);
        p.add(Set.copy(sub));
      }
    }
    return p;
  }
  public Set<E> clear()
  {
    list.clear();
    return this;
  }

  public boolean has(E x) 
  {
    return list.contains(x);
  }
  public int size()
  {
    return list.size();
  }
  public boolean isSubset(Set<E> s)
  {
    boolean isSub = true;
    for (E x : s)
    {
      if (!has(x))
      {
        isSub = false;
      }
    }
    return isSub;
  }
  @Override
  public boolean equals(Object o)
  {
    if (o == null)
    {
      return false;
    }
    if (!(o instanceof Set))
    {
      return false;
    }
    Set s = (Set)o;

    return isSubset(s) && s.isSubset(this);
  }

  @Override
  public String toString()
  {
    String s = "{";
    int i = 0;
    for (E x : this)
    {
      i++;
      s += x + (i == size() ? "" : ", "); 
    }
    s += "}";
    return s;
  }

  // OPERATIONS

  public static <E> Set<E> union(Set<E> s1, Set<E> s2)
  {
    Set<E> u = new Set();
    u.combine(s1);
    u.combine(s2);
    return u;
  }
  public static <E> Set<E> intersection(Set<E> s1, Set<E> s2)
  {
    Set<E> i = new Set();
    i.combine(s1);
    i.intersect(s2);
    return i;
  }
  public static <E> Set<E> difference(Set<E> s1, Set<E> s2)
  {
    Set<E> d = new Set();
    d.combine(s1);
    d.subtract(s2);
    return d;
  }
  public static <E> Set<Set<E>> powerSet(Set<E> s)
  {
    return s.power();
  }
  public static <E> Set<Tuple<E>> cartesian(Set<E> s1, Set<E> s2)
  {
    Set<Tuple<E>> cart = new Set();
    for (E x : s1)
    {
      for (E y : s2)
      {
        Tuple t = new Tuple();
        t.add(x).add(y);
        cart.add(t);
      }
    }
    return cart;
  }
  public static <E> Set<E> copy(Set<E> s)
  {
    Set c = new Set();
    c.combine(s);
    return c;
  }

  // CONSTANTS
  
  public static <E> Set<E> emptySet()
  {
    return new Set();
  }

  // ITERATION

  public Iterator<E> iterator()
  {
    return new SetIterator(this);
  }

  class SetIterator<E> implements Iterator<E>
  {
    int index = 0;
    Set set;

    private SetIterator(Set s)
    {
      set = s;
    }
    public E next()
    {
      index += 1;
      return (E)set.list.get(index - 1);
    }
    public boolean hasNext()
    {
      return index < set.size();
    }
  }
}
