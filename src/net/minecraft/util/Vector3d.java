package net.minecraft.util;

public class Vector3d
{
    /** The X coordinate */
    public double x;

    /** The Y coordinate */
    public double y;

    /** The Z coordinate */
    public double z;

    public Vector3d()
    {
        this.x = this.y = this.z = 0.0D;
    }

    public Vector3d(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
