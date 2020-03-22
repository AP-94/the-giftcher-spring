package com.proyecto.thegiftcher.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "friend")
public class Friend implements Serializable {

	private static final long serialVersionUID = 1205558563863494858L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long friendId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friendRequest = (Friend) o;
        return Objects.equals(id, friendRequest.id) &&
                Objects.equals(userId, friendRequest.userId) &&
                Objects.equals(friendId, friendRequest.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, friendId);
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", userId=" + userId +
                ", friendId=" + friendId +
                '}';
    }
}