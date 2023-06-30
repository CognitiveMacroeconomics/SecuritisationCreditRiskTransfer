



import java.util.ArrayList;
import java.util.Iterator;

public class ActionList {
	private static final long serialVersionUID = 1L;
	private ArrayList<PortfolioAction> actionList = new ArrayList();
	private State state;
	private ArrayList<Action> actionDomain = new ArrayList<Action>();
	private int actionKey;

	public ActionList(State s) {
		this.state = s;
	}
	
	public ActionList(){
		
	}
	
	public void add(int key, Action action){
		actionDomain.add(key, action);
	}
	
	public Action getActionAt(int key){
		return actionDomain.get(key);
	}

	public boolean add(PortfolioAction a) {
		if (!(this.actionList.contains(a)))
			this.actionList.add(a);
		return true;
	}

	public Iterator<PortfolioAction> iterator() {
		return this.actionList.iterator();
	}

	public int size() {
		int size = 0;
		if(this.actionList.size()>0){
			size =  this.actionList.size();
		} else
			if(this.actionDomain.size()>0){
				size = this.actionDomain.size();
			
		}
		return size;
	}

	public State getState() {
		return this.state;
	}

	public PortfolioAction get(int i) {
		if (this.actionList.size() != 0)
			return (this.actionList.get(i));
		return null;
	}

	@Override
	public String toString() {
		String s = "";
		for (PortfolioAction action : this.actionList)
			s = s + action + "\n";
		return s;
	}
}