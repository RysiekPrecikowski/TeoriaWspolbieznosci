package production;


import parallelism.MyLock;

public interface IProduction<P> {

    public P apply(P _p);

    public void join() throws InterruptedException;

    public void start();

    public void injectRefs(MyLock _lock);

    public P getObj();
}
