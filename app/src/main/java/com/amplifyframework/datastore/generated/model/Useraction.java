package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Useraction type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Useractions", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Useraction implements Model {
  public static final QueryField ID = field("Useraction", "id");
  public static final QueryField USERNAME = field("Useraction", "username");
  public static final QueryField ITEM = field("Useraction", "item");
  public static final QueryField COUNT = field("Useraction", "count");
  public static final QueryField TIME = field("Useraction", "time");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="String", isRequired = true) String item;
  private final @ModelField(targetType="Int", isRequired = true) Integer count;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime time;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getUsername() {
      return username;
  }
  
  public String getItem() {
      return item;
  }
  
  public Integer getCount() {
      return count;
  }
  
  public Temporal.DateTime getTime() {
      return time;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Useraction(String id, String username, String item, Integer count, Temporal.DateTime time) {
    this.id = id;
    this.username = username;
    this.item = item;
    this.count = count;
    this.time = time;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Useraction useraction = (Useraction) obj;
      return ObjectsCompat.equals(getId(), useraction.getId()) &&
              ObjectsCompat.equals(getUsername(), useraction.getUsername()) &&
              ObjectsCompat.equals(getItem(), useraction.getItem()) &&
              ObjectsCompat.equals(getCount(), useraction.getCount()) &&
              ObjectsCompat.equals(getTime(), useraction.getTime()) &&
              ObjectsCompat.equals(getCreatedAt(), useraction.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), useraction.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUsername())
      .append(getItem())
      .append(getCount())
      .append(getTime())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Useraction {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("item=" + String.valueOf(getItem()) + ", ")
      .append("count=" + String.valueOf(getCount()) + ", ")
      .append("time=" + String.valueOf(getTime()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UsernameStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Useraction justId(String id) {
    return new Useraction(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      username,
      item,
      count,
      time);
  }
  public interface UsernameStep {
    ItemStep username(String username);
  }
  

  public interface ItemStep {
    CountStep item(String item);
  }
  

  public interface CountStep {
    TimeStep count(Integer count);
  }
  

  public interface TimeStep {
    BuildStep time(Temporal.DateTime time);
  }
  

  public interface BuildStep {
    Useraction build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UsernameStep, ItemStep, CountStep, TimeStep, BuildStep {
    private String id;
    private String username;
    private String item;
    private Integer count;
    private Temporal.DateTime time;
    @Override
     public Useraction build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Useraction(
          id,
          username,
          item,
          count,
          time);
    }
    
    @Override
     public ItemStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }
    
    @Override
     public CountStep item(String item) {
        Objects.requireNonNull(item);
        this.item = item;
        return this;
    }
    
    @Override
     public TimeStep count(Integer count) {
        Objects.requireNonNull(count);
        this.count = count;
        return this;
    }
    
    @Override
     public BuildStep time(Temporal.DateTime time) {
        Objects.requireNonNull(time);
        this.time = time;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String username, String item, Integer count, Temporal.DateTime time) {
      super.id(id);
      super.username(username)
        .item(item)
        .count(count)
        .time(time);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder item(String item) {
      return (CopyOfBuilder) super.item(item);
    }
    
    @Override
     public CopyOfBuilder count(Integer count) {
      return (CopyOfBuilder) super.count(count);
    }
    
    @Override
     public CopyOfBuilder time(Temporal.DateTime time) {
      return (CopyOfBuilder) super.time(time);
    }
  }
  
}
