package frc.robot.Constants;

public final class PathPlannerConstants {
    public final class PathConstraints {
        // M/S
        public final static double maxTranslationSpeed = 0.5;
        public final static double maxTranslationAcc= 0.2;
        // RADs/S
        public final static double maxRotationSpeed = 1 * Math.PI;
        public final static double maxRotationAcc= 0.5 * Math.PI;
    }
}
