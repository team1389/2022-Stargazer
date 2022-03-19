package frc.util;

public class Angle {
    public enum AngleType {
        Radian,
        Degree
    }

    public double angle;
    public AngleType angleType;

    public Angle(double angle, AngleType angleType) {
        this.angle = angle;
        this.angleType = angleType;
    }

    public static Angle fromDegrees(double angle) {
        return new Angle(angle, AngleType.Degree);
    }

    public static Angle fromRadians(double angle) {
        return new Angle(angle, AngleType.Degree);
    }

    public double getDegrees() {
        var outAngle = angle;
        if (angleType == AngleType.Degree) {
            outAngle = angle;
        } else {
            outAngle = 180 * angle / Math.PI;
        }
        return outAngle % 360;
    }

    public double getRadians() {
        var outAngle = angle;
        if (angleType == AngleType.Radian) {
            outAngle = angle;
        } else {
            outAngle = Math.PI * angle / 180;
        }
        return outAngle % (2 * Math.PI);
    }

    public double getAbsDegrees() {
        var outAngle = angle;
        if (angleType == AngleType.Degree) {
            outAngle = angle;
        } else {
            outAngle = 180 * angle / Math.PI;
        }
        return outAngle;
    }

    public double getAbsRadians() {
        var outAngle = angle;
        if (angleType == AngleType.Radian) {
            outAngle = angle;
        } else {
            outAngle = Math.PI * angle / 180;
        }
        return outAngle;
    }

    /**
     * @return angle from -180 to 180
     */
    public double getDegreesRanged() {
        var outAngle = getDegrees();
        outAngle = 2 * (outAngle % 180) - outAngle;
        return outAngle;
    }

    public Angle add(Angle rhs) {
        if (angleType == AngleType.Degree) {
            return new Angle(angle + rhs.getAbsDegrees(), AngleType.Degree);
        } else {
            return new Angle(angle + rhs.getAbsRadians(), AngleType.Radian);
        }
    }
}
