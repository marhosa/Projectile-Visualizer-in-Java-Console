package Main;

import java.util.*;

public class Main {

    //gravity acceleration
    static float gravityAcceleration = 9.8F;

    //calculates the horizontal velocity of the projectile
    static float velocity_X(float Vi, float Angle){
        float angleInRadians = (float)Math.toRadians(Angle);
        return (float)(Vi * Math.cos(angleInRadians));
    }

    //calculates the vertical velocity of the projectile
    static float velocity_Y(float Vi, float Angle){
        float angleInRadians = (float)Math.toRadians(Angle);
        return (float)(Vi * Math.sin(angleInRadians));
    }

    //computes for the time the variable is in the sky
    static float calcAirtime(float Vi, float Angle){
        float ViY = velocity_Y(Vi, Angle);
        return (float)(ViY/gravityAcceleration /* <-- Gravity*/ ) * 2;
    }

    //computes for the max height
    static float calcMaxHeight(float Vi, float Angle){
        float ViY = velocity_Y(Vi, Angle);
        return (ViY * ViY)/(2 * gravityAcceleration);
    }

    //calculates the Maximum distance
    static float calcMaxDistance(float Vi, float Angle){
        return velocity_X(Vi, Angle) * calcAirtime(Vi, Angle);
    }


    //returns the height, input is current distance given maxheight and maxdistance
    static float calculateHeight_givenDistance(float maxDistance, float maxHeight, float distance){
        return ((-4 * maxHeight) / (maxDistance * maxDistance)) *
                ((distance - (maxDistance / 2)) * (distance - (maxDistance / 2))) + maxHeight;

    }





    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        //Display Resolution
        int width = 90;
        int height = 20;

        //Variables
        float angle = 0;
        float initialvelocity = 0;


        while(true) {

            //user input for the ANGLE and initial velocity
            System.out.print("Enter Angle, 0 to 90 Degrees: ");
            angle = sc.nextFloat();
            System.out.print("Enter Initial Velocity: ");
            initialvelocity = sc.nextFloat();

            //physics variables
            float AirTime = calcAirtime(initialvelocity, angle); //Distance of projectile
            float maxHeightY = calcMaxHeight(initialvelocity, angle); //Projectile max height
            float maxDistance = calcMaxDistance(initialvelocity, angle);

            //reScaling the given max height and max distance to fit the renderer
            float rescaled_MaxHeight;
            float rescaled_MaxDistance;
            float rescaler_ratio = 0;

            if (width / maxDistance < height / maxHeightY) {
                rescaler_ratio = width / maxDistance;
            } else {
                rescaler_ratio = height / maxHeightY;
            }

            rescaled_MaxHeight = maxHeightY * rescaler_ratio;
            rescaled_MaxDistance = maxDistance * rescaler_ratio;

            System.out.println("Rescaled Max height also the Vertex Y value: " + rescaled_MaxHeight);
            System.out.println("Rescaled Max Distance in X axis: " + rescaled_MaxDistance);


            //RENDERER:
            //notes: i = Y = Height
            //       j = X = Distance
            System.out.println("\n\n\n\n"); //gives endlines for space
            //Renderer
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    //outputs @ when the current index is a point in the parabola
                    if ((int) calculateHeight_givenDistance(rescaled_MaxDistance, rescaled_MaxHeight, j) == height - i) {
                        System.out.print("@");

                        //outputs | in the line of symmetry, showcasing the highest point of the graph
                    } else if ((int) rescaled_MaxDistance / 2 == j && rescaled_MaxHeight > height - i) {
                        System.out.print("|");
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println();
            }

            System.out.println("TIME OF FLIGHT: " + calcAirtime(initialvelocity, angle) + "s |\tMax Height: " + maxHeightY + "m |\tMax Distance: " + maxDistance + "m\nPress Enter To TRY again...");
            sc.nextLine(); sc.nextLine();
            for(int i = 0; i < 10; i++)
                System.out.println(" ");

        }
    }
}
