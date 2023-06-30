import java.awt.Color;
import java.util.ArrayList;

import jas.engine.Sim;
import jas.engine.SimModel;
import jas.plot.ColorMap;
import jas.plot.LayerDblGridDrawer;
import jas.plot.LayerObjGridDrawer;
import jas.plot.LayeredSurfaceFrame;
import jas.plot.LayeredSurfacePanel;


public class SecuritisationModelObserverJAS  extends SimModel{
	
	private MarkoseDYangModel_V01 model;
	
	private LayeredSurfaceFrame display;

	private LayeredSurfacePanel display2;
	
	
	  public SecuritisationModelObserverJAS(){
		model = (MarkoseDYangModel_V01) Sim.engine.getModelWithID("MarkoseDYangModel_V01");
	  }

	  public SecuritisationModelObserverJAS(MarkoseDYangModel_V01 model){
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
	    cRange.addColor(0, Color.black);
	    cRange.addColor(1, Color.blue);

	    LayerDblGridDrawer d = new LayerDblGridDrawer("Geo-Economic Space", model.getWorld().temperatureLayer, cRange);
	    LayerObjGridDrawer bld = new LayerObjGridDrawer("Banks", model.getWorld().agentLayer, Color.blue);
	    LayerObjGridDrawer fld = new LayerObjGridDrawer("Funds", model.getWorld().fundLayer, Color.yellow);
//	    LayerObjGridDrawer blfld = new LayerObjGridDrawer("Funds", model.getWorld().fundLayer, Color.green);
//	    LayerObjGridDrawer brfld = new LayerObjGridDrawer("Funds", model.getWorld().fundLayer, Color.red);
//	    LayerObjGridDrawer rtfld = new LayerObjGridDrawer("Funds", model.getWorld().fundLayer, Color.orange);
//	    LayerObjGridDrawer fld = getfundColors().get(0);
//	    LayerObjGridDrawer blfld = getfundColors().get(1);
//	    LayerObjGridDrawer brfld = getfundColors().get(2);
//	    LayerObjGridDrawer rtfld = getfundColors().get(3);

	    
	    LayerObjGridDrawer dld = new LayerObjGridDrawer("Dealers", model.getWorld().dealerLayer, Color.magenta);

	    display = new LayeredSurfaceFrame(model.getWorld().xSize, model.getWorld().ySize, 4);
	    display2 = new LayeredSurfacePanel(model.getWorld().xSize, model.getWorld().ySize, 5);
	    display.addLayer(d);
	    display.addLayer(bld);
	    display.addLayer(fld);
//	    display.addLayer(blfld);
//	    display.addLayer(brfld);
//	    display.addLayer(rtfld);
	    display.addLayer(dld);
	    //addSimWindow(display);
	    
	    display2.addLayer(d);
	    display2.addLayer(bld);
	    display2.addLayer(fld);
//	    display2.addLayer(blfld);
//	    display2.addLayer(brfld);
//	    display2.addLayer(rtfld);
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
	  
	  
	  public ArrayList<LayerObjGridDrawer> getfundColors(){
		  ArrayList<LayerObjGridDrawer> layers = new ArrayList<LayerObjGridDrawer>();
		  LayerObjGridDrawer fld = null;
		    LayerObjGridDrawer blfld = null;
		    LayerObjGridDrawer brfld = null;
		    LayerObjGridDrawer rtfld = null;
		  if(MarkoseDYangModel_V01.lapfTypeString == "Simple Fund"){
		    	fld = new LayerObjGridDrawer("Funds", model.getWorld().fundLayer, Color.gray);
		    	
		    } else{
		   for(int i = 0; i < model.lapfList.size(); i++){
		    	if(model.lapfList.get(i).temperament == "Bull"){
		    		blfld = new LayerObjGridDrawer("Funds", model.getWorld().fundLayer, Color.green);
		    	} else
		    		if(model.lapfList.get(i).temperament == "Bear"){ //Rational
		    			brfld = new LayerObjGridDrawer("Funds", model.getWorld().fundLayer, Color.red);
			    	}
		    		else
			    		if(model.lapfList.get(i).temperament == "Rational"){
			    			rtfld = new LayerObjGridDrawer("Funds", model.getWorld().fundLayer, Color.orange);
				    	}
		    }
		    }

		    
		  layers.add(fld);
		  layers.add(blfld);
		  layers.add(brfld);
		  layers.add(rtfld);
		  return layers;

	  }
	  
	  



}
