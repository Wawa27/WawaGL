package com.wawacorp.wawagl.core.utils;

import com.wawacorp.wawagl.core.view.buffer.VertexArrayObject;
import org.joml.Vector3f;

public class MathUtils {


    public static Vector3f calculateNormal(Vector3f p1, Vector3f p2, Vector3f p3) {
        return calculateNormal(p1, p2, p3, new Vector3f());
    }

    public static Vector3f calculateNormal(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f dest) {
        Vector3f p12 = p2.sub(p1, new Vector3f());
        Vector3f p13 = p3.sub(p1, new Vector3f());
        p12.cross(p13, dest); // (N) = (P12) x (P13)
        return dest.negate().normalize();
    }
}
