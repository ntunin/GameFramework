package eye.engine.nik.gameframework.GameFramework;

/**
 * Created by Николай on 24.07.2016.
 */
public class DelegatingObject {
    public Delegate getDelegate(String methodName, Class<?>... types) throws DelegateException {
        Delegate delegate = new Delegate(this, methodName, types);
        return delegate;
    }
}
