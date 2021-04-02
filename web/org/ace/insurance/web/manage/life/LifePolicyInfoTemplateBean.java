package org.ace.insurance.web.manage.life;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.life.policy.LifePolicy;
import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "LifePolicyInfoTemplateBean")
public class LifePolicyInfoTemplateBean extends BaseBean {
	private LifePolicy lifePolicy;

	private void initializeInjection() {
		lifePolicy = (LifePolicy) getParam("lifePolicy");
		destroy();
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifePolicy");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

}
