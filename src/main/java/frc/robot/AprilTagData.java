// INFO: ROBOT IMPORTS
package frc.robot;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
// INFO: WPILIB IMPORTS
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;


public class AprilTagData {
    private static AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);
    int tagNumber;
    Pose2d tagFieldPose;
    Transform2d tagOffsetRed;
    Transform2d tagOffsetBlue;

    public AprilTagData(int tagNumber, Pose2d tagFieldPose, Transform2d tagOffsetRed, Transform2d tagOffsetBlue) {
        this.tagNumber = tagNumber;
        this.tagFieldPose = fieldLayout.getTagPose(tagNumber).get().toPose2d();
        this.tagOffsetBlue = tagOffsetBlue;
        this.tagOffsetRed = tagOffsetRed;
    }

    // INFO: Returns values from data structure
    /** @return Tag Number */
    public int getTagNumber() { return tagNumber; }
    public Pose2d tagFieldPose() { return tagFieldPose; }
    /** @return The tag's position on the field */
    public Transform2d tagOffsetRed() { return tagOffsetRed; }
    /** @return The tag offset for the red side */
    public Transform2d tagOffsetBlue() { return tagOffsetBlue; }
    /** @return The tag offset for the blue side */

    /**
     * Prints the values from the data structure
     */
    public void print() {
        System.out.println(
            "Tag Number: " + tagNumber +
            "Tag Field Pose" + tagFieldPose +
            "Tag Offset Red" + tagOffsetRed + 
            "Tag offset Blue" + tagOffsetBlue
        );
    }
}
