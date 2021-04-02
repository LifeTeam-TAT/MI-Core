// package org.ace.insurance.versionref;
//
// import java.io.Serializable;
//
// import javax.persistence.EntityListeners;
// import javax.persistence.AccessType;
// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.NamedQueries;
// import javax.persistence.NamedQuery;
// import javax.persistence.Table;
// import javax.persistence.TableGenerator;
// import javax.persistence.Transient;
//
// import org.ace.insurance.common.TableName;
// import org.ace.java.component.FormatID;
// import org.apache.commons.lang.builder.EqualsBuilder;
// import org.apache.commons.lang.builder.HashCodeBuilder;
//
/// **
// * Entity implementation class for Entity: VersionRef
// *
// */
// @Entity
// @Table(name=TableName.WSVERSIONREF)
// @TableGenerator(name = "WSVERREF_GEN", table = "ID_GEN", pkColumnName =
// "GEN_NAME",
// valueColumnName = "GEN_VAL", pkColumnValue = "WSVERREF_GEN", allocationSize =
// 10)
// @NamedQueries(value = {
// @NamedQuery(name = "VersionRef.findAll",
// query = "SELECT v FROM VersionRef v"),
// @NamedQuery(
// name = "VersionRef.findAllByGroup",
// query = "SELECT v FROM VersionRef v WHERE v.group = :group ORDER BY
// v.versionNo DESC"),
// @NamedQuery(
// name = "VersionRef.findById",
// query = "SELECT v FROM VersionRef v WHERE v.entityId = :id"),
// @NamedQuery(
// name = "VersionRef.findIndexesByGroup",
// query = "SELECT v.entityName, MAX(v.versionNo) FROM VersionRef v " +
// "WHERE v.group = :group " +
// "GROUP BY v.entityName"),
// @NamedQuery(
// name = "VersionRef.findUpdatesByGroup",
// query = "SELECT v FROM VersionRef v " +
// "WHERE v.versionNo > :versionNo AND v.group = :group " +
// "ORDER BY v.entityName"),
// @NamedQuery(
// name = "VersionRef.findUpdatesByGroupAndEntityName",
// query = "SELECT v FROM VersionRef v " +
// "WHERE v.versionNo > :versionNo AND v.group = :group AND v.entityName =
// :entityName " +
// "ORDER BY v.entityName"),
// @NamedQuery(
// name = "VersionRef.findMaxVersionNoByGroupAndEntityName",
// query = "SELECT MAX(v.versionNo) FROM VersionRef v " +
// "WHERE v.group = :group AND v.entityName = :entityName"),
// @NamedQuery(
// name = "VersionRef.findAllByEntityId",
// query = "SELECT v FROM VersionRef v " +
// "WHERE v.entityId = :entityId")
// })
// @EntityListeners(IDInterceptor.class)
// public class VersionRef implements Serializable {
//
// @Transient
// private String id;
// @Transient
// private String prefix;
// private String entityId;
// private String entityName;
// private int versionNo;
// private String operation;
// @Column(name = "groupName")
// private String group;
// private static final long serialVersionUID = 1L;
//
// public VersionRef() {
// super();
// }
//
// public VersionRef(String entityId, String entityName, int versionNo,
// String operation, String group) {
// super();
// this.entityId = entityId;
// this.entityName = entityName;
// this.versionNo = versionNo;
// this.operation = operation;
// this.group = group;
// }
//
// @Id
// @GeneratedValue(strategy = GenerationType.TABLE, generator = "WSVERREF_GEN")
// @Access(value = AccessType.PROPERTY)
// public String getId() {
// return id;
// }
//
// public void setId(String id) {
// if (id != null) {
// this.id = FormatID.formatId(id, getPrefix(), 10);
// }
// }
//
// public String getEntityId() {
// return this.entityId;
// }
//
// public void setEntityId(String entityId) {
// this.entityId = entityId;
// }
// public String getEntityName() {
// return this.entityName;
// }
//
// public void setEntityName(String entityName) {
// this.entityName = entityName;
// }
// public int getVersionNo() {
// return this.versionNo;
// }
//
// public void setVersionNo(int versionNo) {
// this.versionNo = versionNo;
// }
// public String getOperation() {
// return this.operation;
// }
//
// public void setOperation(String operation) {
// this.operation = operation;
// }
//
// public String getGroup() {
// return group;
// }
//
// public void setGroup(String group) {
// this.group = group;
// }
//
// public String getPrefix() {
// return prefix;
// }
//
// public void setPrefix(String prefix) {
// this.prefix = prefix;
// }
//
// @Override
// public int hashCode() {
// final int prime = 31;
// int result = 1;
// result = prime * result + ((entityId == null) ? 0 : entityId.hashCode());
// result = prime * result + ((entityName == null) ? 0 : entityName.hashCode());
// result = prime * result + ((group == null) ? 0 : group.hashCode());
// result = prime * result + ((id == null) ? 0 : id.hashCode());
// result = prime * result + ((operation == null) ? 0 : operation.hashCode());
// result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
// result = prime * result + versionNo;
// return result;
// }
//
// @Override
// public boolean equals(Object obj) {
// if (this == obj)
// return true;
// if (obj == null)
// return false;
// if (getClass() != obj.getClass())
// return false;
// VersionRef other = (VersionRef) obj;
// if (entityId == null) {
// if (other.entityId != null)
// return false;
// } else if (!entityId.equals(other.entityId))
// return false;
// if (entityName == null) {
// if (other.entityName != null)
// return false;
// } else if (!entityName.equals(other.entityName))
// return false;
// if (group == null) {
// if (other.group != null)
// return false;
// } else if (!group.equals(other.group))
// return false;
// if (id == null) {
// if (other.id != null)
// return false;
// } else if (!id.equals(other.id))
// return false;
// if (operation == null) {
// if (other.operation != null)
// return false;
// } else if (!operation.equals(other.operation))
// return false;
// if (prefix == null) {
// if (other.prefix != null)
// return false;
// } else if (!prefix.equals(other.prefix))
// return false;
// if (versionNo != other.versionNo)
// return false;
// return true;
// }
//
// }
