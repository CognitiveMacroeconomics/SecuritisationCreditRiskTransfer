

/**
 *
 * @author Owner
 */
public class GenWiener {
    
    /**
     * Creates a new instance of GenWiener 
     */
    public GenWiener() {
    }
     private double constdrift;
     private double wienerValue;
     private  void setDrift(double driftval)
    {
        constdrift=driftval;
    }
    public double getDrift()
    {
        return constdrift;
    }
    private void setWiener(double wienval)
    {
    	wienerValue=wienval;
    }
    public double getwienerVal()
    {
        return wienerValue;
    }
    public double genWienerproc(double drift, double t, double sd)
    {
        Wiener w=new Wiener();
        double deltaz;
        double driftvalue;
        double deltax;
        deltaz=w.wienerProcess(t);
         setWiener(deltaz);
         driftvalue=drift*t;
         setDrift(driftvalue);
         
         deltax=(driftvalue+(sd*deltaz));
         return deltax;
    }
}
