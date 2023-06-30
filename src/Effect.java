


public class Effect {

	ModelParameter parameter;

	Enviroment enviroment;

	// Calendar date;

	public Effect(ModelParameter parameter, Enviroment envirment) {
		super();
		this.parameter = parameter;
		this.enviroment = envirment;

	}

	public ModelParameter getParameter() {
		return parameter;
	}

	public void setParameter(ModelParameter parameter) {
		this.parameter = parameter;
	}

	public Enviroment getEnvirment() {
		return enviroment;
	}

	public void setEnvirment(Enviroment envirment) {
		this.enviroment = envirment;
	}

	// public Calendar getDate() {
	// return date;
	// }
	//
	// public void setDate(Calendar date) {
	// this.date = date;
	// }

	@Override
	public String toString() {
		return "Effect [parameter=" + parameter + ", envirment=" + enviroment
				+ "]";
	}

	public Enviroment calculateEffect(Enviroment env) {
		return enviroment;
	}

}
