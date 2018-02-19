package com.jblearning.candystorev5;

public class Task {
  private int id;
  private String name;
  private String description;
  private int priority;
  //need deadline date here...

  public Task( int newId, String newTitle, String newText, int newPriority ) {
    setId( newId );
    setName( newTitle );
    setText( newText );
    setPriority(newPriority);
  }

  public void setId( int newId ) {
    id = newId;
  }

  public void setName( String newTitle ) {
    name = newTitle;
  }

  public void setText( String newDescription ) {
    if( newDescription != null )
      description = newDescription;
  }

  public void setPriority(int newPriority)
  {
    priority = newPriority;
  }



  public int getId( ) {
    return id;
  }

  public String getName( ) {
    return name;
  }

  public int getPriority()
  {
    return priority;
  }

  public String getText( ) {
    return description;
  }

  public String toString( ) {
    return id + "; " + name + "; " + description + priority;
  }
}