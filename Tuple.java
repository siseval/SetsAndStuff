import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unchecked")

public class Tuple<E> implements Iterable<E>
{
  private ArrayList<E> list = new ArrayList<E>();

  public Tuple<E> add(E x)
  {
    list.add(x);
    return this;
  }
  public E get(int index)
  {
    return list.get(index);
  }
  public Tuple<E> combine(Tuple<E> t)
  {
    for (E x : t)
    {
      add(x);
    }
    return this;
  }
  public Tuple<E> remove(E x)
  {
    list.remove(x);
    return this;
  }
  public Tuple<E> intersect(Tuple<E> t)
  {
    Tuple<E> i = new Tuple();
    for (E x : this)
    {
      if (!t.has(x))
      {
        i.add(x);
      }
    }
    subtract(i);
    return this;
  }
  public Tuple<E> subtract(Tuple<E> t)
  {
    for (E x : t)
    {
      remove(x);
    }
    return this;
  }
  
  public Tuple<E> clear()
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
  
  @Override
  public boolean equals(Object o)
  {
    if (o == null)
    {
      return false;
    }
    if (!(o instanceof Tuple))
    {
      return false;
    }
    Tuple t = (Tuple)o;

    if (t.size() != size())
    {
      return false;
    }
    for (int i = 0; i < size(); i++)
    {
      if (t.get(i) != get(i))
      {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString()
  {
    String s = "<";
    int i = 0;
    for (E x : this)
    {
      i++;
      s += x + (i == size() ? "" : ", "); 
    }
    s += ">";
    return s;
  }

  // OPERATIONS

    public static <E> Tuple<E> copy(Tuple<E> t)
  {
    Tuple c = new Tuple();
    c.combine(t);
    return c;
  }

  // CONSTANTS
  
  public static <E> Tuple<E> emptyTuple()
  {
    return new Tuple();
  }

  // ITERATION

  public Iterator<E> iterator()
  {
    return new TupleIterator(this);
  }

  class TupleIterator<E> implements Iterator<E>
  {
    int index = 0;
    Tuple tuple;

    private TupleIterator(Tuple t)
    {
      tuple = t;
    }
    public E next()
    {
      index += 1;
      return (E)tuple.list.get(index - 1);
    }
    public boolean hasNext()
    {
      return index < tuple.size();
    }
  }
}
