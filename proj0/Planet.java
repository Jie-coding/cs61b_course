import javax.rmi.ssl.SslRMIClientSocketFactory;

public class Planet{

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, 
                    double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV; 
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet b){
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }
    //** Calculate the distance between two planets. */
    public double calcDistance(Planet b){
        double distance = Math.sqrt(Math.pow((xxPos - b.xxPos), 2) 
                            + Math.pow((yyPos - b.yyPos), 2));
        return distance;
    }
    //** Calculate the total gravitational force between two planets. */
    public double calcForceExertedBy(Planet b){
        if (this.equals(b)){
            return 0;
        }        
        double graviConstant = 6.67e-11;
        double distance = this.calcDistance(b);
        double calcForce = (graviConstant * mass * b.mass) / Math.pow(distance, 2);
        return calcForce;
    }
    //** Calculate the x-component of the force. */
    public double calcForceExertedByX(Planet b){
        double totalForce = this.calcForceExertedBy(b);
        double distance = this.calcDistance(b);
        double forceX = totalForce * (b.xxPos - xxPos) / distance;
        return forceX;
    }
    //** Calculate the y-component of the force. */
    public double calcForceExertedByY(Planet b){
        double totalForce = this.calcForceExertedBy(b);
        double distance = this.calcDistance(b);
        double forceY = totalForce * (b.yyPos - yyPos) / distance;
        return forceY;
    }

    public double calcNetForceExertedByX(Planet[] arrayP){
        double netForceX = 0;
        for (Planet p : arrayP){
            if (this.equals(p)){continue;}
            netForceX = netForceX + this.calcForceExertedByX(p);
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Planet[] arrayP){
        double netForceY = 0;
        for (Planet p : arrayP){
            if (this.equals(p)){continue;}
            netForceY = netForceY + this.calcForceExertedByY(p);
        }
        return netForceY;
    }

    public void update(double dt, double fX, double fY){
        double acceleX = fX / mass;
        double acceleY = fY / mass;
        xxVel = xxVel + acceleX * dt;
        yyVel = yyVel + acceleY * dt;

        xxPos = xxPos + xxVel * dt;
        yyPos = yyPos + yyVel * dt;
    }

    public void draw(){
        String imgName = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, imgName);
    }
}