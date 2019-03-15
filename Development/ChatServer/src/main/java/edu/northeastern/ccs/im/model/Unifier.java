package edu.northeastern.ccs.im.model;

import java.util.List;

/**
 * Parent class of Users and Groups
 */
public interface Unifier {

  String getName();

  void setName(String name);

  List<Unifier> getMembers();

  boolean isGroup();
}
