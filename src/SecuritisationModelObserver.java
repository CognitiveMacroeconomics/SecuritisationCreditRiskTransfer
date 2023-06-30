import java.awt.Color;

import jas.engine.Sim;
import jas.engine.SimModel;
import jas.plot.ColorMap;
import jas.plot.LayerDblGridDrawer;
import jas.plot.LayerObjGridDrawer;
import jas.plot.LayeredSurfaceFrame;
import jas.plot.LayeredSurfacePanel;


public class SecuritisationModelObserver  extends SimModel{
	
	private MarkoseDYangModel_V01 model;
	
	private LayeredSurfaceFrame display;

	private LayeredSurfacePanel display2;
	
	
	  public SecuritisationModelObserver(){
		model = (MarkoseDYangModel_V01) Sim.engine.getModelWithID("MarkoseDYangModel_V01");
	  }

	  public SecuritisationModelObserver(MarkoseDYangModel_V01 model){
		this.model = model;
	  }


	@Override
	public void buildModel() {
		// TODO Auto-generated method stub
		buildDisplay();
	    buildActions();

	}

	@Override
	public void setParameters() {
		// TODO Auto-generated method stub
		Sim.openProbe(this, "Parameters");
	}
	
	
	  @Override
	public void simulationEnd(){
		  
	  }

	  public void buildDisplay(){

	    ColorMap cRange = new ColorMap(2);
	    cRange.addColor(0, Color.white);
	    cRange.addColor(1, Color.blue);

	    LayerDblGridDrawer d = new LayerDblGridDrawer("Geo-Economic Space", model.getWorld().temperatureLayer, cRange);
	    LayerObjGridDrawer bld = new LayerObjGridDrawer("Banks", model.getWorld().agentLayer, Color.blue);
	    LayerObjGridDrawer fld = new LayerObjGridDrawer("Funds", model.getWorld().fundLayer, Color.red);
	    LayerObjGridDrawer dld = new LayerObjGridDrawer("Dealers", model.getWorld().dealerLayer, Color.magenta);

	    display = new LayeredSurfaceFrame(model.getWorld().xSize, model.getWorld().ySize, 4);
	    display2 = new LayeredSurfacePanel(model.getWorld().xSize, model.getWorld().ySize, 5);
	    display.addLayer(d);
	    display.addLayer(bld);
	    display.addLayer(fld);
	    display.addLayer(dld);
	    //addSimWindow(display);
	    
	    display2.addLayer(d);
	    display2.addLayer(bld);
	    display2.addLayer(fld);
	    display2.addLayer(dld);
	  }

	  public void buildActions(){
	    eventList.scheduleSimple(0, 1, display, "update");
	    eventList.scheduleSimple(0, 1, display2, "revalidate");
	    eventList.scheduleSimple(0, 1, this, "setFailedBankColour");
	  }
	  
	  public LayeredSurfaceFrame getDisplayFrame(){
		  return this.display;
	  }
	  
	  public LayeredSurfacePanel getDisplayPanel(){
		  return this.display2;
	  }
	  
	  public void setFailedBankColour(){
		  for(int i = 0; i < model.bankList.size(); i++){
			  if (model.bankList.get(i).getStatus() == CorporateStatus.failed){
				  model.bankList.get(i).setColour(Color.DARK_GRAY);
			  }
		  }
	  }
	  
	  



}
