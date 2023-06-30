


public abstract class ModelParameter implements EffectCalculator {

	String name;

	EffectType effectType; //

	double effect; // how much impact on the number of default

	Object value;

	@Override
	public String toString() {
		return "ModelParameter [name=" + name + ", effectType=" + effectType
				+ ", effect=" + effect + ", value=" + value + "]";
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EffectType getEffectType() {
		return effectType;
	}

	public void setEffectType(EffectType effectType) {
		this.effectType = effectType;
	}

	public double getEffect() {
		return effect;
	}

	public void setEffect(double effect) {
		this.effect = effect;
	}

	public ModelParameter(String name, EffectType effectType, double effect) {
		super();
		this.name = name;
		this.effectType = effectType;
		this.effect = effect;
	}

	//@Override
	@Override
	public abstract Enviroment calculateEffect(Enviroment env);

}
