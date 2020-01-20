package pl.kruko.PracaInz.dataTransferObjects;

import pl.kruko.PracaInz.models.Type;

public class VisitTypeDTO extends VisitTypeForSearchDTO
{

	private Type type;
	
		public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

		
}
