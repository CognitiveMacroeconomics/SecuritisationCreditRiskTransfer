

//import org.apache.commons.collections15.Transformer;
//
//import edu.uci.ics.jung.graph.DelegateForest;
//import edu.uci.ics.jung.visualization.VisualizationViewer;
//import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
//import edu.uci.ics.jung.visualization.decorators.EdgeShape;
//import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;


public class AgentNeighbourhoodViewer {
	
//	private DelegateForest<EconomicAgent, String> graph;
//
//	/**
//	 * This class is a JFreeChart implementing class used to visualiase agent worlds and neighbourhoods
//	 * 
//	 */
//	
//	public VisualizationViewer<? extends Object,? extends Object> 
//	getNieghbourhoodVisualizer(ObjGrid agentLayer, ObjGrid agentLayer2, int x, int y){
//		
//		graph = new DelegateForest<EconomicAgent,String>();
//		
//		return null;
//		
//		
//	}
	
	
//    public VisualizationViewer<? extends Object> getNieghbourhoodVisualizer 
//    (ArrayList<Contagion> contagionList, ArrayList<Node> nodesList, int x, int y){
//    	
//    	contagionNodes = new ContagionNode[nodesList.size()];
//    	contagionNodesTmp = new ContagionNode[nodesList.size()];
//    	   	
//    	
//    	
//    	// create a simple graph for the demo
//        graph = new DelegateForest<ContagionNode,String>();
//        
////        graph.addVertex("root");
//        
//        this.createTree(contagionList, nodesList);
//        
////        layout = new TreeLayout<String,String>(graph);
////        layout = new FRLayout(graph);
//        radialLayout = new RadialTreeLayout<ContagionNode,String>(graph);
//        
//		Transformer<ContagionNode, Paint> paintTransformer = new Transformer<ContagionNode, Paint>()
//		{
//
//			public Paint transform(ContagionNode n) {
//				// TODO Auto-generated method stub
//							
//				return n.getNode().getColor();
//			}
//			
//		};
////		
////		Transformer<String, Integer> sizeTransformer = new Transformer<String, Integer>()
////		{
////
////			public Integer transform(String n) {
////				// TODO Auto-generated method stub
////				return n.getSize();
////			}
////			
////		};
//        
//		
//		this.x = x;
//		this.y = y;
//		radialLayout.setSize(new Dimension(x,y));
//		
//		vv =  new VisualizationViewer<ContagionNode,String>(radialLayout, new Dimension(x,y));
//        vv.setBackground(Color.white);
//        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
//        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
//        vv.getRenderContext().setVertexFillPaintTransformer(paintTransformer);
//        
//        vv.setVertexToolTipTransformer(new ToStringLabeller());
//        
//       
//        
//        rings = new Rings();
//		
//		
//		final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
//		vv.setGraphMouse(graphMouse);
//		
//		vv.addPreRenderPaintable(rings);
//		
//		this.vv.repaint();
//		
//		
////		Container content = new Container ();
////		content.add(vv);
//		
//		return vv;
//	}
//	

}
