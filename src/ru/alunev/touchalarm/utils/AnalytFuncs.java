package ru.alunev.touchalarm.utils;

public class AnalytFuncs {
    public static double scalarProduct(float x1, float y1, float x2, float y2) {
        return x1 * x2 + y1 * y2;
    }

    /**
     * Angle between passed r-vector and vertical unit vector.
     * @return  0 < angle < 360 in degrees
     */
    public static double getVectorAngle(float vectorX, float vectorY) {
        // unit vector
        float unitX = 0.0f;
        float unitY = 1.0f;

        double scalarProduct = AnalytFuncs.scalarProduct(unitX, unitY, vectorX, vectorY);
        double modulesProduct = Math.sqrt(vectorX*vectorX + vectorY*vectorY) * Math.sqrt(unitX*unitX + unitY*unitY);

        double vectorAngle = 0.0;
        if (vectorX >= 0 && vectorY > 0) {
            vectorAngle = Math.acos(scalarProduct / modulesProduct) / (2*Math.PI) * 360.0;
        } else if (vectorX > 0 && vectorY <= 0) {
            vectorAngle = 180 - Math.acos(-1 * scalarProduct / modulesProduct) / (2*Math.PI) * 360.0;
        } else if (vectorX <= 0 && vectorY < 0) {
            vectorAngle = 180 + Math.acos(-1 * scalarProduct / modulesProduct) / (2*Math.PI) * 360.0;
        } else if (vectorX < 0 && vectorY >= 0) {
            vectorAngle = 360 - Math.acos(scalarProduct / modulesProduct) / (2*Math.PI) * 360.0;
        }

        return vectorAngle;
    }
}
