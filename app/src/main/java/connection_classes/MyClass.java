package connection_classes;

public class MyClass {
    private static MyClass myClass = null;

    public static MyClass getInstance() {
        if(myClass == null)
        {
            myClass = new MyClass();
        }
        return myClass;
    }

    public int getSum(int  a, int b)
    {
        return a+b;
    }
}
