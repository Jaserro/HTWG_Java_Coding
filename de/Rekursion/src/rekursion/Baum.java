package rekursion;

import java.awt.*;

public class Baum {


    private static final double SIGMA = 30;


    public static void baum1(double x, double y, double l, double w){
        if(l < 0.1){
            return;
        }
        if (l < 0.3){
            StdDraw.setPenColor(StdDraw.GREEN);
        }else{
            StdDraw.setPenColor(new Color(62, 35, 15));
        }
        double aX = x;
        double aY = y;

        double s =  l * Math.sin(Math.toRadians(w));
        double c =  l * Math.cos(Math.toRadians(w));

        double bX = aX + c;
        double bY = aY + s;

        double cX = aX + c - s;
        double cY = aY + s + c;

        double dX = aX - s;
        double dY = aY + c;


        double u = l * Math.cos(Math.toRadians(SIGMA));
        double v = l * Math.sin(Math.toRadians(SIGMA));

        double eX = dX + u * Math.cos(Math.toRadians(SIGMA + w));
        double eY = dY + u * Math.sin(Math.toRadians(SIGMA + w));

        StdDraw.line(aX,aY,dX,dY);
        StdDraw.line(bX,bY,cX,cY);

        baum1(dX,dY,u,w + SIGMA);//goßer Zweig
        baum1(eX,eY,v,-(90-(w + SIGMA)));//kleiner zweig
    }

    public static void baum2(double x,double y, double l, double w){
        if(l < 0.1){
            return;
        }
        if (l < 0.3){
            StdDraw.setPenColor(StdDraw.GREEN);
        }else{
            StdDraw.setPenColor(new Color(62, 35, 15));
        }
        double max = l * 2;
        double h = Math.random() * (max - l + 1) + l;
        double winkel = Math.random() * (90 - 20 +1) + 1;
        System.out.println(h);
        System.out.println(winkel);

        double aX = x;
        double aY = y;

        double s1 =  h * Math.sin(Math.toRadians(w));
        double c1 =  h * Math.cos(Math.toRadians(w));

        double s2 =  l * Math.sin(Math.toRadians(w));
        double c2 =  l * Math.cos(Math.toRadians(w));

        double bX = aX + c2;
        double bY = aY + s2;

        double cX = aX + c2 - s1;
        double cY = aY + s2 + c1;

        double dX = aX - s1;
        double dY = aY + c1;


        double u = l * Math.cos(Math.toRadians(winkel));
        double v = l * Math.sin(Math.toRadians(winkel));

        double eX = dX + u * Math.cos(Math.toRadians(winkel + w));
        double eY = dY + u * Math.sin(Math.toRadians(winkel + w));

        StdDraw.line(aX,aY,dX,dY);
        StdDraw.line(bX,bY,cX,cY);

        baum2(dX,dY,u,w + winkel);//goßer Zweig
        baum2(eX,eY,v,-(90-(w + winkel)));//kleiner zweig

    }

}
