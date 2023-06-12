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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField USERNAME = field("User", "username");
  public static final QueryField GROUP = field("User", "group");
  public static final QueryField AUTH = field("User", "auth");
  public static final QueryField COIN = field("User", "coin");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="String", isRequired = true) String group;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean auth;
  private final @ModelField(targetType="Int", isRequired = true) Integer coin;
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
  
  public String getGroup() {
      return group;
  }
  
  public Boolean getAuth() {
      return auth;
  }
  
  public Integer getCoin() {
      return coin;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private User(String id, String username, String group, Boolean auth, Integer coin) {
    this.id = id;
    this.username = username;
    this.group = group;
    this.auth = auth;
    this.coin = coin;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getUsername(), user.getUsername()) &&
              ObjectsCompat.equals(getGroup(), user.getGroup()) &&
              ObjectsCompat.equals(getAuth(), user.getAuth()) &&
              ObjectsCompat.equals(getCoin(), user.getCoin()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUsername())
      .append(getGroup())
      .append(getAuth())
      .append(getCoin())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("group=" + String.valueOf(getGroup()) + ", ")
      .append("auth=" + String.valueOf(getAuth()) + ", ")
      .append("coin=" + String.valueOf(getCoin()) + ", ")
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
  public static User justId(String id) {
    return new User(
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
      group,
      auth,
      coin);
  }
  public interface UsernameStep {
    GroupStep username(String username);
  }
  

  public interface GroupStep {
    AuthStep group(String group);
  }
  

  public interface AuthStep {
    CoinStep auth(Boolean auth);
  }
  

  public interface CoinStep {
    BuildStep coin(Integer coin);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UsernameStep, GroupStep, AuthStep, CoinStep, BuildStep {
    private String id;
    private String username;
    private String group;
    private Boolean auth;
    private Integer coin;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          username,
          group,
          auth,
          coin);
    }
    
    @Override
     public GroupStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }
    
    @Override
     public AuthStep group(String group) {
        Objects.requireNonNull(group);
        this.group = group;
        return this;
    }
    
    @Override
     public CoinStep auth(Boolean auth) {
        Objects.requireNonNull(auth);
        this.auth = auth;
        return this;
    }
    
    @Override
     public BuildStep coin(Integer coin) {
        Objects.requireNonNull(coin);
        this.coin = coin;
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
    private CopyOfBuilder(String id, String username, String group, Boolean auth, Integer coin) {
      super.id(id);
      super.username(username)
        .group(group)
        .auth(auth)
        .coin(coin);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder group(String group) {
      return (CopyOfBuilder) super.group(group);
    }
    
    @Override
     public CopyOfBuilder auth(Boolean auth) {
      return (CopyOfBuilder) super.auth(auth);
    }
    
    @Override
     public CopyOfBuilder coin(Integer coin) {
      return (CopyOfBuilder) super.coin(coin);
    }
  }
  
}
