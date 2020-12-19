package gameClient.util;

public class Line {
    Point3D a,b;
    double slope;
    double n;

    public Line(Point3D a, Point3D b) {
        this.a = a;
        this.b = b;
        setSlope(a,b);
        setN();
    }
    public void setSlope(Point3D a, Point3D b) {
        double dx = b.x()-a.x();
        double dy = b.y()-a.y();
        this.slope = (dy/dx);
    }
    public double getSlope() {
        return this.slope;
    }
    public void setN() {
        this.n = (this.a.y() - (getSlope()*this.a.x()));
    }
    public double getN() {
        return this.n;
    }
    public double getx(double y) {
        return (y-getN())/getSlope();
    }
    public double gety(double x) {
        return (x*getSlope() + getN());
    }
    public double minx() {
        if(a.x()<b.x())
            return a.x();
        else
            return b.x();
    }
    public double miny() {
        if(a.y()<b.y())
            return a.y();
        else
            return b.y();
    }
    public double maxx(){
        if(a.x()>b.x())
            return a.x();
        else
            return b.x();
    }
    public double maxy(){
        if(a.y()>b.y())
            return a.y();
        else
            return b.y();
    }
    public boolean isOnLine(Point3D p) {
        return (this.gety(p.x())==p.y() && this.getx(p.y())==p.x() && p.x()>=minx() && p.x()<=maxx() && p.y()<=maxy() && p.y()>=miny());
    }

}
