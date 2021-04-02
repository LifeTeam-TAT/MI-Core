package org.ace.insurance.util.prefix;

import org.ace.insurance.common.interfaces.IEntity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <code>PrefixMutant</code>
 * 
 * @author Ace Plus
 * @version 1.0.0
 * @Date 2013/07/02
 */
public class PrefixMutant {
	private Class type;
	private IEntity entity;

//------------------------------ Constructors ------------------------------

	public PrefixMutant(IEntity entity, Class type) {
		this.type = type;
		this.entity = entity;
	}

//------------------------------ Accessors and Mutators ------------------------------

	public Class getType() {
		return type;
	}

	public IEntity getEntity() {
		return entity;
	}
	
//------------------------------ Overriden and Utility Methods ------------------------------

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
