// TODO UNUSED
// package org.ace.insurance.util.prefix;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import org.ace.insurance.common.interfaces.IEntity;
// import org.apache.commons.lang.builder.EqualsBuilder;
// import org.apache.commons.lang.builder.HashCodeBuilder;
//
/// **
// * <code>PrefixMutation</code>
// *
// * @author Ace Plus
// * @version 1.0.0
// * @Date 2013/07/02
// */
// public class PrefixMutation {
// private List<PrefixMutant> mutantList;
//
//// ------------------------------ Constructors ------------------------------
//
// public PrefixMutation() {
// this(new ArrayList<PrefixMutant>());
// }
//
// public PrefixMutation(List<PrefixMutant> mutants) {
// this.mutantList = mutants;
// }
//
//// ------------------------------ Accessors and Mutators
// ------------------------------
//
// public List<PrefixMutant> getMutantList() {
// return mutantList;
// }
//
//// ------------------------------ Overriden and Utility Methods
// ------------------------------
//
// public void addMutant(IEntity entity, Class type) {
// PrefixMutant mutant = new PrefixMutant(entity, type);
// addMutant(mutant);
// }
//
// public void addMutant(PrefixMutant mutant) {
// if(mutantList == null) {
// mutantList = new ArrayList<PrefixMutant>();
// }
// mutantList.add(mutant);
// }
//
// public void removeMutant(PrefixMutant mutant) {
// if(mutantList != null) {
// mutantList.remove(mutant);
// }
// }
//
// public void appendMutants(List<PrefixMutant> mutants) {
// if(mutants != null) {
// for(PrefixMutant mutant : mutants) {
// addMutant(mutant);
// }
// }
// }
//
// @Override
// public boolean equals(Object object) {
// return EqualsBuilder.reflectionEquals(this, object);
// }
//
// @Override
// public int hashCode() {
// return HashCodeBuilder.reflectionHashCode(this);
// }
// }
