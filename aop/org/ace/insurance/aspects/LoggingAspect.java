
  package org.ace.insurance.aspects;
  
  import org.apache.logging.log4j.LogManager; import
  org.apache.logging.log4j.Logger; import org.aspectj.lang.JoinPoint; import
  org.aspectj.lang.Signature; import
  org.aspectj.lang.annotation.AfterReturning; import
  org.aspectj.lang.annotation.AfterThrowing; import
  org.aspectj.lang.annotation.Aspect; import
  org.aspectj.lang.annotation.Before; import
  org.aspectj.lang.annotation.Pointcut;
  
  @Aspect 
  public class LoggingAspect {
  
	/*
	 * private Logger logger = LogManager.getLogger(this.getClass());
	 * 
	 * @Before("allInsuComp() || allComp()") public void
	 * loggingServiceAround(JoinPoint jointPoint) { Signature signature =
	 * jointPoint.getSignature(); String declareTypeName =
	 * signature.getDeclaringTypeName(); String name = signature.getName(); String
	 * methodName = declareTypeName + "." + name + "() "; logger.debug(methodName +
	 * "method has been started."); }
	 * 
	 * @AfterReturning("allInsuComp() || allComp()") public void
	 * loggingServiceAfterReturn(JoinPoint joinPoint) { Signature signature =
	 * joinPoint.getSignature(); String declareTypeName =
	 * signature.getDeclaringTypeName(); String name = signature.getName(); String
	 * methodName = declareTypeName + "." + name + "() "; logger.debug(methodName +
	 * "method has been successfully finished."); }
	 * 
	 * @AfterThrowing("allInsuComp() || allComp()") public void
	 * loggingServiceAfterThrow(JoinPoint joinPoint) { Signature signature =
	 * joinPoint.getSignature(); String declareTypeName =
	 * signature.getDeclaringTypeName(); String name = signature.getName(); String
	 * methodName = declareTypeName + "." + name + "() "; logger.debug(methodName +
	 * "method has been failed."); }
	 * 
	 * @Pointcut("within(org.ace.java.component..*)") public void allComp() { }
	 * 
	 * @Pointcut("within(org.ace.insurance..*)") public void allInsuComp() { }
	 */
  
  }
 