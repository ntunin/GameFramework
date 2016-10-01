package eye.engine.nik.gameframework.GameFramework.IO.XFile.XValueReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolay on 10.09.16.
 */

class XVariableCacheItem {
    List<XVariable> list;
    boolean needDynamic;

    public XVariableCacheItem() {
        list = new ArrayList<>();
    }

    public void setNeedDynamic(boolean needDynamic) {
        this.needDynamic = needDynamic;
    }

    public boolean getNeedDynamic() {
        return needDynamic;
    }

    public List<XVariable> getList() {
        return list;
    }

    public void add(XVariable var) {
        list.add(var.clone());
    }
}