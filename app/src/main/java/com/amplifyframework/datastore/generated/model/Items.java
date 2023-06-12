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

/** This is an auto generated class representing the Items type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Items", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Items implements Model {
  public static final QueryField ID = field("Items", "id");
  public static final QueryField ITEM = field("Items", "item");
  public static final QueryField COUNT = field("Items", "count");
  public static final QueryField RULE = field("Items", "rule");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String item;
  private final @ModelField(targetType="Int", isRequired = true) Integer count;
  private final @ModelField(targetType="Int") List<Integer> rule;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getItem() {
      return item;
  }
  
  public Integer getCount() {
      return count;
  }
  
  public List<Integer> getRule() {
      return rule;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Items(String id, String item, Integer count, List<Integer> rule) {
    this.id = id;
    this.item = item;
    this.count = count;
    this.rule = rule;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Items items = (Items) obj;
      return ObjectsCompat.equals(getId(), items.getId()) &&
              ObjectsCompat.equals(getItem(), items.getItem()) &&
              ObjectsCompat.equals(getCount(), items.getCount()) &&
              ObjectsCompat.equals(getRule(), items.getRule()) &&
              ObjectsCompat.equals(getCreatedAt(), items.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), items.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getItem())
      .append(getCount())
      .append(getRule())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Items {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("item=" + String.valueOf(getItem()) + ", ")
      .append("count=" + String.valueOf(getCount()) + ", ")
      .append("rule=" + String.valueOf(getRule()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ItemStep builder() {
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
  public static Items justId(String id) {
    return new Items(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      item,
      count,
      rule);
  }
  public interface ItemStep {
    CountStep item(String item);
  }
  

  public interface CountStep {
    BuildStep count(Integer count);
  }
  

  public interface BuildStep {
    Items build();
    BuildStep id(String id);
    BuildStep rule(List<Integer> rule);
  }
  

  public static class Builder implements ItemStep, CountStep, BuildStep {
    private String id;
    private String item;
    private Integer count;
    private List<Integer> rule;
    @Override
     public Items build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Items(
          id,
          item,
          count,
          rule);
    }
    
    @Override
     public CountStep item(String item) {
        Objects.requireNonNull(item);
        this.item = item;
        return this;
    }
    
    @Override
     public BuildStep count(Integer count) {
        Objects.requireNonNull(count);
        this.count = count;
        return this;
    }
    
    @Override
     public BuildStep rule(List<Integer> rule) {
        this.rule = rule;
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
    private CopyOfBuilder(String id, String item, Integer count, List<Integer> rule) {
      super.id(id);
      super.item(item)
        .count(count)
        .rule(rule);
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
     public CopyOfBuilder rule(List<Integer> rule) {
      return (CopyOfBuilder) super.rule(rule);
    }
  }
  
}
