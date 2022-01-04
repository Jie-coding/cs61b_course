public class NPlanet{

    public static double readRadius(String arg){
        In in = new In(arg);
        int Number = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String arg){
        In in = new In(arg);
        int Number = in.readInt();
        double radius = in.readDouble();   // Need to think about here
        Planet[] arrayPlanets = new Planet[Number];
        for (int i=0; i < Number; i++){

            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mass = in.readDouble();
            String imgName = in.readString();
            
            arrayPlanets[i] = new Planet(xPos, yPos, xVel, yVel, mass, imgName);
        }
        return arrayPlanets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);;
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] arrayPlanets = readPlanets(filename);
        String drawBackground = "images/starfield.jpg";
        double time =0;

        StdDraw.enableDoubleBuffering(); // a graphics technique to prevent flickering in the animation;
        StdDraw.setScale(-radius, radius); // Set scale for both X and Y axis
        StdDraw.clear();
        StdDraw.picture(0,0, drawBackground);
        for (Planet p : arrayPlanets){
            p.draw();
        }
        StdDraw.show();

        while(time <= T){
            StdDraw.clear();
            StdDraw.picture(0, 0, drawBackground);
            double[] xForces = new double[arrayPlanets.length];
            double[] yForces = new double[arrayPlanets.length];
            for (int i = 0; i < arrayPlanets.length; i++){
                xForces[i] = arrayPlanets[i].calcNetForceExertedByX(arrayPlanets);
                yForces[i] = arrayPlanets[i].calcNetForceExertedByY(arrayPlanets);
            }
            for (int i =0; i <  arrayPlanets.length; i++){
                arrayPlanets[i].update(dt, xForces[i], yForces[i]);
                arrayPlanets[i].update(dt, xForces[i], yForces[i]);
            }
            for (Planet p : arrayPlanets){
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
        StdOut.printf("%d\n", arrayPlanets.length);
        StdOut.printf("%.2e\n", radius); // Scientific notation and keep 2 digits after decimal;
        for (int i =0; i <  arrayPlanets.length; i++){
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                           arrayPlanets[i].xxPos, arrayPlanets[i].yyPos, arrayPlanets[i].xxVel,
                           arrayPlanets[i].yyVel, arrayPlanets[i].mass, arrayPlanets[i].imgFileName);
        }
    }
}