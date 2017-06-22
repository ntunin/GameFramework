package com.ntunin.cybervision.activity;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.crvinjector.Injectable;
import com.ntunin.cybervision.crvobjectfactory.CRVObjectFactory;
import com.ntunin.cybervision.res.ResMap;
import com.ntunin.cybervision.virtualmanagement.crvactor.CRVActor;
import com.ntunin.cybervision.virtualmanagement.crvactor.CRVBody;

import math.latlng.LatLng;
import math.vector.Vector3;

/**
 * Created by nik on 21.06.17.
 */

public class Attraction implements Injectable{
    private String description;
    private String name;
    private CRVActor actor;
    private Vector3 position;

    @Override
    public void init(ResMap<String, Object> data) {
        ResMap<String, Object> argument = (ResMap<String, Object>) data.get("argument");
        ResMap<String, Object> position = (ResMap<String, Object>) argument.get("position");
        double lat = (double) position.get("lat");
        double lng = (double) position.get("lng");
        double[] buf = new double[2];
        LatLng global = (LatLng) data.get("globalOffset");
        global.offsetFromLatLng(buf, lat, lng);
        CRVObjectFactory factory = (CRVObjectFactory) data.get(R.string.object_factory);
        this.position = (Vector3) factory.get(R.string.vector3).init((float)buf[0], (float)0, (float)buf[1]);
        this.actor = (CRVActor) argument.get("actor");
    }

    public void prepare() {
        this.actor.prepare();
    }

    public void present() {
        CRVBody body = actor.getBody();
        body.setR(position);
        this.actor.play();
    }
}
