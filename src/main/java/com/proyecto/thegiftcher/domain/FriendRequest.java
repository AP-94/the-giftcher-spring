package com.proyecto.thegiftcher.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "friend_request")
public class FriendRequest implements Serializable {

	private static final long serialVersionUID = 1205558563863494858L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long friendRequestId;

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

    public Long getFriendRequestId() {
        return friendRequestId;
    }

    public void setFriendRequestId(Long friendRequestId) {
        this.friendRequestId = friendRequestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequest friendRequest = (FriendRequest) o;
        return Objects.equals(id, friendRequest.id) &&
                Objects.equals(userId, friendRequest.userId) &&
                Objects.equals(friendRequestId, friendRequest.friendRequestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, friendRequestId);
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", userId=" + userId +
                ", friendRequestId=" + friendRequestId +
                '}';
    }
}